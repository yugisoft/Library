package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.List;

import library.yugisoft.module.R;


public class BindingGridView extends GridView
{
    //region Constructor
    public BindingGridView(Context context) {
        this(context, null, 0);
    }
    public BindingGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BindingGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BindingGridView, defStyleAttr, 0);
        if (a!=null)
        {
            setDetailViewID(a.getResourceId(R.styleable.BindingGridView_detailViewID,R.layout.list_deleteable_item));
            setEmptyViewID(a.getResourceId(R.styleable.BindingGridView_emptyViewID,R.layout.list_item_empty));
            setEmtyParentID(a.getResourceId(R.styleable.BindingGridView_emtyParentID,R.id.listParent));
            setEmptyRowCount(a.getInt(R.styleable.BindingGridView_emptyRowCount,0));
            setShowDevider(a.getBoolean(R.styleable.BindingGridView_showDivider,true));
        }

        adapter = new BindingItemAdapter(getContext(),getDetailViewID()).setEmptyViewID(getEmptyViewID()).setEmtyParentID(getEmtyParentID()).setEmptyRowCount(getEmptyRowCount()).setShowDevider(isShowDevider());
        setAdapter(adapter);
    }
    //endregion

    //region Veriables

    private BindingItemAdapter adapter;

    private int detailViewID,emptyViewID = R.layout.list_item_empty,emtyParentID = R.id.listParent;

    private int emptyRowCount = 0;

    private boolean showDevider = true;



    //endregion

    //region Setter

    public BindingGridView setDetailViewID(int detailViewID) {
        this.detailViewID = detailViewID;
        if (getAdapter()!=null) getAdapter().setDetailViewID(detailViewID);
        return this;
    }

    public BindingGridView setEmptyViewID(int emptyViewID) {
        this.emptyViewID = emptyViewID;
        if (getAdapter()!=null) getAdapter().setEmptyViewID(emptyViewID);
        return this;
    }

    public BindingGridView setEmtyParentID(int emtyParentID) {
        this.emtyParentID = emtyParentID;
        if (getAdapter()!=null) getAdapter().setEmtyParentID(emtyParentID);
        return this;
    }

    public BindingGridView setEmptyRowCount(int emptyRowCount) {
        this.emptyRowCount = emptyRowCount;
        if (getAdapter()!=null) getAdapter().setEmptyRowCount(emptyRowCount);
        return this;
    }

    public BindingGridView setShowDevider(boolean showDevider) {
        this.showDevider = showDevider;
        if (getAdapter()!=null) getAdapter().setShowDevider(showDevider);
        return this;
    }

    public BindingGridView setAdapter(BindingItemAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
        return this;
    }

    //endregion

    //region Getter

    public int getDetailViewID() {
        return detailViewID;
    }

    public int getEmptyViewID() {
        return emptyViewID;
    }

    public int getEmtyParentID() {
        return emtyParentID;
    }

    public int getEmptyRowCount() {
        return emptyRowCount;
    }

    public boolean isShowDevider() {
        return showDevider;
    }

    @Override
    public BindingItemAdapter getAdapter() {
        return adapter;
    }

    public void setData(List list) {
        getAdapter().setList(list);
    }

    public List getData() {
        return getAdapter()!=null ? getAdapter().getList() : null;
    }

    //endregion
}
