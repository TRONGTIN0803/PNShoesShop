package com.example.duanmau_application.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.duanmau_application.Database.STLibHelper;
import com.example.duanmau_application.Model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {

    STLibHelper stLibHelper;
    public ThanhVienDAO(Context context){
        stLibHelper=new STLibHelper(context);
    }
    public ArrayList<ThanhVien>getDSThanhVien(){
        ArrayList<ThanhVien>list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT*FROM THANHVIEN",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }


        return list;
    }
    public boolean themTHanhVien(String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("hoten",hoten);
        contentValues.put("namsinh",namsinh);
        long check=sqLiteDatabase.insert("THANHVIEN",null,contentValues);
        if (check==-1){
            return false;
        }else{
            return true;
        }

    }
    public boolean suaThongTinThanhVien(int matv, String hoten, String namsinh){

        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("hoten",hoten);
        contentValues.put("namsinh",namsinh);
        long check=sqLiteDatabase.update("THANHVIEN",contentValues,"matv=?",new String[]{String.valueOf(matv)});
        if (check==-1){
            return false;
        }else{
            return true;
        }
    }
    public int xoaThanhVien(int matv){
        SQLiteDatabase sqLiteDatabase= stLibHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE matv=?",new String[]{String.valueOf(matv)});
        if (cursor.getCount()!=0){
            return -1;
        }
        long check=sqLiteDatabase.delete("THANHVIEN","matv=?",new String[]{String.valueOf(matv)});
        if (check==-1){
            return 0;
        }else{
            return 1;
        }
    }
}
