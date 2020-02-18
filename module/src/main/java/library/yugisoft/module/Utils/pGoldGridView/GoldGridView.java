package library.yugisoft.module.Utils.pGoldGridView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import java.util.List;

import library.yugisoft.module.R;
import library.yugisoft.module.Utils.pSmartGridView.ISmartGridAdapterListener;
import library.yugisoft.module.Utils.pSmartGridView.SmartGridViewHolder;
import library.yugisoft.module.vList;

public class GoldGridView extends SmartGridViewHolder implements IGoldGridSettings
{
    private boolean isReadSettings = false;


    public GoldGridView(Context context) {
        this(context,null,0);
    }
    public GoldGridView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public GoldGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        isReadSettings = false;
        TypedArray typedArray = getTypedArray(R.styleable.GoldGridView);
        if (typedArray!= null)
        {
            //setDetailViewID(typedArray.getResourceId(R.styleable.SmartGridView_detailViewID,0));
            //setPageViewCount(typedArray.getInteger(R.styleable.SmartGridView_pageViewCount,30));
            //setPageMoreCount(typedArray.getInteger(R.styleable.SmartGridView_pageMoreCount,30));
            setIsShowDivider(typedArray.getBoolean(R.styleable.GoldGridView_showDivider,true));
            setNumColumns(typedArray.getInteger(R.styleable.SmartGridView_numColumns,1));
            setRowBackColor(typedArray.getColor(R.styleable.SmartGridView_rowBackColor, Color.WHITE));
            setSwipeEnable(typedArray.getBoolean(R.styleable.GoldGridView_swipeEnable,false));
        }
        swipe.setOnRefreshListener(() -> notifyDataSetChanged());
        isReadSettings = true;
    }

    //region Settings
    private int numColumns = 1;

    public void setNumColumns(int numColumns) {
        if (numColumns<=0)
            numColumns = 1;

        this.numColumns = numColumns;
    }

    private boolean isvertical = true;
    public void setIsVertical(boolean isvertical) {
        if (this.isvertical != isvertical)
        {
            this.isvertical = isvertical;
            notifyDataSetChanged();
        }
        this.isvertical = isvertical;
    }

    private int horizantalLayoutID = R.layout.view_smart_grid_row_horizantal;
    public void setHorizantalLayoutID(int horizantalLayoutID) {
        this.horizantalLayoutID = horizantalLayoutID;
        if (!isVertical())
        notifyDataSetChanged();
    }

    private int verticalLayoutID = R.layout.view_smart_grid_row;
    public void setVerticalLayoutID(int verticalLayoutID) {
        this.verticalLayoutID = verticalLayoutID;
        if (isVertical())
            notifyDataSetChanged();
    }

    private boolean isshowDivider = true;
    public void setIsShowDivider(boolean isshowDivider) {
        this.isshowDivider = isshowDivider;
        notifyDataSetChanged();
    }

    private int rowBackColor = Color.TRANSPARENT;
    public void setRowBackColor(int rowBackColor) {
        this.rowBackColor = rowBackColor;
        notifyDataSetChanged();
    }

    private vList list = new vList();

    public void setData(List list) {
        this.list.list = list;
        notifyDataSetChanged();
    }
    public void setData(vList list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private ISmartGridAdapterListener adapterListener;
    public void setAdapterListener(ISmartGridAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public void setSwipeEnable(boolean enable)
    {
        swipe.setEnabled(enable);
    }

    public boolean isSwipeEnabled()
    {
        return swipe.isEnabled();
    }

    //endregion

    //region Interface - IGoldGridSettings
    @Override
    public boolean isVertical() {
        return isvertical;
    }

    @Override
    public int getHorizantalLayoutID() {
        return horizantalLayoutID;
    }

    @Override
    public int getVerticalLayoutID() {
        return verticalLayoutID;
    }

    @Override
    public boolean isShowDivider() {
        return isshowDivider;
    }

    @Override
    public int getRowBackColor() {
        return rowBackColor;
    }

    @Override
    public List getList() {
        return list.list;
    }

    @Override
    public vList getData() {
        return list;
    }

    @Override
    public ViewGroup getViewLayout() {
        return bar_detail;
    }


    @Override
    public void setRefreshing(boolean refreshing) {
        swipe.setRefreshing(refreshing);
    }

    @Override
    public int getNumColumns() {
        return numColumns;
    }

    @Override
    public void onRemoveAllViews() {
        bar_header.removeAllViews();
        bar_detail.removeAllViews();
        bar_footer.removeAllViews();
    }

    @Override
    public ISmartGridAdapterListener getAdapterListener() {
        return adapterListener == null ? new ISmartGridAdapterListener() {} : adapterListener;
    }

    //endregion

    //region Adapter Settings

    private GoldGridAdapter adapter;
    public GoldGridAdapter getAdapter() {
        if (adapter == null )
            adapter = new GoldGridAdapter(this);

        return adapter;
    }

    public void setAdapter(GoldGridAdapter adapter) {
        this.adapter = adapter;
    }


    public void notifyDataSetChanged()
    {
        notifyDataSetChanged(false);
    }
    public void notifyDataSetChanged(boolean moreData)
    {
        if (!isReadSettings)
            return;
        getAdapter().notifyDataSetChanged(moreData);
    }

    //endregion
}
