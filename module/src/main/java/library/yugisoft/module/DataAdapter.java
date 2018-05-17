package library.yugisoft.module;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Yusuf on 09.05.2018.
 */

public class DataAdapter<T>
{
    Class<T> myType; public DataAdapter() {this.myType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];}



}
