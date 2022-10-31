package com.example.duanmau_application.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau_application.DAO.SachDAO;
import com.example.duanmau_application.Model.Sach;
import com.example.duanmau_application.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHoler>{
    private Context context;
    private ArrayList<Sach>list;
    private ArrayList<HashMap<String, Object>>listHM;
    private SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list,ArrayList<HashMap<String, Object>>listHM,SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM=listHM;
        this.sachDAO=sachDAO;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.item_sach,parent,false);

        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

        holder.txtmasach.setText("Ma Sach: "+list.get(position).getMasach());
        holder.txttensach.setText("Ten Sach: "+list.get(position).getTensach());
        holder.txtgiathue.setText("Gia Thue: "+list.get(position).getGiathue());
        holder.txtloaisach.setText("Ma Loai Sach: "+list.get(position).getMaloai());
        holder.txttenloai.setText("Ten Loai: "+list.get(position).getTenloai());

        holder.ivsuathongtinsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiaLogSuaSach(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivxoasach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check=sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xoa Thanh Cong", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoa KO Thanh cong", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Ko the xoa sach nay. Co sach dang muon", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView txtmasach, txttensach, txtloaisach, txtgiathue,txttenloai;
        ImageView ivsuathongtinsach,ivxoasach;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            txtmasach=itemView.findViewById(R.id.txtmasach);
            txttensach=itemView.findViewById(R.id.txttensach);
            txtloaisach=itemView.findViewById(R.id.txtmaloai);
            txtgiathue=itemView.findViewById(R.id.txtgiathue);
            txttenloai=itemView.findViewById(R.id.txttenloai);
            ivsuathongtinsach=itemView.findViewById(R.id.ivsuathongtinsach);
            ivxoasach=itemView.findViewById(R.id.ivxoasach);
        }
    }
    private void showDiaLogSuaSach(Sach sach){
        Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suathongtinsach);

        Window window=dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button btnhuycapnhatsach=dialog.findViewById(R.id.btnhuycapnhatsach);
        Button btncapnhatsach=dialog.findViewById(R.id.btncapnhatsachsach);

        EditText edtsuatensach=dialog.findViewById(R.id.edtsuatensach);
        EditText edtsuagiathue=dialog.findViewById(R.id.edtsuagiathue);
        TextView txtmasach=dialog.findViewById(R.id.txtmasach);
        Spinner spnsualoaisach=dialog.findViewById(R.id.spnsualoaisach);

        txtmasach.setText("Ma Sach: "+sach.getMasach());
        edtsuatensach.setText(sach.getTensach());
        edtsuagiathue.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter=new SimpleAdapter(context,
                listHM, android.R.layout.simple_list_item_1,new String[]{"tenloai"},new int[]{android.R.id.text1});
        spnsualoaisach.setAdapter(simpleAdapter);

        btnhuycapnhatsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


//        int index=0;
//        int postion=-1;
//        for (HashMap<String, Object>item:listHM){
//            if ((int)item.get("maloai")==sach.getMaloai()){
//                postion=index;
//            }
//            index++;
//        }
//        spnsualoaisach.setSelection(postion);

        btncapnhatsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach=edtsuatensach.getText().toString();
                int giathue=Integer.parseInt(edtsuagiathue.getText().toString());
                HashMap<String, Object>hs=(HashMap<String, Object>) spnsualoaisach.getSelectedItem();
                int maloai=(int) hs.get("maloai");

                boolean check=sachDAO.capnhatTHongTinSach(sach.getMasach(),tensach,giathue,maloai);
                if (check){
                    Toast.makeText(context, "Them Thanh Cong!", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();

                }else{
                    Toast.makeText(context, "Them That Bai", Toast.LENGTH_SHORT).show();
                }
            }
        });


        dialog.show();

    }

    private void loadData(){
        list.clear();
        list=sachDAO.getDSDauSach();
        notifyDataSetChanged();
    }
//    public void showDialog(ArrayList<ThongTin>list){
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        LayoutInflater inflater=((Activity)context).getLayoutInflater();
//        View view=inflater.inflate(R.layout.dialog_thongtin,null);
//        builder.setView(view);
//        ListView lvitemtt=view.findViewById(R.id.lvitemtt);
//
//        ArrayList<HashMap<String ,Object>>listHM=new ArrayList<>();
//        for (ThongTin tt:list){
//            HashMap<String, Object>hs=new HashMap<>();
//            hs.put("data","Ngay hoc: "+tt.getDate());
//            hs.put("address","Dia diem: "+tt.getAddress());
//            listHM.add(hs);
//        }
//        SimpleAdapter simpleAdapter=new SimpleAdapter(context,listHM, android.R.layout.simple_list_item_2,new String[]{"data","address"},new int[]{android.R.id.text1,android.R.id.text2});
//        lvitemtt.setAdapter(simpleAdapter);
//
//        AlertDialog dialog=builder.create();
//        dialog.show();
//    }
}
