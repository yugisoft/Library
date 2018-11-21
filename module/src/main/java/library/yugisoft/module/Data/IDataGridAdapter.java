package library.yugisoft.module.Data;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import library.yugisoft.module.DataTable;

public interface IDataGridAdapter
{
    int getColor();
    int getTextSize();
    DataTable getData();
    View getView(int i, android.view.View view, ViewGroup viewGroup);
    View getHeaderView(LinearLayout viewGroup);
    View getFilterView(LinearLayout viewGroup);
    void UpdateHeaderView(LinearLayout header);
}
