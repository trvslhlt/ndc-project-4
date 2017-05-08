package com.travisandjersy.safephoto;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.travisandjersy.safephoto.model.Photo;
import com.travisandjersy.safephoto.service.AuthenticationService;
import com.travisandjersy.safephoto.service.CloudDataService;
import com.travisandjersy.safephoto.service.LocalStorageService;
import com.travisandjersy.safephoto.service.PhotoService;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;


/**
 * Created by trvslhlt on 4/29/17.
 */

public class SearchFragment extends Fragment implements OnItemClickListener {
    private static final String TAG = "SearchFragment";
    private ListView list;
    private SearchView searchBar;
    private List<String> publicNameList;
    private List<String> privateNameList;
    private List<String> nameList;
    ArrayAdapter<String> adapter;
    List<Photo> publicPhotos;
    List<Photo> privatePhotos;
    List<Photo> photos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_view, container, false);
        list = (ListView) view.findViewById(R.id.listview);
        searchBar = (SearchView) view.findViewById(R.id.simpleSearchView);

        searchBar.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                update(newText);
                return false;
            }
        });

        privatePhotos = PhotoService.getPrivatePhotos();
        privateNameList = PhotoService.getPrivatePhotoNames();
        for (ListIterator i = privateNameList.listIterator(); i.hasNext(); ) {
            i.set("private: " + i.next());
        }
        publicPhotos = PhotoService.getPublicPhotos();
        publicNameList = PhotoService.getPublicPhotoNames();
        for (ListIterator i = publicNameList.listIterator(); i.hasNext(); ) {
            i.set("public: " + i.next());
        }
        nameList = new ArrayList<String>(publicNameList);
        if (AuthenticationService.isSignedIn())
            nameList.addAll(privateNameList);
        photos = new ArrayList<Photo>(publicPhotos);
        photos.addAll(privatePhotos);
        adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, nameList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        //CloudDataService.enable(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        //CloudDataService.disable();
    }

/*
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.update(newText);
        return true;
    }
*/

    public void update(String text) {
        //photos = PhotoService.getPublicPhotos();
        List<String> updateList = new ArrayList<String>();
        text = text.toLowerCase();
        if (text.length() == 0) {
            updateList = nameList;
        } else {
            for (String wp : nameList) {
                if (wp.toLowerCase().contains(text)) {
                    updateList.add(wp);
                }
            }
        }
        adapter.clear();
        adapter.addAll(updateList);
        adapter.notifyDataSetChanged();
    }

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
