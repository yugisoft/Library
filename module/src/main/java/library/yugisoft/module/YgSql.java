package library.yugisoft.module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class YgSql
{
    public abstract Context context();

    public static String DATABASE_NAME = "GPOS";
    private  static final int DATABASE_VERSION = 1;
    public static class DBColumn {

        private String ColumnName="",DataType="",Lenght="",DefaultValue="";

        public DBColumn(){}

        public DBColumn(String Params) {
            String[] param=Params.split(" ");
            for (int i  =0;i<param.length;i++)
                switch (i) {
                    case 0:
                        setColumnName(param[i]);
                        break;
                    case 1:
                        setDataType(param[i]);
                        break;
                    case 2:
                        setLenght(param[i]);
                        break;
                    case 3:
                        setDefaultValue(param[i]);
                        break;
                }
        }

        public DBColumn(String columnName, String dataType, String lenght, String defaultValue) {
            ColumnName = columnName;
            DataType = dataType="varchar";
            Lenght = lenght="50";
            DefaultValue = defaultValue;
        }

        public String getColumnName() {
            return ColumnName;
        }

        public void setColumnName(String columnName) {
            ColumnName = columnName;
        }

        public String getDataType() {
            return DataType;
        }

        public void setDataType(String dataType) {
            DataType = dataType;
        }

        public String getLenght() {
            return Lenght;
        }

        public void setLenght(String lenght) {
            Lenght = lenght;
        }

        public String getDefaultValue() {
            return DefaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            DefaultValue = defaultValue;
        }
    }
    private static class DBTABLE<T> extends SQLiteOpenHelper implements IGeneric {

        DataTable dt = new DataTable();
        public String TABLENAME;
        public List<DBColumn> Columns = new ArrayList<>();

        public DBTABLE(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            TABLENAME = getGenericClass().getName();
        }


        public void CREATE() {
            try {
                String SQL = sqlCreate();
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(SQL);
                db.close();
            } catch (Exception ex) {
                yugi.Print("e", " | DBTABLE | CREATE | " + TABLENAME, ex.getMessage());
            }
            try {
                dt = new DataTable();
                for (DBColumn col : Columns) {
                    dt.Columns.add(col.ColumnName);
                }
            } catch (Exception ex) {

            }


        }


        public DataTable Select() {
            return mSelect("SELECT * FROM " + TABLENAME);
        }


        public DataTable Select(String WHERE) {
            return mSelect("SELECT * FROM " + TABLENAME + " WHERE " + WHERE);
        }

        public DataTable mSelect(String SQL) {
            dt.Rows.clear();
            dt.mRows.clear();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(SQL, null);
            if (cursor!=null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    String[] row = new String[cursor.getColumnCount()];
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        row[i] = cursor.getString(i);
                    }
                    dt.add(row);
                } while (cursor.moveToNext());
            }
            return dt;
        }
        public DataTable pSelect(String SQL) {

            String[] col = new String[1];
            DataTable dataTable = new DataTable();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(SQL, null);
            int rowindex =0 ;

            if (cursor!=null && cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    String[] row = new String[cursor.getColumnCount()];
                    col = new String[cursor.getColumnCount()];

                    for (int i = 0; i < cursor.getColumnCount(); i++) {

                        if (rowindex == 0) {
                            col[i] = cursor.getColumnName(i);
                        }
                        row[i] = cursor.getString(i);
                    }
                    if (rowindex == 0)
                    {
                        dataTable = new DataTable(true,col);
                        rowindex++;
                    }
                    dt.add(row);
                } while (cursor.moveToNext());
            }
            return dt;
        }
        public int SelectCount() {
            return pSelect("SELECT Count(*) FROM " + TABLENAME).getInt(0,0);
        }

        public void Insert(Object o)
        {

            //region Fields

            Field[] f = o.getClass().getFields();
            String name="";
            String SQL = sqlInsert();

            for (Field fi : f)
            {
                try
                {
                    fi.setAccessible(true);
                    String simlename = fi.getType().getSimpleName().toLowerCase();

                    name = fi.getName();
                    if(name.equals("$change") || name.equals("serialVersionUID"))
                    {
                        continue;
                    }
                    switch (simlename) {
                        case "int":
                            SQL=SQL.replace("{"+name+"}",fi.get(o).toString());
                            break;
                        case "long":
                            SQL=SQL.replace("{"+name+"}",fi.get(o).toString());
                            break;
                        case "double":
                            SQL=SQL.replace("{"+name+"}",fi.get(o).toString());
                            break;
                        case "boolean":
                            SQL=SQL.replace("{"+name+"}",fi.getBoolean(o)?"1":"0");
                            break;
                        case "string":
                            SQL=SQL.replace("{"+name+"}","'"+fi.get(o).toString()+"'");
                            break;
                        default:
                            SQL=SQL.replace("{"+name+"}","'"+JSON.DataTable(fi.get(o)).getJsonData()+"'");
                            break;

                    }
                } catch (Exception e)
                {

                }

            }
            yugi.Print("i"," | DBTABLE | Insert | insertSQL ",SQL);

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(SQL);
            db.close();
            //endregion
        }

        public String sqlCreate() {
            String SQL = "CREATE TABLE " + TABLENAME + " ( ";
            int i = 0;
            for (DBColumn col : Columns) {
                if (i > 0) SQL += ",";
                SQL += col.ColumnName + " " + col.DataType;
                if (col.Lenght.length() > 0) SQL += "(" + col.Lenght + ")";
                i++;
            }
            SQL += ")";
            return SQL;
        }

        public String sqlInsert() {
            String SQL = "INSERT INTO " + TABLENAME + " ( ";
            String SQL2 = " VALUES (";
            int i = 0;
            for (DBColumn col : Columns) {
                if (i > 0) {
                    SQL += ",";
                    SQL2 += ",";
                }

                SQL += col.ColumnName;
                SQL2 += "{" + col.ColumnName + "}";
                i++;
            }
            SQL += ")";
            SQL2 += ")";
            return SQL + SQL2;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    public static class TABLE<T> extends DBTABLE {


        public TABLE(Context context) {
            super(context);
            CreateForClass();
        }
        public TABLE(YgSql context) {
            super(context.context());
            CreateForClass();
        }
        private void CreateForClass() {
            try
            {
                Object o = getGenecericInstance();
                //region Fields

                Field[] f = o.getClass().getFields();
                String name="";

                for (Field fi : f)
                {
                    DBColumn col = new DBColumn();

                    try
                    {
                        fi.setAccessible(true);
                        String simlename = fi.getType().getSimpleName().toLowerCase();
                        name = fi.getName();

                        if(name.equals("$change") || name.equals("serialVersionUID"))
                        {
                            continue;
                        }
                        col.ColumnName=name;
                        switch (simlename) {
                            case "int":
                                col.DataType = "INT";
                                break;
                            case "long":
                                col.DataType = "BIGINT";
                                break;
                            case "double":
                                col.DataType = "FLOAT";
                                break;
                            case "boolean":
                                col.DataType = "BIT";
                                break;
                            case "string":
                                col.DataType = "TEXT";

                                break;
                        }
                        this.Columns.add(col);

                    } catch (Exception e)
                    {

                    }
                }
                //endregion
                CREATE();
            }
            catch (Exception e)
            {
                yugi.Print("e"," | TABLE | CreateForClass : Error",e.getMessage());
            }
        }

        public List<T> getList()
        {
            DataTable dt = super.Select();
            List<T> list = new ArrayList();
            dt.ToClass(list);
            return  list;
        }
        public List<T> getList(String WHERE) {
            DataTable dt = super.Select(WHERE);
            List<T> list = new ArrayList();
            dt.ToClass(list);
            return  list;
        }

        
    }
}
