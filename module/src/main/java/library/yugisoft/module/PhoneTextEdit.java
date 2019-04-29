package library.yugisoft.module;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import library.yugisoft.module.decoro.MaskImpl;
import library.yugisoft.module.decoro.slots.PredefinedSlots;
import library.yugisoft.module.decoro.watchers.FormatWatcher;
import library.yugisoft.module.decoro.watchers.MaskFormatWatcher;

public class PhoneTextEdit extends android.support.v7.widget.AppCompatEditText {


    public PhoneTextEdit(Context context) {
        super(context);
        init();
    }

    public PhoneTextEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhoneTextEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int oldSize = 0;
    void init()
    {
        //  PhoneNumberFormattingTextWatcher addLineNumberFormatter = new PhoneNumberFormattingTextWatcher();
        //  this.addTextChangedListener(addLineNumberFormatter);
        this.setInputType(InputType.TYPE_CLASS_PHONE);
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.TR_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(this);

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (oldSize!=PhoneTextEdit.this.getText().length() && (PhoneTextEdit.this.getText().length() == mask.getSize()))
                {
                    if (onPhoneTextListener!=null)
                        onPhoneTextListener.onPhoneText(PhoneTextEdit.this.toString());
                }
                oldSize = PhoneTextEdit.this.getText().length();
            }
        });

    }

    @Override
    public String toString() {
        return this.getText().toString().replace(" ","").replace("-","").replace("(","").replace(")","").replace("+90","");
    }

    public INTERFACES.OnPhoneTextListener getOnPhoneTextListener() {
        return onPhoneTextListener;
    }

    public void setOnPhoneTextListener(INTERFACES.OnPhoneTextListener onPhoneTextListener) {
        this.onPhoneTextListener = onPhoneTextListener;
    }




    private INTERFACES.OnPhoneTextListener onPhoneTextListener;
}