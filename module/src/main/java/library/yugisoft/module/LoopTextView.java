package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import library.yugisoft.module.Utils.CustomBinding.BindingGridView;
import library.yugisoft.module.Utils.CustomBinding.OnRowDrawing;

public class LoopTextView extends android.support.v7.widget.AppCompatTextView {


    AttributeSet attrs; int defStyle;
    public TypedArray getTypedArray(int[] style) { return  getContext().obtainStyledAttributes(attrs, style, defStyle, 0); }

    private ItemLooper itemLooper;
    private int index = -1;
    public <T> T getSelectedObject() {
        if (index < 0)
            return null;
        else
            return (T)getItemLooper().getList().get(index);
    }
    public LoopTextView(Context context) { this(context,null,0);init(); }
    public LoopTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        this.attrs = attrs;
        init();
    }
    public LoopTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defStyle =defStyleAttr;

        init();
    }

    private int contentViewID = R.layout.list_item_text;

    public int getContentViewID() {
        return contentViewID;
    }

    public void setContentViewID(int contentViewID) {
        this.contentViewID = contentViewID;
        getItemLooper().setDetailViewID(contentViewID);
    }

    private void init()
    {

        TypedArray typeArray = getTypedArray(R.styleable.LoopTextView);

        if (typeArray != null)
        {
            contentViewID = typeArray.getResourceId(R.styleable.LoopTextView_contentViewID,R.layout.list_item_text);
            setLoopDisplayType(LoopDisplayType.GetValue(typeArray.getInt(R.styleable.LoopTextView_LoopDisplayType,0)));
        }

        setOnClickListener(p->loop());
    }

    public void loop()
    {
        if (getItemLooper().getList().size()==0)
            return;
        
        switch (getLoopDisplayType())
        {

            case popup:
                getItemLooper().show();
                break;
            case listPopup:
                getItemLooper().show(this);
                break;
            case Dialog:
                getItemLooper().showDialog(this.getText().toString());
                break;
            case FullScreenDialog:
                getItemLooper().showFullScreen(this.getText().toString());
                break;
            case ListDialog:
                getItemLooper().showListDialog(this.getText().toString());
                break;
            case FullScreenListDialog:
                getItemLooper().showListFullDialog(this.getText().toString());
                break;
        }
    }

    private ItemLooper.OnItemLooperSelected onItemLooperSelected;

    public ItemLooper getItemLooper() {
        if (itemLooper == null)
        {
            itemLooper = new ItemLooper();
            itemLooper.setOnItemLooperSelected(new ItemLooper.OnItemLooperSelected() {
                @Override
                public void onSelected(int index, Object Item) {
                    LoopTextView.this.index = index;
                    LoopTextView.this.setText(Item.toString());
                    if (getOnItemLooperSelected() != null)
                        getOnItemLooperSelected().onSelected(index,Item);
                }
            });
            itemLooper.setDetailViewID(contentViewID);
            itemLooper.setOnRowDrawing(getOnRowDrawing());

        }
        return itemLooper;
    }

    public <T> void setItemLooper(ItemLooper<T> itemLooper, ItemLooper.OnItemLooperSelected<T> onItemLooperSelected) {
        this.itemLooper = itemLooper;
        this.itemLooper.setOnItemLooperSelected(new ItemLooper.OnItemLooperSelected<T>() {
            @Override
            public void onSelected(int index, T Item)
            {
                LoopTextView.this.index = index;
                LoopTextView.this.setText(Item.toString());
                if (onItemLooperSelected!=null)
                    onItemLooperSelected.onSelected(index,Item);
            }
        });
    }

    public void  setList(List list)
    {
        getItemLooper().setList(list);
    }

    public <T> List<T> getList()
    {
        return (List<T>)getItemLooper().getList();
    }



    private LoopDisplayType loopDisplayType = LoopDisplayType.listPopup;

    public LoopDisplayType getLoopDisplayType() {
        return loopDisplayType;
    }

    public void setLoopDisplayType(LoopDisplayType loopDisplayType) {
        this.loopDisplayType = loopDisplayType;
    }


    public void show() { getItemLooper().show(); }
    public void show(View anchorView){getItemLooper().show(anchorView);}
    public void showDialog(String title){getItemLooper().showDialog(title);}
    public void showFullScreen(String title){getItemLooper().showFullScreen(title);}
    public static int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    public void showDialog(String title, int witdh, int height){ getItemLooper().showDialog(title,witdh,height); }

    public BindingGridView showListDialog(String title){ return getItemLooper().showListDialog(title); }
    public BindingGridView showListFullDialog(String title){return getItemLooper().showListFullDialog(title);}
    public BindingGridView showListDialog(String title,int id){return getItemLooper().showListDialog(title,id);}
    public BindingGridView showListFullDialog(String title,int id){return getItemLooper().showListFullDialog(title,id);}
    public ItemLooper  setDetailViewID(int detailViewID){return getItemLooper().setDetailViewID(detailViewID);}

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        if (index >= 0)
        {
            getItemLooper().setSelectedITem(getList().get(index));
            LoopTextView.this.setText(getList().get(index).toString());
        }
        else
        {
            getItemLooper().setSelectedITem(null);
            LoopTextView.this.setText(R.string.str_seciniz);
        }
    }

    public ItemLooper.OnItemLooperSelected getOnItemLooperSelected() {
        return onItemLooperSelected;
    }

    public void setOnItemLooperSelected(ItemLooper.OnItemLooperSelected onItemLooperSelected) {
        this.onItemLooperSelected = onItemLooperSelected;

    }

    private OnRowDrawing onRowDrawing;

    public OnRowDrawing getOnRowDrawing() {
        return onRowDrawing;
    }

    public void setOnRowDrawing(OnRowDrawing onRowDrawing) {
        this.onRowDrawing = onRowDrawing;
    }
}
