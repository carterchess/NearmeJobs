package com.modernizeworkers.jobapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import static android.R.id.message;

/**
 * Created by Bino on 9/1/2017.
 */

public class Utils {

    public static boolean isNetworkAvailable(Context context) {

        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }


    public static void showErrorToast(Context context, String message) {
        MDToast mdToast = MDToast.makeText(context, message, Toast.LENGTH_LONG, MDToast.TYPE_ERROR);
        mdToast.show();
    }


    public static void showSuccesToast(Context context, String message) {
        MDToast mdToast = MDToast.makeText(context, message, Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
        mdToast.show();
    }

    public static void showInfoToast(Context context, String message) {
        MDToast mdToast = MDToast.makeText(context, message, Toast.LENGTH_LONG, MDToast.TYPE_INFO);
        mdToast.show();
    }


}
