package com.example.timewrapscan.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.timewrapscan.Activity.Utl;
import com.example.timewrapscan.R;
import com.example.timewrapscan.adapter.ImageAdapter;
import com.example.timewrapscan.adapter.VideoAdapter;

import java.io.File;
import java.util.ArrayList;

public class VideoFragment extends Fragment {
    RecyclerView recyclerView;

    TextView textView_noData;
    ProgressBar progressBar;
    ArrayList<String> videoList=new ArrayList<>();
    VideoAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressbar_fragment);
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
        textView_noData = view.findViewById(R.id.textView_noData_fragment);
        textView_noData.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void getData() {

        progressBar.setVisibility(View.GONE);
        if(videoList.size()>0){
            videoList.clear();
        }
        getfolderdata(create_folder(getResources().getString(R.string.app_name)));

        if (videoList.size() == 0) {
            textView_noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textView_noData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new VideoAdapter(getContext(), videoList, new VideoFragment.DeleteClick() {
                @Override
                public void getPosition(int a) {
                    deleteI(a);
                }
            });
            recyclerView.setAdapter(adapter);
        }

    }
    public interface DeleteClick {
        public void getPosition(int a);
    }
    public static String create_folder(String foldername) {
        File folder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            folder = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES )+ File.separator  + foldername+"/"+"Videos"+"/"));


            if (!folder.exists()) {
                folder.mkdirs();
            }
            return folder.getAbsolutePath();

        } else {
            folder = new File(Environment.getExternalStorageDirectory() + "/" + foldername+"/"+"Videos"+"/");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            return folder.getAbsolutePath();

        }

    }
    public ArrayList<String> getfolderdata(String folderpath) {
        File checkfolderpath = new File(folderpath);
        if (checkfolderpath.exists()) {
            File listoffiles = new File(folderpath);
            //  Log.d(TAG, "onResume: " + listoffiles);
            File[] imglist = listoffiles.listFiles();
            if (imglist != null) {
                if (imglist.length != 0) {

                    for (int i = 0; i < imglist.length; i++) {
                        videoList.add(imglist[i].toString());

                    }
                    return videoList;
                }
                else {
                    return videoList;
                }
            }
        }
        return null;
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    public void deleteI(int a) {
        final Dialog delete = new Dialog(getContext());
        delete.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        delete.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        delete.setContentView(R.layout.custom_delete_dailog);
        final LinearLayout linerMainDeleteDialog = delete.findViewById(R.id.linerMainDeleteDialog);
        ImageView imageViewCancel = delete.findViewById(R.id.imageViewCancel);
        ImageView imageViewDelete = delete.findViewById(R.id.imageViewDelete);

        Utl.SetUILinear(getContext(),linerMainDeleteDialog,980,788);
        Utl.SetUILinear(getContext(),imageViewCancel,228,100);
        Utl.SetUILinear(getContext(),imageViewDelete,228,100);
        delete.show();
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.dismiss();
            }
        });
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteVideo(a);
                getData();
                delete.dismiss();
            }
        });
    }
    private void DeleteVideo(int p) {
        File file1 = new File(videoList.get(p));
        if (file1.exists()) {
            file1.delete();
            videoList.remove(p);
            recyclerView.getAdapter().notifyDataSetChanged();
            adapter = new VideoAdapter(getContext(), videoList, new VideoFragment.DeleteClick() {
                @Override
                public void getPosition(int a) {

                }
            });
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if(videoList.size()==0){
                textView_noData.setVisibility(View.VISIBLE);
            }

        }
    }
}
