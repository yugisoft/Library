package library.yugisoft.module;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DialogBox
{
    public static int layoutStyle = R.layout.dialog_result1;

    //region showOK
    public static void showOK(String mesaj,IDialogResult result) {showOK(yugi.activity,mesaj,"Uyarı",result);}
    public static void showOK(String mesaj,String title,IDialogResult result) {showOK(yugi.activity,mesaj,title,result);}
    public static void showOK(Activity activity , String mesaj,String title, IDialogResult result) {showResult(activity,mesaj,title,EDialogButtons.OK,result);}
    //endregion

    //region showYES
    public static void showYES(String mesaj,IDialogResult result) {showYES(yugi.activity,mesaj,"Uyarı",result);}
    public static void showYES(String mesaj,String title,IDialogResult result) {showYES(yugi.activity,mesaj,title,result);}
    public static void showYES(Activity activity ,String mesaj,String title,IDialogResult result) {showResult(activity,mesaj,title,EDialogButtons.YES,result);}
    //endregion

    //region showNO
    public static void showNO(String mesaj,IDialogResult result) {showNO(yugi.activity,mesaj,"Uyarı",result);}
    public static void showNO(String mesaj,String title,IDialogResult result) {showNO(yugi.activity,mesaj,title,result);}
    public static void showNO(Activity activity ,String mesaj,IDialogResult result) {showResult(activity,mesaj,"Uyarı",EDialogButtons.NO,result);}
    public static void showNO(Activity activity ,String mesaj,String title,IDialogResult result) {showResult(activity,mesaj,title,EDialogButtons.NO,result);}
    //endregion

    //region showYESNO
    public static void showYESNO(String mesaj,IDialogResult result) {showYESNO(yugi.activity,mesaj,"Uyarı",result);}
    public static void showYESNO(String mesaj,String title,IDialogResult result) {showYESNO(yugi.activity,mesaj,title,result);}
    public static void showYESNO(Activity activity ,String mesaj,IDialogResult result) {showResult(activity,mesaj,"Uyarı",EDialogButtons.YESNO,result);}
    public static void showYESNO(Activity activity ,String mesaj,String title,IDialogResult result) {showResult(activity,mesaj,title,EDialogButtons.YESNO,result);}
    //endregion

    //region showRESULTTEXT
    public static void showRESULTTEXT(String mesaj,IDialogResult result) {showRESULTTEXT(yugi.activity,mesaj,result);}
    public static void showRESULTTEXT(String mesaj,String title,IDialogResult result) {showRESULTTEXT(yugi.activity,mesaj,title,result);}
    public static void showRESULTTEXT(Activity activity ,String mesaj,IDialogResult result) {
        showResult(activity,mesaj,"Uyarı",EDialogButtons.RESULTTEXT,result);
    }
    public static void showRESULTTEXT(Activity activity ,String mesaj, String title,IDialogResult result) {
        showResult(activity,mesaj,title,EDialogButtons.RESULTTEXT,result);
    }
    //endregion

    //region showResult
    public static void showResult(Activity activity , String mesaj, String title, EDialogButtons buttons, IDialogResult result) {

        yugi.Dialog.mDialog dialog = new yugi.Dialog.mDialog(activity,R.layout.dialog_result1,true);

        dialog.dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView dialogMesaj = ((TextView)dialog.view.findViewById(R.id.dialogMesaj));
        TextView dialogTitle = ((TextView)dialog.view.findViewById(R.id.dialogTitle));
        EditText dialogEditText = (EditText)dialog.view.findViewById(R.id.dialogEditText);
        dialogEditText.setOnKeyListener((view, i, keyEvent) -> {

            if (keyEvent.getAction() == KeyEvent.ACTION_UP && ( keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER || keyEvent.getKeyCode() == KeyEvent.KEYCODE_NUMPAD_ENTER ))
            {
                String sstr = dialogEditText.getText().toString();
                if (result!=null)
                    result.onResult(EDialogButtons.OK,sstr);
                dialog.dialog.dismiss();
            }
            return false;
        });

        dialogMesaj.setText(mesaj);
        dialogEditText.setText(mesaj);
        dialogTitle.setText(title);


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
    //endregion

    public enum EDialogButtons {
        OK,
        YES,
        NO,
        YESNO,
        RESULTTEXT
    }
    public interface IDialogResult {
        void onResult(EDialogButtons buttons,String result);
    }

}
