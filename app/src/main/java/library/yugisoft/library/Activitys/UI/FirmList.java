package library.yugisoft.library.Activitys.UI;

import android.view.View;


import library.yugisoft.library.R;
import library.yugisoft.library.UI_list_item_firm;
import library.yugisoft.module.Json;
import library.yugisoft.module.Utils.pGoldGridView.IGoldGridItem;
import library.yugisoft.module.Utils.pSmartGridView.SmartGridView;
import library.yugisoft.module.yugi;

public class FirmList implements IGoldGridItem
{
    //region FirmID
    @Json(name = "FirmID")
    private long firmID;

    public void setFirmID(Long firmID) {
        this.firmID = firmID;
    }

    public long getFirmID() {
        return firmID;
    }

    //endregion
    //region FirmTitle
    @Json(name = "FirmTitle")
    private String firmTitle;

    public void setFirmTitle(String firmTitle) {
        this.firmTitle = firmTitle;
    }

    public String getFirmTitle() {
        return firmTitle;
    }
    //endregion
    //region IGoldGridItem

    UI_list_item_firm holder;
    @Override
    public int getViewID() {
        return R.layout.list_item_firm;
    }

    @Override
    public View getView() {
        return holder == null ? null : holder.itemView;
    }

    @Override
    public void setView(View view)
    {
        holder = new UI_list_item_firm(view);
        holder.txt_id.setText(yugi.NF(getFirmID(),0));
        holder.txt_title.setText(getFirmTitle());
    }

    //endregion

}
