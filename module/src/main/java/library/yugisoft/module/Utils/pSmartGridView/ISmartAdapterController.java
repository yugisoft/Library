package library.yugisoft.module.Utils.pSmartGridView;

import android.view.View;

public interface ISmartAdapterController<T>
{
    default int detailViewID(T item) {return 0;}
    default View detailView(T item) {return null;}
    void setView(View view, T item, int index);
    default void onFinish() {}
    default void onStart(){}
}
