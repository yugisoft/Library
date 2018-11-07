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

    private LinearLayout verLayout,headerLayout,filterLayout;
    //private ListView listView;
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

            setTextColor(a.getColor(R.styleable.DataGridView_textColor,getDataGridAdapter().getColor()));
            setTextSize(a.getInt(R.styleable.DataGridView_textSize,getDataGridAdapter().getTextSize()));
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
                if (i%2 ==0 )
                    v.getBackground().setColorFilter(Color.parseColor("#E6FFF3"), PorterDuff.Mode.MULTIPLY);
            }

            getDataGridAdapter().getData().Parent = this;
        }

    }


    private boolean showHeader = true,showFilter = false;
    private int textSize,textColor;

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
        headerLayout.setVisibility(showHeader ? VISIBLE : GONE);
    }

    public boolean isShowFilter() {
        return showFilter;
    }

    public void setShowFilter(boolean showFilter) {
        this.showFilter = showFilter;
        filterLayout.setVisibility(showFilter ? VISIBLE : GONE);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        if (getDataGridAdapter()!=null)
            getDataGridAdapter().setTextSize(textSize);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (getDataGridAdapter()!=null)
            getDataGridAdapter().setColor(textColor);
    }
}
