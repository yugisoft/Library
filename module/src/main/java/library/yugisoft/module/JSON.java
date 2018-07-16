package library.yugisoft.module;

import com.google.gson.Gson;

public class JSON<T>
{
    public static String ToString(Object object) {
        Gson gson = new Gson();
        return  gson.toJson(object);
    }





    public <E> T JsonToClass(Class<E> t,String Json)
    {

        try
        {
            Object o = null;
            Gson gson = new Gson();
            o = gson.fromJson(Json, t);
            return (T)o;
        }
        catch (Exception ex)
        {
            return  null;
        }
    }

}
