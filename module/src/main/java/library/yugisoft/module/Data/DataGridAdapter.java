package library.yugisoft.module.Data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
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
                int index = Integer.parseInt(v.getTag().toString());
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
                                f+=data.Columns.get(i)+ ((str.indexOf("%")>=0) ? "%%" : "==")+str.replace("%","") ;
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
                        f+=data.Columns.get(i)+ ((str.indexOf("%")>=0) ? "%%" : "==")+str.replace("%","") ;
                    }

                }

                setList(data.Where(f));
            }
        }
    };
    private EditText current;
    private int height;
    public DataGridAdapter(Context context) {
        super(context);
    }


    public LinearLayout getLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return  linearLayout;
    }
    void setLayoutClickable(View view,int i) {
        view.setTag(i);
        view.setBackground(yugi.activity.getResources().getDrawable(R.drawable.ripple_sold));
        view.setOnClickListener(this);
    }
    private View getTextView(int i, int k) {
        String caption = getList().get(i).get(k);
        float width = getColumnLenght(k);

        TextView textView = new TextView(context);

        textView.setText(caption);
        textView.setTextSize(getTextSize());
        textView.setTextColor(getColor());
        textView.setTypeface(getTypeface());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)width, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setPaddingRelative(3,5,3,5);
        params.setMargins(3,5,3,5);
        textView.setLayoutParams(params);


        return  textView;
    }
    public View getView(int i, View view, ViewGroup viewGroup) {

        LinearLayout layout = (LinearLayout) (view == null ? view = getLayout() : view);
        setLayoutClickable(layout,i);
        layout.removeAllViews();
        for (int k = 0; k< data.Columns.size() ; k++ )
        {
            layout.addView(getTextView(i,k));
        }
        return layout;
    }

    public View getFilterView() {

        LinearLayout layout = getLayout() ;
        for (int k = 0; k< data.Columns.size() ; k++ ) { layout.addView(getFilterTextView(k)); }
        return layout;
    }
    public View getHeaderView() {

        LinearLayout layout = getLayout() ;
        for (int k = 0; k< data.Columns.size() ; k++ ) { layout.addView(getHeaderTextView(k)); }
        return layout;
    }
    private View getHeaderTextView(int k)
    {
        String caption = data.Captions.get(k);
        float width = getColumnLenght(k);

        TextView textView = new TextView(context);

        textView.setText(caption);
        textView.setTextSize(getTextSize()+2);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(Typeface.DEFAULT_BOLD);


        textView.setTag(k);
        textView.setOnClickListener(HeaderClick);
        textView.setOnLongClickListener(HeaderLongClick);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)width, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setPaddingRelative(3,5,3,5);
        params.setMargins(3,5,3,5);
        textView.setLayoutParams(params);

        return  textView;
    }
    private View getFilterTextView(int k)
    {
        String caption = data.Captions.get(k);
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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)width, height+(int)yugi.convertPixelToDp(20,context));
        textView.setPaddingRelative(3,5,3,5);
        params.setMargins(3,5,3,5);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(Color.WHITE);
        //textView.setBackground(context.getResources().getDrawable(R.drawable.border));
        return  textView;
    }

    //region Property
    String[] filters ;

    private float getColumnLenght(int i) {
        try
        {
            float s = columLenght.get(i);
            if (s>0)
                return s;
            else
            {
                String lenght = data.Captions.get(i);
                String lenght2 = vList.Max(getList(),p-> p.get(i).length()).item.get(i);
                columLenght.set(i,getTextLenght( lenght.length() > lenght2.length() ? lenght: lenght2 ));
                return columLenght.get(i);
            }
        }
        catch (Exception ex)
        {
            String lenght = data.Captions.get(i);
            String lenght2 = vList.Max(getList(),p-> p.get(i).length()).item.get(i);
            columLenght.add(getTextLenght( lenght.length() > lenght2.length() ? lenght: lenght2 ));
            return columLenght.get(i);

        }
    }
    private float getTextLenght(String text) {

        text = "___"+text+"____";
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
    List<Float> columLenght = new ArrayList<>();
    public DataTable getData() {
        return data;
    }
    public void setData(DataTable data) {
        this.data = data;
        columLenght.clear();
        filters = new String[data.Columns.size()];
        setList(data.Rows);
    }




    //endregion



    @Override
    public void onClick(View v) {

        int i = Integer.parseInt(v.getTag().toString());
        if (getOnItemClickListener()!=null)
            getOnItemClickListener().onItemClick(null,v,i,0);
    }

    private AdapterView.OnItemClickListener onItemClickListener;

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}


