package com.travisandjersy.safephoto;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.travisandjersy.safephoto.model.Photo;
import com.travisandjersy.safephoto.service.CloudDataService;
import com.travisandjersy.safephoto.service.LocalStorageService;
import com.travisandjersy.safephoto.service.PhotoService;

import java.util.List;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class PublicPhotosFragment extends ListFragment implements OnItemClickListener {

    private static final String TAG = "PhotosFragment";
    ArrayAdapter<String> adapter;
    List<Photo> photos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photos_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        photos = PhotoService.getPublicPhotos();
        List<String> photoNames = PhotoService.getPublicPhotoNames();
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, photoNames);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //CloudDataService.enable(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        CloudDataService.disable();
    }

    public void update() {
        photos = PhotoService.getPublicPhotos();
        List<String> photoNames = PhotoService.getPublicPhotoNames();
        adapter.clear();
        adapter.addAll(photoNames);
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.photo_view, null);
        builder.setView(dialogView);

        Dialog dialog = builder.create();
        Photo photo = photos.get(position);
        dialog.setTitle(photo.name);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_view);

        Bitmap bitmap = LocalStorageService.getImageWithName(photo.name);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.placeholder);
        }
        imageView.setImageBitmap(bitmap);
        dialog.show();
    }
}
