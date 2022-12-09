package com.example.duan1_application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;

public class ShowNotification {
    private static AppCompatDialog mProgressDialog;

    public static void showProgressDialog(Context context, String message) {
        mProgressDialog = new AppCompatDialog(context);
   //     mProgressDialog.setIndeterminate(true);
   //     mProgressDialog.setMessage(message);
        View view= LayoutInflater.from(context).inflate(R.layout.layout_progress,null);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(view);
        ImageView ivprogress=view.findViewById(R.id.ivprogress);
        Glide.with(context).load(R.mipmap.progress).override(300,200).into(ivprogress);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }
    public static void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    public  static void showAlertDialog(Context context, String message){
        new AlertDialog.Builder(context)
                .setTitle("Thông báo")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
