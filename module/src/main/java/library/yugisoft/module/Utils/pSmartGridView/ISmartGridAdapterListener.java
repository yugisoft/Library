package library.yugisoft.module.Utils.pSmartGridView;

import android.view.View;

public interface ISmartGridAdapterListener
{
    default void onFinish() {}
    default void onStart(){}
    default void onReadyView(View view){};
    default void onAddHeader(View view){};
    default void onRemoveAllViews(){};
    default void onItemClick(View view,Object item){};
}
