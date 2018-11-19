package library.yugisoft.module.Data;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTime;
import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

public class DataGridTextView extends android.support.v7.widget.AppCompatTextView
{

    public DataGridTextView(Context context) { this(context,null,0); }

    public DataGridTextView(Context context, AttributeSet attrs) { this(context,attrs,0); }

    public DataGridTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        TypedArray typed = getContext().obtainStyledAttributes(attrs, R.styleable.DataGridTextView, defStyleAttr, 0);
        if (typed!=null)
        {
            setType(typed.getInt(R.styleable.DataGridTextView_dataType,0));
            setFormat(typed.getString(R.styleable.DataGridTextView_format));
            if (getFormat()==null)setFormat("");
            setFieldName(typed.getString(R.styleable.DataGridTextView_fieldName));
            setAutoSize(typed.getBoolean(R.styleable.DataGridTextView_autoWidth,autoSize));
        }
    }




    private int type = 0,row,cell;

    private DataGridValueChanged onDataGridValueChanged;

    private String format="",fieldName="";

    private boolean autoSize = true;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
        setTag(fieldName);
    }

    public boolean isAutoSize() {
        return autoSize;
    }

    public void setAutoSize(boolean autoSize) {
        this.autoSize = autoSize;
    }

    public void setValue(String caption) {
        if (getRow() <  0)
        {
         setText(caption);
        }
        else {
            if (getFormat().equals("")) {
                switch (getType()) {
                    case 0:
                    case 1:
                        setText(caption);
                        break;
                    case 2:
                        setText(yugi.NF2(caption));
                        break;
                    case 3:
                        setText(caption);
                        break;
                }

            } else {
                switch (getType()) {
                    case 0:
                        setText(String.format(getFormat(), caption));
                    case 1:
                        setText(String.format(getFormat(), Integer.parseInt(caption)));
                        break;
                    case 2:
                        setText(String.format(getFormat(), Double.parseDouble(caption)));
                        break;
                    case 3:
                        setText(String.format(getFormat(), DateTime.fromDateTime(caption)));
                        break;
                }

                if (getText().toString().indexOf("[") > 0 && getText().toString().indexOf("]") > 0) {
                    for (String item : getText().toString().split("\\[")) {
                        for (String item2 : item.split("\\]")) {
                            String itemValue = "";
                            try {
                                itemValue = getDataGridView().getData().get(getRow(), item2);
                            } catch (Exception ex) {
                            }
                            if (getOnDataGridValueChanged() != null) {
                                DataGridTextView t = RowTextView(item2);
                                setText(getText().toString().replace("[" + item2 + "]", getOnDataGridValueChanged().onChange(getRow(), t.getCell(), t, itemValue)));
                            } else {
                                setText(getText().toString().replace("[" + item2 + "]", itemValue));
                            }
                        }
                    }
                }


            }
            if (getOnDataGridValueChanged() != null && getRow() >= 0)
                setText(getOnDataGridValueChanged().onChange(getRow(), getCell(), this, getText().toString()));
        }
    }

    public void setValue(String caption,Object obj) {
        if (getRow() <  0)
        {
            setText(caption);
        }
        else {
            if (getFormat().equals("")) {
                switch (getType()) {
                    case 0:
                    case 1:
                        setText(caption);
                        break;
                    case 2:
                        setText(yugi.NF2(caption));
                        break;
                    case 3:
                        setText(caption);
                        break;
                }

            } else {
                switch (getType()) {
                    case 0:
                        setText(String.format(getFormat(), caption));
                    case 1:
                        setText(String.format(getFormat(), Integer.parseInt(caption)));
                        break;
                    case 2:
                        setText(String.format(getFormat(), Double.parseDouble(caption)));
                        break;
                    case 3:
                        setText(String.format(getFormat(), DateTime.fromDateTime(caption)));
                        break;
                }

                if (getText().toString().indexOf("[") > 0 && getText().toString().indexOf("]") > 0)
                {
                    for (String item : getText().toString().split("\\[")) {
                        for (String item2 : item.split("\\]")) {
                            String itemValue = "";
                            try {
                                itemValue = obj.getClass().getField(item2).get(obj).toString();
                            } catch (Exception ex) {
                            }
                            if (getOnDataGridValueChanged() != null) {
                                DataGridTextView t = RowTextView(item2);
                                setText(getText().toString().replace("[" + item2 + "]", getOnDataGridValueChanged().onChange(getRow(), t.getCell(), t, itemValue)));
                            } else {
                                setText(getText().toString().replace("[" + item2 + "]", itemValue));
                            }
                        }
                    }
                }


            }
            if (getOnDataGridValueChanged() != null && getRow() >= 0)
                setText(getOnDataGridValueChanged().onChange(getRow(), getCell(), this, getText().toString()));
        }
    }

    private DataGridView gridView;
    public DataGridView getDataGridView() {
        return gridView;
    }
    public void setDataGridView(DataGridView adapter) {
        this.gridView = adapter;
    }


   DataGridTextView RowTextView(String name)
    {
        DataGridTextView textView = new DataGridTextView(getContext());

        if (getDataGridView()!=null)
        {
            textView.setRow(getRow());
            textView.setCell(gridView.getDataGridAdapter().getColumns().indexOf(name));
            textView.setFieldName(name);
        }
        else
        {
            textView.setRow(0);
            textView.setCell(0);
            textView.setFieldName("");
        }

        return textView;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public DataGridValueChanged getOnDataGridValueChanged() {
        return onDataGridValueChanged;
    }

    public void setOnDataGridValueChanged(DataGridValueChanged onDataGridValueChanged) {
        this.onDataGridValueChanged = onDataGridValueChanged;
    }

    public interface DataGridValueChanged
    {
        String  onChange(int row,int cell,DataGridTextView textView,String value);
    }


}
