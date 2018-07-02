package library.yugisoft.module;

import java.util.ArrayList;
import java.util.List;

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
}
