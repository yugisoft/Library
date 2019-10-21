package library.yugisoft.module.Base;

/* Created By Android Resurce Manager Of Yusuf AYDIN 17.10.2019 - 15:09*/

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import library.yugisoft.module.R;

public abstract class BaseCustomLayout extends LinearLayout
{
    protected TypedArray baseProp;
    private int contentViewID ;
    Context context ; @Nullable
    AttributeSet attrs; int defStyle;
    public BaseCustomLayout(Context context) { this(context,null,0); }
    public BaseCustomLayout(Context context, @Nullable AttributeSet attrs) { this(context, attrs,0); }
    public BaseCustomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); init(context,attrs,defStyleAttr);}

    public void init(Context context , @Nullable AttributeSet attrs, int defStyle)
    {
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        baseProp = getTypedArray(R.styleable.BaseCustomLayout);
        contentViewID = getDefaultContentViewID();
        if (baseProp!=null)
            contentViewID = baseProp.getResourceId(R.styleable.BaseCustomLayout_contentViewID,getDefaultContentViewID());
        inflate(context,contentViewID,this);
    }

    public TypedArray getTypedArray(int[] style) { return  getContext().obtainStyledAttributes(attrs, style, defStyle, 0); }

    protected abstract int getDefaultContentViewID();
}

