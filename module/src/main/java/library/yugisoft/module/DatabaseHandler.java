package library.yugisoft.module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 28.02.2018.
 */

public class DatabaseHandler
{
    public static String DATABASE_NAME = "GPOS";
    private static final int DATABASE_VERSION = 1;
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
    public static class DBTABLE extends SQLiteOpenHelper {
        DataTable dt = new DataTable();
        public String TABLENAME;
        public List<DBColumn> Columns = new ArrayList<>();

        public DBTABLE(Context context, String tablename) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            TABLENAME = tablename;
        }

        public DBTABLE(Context context, String tablename, String... columns) {
            this(context, tablename);
            for (String s : columns) {
                Columns.add(new DBColumn(s));
            }
            CREATE();
        }

        public void CREATE() {
            try {
                String SQL = sqlCreate();
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL(SQL);
                db.close();
            } catch (Exception ex) {
                yugi.Print("e", "DatabaseHandler | DBTABLE | CREATE | " + TABLENAME, ex.getMessage());
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

        private DataTable mSelect(String SQL) {
            dt.Rows.clear();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(SQL, null);
            if (cursor.moveToFirst()) {
                do {
                    String[] row = new String[Columns.size()];
                    for (int i = 0; i < Columns.size(); i++) {
                        row[i] = cursor.getString(i);
                    }
                    dt.add(row);
                } while (cursor.moveToNext());
            }
            return dt;
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
                            SQL=      SQL.replace("{"+name+"}","'"+fi.get(o).toString()+"'");
                            break;
                    }
                } catch (Exception e)
                {

                }

            }
            yugi.Print("i","DatabaseHandler | DBTABLE | Insert | insertSQL ",SQL);

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
        Class<?> mClass=null;
        public TABLE(Context context,Class<?> mclassl, String tablename) {
            super(context, tablename);
            mClass = mclassl;
            CreateForClass();
        }
        public TABLE(Context context,Class<?> mclassl, String tablename, String... columns) {
            super(context,tablename,columns);
            mClass = mclassl;
        }
        public List<T> getList() {
            DataTable dt = super.Select();
            List<T> list = new SmartList(mClass);
            dt.ToClass(list);
            return  list;
        }
        public List<T> getList(String WHERE) {
            DataTable dt = super.Select(WHERE);
            List<T> list = new SmartList(mClass);
            dt.ToClass(list);
            return  list;
        }
        public void CreateForClass() {
            try
            {
                Object o = Class.forName(mClass.getName()).newInstance();
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
                    yugi.Print("e","DatabaseHandler | TABLE | CreateForClass : Error",e.getMessage());
            }
        }
    }
}
