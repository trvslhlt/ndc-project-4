package com.travisandjersy.safephoto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.travisandjersy.safephoto.model.Photo;
import com.travisandjersy.safephoto.service.PhotoService;

import java.util.ArrayList;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         String photoName = photoNames.get(position);
        Toast.makeText(getActivity(), "Selected: " + photoName, Toast.LENGTH_LONG   ).show();
    }
}
