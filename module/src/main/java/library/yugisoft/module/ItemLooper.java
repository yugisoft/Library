package library.yugisoft.module;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import library.yugisoft.module.Base.BaseDialog;

public class ItemLooper<T>
{

    public ItemLooper() {this(yugi.activity);}
    public ItemLooper(Context context) { this(context,new ArrayList<>());}
    public ItemLooper(List<T> list) { this(yugi.activity,list);}
    public ItemLooper(Context context,List<T> list)
    {
        setContext(context);
        setList(list);
    }

    //region DECLARE
    private Context context;
    private List<T> list;
    private LoopView loopView = null;
    private int textSize = 12,drawItemsCount = 7,textAlignment = Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,textColor = Color.BLACK,BackColor = Color.TRANSPARENT;
    private boolean canLoop;



    private OnItemLooperSelected<T> onItemLooperSelected;

    public OnItemLooperSelected<T> getOnItemLooperSelected() {
        return onItemLooperSelected;
    }

    public void setOnItemLooperSelected(OnItemLooperSelected<T> onItemLooperSelected) {
        this.onItemLooperSelected = onItemLooperSelected;
    }
    //endregion
    //region Builds

        public ItemLooper<T> setContext(Context context) { this.context = context; return this;}
        public ItemLooper<T> setList(List<T> list) {
            this.list = list;
            if (loopView ==null)
                createLoopView();
            else
                loopView.setDataList(list);
            return this;}
        public ItemLooper<T> setCanLoop(boolean canLoop) { this.canLoop = canLoop; return this;}
        public ItemLooper<T> setTextSize(int textSize) { this.textSize = textSize; return this;}
        public ItemLooper<T> setDrawItemsCount(int drawItemsCount) { this.drawItemsCount = drawItemsCount; return this;}
        public ItemLooper<T> setTextAlignment(int textAlignment) { this.textAlignment = textAlignment;  return  this;}
        public ItemLooper<T> setTextColor(int textColor) { this.textColor = textColor; return  this;}
        public ItemLooper<T> setBackColor(int backColor) { BackColor = backColor; return  this;}

    //endregion
    //region Getters
    public Context getContext() {
        return context;
    }
    public List<T> getList() { return list; }
    public boolean isCanLoop() {
        return canLoop;
    }
    public int getTextSize() {
        return textSize;
    }
    public int getDrawItemsCount() {
        return drawItemsCount;
    }
    public LoopView getLoopView() {
        return loopView;
    }
    public int getTextAlignment() {
        return textAlignment;
    }
    public int getTextColor() {
        return textColor;
    }
    public int getBackColor() {
        return BackColor;
    }
    //endregion

    void createLoopView()
    {
        loopView = new LoopView(getContext());
        loopView.setCanLoop(isCanLoop());
        loopView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1F));
        loopView.setTextSize(getTextSize());
        loopView.setDrawItemsCount(getDrawItemsCount());
        loopView.setDataList(getList());
    }

    //region Popup
    IL_Popup<T> popup;
    public void show() {
        if (popup == null)
        {
            popup = new IL_Popup<T>(getContext(),this)
            {
                @Override
                public void Confirm() {
                    if (getOnItemLooperSelected() != null)
                    {
                        getOnItemLooperSelected().onSelected(loopView.getSelectedItem(),getList().get(loopView.getSelectedItem()));
                    }
                }
            };

        }
        popup.show();
    }
    //endregion
    //region ListPopup
    IL_ListPopup<T> listPopup;
    public void show(View anchorView) {
        if (listPopup == null)
        {
            listPopup = new IL_ListPopup<T>(getContext(),this);
            listPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (getOnItemLooperSelected() != null)
                    {
                        getOnItemLooperSelected().onSelected(position,getList().get(position));
                    }
                }
            });
        }
        listPopup.setAnchorView(anchorView);
        listPopup.show();;
    }
    //endregion
    //region Dialog
    IL_Dialog dialog;
    public void showDialod(String title) {
        if (dialog == null)
        {
            dialog = new IL_Dialog(getContext())
            {
                @Override
                public void onConfirm() {
                    if (getOnItemLooperSelected() != null)
                    {
                        getOnItemLooperSelected().onSelected(loopView.getSelectedItem(),getList().get(loopView.getSelectedItem()));
                    }
                    dismiss();
                }
            };
            dialog.loop = loopView;
        }
        try{((ViewGroup)loopView.getParent()).removeAllViews();}catch (Exception ex){}
        dialog.bar_loop.addView(loopView);
        dialog.txt_dialog_title.setText(title);
        dialog.show();
    }


    //endregion


    public interface OnItemLooperSelected<T>
    {
        void onSelected(int index, T Item);
    }
}

class IL_ListPopup<T> extends ListPopupWindow
{
    int layout = R.layout.view_item_looper;

    private ItemLooper<T> looper;

    ItemAdapter<T> adapter;

    public IL_ListPopup(ItemLooper<T> looper)
    {
        this(yugi.activity,looper);
    }
    public IL_ListPopup(Context context, ItemLooper<T> looper)
    {
        super(context);
        this.looper = looper;
        adapter = new ItemAdapter<T>(context) {

            @Override
            public long getItemId(int i)
            {
                if (getItem(i) instanceof  IItemLooper)
                    return ((IItemLooper)getItem(i)).toLooperLong();
                else
                    return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup)
            {
                Object item = getList().get(i);
                if (view == null)
                {
                    TextView tview = new TextView(context);
                    tview.setTextSize(IL_ListPopup.this.looper.getTextSize());
                    tview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tview.setGravity(looper.getTextAlignment());
                    tview.setText(item instanceof IItemLooper ? ((IItemLooper)item).toLooperString() : item.toString());
                    tview.setTextColor(looper.getTextColor());
                    tview.setBackgroundColor(looper.getBackColor());
                    view = tview;
                }
                else
                {
                    TextView tview = (TextView) view;
                    tview.setText(item instanceof IItemLooper ? ((IItemLooper)item).toLooperString() : item.toString());
                }

                return view;
            }
        };
        setAdapter(adapter);
        adapter.setList(looper.getList());
        this.setModal(true);
    }
}

class IL_Dialog extends BaseDialog
{

    public IL_Dialog() {
        this(yugi.activity);
    }
    public IL_Dialog(Context context) {
        super(context, R.layout.view_item_looper);
    }

    //region DECLARE

    public TextView txt_dialog_title;
    public View btn_confirm;
    public View btn_cancel;
    public LoopView loop;
    public LinearLayout bar_loop;
//endregion


    @Override
    public void Initilialize() {

        txt_dialog_title = (TextView)itemView.findViewById(R.id.txt_dialog_title);
        btn_confirm = itemView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(p->onConfirm());
        btn_cancel = itemView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(p->onCancel());

        bar_loop = (LinearLayout)itemView.findViewById(R.id.bar_loop);
    }


    public  void onConfirm(){};
    public  void onCancel(){dismiss();};

}

class IL_Popup<T> extends PopupWindow
{
    //region DECLARE

    public LinearLayout container_picker;
    public RelativeLayout container_toolbar;
    public Button btn_cancel;
    public Button btn_confirm;
    public LinearLayout ly_pickers;
    public View itemView;
    //endregion

    public  void Confirm(){}

    private ItemLooper<T> looper;

    ItemAdapter<T> adapter;

    public IL_Popup(ItemLooper<T> looper)
    {
        this(yugi.activity,looper);
    }
    public IL_Popup(Context context, ItemLooper<T> looper)
    {
        super(context);
        this.looper = looper;
        itemView = LayoutInflater.from(yugi.activity).inflate(R.layout.item_looper_popup_layout, null);
        container_picker = (LinearLayout)itemView.findViewById(R.id.container_picker);
        container_toolbar = (RelativeLayout)itemView.findViewById(R.id.container_toolbar);
        btn_cancel = (Button)itemView.findViewById(R.id.btn_cancel);
        btn_confirm = (Button)itemView.findViewById(R.id.btn_confirm);
        ly_pickers = (LinearLayout)itemView.findViewById(R.id.ly_pickers);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Confirm();
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

    public void show()
    {
        try{((ViewGroup)looper.getLoopView().getParent()).removeAllViews();}catch (Exception ex){}
        ly_pickers.addView(looper.getLoopView());
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


interface IItemLooper {
    default String toLooperString(){ return  this.toString();};
    default int toLooperInt(){ return  0;}
    default long toLooperLong(){ return  0;}
}
