package library.yugisoft.module;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

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
}
