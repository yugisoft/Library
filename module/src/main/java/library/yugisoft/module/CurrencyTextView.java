package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Yusuf on 12.01.2018.
 */

public class CurrencyTextView extends android.support.v7.widget.AppCompatTextView
{


    yugi.mNumpad numpad ;

    public Double Tutar=0.0;
    public String Currency ="";


    public CurrencyTextView(Context context) {
        this(context,null,0);
    }

    private INTERFACES.OnNumpadListener onNumpadListener;

    public CurrencyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        numpad = new yugi.mNumpad(yugi.activity);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CurrencyTextView, defStyleAttr, 0);
        if (a != null) {

            numpad.Currency = a.getString(R.styleable.CurrencyTextView_currency);
            numpad.setDecimal(a.getInteger(R.styleable.CurrencyTextView_decimal,2));

        }



        numpad.mlistener=new INTERFACES.OnNumpadListener() {
            @Override
            public void onResultOK(double Before, double After)
            {

                Tutar=After;
                CurrencyTextView.this.setText(yugi.NFReplace(Tutar,numpad.decimal)+" "+Currency);
                if (onNumpadListener!=null)onNumpadListener.onResultOK(Before,After);
            }
        };
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                numpad.Ucret=Tutar;
                numpad.totalUcret=Tutar;
                numpad.Currency= Currency;
                numpad.Show();
            }
        });
    }


    public INTERFACES.OnNumpadListener getOnNumpadListener() {
        return onNumpadListener;
    }

    public void setOnNumpadListener(INTERFACES.OnNumpadListener onNumpadListener) {
        this.onNumpadListener = onNumpadListener;
    }

    public void setTutar(Double t)
    {
        Tutar=t;
        CurrencyTextView.this.setText(yugi.NFReplace(Tutar,numpad.decimal)+" "+Currency);
    }

    public int getDecimal() {
        try {
            return numpad.decimal;
        }
        catch (Exception ee)
        {
            return 2;
        }
    }

    public void setDecimal(int decimal) {
        numpad.setDecimal(decimal);
    }
}
