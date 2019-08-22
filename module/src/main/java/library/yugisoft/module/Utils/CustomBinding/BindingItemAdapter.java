package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.TextView;


import java.util.Hashtable;

import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

public class BindingItemAdapter<T> extends ItemAdapter<T> {




    private View layoutController,firstView;
    private boolean rowColors,selectable;
    private boolean emptyRowWithDetailView;
    private OnRowDrawing onRowDrawing;
    private Hashtable<String, CustomBindingAdapter.OnViewDrawing> onViewDrawings = new Hashtable<>();
    private OnGridItemClick<T> onGridItemClick;
    private OnGridItemSelect<T> onGridItemSelect;

    public BindingItemAdapter(int id) {

        this(yugi.activity,id);
    }
    public BindingItemAdapter(Context context, int id) {
        super(context);
        super.setContentView(id);
        contentViewID = id;
    }

    private String idTag = "v";

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewGroup layout  = null,parent = null;

        CustomBindingAdapter adapter = null;

        //region newView
        if (isNew(view,i))
        {
            if (getDetailViewID()>0)
            {
               try
               {
                   layout = (ViewGroup) getLayoutInflater().inflate(getEmptyViewID(), null);
                   if (layout != null)
                       parent = layout.findViewById(getEmtyParentID());
                   View infView = getLayoutInflater().inflate(getDetailViewID(),null);
                   if ((super.getCount() > i) || isEmptyRowWithDetailView()) {
                       if (parent != null && isShowDevider()) {
                           parent.addView(infView);
                           view = layout;
                       } else
                           view = infView;
                   } else
                       view = layout;
               }
               catch (Exception ignored)
               {
                   view = getLayoutInflater().inflate(getEmptyViewID(),null);
               }
            }
            else
            {
                view = getLayoutInflater().inflate(getEmptyViewID(),null);
            }
        }
        //endregion

        //region BindingAdapter
        adapter = new CustomBindingAdapter(view,getItem(i)).setOnViewDrawings(onViewDrawings).setOnRowDrawing(getOnRowDrawing()).setRow(i).setIdTag(getIdTag());

        if (getLayoutController() != null)
            adapter.setLayoutController(getLayoutController());
        //endregion

        if (getItem(i) != null) {
            adapter.bind(getList().get(i));

            if (isSelectable() && getList().get(i) instanceof Checkable)
            {
                View colorView = isShowDevider() ? view.findViewById(getEmtyParentID()) : adapter.getBindingView();
                colorView.setBackground(context.getResources().getDrawable(R.drawable.ripple));
                colorView.getBackground().setColorFilter( context.getResources().getColor(((Checkable)getList().get(i)).isChecked() ? R.color.color_list_selected : R.color.color_list_unselected) , PorterDuff.Mode.MULTIPLY);
                //colorView.setBackgroundColor( context.getResources().getColor(((Checkable)getList().get(i)).isChecked() ? R.color.color_list_selected : R.color.color_list_unselected));
            }
        }

        if (i == 0)
            firstView = view;

        if (isRowColors())
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.ripple));
            view.getBackground().setColorFilter( context.getResources().getColor(i % 2 == 0 ? R.color.color_list_cift : R.color.color_list_tek) , PorterDuff.Mode.MULTIPLY);
        }


        if (getItem(i) != null && (getOnGridItemClick() != null || getOnGridItemSelect() != null || isSelectable()))
        {
            View finalView = view;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isSelectable() && getList().get(i) instanceof Checkable) {
                        if (getOnGridItemSelect() != null) {
                            getOnGridItemSelect().OnSelect(b ->
                            {
                                ((Checkable) getList().get(i)).setChecked(b);
                                notifyDataSetChanged();
                            }, i, (T) getItem(i), finalView);
                        }
                        else
                        {
                            ((Checkable) getList().get(i)).toggle();
                            notifyDataSetChanged();
                        }
                    } else {
                        if (getOnGridItemClick() != null)
                            getOnGridItemClick().onClick(i, (T) getItem(i), finalView);
                    }
                }
            });
        }
        else if (getItem(i) == null)
            view.setOnClickListener(null);

        view.setTag(getItem(i));
        return view;
    }

    @Override
    public boolean isNew(View v, int i) {

        boolean mNew = true;
        try
      {
          mNew = super.isNew(v, i);
          if (super.getCount() > i)
              try {
                  mNew = mNew || ((ViewGroup) v.findViewById(getEmtyParentID())).getChildCount() == 0;
              } catch (Exception ignored) { }
      }
      catch (Exception ignored) { }

        return mNew;
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
    public BindingItemAdapter<T> setLayoutController(View layoutController) {
        this.layoutController = layoutController;
        return  this;
    }
    public BindingItemAdapter<T> setRowColors(boolean rowColors) {
        this.rowColors = rowColors;
        return  this;
    }
    public BindingItemAdapter<T> setOnViewDrawing(Hashtable<String, CustomBindingAdapter.OnViewDrawing> onViewDrawings) {
        this.onViewDrawings = onViewDrawings;
        return  this;
    }
    public BindingItemAdapter<T> setEmptyRowWithDetailView(boolean emptyRowWithDetailView) {
        this.emptyRowWithDetailView = emptyRowWithDetailView;
        return  this;
    }
    public BindingItemAdapter<T> setSelectable(boolean selectable) {
        this.selectable = selectable;
        return  this;
    }

    public BindingItemAdapter<T> setOnGridItemClick(OnGridItemClick<T> onGridItemClick) {
        this.onGridItemClick = onGridItemClick;
        return this;
    }
    public BindingItemAdapter<T> setOnGridItemSelect(OnGridItemSelect<T> onGridItemSelect) {
        this.onGridItemSelect = onGridItemSelect;
        return  this;
    }

    public <R> void setOnRowDrawing(OnRowDrawing<R> onRowDrawing) {
        this.onRowDrawing = onRowDrawing;

    }
    public <R> BindingItemAdapter addOnViewDrawing(String name , CustomBindingAdapter.OnViewDrawing<R> onViewDrawing) {
        onViewDrawings.put(name,onViewDrawing);
        return  this;
    }

    public void RemoveOnViewDrawing(String name)
    {
        onViewDrawings.remove(name);
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

    public boolean isEmptyRowWithDetailView() {
        return emptyRowWithDetailView;
    }

    public View getFirstView() {
        return firstView;
    }
    public int getCount() {
        int i = super.getCount();
        return i < getEmptyRowCount() ?  getEmptyRowCount() : i;
    }
    public int getSuperCount()
    {
        return super.getCount();
    }
    public View getLayoutController() {
        return layoutController;
    }
    public boolean isRowColors() {
        return rowColors;
    }
    public OnGridItemClick<T> getOnGridItemClick() {
        return onGridItemClick;
    }
    public <R> OnRowDrawing<R> getOnRowDrawing() {
        return onRowDrawing;
    }
    public <R> CustomBindingAdapter.OnViewDrawing<R> getOnViewDrawing(String name) {
        return onViewDrawings.get(name);
    }
    public boolean isSelectable() {
        return selectable;
    }

    public OnGridItemSelect<T> getOnGridItemSelect() {
        return onGridItemSelect;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }
    //endregion






}

