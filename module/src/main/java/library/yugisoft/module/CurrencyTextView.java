package library.yugisoft.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import library.yugisoft.module.Base.DLG_Numpad;


/**
 * Created by Yusuf on 12.01.2018.
 */

public class CurrencyTextView extends android.support.v7.widget.AppCompatTextView
{



    DLG_Numpad numpad;
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
        numpad = new DLG_Numpad();
       try {
           final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CurrencyTextView, defStyleAttr, 0);
           if (a != null) {

               Currency = a.getString(R.styleable.CurrencyTextView_currency);
               Currency = (Currency == null ? "" : Currency);

               numpad.setCurrency(Currency);
               numpad.setDigit(a.getInteger(R.styleable.CurrencyTextView_decimal,2));

           }
       }catch (Exception ex)
       {}


       numpad.setListener(new INTERFACES.OnResponse<Double>() {
           @Override
           public void onResponse(Double item) {

               CurrencyTextView.this.setText(yugi.NFReplace(item,numpad.getDigit())+" "+Currency);
               if (onNumpadListener!=null)onNumpadListener.onResultOK(Tutar,item);
               Tutar=item;
           }
       });


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               numpad.setTotalAmount(Tutar);
               numpad.show();
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
        CurrencyTextView.this.setText(yugi.NFReplace(Tutar,numpad.getDigit())+" "+Currency);
    }

    public int getDecimal() {
        try {
            return numpad.getDigit();
        }
        catch (Exception ee)
        {
            return 2;
        }
    }

    public void setDecimal(int decimal) {
        numpad.setDigit(decimal);
    }
}
