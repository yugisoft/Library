package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class InputLayout extends LinearLayout implements View.OnFocusChangeListener , View.OnClickListener
{
    Drawable focused,unfocused;
    private String hint="";
    public InputLayout(Context context) {
        this(context,null,0);
    }
    public InputLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    public InputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    void init(Context context , @Nullable AttributeSet attrs, int defStyle)
    {
        if (getEditText()!=null)
        {
            this.setBackground(getDrawable());
        }
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.InputLayout, defStyle, 0);
        if (a!=null)
        {
            focused = a.getDrawable(R.styleable.InputLayout_focusedDrawable);
            setDrawable(a.getDrawable(R.styleable.InputLayout_unFocusedDrawable));
            if (focused==null)
                focused = getContext().getResources().getDrawable(R.drawable.input_border_blue);
            setHint(a.getString(R.styleable.ViewLayout_hint));
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
    private EditText editText;
    private ImageView imageView;
    private Drawable drawable;

    public void setChildtype()
    {
        if(editText!=null) return;
        int count = this.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View v = this.getChildAt(i);

            if (v instanceof EditText)
                    setEditText((EditText)v);

            else if (v instanceof ImageView)
                setImageView((ImageView)v);

            if (v.getContentDescription() != null && v.getContentDescription().toString().toLowerCase().equals("clear"))
            {
                setTextClear(v);

                getTextClear().setOnClickListener(this::onClick);
            }
        }
    }

    void setFocus(View v,boolean hasFocus) {

    if (hasFocus)
    {
        InputLayout.this.setBackground(focused);
        ((EditText)v).setTextSize(((EditText)v).getTextSize()+2);

    }
    else
    {
        InputLayout.this.setBackground(drawable);//(getContext().getResources().getDrawable(R.drawable.input_border_default));
        ((EditText)v).setTextSize(((EditText)v).getTextSize()-2);

    }

    if (this.textClear!=null)
    {
        textClear.setVisibility( hasFocus ? VISIBLE : GONE);
    }
}

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        setFocus(v,hasFocus);

    }

    @Override
    public void onClick(View v) {
        editText.setText("");
    }

    public void setEditText(EditText editText) {
        if (this.editText==null)
        {
            this.editText = editText;
            try {
                this.setHint(editText.getHint().toString());
            }
            catch (Exception ex)
            {

            }
            this.editText.setHint("");
            int l = this.editText.getPaddingLeft();
            int r = this.editText.getPaddingRight();
            this.editText.setPaddingRelative(l,5,r,0);
            //this.editText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1));

            this.editText.setOnFocusChangeListener(this::onFocusChange);
            if (getDrawable()==null)
                setDrawable(editText.getBackground());
            this.editText.setBackground(null);
            this.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    InputLayout.this.requestLayout();
                }
            });
            setFocus(editText,editText.isFocused());

        }
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public EditText getEditText() {
        return editText;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public View getTextClear() {
        return textClear;
    }

    public void setTextClear(View textClear) {
        this.textClear = textClear;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        try {
            String text = getHint();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);


            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            if (editText.getText().length()==0)
                paint.setTextSize(editText.getTextSize());
            int height = bounds.height();
            int x = getPaddingLeft()+editText.getLeft()+8;
            //int x = getPaddingLeft()+(imageView != null  ? imageView.getWidth() : 0)+8;
            int y =( (editText.getText().length() == 0 ) ? (getHeight()/2) - (height/2) : getPaddingTop() ) + +12;
            //int y =getPaddingTop() + 12;






            paint.setColor(Color.RED);
            canvas.drawText(text, x, y, paint);
        }
        catch (Exception ex)
        {

        }
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        if (hint==null || hint.length()==0 ) hint = "";
        this.hint = hint;
    }
}
