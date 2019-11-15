package library.yugisoft.module.Utils.pSmartGridView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import library.yugisoft.module.R;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

public class SmartGridAdapter<T>
{

    private int backGroundColor = Color.WHITE;

    public void setBackGroundColor(int backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public int getBackGroundColor() {
        return backGroundColor;
    }
    //region Declare

    public static final int VISIBLE = 0x00000000;

    private ISmartGridAdapterListener adapterListener;

    private SmartGridView smartGridView;
    private TextView selectedGroup;
    protected  int detailViewID = 0;
    protected vList<T> vData = new vList<T>();
    protected List<T>  sData = new ArrayList<T>();
    protected int lastViewIndex = 0;
    protected int pageViewCount = 40;
    protected int pageMoreCount = 10;
    protected int numColumns = 3;
    protected String lastGroupTitle = "";



    //region SETTER
    public void setData(vList<T> data) {
        this.vData = data;
        this.sData = this.vData.list;
        notifyDataSetChanged();
    }
    public void setData(List<T> data) {
        this.vData = new vList<>();
        this.sData = data;
        this.vData.list = this.sData;
        notifyDataSetChanged();
    }
    public void setDetailViewID(int detailViewID) {
        this.detailViewID = detailViewID;
    }
    public void setSelectedGroup(TextView selectedGroup) {
        this.selectedGroup = selectedGroup;
    }
    public void setPageViewCount(int pageViewCount) {
        this.pageViewCount = pageViewCount;
    }
    public void setPageMoreCount(int pageMoreCount) {
        this.pageMoreCount = pageMoreCount;
    }
    public void setLastViewIndex(int lastViewIndex) {
        this.lastViewIndex = lastViewIndex;
    }
    public void setLastGroupTitle(String lastGroupTitle) {
        this.lastGroupTitle = lastGroupTitle;
    }
    public void setSmartGridView(SmartGridView smartGridView) { this.smartGridView = smartGridView; }
    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }
    //endregion

    //region GETTER
    public vList<T> getData() {
        return vData;
    }
    public int getDetailViewID() {
        return detailViewID;
    }
    public TextView getSelectedGroup() {
        return selectedGroup;
    }
    public int getPageViewCount() {
        return pageViewCount;
    }
    public int getPageMoreCount() {
        return pageMoreCount;
    }
    public int getLastViewIndex() {
        return lastViewIndex;
    }
    public String getLastGroupTitle() {
        return lastGroupTitle;
    }
    public SmartGridView getSmartGridView() {
        return smartGridView;
    }
    public int getNumColumns() {
        return numColumns;
    }

    //endregion


    //endregion

    public int getCount() {
        return pageViewCount > vData.size() ? vData.size() : pageViewCount;
    }

    public T getItem(int position) {
        return getData().get(position);
    }
    public <E> E getItemForGeneric(int position)
    {
        return (E)getData().get(position);
    }



    public void notifyDataSetChanged() {
        notifyDataSetChanged(false);
    }
    public void notifyDataSetChanged(boolean moreData) {

        if (!moreData)
        {
            lastGroupTitle = "";
            lastViewIndex = 0;
            if (getAdapterListener() != null)
                getAdapterListener().onRemoveAllViews();
        }
        loadMoreData();
    }

    Context getContext() {
        return  getSmartGridView() != null ? getSmartGridView().getContext() : yugi.activity;
    }

    private void loadMoreData() {
        if (getAdapterListener() != null)
            getAdapterListener().onStart();
        numColumns = numColumns < 1 ? 1 : numColumns;
        new AsyncTask<Void, View, String>() {

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Void... voids) {
                for (;lastViewIndex < getCount();lastViewIndex++)
                {

                    SmartGridViewRowHolder row_holder = new SmartGridViewRowHolder(isVertical() ? R.layout.view_smart_grid_row : R.layout.view_smart_grid_row_horizantal);
                    row_holder.row_detail.setBackgroundColor(getBackGroundColor());
                    for (int i = 0; i < numColumns  ; i++)
                    {


                        SmartGridViewCellHolder cell_holder = new SmartGridViewCellHolder();
                        ((ViewGroup)cell_holder.itemView).removeAllViews();
                        cell_holder.cell_detail.setBackgroundColor(getBackGroundColor());
                        row_holder.row_detail.addView(cell_holder.cell_detail);

                        if (lastViewIndex < getCount())
                        {
                            Object item = vData.get(lastViewIndex);

                            if (item instanceof ISmartViewItem)
                            {
                                String title = ((ISmartViewItem)item).getGroupTitle();
                                if (lastGroupTitle.length() > 0 && !title.equals(lastGroupTitle) )
                                {
                                    lastGroupTitle = "";
                                    lastViewIndex--;
                                    i++;
                                    for (;i < numColumns  ; i++)
                                    {
                                        SmartGridViewCellHolder     cell_holder2 = new SmartGridViewCellHolder();
                                        ((ViewGroup)cell_holder2.itemView).removeAllViews();
                                        row_holder.row_detail.addView(cell_holder2.cell_detail);
                                    }
                                    break;
                                }

                                if (lastGroupTitle.length() == 0 && title.length()>0)
                                {
                                    lastGroupTitle = title;
                                    row_holder.txt_title.setText(lastGroupTitle);
                                    row_holder.txt_title.setTag(lastGroupTitle);
                                    row_holder.txt_title.setVisibility(VISIBLE);

                                    ViewGroup gr = (ViewGroup)((Activity)getContext()).getLayoutInflater().inflate(R.layout.view_smart_grid_group_button,null);
                                    TextView textView = (TextView)gr.getChildAt(0);
                                    textView.setTag(lastGroupTitle);
                                    gr.removeAllViews();
                                    textView.setText(lastGroupTitle);
                                    textView.setOnClickListener(v -> {
                                        if (getSmartGridView() != null)
                                            getSmartGridView().scrollTo(((TextView)v).getText().toString());
                                    });
                                    yugi.activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                if (getAdapterListener() != null)
                                                    getAdapterListener().onAddHeader(textView);
                                                if (getSelectedGroup()==null)
                                                    setSelectedGroup(textView);
                                            }
                                            catch (Exception ex)
                                            {}
                                        }
                                    });
                                }
                            }


                            cell_holder.cell_detail.addView(buildView(this));
                            cell_holder.cell_detail.setOnClickListener(v-> {
                                if (getAdapterListener() != null)
                                    getAdapterListener().onItemClick(cell_holder.cell_detail.getChildAt(0),item);

                            });
                            if (numColumns > 1)
                                 lastViewIndex++;
                        }
                    }
                    onProgressUpdate(row_holder.itemView);
                    //if (view != null)

                }
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                if (getAdapterListener() != null)
                    getAdapterListener().onFinish();
            }
            @Override
            protected void onProgressUpdate(View... values) {

                yugi.activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (getAdapterListener() != null)
                            getAdapterListener().onReadyView(values[0]);
                       //
                    }
                });
            }
        }.execute();
    }

    private View buildView(AsyncTask<Void, View, String> task) {

        boolean insertView = false;
        Object item = vData.get(lastViewIndex);
        View view = null;


        if (item instanceof ISmartViewItem)
        {
            view = ((ISmartViewItem) item).getView();
        }

        if (view == null && getDetailViewID() != 0)
        {
            view = yugi.activity.getLayoutInflater().inflate(getDetailViewID(),null);
            view.setId(lastViewIndex);
            insertView = true;
        }


        if (item instanceof ISmartViewItem )
        {
            if (view != null)
                ((ISmartViewItem)item).setView(getSmartGridView(),view,lastViewIndex,!insertView);
            else
                ((ISmartViewItem)item).setView(getSmartGridView(),view,lastViewIndex);
            view = ((ISmartViewItem) item).getView();
            insertView = true;
        }
        return  insertView ? view : null;
    }

    public ISmartGridAdapterListener getAdapterListener() {
        return adapterListener;
    }

    public void setAdapterListener(ISmartGridAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    private boolean vertical;
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public boolean isVertical() {
        return vertical;
    }


    private  Object itemViewController;
    public Object getItemViewController() {
        return itemViewController;
    }
    public void setItemViewController(Object itemViewController) {
        this.itemViewController = itemViewController;
    }
    public void callItemViewController(String methodName,Object... prms) {
        if (getItemViewController()!= null)
        {
            Method method = null;
            try { method = this.getClass().getDeclaredMethod(methodName); } catch (NoSuchMethodException e) { e.printStackTrace(); }
            if (method == null)
                try { method = this.getClass().getMethod(methodName); } catch (NoSuchMethodException e) { e.printStackTrace(); }
            if (method != null)
                try { method.invoke(getItemViewController(),prms); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
