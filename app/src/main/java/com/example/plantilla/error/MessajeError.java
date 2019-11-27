package com.example.plantilla.error;

import android.app.Activity;
import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MessajeError {

    public   void errorMessege(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show();
    }
}
