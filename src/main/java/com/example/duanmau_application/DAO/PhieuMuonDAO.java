package com.example.duanmau_application.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau_application.Database.STLibHelper;
import com.example.duanmau_application.Model.PhieuMuon;
import com.example.duanmau_application.Model.ThanhVien;

import java.util.ArrayList;

public class PhieuMuonDAO {
    STLibHelper stLibHelper;
    public PhieuMuonDAO(Context context){
        stLibHelper=new STLibHelper(context);
    }

    public ArrayList<PhieuMuon>getDSPhieuMuon(){
        ArrayList<PhieuMuon>list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select pm.mapm, pm.matv, tv.hoten, pm.mauser, user.hoten, pm.masach, sc.tensach, pm.ngaythue, pm.trangthai, pm.giathue from PHIEUMUON pm, USER user, THANHVIEN tv, SACH sc where pm.matv=tv.matv and pm.mauser=user.mauser and pm.masach=sc.masach ORDER BY pm.mapm DESC",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new PhieuMuon(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<PhieuMuon>getDSPhieuMuonTT(String matt){
        ArrayList<PhieuMuon>list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select pm.mapm, pm.matv, tv.hoten, pm.mauser, user.hoten, pm.masach, sc.tensach, pm.ngaythue, pm.trangthai, pm.giathue from PHIEUMUON pm, USER user, THANHVIEN tv, SACH sc where pm.matv=tv.matv and pm.mauser=user.mauser and pm.masach=sc.masach and pm.mauser=? ORDER BY pm.mapm DESC",new String[]{matt});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new PhieuMuon(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<ThanhVien>getDSTTAdmin(){
        ArrayList<ThanhVien>list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=stLibHelper.getReadableDatabase();
//        Cursor cursor=sqLiteDatabase.rawQuery("select count(pm.mapm), user.mauser,user.hoten,sum(pm.giathue) from PHIEUMUON pm, USER user where pm.mauser=user.mauser and user.loaitaikhoan=\"thuthu\" GROUP BY user.mauser ",null);
        Cursor cursor=sqLiteDatabase.rawQuery("select mauser, hoten, namsinh, diachi from USER where loaitaikhoan=\"thuthu\" ",null);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {

                list.add(new ThanhVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }


    public boolean ThayDoiTrangThai(int mapm){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("trangthai",1);
        long check=sqLiteDatabase.update("PHIEUMUON",contentValues,"mapm=?",new String[]{String.valueOf(mapm)});
        if (check==-1){
            return false;
        }else{
            return true;
        }

    }
//    mapm integer primary key autoincrement, mauser text, matv integer, masach integer, " +
//            "ngaythue text, trangthai integer, giathue integer
    public boolean themPhieuMuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase=stLibHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("mauser",phieuMuon.getMauser());
        contentValues.put("matv",phieuMuon.getMatv());
        contentValues.put("masach",phieuMuon.getMasach());
        contentValues.put("ngaythue",phieuMuon.getNgay());
        contentValues.put("trangthai",phieuMuon.getTrangthai());
        contentValues.put("giathue",phieuMuon.getGiathue());

        long check=sqLiteDatabase.insert("PHIEUMUON",null,contentValues);
        if (check==-1){
            return false;
        }else{
            return true;
        }

    }
}
