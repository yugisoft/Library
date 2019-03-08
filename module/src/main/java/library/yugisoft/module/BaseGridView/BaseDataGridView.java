package library.yugisoft.module.BaseGridView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

import library.yugisoft.module.INTERFACES;
import library.yugisoft.module.R;

public class BaseDataGridView extends LinearLayout implements INTERFACES.OnAdapterDataLoad {

    private LinearLayout verLayout, filterLayout,headerLayout;

    public BaseDataGridView(Context context)
    {
        this(context,null,0);
    }
    public BaseDataGridView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs,0);
    }
    public BaseDataGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.view_datagridview, this);
        verLayout = (LinearLayout) findViewById(R.id.layout);
        headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
        filterLayout = (LinearLayout) findViewById(R.id.filterLayout);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BaseDataGridView, defStyleAttr, 0);
        if (a!=null)
        {
            headerLayoutID = a.getResourceId(R.styleable.BaseDataGridView_headerLayout, 0);
            detailLayoutID = a.getResourceId(R.styleable.BaseDataGridView_detailLayout, 0);
        }



    }

    int headerLayoutID,detailLayoutID;



    private IGridAdapter gridAdapter;

    public IGridAdapter getGridAdapter() {
        return gridAdapter;
    }

    public void setAdapter(IGridAdapter gridAdapter) {
        this.gridAdapter = gridAdapter;

        gridAdapter.setOnAdapterDataLoad(this);
        gridAdapter.setHeaderLayout(headerLayout);
        gridAdapter.setHedaerViewID(headerLayoutID);
        gridAdapter.setContentView(detailLayoutID);
    }

    @Override
    public void onLoad(List data)
    {
           verLayout.removeAllViews();
           headerLayout.removeAllViews();

           for (int i = 0 ; i< getGridAdapter().getCount() ; i++)
           {
               verLayout.addView(getGridAdapter().getView(i,null,this));
           }
    }
}
