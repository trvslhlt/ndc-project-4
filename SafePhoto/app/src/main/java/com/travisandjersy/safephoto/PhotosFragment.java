package com.travisandjersy.safephoto;

import android.app.Dialog;
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

import com.travisandjersy.safephoto.service.PhotoService;

import java.util.List;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class PhotosFragment extends ListFragment implements OnItemClickListener{

    private static final String TAG = "PhotosFragment";
    ArrayAdapter<String> adapter;
    List<String> photoNames;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photos_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        photoNames = PhotoService.getPhotoNames();
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, photoNames);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

//        use this on data update
//        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.photo_view, null);
        ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_view);
        builder.setView(dialogView);

        Dialog dialog = builder.create();

        String photoName = photoNames.get(position);
//        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(android.R.layout.select_dialog_item);
        dialog.setTitle(photoName);
        dialog.show();

//        Toast.makeText(getActivity(), "Selected: " + photoName, Toast.LENGTH_LONG   ).show();
    }
}
