package library.yugisoft.module.Data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DialogBox;
import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class DataGridAdapter extends ItemAdapter<DataTable.DataRow> implements View.OnClickListener
{

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

                        setList(data.Where(f));
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

                setList(data.Where(f));
            }
        }
    };
    private EditText current;
    private int height;
    private int headerForeColor = Color.WHITE;
    private int parentWidth;

    public DataGridAdapter(Context context) {
        super(context);
    }



    void setLayoutClickable(View view,int i) {
        view.setTag(i);
        view.setBackground(yugi.activity.getResources().getDrawable(R.drawable.ripple_sold));
        view.setOnClickListener(this);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        LinearLayout layout = (LinearLayout) (view == null ? view = getLayout() : view);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        if (Columns !=null)
            layout = (LinearLayout) super.getView(i,view,viewGroup);

        setLayoutClickable(layout,i);
        if (Columns ==null)
        layout.removeAllViews();

        for (int k = 0; k< getColumns().size() ; k++ )
        {
            if (Columns ==null)
                    layout.addView(getTextView(i,k));
            else
                    getTextView(layout,i,k);
        }

        return layout;
    }

    @Override
    public void onClick(View v) {

        int i = Integer.parseInt(v.getTag().toString());
        if (getOnItemClickListener()!=null)
            getOnItemClickListener().onItemClick(null,v,i,0);
    }

    //region Property
    String[] filters ;

    private float getColumnLenght(int i)
    {

        if (getParentWidth()>0 && totalWith()<getParentWidth())
        {
            if (vList.Min(columLenght).value != -1)
            {
                int in = columLenght.indexOf(vList.Max(columLenght).item);
                columLenght.set(in, -1);
            }
        }
        String name = getColumns().get(i);
        try
        {

            float s = columLenght.get(i);
            if (s!=0)
                return s;
            else
            {
                String lenght = getCaption(i);
                String lenght2 = "";
                try
                {
                    vList.Max(getList(),p-> p.get(name).length()).item.get(name);
                }
                catch (Exception ex)
                {

                }
                columLenght.set(i,getTextLenght( lenght.length() > lenght2.length() ? lenght: lenght2 ));

                return columLenght.get(i);
            }
        }
        catch (Exception ex)
        {
            String lenght = getCaption(i);
            String lenght2 = "";
            try
            {
                vList.Max(getList(),p-> p.get(name).length()).item.get(name);
            }
            catch (Exception exx)
            {

            }
            columLenght.add(getTextLenght( lenght.length() > lenght2.length() ? lenght: lenght2 ));
        }
        return columLenght.get(i);
    }

    private int totalWith() {
        int w = 0;
        for (int i = 0; i < getColumns().size() ; i++)
        {
            String index = getColumns().get(i);
            String lenght =getCaption(i);
            String lenght2 = vList.Max(getList(),p-> p.get(index).length()).item.get(index);
            w += getTextLenght( lenght.length() > lenght2.length() ? lenght: lenght2 );
        }
        return w;
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
    }

    private int textSize = 14;
    private Typeface typeface = Typeface.SANS_SERIF;
    private int color = Color.BLACK;

    public int getTextSize() { return textSize; }
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
    public Typeface getTypeface() {
        return typeface;
    }
    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    private DataTable data;
    List<Integer> columLenght = new ArrayList<>();
    public DataTable getData() {
        return data;
    }
    public void setData(DataTable data) {
        this.data = data;
        columLenght.clear();
        filters = new String[getColumns().size()];
        setList(data.Rows);
    }


    private AdapterView.OnItemClickListener onItemClickListener;

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //endregion

    //region Captions
    private List<String> Columns,Captions;
    public List<String> getColumns()
    {
        return Columns ==null ? data.Columns : Columns;
    }
    public List<String> getCaptions()
    {
        return Captions== null? data.Captions : Captions;
    }
    public void setColumns(List<String> columns) {
        Columns = columns;
    }
    public void setCaptions(List<String> captions) {
        Captions = captions;
    }
    //endregion

    //region Create View
    public LinearLayout getLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        return  linearLayout;
    }

    public View getFilterView() {

        LinearLayout layout = getLayout() ;
        for (int k = 0; k< getColumns().size() ; k++ ) { layout.addView(getFilterTextView(k)); }
        return layout;
    }
    public View getFilterView(ViewGroup lyt) {

        LinearLayout layout = getLayout() ;
        for (int k = 0; k< getColumns().size() ; k++ )
        {
            layout.addView(getFilterTextView(k, (LinearLayout.LayoutParams) lyt.getChildAt(k).getLayoutParams()));
        }
        return layout;
    }
    public View getHeaderView() {

        LinearLayout layout = getLayout() ;
        for (int k = 0; k< getColumns().size() ; k++ ) { layout.addView(getHeaderTextView(k)); }
        return layout;
    }
    private View getHeaderTextView(int k)
    {


        String caption = getCaption(k);

        float width = getColumnLenght(k);

        TextView textView = new TextView(context);

        textView.setText(caption);
        textView.setTextSize(getTextSize()+2);
        textView.setTextColor(headerForeColor);
        textView.setTypeface(Typeface.DEFAULT_BOLD);


        textView.setTag(k);
        textView.setOnClickListener(HeaderClick);
        textView.setOnLongClickListener(HeaderLongClick);

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

        return  textView;
    }

    private String getCaption(int k)
    {
        try
        {
            return  getCaptions().get(k);
        }
        catch (Exception ex)
        {
            try
            {
                return  getColumns().get(k);
            }
            catch (Exception exx)
            {
                return "invalid";
            }
        }

    }

    private View getFilterTextView(int k) {

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
    private View getFilterTextView(int k, LinearLayout.LayoutParams params) {

        EditText textView = new EditText(context);
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
        textView.setPaddingRelative(3,5,3,5);

        params.setMargins(3,5,3,5);

        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.WHITE);



        //textView.setBackground(context.getResources().getDrawable(R.drawable.border));
        return  textView;
    }

    private View getTextView(int i, int k) {

        String name = getColumns().get(k);

        DataGridTextView textView = new DataGridTextView(context);

        if (textView.isAutoSize())
        {
            float width = getColumnLenght(k);
            LinearLayout.LayoutParams params;
            if (width==-1)
            {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height+20,1.0f);
            }
            else
            {
                params = new LinearLayout.LayoutParams((int)width, height+20);
            }
            textView.setPaddingRelative(3,5,3,5);
            params.setMargins(3,5,3,5);

            params.gravity = Gravity.CENTER_VERTICAL;
            textView.setLayoutParams(params);
        }

        textView.setBackground(context.getResources().getDrawable(android.R.drawable.edit_text));
        textView.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);

        String caption = getList().get(i).get(name);
        textView.setValue(caption);
        textView.setTextSize(getTextSize());
        textView.setTextColor(getColor());
        textView.setTypeface(getTypeface());
        textView.setPaddingRelative(3,5,3,5);



        return  textView;
    }
    private View getTextView(ViewGroup layout,int i, int k) {
        String name = getColumns().get(k);
        DataGridTextView textView = (DataGridTextView) layout.getChildAt(k);
        if (textView.isAutoSize())
        {
            float width = getColumnLenght(k);
            LinearLayout.LayoutParams params;
            if (width==-1)
            {
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1.0f);
            }
            else
            {
                params = new LinearLayout.LayoutParams((int)width, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.gravity = Gravity.CENTER_VERTICAL;
            params.setMargins(3,5,3,5);
            textView.setLayoutParams(params);
        }
        String caption = getList().get(i).get(name);
        textView.setValue(caption);
        textView.setTextSize(getTextSize());
        textView.setTextColor(getColor());
        textView.setTypeface(getTypeface());
        textView.setPaddingRelative(3,5,3,5);
        return  textView;
    }


    //endregion

    public void setHeaderItemSize(ViewGroup childAt) {
     for (int k = 0; k< getColumns().size();k++)
     {
         DataGridTextView textView = (DataGridTextView) childAt.getChildAt(k);
         textView.setTag(k);
         textView.setOnClickListener(HeaderClick);
         if (textView.isAutoSize())
         {
             float width = getColumnLenght(k);
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
         }
         else
         {
             columLenght.add(textView.getWidth());
         }
     }
    }

    public void setHeaderForeColor(int headerForeColor) {
        this.headerForeColor = headerForeColor;
    }

    public void setParentWidth(int parentWidth) {
        this.parentWidth = parentWidth;
    }

    public int getParentWidth() {
        return parentWidth;
    }
}


