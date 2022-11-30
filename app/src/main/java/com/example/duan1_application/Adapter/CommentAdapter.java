package com.example.duan1_application.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_application.R;
import com.example.duan1_application.model.Comment;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Comment>list;

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txttenngcmt.setText(list.get(position).getTenkh());
        holder.txtngaycmt.setText(list.get(position).getNgayCmt());
        holder.txtndcmt.setText(list.get(position).getComments());
        Glide.with(context)
                .load(list.get(holder.getAdapterPosition()).getHinhanh())
                .centerCrop()
                .into(holder.ivavtngcmt);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenngcmt,txtngaycmt,txtndcmt;
        ImageView ivavtngcmt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivavtngcmt=itemView.findViewById(R.id.ivavtngcmt);
            txttenngcmt=itemView.findViewById(R.id.txttenngcmt);
            txtngaycmt=itemView.findViewById(R.id.txtngaycmt);
            txtndcmt=itemView.findViewById(R.id.txtndcmt);
        }
    }
}
