package com.travisandjersy.safephoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.travisandjersy.safephoto.service.AuthenticationService;

import static android.app.Activity.RESULT_OK;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class UploadFragment extends Fragment {

    private static final String TAG = "UploadFragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Button takePictureButton;
    Button uploadImagebutton;
    ImageView imagePreview;
    TextView signInPrompt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_view, container, false);
        signInPrompt = (TextView) view.findViewById(R.id.upload_instructions_text_view);
        takePictureButton = (Button) view.findViewById(R.id.take_picture_button);
        uploadImagebutton = (Button) view.findViewById(R.id.upload_image_button);
        imagePreview = (ImageView) view.findViewById(R.id.pre_upload_image_preview);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        uploadImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        configureForAuthenticationState();
        return view;
    }

    private void takePicture() {
        takePictureButton.setEnabled(false);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void uploadImage() {
        Log.d(TAG, "need to implement uploadImage");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagePreview.setImageBitmap(imageBitmap);
            takePictureButton.setEnabled(true);
            uploadImagebutton.setEnabled(true);
        }
    }

    private void configureForAuthenticationState() {
        if (AuthenticationService.isSignedIn()) {
            takePictureButton.setEnabled(true);
            signInPrompt.setVisibility(View.GONE);
        } else {
            takePictureButton.setEnabled(false);
            signInPrompt.setVisibility(View.VISIBLE);
        }
    }
}
