package com.example.arhiking.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arhiking.R;

import org.opencv.android.BaseLoaderCallback;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Mat;


public class CameraFragment extends Fragment {
    private static final String TAG = "CameraFragment";
    Mat mRGBA;
    Mat mRGBAT;
    CameraBridgeViewBase cameraBridgeViewBase;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context ctx = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        cameraBridgeViewBase = (CameraBridgeViewBase) view.findViewById(R.id.camera_surface);
        BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(ctx)
        {
            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:{
                        Log.i(TAG,"onManagerConnected: Opencv loaded");
                    }
                    default:{
                        super.onManagerConnected(status);
                    }
                    break;
                }

            }
        };
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    cameraBridgeViewBase.setCameraPermissionGranted();
                }
                else{
                    //permission denied
                }
                return;
            }
        }
    }
}