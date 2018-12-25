package library.yugisoft.module;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class parse
{

    public static int toInt(Object p) {
        try
        {
            return Integer.parseInt(p.toString().replace(".0","").replace(",0",""));
        }
        catch (Exception ex)
        {
            return  defaultInt;
        }
    }
    public static long toLong(Object p) {
        try
        {
            return Long.parseLong(p.toString().replace(".0",""));
        }
        catch (Exception ex)
        {
            return  defaultLong;
        }
    }
    public static double toDouble(Object p) {
        try
        {
            return Double.parseDouble(p.toString());
        }
        catch (Exception ex)
        {
            return  defaultDouble;
        }
    }
    public static boolean toBoolean(Object p) {
        try
        {
            return Boolean.parseBoolean(p.toString());
        }
        catch (Exception ex)
        {
            return  defaultBoolean;
        }
    }
    public static DataTable toDataTable(Object p) {
        try
        {
            return new DataTable(p.toString());
        }
        catch (Exception ex)
        {
            return  new DataTable();
        }
    }
    public static DateTime toDateTime(Object value) {
        try
        {
            boolean Iso8601 = value.toString().indexOf("T")>0;
            if (Iso8601)
                return DateTime.fromISO8601UTC(value.toString());
            else
                return DateTime.fromDateTime(value.toString());
        }
        catch (Exception ex)
        {
            return null;
        }

    }

    public static String toJson(Object item) {
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
                    String json = toJson(i);
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
    public static  <T> T jsonTo(String Json,Class tClass)
    {
        try
        {
            T item = jsonTo(Json,0,tClass);;
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T jsonTo(String Json,int index,Class tClass)
    {
        try
        {
            T item = jsonTo(Json,"",index,tClass);
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T jsonTo(String Json, String key, int index,Class tClass)
    {
        try
        {
            String objectClassName = tClass.getSimpleName().toLowerCase();
            if (objectClassName.equals("list")||objectClassName.equals("smartlist") )
            {
                T item =(T)jsonToList(new ArrayList(),Json,key,index,tClass);
                return  item;
            }
            else
            {
                T item =(T)jsonTo(tClass.newInstance(),Json,key,index);
                return  item;
            }
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T jsonToList(String Json,Class tClass)
    {
        try
        {
            T item = jsonToList(Json,0,tClass);;
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T jsonToList(String Json,int index,Class tClass)
    {
        try
        {
            T item = jsonToList(Json,"",index,tClass);
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }
    public static  <T> T jsonToList(String Json, String key, int index,Class tClass)
    {
        try
        {
            T item =(T)jsonToList(new ArrayList(),Json,key,index,tClass);
            return  item;
        }
        catch (Exception ex)
        {
            return  null;
        }

    }


    private  static  Object jsonTo(Object parseItem,String Json, String key, int index) {
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
    private  static  List jsonToList(List list,String Json, String key, int index,Class parseClass) {
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
    private static void set(Object object , JSONObject jsonObject) {
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        for (Field f:fields)
        {
            f.setAccessible(true);
            String fName = f.getName();
            String fType = f.getType().getSimpleName().toLowerCase();
            try
            {
                Object value = jsonObject.get(fName);
                Class<?> clazz = f.getType();

                if (clazz.equals(Integer.class) || clazz.equals(int.class))
                {
                    f.setInt(object,toInt(value));
                }
                else if (clazz.equals(Long.class) || clazz.equals(long.class))
                {
                    f.setLong(object,toLong(value));
                }
                else if (clazz.equals(Double.class) || clazz.equals(double.class))
                {
                    f.setDouble(object, toDouble(value));
                }
                else if (clazz.equals(DataTable.class))
                {
                    f.set(object, toDataTable(value));
                }
                else if (clazz.equals(List.class) || fType.equals("list"))
                {
                    List l = new ArrayList();
                    JSONArray listJson = new JSONArray(value.toString());
                    for (int ds =0 ;ds<listJson.length();ds++)
                    {
                        Object o = Generic.getGenericInstance(f);
                        jsonTo(o,listJson.getString(ds),"",0);
                        l.add(o);
                    }
                    f.set(object,l);
                }
                else if (clazz.equals(DateTime.class) || fType.equals("datetime"))
                {
                    f.set(object,toDateTime(value));
                }
                else if (clazz.equals(Boolean.class) || fType.equals("boolean"))
                    f.setBoolean(object,toBoolean(value));
                else if (clazz.equals(String.class) || fType.equals("string"))
                {
                    if(value.equals("null")) value=null;
                    f.set(object, value);
                }
                else
                {
                    if(value.equals("null")) value=null;
                    if (f.getType().isPrimitive())
                    {
                        f.set(object, value);
                    }
                    else
                    {
                        Object ft = f.getType().newInstance();
                        set(ft,new JSONObject(value.toString()));
                        f.set(object,ft);
                    }
                }
            }
            catch (Exception ex)
            {
                yugi.Print("e","JSON PARSE SET EXCEOTION",ex.getMessage());
            }
            f.setAccessible(false);
        }
        yugi.Print("i","JSON END SET");
    }

    private static int defaultInt = 0;
    public static int getDefaultInt() {
        return defaultInt;
    }
    public static void setDefaultInt(int defaultInt) {
        parse.defaultInt = defaultInt;
    }

    private static double defaultDouble = 0;
    public static double getDefaultDouble() {
        return defaultDouble;
    }
    public static void setDefaultDouble(double defaultDouble) {
        parse.defaultDouble = defaultDouble;
    }

    private static boolean defaultBoolean = false;
    public static boolean getDefaultBoolean() {
        return defaultBoolean;
    }
    public static void setDefaultBoolean(boolean defaultBoolean) {
        parse.defaultBoolean = defaultBoolean;
    }

    private static long defaultLong = 0;
    public static long getDefaultLong() {
        return defaultLong;
    }
    public static void setDefaultLong(long defaultLong) {
        parse.defaultLong = defaultLong;
    }


}