package com.modernizeworkers.jobapplication.webservice;

import android.content.Context;

/**
 * Created by Bino on 5/16/2017.
 */
public class ErrorUtils {

    public static String errorMessage(Context context,int code) {

        String  token = "";

        switch (code)
        {
            case 401:
                token="User Not Exist";
                break;
            case 403:
                token="Technical error try again later";
                break;
            case 500:
                token="Technical error try again later";
                break;

            case 409:
                token="Email id already exist";
                break;

            case 400:
                token="Technical error try again later";
                break;

            default:
                break;

        }

        return token;
    }



}
