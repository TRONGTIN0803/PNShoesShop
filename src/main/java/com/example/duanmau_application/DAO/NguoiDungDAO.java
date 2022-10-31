package com.example.duanmau_application.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau_application.Database.STLibHelper;

public class NguoiDungDAO {
    private STLibHelper stLibHelper;
    private SharedPreferences sharedPreferences;

    public NguoiDungDAO(Context context){
        stLibHelper=new STLibHelper(context);
        sharedPreferences=context.getSharedPreferences("THONGTIN",Context.MODE_PRIVATE);
    }
    public boolean KiemTraDangNhap(String mauser, String password){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from USER where mauser=? and password=?",new String[]{mauser,password});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("mauser",cursor.getString(0));
            editor.putString("loaitaikhoan",cursor.getString(3));
            editor.putString("hoten",cursor.getString(1));
            editor.putString("password",cursor.getString(2));
            editor.putString("namsinh",cursor.getString(4));
            editor.putString("diachi",cursor.getString(5));
            editor.commit();

            return true;
        }else {
            return false;
        }
    }
    public boolean DangKy(String mauser, String password,String hoten,String loaitaikhoan,String namsinh, String diachi){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("mauser",mauser);
        contentValues.put("password",password);
        contentValues.put("hoten",hoten);
        contentValues.put("loaitaikhoan",loaitaikhoan);
        contentValues.put("namsinh",namsinh);
        contentValues.put("diachi",diachi);
        long check=sqLiteDatabase.insert("USER",null,contentValues);
        if (check==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean capnhatThongTinTT(String matt, String hoten, String namsinh, String diachi){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("mauser",matt);
        contentValues.put("hoten",hoten);
        contentValues.put("namsinh",namsinh);
        contentValues.put("diachi",diachi);
        long check=sqLiteDatabase.update("USER",contentValues,"mauser=?",new String[]{matt});
        if (check==-1){
            return false;
        }else {
            return true;
        }

    }

    public int xoaTT(String matt){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE mauser=?",new String[]{String.valueOf(matt)});
        if (cursor.getCount()!=0){
            return -1;
        }
        long check=sqLiteDatabase.delete("USER","mauser=?",new String[]{ String.valueOf(matt)});
        if (check==-1){
            return 0;
        }else{
            return 1;
        }
    }
    public String QuenMatKhau(String mauser){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * from USER WHERE mauser=?",new String[]{mauser});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            String dulieu=cursor.getString(2);
            return "Mật khẩu: "+dulieu;
        }else{
            return "Không tìm thấy mật khẩu cho tài khoản này";
        }
    }


    public boolean capnhatmatkhau(String username, String oldpass, String newpass){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select*from USER where mauser=? and password=?",new String[]{username,oldpass});
        if (cursor.getCount()>0){
            ContentValues contentValues=new ContentValues();
            contentValues.put("password",newpass);
            long check=sqLiteDatabase.update("USER",contentValues,"mauser=?",new String[]{username});
            if (check==-1){
                return false;
            }else{
                return true;
            }

        }else{
            return false;
        }
    }
}
