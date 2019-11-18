package library.yugisoft.module;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

public class Generic<T> implements IGeneric<T>
{
    public static Object getGenericInstance(Field field) {
        try
        {
            Class<?> cl = (Class<?>)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
            return  cl.newInstance();
        }
        catch (Exception ex)
        {
            return  null;
        }
    }
    public static Class<?> getGenericClass(Field field) {
        try
        {
            Class<?> cl = (Class<?>)((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
            return cl;
        }
        catch (Exception ex)
        {
            return  null;
        }
    }

    List<T> asda;

    public Class<T> genericClass=null;


    @Override
    public Class<T> getGenericClass() {

        try
        {
            Type parametrizedType = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (parametrizedType instanceof Class) {

            } else if (parametrizedType instanceof WildcardType) {

            } else if (parametrizedType instanceof TypeVariable)
            {
                TypeVariable variable = (TypeVariable)parametrizedType;
                Type boundType = ((TypeVariable<?>) variable).getBounds()[0];
                return (Class<T>) boundType;

            } else {

            }
           this.getClass().getTypeParameters();
            genericClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        catch (Exception ex)
        {
            yugi.Print("e","genericClass Can Not be Created! : in Constructer");
        }
        return genericClass;
    }

    @Override
    public T getGenecericInstance() {
        try
        {
            Method method = this.getClass().getMethod("getGenecericInstance");
            method.getGenericParameterTypes();
            return  getGenericClass().newInstance();
        }
        catch (Exception ex)
        {
            yugi.Print("e","genericClass Can Not be Created! : "+ex.getMessage());
            return  null;
        }
    }


    public static Object getGenericValue(Object object,Field f,Object value) {

        try
        {

            Class<?> clazz = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
            String fType = f.getType().getSimpleName().toLowerCase();
            if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
                value=(parse.toInt(value));
            } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
                value=( parse.toLong(value));
            } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
                value=(parse.toDouble(value));
            } else if (clazz.equals(DataTable.class)) {
                value=(parse.toDataTable(value));
            }
            else if (clazz.equals(DateTime.class) || fType.equals("datetime")) {
                value=(parse. toDateTime(value));
            } else if (clazz.equals(Boolean.class) || fType.equals("boolean"))
                value=(parse.toBoolean(value));
            else if (clazz.equals(List.class) || fType.equals("list"))
            {
                List l = new ArrayList();
                JSONArray listJson = new JSONArray(value.toString());
                for (int ds = 0; ds < listJson.length(); ds++) {
                    Object o = null;
                    Object GItem = Generic.getGenericInstance(f);
                    if(GItem != null)
                        parse.jsonTo(Generic.getGenericInstance(f), listJson.getString(ds), "", 0);
                    else
                        o = Generic.getGenericValue(object,f,listJson.getString(ds));
                    if (o!= null)
                        l.add(o);
                }
                value=(l);
            }
            else if (clazz.equals(vList.class) || fType.equals("vlist"))
            {
                vList l = new vList();
                JSONArray listJson = new JSONArray(value.toString());
                for (int ds = 0; ds < listJson.length(); ds++) {

                    Object o = null;

                    Object GItem = Generic.getGenericInstance(f);
                    if(GItem != null)
                        o = parse.jsonTo(Generic.getGenericInstance(f), listJson.getString(ds), "", 0);
                    else
                        o = Generic.getGenericValue(object,f,listJson.getString(ds));
                    if (o!= null)
                        l.add(o);
                }
                value=( l);
            }

            return value;
        } catch (Exception ex) {
            return null;
        }

    }
}
