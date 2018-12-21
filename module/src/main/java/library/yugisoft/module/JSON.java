package library.yugisoft.module;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JSON<T> {
    public JSON() {
    }
    @Deprecated
    public static String ToString(Object object) {

        return ToString(object,0);
    }
    @Deprecated
    private static String ToString(Object object, int i) {
        return DataTable.ToTable(object).getJsonData(i);
    }
    public <E> T JsonToClass(Class<E> t, String Json) {

        try {
            Object o = t.newInstance();
            new DataTable(Json).ToClass(o);
            return (T) o;
        } catch (Exception ex) {
            return null;
        }
    }
    public static DataTable DataTable(Object object) {
        Class cl = object.getClass();
        String cl_type = cl.getSimpleName().toLowerCase();
        DataTable dataTable = new DataTable();


        if (cl_type.equals("list") || cl_type.equals("smartlist"))
        {
            List list = (List)object;
            for (Object ol:list)
            {
                dataTable.add(buildRow(ol,dataTable,ol.getClass()));
            }
        }
        else
        {
            dataTable.add(buildRow(object,dataTable,object.getClass()));
        }
        return  dataTable;
    }
    private static String[] buildRow(Object ob,DataTable dt,Class c) {
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
                        if (dt.Columns.size() < i+1)dt.Columns.add(f.getName());
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
                            case "datetime":
                            case "string":
                            case "char":
                                s[i] = String.valueOf(f.get(ob));
                                break;
                                default:
                                if (f.getType().isPrimitive())
                                {
                                    s[i] = String.valueOf(f.get(ob));
                                }
                                else
                                {
                                    s[i] = JSON.ToString(f.get(ob));
                                }
                                break;
                        }

                    }
                }
                catch (Exception e){}
                i++;
            }
        }
        return  s;
    }


    public static  <T> T parse(String Json,Class tClass)
    {
        try
        {
            T item = parse(Json,0,tClass);;
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T parse(String Json,int index,Class tClass)
    {
        try
        {
            T item = parse(Json,"",index,tClass);
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T parse(String Json, String key, int index,Class tClass)
    {
        try
        {
            String objectClassName = tClass.getSimpleName().toLowerCase();
            if (objectClassName.equals("list")||objectClassName.equals("smartlist") )
            {
                T item =(T)parseList(new ArrayList(),Json,key,index,tClass);
                return  item;
            }
            else
            {
                T item =(T)parse(tClass.newInstance(),Json,key,index);
                return  item;
            }
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T parseList(String Json,Class tClass)
    {
        try
        {
            T item = parseList(Json,0,tClass);;
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T parseList(String Json,int index,Class tClass)
    {
        try
        {
            T item = parseList(Json,"",index,tClass);
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T parseList(String Json, String key, int index,Class tClass)
    {
        try
        {
            T item =(T)parseList(new ArrayList(),Json,key,index,tClass);
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    private  static  Object parse(Object parseItem,String Json, String key, int index) {
        if (Json != null && Json.length() > 0 && !Json.equals("[]"))
        {
            Class objectClass = parseItem.getClass();
            String objectClassName= objectClass.getSimpleName().toLowerCase();
            try
            {
                if (!Json.substring(0, 1).equals("[")) { Json = "[ " + Json + " ]"; }
                JSONArray array = new JSONArray(Json);
                JSONArray subArray = key.length() > 0 ? array.getJSONObject(0).getJSONArray(key) : array;




                JSONObject object = subArray.getJSONObject(index);

                set(parseItem,object);



            }
            catch (Exception ex)
            {

            }

        }
        return  parseItem;

    }
    private  static  List parseList(List list,String Json, String key, int index,Class parseClass) {
        if (Json != null && Json.length() > 0 && !Json.equals("[]"))
        {
            try
            {
                if (!Json.substring(0, 1).equals("[")) { Json = "[ " + Json + " ]"; }
                JSONArray array = new JSONArray(Json);
                JSONArray subArray = key.length() > 0 ? array.getJSONObject(0).getJSONArray(key) : array;

                for(int i = 0 ;i<subArray.length();i++)
                {
                    JSONObject object = subArray.getJSONObject(i);
                    Object listItem = parseClass.newInstance();
                    set(listItem,object);
                    list.add(listItem);
                }

            }
            catch (Exception ex)
            {

            }

        }
        return  list;

    }
    public static void set(Object object , JSONObject jsonObject) {
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getFields();
        for (Field f:fields)
        {
            String fName = f.getName();
            String fType = f.getType().getSimpleName().toLowerCase();
            try
            {
                Object value = jsonObject.get(fName);
                switch (fType) {
                    case "int":
                        f.setInt(object, Integer.parseInt(value.toString().replace(".0","").replace(",0","")));
                        break;
                    case "long":
                        f.setLong(object, Long.parseLong(value.toString()));
                        break;
                    case "double":
                        f.setDouble(object, Double.parseDouble(value.toString()));
                        break;
                    case "boolean":
                        f.setBoolean(object, Boolean.parseBoolean(value.toString()));
                        break;
                    case "datatable":
                        f.set(object, new DataTable(value.toString()));
                        break;
                    case "list":
                        List l = new ArrayList();
                        JSONArray listJson = new JSONArray(value.toString());
                        for (int ds =0 ;ds<listJson.length();ds++)
                        {
                            Object o = Generic.getGenericInstance(f);
                            parse(o,listJson.getString(ds),"",0);
                            l.add(o);
                        }
                        f.set(object,l);
                        break;
                    case "datetime":
                        boolean Iso8601 = value.toString().indexOf("T")>0;
                        if (Iso8601)
                            f.set(object, DateTime.fromISO8601UTC(value.toString()));
                        else
                            f.set(object, DateTime.fromDateTime(value.toString()));
                        break;
                    default:
                        if(value.equals("null")) value=null;
                        if (f.getType().isPrimitive())
                        {
                            f.set(object, value);
                        }
                        else
                        {
                            set(f.getDeclaringClass().newInstance(),new JSONObject(value.toString()));
                        }
                        break;
                }
            }
            catch (Exception ex)
            {

            }
        }
    }

    public static String convert(Object item) {
        Class cls = item.getClass();
        String name = cls.getSimpleName().toLowerCase();
        String type = cls.getName().toLowerCase();

        JSONArray jsonArray  = new JSONArray();
        boolean isArray = (item instanceof List);
        if (isArray)
        {
            ArrayList list = (ArrayList)item;
            for (Object i: list)
            {
                try
                {
                    String json = convert(i);
                    if (json.substring(0,1).equals("["))
                        jsonArray.put(new JSONArray(json));
                    else
                        jsonArray.put(new JSONObject(json));

                } catch (Exception e) { }
            }
        }
        else
        {
            JSONObject object = new JSONObject();
            for (Field f:cls.getFields())
            {
                if(!f.getName().equals("$change") && !f.getName().equals("serialVersionUID"))
                    try
                    {
                        object.put(f.getName(),f.get(item));
                    } catch (Exception ex) {}
            }
            jsonArray.put(object);
        }


        try
        {
            return  isArray ? jsonArray.toString() : jsonArray.length() > 0 ? jsonArray.getJSONObject(0).toString() : "{}";
        } catch (JSONException e) {
            return "{}";
        }
    }

}
