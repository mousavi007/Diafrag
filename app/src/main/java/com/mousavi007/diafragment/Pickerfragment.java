package com.mousavi007.diafragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class Pickerfragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final int PICK_PHOTO = 100;
    private static final int TAKE_PHOTO = 101;
    private static final String TAG = "PICKER_FRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.pickImage)Button pickImage;
    @BindView(R.id.takeImage)Button takeImage;




    // TODO: Rename and change types and number of parameters

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment,container,false);
        ButterKnife.bind(this,view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @OnClick(R.id.pickImage)
    void pickImage(View view){
        Intent photopickerintent=new Intent(Intent.ACTION_PICK);
        photopickerintent.setType("image/*");
        startActivityForResult(photopickerintent,PICK_PHOTO);
    }

    @OnClick(R.id.takeImage)
    void takeImage(View view) {
//        Snackbar.make(getView(), "I want to take an image.", Snackbar.LENGTH_SHORT)
//                .show();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Picked a photo.");
                    Uri selectedImage = data.getData();
                    ((MainActivity)getActivity()).createPalette(selectedImage);
                    getDialog().dismiss();
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "Took a photo.");
                }
        }
    }
}
