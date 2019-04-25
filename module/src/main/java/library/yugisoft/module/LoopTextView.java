package library.yugisoft.module;

import android.content.Context;
import android.util.AttributeSet;

public class LoopTextView extends android.support.v7.widget.AppCompatTextView {


    private ItemLooper itemLooper;
    private ItemLooper.OnItemLooperSelected onItemLooperSelected;


    public LoopTextView(Context context) { this(context,null,0); }
    public LoopTextView(Context context, AttributeSet attrs) { this(context, attrs,0); }
    public LoopTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(p->loop());
    }

    private void loop()
    {
        if (itemLooper!=null)
            itemLooper.show();
    }

    public ItemLooper getItemLooper() {
        return itemLooper;
    }

    public <T> void setItemLooper(ItemLooper<T> itemLooper, ItemLooper.OnItemLooperSelected<T> onItemLooperSelected) {
        this.itemLooper = itemLooper;
        this.itemLooper.setOnItemLooperSelected(new ItemLooper.OnItemLooperSelected<T>() {
            @Override
            public void onSelected(int index, T Item)
            {
                LoopTextView.this.setText(Item.toString());
                if (onItemLooperSelected!=null)
                    onItemLooperSelected.onSelected(index,Item);
            }
        });
    }
}
