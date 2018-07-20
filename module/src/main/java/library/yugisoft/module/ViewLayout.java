package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ViewLayout extends LinearLayout implements View.OnFocusChangeListener
{
    Drawable focused,unfocused;
    int viewid =0;
    public ViewLayout(Context context) {
        this(context,null,0);
    }
    public ViewLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    public ViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }
    void init(Context context , @Nullable AttributeSet attrs, int defStyle)
    {
        if (getView()!=null)
        {
            this.setBackground(getDrawable());
        }
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ViewLayout, defStyle, 0);
        if (a!=null)
        {
            viewid = a.getResourceId(R.styleable.ViewLayout_viewID,0);
            if (viewid==0) {

                yugi.Print("e", "ViewLayout için ViewID Girilmelidir...");
            }

            focused = a.getDrawable(R.styleable.ViewLayout_focusedDrawable);
            setDrawable(a.getDrawable(R.styleable.ViewLayout_unFocusedDrawable));
            if (focused==null)
                focused = getContext().getResources().getDrawable(R.drawable.input_border_blue);

        }


    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        if (changed) {
            setChildtype();
        }
    }
    private View textClear;
    private View view;
    private ImageView imageView;
    private Drawable drawable;
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        setFocus(v,hasFocus);

    }
    void setFocus(View v,boolean hasFocus) {

        if (hasFocus)
        {
            ViewLayout.this.setBackground(focused);

            if (v instanceof EditText)
                ((EditText)v).setTextSize(((EditText)v).getTextSize()+2);

        }
        else
        {
            ViewLayout.this.setBackground(drawable);//(getContext().getResources().getDrawable(R.drawable.input_border_default));
            if (v instanceof EditText)
                ((EditText)v).setTextSize(((EditText)v).getTextSize()-2);
        }

        if (this.textClear!=null)
        {
            textClear.setVisibility( hasFocus ? VISIBLE : GONE);
        }
    }
    public void setChildtype() {
        if(view!=null) return;
        int count = this.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View v = this.getChildAt(i);
            if (v.getId()==viewid)
                setView(v);
        }
    }
    public void setView(View editText) {

        this.view = editText;
        //this.editText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));
        this.view.setOnFocusChangeListener(this::onFocusChange);
        if (getDrawable()==null)
            setDrawable(editText.getBackground());

        //region Spinner
        if (editText instanceof CustomSpinner)
        {

            CustomSpinner sp = (CustomSpinner)editText;
            sp.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
                @Override
                public void onSpinnerOpened() {
                    setFocus(sp,true);
                }

                @Override
                public void onSpinnerClosed() {
                    setFocus(sp,false);
                }
            });
        }
        //endregion
        //region DEFAULT
        else
        {
            this.view.setBackground(null);
            setFocus(editText,editText.isFocused());
        }
        //endregion

    }
    public View getView() {
        return view;
    }
    public Drawable getDrawable() {
        return drawable;
    }
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
