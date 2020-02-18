package library.yugisoft.module.Utils.pGoldGridView;

import android.view.View;
import android.view.ViewGroup;

public interface IGoldGridItem
{
    int getViewID();
    View getView();
    void setView(View view);
    default String getGroupTitle(){ return ""; };

    default void onItemClick(){}

}
