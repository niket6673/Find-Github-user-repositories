package com.example.niket.jsonparserandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ContactViewHolder> {

    Context context;
    ArrayList<POJO> arrayList;

    public Adapter(Context context, ArrayList<POJO> arrayList) {

        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public Adapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter.ContactViewHolder holder, int position) {
        POJO pojo = arrayList.get(position);


        Glide.with(context).load(pojo.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
        holder.repoName.setText(pojo.getRepoName());
        holder.lastUpdated.setText(pojo.getLastUpdated());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView repoName, lastUpdated;

        public ContactViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.avtar);
            repoName = itemView.findViewById(R.id.repo);
            lastUpdated = itemView.findViewById(R.id.lastupdated);
        }
    }
}
