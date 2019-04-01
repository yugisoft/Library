package library.yugisoft.module;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import library.yugisoft.module.Interfaces.ASerializable;
import library.yugisoft.module.Interfaces.ISerializable;

public class parse {

    public static int toInt(Object p) {
        try {
            return Integer.parseInt(p.toString().replace(".0", "").replace(",0", ""));
        } catch (Exception ex) {
            return defaultInt;
        }
    }

    public static long toLong(Object p) {
        try {
            return Long.parseLong(p.toString().replace(".0", ""));
        } catch (Exception ex) {
            return defaultLong;
        }
    }

    public static double toDouble(Object p) {
        try {
            return Double.parseDouble(p.toString());
        } catch (Exception ex) {
            try {
                return Double.parseDouble(p.toString().replace(",", "."));
            } catch (Exception exd) {
                return defaultDouble;
            }
        }
    }

    public static float toFloat(Object p) {
        try {
            return Float.parseFloat(p.toString());
        } catch (Exception ex) {
            try {
                return Float.parseFloat(p.toString().replace(",", "."));
            } catch (Exception exd) {
                return defaultFloat;
            }
        }
    }

    public static boolean toBoolean(Object p) {
        try {
            if (toInt(p) == 1)
                return true;
            else
                return Boolean.parseBoolean(p.toString());
        } catch (Exception ex) {
            return defaultBoolean;
        }
    }

    public static DataTable toDataTable(Object p) {
        try {
            if (p instanceof String)
                return new DataTable(p.toString());
            else
                return new DataTable(toJson(p));
        } catch (Exception ex) {
            return new DataTable();
        }
    }

    public static DateTime toDateTime(Object value) {
        try {
            boolean Iso8601 = value.toString().indexOf("T") > 0;
            if (Iso8601)
                return DateTime.fromISO8601UTC(value.toString());
            else
                return DateTime.fromDateTime(value.toString());
        } catch (Exception ex) {
            return null;
        }

    }

    public static String toJson(Object item) {
        Class cls = item.getClass();
        String name = cls.getSimpleName().toLowerCase();
        String type = cls.getName().toLowerCase();

        JSONArray jsonArray = new JSONArray();
        boolean isArray = (item instanceof List);
        if (isArray) {
            ArrayList list = (ArrayList) item;
            for (Object i : list) {
                try {
                    String json = toJson(i);
                    if (json.substring(0, 1).equals("["))
                        jsonArray.put(new JSONArray(json));
                    else
                        jsonArray.put(new JSONObject(json));

                } catch (Exception e) {
                }
            }
        } else {
            JSONObject object = new JSONObject();

            for (Field f : vList.Merge(Arrays.asList(cls.getFields()), Arrays.asList(cls.getDeclaredFields()))) {
                f.setAccessible(true);
                if (!f.getName().equals("$change") && !f.getName().equals("serialVersionUID"))
                    try {
                        Object pObject = f.get(item);
                        Class objectClass = pObject.getClass();
                        if (objectClass.equals(Integer.class) || objectClass.equals(int.class))
                            object.put(f.getName(), toInt(pObject));
                        else if (objectClass.equals(Long.class) || objectClass.equals(long.class))
                            object.put(f.getName(), toLong(pObject));
                        else if (objectClass.equals(Double.class) || objectClass.equals(double.class))
                            object.put(f.getName(), toDouble(pObject));
                        else if (objectClass.equals(DataTable.class))
                            object.put(f.getName(), ((DataTable) pObject).getJsonData());
                        else if (objectClass.equals(DateTime.class))
                            object.put(f.getName(), ((DateTime) pObject).toString());
                        else if (objectClass.equals(Boolean.class))
                            object.put(f.getName(), (pObject));
                        else if (objectClass.equals(String.class))
                            object.put(f.getName(), pObject);
                        else if (pObject instanceof List)
                            object.put(f.getName(), new JSONArray(toJson(pObject)));
                        else {

                            if (pObject instanceof ASerializable)
                                object.put(f.getName(), ((ASerializable) pObject).Serialize());
                            else if (pObject instanceof ISerializable)
                                object.put(f.getName(), ((ISerializable) pObject).Serialize());
                            else if (pObject.toString().split("\\.").length > 2 && pObject.toString().indexOf("@") != -1)
                                object.put(f.getName(), new JSONObject(toJson(pObject)));
                            else
                                object.put(f.getName(), pObject);
                        }


                    } catch (Exception ex) {
                    }
                f.setAccessible(false);
            }
            jsonArray.put(object);
        }


        try {
            return isArray ? jsonArray.toString() : jsonArray.length() > 0 ? jsonArray.getJSONObject(0).toString() : "{}";
        } catch (JSONException e) {
            return "{}";
        }
    }

    public static <T> T jsonTo(String Json, Class tClass) {
        try {
            T item = jsonTo(Json, 0, tClass);
            ;
            return item;
        } catch (Exception ex) {
            return null;
        }

    }

    public static <T> T jsonTo(String Json, int index, Class tClass) {
        try {
            T item = jsonTo(Json, "", index, tClass);
            return item;
        } catch (Exception ex) {
            return null;
        }

    }

    public static <T> T jsonTo(String Json, String key, int index, Class tClass) {
        try {
            String objectClassName = tClass.getSimpleName().toLowerCase();
            if (objectClassName.equals("list") || objectClassName.equals("smartlist")) {
                T item = (T) jsonToList(new ArrayList(), Json, key, index, tClass);
                return item;
            } else {
                T item = (T) jsonTo(tClass.newInstance(), Json, key, index);
                return item;
            }
        } catch (Exception ex) {
            return null;
        }

    }

    public static <T> T jsonToList(String Json, Class tClass) {
        try {
            T item = jsonToList(Json, 0, tClass);
            ;
            return item;
        } catch (Exception ex) {
            return null;
        }

    }

    public static <T> T jsonToList(String Json, int index, Class tClass) {
        try {
            T item = jsonToList(Json, "", index, tClass);
            return item;
        } catch (Exception ex) {
            return null;
        }

    }

    public static <T> T jsonToList(String Json, String key, int index, Class tClass) {
        try {
            T item = (T) jsonToList(new ArrayList(), Json, key, index, tClass);
            return item;
        } catch (Exception ex) {
            return null;
        }

    }


    public static Object jsonTo(Object parseItem, String Json, String key, int index) {
        if (Json != null && Json.length() > 0 && !Json.equals("[]")) {
            Class objectClass = parseItem.getClass();

            if (objectClass.equals(Integer.class) || objectClass.equals(int.class))
                parseItem = toInt(Json);
            else if (objectClass.equals(Long.class) || objectClass.equals(long.class))
                parseItem = toLong(Json);
            else if (objectClass.equals(Double.class) || objectClass.equals(double.class))
                parseItem = toDouble(Json);
            else if (objectClass.equals(DataTable.class))
                parseItem = toDataTable(Json);
            else if (objectClass.equals(DateTime.class))
                parseItem = toDateTime(Json);
            else if (objectClass.equals(Boolean.class))
                parseItem = toBoolean(Json);
            else if (objectClass.equals(String.class))
                parseItem = Json;
            else {
                String objectClassName = objectClass.getSimpleName().toLowerCase();
                try {
                    if (!Json.substring(0, 1).equals("[")) {
                        Json = "[ " + Json + " ]";
                    }
                    JSONArray array = new JSONArray(Json);
                    JSONArray subArray = key.length() > 0 ? array.getJSONObject(0).getJSONArray(key) : array;


                    JSONObject object = subArray.getJSONObject(index);

                    set(parseItem, object);


                } catch (Exception ex) {

                }
            }
        }
        return parseItem;

    }

    public static List jsonToList(List list, String Json, String key, int index, Class parseClass) {
        if (Json != null && Json.length() > 0 && !Json.equals("[]")) {
            try {
                if (!Json.substring(0, 1).equals("[")) {
                    Json = "[ " + Json + " ]";
                }
                JSONArray array = new JSONArray(Json);
                JSONArray subArray = key.length() > 0 ? array.getJSONObject(0).getJSONArray(key) : array;

                for (int i = 0; i < subArray.length(); i++) {
                    JSONObject object = subArray.getJSONObject(i);
                    Object listItem = parseClass.newInstance();
                    set(listItem, object);
                    list.add(listItem);
                }

            } catch (Exception ex) {

            }

        }
        return list;

    }

    private static void set(Object object, JSONObject jsonObject) {
        Class objectClass = object.getClass();
        setSub(object, jsonObject, objectClass.getDeclaredFields());
        setSub(object, jsonObject, objectClass.getFields());
        yugi.Print("i", "JSON END SET");
    }

    private static void setSub(Object object, JSONObject jsonObject, Field[] fields) {
        for (Field f : fields) {
            String fName = f.getName();
            String fType = f.getType().getSimpleName().toLowerCase();
            if (fName.equals("$change") || fName.equals("serialVersionUID")) {
                continue;
            }
            f.setAccessible(true);

            try {
                Object value = jsonObject.get(fName);
                Class<?> clazz = f.getType();

                if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                    f.setInt(object, toInt(value));
                } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
                    f.setLong(object, toLong(value));
                } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
                    f.setDouble(object, toDouble(value));
                } else if (clazz.equals(DataTable.class)) {
                    f.set(object, toDataTable(value));
                } else if (clazz.equals(List.class) || fType.equals("list")) {
                    List l = new ArrayList();
                    JSONArray listJson = new JSONArray(value.toString());
                    for (int ds = 0; ds < listJson.length(); ds++) {

                        Object o = jsonTo(Generic.getGenericInstance(f), listJson.getString(ds), "", 0);
                        l.add(o);
                    }
                    f.set(object, l);
                } else if (clazz.equals(DateTime.class) || fType.equals("datetime")) {
                    f.set(object, toDateTime(value));
                } else if (clazz.equals(Boolean.class) || fType.equals("boolean"))
                    f.setBoolean(object, toBoolean(value));
                else if (clazz.equals(String.class) || fType.equals("string")) {
                    if (value.equals("null")) value = null;
                    f.set(object, value);
                } else {
                    if (value.equals("null")) value = null;
                    if (f.getType().isPrimitive()) {
                        f.set(object, value);
                    } else {
                        Object ft = f.getType().newInstance();
                        set(ft, new JSONObject(value.toString()));
                        f.set(object, ft);
                    }
                }
            } catch (Exception ex) {
                yugi.Print("e", "JSON PARSE SET EXCEOTION", ex.getMessage());
            }
            f.setAccessible(false);
        }
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

    private static float defaultFloat = 0f;

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


    public static float getDefaultFloat() {
        return defaultFloat;
    }

    public static void toLayoutParams(View view, int Wi, int He, int We) {
        Object params = view.getLayoutParams();
        if (params.getClass().equals(LinearLayout.LayoutParams.class))
            view.setLayoutParams(new LinearLayout.LayoutParams(Wi, He, We));
        else if (params.getClass().equals(RelativeLayout.LayoutParams.class))
            view.setLayoutParams(new RelativeLayout.LayoutParams(Wi, He));
        else if (params.getClass().equals(ViewGroup.LayoutParams.class))
            view.setLayoutParams(new ViewGroup.LayoutParams(Wi, He));
        else if (params.getClass().equals(AbsListView.LayoutParams.class))
            view.setLayoutParams(new AbsListView.LayoutParams(Wi, He));
        else if (params.getClass().equals(FrameLayout.LayoutParams.class))
            view.setLayoutParams(new FrameLayout.LayoutParams(Wi, He));
    }

    public static class Formatter {
        public static String get(String format, Object args) {
            format = args instanceof DataTable.DataRow ? purifyDR(format, (DataTable.DataRow) args) : purify(format, args);
            return String.format(format, args);
        }

        public static String purify(String format, Object object) {


            try {
                /*Modeldeki alanları bulma*/
                //region Field
                while (format.contains("${")) {
                    try {
                        String f, fieldName, fieldFormat;
                        f = format.replace("${", "~").split("~")[1].split("\\}")[0];
                        String[] fieldArea = f.split(":");
                        fieldName = fieldArea[0];
                        fieldFormat = fieldArea.length > 1 ? fieldArea[1] : "";
                        try {
                            Field ff = null;
                            try {
                                ff = object.getClass().getField(fieldName);
                            } catch (Exception e) {
                            }
                            if (ff == null)
                                ff = object.getClass().getDeclaredField(fieldName);
                            if (ff != null) {
                                ff.setAccessible(true);
                                Object value = ff.get(object);
                                if (fieldFormat.length() > 0) {
                                    if (fieldFormat.substring(0, 1).equals("n")) {
                                        int len = toInt(fieldFormat.substring(1, fieldFormat.length()));
                                        format = format.replace("${" + f + "}", yugi.NF(value, len));
                                    }
                                } else {
                                    format = format.replace("${" + f + "}", value.toString());
                                }
                                ff.setAccessible(false);
                            } else {
                                format = format.replace("${" + f + "}", "");
                            }

                        } catch (Exception ex) {
                            format = format.replace("${" + f + "}", "");
                        }
                    } catch (Exception e) {
                        break;
                    }
                }
                //endregion

                /*String Resource Okuma*/
                format = purifyS(format);
            } catch (Exception ex) {

            }


            return format;
        }

        public static String purifyDR(String format, DataTable.DataRow row) {


            try {
                /*Modeldeki alanları bulma*/
                //region Field
                if (format.length() > 0) {
                    while (format.contains("${")) {
                        try {
                            String f, fieldName, fieldFormat;
                            f = format.replace("${", "~").split("~")[1].split("\\}")[0];
                            String[] fieldArea = f.split(":");
                            fieldName = fieldArea[0];
                            fieldFormat = fieldArea.length > 1 ? fieldArea[1] : "";
                            String fS = "${" + f + "}";
                            String value = row.get(fieldName);

                            if (value.length() == 0) {
                                format = format.replace("${" + fieldName + "}", "");
                            } else if (fieldFormat.length() > 0) {

                                if (fieldFormat.substring(0, 1).equals("n")) {
                                    int len = toInt(fieldFormat.substring(1, fieldFormat.length()));

                                    format = format.replace(fS, yugi.NF(value, len));
                                } else {
                                    switch (fieldFormat) {

                                        case "DS":
                                            format = format.replace(fS, DateTime.fromISO8601UTC(value).toShortDateString());
                                            break;
                                        case "DL":
                                            format = format.replace(fS, DateTime.fromISO8601UTC(value).toLongDateString());
                                            break;
                                        case "TS":
                                            format = format.replace(fS, DateTime.fromISO8601UTC(value).toShortTimeString());
                                            break;
                                        case "TL":
                                            format = format.replace(fS, DateTime.fromISO8601UTC(value).toLongTimeString());
                                            break;
                                        case "DTS":
                                            format = format.replace(fS, DateTime.fromISO8601UTC(value).toShortDateTimeString());
                                            break;
                                        case "DTL":
                                            format = format.replace(fS, DateTime.fromISO8601UTC(value).toLongDateTimeString());
                                            break;
                                    }
                                }

                            } else {
                                format = format.replace(fS, value);
                            }

                        } catch (Exception e) {
                            break;
                        }
                    }
                } else {

                }
                //endregion

                /*String Resource Okuma*/
                format = purifyS(format);
            } catch (Exception ex) {

            }


            return format;
        }

        public static String purifyS(String format) {
            /*String Resource Okuma*/
            while (format.contains("$S{")) {
                try {
                    String f = format.replace("$S{", "~").split("~")[1].split("\\}")[0];
                    try {
                        int id = yugi.activity.getResources().getIdentifier(f, "string", yugi.activity.getPackageName());
                        format = format.replace("$S{" + f + "}", yugi.activity.getResources().getString(id));
                    } catch (Exception ex) {
                        format = format.replace("$S{" + f + "}", "");
                    }
                } catch (Exception ex2) {
                    break;
                }
            }

            return format;
        }
    }

}

