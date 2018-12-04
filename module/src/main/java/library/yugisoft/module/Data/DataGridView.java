package library.yugisoft.module.Data;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import library.yugisoft.module.DataTable;
import library.yugisoft.module.DateTextView;
import library.yugisoft.module.INTERFACES;
import library.yugisoft.module.R;

public class DataGridView extends LinearLayout implements INTERFACES.OnAdapterDataLoad {

    //region MORE

    private BaseDataGridAdapter baseDataGridAdapter;
    private DataGridAdapter2 dataGridAdapter;
    AttributeSet attrs;
    int defStyleAttr;

    public DataGridView(Context context) {
        this(context, null, 0);
    }

    public DataGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        this.defStyleAttr = defStyleAttr;
        init();
    }

    boolean useHeaderLayout = false;
    int headerLayoutid = 0;
    boolean isLoad = false;
    private LinearLayout verLayout, filterLayout;
    private LinearLayout headerLayout;

    private void init() {

        isLoad = false;
        inflate(getContext(), R.layout.view_datagridview, this);

        verLayout = (LinearLayout) findViewById(R.id.layout);

        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        filterLayout = (LinearLayout) findViewById(R.id.filterLayout);
        if (dataGridAdapter == null)
            setDataGridAdapter(new DataGridAdapter2(getContext(), this));

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DataGridView, defStyleAttr, 0);
        if (a != null) {
            setShowHeader(a.getBoolean(R.styleable.DataGridView_showHeader, showHeader));
            setShowFilter(a.getBoolean(R.styleable.DataGridView_showFilter, showFilter));

            headerLayoutid = a.getResourceId(R.styleable.DataGridView_headerLayout, 0);
            if (headerLayoutid > 0) {
                useHeaderLayout = true;
                headerLayout = (LinearLayout) inflate(getContext(), headerLayoutid, (ViewGroup) findViewById(R.id.headerLayout));
                getDataGridAdapter().setContentView(headerLayoutid);
                setDataGridAdapterHeaderInfo();
            }

            setRowColor((a.getResourceId(R.styleable.DataGridView_rowColor,0)));
            setTextColor(a.getColor(R.styleable.DataGridView_textColor, getDataGridAdapter().getColor()));
            setTextSize(a.getInt(R.styleable.DataGridView_textSize, getDataGridAdapter().getTextSize()));

            if (rowColor!=0)
            {
                try
                {
                    try {
                        headerLayout.setBackground(getContext().getResources().getDrawable(rowColor));
                        filterLayout.setBackground(getContext().getResources().getDrawable(rowColor));
                    }
                    catch (Exception ex){
                        try {
                            headerLayout.setBackgroundColor(rowColor);
                            filterLayout.setBackgroundColor(rowColor);
                        }
                        catch (Exception e){}
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            setRowColor1(a.getColor(R.styleable.DataGridView_rowColor1, getRowColor1()));
            setRowColor2(a.getColor(R.styleable.DataGridView_rowColor2, getRowColor2()));
            setHeaderBackColor(a.getColor(R.styleable.DataGridView_headerBackColor, getHeaderBackColor()));
            setHeaderForeColor(a.getColor(R.styleable.DataGridView_headerForeColor, getHeaderForeColor()));
        } else {
            setShowHeader(showHeader);
            setShowFilter(showFilter);
            setTextColor(getDataGridAdapter().getColor());
            setTextSize(getDataGridAdapter().getTextSize());
        }
        isLoad = true;


    }

    public DataGridAdapter2 getDataGridAdapter() {
        return dataGridAdapter;
    }

    private void setDataGridAdapter(DataGridAdapter2 dataGridAdapter) {
        this.dataGridAdapter = dataGridAdapter;
        this.dataGridAdapter.setDataGridValueChanged(gridValueChanged);
        dataGridAdapter.setOnAdapterDataLoad(this);
        dataGridAdapter.notifyDataSetChanged();
    }

    private HashMap<String, DataGridTextView.DataGridValueChanged> onChangeMap = new HashMap<>();

    public void addChangeListener(String fieldName, DataGridTextView.DataGridValueChanged onGridValueChanged) {
        onChangeMap.remove(fieldName);
        onChangeMap.put(fieldName, onGridValueChanged);
    }

    public void removeChangeListener(String fieldName) {
        onChangeMap.remove(fieldName);
    }

    private DataGridTextView.DataGridValueChanged gridValueChanged = new DataGridTextView.DataGridValueChanged() {
        @Override
        public String onChange(int row, int cell, DataGridTextView textView, String value) {


            try {
                return onChangeMap.get(textView.getFieldName()).onChange(row, cell, textView, value);
            } catch (Exception ex) {
                return value;
            }
        }


    };


    public void onLoadd(List data) {


        if (isLoad) {
            //region Manual Load
            if (!useHeaderLayout) {
                if (getDataGridAdapter().getData() == null) {
                    headerLayout.removeAllViews();
                    filterLayout.removeAllViews();
                    verLayout.removeAllViews();
                    return;
                } else

                {
                    if (getDataGridAdapter().getData().Parent == null) {
                        headerLayout.removeAllViews();
                        filterLayout.removeAllViews();
                    }
                    verLayout.removeAllViews();

                    if (getDataGridAdapter().getData().Columns.size() > 0 && headerLayout.getChildCount() == 0) {
                        //  headerLayout.addView(getDataGridAdapter().getHeaderView());

                        //  filterLayout.addView(getDataGridAdapter().getFilterView());
                    }

                    for (int i = 0; i < getDataGridAdapter().getCount(); i++) {
                        View v = getDataGridAdapter().getView(i, verLayout.getChildAt(i), verLayout);
                        verLayout.addView(v);
                        v.getBackground().setColorFilter((i % 2 != 0) ? getRowColor2() : getRowColor1(), PorterDuff.Mode.MULTIPLY);

                    }

                    getDataGridAdapter().getData().Parent = this;
                }
            }
            //endregion
            //region Custom Load
            else {

                verLayout.removeAllViews();
                if (getDataGridAdapter().getData() == null) {
                    filterLayout.removeAllViews();
                } else {
                    //  setDataGridAdapterHeaderInfo();
                    //  if (getDataGridAdapter().getData().Columns.size() > 0 && filterLayout.getChildCount() == 0)
                    //  {
                    //      getDataGridAdapter().setHeaderItemSize((ViewGroup)headerLayout.getChildAt(0));
                    //      filterLayout.addView(getDataGridAdapter().getFilterView((ViewGroup) headerLayout.getChildAt(0)));
                    //  }
//
                    //  for (int i = 0 ; i < getDataGridAdapter().getCount();i++)
                    //  {
                    //      View v = getDataGridAdapter().getView(i,verLayout.getChildAt(i),verLayout);
                    //      verLayout.addView(v);
//
                    //      v.getBackground().setColorFilter( (i%2 !=0 ) ? getRowColor2() : getRowColor1() , PorterDuff.Mode.MULTIPLY);
                    //  }
//
                    //  getDataGridAdapter().getData().Parent = this;

                }
            }
            //endregion
        }

    }

    List<DataGridTextView> textViews = new ArrayList<>();
    List<String> Columns = new ArrayList<>();
    List<String> Captions = new ArrayList<>();

    private void setDataGridAdapterHeaderInfo() {
        textViews = new ArrayList<>();
        Columns = new ArrayList<>();
        Captions = new ArrayList<>();

        for (int i = 0; i < ((ViewGroup) headerLayout.getChildAt(0)).getChildCount(); i++) {
            View v = ((ViewGroup) headerLayout.getChildAt(0)).getChildAt(i);
            if (v instanceof DataGridTextView) {
                DataGridTextView gridTextView = (DataGridTextView) v;
                Columns.add(gridTextView.getFieldName());
                Captions.add(gridTextView.getText().toString());
                filterLayout.removeAllViews();
                verLayout.removeAllViews();
                textViews.add(gridTextView);
            }
        }
        getDataGridAdapter().setTextViews(textViews);
        getDataGridAdapter().setColumns(Columns);
        getDataGridAdapter().setCaptions(Captions);

    }


    //region Property

    //PRIVATE
    private boolean showHeader = true, showFilter = false;
    private int
            textSize, textColor, rowColor1 = Color.WHITE, rowColor2 = Color.parseColor("#E6FFF3"), headerBackColor = Color.parseColor("#6d8aa1"), headerForeColor = Color.WHITE;
    private int rowColor = 0;


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

    public int getRowColor1() {
        return rowColor1;
    }

    public int getRowColor2() {
        return rowColor2;
    }

    public int getHeaderBackColor() {
        return headerBackColor;
    }

    public int getHeaderForeColor() {
        return headerForeColor;
    }


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
        if (getDataGridAdapter() != null)
            getDataGridAdapter().setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if (getDataGridAdapter() != null)
            getDataGridAdapter().setColor(textColor);
    }

    public void setRowColor1(int rowColor1) {
        this.rowColor1 = rowColor1;
    }

    public void setRowColor2(int rowColor2) {
        this.rowColor2 = rowColor2;
    }

    public void setHeaderBackColor(int headerBackColor) {
        this.headerBackColor = headerBackColor;

        if (rowColor!=0)
        {
            try {
                headerLayout.setBackground(getContext().getResources().getDrawable(rowColor));
                filterLayout.setBackground(getContext().getResources().getDrawable(rowColor));
            }
            catch (Exception ex){
                try {
                    headerLayout.setBackgroundColor(rowColor);
                    filterLayout.setBackgroundColor(rowColor);
                }
                catch (Exception e){}
            }

        }
        else
        {
            headerLayout.setBackgroundColor(headerBackColor);
            filterLayout.setBackgroundColor(headerBackColor);
            //filterLayout.getBackground().setColorFilter(headerBackColor, PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setHeaderForeColor(int headerForeColor) {
        this.headerForeColor = headerForeColor;
        getDataGridAdapter().setHeaderForeColor(headerForeColor);
    }

    //endregion


    public int width = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (width == 0 || width != this.getWidth()) {
            width = this.getWidth();
            try {
                getDataGridAdapter().getData().Parent = null;
            } catch (Exception ex) {
            }
            // try{ getDataGridAdapter().setParentWidth(width);}catch (Exception ex){}
            try {
                getDataGridAdapter().notifyDataSetChanged();
            } catch (Exception ex) {
            }
        }


    }


    public void setData(DataTable data) {
        width = 0;
        // getDataGridAdapter().setParentWidth(0);
        getDataGridAdapter().setData(data);
        this.requestLayout();
    }

    public DataTable getData() {
        return getDataGridAdapter().getData();
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return getDataGridAdapter().getOnItemClickListener();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        getDataGridAdapter().setOnItemClickListener(onItemClickListener);
    }


    private SwipeRefreshLayout swipeRefreshLayout;

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;

    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((ScrollView) findViewById(R.id.gridVerticalScroll)).setOnScrollChangeListener(new OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    swipeRefreshLayout.setEnabled(scrollY == 0);
                }
            });
        }
    }
    //endregion


    @Override
    public void onLoad(List data) {


        if (baseDataGridAdapter!=null)
        {
            verLayout.removeAllViews();
            for (int i = 0; i < getBaseDataGridAdapter().getCount(); i++) {
                View v = getBaseDataGridAdapter().getView(i, null,verLayout);

                v.getBackground().setColorFilter((i % 2 != 0) ? getRowColor2() : getRowColor1(), PorterDuff.Mode.MULTIPLY);
            }
        }
        else
        {
            if (getDataGridAdapter().getData() == null) {
                if (headerLayoutid == 0)
                    headerLayout.removeAllViews();
                filterLayout.removeAllViews();
                verLayout.removeAllViews();
                return;
            } else {
                if (getDataGridAdapter().getData().Parent == null)
                {
                    getDataGridAdapter().calculateColumnHeight();
                    if (headerLayoutid == 0)
                        headerLayout.removeAllViews();
                    filterLayout.removeAllViews();
                }
                verLayout.removeAllViews();

                if (getDataGridAdapter().getData().Columns.size() > 0) {
                    if (headerLayoutid == 0)
                        getDataGridAdapter().getHeaderView(headerLayout);
                    else
                        getDataGridAdapter().UpdateHeaderView((LinearLayout) headerLayout.getChildAt(0));
                    getDataGridAdapter().getFilterView(filterLayout);

                }

                for (int i = 0; i < getDataGridAdapter().getCount(); i++) {
                    View v = getDataGridAdapter().getView(i, verLayout);
                    // if (rowColor == 0)
                    v.getBackground().setColorFilter((i % 2 != 0) ? getRowColor2() : getRowColor1(), PorterDuff.Mode.MULTIPLY);
                }
                getDataGridAdapter().getData().Parent = this;
            }
        }

    }

    public int getRowColor() {
        return rowColor;
    }

    public void setRowColor(int rowColor) {
        this.rowColor = rowColor;
        getDataGridAdapter().setRowColor(rowColor);
    }

    public BaseDataGridAdapter getBaseDataGridAdapter() {
        return baseDataGridAdapter;
    }

    public void setBaseDataGridAdapter(BaseDataGridAdapter baseDataGridAdapter) {
        this.baseDataGridAdapter = baseDataGridAdapter;
        baseDataGridAdapter.setDataGridValueChanged(gridValueChanged);
        baseDataGridAdapter.setOnAdapterDataLoad(this::onLoad);
        onLoad(null);

    }
}
