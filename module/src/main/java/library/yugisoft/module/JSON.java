package library.yugisoft.module;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.List;

public class JSON<T> {
    public JSON()
    {
    }

    public static String ToString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public <E> T JsonToClass(Class<E> t, String Json) {

        try {
            Object o = null;
            Gson gson = new Gson();
            o = gson.fromJson(Json, t);
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


    private static String[] buildRow(Object ob,DataTable dt,Class c)
    {
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
        return  s;
    }

}
