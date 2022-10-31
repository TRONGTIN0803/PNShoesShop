package com.example.duanmau_application.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau_application.Database.STLibHelper;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.Model.Sach;

import java.util.ArrayList;

public class SachDAO {
    STLibHelper stLibHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SachDAO(Context context){
        stLibHelper=new STLibHelper(context);
    }

    public ArrayList<Sach>getDSDauSach(){
        ArrayList<Sach>list=new ArrayList<>();
        sqLiteDatabase=stLibHelper.getReadableDatabase();
//        Cursor cursor=sqLiteDatabase.rawQuery("SELECT*FROM SACH",null);
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloaisach, ls.tenloaisach FROM SACH sc, LOAISACH ls WHERE sc.maloaisach=ls.maloaisach",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<PhieuMuon>getDoanhThuThuThu(){
        ArrayList<PhieuMuon>list=new ArrayList<>();
        sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select user.mauser, user.hoten, sum(pm.giathue) from PHIEUMUON pm, USER user where pm.mauser=user.mauser and user.loaitaikhoan=\"thuthu\" GROUP BY pm.mauser ORDER BY sum(pm.giathue) DESC",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getString(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Sach>getDSTop10(){
        ArrayList<Sach>list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select pm.masach, sc.tensach, Count(pm.masach) from PHIEUMUON pm, SACH sc WHERE pm.masach=sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public int getDoanhThu(String ngaybatdau, String ngayketthuc){
        ngaybatdau=ngaybatdau.replace("/","");
        ngayketthuc=ngayketthuc.replace("/","");
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT SUM(giathue) FROM PHIEUMUON WHERE substr(ngaythue,7)||substr(ngaythue,4,2)||substr(ngaythue,1,2) BETWEEN ? AND ?",new String[]{ngaybatdau,ngayketthuc});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    public boolean themSach(String tensach,int giathue, int maloai){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tensach",tensach);
        contentValues.put("giathue",giathue);
        contentValues.put("maloaisach",maloai);
        long check=sqLiteDatabase.insert("SACH",null,contentValues);
        if (check==-1) {
            return false;
        }else{
            return true;
        }
    }
    public boolean capnhatTHongTinSach(int masach,String tensach, int giathue,int maloai){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("tensach",tensach);
        contentValues.put("giathue",giathue);
        contentValues.put("maloaisach",maloai);
        long check=sqLiteDatabase.update("SACH",contentValues,"masach=?",new String[]{String.valueOf(masach)});
        if (check==-1){
            return false;
        }else{
            return true;
        }

    }

    public int xoaSach(int masach){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE masach=?",new String[]{String.valueOf(masach)});
        if (cursor.getCount()!=0){
            return -1;
        }
        long check=sqLiteDatabase.delete("SACH","masach=?",new String[]{ String.valueOf(masach)});
        if (check==-1){
            return 0;
        }else{
            return 1;
        }
    }
}
