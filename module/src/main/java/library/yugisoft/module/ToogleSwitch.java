package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ToogleSwitch extends LinearLayout implements View.OnClickListener
{
    public ToogleSwitch(Context context) {
        this(context,null,0);
    }

    public ToogleSwitch(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ToogleSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public TextView txt1;
    public TextView txt2;


    private String Text1,Text2;

    private Selection selection;


    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        inflate(getContext(),R.layout.view_swich,this);
        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);

        txt1.setOnClickListener(this::onClick);
        txt2.setOnClickListener(this::onClick);

        if (attrs != null)
        {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToogleSwitch);
            setSelection(typedArray.getInt(R.styleable.ToogleSwitch_Selection,0),true);

            setText1(typedArray.getString(R.styleable.ToogleSwitch_text1));
            setText2(typedArray.getString(R.styleable.ToogleSwitch_text2));
        }

        this.requestLayout();

    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
        setSelection(selection.getValue(),true);

    }
    private void setSelection(int selection,boolean isTrig)
    {
        if (selection==0)
        {

            this.selection= Selection.LEFT;
            txt1.setTextColor(Color.parseColor("#FFFFFF"));
            txt1.getBackground().setColorFilter(Color.parseColor("#0B81BC"), PorterDuff.Mode.MULTIPLY);

            txt2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            txt2.setTextColor(Color.parseColor("#0B81BC"));
        }
        else
        {
            this.selection= Selection.RIGHT;
            txt2.getBackground().setColorFilter(Color.parseColor("#0B81BC"), PorterDuff.Mode.MULTIPLY);
            txt2.setTextColor(Color.WHITE);
            txt1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            txt1.setTextColor(Color.parseColor("#0B81BC"));
        }
        if (isTrig && getOnResponse()!=null)
            getOnResponse().onResponse( selection == 0 ? Selection.LEFT : Selection.RIGHT );

    }
    private void setSelection(int selection) {
        setSelection(selection,false);
    }

    @Override
    public void onClick(View v) {
        setSelection(((ViewGroup)this.getChildAt(0)).indexOfChild(v),true);
    }

    public String getText1() {
        return Text1;
    }

    public void setText1(String text1) {
        Text1 = text1;
        txt1.setText(text1);
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
        txt2.setText(text2);

    }

    public INTERFACES.OnResponse<Selection> getOnResponse() {
        return onResponse;
    }

    public void setOnResponse(INTERFACES.OnResponse<Selection> onResponse) {
        this.onResponse = onResponse;
    }

    public enum Selection {
        LEFT(0), RIGHT(1);

        private final int value;
        private Selection(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private INTERFACES.OnResponse<Selection> onResponse;
}


