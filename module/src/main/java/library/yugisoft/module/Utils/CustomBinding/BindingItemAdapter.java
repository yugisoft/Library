package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;


import java.util.Hashtable;

import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

public class BindingItemAdapter<T> extends ItemAdapter<T> {


    private View layoutController,firstView;
    private boolean rowColors;
    private boolean emptyRowWithDetailView;

    public BindingItemAdapter(int id) {

        this(yugi.activity,id);
    }

    public BindingItemAdapter(Context context, int id) {
        super(context);
        super.setContentView(id);
        contentViewID = id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (getDetailViewID()>0)
        {
            ViewGroup layout  = null,parent = null;
            CustomBindingAdapter adapter = null;
            if (view == null)
            {
                layout =(ViewGroup) getLayoutInflater().inflate(getEmptyViewID(),null);
                if (layout != null)
                    parent = layout.findViewById(getEmtyParentID());
                adapter = new CustomBindingAdapter(context, contentViewID).setOnViewDrawings(onViewDrawings).setOnRowDrawing(getOnRowDrawing()).setRow(i);

                if (getLayoutController() != null)
                    adapter.setLayoutController(getLayoutController());

                if ((super.getCount() > i) || isEmptyRowWithDetailView())
                {
                    if (parent!= null && isShowDevider())
                    {
                        parent.addView(adapter.getBindingView());
                        view = layout;
                    }
                    else
                    {
                        view = adapter.getBindingView();
                    }
                }
                else
                {
                    view = layout;
                }

            }
            else
                adapter = new CustomBindingAdapter(context, view).setOnViewDrawings(onViewDrawings).setOnRowDrawing(getOnRowDrawing()).setRow(i);

            if (super.getCount() > i)
                adapter.bind(getList().get(i));
            if (i == 0)
                firstView = view;

            if (isRowColors())
            {
                view.setBackground(context.getResources().getDrawable(R.drawable.ripple));
                view.getBackground().setColorFilter( context.getResources().getColor(i % 2 == 0 ? R.color.color_list_cift : R.color.color_list_tek) , PorterDuff.Mode.MULTIPLY);
            }

            return view;
        }
        else
        {
            View retView = view != null ? view : getLayoutInflater().inflate(getEmptyViewID(),null);
            if (isRowColors())
            {
                retView.setBackground(context.getResources().getDrawable(R.drawable.ripple));
                retView.getBackground().setColorFilter( context.getResources().getColor(i % 2 == 0 ? R.color.color_list_cift : R.color.color_list_tek) , PorterDuff.Mode.MULTIPLY);
            }
            if (i == 0)
                firstView =retView;
            return  retView;
        }

    }

    //region Veriables


    private int contentViewID,emptyViewID = R.layout.list_item_empty,emtyParentID = R.id.listParent;

    private int emptyRowCount = 0;

    private boolean showDevider = true;



    //endregion

    //region Setter

    public BindingItemAdapter<T> setDetailViewID(int detailViewID) {
        this.contentViewID = detailViewID; return this;
    }

    public BindingItemAdapter<T> setEmptyViewID(int emptyViewID) {
        this.emptyViewID = emptyViewID; return this;
    }

    public BindingItemAdapter<T> setEmtyParentID(int emtyParentID) {
        this.emtyParentID = emtyParentID; return this;
    }

    public BindingItemAdapter<T> setEmptyRowCount(int emptyRowCount) {
        this.emptyRowCount = emptyRowCount; return this;
    }

    public BindingItemAdapter<T> setShowDevider(boolean showDevider) {
        this.showDevider = showDevider; return this;
    }


    //endregion

    //region Getter

    public int getDetailViewID() {
        return contentViewID;
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



    //endregion


    @Override
    public int getCount()
    {
        int i = super.getCount();
        return i < getEmptyRowCount() ?  getEmptyRowCount() : i;
    }
    public int getSuperCount()
    {
        return super.getCount();
    }

    public void setLayoutController(View layoutController) {
        this.layoutController = layoutController;
    }

    public View getLayoutController() {
        return layoutController;
    }

    public View getFirstView() {
        return firstView;
    }

    public boolean isRowColors() {
        return rowColors;
    }

    public BindingItemAdapter setRowColors(boolean rowColors) {
        this.rowColors = rowColors;
        return  this;
    }


    private Hashtable<String, CustomBindingAdapter.OnViewDrawing> onViewDrawings = new Hashtable<>();

    public <T> BindingItemAdapter addOnViewDrawing(String name , CustomBindingAdapter.OnViewDrawing<T> onViewDrawing) {
        onViewDrawings.put(name,onViewDrawing);
        return  this;
    }

    public <T> CustomBindingAdapter.OnViewDrawing<T> getOnViewDrawing(String name) {
        return onViewDrawings.get(name);
    }

    public void RemoveOnViewDrawing(String name)
    {
        onViewDrawings.remove(name);
    }

    public void setOnViewDrawing(Hashtable<String, CustomBindingAdapter.OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
    }

    private BindingGridView.OnRowDrawing onRowDrawing;

    public <T> BindingGridView.OnRowDrawing<T> getOnRowDrawing() {
        return onRowDrawing;
    }

    public <T> void setOnRowDrawing(BindingGridView.OnRowDrawing<T> onRowDrawing) {
        this.onRowDrawing = onRowDrawing;

    }

    public boolean isEmptyRowWithDetailView() {
        return emptyRowWithDetailView;
    }

    public void setEmptyRowWithDetailView(boolean emptyRowWithDetailView) {
        this.emptyRowWithDetailView = emptyRowWithDetailView;
    }

    public interface OnRowDrawing<T>
    {
        default void onDraw(int index,View view,T item) {}
        default void onDraw(int index,View view,T item,View[] views) {}
    }
}

