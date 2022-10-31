package com.example.duanmau_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.duanmau_application.Model.LoaiSach;

import java.util.ArrayList;

public class TheLoai_Activity extends AppCompatActivity {
    private ArrayList<LoaiSach>list;
    private RecyclerView recyclerViewtheloai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
//        FloatingActionButton btnadd=findViewById(R.id.frmbuttonadd);
//        recyclerViewtheloai=findViewById(R.id.recycleviewtheloai);
//
//        QLSDAO qlsdao=new QLSDAO(this);
//        list=qlsdao.getDSTheLoai();
//
//
//        loadData();
//
//        btnadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Dialog dialog=new Dialog(TheLoai_Activity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.layout_dialog_add_dulieu);
//                EditText edtdialogcode=dialog.findViewById(R.id.edtdialogcode);
//                EditText edtdialogname=dialog.findViewById(R.id.edtdialogname);
//                Button btndialogadd=dialog.findViewById(R.id.btndialogadd);
//
//                btndialogadd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int code=Integer.parseInt(edtdialogcode.getText().toString());
//                        String name=edtdialogname.getText().toString();
//                        QLS qls=new QLS(code,name);
//                        boolean check=qlsdao.themLoaiSach(qls);
//                        if (check){
//                            Toast.makeText(TheLoai_Activity.this, "Them Thanh Cong", Toast.LENGTH_SHORT).show();
//                            list= qlsdao.getDSTheLoai();
//                            loadData();
//                        }else{
//                            Toast.makeText(TheLoai_Activity.this, "Them That Bai!", Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//            }
//        });
    }
//    public void loadData(){
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        recyclerViewtheloai.setLayoutManager(linearLayoutManager);
//        QLSAdapter adapter=new QLSAdapter(this,list);
//        recyclerViewtheloai.setAdapter(adapter);
//    }
}