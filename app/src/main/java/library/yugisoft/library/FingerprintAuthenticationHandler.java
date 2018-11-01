package library.yugisoft.library;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;

import library.yugisoft.module.DialogBox;


@TargetApi(Build.VERSION_CODES.M)
public class FingerprintAuthenticationHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;

    // Constructor
    public FingerprintAuthenticationHandler(Context mContext) {
        context = mContext;
    }



    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {


        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }
    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }
    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }
    @Override
    public void onAuthenticationSucceeded( FingerprintManager.AuthenticationResult result) {

        FingerprintManager.CryptoObject ob = result.getCryptoObject();
        this.update("Fingerprint Authentication succeeded.", true);

    }
    public void update(String e, Boolean success){
        DialogBox.showOK(e,null);
    }


    public void  vv()
    {
        FingerprintManager fingerprintManager ;

    }

}
