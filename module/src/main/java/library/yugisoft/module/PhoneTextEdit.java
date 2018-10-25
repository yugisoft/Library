package library.yugisoft.module;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.lang.ref.WeakReference;


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

    void init()
    {
        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter( new WeakReference<EditText>(this));
        this.addTextChangedListener(addLineNumberFormatter);
    }

    @Override
    public String toString() {
        return this.getText().toString().replace(" ","").replace("-","").replace("(","").replace(")","");
    }

    public INTERFACES.OnPhoneTextListener getOnPhoneTextListener() {
        return onPhoneTextListener;
    }

    public void setOnPhoneTextListener(INTERFACES.OnPhoneTextListener onPhoneTextListener) {
        this.onPhoneTextListener = onPhoneTextListener;
    }

    private class UsPhoneNumberFormatter implements TextWatcher {
        //This TextWatcher sub-class formats entered numbers as 1 (123) 456-7890
        private boolean mFormatting; // this is a flag which prevents the
        // stack(onTextChanged)
        private boolean clearFlag;
        private int mLastStartLocation;
        private String mLastBeforeText;
        private WeakReference<EditText> mWeakEditText;

        public UsPhoneNumberFormatter(WeakReference<EditText> weakEditText) {
            this.mWeakEditText = weakEditText;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            if (after == 0 && s.toString().equals("1 ")) {
                clearFlag = true;
            }
            mLastStartLocation = start;
            mLastBeforeText = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

        @Override
        public void afterTextChanged(Editable s) {
            // Make sure to ignore calls to afterTextChanged caused by the work
            // done below
            if (!mFormatting)
            {
                mFormatting = true;

                int curPos = mLastStartLocation;
                String beforeValue = mLastBeforeText;

                String currentValue = s.toString();
                String formattedValue = formatUsNumber(s);
                if (currentValue.length() > beforeValue.length())
                {
                    int setCusorPos = formattedValue.length() - (beforeValue.length() - curPos);
                    mWeakEditText.get().setSelection(setCusorPos < 0 ? 0 : setCusorPos);
                } else {
                    int setCusorPos = formattedValue.length()
                            - (currentValue.length() - curPos);
                    if(setCusorPos > 0 && !Character.isDigit(formattedValue.charAt(setCusorPos -1))){
                        setCusorPos--;
                    }
                    mWeakEditText.get().setSelection(setCusorPos < 0 ? 0 : setCusorPos);
                }
                if (s.toString().equals("0"))
                    s.clear();
                mFormatting = false;
            }
            if (onPhoneTextListener !=null && PhoneTextEdit.this.toString().length()==10)
                onPhoneTextListener.onPhoneText(PhoneTextEdit.this.toString());

        }



        String formatUsNumber(Editable text) {
            StringBuilder formattedString = new StringBuilder();
            // Remove everything except digits
            int p = 0;
            while (p < text.length()) {
                char ch = text.charAt(p);
                if (!Character.isDigit(ch)) {
                    text.delete(p, p + 1);
                } else {
                    p++;
                }
            }
            // Now only digits are remaining
            String allDigitString = text.toString();

            int totalDigitCount = allDigitString.length();

            if (totalDigitCount == 0
                    || (totalDigitCount > 10 && !allDigitString.startsWith("1"))
                    || totalDigitCount > 11) {
                // May be the total length of input length is greater than the
                // expected value so we'll remove all formatting
                text.clear();
                text.append(allDigitString);
                return allDigitString;
            }
            int alreadyPlacedDigitCount = 0;
            // Only '1' is remaining and user pressed backspace and so we clear
            // the edit text.
            if (allDigitString.equals("1") && clearFlag) {
                text.clear();
                clearFlag = false;
                return "";
            }
            if (allDigitString.startsWith("1")) {
                formattedString.append("1 ");
                alreadyPlacedDigitCount++;
            }
            // The first 3 numbers beyond '1' must be enclosed in brackets "()"
            if (totalDigitCount - alreadyPlacedDigitCount > 3) {
                formattedString.append("("
                        + allDigitString.substring(alreadyPlacedDigitCount,
                        alreadyPlacedDigitCount + 3) + ") ");
                alreadyPlacedDigitCount += 3;
            }
            // There must be a '-' inserted after the next 3 numbers
            if (totalDigitCount - alreadyPlacedDigitCount > 3) {
                formattedString.append(allDigitString.substring(
                        alreadyPlacedDigitCount, alreadyPlacedDigitCount + 3)
                        + "-");
                alreadyPlacedDigitCount += 3;
            }
            // All the required formatting is done so we'll just copy the
            // remaining digits.
            if (totalDigitCount > alreadyPlacedDigitCount) {
                formattedString.append(allDigitString
                        .substring(alreadyPlacedDigitCount));
            }

            text.clear();

            text.append(formattedString.toString());
            return formattedString.toString();
        }

    }


    private INTERFACES.OnPhoneTextListener onPhoneTextListener;
}
