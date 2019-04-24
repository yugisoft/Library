package library.yugisoft.module.Utils.CustomBinding;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import library.yugisoft.module.ItemAdapter;
import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

public class BindingItemAdapter<T> extends ItemAdapter<T> {



    public BindingItemAdapter(int id) {

        this(yugi.activity,id);
    }

    public BindingItemAdapter(Context context, int id) {
        super(context, id);
        contentViewID = id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewGroup layout  = null,parent = null;

        CustomBindingAdapter adapter = null;
        if (view == null)
        {
            layout =(ViewGroup) getLayoutInflater().inflate(getEmptyViewID(),null);
            if (layout != null)
            parent = layout.findViewById(getEmtyParentID());
            adapter = new CustomBindingAdapter(context, contentViewID);

            if (parent!= null && isShowDevider())
            {
                parent.addView(adapter.getBindingView());
                view = layout;
            }
        }
        else
            adapter = new CustomBindingAdapter(context,view);

        if (super.getCount() > i)
            adapter.bind(getList().get(i));
        return view;

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
}
