package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.Hashtable;
import java.util.List;

import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

public class BindingGridView extends GridView
{
    private View layoutController;
    private boolean autoHeightOnChild;
    private int heightRow = 0;
    private boolean rowColors;
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
            setDetailViewID(a.getResourceId(R.styleable.BindingGridView_detailViewID,0));
            setEmptyViewID(a.getResourceId(R.styleable.BindingGridView_emptyViewID,R.layout.list_item_empty));
            setEmtyParentID(a.getResourceId(R.styleable.BindingGridView_emtyParentID,R.id.listParent));
            setEmptyRowCount(a.getInt(R.styleable.BindingGridView_emptyRowCount,0));
            setShowDevider(a.getBoolean(R.styleable.BindingGridView_showDivider,true));
            setAutoHeightOnChild(a.getBoolean(R.styleable.BindingGridView_autoHeightOnChild,false));
            setRowColors(a.getBoolean(R.styleable.BindingGridView_rowColors,false));
        }

        adapter = new BindingItemAdapter(getContext(),getDetailViewID());
        adapter.setEmptyViewID(getEmptyViewID());
        adapter.setEmtyParentID(getEmtyParentID());
        adapter.setEmptyRowCount(getEmptyRowCount());
        adapter.setShowDevider(isShowDevider());
        adapter.setRowColors(isRowColors());
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

        if (adapter!=null) {
            adapter.setOnAdapterDataLoad(p ->
            {
                heightRow = 0;
                AutoHeightOnChildControl();
            });
            adapter.setOnViewDrawing(onViewDrawings);
        }
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

    public <Ret> List<Ret> getData() {
        return getAdapter()!=null ? getAdapter().getList() : null;
    }

    public BindingGridView setLayoutController(View layoutController) {
        this.layoutController = layoutController;
        if (getAdapter()!=null)
            getAdapter().setLayoutController(layoutController);
        return  this;
    }

    public View getLayoutController() {
        return layoutController;
    }

    //endregion

    public void AutoHeightOnChildControl() {

        try {
            if (isAutoHeightOnChild() && getAdapter().getFirstView().getHeight()  > heightRow )
            {
                new Handler().postDelayed(()->
                {
                    heightRow = getAdapter().getFirstView().getHeight();
                    BindingGridView.this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) yugi.convertDpToPixel(yugi.convertPixelToDp(heightRow,yugi.activity)* getCount() , yugi.activity)));
                },500);
            }
        }
        catch (Exception ex)
        {
            if (isAutoHeightOnChild())
                new Handler().postDelayed(()->AutoHeightOnChildControl(),500);
        }
    }

    public boolean isAutoHeightOnChild() {
        return autoHeightOnChild;
    }

    public void setAutoHeightOnChild(boolean autoHeightOnChild) {
        this.autoHeightOnChild = autoHeightOnChild;
    }

    public boolean isRowColors() {
        return rowColors;
    }

    public BindingGridView setRowColors(boolean rowColors) {
        this.rowColors = rowColors;
        if (getAdapter()!=null)
            getAdapter().setRowColors(isRowColors());
        return this;
    }

    private Hashtable<String, CustomBindingAdapter.OnViewDrawing> onViewDrawings = new Hashtable<>();

    public BindingGridView addOnViewDrawing(String name , CustomBindingAdapter.OnViewDrawing onViewDrawing) {
        onViewDrawings.put(name,onViewDrawing);
        setAdapter(getAdapter());
        return  this;
    }

    public CustomBindingAdapter.OnViewDrawing getOnViewDrawing(String name) {
        return onViewDrawings.get(name);
    }

    public void RemoveOnViewDrawing(String name)
    {
        onViewDrawings.remove(name);
        setAdapter(getAdapter());
    }

    public void setOnViewDrawing(Hashtable<String, CustomBindingAdapter.OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
        setAdapter(getAdapter());
    }


    private OnRowDrawing onRowDrawing;

    public OnRowDrawing getOnRowDrawing() {
        return onRowDrawing;
    }

    public void setOnRowDrawing(OnRowDrawing onRowDrawing) {
        this.onRowDrawing = onRowDrawing;
        setAdapter(getAdapter());
    }

    public interface OnRowDrawing {
        void onDraw(int index,View view,Object item);
    }

}
