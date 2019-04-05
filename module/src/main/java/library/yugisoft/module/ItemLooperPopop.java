package library.yugisoft.module;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

public class ItemLooperPopop  extends PopupWindow {


    //region DECLARE

    public LinearLayout container_picker;
    public RelativeLayout container_toolbar;
    public Button btn_cancel;
    public Button btn_confirm;
    public LinearLayout ly_pickers;

    //endregion

    public View itemView;

    ItemLooper[] looper;
    public   LoopView[] loopViews;
    public ItemLooperPopop(ItemLooper... looper)
    {
        this.looper = looper;
        itemView = LayoutInflater.from(yugi.activity).inflate(R.layout.item_looper_popup_layout, null);
        container_picker = (LinearLayout)itemView.findViewById(R.id.container_picker);
        container_toolbar = (RelativeLayout)itemView.findViewById(R.id.container_toolbar);
        btn_cancel = (Button)itemView.findViewById(R.id.btn_cancel);
        btn_confirm = (Button)itemView.findViewById(R.id.btn_confirm);
        ly_pickers = (LinearLayout)itemView.findViewById(R.id.ly_pickers);

        loopViews = new LoopView[looper.length];
        for(int i = 0 ; i< looper.length ; i++)
        {
            loopViews[i] = new LoopView(yugi.activity);
            loopViews[i].setCanLoop(false);
            loopViews[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1F));
            loopViews[i].setTextSize(12);
            loopViews[i].setDrawItemsCount(7);
            loopViews[i].setDataList(looper[i].getList());
            ly_pickers.addView(loopViews[i]);
        }

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (looper[0].getOnItemLooperSelected() != null)
                    looper[0].getOnItemLooperSelected().onSelected(loopViews[0].getSelectedItem(),looper[0].getList().get(loopViews[0].getSelectedItem()));
                dismiss();
            }
        });
        btn_cancel.setOnClickListener(p-> dismiss());

        setTouchable(true);
        setFocusable(true);
        setAnimationStyle(R.style.FadeInPopWin);



        setContentView(itemView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public ItemLooperPopop setPickerData(List... list)
    {
        for (int i = 0;i<list.length;i++)
            loopViews[i].setDataList(list[i]);
        return  this;
    }

    public void show()
    {
        TranslateAnimation trans = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0);

        showAtLocation(yugi.activity.getWindow().getDecorView(), Gravity.BOTTOM,
                0, 0);
        trans.setDuration(400);
        trans.setInterpolator(new AccelerateDecelerateInterpolator());

        container_picker.startAnimation(trans);
    }


}

