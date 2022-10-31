package com.example.duanmau_application.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class STLibHelper extends SQLiteOpenHelper {
    public STLibHelper(Context context){
        super(context,"thuvien",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String dbthanhvien="CREATE TABLE THANHVIEN(matv integer primary key autoincrement," +
                " hoten text, namsinh text)";
        sqLiteDatabase.execSQL(dbthanhvien);

        String dbloaisach="CREATE TABLE LOAISACH(maloaisach integer primary key autoincrement," +
                " tenloaisach text)";
        sqLiteDatabase.execSQL(dbloaisach);

        String dbsach="CREATE TABLE SACH(masach integer primary key autoincrement," +
                " tensach text, giathue integer, maloaisach integer)";
        sqLiteDatabase.execSQL(dbsach);

        String dbuser="CREATE TABLE USER(mauser text primary key, hoten text," +
                " password text,loaitaikhoan text, namsinh text, diachi text)";
        sqLiteDatabase.execSQL(dbuser);

        String dbphieumuon="CREATE TABLE PHIEUMUON(mapm integer primary key autoincrement, mauser text, " +
                "matv integer, masach integer, " +
                "ngaythue text, trangthai integer, giathue integer)";
        sqLiteDatabase.execSQL(dbphieumuon);

        String instv="INSERT INTO THANHVIEN VALUES(1, 'Văn Tường',2003),(2, 'Tâm Như',2003)";
        sqLiteDatabase.execSQL(instv);

        String insloaisach="INSERT INTO LOAISACH VALUES(1,'Văn Học'),(2,'IT')";
        sqLiteDatabase.execSQL(insloaisach);

        String inssach="INSERT INTO SACH VALUES(1,'Android',5000,2),(2,'Chí Phèo',6000,1),(3,'Lão Hạc',7000,1)";
        sqLiteDatabase.execSQL(inssach);

        String insuser="INSERT INTO USER VALUES('ps23449','Trọng Tín', '123','admin','2003','AHT')," +
                "('tt01','Hồng Diệp', '123','thuthu','2003','HCM')," +
                "('tt02','Mỹ Tiên', '123','thuthu','2003','AT')";

        String insphieumuon="INSERT INTO PHIEUMUON VALUES(1,'tt01',1,1,'03/10/2022',1,5000)," +
                "(2,'tt02',1,2,'03/10/2022',0,6000),(3,'tt02',1,2,'04/10/2022',0,6000)";
        sqLiteDatabase.execSQL(insphieumuon);




        sqLiteDatabase.execSQL(insuser);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i!=i1){
            sqLiteDatabase.execSQL("Drop table if exists THANHVIEN");
            sqLiteDatabase.execSQL("Drop table if exists LOAISACH");
//            sqLiteDatabase.execSQL("Drop table if exists NXB");
//            sqLiteDatabase.execSQL("Drop table if exists TACGIA");
            sqLiteDatabase.execSQL("Drop table if exists SACH");
            sqLiteDatabase.execSQL("Drop table if exists USER");
            sqLiteDatabase.execSQL("Drop table if exists PHIEUMUON");
            onCreate(sqLiteDatabase);
        }

    }
}
