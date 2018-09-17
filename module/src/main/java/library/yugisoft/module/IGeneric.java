package library.yugisoft.module;

import java.lang.reflect.ParameterizedType;

public interface IGeneric<T>
{

    default Class<T> getGenericClass()
    {
        Class<T> genericClass=null;
        try
        {
            genericClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        catch (Exception ex)
        {
            yugi.Print("e","genericClass Can Not be Created! : in Constructer");
        }
        return genericClass;
    }

    default T getGenecericInstance()
    {
        try
        {
            return  getGenericClass().newInstance();
        }
        catch (Exception ex)
        {
            yugi.Print("e","genericClass Can Not be Created! : "+ex.getMessage());
            return  null;
        }
    }




}
