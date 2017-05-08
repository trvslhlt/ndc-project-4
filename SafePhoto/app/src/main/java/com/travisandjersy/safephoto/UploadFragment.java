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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.travisandjersy.safephoto.model.Photo;
import com.travisandjersy.safephoto.service.AuthenticationService;
import com.travisandjersy.safephoto.service.CloudDataService;
import com.travisandjersy.safephoto.service.CloudStorageService;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class UploadFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "UploadFragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Button takePictureButton;
    Button uploadImagebutton;
    Button commitUploadButton;
    Button radioPrivateButton;
    Button radioPublicButton;
    ImageView imagePreview;
    TextView signInPrompt;
    EditText description;
    TextView des;
    boolean checked;
    boolean isPrivate;
    Bitmap picture;
    RadioGroup rg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_view, container, false);
        signInPrompt = (TextView) view.findViewById(R.id.upload_instructions_text_view);
        takePictureButton = (Button) view.findViewById(R.id.take_picture_button);
        uploadImagebutton = (Button) view.findViewById(R.id.upload_image_button);
        commitUploadButton = (Button) view.findViewById(R.id.commit_upload_button);
        radioPrivateButton = (Button) view.findViewById(R.id.radio_private);
        radioPublicButton = (Button) view.findViewById(R.id.radio_public);
        rg = (RadioGroup) view.findViewById(R.id.radio_group);
        imagePreview = (ImageView) view.findViewById(R.id.pre_upload_image_preview);
        description = (EditText) view.findViewById(R.id.upload_description_field);
        des = (TextView) view.findViewById(R.id.upload_description_title);
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
        commitUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitUpload();
            }
        });
        radioPrivateButton.setOnClickListener(this);
        radioPublicButton.setOnClickListener(this);
        checked = false;
        isPrivate = false;
        configureForAuthenticationState();
        return view;
    }

    @Override
    public void onClick(View view) {
        checked = ((RadioButton)view).isChecked();
        switch(view.getId()){
            case R.id.radio_private:
                Toast.makeText(getActivity(), "Private is checked", Toast.LENGTH_LONG ).show();
                isPrivate = true;
            case R.id.radio_public:
                Toast.makeText(getActivity(), "Public is checked", Toast.LENGTH_LONG ).show();
        }
    }

    private void takePicture() {
        takePictureButton.setEnabled(false);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        uploadImagebutton.setEnabled(true);
        uploadImagebutton.setText(R.string.upload_image);
        configureForAuthenticationState();
    }

    private void uploadImage() {
        description.setVisibility(View.VISIBLE);
        des.setVisibility(View.VISIBLE);
        commitUploadButton.setVisibility(View.VISIBLE);
        radioPublicButton.setVisibility(View.VISIBLE);
        radioPrivateButton.setVisibility(View.VISIBLE);
    }

    private void commitUpload() {
        if (description.getText() == null) {
            Toast.makeText(getActivity(), "no name", Toast.LENGTH_LONG ).show();
            return;
        }
        if (!checked) {
            Toast.makeText(getActivity(), "no checked", Toast.LENGTH_LONG ).show();
            return;
        }
        if (picture == null) {
            Toast.makeText(getActivity(), "no picture", Toast.LENGTH_LONG ).show();
            return;
        }
        final Photo photo = new Photo(description.getText().toString(), isPrivate, AuthenticationService.getUserUID());

        Log.e("Photo name", photo.name);
        if (photo.isPrivate) Log.e("Photo private", "private");
        else Log.e("Photo private", "public");
        Log.e("Photo description", photo.description);
        Log.e("Photo uploadBy", photo.uploadBy);

        CloudStorageService.uploadImage(picture, photo.name, new CloudStorageService.UploadResult() {
            @Override
            public void didComplete(boolean success, String message) {
                if (success) {
                    //reconfigure UI
                    description.setText("");
                    rg.clearCheck();
                    uploadImagebutton.setEnabled(false);
                    uploadImagebutton.setText(R.string.upload_success);
                    configureForAuthenticationState();
                    CloudDataService.uploadObject(photo);
                    Toast.makeText(getActivity(), "nice", Toast.LENGTH_LONG ).show();
                }
                else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG ).show();
                }
                return;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture = imageBitmap;
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
        description.setVisibility(View.GONE);
        des.setVisibility(View.GONE);
        commitUploadButton.setVisibility(View.GONE);
        radioPrivateButton.setVisibility(View.GONE);
        radioPublicButton.setVisibility(View.GONE);

    }
}
