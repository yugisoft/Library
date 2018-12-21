package library.yugisoft.module;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class ComboBox extends android.support.v7.widget.AppCompatSpinner {

    public ComboBox(Context context) {
        super(context);
    }

    public ComboBox(Context context, int mode) {
        super(context, mode);
    }

    public ComboBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(ItemAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(this.adapter);

    }


    private boolean priveteSelected = false;


    public void setSelection(int position,boolean priveteSelected)
    {
        this.priveteSelected = priveteSelected;
        super.setSelection(position);
    }


    public void setSelection(int position, boolean priveteSelected, boolean animate) {
        this.priveteSelected = priveteSelected;
        super.setSelection(position, animate);
    }



    @Override
    public void setOnItemSelectedListener(@Nullable OnItemSelectedListener listener) {

        super.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!priveteSelected)
                {
                    listener.onItemSelected(parent,view,position,id);
                }
                priveteSelected=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private ItemAdapter adapter;

    public <T> T getItem(int i)
    {
        try
        {
            return (T)adapter.getList().get(i);
        }
        catch (Exception ex)
        {
            yugi.Print("e","ComboBox-getItem",ex.getMessage());
            return null;
        }
    }

    private OnComboBoxSelectedItem onComboBoxSelectedItem;

    public OnComboBoxSelectedItem getOnComboBoxSelectedItem() {
        return onComboBoxSelectedItem;
    }

    public void setOnComboBoxSelectedItem(OnComboBoxSelectedItem onComboBoxSelectedItem) {
        this.onComboBoxSelectedItem = onComboBoxSelectedItem;
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (onComboBoxSelectedItem!=null)
                onComboBoxSelectedItem.onSelected(ComboBox.this,position,getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface OnComboBoxSelectedItem<T> {
        void onSelected(ComboBox comboBox,int index, T item);
    }
}
