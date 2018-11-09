package library.yugisoft.module.Data;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.INTERFACES;
import library.yugisoft.module.R;

public class DataGridView extends LinearLayout implements INTERFACES.OnAdapterDataLoad {

    private DataGridAdapter dataGridAdapter;
    AttributeSet attrs; int defStyleAttr;
    public DataGridView(Context context) {
        this(context, null, 0);
    }
    public DataGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DataGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs=attrs;
        this.defStyleAttr =defStyleAttr;
        init();
    }

    boolean useHeaderLayout=false;
    int headerLayoutid = 0;

    private LinearLayout verLayout,filterLayout;
    private ViewGroup headerLayout;

    private void init() {


        inflate(getContext(), R.layout.view_datagridview,this);
        this.setPadding(3,0,3,3);
        verLayout = (LinearLayout)findViewById(R.id.layout);

        headerLayout= (LinearLayout)findViewById(R.id.headerLayout);
        filterLayout = (LinearLayout)findViewById(R.id.filterLayout);
        if (dataGridAdapter == null)
            setDataGridAdapter(new DataGridAdapter(getContext()));

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DataGridView, defStyleAttr, 0);
        if (a!=null)
        {
            setShowHeader(a.getBoolean(R.styleable.DataGridView_showHeader,showHeader));
            setShowFilter(a.getBoolean(R.styleable.DataGridView_showFilter,showFilter));

            headerLayoutid = a.getResourceId(R.styleable.DataGridView_headerLayout,0);
            if (headerLayoutid>0)
            {
                useHeaderLayout = true;
                headerLayout = (ViewGroup) inflate(getContext(),headerLayoutid, (ViewGroup)findViewById(R.id.headerLayout));
                getDataGridAdapter().setContentView(headerLayoutid);
            }


            setTextColor(a.getColor(R.styleable.DataGridView_textColor,getDataGridAdapter().getColor()));
            setTextSize(a.getInt(R.styleable.DataGridView_textSize,getDataGridAdapter().getTextSize()));

            setRowColor1(a.getColor(R.styleable.DataGridView_rowColor1,getRowColor1()));
            setRowColor2(a.getColor(R.styleable.DataGridView_rowColor2,getRowColor2()));
            setHeaderBackColor(a.getColor(R.styleable.DataGridView_headerBackColor,getHeaderBackColor()));
            setHeaderForeColor(a.getColor(R.styleable.DataGridView_headerForeColor,getHeaderForeColor()));
        }
        else
        {
            setShowHeader(showHeader);
            setShowFilter(showFilter);
            setTextColor(getDataGridAdapter().getColor());
            setTextSize(getDataGridAdapter().getTextSize());
        }





    }

    public DataGridAdapter getDataGridAdapter() {
        return dataGridAdapter;
    }

    public void setDataGridAdapter(DataGridAdapter dataGridAdapter) {
        this.dataGridAdapter = dataGridAdapter;
        dataGridAdapter.setOnAdapterDataLoad(this);
        //listView.setAdapter(getDataGridAdapter());
        dataGridAdapter.notifyDataSetChanged();
    }



    @Override
    public void onLoad(List data) {


        //region Manual Load
        if (!useHeaderLayout)
        {
            if (getDataGridAdapter().getData()==null)
            {
                headerLayout.removeAllViews();
                filterLayout.removeAllViews();
                verLayout.removeAllViews();
                return;
            }
            else

            {
                if ( getDataGridAdapter().getData().Parent ==null)
                {
                    headerLayout.removeAllViews();
                    filterLayout.removeAllViews();
                }
                verLayout.removeAllViews();

                if (getDataGridAdapter().getData().Columns.size() > 0 && headerLayout.getChildCount() ==0)
                {
                    headerLayout.addView(getDataGridAdapter().getHeaderView());

                    filterLayout.addView(getDataGridAdapter().getFilterView());
                }

                for (int i = 0 ; i < getDataGridAdapter().getCount();i++)
                {
                    View v = getDataGridAdapter().getView(i,verLayout.getChildAt(i),verLayout);
                    verLayout.addView(v);
                    v.getBackground().setColorFilter( (i%2 !=0 ) ? getRowColor2() : getRowColor1() , PorterDuff.Mode.MULTIPLY);

                }

                getDataGridAdapter().getData().Parent = this;
            }
        }
        //endregion
        //region Custom Load
        else
        {

            verLayout.removeAllViews();
            if (getDataGridAdapter().getData()==null)
            {
                filterLayout.removeAllViews();
            }
            else
            {
                if ( getDataGridAdapter().getData().Parent ==null)
                    setDataGridAdapterHeaderInfo();

                if (getDataGridAdapter().getData().Columns.size() > 0 && filterLayout.getChildCount() == 0)
                {
                    getDataGridAdapter().setHeaderItemSize((ViewGroup)headerLayout.getChildAt(0));
                    filterLayout.addView(getDataGridAdapter().getFilterView((ViewGroup) headerLayout.getChildAt(0)));
                }

                for (int i = 0 ; i < getDataGridAdapter().getCount();i++)
                {
                    View v = getDataGridAdapter().getView(i,verLayout.getChildAt(i),verLayout);
                    verLayout.addView(v);

                    v.getBackground().setColorFilter( (i%2 !=0 ) ? getRowColor2() : getRowColor1() , PorterDuff.Mode.MULTIPLY);
                }

                getDataGridAdapter().getData().Parent = this;

            }
        }
        //endregion

    }

    private void setDataGridAdapterHeaderInfo() {
        List<String> Columns = new ArrayList<>();
        List<String> Captions = new ArrayList<>();
        for (int i=0;i< ((ViewGroup)headerLayout.getChildAt(0)).getChildCount() ; i++)
        {
            View v = ((ViewGroup)headerLayout.getChildAt(0)).getChildAt(i);
            if (v instanceof DataGridTextView)
            {
                DataGridTextView gridTextView = (DataGridTextView) v;
                Columns.add(gridTextView.getFieldName());
                Captions.add(gridTextView.getText().toString());
                filterLayout.removeAllViews();
                verLayout.removeAllViews();

            }
        }

        getDataGridAdapter().setColumns(Columns);
        getDataGridAdapter().setCaptions(Captions);
    }


    //region Property

    //PRIVATE
    private boolean showHeader = true,showFilter = false;
    private int
             textSize
            ,textColor
            ,rowColor1=Color.WHITE
            ,rowColor2  = Color.parseColor("#E6FFF3")
            ,headerBackColor = Color.parseColor("#6d8aa1")
            ,headerForeColor = Color.WHITE;


    //GET

    public boolean isShowHeader() {
        return showHeader;
    }
    public boolean isShowFilter() {
        return showFilter;
    }
    public int getTextSize() {
        return textSize;
    }
    public int getTextColor() {
        return textColor;
    }
    public int getRowColor1() { return rowColor1; }
    public int getRowColor2() { return rowColor2; }
    public int getHeaderBackColor() { return headerBackColor; }
    public int getHeaderForeColor() { return headerForeColor; }


    //SET
    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
        headerLayout.setVisibility(showHeader ? VISIBLE : GONE);
    }
    public void setShowFilter(boolean showFilter) {
        this.showFilter = showFilter;
        filterLayout.setVisibility(showFilter ? VISIBLE : GONE);
    }
    public void setTextSize(int textSize) {
        this.textSize = textSize;
        if (getDataGridAdapter()!=null)
            getDataGridAdapter().setTextSize(textSize);
    }
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (getDataGridAdapter()!=null)
            getDataGridAdapter().setColor(textColor);
    }
    public void setRowColor1(int rowColor1) { this.rowColor1 = rowColor1; }
    public void setRowColor2(int rowColor2) { this.rowColor2 = rowColor2; }
    public void setHeaderBackColor(int headerBackColor) {
        this.headerBackColor = headerBackColor;
        headerLayout.setBackgroundColor(headerBackColor);
        filterLayout.setBackgroundColor(headerBackColor);
    }
    public void setHeaderForeColor(int headerForeColor) {
        this.headerForeColor = headerForeColor;
        getDataGridAdapter().setHeaderForeColor(headerForeColor);
    }

    //endregion


    int width = 0;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (width == 0 || width != this.getWidth())
        {
            width = this.getWidth();
            getDataGridAdapter().getData().Parent =null;
            getDataGridAdapter().setParentWidth(width);
            getDataGridAdapter().notifyDataSetChanged();
        }


    }
}
