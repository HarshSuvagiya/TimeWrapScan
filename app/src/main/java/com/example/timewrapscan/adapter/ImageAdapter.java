package com.example.timewrapscan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timewrapscan.Activity.MyCreDisplayActivity;
import com.example.timewrapscan.Activity.Utl;
import com.example.timewrapscan.Fragment.ImageFragment;
import com.example.timewrapscan.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    Context context;
   public static ArrayList<String> iList;
    ImageFragment.DeleteClick deleteClick;
    public ImageAdapter(Context context,ArrayList<String> iList,ImageFragment.DeleteClick deleteClick){
        this.context=context;
        this.iList=iList;
        this.deleteClick=deleteClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(iList.get(position)).into(holder.imageView);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClick.getPosition(position);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context, MyCreDisplayActivity.class);
                i.putExtra("position",position);
                context.startActivity(i);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File file=new File(iList.get(position));
                Uri uri= FileProvider.getUriForFile(context,context.getPackageName(),file);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                context.startActivity(Intent.createChooser(intent,"Share using"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView share, delete,icon;
        RelativeLayout img_border;
        RoundedImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_view_adapter);
            share = itemView.findViewById(R.id.share);
            delete = itemView.findViewById(R.id.delete);

            img_border = itemView.findViewById(R.id.border);

            Utl.SetUILinearVivo(context, share, 60, 60);
            Utl.SetUILinearVivo(context, delete, 60, 60);
            Utl.SetUIRelative(context, imageView, 314, 556);
            Utl.SetUIRelative(context, img_border, 314, 556);

        }
    }
}
