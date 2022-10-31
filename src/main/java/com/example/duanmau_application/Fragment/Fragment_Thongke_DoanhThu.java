package com.example.duanmau_application.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.R;

import java.util.Calendar;

public class Fragment_Thongke_DoanhThu extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_thongke_doanhthu,container,false);

        EditText edtbatdau=view.findViewById(R.id.edtbatdau);
        EditText edtketthuc=view.findViewById(R.id.edtketthuc);
        Button btnthongke=view.findViewById(R.id.btnthongke);
        TextView txtketqua=view.findViewById(R.id.txtketqua);

        Calendar calendar=Calendar.getInstance();

        edtbatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String ngay= "";
                                String thang= "";
                                if (i2<10){
                                    ngay= "0" +i2;
                                }else{
                                    ngay= String.valueOf(i2);
                                }
                                if ((i1+1)<10){
                                    thang="0"+(i1+1);
                                }else{
                                    thang=String.valueOf(i1+1);
                                }
                                edtbatdau.setText(i+"/"+thang+"/"+ngay);
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        edtketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String ngay= "";
                        String thang= "";
                        if (i2<10){
                            ngay= "0" +i2;
                        }else{
                            ngay= String.valueOf(i2);
                        }
                        if ((i1+1)<10){
                            thang="0"+(i1+1);
                        }else{
                            thang=String.valueOf(i1+1);
                        }
                        edtketthuc.setText(i+"/"+thang+"/"+ngay);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        btnthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SachDAO sachDAO=new SachDAO(getContext());
                String ngaybatdau=edtbatdau.getText().toString();
                String ngayketthuc=edtketthuc.getText().toString();
                int doanhthu=sachDAO.getDoanhThu(ngaybatdau,ngayketthuc);
                txtketqua.setText(doanhthu+" VND");


            }
        });

        return view;
    }
}
