package library.yugisoft.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import library.yugisoft.module.Base.BaseDialog;

public class ItemLooper<T>  extends DLG_UI_view_item_looper{
    Context context = null;
    Dialog dialog;

    public ItemLooper()
    {
        this(yugi.activity);
    }

    @Override
    public void onConfirm() {
        if (getOnItemLooperSelected()!=null)
            getOnItemLooperSelected().onSelected(loop.getSelectedItem(),getItem());
        dialog.dismiss();
    }

    @Override
    public void onCancel() {
        dialog.dismiss();
    }

    public ItemLooper(Activity activity)
    {
        try { yugi.activity = (yugi.vActivity)activity; } catch (Exception e){}
        context = activity;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(itemView);
    }




    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public ItemLooper<T> setList(List<T> list) {
        this.list = list;
        loop.setDataList(list);
        return this;
    }



    public  T getItem() {
        try
        {
            return (T)loop.getSelectedObject();
        }
        catch (Exception ex)
        {
            return  null;
        }
    }

    public ItemLooper<T> add(T... items)
    {
        if (list==null)
            list = new ArrayList<>();
        list.addAll(Arrays.asList(items));
        return this;
    }

    private OnItemLooperSelected<T> onItemLooperSelected;

    public OnItemLooperSelected<T> getOnItemLooperSelected() {
        return onItemLooperSelected;
    }

    public void setOnItemLooperSelected(OnItemLooperSelected<T> onItemLooperSelected) {
        this.onItemLooperSelected = onItemLooperSelected;
    }


    public void show()
    {
        show("",getOnItemLooperSelected());
    }
    public void show(OnItemLooperSelected<T> itemLooperSelected) {
        show("",itemLooperSelected);
    }
    public void show(String title)
    {
        show(title,getOnItemLooperSelected());
    }
    public void show(String title, OnItemLooperSelected<T> itemLooperSelected) {
        txt_dialog_title.setText(title);
        setOnItemLooperSelected(new OnItemLooperSelected<T>() {
            @Override
            public void onSelected(int index, T Item) {
                if (contentView!=null)
                {
                    contentView.setText(Item.toString());
                }
                if (itemLooperSelected!=null)
                    itemLooperSelected.onSelected(index,Item);
            }
        });
        dialog.show();
    }

    public TextView getContentView() {
        return contentView;
    }

    public void setContentView(TextView contentView) {
        this.contentView = contentView;
        if (contentView!=null)
        {
            contentView.setOnClickListener(p-> show(contentView.getTag().toString()));
        }
    }

    public interface OnItemLooperSelected<T>
    {
        void onSelected(int index, T Item);
    }

    private TextView contentView;

}

abstract class DLG_UI_view_item_looper extends BaseDialog
{

    public DLG_UI_view_item_looper() {
        this(yugi.activity);
    }
    public DLG_UI_view_item_looper(Context context) {
        super(context, R.layout.view_item_looper);
    }

//region DECLARE

    public TextView txt_dialog_title;
    public ImageView btn_confirm;
    public ImageView btn_cancel;
    public LoopView loop;
//endregion


    @Override
    public void Initilialize() {

        txt_dialog_title = (TextView)itemView.findViewById(R.id.txt_dialog_title);
        btn_confirm = (ImageView)itemView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(p->onConfirm());
        btn_cancel = (ImageView)itemView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(p->onCancel());
        loop = (LoopView)itemView.findViewById(R.id.loop);}


    public abstract void onConfirm();
    public abstract void onCancel();

}
