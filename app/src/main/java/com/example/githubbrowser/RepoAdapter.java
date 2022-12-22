package com.example.githubbrowser;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyViewHolder> {
    List<ModelClass> data;
    Context context;

    public RepoAdapter(List<ModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RepoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.MyViewHolder holder, int position) {
        int pos = holder.getAbsoluteAdapterPosition();
        holder.repo_name.setText(data.get(pos).getRepo_name());
        holder.description.setText(data.get(pos).getDescription());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sub = data.get(pos).getHtml_url();
                intent.putExtra(Intent.EXTRA_TEXT, sub);
                context.startActivity(Intent.createChooser(intent, "Share Using"));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelClass result = data.get(pos);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("repo name", result.getRepo_name());
                intent.putExtra("description", result.getDescription());
                intent.putExtra("issues count", result.getIssues_cnt());
                intent.putExtra("url", result.getHtml_url());
                intent.putExtra("DocId", result.getDocids());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView repo_name, description;
        ImageView share;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            repo_name = itemView.findViewById(R.id.repo_name);
            description = itemView.findViewById(R.id.description);
            share = itemView.findViewById(R.id.share);

        }
    }
}
