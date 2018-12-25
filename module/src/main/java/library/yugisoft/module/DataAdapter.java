package library.yugisoft.module;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static library.yugisoft.module.yugi.getAnnotatedDeclaredFields;

/**
 * Created by Yusuf on 09.05.2018.
 */

public abstract class DataAdapter<T,E> extends BaseAdapter
{

    private List<T> data = new ArrayList<T>();

    @Override
    public int getCount() { return data.size(); }
    @Override
    public Object getItem(int position) { return getData().get(position); }
    @Override
    public long getItemId(int position) { return 0; }
    public List<T> getData() { return data; }
    public void setData(List<T> data) { this.data = data; }
    //region @INTERFACE
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataAdapterID {}
    //endregion
    public E getID(int position) {
        Object item = getItem(position);
        try
        {
            Field[] asd = getAnnotatedDeclaredFields(item.getClass(), DataAdapterID.class, true);
            if (asd.length>0)
                return (E)asd[0].get(item);

        }
        catch (Exception ex)
        {

        }
        return  null;

    }
    public T get(int position) {
        try
        {
            return (T) getItem(position);
        }
        catch (Exception ex)
        {
            return  null;
        }
    }
    public  View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = convertView==null ? CreateView() : convertView;
        RequstView(convertView,get(position),position);
        return convertView;
    }
    public  View CreateView() { return yugi.activity.getLayoutInflater().inflate(getContentViewID(),null); }
    public abstract int getContentViewID();
    public abstract void RequstView(View view,T item,int position);

}
