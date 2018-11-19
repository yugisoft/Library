package library.yugisoft.module.Data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTextView;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class BaseDataGridAdapter<T> extends ItemAdapter<T>
{
    public BaseDataGridAdapter(Context context) { super(context); }

    private DataGridTextView.DataGridValueChanged dataGridValueChanged;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = super.getView(i, view, viewGroup);

        Object ob = (T)getItem(i);
        Class clas = ob.getClass();

        for ( Field field :clas.getFields())
        {
            View v = view.findViewWithTag(field.getName());

            if (v != null && v instanceof DataGridTextView)
            {
                DataGridTextView textView = (DataGridTextView) v;
                textView.setOnDataGridValueChanged(getDataGridValueChanged());
                try
                {
                    textView.setValue(field.get(ob).toString(),ob);

                } catch (Exception ex) { }
            }
        }

        if (viewGroup!=null)
            viewGroup.addView(view);
        return view;
    }

    public DataGridTextView.DataGridValueChanged getDataGridValueChanged() {
        return dataGridValueChanged;
    }

    public void setDataGridValueChanged(DataGridTextView.DataGridValueChanged dataGridValueChanged) {
        this.dataGridValueChanged = dataGridValueChanged;
    }
}
