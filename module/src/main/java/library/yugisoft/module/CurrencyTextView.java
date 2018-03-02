package library.yugisoft.module;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Yusuf on 12.01.2018.
 */

public class CurrencyTextView extends android.support.v7.widget.AppCompatTextView
{
    yugi.mNumpad numpad ;

    public Double Tutar=0.0;

    public String Currency="";

    public CurrencyTextView(Context context) {
        super(context);
        Init();
    }

    private INTERFACES.OnNumpadListener onNumpadListener;

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }
    public void Init()
    {
        numpad = new yugi.mNumpad(yugi.activity);
        numpad.mlistener=new INTERFACES.OnNumpadListener() {
            @Override
            public void onResultOK(double Before, double After)
            {

                Tutar=After;
                CurrencyTextView.this.setText(yugi.NF2Replace(Tutar)+" "+Currency);
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
        CurrencyTextView.this.setText(yugi.NF2Replace(Tutar)+" "+Currency);
    }
}
