package library.yugisoft.module.Utils.pSmartGridView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import library.yugisoft.module.R;
import library.yugisoft.module.yugi;

/* Created By Android Resurce Manager Of Yusuf AYDIN 18.10.2019 - 10:32*/

public  class SmartGridViewRowHolder extends RecyclerView.ViewHolder {

public SmartGridViewRowHolder() {
    this(null);
}
public SmartGridViewRowHolder(View itemView) {
    super(itemView== null ? yugi.activity.getLayoutInflater().inflate(R.layout.view_smart_grid_row,null) :itemView);
    init();
}
    public SmartGridViewRowHolder(int itemView) {
        super(yugi.activity.getLayoutInflater().inflate(itemView,null));
        init();
    }


//region DECLARE

public TextView txt_title;
public LinearLayout row_detail;
//endregion

public void init()
{
    
txt_title = (TextView)itemView.findViewById(R.id.txt_title);
row_detail = (LinearLayout)itemView.findViewById(R.id.row_detail);


}


}
