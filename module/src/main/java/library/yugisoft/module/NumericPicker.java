package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Yusuf on 07.06.2018.
 */

public class NumericPicker extends LinearLayout implements View.OnClickListener
{
    //region SUPER
    AttributeSet attrs=null;
    int defStyleAttr=0;


    public NumericPicker(Context context) {
        super(context);
        Init();
    }

    public NumericPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs=attrs;
        defStyleAttr=0;
        Init();
    }

    public NumericPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs=attrs;
        this.defStyleAttr=defStyleAttr;
        Init();
    }




    //endregion

    //region INTERFACE
    public interface NumericPickerValueChaned {
        void onValueChanged(int oldValue, int newValue);
    }

    private NumericPickerValueChaned numericPickerValueChaned;

    public NumericPickerValueChaned getNumericPickerValueChaned() {
        return numericPickerValueChaned;
    }

    public void setNumericPickerValueChaned(NumericPickerValueChaned numericPickerValueChaned) {
        this.numericPickerValueChaned = numericPickerValueChaned;
    }

    void trigChanged(int o,int n)
    {
        if (numericPickerValueChaned!=null)
            numericPickerValueChaned.onValueChanged(o,n);
    }

    //endregion

    View root,img_next,img_back;

    TextView txt_adet;

    public int value=0,maxValue=0,minValue=0;
    private int buttonGravity=0;

    void Init() {
        root = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.numeric_picker, null);
        if (root == null) return;
        img_next = root.findViewById(R.id.nimg_next);
        img_back = root.findViewById(R.id.nimg_back);
        txt_adet = (TextView) root.findViewById(R.id.ntxt_adet);

        img_back.setOnClickListener(this);
        img_next.setOnClickListener(this);


        try
        {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NumericPicker, defStyleAttr, 0);

            int val = a.getInt(R.styleable.NumericPicker_Value,0),min = a.getInt(R.styleable.NumericPicker_MinValue,0),max = a.getInt(R.styleable.NumericPicker_MaxValue,0);

            setMinValue(min);
            setMaxValue(max);
            setValue(val);

            int gr = a.getInt(R.styleable.NumericPicker_ButtonGravity,0);

            setButtonGravity(gr);

        } catch (Exception e) {
        }
        this.addView(root);
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.nimg_back) {
            setValue(value - 1);

        } else if (i == R.id.nimg_next) {
            setValue(value + 1);

        }


    }


    public double getValue() {
        return value;
    }

    public void setValue(int value)
    {
        int old = this.value;
        if (value>= minValue && value<=maxValue)
        {
            this.value = value;
            txt_adet.setText(yugi.NF(value,0));

            img_back.setEnabled(value!=minValue);
            img_next.setEnabled(value!=maxValue);
            trigChanged(old,value);
        }
    }

    public void setValue2(int value) {
        if (value>= minValue && value<=maxValue)
        {
            this.value = value;
            txt_adet.setText(yugi.NF(value,0));

            img_back.setEnabled(value!=minValue);
            img_next.setEnabled(value!=maxValue);
        }
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        if (value<minValue)
            value=minValue;
    }

    public int getButtonGravity() {
        return buttonGravity;
    }

    public void setButtonGravity(int buttonGravity) {
        if (buttonGravity == 1)
            this.setOrientation(VERTICAL);
        else
            this.setOrientation(HORIZONTAL);
        this.buttonGravity = buttonGravity;
    }
}
