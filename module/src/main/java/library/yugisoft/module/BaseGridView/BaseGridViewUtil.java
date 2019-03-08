package library.yugisoft.module.BaseGridView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTime;
import library.yugisoft.module.yugi;

public interface BaseGridViewUtil
{

    Hashtable<String,BaseGridCell> getColumns();
    default Hashtable<String,BaseGridCell> createCellsForObject(Object object) {
        Hashtable<String,BaseGridCell> cells = new Hashtable<>();

        Field[] f1 = object.getClass().getFields();
        Field[] f2 = object.getClass().getDeclaredFields();

        loadFiledToHashtable(cells,f1);
        loadFiledToHashtable(cells,f2);

        return cells;
    }
    default Hashtable<String,BaseGridCell> createCellsForLayout(int id) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(yugi.activity).inflate(id,null);
        return getTextViewInLayout(viewGroup);
    }
    default Hashtable<String,BaseGridCell> createCellsForLayout(ViewGroup object) { return getTextViewInLayout(object); }
    default void loadFiledToHashtable(Hashtable<String,BaseGridCell> cells,Field[] f1) {
        for (Field f : f1)
        {
            String fName = f.getName();
            if (fName.equals("$change") ||fName.equals("serialVersionUID") ||cells.get(f.getName()) != null)
            {
                continue;
            }
            else
            {
                BaseGridCell cell = new BaseGridCell();
                Class clazz = f.getDeclaringClass();
                cell.setType(clazz);
                cell.setFieldName(fName);


                if (clazz.equals(Integer.class) || clazz.equals(int.class))
                {
                    cell.setTextAlign(Gravity.CENTER|Gravity.CENTER_VERTICAL);
                    cell.setFormat("${"+fName+":n0}");
                }
                else if (clazz.equals(Long.class) || clazz.equals(long.class))
                {
                    cell.setTextAlign(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                    cell.setFormat("${"+fName+":n0}");
                }
                else if (clazz.equals(Double.class) || clazz.equals(double.class))
                {
                    cell.setTextAlign(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                    cell.setFormat("${"+fName+":n2}");
                }
                else if (clazz.equals(DateTime.class))
                {
                    cell.setTextAlign(Gravity.CENTER|Gravity.CENTER_VERTICAL);
                    cell.setFormat("${"+fName+"}");
                }
                else
                {
                    cell.setTextAlign(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                    cell.setFormat("${"+fName+"}");
                }

                cell.setVisible(Modifier.isPublic(f.getModifiers()));

                cells.put(fName,cell);
            }
        }
    }
    default Hashtable<String,BaseGridCell> getTextViewInLayout(ViewGroup viewGroup) {
        Hashtable<String,BaseGridCell> list = new Hashtable<>();
        for (int i = 0 ; i< viewGroup.getChildCount() ; i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof BaseGridTextView)
                list.put(((BaseGridTextView)view).getBaseGridCell().getFieldName(),((BaseGridTextView)view).getBaseGridCell());
            else if (view instanceof  ViewGroup)
                list.putAll(getTextViewInLayout((ViewGroup) view));
        }
        return list;
    }
}
