package library.yugisoft.module.Base;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import library.yugisoft.module.INTERFACES;
import library.yugisoft.module.R;
import library.yugisoft.module.parse;
import library.yugisoft.module.yugi;

public class DLG_Numpad extends BaseDialog {

    public DLG_Numpad() {
        this(yugi.activity);
    }
    public DLG_Numpad(Context context) {
        super(context, R.layout.ly_numpad);
    }

    //region DECLARE

    public TextView txt_ucret;
    public TextView np7;
    public TextView np8;
    public TextView np9;
    public TextView np4;
    public TextView np5;
    public TextView np6;
    public TextView np1;
    public TextView np2;
    public TextView np3;
    public TextView npclear;
    public TextView np0;
    public TextView npvirgul;
    public TextView npBack;
    public TextView npTotal;
    public TextView n2;
    public TextView n3;
    public TextView n4;
    public TextView ly_kapat;
    public TextView npOk;
    //endregion

    @Override
    public void Initilialize() {

        txt_ucret = (TextView)itemView.findViewById(R.id.txt_ucret);
        np7 = (TextView)itemView.findViewById(R.id.np7);
        np7.setOnClickListener(p->numpad(((TextView)p).getText()));
        np8 = (TextView)itemView.findViewById(R.id.np8);
        np8.setOnClickListener(p->numpad(((TextView)p).getText()));
        np9 = (TextView)itemView.findViewById(R.id.np9);
        np9.setOnClickListener(p->numpad(((TextView)p).getText()));
        np4 = (TextView)itemView.findViewById(R.id.np4);
        np4.setOnClickListener(p->numpad(((TextView)p).getText()));
        np5 = (TextView)itemView.findViewById(R.id.np5);
        np5.setOnClickListener(p->numpad(((TextView)p).getText()));
        np6 = (TextView)itemView.findViewById(R.id.np6);
        np6.setOnClickListener(p->numpad(((TextView)p).getText()));
        np1 = (TextView)itemView.findViewById(R.id.np1);
        np1.setOnClickListener(p->numpad(((TextView)p).getText()));
        np2 = (TextView)itemView.findViewById(R.id.np2);
        np2.setOnClickListener(p->numpad(((TextView)p).getText()));
        np3 = (TextView)itemView.findViewById(R.id.np3);
        np3.setOnClickListener(p->numpad(((TextView)p).getText()));
        npclear = (TextView)itemView.findViewById(R.id.npclear);
        npclear.setOnClickListener(p->Clear());
        np0 = (TextView)itemView.findViewById(R.id.np0);
        np0.setOnClickListener(p->numpad(((TextView)p).getText()));
        npvirgul = (TextView)itemView.findViewById(R.id.npvirgul);
        npvirgul.setOnClickListener(p->onVirgulClick());
        npBack = (TextView)itemView.findViewById(R.id.npBack);
        npBack.setOnClickListener(p->Del());
        npTotal = (TextView)itemView.findViewById(R.id.npTotal);
        npTotal.setOnClickListener(p->nAmount(((TextView)p).getText()));
        n2 = (TextView)itemView.findViewById(R.id.n2);
        n2.setOnClickListener(p->nAmount(((TextView)p).getText()));
        n3 = (TextView)itemView.findViewById(R.id.n3);
        n3.setOnClickListener(p->nAmount(((TextView)p).getText()));
        n4 = (TextView)itemView.findViewById(R.id.n4);
        n4.setOnClickListener(p->nAmount(((TextView)p).getText()));
        ly_kapat = (TextView)itemView.findViewById(R.id.ly_kapat);
        ly_kapat.setOnClickListener(p->Close());
        npOk = (TextView)itemView.findViewById(R.id.npOk);
        npOk.setOnClickListener(p->Ok());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setDigit(getDigit());
                firstDigit = "0";
                for(int i = 0 ;i<digits.length;i++)
                    digits[i] = "0";
            }
        },500);
    }

    //region MORE

    private int digit = 2;
    public int getDigit() {
        return digit;
    }
    public DLG_Numpad setDigit(int digit) {
        this.digit = digit;
        digits = new String[digit];
        setAmount(getAmount());
        setTotalAmount(getTotalAmount());
        return  this;
    }

    private String currency = "₺";
    public String getCurrency() {
        return currency;
    }
    public DLG_Numpad setCurrency(String currency) {
        this.currency = currency;
        return  this;
    }

    private double Amount = 0;

    public double getAmount() {
        return Amount;
    }

    public DLG_Numpad setAmount(double amount) {
        Amount = amount;
        try{txt_ucret.setText(getCurrency()+yugi.NF(amount,digit));}catch (Exception e){}
        return this;
    }

    private int digitLimit = 9;
    public int getDigitLimit() {
        return digitLimit;
    }

    public DLG_Numpad setDigitLimit(int digitLimit) {
        this.digitLimit = digitLimit;
        return  this;
    }

    private INTERFACES.OnResponse<Double> listener;
    public INTERFACES.OnResponse<Double> getListener() {
        return listener;
    }
    public DLG_Numpad setListener(INTERFACES.OnResponse<Double> listener) {
        this.listener = listener;
        return  this;
    }

    private Double totalAmount = 0.0;
    public Double getTotalAmount() {
        return totalAmount;
    }

    public DLG_Numpad setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;

        npTotal .setText(yugi.NF(totalAmount ,digit));
        n2.setText(yugi.NF(totalAmount /2,digit));
        n3.setText(yugi.NF(totalAmount / 3,digit));
        n4.setText(yugi.NF(totalAmount /4,digit));
        return this;
    }
    //endregion

    boolean contiuneClear = true;

    String firstDigit = "0";
    String[] digits = new String[digit];
    int currentDigit = 0;
    boolean virgul = false;

    public void numpad(Object... args)
    {
        if (contiuneClear)
        {
            Clear();
            contiuneClear = false;
        }
        try
        {
            if (args.length == 1) {

                String d = (args[0].toString());
                if (virgul) {
                    if (currentDigit < digits.length)
                    {
                        digits[currentDigit] = d;
                        currentDigit++;
                    }
                }
                else
                {
                    if (firstDigit.length()<digitLimit)
                        firstDigit = firstDigit.equals("0") ? d : firstDigit + d;
                    else
                    {
                        virgul=true;
                        numpad(args);
                    }
                }

            }
        }
        catch (Exception ex){}

        DisplayAmount();
    }

    public void Clear()
    {
        firstDigit = "0";
        for(int i = 0 ;i<digits.length;i++)
            digits[i] = "0";
        virgul = false;
        currentDigit = 0;
        setAmount(0);
    }

    public void onVirgulClick()
    {
        if (contiuneClear)
        {
            Clear();
            contiuneClear = false;
        }
        virgul = true;
    }

    public void Del() {
        if (contiuneClear)
        {
            Clear();
            contiuneClear = false;
        }
        String d = ("0");
        if (virgul) {
            if (currentDigit > digits.length)
                currentDigit = digits.length - 1;

            if (currentDigit > 0)
            {
                currentDigit--;
                digits[currentDigit] = d;

            } else
            {
                currentDigit = 0;
                virgul = false;
                Del();
            }

        } else
        {
            if (!firstDigit.equals("0"))
            {
                if (firstDigit.length()>1)
                    firstDigit = firstDigit.substring(0,firstDigit.length()-1);
                else firstDigit = "0";
            }
        }
        DisplayAmount();
    }

    public void nAmount(Object... args) {


        try
        {
            if (args.length == 1)
            {
                String d = (args[0].toString());
                AmountToText(parse.toDouble(d));
                contiuneClear = true;
            }
        }
        catch (Exception ex){}

    }

    public void Close()
    {
        Clear();
        dismiss();
    }

    public void Ok()
    {
        if (listener!=null)
            listener.onResponse(getAmount());
        dismiss();
    }

    private void DisplayAmount() {
        String a  = firstDigit+".";
        for (String s:digits)
            a+=s;
        setAmount(parse.toDouble(a));
    }

    private void AmountToText(Double a)
    {
        String sucret = yugi.NFReplace(a,digit).replace(".","-");
        String s[] = sucret.split("-");
        firstDigit=s[0];

        for (int i =0;i<digit ;i++)
            digits[i] = s[1].substring(i,i+1);

        DisplayAmount();
    }

    @Override
    public void show()
    {
        npTotal.setEnabled(getDigit() != 0);
        n2.setEnabled(getDigit() != 0);
        n3.setEnabled(getDigit() != 0);
        n4.setEnabled(getDigit() != 0);

        super.show();
    }
}
