package library.yugisoft.module;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yusuf on 12.10.2017.
 */

public class DataTable
{
    private static final long DataTableVersiyon = 18032701;
    public List<DataRow> Rows = new SmartList<DataRow>();
    public List<DataRow> mRows = new SmartList<DataRow>();
    public List<String> Columns = new SmartList<String>();
    public void add(String... values) {
        DataRow row = new DataRow();
        for (int i = 0; i < values.length; i++)
        {
            try
            {

                DataColumn col = new DataColumn();
                col.Name = Columns.get(i);
                col.Value = values[i];
                row.Cells.add(col);
            }
            catch (Exception e){}
        }
        this.mRows.add(row);
        if(getFilterText().equals("")) Rows =SmartList.Copy(mRows);
        else
            setFilterText(getFilterText());
    }
    private String FilterText="";
    private int PrimaryCell=0;
    private String PrimaryCellName = "";
    //region Constr
    public DataTable() {
    }

    public DataTable(String JSONDATA) {
        vLoad(JSONDATA);
    }

    public DataTable(String JSONDATA, String JSONKEY) {
        vLoad(JSONDATA, JSONKEY);
    }

    public DataTable(boolean asd, String... columns) {
        for (String s : columns) {
            Columns.add(s.replace(" ",""));
        }
    }

    //endregion
    // region JSON_LOAD
    public void vLoad(String str) {
        vLoad(str, "");
    }


    int column = 0;
    @TargetApi(Build.VERSION_CODES.N)
    public void vLoad(String str, String Key) {
        try {
            if (!str.equals("[]") && str != null)
            {
                if (str.equals("") || str.equals("null") || str.equals("Error")) {
                    str = "[{\"Aciklama\":\"Kayıt Bulunamadı...\",\"id\":\"1\"}]";
                    return;
                }

                if (!str.substring(0, 1).equals("[")) str = "[ " + str + " ]";
                try {


                    JSONArray job =  new JSONArray(str);

                    JSONArray json = (Key.length() > 0 ? job.getJSONObject(0).getJSONArray(Key) : new JSONArray(str));

                    List<Integer> ignoreCols = new ArrayList<Integer>();

                    for (int k = 0; k < json.length(); k++) {
                        DataRow row = new DataRow();
                        JSONObject ob = ((JSONObject) json.get(k));

                        Iterator it;

                        if (k == 0) {
                            it = ob.keys();
                            while (it.hasNext()) {
                                String s = it.next().toString();
                                    Columns.add(s.replace(" ",""));
                            }
                        }

                        it = ob.keys();
                        column = 0;
                        while (it.hasNext())
                        {

                            row.Cells.add(new DataColumn(Columns.get(column), ob.getString(it.next().toString())));
                            column++;
                        }
                        this.mRows.add(row);
                    }
                } catch (JSONException k) {

                }
            }
        } catch (Exception e) {

        }finally {
            if(getFilterText().equals("")) Rows =mRows;
            else
                setFilterText(getFilterText());
        }
    }

    public void vLoadParse(String str) {
        try {
            List<DataColumn> cols = new ArrayList<>();
            if(str.equals("[]") || str.equals("null") || str.equals("Error"))str="[{\"Aciklama\":\"Kayıt Bulunamadı...\",\"id\":\"1\"}]";
            JSONObject job = new JSONObject(str);
            try
            {
                JSONArray jArr = job.getJSONArray("properties");
                for(int i =0;i<jArr.length();i++) {
                    DataColumn col = new DataColumn();
                    JSONObject ob = (JSONObject) jArr.get(i);
                    Iterator it = ob.keys();
                    JSONObject c = jArr.getJSONObject(i);
                    col.Name = c.getString("Name");
                    col.Format = c.getString("Format");
                    switch (c.getString("Gravity"))
                    {
                        case "LEFT":    col.gravity= Gravity.LEFT;
                            break;
                        case "RIGHT":   col.gravity= Gravity.RIGHT;
                            break;
                        case "CENTER":  col.gravity= Gravity.CENTER;
                            break;
                        default:        col.gravity= Gravity.LEFT;
                            break;
                    }
                    col.Oran=c.getInt("Oran");
                    col.Value=c.getString("Value");;
                    cols.add(col);
                    Columns.add(col.Name.replace(" ",""));
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                JSONArray _row = job.getJSONArray("data");
                for(int i =0;i<_row.length();i++) {

                    DataRow row = new DataRow();
                    DataColumn col = new DataColumn();
                    JSONObject ob = (JSONObject) _row.get(i);
                    Iterator it = ob.keys();
                    int kolon =0;
                    while(it.hasNext())
                    {
                        DataColumn cl = new DataColumn();
                        cl.Oran=cols.get(kolon).Oran;
                        cl.Format=cols.get(kolon).Format;
                        cl.gravity=cols.get(kolon).gravity;
                        cl.Name=cols.get(kolon).Name;
                        cl.Value=ob.getString(it.next().toString());
                        row.Cells.add(cl);
                        kolon++;
                    }
                    this.Rows.add(row);
                }
            }
            catch (Exception e)
            {}
        }
        catch (JSONException e) {e.printStackTrace();}
    }

    public static DataTable CreateParser(String string)
    {
        DataTable dt = new DataTable();
        dt.vLoadParse(string);
        return dt;
    }
    //endregion
    //region GET
    //region STRGING
    public String get(int RowIndex, int ColumnIndex) {
        try {
            return Rows.get(RowIndex).get(ColumnIndex);
        } catch (Exception e) {
            return "";
        }
    }
    public String get(int RowIndex, String ColumnName) {
        try {
            return Rows.get(RowIndex).get(ColumnName);
        } catch (Exception e) {
            return "";
        }
    }

    //region Number Format KOLON ISIMLI
    public String getNFreplace(int RowIndex, String ColumnName, int bas) {
        return getNF(RowIndex,ColumnName,true,bas);
    }
    public String getNF(int RowIndex, String ColumnName, int bas) {
        return getNF(RowIndex,ColumnName,false,bas);
    }
    public String getNF2(int RowIndex, String ColumnName) {
        return getNF(RowIndex,ColumnName,false,2);
    }
    public String getNF2replace(int RowIndex, String ColumnName) {
        return getNF(RowIndex,ColumnName,true,2);
    }
    public String getNF(int RowIndex, String ColumnName, boolean replace, int bas) {
        return yugi.NF(get(RowIndex,ColumnName),replace,bas);
    }
    //endregion
    //region Number Format KOLON INDEXLI
    public String getNFreplace(int RowIndex, int ColumnName, int bas) {
        return getNF(RowIndex,ColumnName,true,bas);
    }
    public String getNF(int RowIndex, int ColumnName, int bas) {
        return getNF(RowIndex,ColumnName,false,bas);
    }
    public String getNF2(int RowIndex, int ColumnName) {
        return getNF(RowIndex,ColumnName,false,2);
    }
    public String getNF2replace(int RowIndex, int ColumnName) {
        return getNF(RowIndex,ColumnName,true,2);
    }
    public String getNF(int RowIndex, int ColumnName, boolean replace, int bas) {
        return yugi.NF(get(RowIndex,ColumnName),replace,bas);
    }
    //endregion
    //endregion
    //region INT
    public int getInt(int RowIndex, String ColumnName) {

        try {
            return Rows.get(RowIndex).getInt(ColumnName);
        } catch (Exception e) {
            return 0;
        }
    }
    public int getInt(int RowIndex, int ColumnIndex) {

        try {
            return Rows.get(RowIndex).getInt(ColumnIndex);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getIndex(int ColumnIndex,Object Valuee)
    {
        String Value = String.valueOf(Valuee);
        for(int i =0;i< Rows.size();i++)
        {
            if(Rows.get(i).get(ColumnIndex).equals(Value))
                return i;
        }
        return -1;
    }
    public int getIndex(String ColumnName,Object Valuee)
    {
        String Value = String.valueOf(Valuee);
        int ColumnIndex = -1;
        for(int i =0;i<Columns.size();i++)
        {
            if(Columns.get(i).equals(ColumnName)) {
                ColumnIndex = i;
                break;
            }
            if(ColumnIndex!=-1)break;
        }
        for(int i =0;i< Rows.size();i++)
        {
            if(Rows.get(i).get(ColumnIndex).equals(Value))
            {
                Log.i("|G|","DataTable : \n getIndex : "+i);
                return i;
            }
        }
        Log.i("|G|","DataTable : \n getIndex : -1");
        return -1;
    }
    //endregion
    //region DOUBLE
    public Double getDouble(int RowIndex, String ColumnName) {

        try {
            return Rows.get(RowIndex).getDouble(ColumnName);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double getDouble(int RowIndex, int ColumnIndex) {

        try {
            return Rows.get(RowIndex).getDouble(ColumnIndex);
        } catch (Exception e) {
            return 0.0;
        }
    }

    //endregion
    //region LONG
    public long getLong(int RowIndex, String ColumnName) {

        try {
            return Rows.get(RowIndex).getLong(ColumnName);
        } catch (Exception e) {
            return 0;
        }
    }

    public long getLong(int RowIndex, int ColumnIndex) {

        try {
            return Rows.get(RowIndex).getLong(ColumnIndex);
        } catch (Exception e) {
            return 0;
        }
    }

    //endregion
    //region BOOL
    public boolean getBool(int RowIndex, String ColumnName) {


        try {
            return Rows.get(RowIndex).getBool(ColumnName);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean getBool(int RowIndex, int ColumnIndex) {

        try {
            return Rows.get(RowIndex).getBool(ColumnIndex);
        } catch (Exception e) {
            return false;
        }
    }

    //endregion
    public int getPrimaryCell() {
        return PrimaryCell;
    }
    public String getFilterText() {
        return FilterText;
    }
    public String getPrimaryCellName() {
        return PrimaryCellName;
    }
    //region JSON
    public String getJsonData() {
        String str = "[\n";
        for (int i = 0; i < this.Rows.size(); i++) {
            if (i != 0) str += ",";
            str += getJsonData(i);
        }
        str += "\n]";
        return str;
    }

    public String getJsonData(int RowIndex) {
        String str = "";
        str += "{\n";
        try {
            for (int k = 0; k < this.Columns.size(); k++)
            {
                if (k != 0) str += "\n,";
                String cel = this.Rows.get(RowIndex).Cells.get(k).Value;
                if(cel.length()>0)
                    str += "\"" + this.Columns.get(k) + "\" : " + (cel.substring(0, 1).equals("{") ? cel : (cel.substring(0, 1).equals("[") ? cel : "\"" + cel + "\""));
                else
                    str += "\"" + this.Columns.get(k) + "\" : \"\" ";
            }
        } catch (Exception e) {
        }
        str += "\n}";
        return str;
    }


    public void setFilterText(String filterText) {
        FilterText = filterText;
        Filter<DataRow,String> filter = new Filter<DataRow,String>()
        {
            public boolean isMatched(DataRow object, String text)
            {
                String val =object.get(PrimaryCell).toLowerCase();
                if(Filterlike)
                    return val.contains(String.valueOf(text).toLowerCase());
                else
                    return val.equals(String.valueOf(text).toLowerCase());
                //return object.get(PrimaryCell).toLowerCase().contains(String.valueOf(text).toLowerCase());
            }
        };
        Rows = new FilterList().filterList(mRows, filter, filterText);
    }
    public void setPrimaryCell(int primaryCell) {
        PrimaryCell = primaryCell;
        try{ PrimaryCellName=Columns.get(primaryCell);}catch (Exception e){}
        for (DataRow row: Rows) {row.Primary=PrimaryCell;}
    }
    public void setPrimaryCellName(String primaryCellName) {
        PrimaryCellName = primaryCellName;
        for(int i=0;i<Columns.size();i++)
        {
            if(Columns.get(i).equals(primaryCellName))
            {
                setPrimaryCell(i);
                return;
            }
        }

    }



    //endregion
    //endregion


    //region Alt Class
    public class DataRow {



        int Primary=0;
        public List<DataColumn> Cells = new ArrayList<>();
        //region GET
        //region STRGING
        public String get(int ColumnIndex) {
            String str = "";
            try {
                str = Cells.get(ColumnIndex).Value;
            } catch (Exception e) {
            }
            return str;
        }
        public String get(String ColumnName) {
            String str = "";
            int k = 0;
            while (k < Cells.size()) {
                if (Cells.get(k).Name.equals(ColumnName)) {
                    try {
                        str = get(k);
                        k = 9999;
                    } catch (Exception ex) {
                        String sr = "";
                        sr = ex.getMessage().toString();
                    }
                }
                k++;
            }
            return str;

        }
        public String getNFreplace(String ColumnName, int bas)
        {
            return getNF(ColumnName,true,bas);
        }
        public String getNF(String ColumnName, int bas)
        {
            return getNF(ColumnName,false,bas);
        }
        public String getNF2(String ColumnName)
        {
            return getNF(ColumnName,false,2);
        }
        public String getNF2replace(String ColumnName)
        {
            return getNF(ColumnName,true,2);
        }
        public String getNF(String ColumnName, boolean replace, int bas) {
            return yugi.NF(get(ColumnName),replace,bas);
        }
        //endregion
        //region INT
        public int getInt(String ColumnName) {
            try {
                return Integer.parseInt(get(ColumnName).replace(".0", ""));
            } catch (Exception e) {
                return 0;
            }
        }

        public int getInt(int ColumnIndex) {
            try {
                return Integer.parseInt(get(ColumnIndex).replace(".0", ""));
            } catch (Exception e) {
                return 0;
            }
        }

        //endregion
        //region DOUBLE
        public Double getDouble(String ColumnName)
        {
            //
            try
            {
                return Double.parseDouble(get(ColumnName).replace(".0", ""));
            }
            catch (Exception e)
            {
                try
                {
                    return Double.parseDouble(get(ColumnName).replace(".0", "").replace(",","."));
                }
                catch (Exception ce)
                {
                    return 0.0;
                }
            }
        }

        public Double getDouble(int ColumnIndex) {
            try
            {
                return Double.parseDouble(get(ColumnIndex).replace(".0", ""));
            }
            catch (Exception e)
            {
                try
                {
                    return Double.parseDouble(get(ColumnIndex).replace(".0", "").replace(",","."));
                }
                catch (Exception ce)
                {
                    return 0.0;
                }
            }
        }

        //endregion
        //region LONG
        public long getLong(String ColumnName) {
            try {
                return Long.parseLong(get(ColumnName).replace(".0", ""));
            } catch (Exception e) {
                return 0;
            }
        }

        public long getLong(int ColumnIndex) {
            try {
                return Long.parseLong(get(ColumnIndex).replace(".0", ""));
            } catch (Exception e) {
                return 0;
            }
        }

        //endregion
        //region BOOL
        public boolean getBool(String ColumnName) {

            try {
                String s = (get(ColumnName).replace(".0", ""));
                switch (s.toLowerCase()) {
                    case "true":
                    case "1":
                        return true;
                    default:
                        return false;
                }

            } catch (Exception e) {
                return false;
            }
        }

        public boolean getBool(int ColumnIndex) {
            try {
                String s = (get(ColumnIndex).replace(".0", ""));
                switch (s.toLowerCase()) {
                    case "true":
                    case "1":
                        return true;
                    default:
                        return false;
                }

            } catch (Exception e) {
                return false;
            }
        }
        //endregion
        public void ToClass(Object ob) {
            Class c = ob.getClass();
            Field[] f = c.getFields();
            for (Field fi : f) {
                try {
                    fi.setAccessible(true);
                    String simlename = fi.getType().getSimpleName().toLowerCase();
                    String name = fi.getName();
                    switch (simlename) {
                        case "int":
                            fi.setInt(ob, this.getInt(name));
                            break;
                        case "long":
                            fi.setLong(ob, this.getLong(name));
                            break;
                        case "double":
                            fi.setDouble(ob, this.getDouble(name));
                            break;
                        case "boolean":
                            fi.setBoolean(ob, this.getBool(name));
                            break;
                        case "DataTable":
                            fi.set(ob, new DataTable(this.get(name)));
                            break;
                        default:

                            fi.set(ob, this.get(name));
                            break;
                    }


                } catch (Exception e) {
                    String ex = e.getMessage();
                }
            }
        }

        //endregion
        //region SET
        public void setValue(Object Key,Object Value) {
            int k = 0;
            while (k < Cells.size()) {
                if (Cells.get(k).Name.equals(Key.toString())) {
                    try {
                        Cells.get(k).Value=Value.toString();
                    }
                    catch (Exception ex) {

                    }
                }
                k++;
            }

        }
        //endregion
        @Override
        public String toString() {
            return (Columns.size()>0 ? get(PrimaryCell) : "");
        }
    }

    public static class DataColumn {
        public String Name, Value, Format = "";
        public int gravity = Gravity.LEFT, Oran = 30;

        public DataColumn() {

        }

        public DataColumn(String _name, String Deger) {
            Name = _name;
            Value = Deger;
        }
    }

    //endregion

    private boolean Filterlike =true;
    public boolean isFilterlike() {
        return Filterlike;
    }
    public void setFilterlike(boolean filterlike) {
        Filterlike = filterlike;
    }
    public boolean setValue(int RowIndex, String ColumnName,Object Value) {
        int k=0;
        while(k<Columns.size()) {
            if (Columns.get(k).equals(ColumnName))
            {
                Rows.get(RowIndex).Cells.get(k).Value= ""+Value;
                k=9999;
                return true;
            }
            k++;
        }
        return false;


    }
    public boolean setValue(int RowIndex, String ColumnName,Object Value,boolean addnew) {
        ColumnName=ColumnName.replace(" ","");
        if(addnew)
        {
            if(setValue(RowIndex,ColumnName,Value))
                return true;
            else
            {

                Columns.add(ColumnName);
                Rows.get(0).Cells.add(new DataColumn());
                return   setValue(0,ColumnName,Value);
            }
        }
        else
            return setValue(RowIndex,ColumnName,Value);
    }
    public static DataTable ToTable(Object ob) {
        DataTable dt = new DataTable();
        Class c = ob.getClass();
        Field[] fields = c.getFields();
        String[] s = new String[fields.length];
        int i =0;
        for (Field f:fields)
        {
            if(!f.getName().equals("$change") && !f.getName().equals("serialVersionUID"))
            {
                try
                {
                    if(i<(fields.length))
                    {
                        dt.Columns.add(f.getName());
                        String simlename = f.getType().getSimpleName().toLowerCase();
                        String name = f.getName();
                        switch (simlename)
                        {
                            case "int":
                            case "long":
                            case "double":
                            case "boolean":
                                s[i] = String.valueOf(f.get(ob));
                                break;
                            case "DataTable":
                                DataTable tmp = (DataTable) f.get(ob);
                                s[i] = (tmp.Rows.size()>1 ? tmp.getJsonData() : (tmp.Rows.size()== 1 ? tmp.getJsonData(0) : ""));
                                break;
                            case "list":
                                List l = (List)f.get(ob);
                                s[i] ="[";
                                int k=0;
                                for ( Object o :l)
                                {
                                    if(k>0)
                                        s[i] +=",";
                                    DataTable dtlist = DataTable.ToTable(o);
                                    s[i] += (dtlist.Rows.size()>1 ? dtlist.getJsonData() : (dtlist.Rows.size()== 1 ? dtlist.getJsonData(0) : ""));
                                    k++;
                                }
                                s[i] +="]";
                                break;
                            default:
                                s[i] = String.valueOf(f.get(ob));
                                break;
                        }

                    }
                }
                catch (Exception e){}
                i++;
            }
        }
        dt.add(s);


        return  dt;
    }
    public void ToClass(Object ob, int index) {
        Class c = ob.getClass();
        String classname = c.getSimpleName().toLowerCase();
        if (classname.contains("SmartList"))
        {
            SmartList list = (SmartList)ob;

            for (int ds =0 ;ds<this.Rows.size();ds++)
            {
                try
                {
                    Object o = Class.forName(list.ParentClas.getName()).newInstance();
                    this.ToClass(o,ds);
                    list.add(o);
                }
                catch (Exception e)
                {}
            }
            ob = list;
        }
        else
        {
            //region Fields
            Field[] f = c.getFields();
            String name="";
            int colindex= -1;
            for (Field fi : f) {
                try {
                    fi.setAccessible(true);
                    String simlename = fi.getType().getSimpleName().toLowerCase();

                    name = fi.getName();

                        colindex=this.getColumnIndex(name);

                    if (colindex!=-1)
                        switch (simlename) {
                            case "int":
                                fi.setInt(ob, this.getInt(index,colindex));
                                break;
                            case "long":
                                fi.setLong(ob, this.getLong(index,colindex));
                                break;
                            case "double":
                                fi.setDouble(ob, this.getDouble(index,colindex));
                                break;
                            case "boolean":
                                fi.setBoolean(ob, this.getBool(index,colindex));
                                break;
                            case "datatable":
                                fi.set(ob, new DataTable(this.get(index,colindex)));
                                break;
                            case "list":
                                SmartList list = (SmartList)fi.get(ob);

                                DataTable dtlist =new DataTable(this.get(index,colindex));
                                for (int ds =0 ;ds<dtlist.Rows.size();ds++)
                                {
                                    Object o = Class.forName(list.ParentClas.getName()).newInstance();
                                    dtlist.ToClass(o,ds);
                                    list.add(o);
                                }
                                fi.set(ob,list);
                                break;

                            default:
                                String value = this.get(index,colindex);
                                if(value.equals("null")) value="";
                                fi.set(ob, value);
                                break;
                        }

                } catch (Exception e)
                {
                    String ex = e.getMessage();

                    try
                    {
                        Object o = fi.get(ob);
                        new DataTable(this.get(index,name)).ToClass(o,index);
                        fi.set(ob, o);
                    }
                    catch (Exception ee)
                    {
                        ex = e.getMessage();
                    }
                }
            }
            //endregion
        }
    }
    private int getColumnIndex(String name) {
        for (int i =0 ;i<this.Columns.size();i++)
            if(this.Columns.get(i).equals(name))
                return i;
        return  -1;

    }
    public Object getClass(Class cl,int position) {
        Object o = null;
        try {
            o = Class.forName(cl.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.ToClass(o,position);
        return  o;
    }
    public void ToClass(Object ob) {
        Class c = ob.getClass();
        String classname = c.getSimpleName().toLowerCase();
        if (classname.equals("list")||classname.equals("smartlist") )
        {
            SmartList list = (SmartList)ob;

            for (int ds =0 ;ds<this.Rows.size();ds++)
            {
                try
                {
                    Object o = Class.forName(list.ParentClas.getName()).newInstance();
                    this.ToClass(o,ds);
                    list.add(o);
                }
                catch (Exception e)
                {}
            }
            ob = list;
        }
    }



    interface Filter<T,E> {
        public boolean isMatched(T object, E text);
    }


    public static class FilterList<E> {
        public  <T> List filterList(List<T> originalList, Filter filter, E text)
        {
            List<T> filterList = new ArrayList<T>();
            for (T object : originalList) {
                if (filter.isMatched(object, text)) {
                    filterList.add(object);
                } else {
                    continue;
                }
            }
            return filterList;
        }
    }



    String col="",value = "";
    public List<DataRow> Where(String pWhere)
    {
        List<List<DataRow>> results = new SmartList<>();
        for (String veya :pWhere.split("\\|"))
        {
            List<DataRow> tList = SmartList.Copy(mRows);
            for (String item: veya.split("&&"))
            {

                boolean like = false;
                String[] ops = {"==","%%","||"};

                for (String s: ops) {
                    if(item.indexOf(s)>0)
                    {
                        col = item.split(s)[0];
                        value = item.split(s)[1];
                        Filterlike = !s.equals("==");
                        break;
                    }
                }


                Filter<DataRow,String> filter = new Filter<DataRow,String>()
                {
                    public boolean isMatched(DataRow object, String text)
                    {
                        String val =object.get(col).toLowerCase();
                        if(Filterlike)
                            return val.contains(String.valueOf(text).toLowerCase());
                        else
                            return val.equals(String.valueOf(text).toLowerCase());
                    }
                };
                tList = new FilterList().filterList(tList, filter, value);
                results.add(tList);
            }
        }
        List tList = new SmartList();
        for (List l :results) {
            tList=  SmartList.Marge(tList,l,true);
        }
        return  tList;
    }



}