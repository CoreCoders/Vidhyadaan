package com.unbeatable.vidhyadaan;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Rakshit on 03-09-2016 at 13:47.
 */
public class Utils {

    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }

    }


    public static boolean isFormValid(ViewGroup group) {
        boolean isValid=true;
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                EditText et=(EditText) view;
                if(et.getText().toString().isEmpty()){
                    et.setError("field required");
                    isValid=false;
                }
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                isFormValid((ViewGroup) view);
        }

        return isValid;

    }

}
