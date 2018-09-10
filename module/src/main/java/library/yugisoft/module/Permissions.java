package library.yugisoft.module;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class Permissions
{
    public static void RequestPermissions(View view, final String permission, final int requestID) {
        RequestPermissions(view,permission,requestID);
    }
    public static void RequestPermissions(View view, final String[] permission, final int requestID) {
        RequestPermissions(view,permission,requestID);
        }
    public static void RequestPermissions(final Activity activity, View view, final String permission, final int requestID) {

        if ((ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)))
            {


            }
            else
            {
                ActivityCompat.requestPermissions(activity, new String[]{permission},requestID);
            }

        }



    }
    public static void RequestPermissions(final Activity activity, View view, final String[] permission, final int requestID) {

        for (int i = 0 ;i<permission.length;i++)
        {
            if ((ContextCompat.checkSelfPermission(activity, permission[i]) != PackageManager.PERMISSION_GRANTED)) {

                if ((ActivityCompat.shouldShowRequestPermissionRationale(activity, permission[i])))
                {


                }
                else
                {
                    ActivityCompat.requestPermissions(activity, new String[]{permission[i]},requestID);
                }

            }
        }


    }
}
