package library.yugisoft.module;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class CheckPermission extends yugi.vActivity {

    private static final int PERMISSION = 256;
    public static INTERFACES.OnResponse<Boolean> onResponse;

    public static void checkPermissionResult(String[] permis, INTERFACES.OnResponse<Boolean> onResponse)
    {
        onResponse = onResponse;
        permissions=permis;
        yugi.activity.startActivity(new Intent(yugi.activity,CheckPermission.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isCheckSelfPermission())
        {
            this.finish();
            onResponse.onResponse(true);
        }
        else
            checkPermissions();
    }

    static String[] permissions ;
    String[] wantPermissions ;

    private void checkPermissions()
    {
        if (!isCheckSelfPermission())
        {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION);
        }
        else
        {
            this.finish();
            onResponse.onResponse(true);
        }
    }

    boolean isCheckSelfPermission() {
        for (String item:permissions)
        {
            if ((ContextCompat.checkSelfPermission(this, item ) != PackageManager.PERMISSION_GRANTED))
            {
                return false;
            }
        }
        return  true;
    }

    @Override
    public void onBackPressed() {
        onResponse.onResponse(false);
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode== PERMISSION)
        {
            this.finish();
            onResponse.onResponse(isCheckSelfPermission());
        }
    }
}