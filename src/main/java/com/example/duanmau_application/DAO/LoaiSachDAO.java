package com.example.duanmau_application.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau_application.Database.STLibHelper;
import com.example.duanmau_application.Model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    STLibHelper stLibHelper;
    public LoaiSachDAO(Context context){
        stLibHelper=new STLibHelper(context);
    }
    public ArrayList<LoaiSach>getDSTheLoai(){
        ArrayList<LoaiSach>list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select*from LOAISACH",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new LoaiSach(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean themLoaiSach(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tenloaisach", loaiSach.getName());
        long check=sqLiteDatabase.insert("LOAISACH",null,contentValues);
        if (check==-1){
            return false;
        }else{
            return true;
        }
    }
    //1: thanh cong; 0: that bai; -1: ko dc phep
    public  int deleteTheLoai(int id){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from SACH where maloaisach=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1;
        }else{
            long check=sqLiteDatabase.delete("LOAISACH", "maloaisach=?", new String[]{String.valueOf(id)});
            if (check==-1){
                return 0;
            }else{
                return 1;
            }
        }

    }
    public boolean suaLoaiSach(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tenloaisach",loaiSach.getName());
        long check=sqLiteDatabase.update("LOAISACH",contentValues,"maloaisach=?",new String[]{String.valueOf(loaiSach.getCode())});
        if (check==-1){
            return false;
        }else{
            return true;
        }
    }
}
