package library.yugisoft.module.Utils.pGoldGridView;

import android.graphics.Color;
import android.view.ViewGroup;

import java.util.List;

import library.yugisoft.module.R;
import library.yugisoft.module.Utils.pSmartGridView.ISmartGridAdapterListener;
import library.yugisoft.module.vList;

public interface IGoldGridSettings<T>
{
    default boolean isVertical() {
        return true;
    }
    default int getHorizantalLayoutID() {
        return R.layout.view_smart_grid_row_horizantal;
    }
    default int getVerticalLayoutID() {
        return R.layout.view_smart_grid_row;
    }
    default boolean isShowDivider() {
        return true;
    }
    default int getRowBackColor() {
        return Color.TRANSPARENT;
    }
    default List<T> getList(){ return getData().list; };
    vList<T> getData();
    ViewGroup getViewLayout();
    default void  setRefreshing(boolean refreshing){};
    default int getNumColumns(){ return  1; }
    void  onRemoveAllViews();
    ISmartGridAdapterListener getAdapterListener();
}
