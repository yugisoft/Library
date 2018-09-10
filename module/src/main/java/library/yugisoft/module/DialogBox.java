package library.yugisoft.module;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DialogBox {
    public static void showOK(String mesaj,IDialogResult result) {showOK(yugi.activity,mesaj,result);}
    public static void showOK(Activity activity , String mesaj, IDialogResult result) {showResult(activity,mesaj,EDialogButtons.OK,result);}

    public static void showYES(String mesaj,IDialogResult result) {showYES(yugi.activity,mesaj,result);}
    public static void showYES(Activity activity ,String mesaj,IDialogResult result) {showResult(activity,mesaj,EDialogButtons.YES,result);}

    public static void showNO(String mesaj,IDialogResult result) {showNO(yugi.activity,mesaj,result);}
    public static void showNO(Activity activity ,String mesaj,IDialogResult result) {showResult(activity,mesaj,EDialogButtons.NO,result);}

    public static void showYESNO(String mesaj,IDialogResult result) {showYESNO(yugi.activity,mesaj,result);}
    public static void showYESNO(Activity activity ,String mesaj,IDialogResult result) {showResult(activity,mesaj,EDialogButtons.YESNO,result);}

    public static void showRESULTTEXT(String mesaj,IDialogResult result) {showRESULTTEXT(yugi.activity,mesaj,result);}
    public static void showRESULTTEXT(Activity activity ,String mesaj,IDialogResult result) {showResult(activity,mesaj,EDialogButtons.RESULTTEXT,result);}

    public static void showResult(Activity activity, String mesaj, EDialogButtons buttons, IDialogResult result) {

        yugi.Dialog.mDialog dialog = new yugi.Dialog.mDialog(activity,R.layout.dialog_result1,true);

        TextView dialogMesaj = ((TextView)dialog.view.findViewById(R.id.dialogMesaj));
        EditText dialogEditText = (EditText)dialog.view.findViewById(R.id.dialogEditText);


        dialogMesaj.setText(mesaj);
        dialogEditText.setText(mesaj);


        switch (buttons)
        {
            case OK:
                dialog.view.findViewById(R.id.lyOK).setVisibility(View.VISIBLE);
                dialogMesaj.setVisibility(View.VISIBLE);
                break;
            case YES:
                dialog.view.findViewById(R.id.lyYes).setVisibility(View.VISIBLE);
                dialogMesaj.setVisibility(View.VISIBLE);
                break;
            case NO:
                dialog.view.findViewById(R.id.lyNO).setVisibility(View.VISIBLE);
                dialogMesaj.setVisibility(View.VISIBLE);
                break;
            case YESNO:
                dialogMesaj.setVisibility(View.VISIBLE);
                dialog.view.findViewById(R.id.lyYes).setVisibility(View.VISIBLE);
                dialog.view.findViewById(R.id.lyNO).setVisibility(View.VISIBLE);
                break;
            case RESULTTEXT:
                dialog.view.findViewById(R.id.lyOK).setVisibility(View.VISIBLE);
                dialogEditText.setVisibility(View.VISIBLE);
                break;
        }


        dialog.view.findViewById(R.id.resultOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sstr = dialogEditText.getText().toString();
                if (result!=null)
                    result.onResult(EDialogButtons.OK,sstr);
                dialog.dialog.dismiss();
            }
        });
        dialog.view.findViewById(R.id.resultNO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result!=null)
                    result.onResult(EDialogButtons.NO,"");
                dialog.dialog.dismiss();
            }
        });
        dialog.view.findViewById(R.id.resultYES).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result!=null)
                    result.onResult(EDialogButtons.YES,"");
                dialog.dialog.dismiss();
            }
        });

        dialog.dialog.show();

    }


    public enum EDialogButtons
    {
        OK,
        YES,
        NO,
        YESNO,
        RESULTTEXT
    }
    public interface IDialogResult
    {
        void onResult(EDialogButtons buttons,String result);
    }
}