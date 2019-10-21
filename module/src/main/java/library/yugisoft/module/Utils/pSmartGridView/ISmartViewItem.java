package library.yugisoft.module.Utils.pSmartGridView;

import android.app.Activity;
import android.view.View;

import library.yugisoft.module.Utils.CustomBinding.CustomBindingAdapter;

public interface ISmartViewItem
{
    View getView();
    default boolean setView(SmartGridView smartGridView, View view, int index) {
        new CustomBindingAdapter(smartGridView.getContext(),view,this);
        return  true;
    }
    default void setView(SmartGridView smartGridView, View view, int index, boolean async) {
        ((Activity)smartGridView.getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setView(smartGridView,view,index);
            }
        });
    }

    default String getGroupTitle() { return  "";}

    int getIndex();
}
