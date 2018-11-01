package library.yugisoft.module;

import android.os.Build;
import android.support.annotation.NonNull;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class vList<E>  implements List<E>{


    public vList() {
        list = new ArrayList<>();
    }

    //region LIST
    public List<E> list;
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends E> c) {
        return list.addAll(index,c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @NonNull
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
    //endregion

    //region Stream

    //region Local
    public interface Pre<T, R> {
        R get(T item);
    }

    public vList<E> Filter(Pre<E,Boolean> filter) {
        return Filter(this.list, filter);
    }


    //region INT
    public int Sum(Pre<E,Integer> pre) {

        return Sum(list,pre);
    }
    public vListItem<E,Integer> Max(SmartList.Pre<E,Integer> pre) {


        return Max(list,pre);
    }
    public vListItem<E,Integer> Min(SmartList.Pre<E,Integer> pre) {

        return Min(list,pre);
    }
    //endregion

    //region Double
    public  double SumDouble(Pre<E,Double> pre) {

        return  SumDouble(list,pre);
    }

    public vListItem<E,Double> MaxDouble(SmartList.Pre<E,Double> pre) {

        return  MaxDouble(list,pre);

    }
    public  vListItem<E,Double> MinDouble(SmartList.Pre<E,Double> pre) {

        return  MinDouble(list,pre);
    }



    //endregion

    //endregion

    //region static

    public static <T> vList<T> Filter(List<T> list, Object filter) {
        vList<T> vlist = new vList<T>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vlist.list = list.stream().filter(p -> p.equals(filter)).collect(Collectors.toList());
        } else {

            for (int i = 0; i < list.size(); i++)
                if (list.get(i).equals(filter))
                    vlist.list.add(list.get(i));
        }
        return vlist;
    }


    //region INT
    public static int Sum(List<Integer> list) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().mapToInt(p -> p).sum();
        } else {
            int total = 0;
            for (int i = 0; i < list.size(); i++)
                total += list.get(i);
            return total;
        }
    }

    public static vListItem<Integer,Integer> Max(List<Integer> list) {

        vListItem<Integer,Integer> item = new vListItem<Integer,Integer>();

        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            item.item = list.stream().mapToInt(p -> p).max().getAsInt();
            item.value = item.item;
        } else {
            int total = 0;
            for (int i = 0; i < list.size(); i++)
                if (list.get(i) > total) {
                    total = list.get(i);
                    item.item = total;
                    item.value = total;
                }

        }
        return item;
    }
    public static vListItem<Integer,Integer> Min(List<Integer> list) {

        vListItem<Integer,Integer> item = new vListItem<Integer,Integer>();

        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            item.item = list.stream().mapToInt(p -> p).min().getAsInt();
            item.value = item.item;
        } else {

            int total = list.get(0);
            for (int i = 0; i < list.size(); i++)
                if (list.get(i) > total)
                {
                    total = list.get(i);
                    item.item = total;
                    item.value = total;
                }

        }
        return item;
    }


    public static  <T> int Sum(List<T> list,Pre<T,Integer> pre) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().mapToInt(p -> pre.get(p)).sum();
        } else {
            int total = 0;
            for (int i = 0; i < list.size(); i++)
                total += pre.get(list.get(i));
            return total;
        }
    }

    public static  <T> vListItem<T,Integer> Max(List<T> list,SmartList.Pre<T,Integer> pre) {

        vListItem<T,Integer> item = new vListItem<T,Integer>();
        final Comparator<T> comp = (p1, p2) -> Integer.compare(pre.get(p1), pre.get(p2));
        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            item.item  = list.stream().max(comp).get();
            item.value = pre.get(item.item);

        } else {
            int total = 0;
            for (int i = 0; i < list.size(); i++)
                if (pre.get(list.get(i)) > total) {
                    total = pre.get(list.get(i));
                    item.item = list.get(i);
                    item.value = total;
                }

        }
        return item;
    }
    public static  <T> vListItem<T,Integer> Min(List<T> list,SmartList.Pre<T,Integer> pre) {

        vListItem<T,Integer> item = new vListItem<T,Integer>();
        final Comparator<T> comp = (p1, p2) -> Integer.compare(pre.get(p1), pre.get(p2));
        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            item.item  = list.stream().max(comp).get();
            item.value = pre.get(item.item);

        } else {
            int total = pre.get(list.get(0));
            for (int i = 0; i < list.size(); i++)
                if (pre.get(list.get(i)) < total) {
                    total = pre.get(list.get(i));
                    item.item = list.get(i);
                    item.value = total;
                }

        }
        return item;
    }

    //endregion

    //region Double
    public static double SumDouble(List<Double> list) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().mapToDouble(p -> p).sum();
        } else {
            int total = 0;
            for (int i = 0; i < list.size(); i++)
                total += list.get(i);
            return total;
        }
    }

    public static vListItem<Double,Double> MaxDouble(List<Double> list) {

        vListItem<Double,Double> item = new vListItem<Double,Double>();

        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            item.item = list.stream().mapToDouble(p -> p).max().getAsDouble();
            item.value = item.item;
        } else {
            double total = 0;
            for (int i = 0; i < list.size(); i++)
                if (list.get(i) > total) {
                    total = list.get(i);
                    item.item = total;
                    item.value = total;
                }

        }
        return item;
    }
    public static vListItem<Double,Double> MinDouble(List<Double> list) {

        vListItem<Double,Double> item = new vListItem<Double,Double>();

        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            item.item = list.stream().mapToDouble(p -> p).min().getAsDouble();
            item.value = item.item;
        } else {

            double total = list.get(0);
            for (int i = 0; i < list.size(); i++)
                if (list.get(i) > total)
                {
                    total = list.get(i);
                    item.item = total;
                    item.value = total;
                }

        }
        return item;
    }

    public static  <T> double SumDouble(List<T> list,Pre<T,Double> pre) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return list.stream().mapToDouble(p -> pre.get(p)).sum();
        } else {
            int total = 0;
            for (int i = 0; i < list.size(); i++)
                total += pre.get(list.get(i));
            return total;
        }
    }

    public static  <T> vListItem<T,Double> MaxDouble(List<T> list,SmartList.Pre<T,Double> pre) {

        vListItem<T,Double> item = new vListItem<T,Double>();
        final Comparator<T> comp = (p1, p2) -> Double.compare(pre.get(p1), pre.get(p2));
        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            item.item  = list.stream().max(comp).get();
            item.value = pre.get(item.item);

        } else {
            double total = 0;
            for (int i = 0; i < list.size(); i++)
                if (pre.get(list.get(i)) > total) {
                    total = pre.get(list.get(i));
                    item.item = list.get(i);
                    item.value = total;
                }

        }
        return item;
    }
    public static  <T> vListItem<T,Double> MinDouble(List<T> list,SmartList.Pre<T,Double> pre) {

        vListItem<T,Double> item = new vListItem<T,Double>();
        final Comparator<T> comp = (p1, p2) -> Double.compare(pre.get(p1), pre.get(p2));
        if (list.size() == 0) return item;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            item.item  = list.stream().max(comp).get();
            item.value = pre.get(item.item);

        } else {
            double total = pre.get(list.get(0));
            for (int i = 0; i < list.size(); i++)
                if (pre.get(list.get(i)) < total) {
                    total = pre.get(list.get(i));
                    item.item = list.get(i);
                    item.value = total;
                }

        }
        return item;
    }



    //endregion



    //endregion


    //endregion

    public static class vListItem<V,VT>
    {


        public V item = null;
        public VT value = null;
    }
}
