package library.yugisoft.module.Data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class DataGridAdapter2 extends ItemAdapter<DataTable.DataRow> implements View.OnClickListener {

    //region MORE
    List<Integer> columLenght = new ArrayList<>();
    String[] filters ;
    private DataGridView dataGridView;
    private int height=0;
    private int rowColor = 0;


    public DataGridAdapter2(Context context,DataGridView dataGridView) {
        super(context);
        this.dataGridView = dataGridView;
    }
    private List<String> columns = null,captions;
    public List<String> getColumns() {
        return columns == null ? getData().Columns : columns;
    }
    public List<String> getCaptions() {
        return captions == null ? getData().Captions : captions;
    }
    //region PRIVATE

    private int textSize = 14,color = Color.BLACK,headerForeColor = Color.WHITE;

    private Typeface typeface = Typeface.SANS_SERIF;

    private DataTable Data;

    private AdapterView.OnItemClickListener onItemClickListener;



    //endregion
    //region GET&SET
    public int getTextSize() {
        return textSize;
    }
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public DataTable getData() {
        return Data;
    }

    public void setData(DataTable data) {
      Data = data;
      columLenght.clear();
      filters = new String[getColumns().size()];
      list=data.Rows;
      calculateColumnHeight();
      notifyDataSetChanged();
    }

    @Override
    public void setList(List<DataTable.DataRow> listt)
    {
        yugi.Print("e","DataGridAdapter","setList Yerine setData methodunu kullanın.");
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public void setCaptions(List<String> captions) {
        this.captions = captions;
    }


    //endregion

    private DataGridTextView.DataGridValueChanged onGridValueChanged = new DataGridTextView.DataGridValueChanged() {
        @Override
        public String onChange(int row, int cell, DataGridTextView textView,String value)
        {
            try
            {
                return dataGridValueChanged.onChange(row,cell,textView,value);
            }
            catch (Exception ex)
            {
                return value;
            }

        }
    };

    private DataGridTextView.DataGridValueChanged dataGridValueChanged;






    //endregion
    @Override
    public void onClick(View v) {

        int i = Integer.parseInt(v.getTag().toString());
        if (getOnItemClickListener()!=null)
            getOnItemClickListener().onItemClick(null,v,i,0);
    }
    private View.OnClickListener HeaderClick = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            boolean intt = false;
            if (v instanceof DataGridTextView)
            {
                DataGridTextView textView = (DataGridTextView) v;
                intt = textView.getType() == 1 || textView.getType() == 2;

            }
            String index = getColumns().get(Integer.parseInt(v.getTag().toString()));

            if (intt)
                //vList.Sort(getList(),(p1,p2) -> Integer.valueOf(p1.getInt(index)).compareTo(p2.getInt(index)));
                vList.Sort(getList(), new vList.PreTwice<DataTable.DataRow, Integer>() {
                    @Override
                    public Integer get(DataTable.DataRow t1, DataTable.DataRow t2) {
                        return Double.valueOf(t1.getDouble(index)).compareTo(t2.getDouble(index));
                    }
                });
            else
                vList.Sort(getList(),(p1,p2) -> p1.get(index).compareToIgnoreCase(p2.get(index)));
            notifyDataSetChanged();
        }
    };
    private View.OnLongClickListener HeaderLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v)
        {
            int index = Integer.parseInt(v.getTag().toString());

            DialogBox.showRESULTTEXT(filters[index], new DialogBox.IDialogResult() {
                @Override
                public void onResult(DialogBox.EDialogButtons buttons, String result) {
                    if (DialogBox.EDialogButtons.OK == buttons)
                    {

                        filters[index]=result;
                        String f ="";
                        for (int i =0 ; i< filters.length ;i++)
                        {
                            String str = filters[i];
                            if ( str != null && str.length()>0)
                            {
                                if (!f.equals(""))f +="&&";
                                f+=getColumns().get(i)+ ((str.indexOf("%")>=0) ? "%%" : "==")+str.replace("%","") ;
                            }

                        }

                        DataGridAdapter2.super.setList(getData().Where(f));
                    }
                }
            });
            return false;
        }
    };
    private TextWatcher filterchange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (current !=null)
            {
                int index = Integer.parseInt(current.getTag().toString());
                filters[index]=current.getText().toString();
                String f ="";
                for (int i =0 ; i< filters.length ;i++)
                {
                    String str = filters[i];
                    if ( str != null && str.length()>0)
                    {
                        if (!f.equals(""))f +="&&";
                        f+=getColumns().get(i)+ ((str.indexOf("%")>=0) ? "%%" : "%%")+str.replace("%","") ;
                    }

                }

                DataGridAdapter2.super.setList(getData().Where(f));
            }
        }
    };
    private EditText current;
    public void setHeaderForeColor(int headerForeColor) {
        this.headerForeColor = headerForeColor;
    }

    private List<DataGridTextView> textViews = new ArrayList<>();

    String formatValue ="%s";
    public void calculateColumnHeight()
    {
        columLenght.clear();
        if (textViews.size()>0)
        {
            for (int i  = 0 ; i<textViews.size();i++)
            {
                DataGridTextView textView =textViews.get(i);
                textView.setDataGridView(dataGridView);

                String caption = textView.getText().toString();
                String fieldName = textView.getFieldName();
                int cellIndex = getData().Columns.indexOf(fieldName);

                if (textView.getFormat().indexOf("[")>0)
                {
                    formatValue =textView.getFormat();

                    for (String item : textView.getFormat().split("\\[")) {
                        for (String item2 : item.split("\\]")) {
                            String itemValue = "";
                            try
                            {
                                DataTable.DataRow row = vList.Max(getList(),p-> onGridValueChanged.onChange(-1,getColumns().indexOf(item2),textView,p.get(item2)).length()).item;
                                formatValue = formatValue.replace("[" + item2 + "]",row.get(item2));

                                DataGridTextView t = new DataGridTextView(context);
                                t.setRow(getList().indexOf(row));
                                t.setCell(getColumns().indexOf(item2));

                                formatValue = (formatValue.replace("[" + item2 + "]",  onGridValueChanged.onChange(t.getRow(), t.getCell(), t, itemValue)));

                            } catch (Exception ex) {
                            }
                        }
                    }

                    DataTable.DataRow row = vList.Max(getList(), p-> onGridValueChanged.onChange(-1,textView.getCell(),textView,(p.get(fieldName))).length()).item;
                    String  value = onGridValueChanged.onChange(-1,textView.getCell(),textView,row.get(fieldName))+formatValue;
                    columLenght.add(i,getTextLenght(value.length() > caption.length() ? value : caption));

                }
                else
                {
                    DataTable.DataRow row = vList.Max(getList(),p-> onGridValueChanged.onChange(-1,textView.getCell(),textView,p.get(fieldName)).length()).item;
                    String value = onGridValueChanged.onChange(-1,textView.getCell(),textView,row.get(fieldName));
                    columLenght.add(i,getTextLenght(value.length() > caption.length() ? value : caption));
                }
            }
        }
        else
        {
            for (int i  = 0 ; i<getColumns().size();i++)
            {
                DataGridTextView textView = new DataGridTextView(context);
                textView.setDataGridView(dataGridView);
                textView.setFieldName(getColumns().get(i));
                textView.setText(getCaptions().get(i));
                textView.setType(0);
                String caption = getCaptions().get(i);
                String fieldName = textView.getFieldName();
                int cellIndex = i;
                DataTable.DataRow row = vList.Max(getList(),p-> onGridValueChanged.onChange(-1,textView.getCell(),textView,p.get(fieldName)).length()).item;
                String value = onGridValueChanged.onChange(-1,textView.getCell(),textView,row.get(fieldName));
                columLenght.add(i,getTextLenght(value.length() > caption.length() ? value : caption));
                textViews.add(textView);
            }
        }
        if (vList.Sum(columLenght)< dataGridView.getWidth() && vList.Min(columLenght).value > 0)
        {
            columLenght.set(columLenght.indexOf(vList.Max(columLenght).item),-1);
        }


    }
    private int getTextLenght(String text) {

        try
        {
            text = "_"+text+"_";
            Paint paint = new Paint();
            paint.setTextSize(yugi.convertDpToPixel(getTextSize()+2,yugi.activity));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setColor(getColor());
            paint.setStyle(Paint.Style.FILL);
            Rect result = new Rect();
            paint.getTextBounds(text, 0, text.length(), result);
            height=result.height();
            return result.width();
        }
        catch (Exception ex)
        {
            return -2;
        }
    }

    public DataGridTextView.DataGridValueChanged getDataGridValueChanged() {
        return dataGridValueChanged;
    }

    public void setDataGridValueChanged(DataGridTextView.DataGridValueChanged dataGridValueChanged) {
        this.dataGridValueChanged = dataGridValueChanged;
    }

    public LinearLayout getLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackground(context.getResources().getDrawable(R.drawable.ripple_sold));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        return  linearLayout;
    }


    private int getColumnLenght(int k) { return columLenght.get(k); }

    private DataGridTextView getTextView(int i, int k) {
       DataGridTextView textView = CreateDataGridTextView(i,k,new DataGridTextView(context));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setTextAlignment(textViews.get(k).getTextAlignment());
        }
        textView.setValue(getData().get(i,textView.getFieldName()));

       return textView;
    }
    private DataGridTextView getHeaderTextView(int i, int k,DataGridTextView textView) {
        textView = CreateDataGridTextView(i,k,textView);
        textView.setValue(getCaptions().get(k));
        textView.setTextColor(headerForeColor);
        textView.setTextSize(getTextSize()+2);
        textView.setOnClickListener(HeaderClick);
        textView.setOnLongClickListener(HeaderLongClick);
        return textView;
    }
    private DataGridTextView CreateDataGridTextView(int i, int k,DataGridTextView textView) {
        //DataGridTextView textView = new DataGridTextView(context);
        // textView.setFormat(tView.getFormat());
        // textView.setType(tView.getType());
        // if (!tView.isAutoSize())
        // {
        //     textView.setLayoutParams(tView.getLayoutParams());
        // }

        textView.setType(textViews.get(k).getType());
        textView.setFormat(textViews.get(k).getFormat());
        String name = getColumns().get(k);
        textView.setDataGridView(this.dataGridView);
        textView.setFieldName(name);
        textView.setRow(i);
        textView.setCell(k);

        textView.setOnDataGridValueChanged(onGridValueChanged);

        textView.setBackground(context.getResources().getDrawable(android.R.drawable.edit_text));
        textView.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        textView.setTextSize(getTextSize());
        textView.setTextColor(getColor());
        textView.setTypeface(getTypeface());

        //region AutoSize
        if (textView.isAutoSize())
        {
            int width = getColumnLenght(k);
            LinearLayout.LayoutParams params;
            if (width==-1)
            {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height+20,1.0f);
            }
            else
            {
                params = new LinearLayout.LayoutParams(width, height+20);
            }
            textView.setPaddingRelative(3,5,3,5);
            params.setMargins(3,5,3,5);

            params.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(params);
        }
        //endregion



        return  textView;
    }

    public View getHeaderView(LinearLayout viewGroup)
    {
        LinearLayout layout = getLayout();
        layout.setBackground(null);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.removeAllViews();
        for (int k = 0; k < getColumns().size(); k++) { layout.addView(getHeaderTextView(-1, k,new DataGridTextView(context))); }
        viewGroup.addView(layout);
        return layout;
    }
    public View getFilterView(LinearLayout viewGroup) {
        LinearLayout layout = getLayout();
        layout.setBackground(null);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.removeAllViews();
        for (int k = 0; k < getColumns().size(); k++) { layout.addView(getFilterEditTextView(-1, k)); }
        viewGroup.addView(layout);
        return layout;
    }

    private View getFilterEditTextView(int i, int k) {
        float width = getColumnLenght(k);

        EditText textView = new EditText(context);
        //textView.setBackground(context.getResources().getDrawable(android.R.drawable.edit_text));

        //textView.setBackgroundColor(Color.WHITE);


        textView.setTextSize(getTextSize());
        textView.setTextColor(getColor());
        textView.setTypeface(getTypeface());

        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    current = (EditText)v;
            }
        });
        textView.addTextChangedListener(filterchange);
        textView.setTag(k);

        LinearLayout.LayoutParams params;
        if (width==-1)
        {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        }
        else
        {
            params = new LinearLayout.LayoutParams((int)width, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        textView.setPaddingRelative(3,5,3,5);
        params.setMargins(3,5,3,5);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.WHITE);
        //textView.setBackground(context.getResources().getDrawable(R.drawable.border));
        return  textView;
    }

    public View getView(int i,  LinearLayout viewGroup) {
        LinearLayout layout = getLayout();
        if (rowColor!=0)
        {
            try
            {

                layout.setBackground(context.getResources().getDrawable(rowColor));
            }
            catch (Exception ex){

                layout.setBackgroundColor(rowColor);
            }
        }
        layout.setTag(i);
        layout.setOnClickListener(this::onClick);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        layout.removeAllViews();
        for (int k = 0; k < getColumns().size(); k++) { layout.addView(getTextView(i, k)); }
        viewGroup.addView(layout);
        return layout;
    }

    public void setTextViews(List<DataGridTextView> textViews) {
        this.textViews = textViews;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void UpdateHeaderView(LinearLayout header)
    {
        for (int i =0 ; i< getColumns().size() ;i++)
        {
            View v =header.getChildAt(i);
             v = getHeaderTextView(-1,i, (DataGridTextView) header.getChildAt(i));
        }
    }

    public void setRowColor(int rowColor)
    {
        this.rowColor = rowColor;
    }
}
