package library.yugisoft.library;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import library.yugisoft.module.yugi;


/* Created By Android Resurce Manager Of Yusuf AYDIN 17.02.2020 - 15:14*/

public  class UI_list_item_firm extends RecyclerView.ViewHolder {

public UI_list_item_firm() {
    this(null);
}
public UI_list_item_firm(View itemView) {
    super(itemView== null ? yugi.activity.getLayoutInflater().inflate(R.layout.list_item_firm,null) :itemView);
    init();
}


//region declare

public LinearLayout listParent;
public TextView txt_id;
public TextView txt_title;
//endregion

public void init()
{

listParent = (LinearLayout)itemView.findViewById(R.id.listParent);
txt_id = (TextView)itemView.findViewById(R.id.txt_id);
txt_title = (TextView)itemView.findViewById(R.id.txt_title);
}


}


