package library.yugisoft.module.Utils.pSmartGridView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;


import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

/* Created By Android Resurce Manager Of Yusuf AYDIN 18.10.2019 - 09:38*/

public  class SmartGridViewCellHolder extends RecyclerView.ViewHolder {

public SmartGridViewCellHolder() {
    this(null);
}
public SmartGridViewCellHolder(View itemView) {
    super(itemView== null ? yugi.activity.getLayoutInflater().inflate(R.layout.view_smart_grid_cell,null) :itemView);
    init();
}


//region DECLARE

public LinearLayout cell_detail;
//endregion

public void init()
{
    
cell_detail = (LinearLayout)itemView.findViewById(R.id.cell_detail);
}


}
