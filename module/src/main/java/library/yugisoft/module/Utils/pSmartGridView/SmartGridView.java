package library.yugisoft.module.Utils.pSmartGridView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import library.yugisoft.module.R;
import library.yugisoft.module.Utils.CustomUtil;
import library.yugisoft.module.vList;

public class SmartGridView extends SmartGridViewHolder implements ISmartGridAdapterListener {


    public boolean isVertical()
    {
        return  scrollViewv != null;
    }

    //region CONSTR
    public SmartGridView(Context context) {
        super(context);
    }
    public SmartGridView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }
    public SmartGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //endregion

    //region Declare

    SmartGridAdapter smartGridAdapter;
    public void setSmartGridAdapter(SmartGridAdapter smartGridAdapter) {
        this.smartGridAdapter = smartGridAdapter;
        this.smartGridAdapter.setVertical(isVertical());

    }
    public SmartGridAdapter getSmartGridAdapter() {
        return smartGridAdapter;
    }

    private ISmartGridViewListener viewListener;
    public ISmartGridViewListener getViewListener() {
        return viewListener;
    }

    public void setViewListener(ISmartGridViewListener viewListener) {
        this.viewListener = viewListener;
    }

    //region Data

    public void setData(vList data) {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setData(data);
    }
    public void setData(List data) {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setData(data);
    }
    public vList getData() {
        if (getSmartGridAdapter() != null)
            return getSmartGridAdapter().getData();
        return null;
    }

    //endregion

    //region detailViewID


    public void setDetailViewID(int detailViewID) {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setDetailViewID(detailViewID);
    }
    public int getDetailViewID() {
        if (getSmartGridAdapter() != null)
            return getSmartGridAdapter().getDetailViewID();
        return 0;
    }

    //endregion

    //region selectedText

    public TextView getSelectedGroup() {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().getSelectedGroup();
        return null;
    }
    public void setSelectedGroup(TextView selectedGroup)
    {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setSelectedGroup(selectedGroup);
    }
    //endregion

    public void setPageViewCount(int pageViewCount) {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setPageViewCount(pageViewCount);
    }
    public int getPageViewCount() {
        if (getSmartGridAdapter() != null)
            return getSmartGridAdapter().getPageViewCount();
        return 0;
    }
    public void setPageMoreCount(int pageMoreCount) {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setPageMoreCount(pageMoreCount);
    }
    public int getPageMoreCount() {
        if (getSmartGridAdapter() != null)
            return   getSmartGridAdapter().getPageMoreCount();
        return 0;
    }

    public  int getLastViewIndex()
    {
        if (getSmartGridAdapter()!= null)
            return  getSmartGridAdapter().getLastViewIndex();
        return  -1;
    }
    //endregion

    private Drawable drawableEmpty;

    public void setNumColumns(int numColumns) {
        if (getSmartGridAdapter() != null)
            getSmartGridAdapter().setNumColumns(numColumns);
    }
    public int getNumColumns()
    {
        if (getSmartGridAdapter() != null)
            return  getSmartGridAdapter().getNumColumns();
        return 0;
    }

    @Override
    public void init(Context context,  AttributeSet attrs, int defStyle) {
        super.init(context, attrs, defStyle);

        setSmartGridAdapter(new SmartGridAdapter());

        getSmartGridAdapter().setSmartGridView(this);
        getSmartGridAdapter().setAdapterListener(this);

        swipe.setOnRefreshListener(()-> {
            if (getSmartGridAdapter() != null)
                getSmartGridAdapter().notifyDataSetChanged();
        } );


        TypedArray typedArray = getTypedArray(R.styleable.SmartGridView);
        if (typedArray!= null)
        {
            setDetailViewID(typedArray.getResourceId(R.styleable.SmartGridView_detailViewID,0));
            setPageViewCount(typedArray.getInteger(R.styleable.SmartGridView_pageViewCount,30));
            setPageMoreCount(typedArray.getInteger(R.styleable.SmartGridView_pageMoreCount,30));
            setNumColumns(typedArray.getInteger(R.styleable.SmartGridView_numColumns,1));
        }


    }

    public void notifyDataSetChanged()
    {
        if(getSmartGridAdapter() != null)
            getSmartGridAdapter().notifyDataSetChanged();
    }
    public void notifyDataSetChanged(boolean moreData)
    {
        if(getSmartGridAdapter() != null)
            getSmartGridAdapter().notifyDataSetChanged(moreData);
    }

    //region  LoadData

    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
    {
        if (getSmartGridAdapter() != null)
        {
            View view = ScrollChild();
            int diff = (view.getBottom() - (scrollViewgetHeight() + scrollViewgetScrollY()));
            if (diff == 0)
            {
                if (getLastViewIndex() < getData().size())
                {

                    setScrollChangeListener(null);
                    setPageViewCount(getPageViewCount()+getPageMoreCount());
                    notifyDataSetChanged(true);
                }
                if (getViewListener() != null)
                    getViewListener().onScrollToMoreLoad();
            }
        }
    }

    public int scrollViewgetHeight() {
        if (scrollViewv != null)
            return  scrollViewv.getHeight();
        if (scrollViewh != null)
            return  scrollViewh.getHeight();
        return 0;
    }
    public int scrollViewgetScrollY() {
        if (scrollViewv != null)
            return  scrollViewv.getScrollY();
        if (scrollViewh != null)
            return  scrollViewh.getScrollY();
        return 0;
    }
    public void setScrollChangeListener(OnScrollChangeListener o) {
       if (scrollViewv != null)
           scrollViewv.setOnScrollChangeListener(o);
        if (scrollViewh != null)
            scrollViewh.setOnScrollChangeListener(o);
    }
    public View ScrollChild() {
        if (scrollViewv != null)
            return  (View) scrollViewv.getChildAt(scrollViewv.getChildCount() - 1);
        if (scrollViewh != null)
            return  (View) scrollViewh.getChildAt(scrollViewh.getChildCount() - 1);

        return  null;
    }
    public void scrollViewsmoothScrollTo(int i, int top) {
        if (scrollViewv != null)
            scrollViewv.smoothScrollTo(i, top);
        if (scrollViewh != null)
            scrollViewh.smoothScrollTo(i, top);
    }
    public View scrollView() {
        if (scrollViewv != null)
            return scrollViewv;
        return scrollViewh;
    }
    //endregion


    private OnSmartItemClickListener onSmartItemClickListener;


    /*Selected Item Event*/
    public void onItemClick(View view, Object item)
    {

        if (item instanceof ISmartViewItem)
        {
            ((ISmartViewItem)item).onItemClick(view);
        }
        else
        {
            View.OnClickListener onClickListener = CustomUtil.getOnClickListener(view);
            if (onClickListener != null)
                onClickListener.onClick(view);
        }

        if (getOnSmartItemClickListener()!=null)
            getOnSmartItemClickListener().onItemClick(view,item);


    }

    /*İstenilen Grup Başlığına Scrol Yapar*/
    public void scrollTo(String s) {
        View view = bar_detail.findViewWithTag(s);
        scrollViewsmoothScrollTo(0, (int)((View)view.getParent()).getTop());
    }




    @Override
    public void onFinish()
    {
        if (bar_detail.getChildCount() == 0 && getDrawableEmpty() != null)
            scrollView().setBackground(getBackground());
        else if ( scrollView().getBackground() != null &&  scrollView().getBackground().equals(getDrawableEmpty()))
            scrollView().setBackground(null);

        swipe.setRefreshing(false);
        scrollView().setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> onScrollChange(v,scrollX,scrollY,oldScrollX,oldScrollY));
    }

    @Override
    public void onStart() {
        swipe.setRefreshing(true);
    }


    @Override
    public void onReadyView(View view) {
        bar_detail.addView(view);
    }

    @Override
    public void onAddHeader(View view) {
        bar_header.addView(view);
    }

    @Override
    public void onRemoveAllViews() {
        bar_header.removeAllViews();
        bar_detail.removeAllViews();
        bar_footer.removeAllViews();
    }


    public Drawable getDrawableEmpty() {
        return drawableEmpty;
    }

    public void setDrawableEmpty(Drawable drawableEmpty) {
        this.drawableEmpty = drawableEmpty;
    }

    public OnSmartItemClickListener getOnSmartItemClickListener() {
        return onSmartItemClickListener;
    }

    public void setOnSmartItemClickListener(OnSmartItemClickListener onSmartItemClickListener) {
        this.onSmartItemClickListener = onSmartItemClickListener;
    }
}
