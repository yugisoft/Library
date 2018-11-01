package library.yugisoft.module;

import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.stream.Collectors;

/**
 * Created by Yusuf on 29.01.2018.
 */
public class SmartList<T> extends ArrayList
{
    public Class ParentClas;
    public SmartList(Class parentClas)
    {
        ParentClas=parentClas;
    }
    public SmartList() {}





    public static<E> List<E> Copy(List<E> list) {
        List<E> tList = new SmartList<E>();
        for (E item : list) {
            tList.add(item);
        }
        return tList;
    }
    public static<E> SmartList<E> CopyList(List<E> list) {
        SmartList<E> tList = new SmartList<E>();
        for (E item : list) {
            tList.add(item);
        }
        return tList;
    }



    public static List Marge(List list1,List list2)
    {
       return Marge(list1,list2,false);
    }
    public static List Marge(List list1,List list2,boolean varolanlarıekleme) {
        for (Object item:list2) {
            if (varolanlarıekleme)
            {
                if (!list1.contains(item))
                    list1.add(item);
            }
            else
                list1.add(item);
        }
        return  list1;
    }



    @Deprecated
    public List<T> filter(String ColumnName,Object value) {
        List l = new DataTable.FilterList().filterList(this, new DataTable.Filter() {
            @Override
            public boolean isMatched(Object object, Object text) {
                try
                {
                    return object.getClass().getField(ColumnName).get(object).toString().contains(text.toString());
                }
                catch (Exception ex)
                {

                }

                return false;
            }
        },value);
        return  l;
    }
    @Deprecated
    public List<T> Where(String pWhere) {
        List<T> tList = SmartList.Copy(this);

        for (String item: pWhere.split("&&"))
        {
            String col = item.split("==")[0];
            String value = item.split("==")[1];
            tList = ((SmartList<T>)tList).filter(col,value);
        }


        return  null;
    }


    //region Stream
    public interface Pre<T,R> {
        R get(T item);
    }


    public static  <T> List<T> Filter(List<T> list, Pre<T,Boolean> pre) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return list.stream().filter(p -> pre.get(p)).collect(Collectors.toList());
        }
        else
        {
            List<T> col = new ArrayList<T>();
            for (int i = 0 ; i < list.size() ; i++)
                if (pre.get(list.get(i)))
                    col.add(list.get(i));
            return col;
        }
    }

    public static  <T> int Sum(List<T> list, Pre<T,Integer> pre) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return list.stream().mapToInt(p->pre.get(p)).sum();
        }
        else
        {
            int total = 0;
            for (int i = 0 ; i < list.size() ; i++)
                total += pre.get(list.get(i));
            return total;
        }
    }

    public static  <T> double SumDouble(List<T> list, Pre<T,Double> pre) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return list.stream().mapToDouble(p->pre.get(p)).sum();
        }
        else
        {
            double total = 0;
            for (int i = 0 ; i < list.size() ; i++)
                total += pre.get(list.get(i));
            return total;
        }
    }

    //endregion


}
