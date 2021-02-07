package com.gzeinnumer.mylibsavedinstancestateexample.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToastDown {

    public CustomToastDown(Context applicationContext, String msg) {
        Toast toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 600);
        toast.show();
    }
}
