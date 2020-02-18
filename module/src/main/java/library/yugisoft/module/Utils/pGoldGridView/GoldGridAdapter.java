package library.yugisoft.module.Utils.pGoldGridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import library.yugisoft.module.R;
import library.yugisoft.module.Utils.CustomUtil;
import library.yugisoft.module.Utils.pSmartGridView.ISmartViewItem;
import library.yugisoft.module.Utils.pSmartGridView.SmartGridViewCellHolder;
import library.yugisoft.module.Utils.pSmartGridView.SmartGridViewRowHolder;
import library.yugisoft.module.vList;
import library.yugisoft.module.yugi;

import static android.view.View.VISIBLE;

public class GoldGridAdapter<T extends IGoldGridItem> {


    public GoldGridAdapter(IGoldGridSettings<T> settings)
    {
        this.settings = settings;
    }

    private IGoldGridSettings<T> settings;

    private Context context;
    public Context getContext() {
        return context == null ? yugi.activity : context;
    }

    //region Voids

    public  int getCount() {
        return settings.getList().size();
    }




    //endregion


    int lastViewIndex = 0;
    String lastGroupTitle = "";

    private void generate()
    {

        yugi.Run(() -> {

            settings.getAdapterListener().onStart();
            settings.setRefreshing(true);
            settings.onRemoveAllViews();

        });

        for (;lastViewIndex < getCount();lastViewIndex++)
        {

                //region Row Create
                SmartGridViewRowHolder row_holder = new SmartGridViewRowHolder(settings.isVertical() ? settings.getVerticalLayoutID() : settings.getHorizantalLayoutID());
                row_holder.row_divider.setVisibility(settings.isShowDivider() ? VISIBLE : View.GONE);
                if (row_holder.itemView.getBackground() == null)
                    row_holder.itemView.setBackgroundColor(settings.getRowBackColor());
                else
                    row_holder.itemView.getBackground().setColorFilter(settings.getRowBackColor(), PorterDuff.Mode.MULTIPLY);
                //endregion

                //region Cell Create

                for (int ic = 0; ic < settings.getNumColumns(); ic++) {

                    if (settings.getNumColumns() > 1 && ic > 0)
                        lastViewIndex++;

                    if (lastViewIndex < getCount()) {
                        IGoldGridItem gridItem = settings.getList().get(lastViewIndex);

                        if (gridItem.getView() == null) {
                            SmartGridViewCellHolder cell_holder = new SmartGridViewCellHolder();
                            View view = yugi.activity.getLayoutInflater().inflate(gridItem.getViewID(), null, false);
                            cell_holder.cell_detail.addView(view);
                            gridItem.setView(CustomUtil.RemoveParentInView(cell_holder.cell_detail));
                            cell_holder.cell_detail.setOnClickListener(cCel -> {

                                gridItem.onItemClick();
                                settings.getAdapterListener().onItemClick(cCel, gridItem);

                            });
                        }

                        row_holder.row_detail.addView(CustomUtil.RemoveParentInView(gridItem.getView()));

                        //region GroupTitle
                        // TODO: 17.02.2020 Group Title
                        String title = gridItem.getGroupTitle();

                        if (lastGroupTitle.length() > 0 && !title.equals(lastGroupTitle)) {
                            // Bir Gruba Ait Sutunlar Bittiğinde , Satırı Kolon Sayısına Göre Doldurmak İçin Kullanılır
                            lastGroupTitle = "";
                            lastViewIndex--;
                            //ic++;
                            for (; ic < settings.getNumColumns(); ic++)
                                row_holder.row_detail.addView(CustomUtil.RemoveParentInView(new SmartGridViewCellHolder().cell_detail));


                            break;
                        }

                        if (lastGroupTitle.length() == 0 && title.length() > 0) {
                            lastGroupTitle = title;
                            row_holder.txt_title.setText(lastGroupTitle);
                            row_holder.txt_title.setTag(lastGroupTitle);
                            row_holder.txt_title.setVisibility(VISIBLE);

                            //region Group Button
                            // TODO: 17.02.2020 Group Button
                        /*
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
                        */
                            //endregion

                        }

                        //endregion


                    }

                }


                //endregion

                yugi.Run(() -> settings.getViewLayout().addView(row_holder.itemView));

        }


        yugi.Run(() -> {

            settings.setRefreshing(false);
            settings.getAdapterListener().onFinish();

        });

    }







    public void notifyDataSetChanged()
    {
        notifyDataSetChanged(false);
    }

    private AsyncTask notifyDataSetChangedTask = null;
    @SuppressLint("StaticFieldLeak")
    public void notifyDataSetChanged(boolean moreData)
    {
        if (!moreData)
            lastViewIndex = 0;

        if(notifyDataSetChangedTask != null && notifyDataSetChangedTask.getStatus() != AsyncTask.Status.FINISHED)
            notifyDataSetChangedTask.cancel(true);

        notifyDataSetChangedTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                generate();
                return null;
            }
        };

        notifyDataSetChangedTask.execute();
    }

}
