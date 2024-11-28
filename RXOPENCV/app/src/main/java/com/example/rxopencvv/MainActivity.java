package com.example.rxopencv;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OpenCV-APP";
    private CameraBridgeViewBase mOpenCvCameraView;
    private List<CameraBridgeViewBase> cameraViews; // List to manage camera views

    // Add LEDDetector and BinaryDecoder instances
    private leddetector ledDetector;
    private binarydecoder binaryDecoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize OpenCV Camera View
        mOpenCvCameraView = findViewById(R.id.camera_view); // Initialize global mOpenCvCameraView
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this); // Set listener for camera frames

        // Add mOpenCvCameraView to the list
        cameraViews = Collections.singletonList(mOpenCvCameraView);

        // Initialize LEDDetector and BinaryDecoder
        ledDetector = new leddetector();
        binaryDecoder = new binarydecoder();

        // Request Camera Permission
        getPermission();

        // Initialize OpenCV
        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "OpenCV initialization failed");
        } else {
            Log.d(TAG, "OpenCV initialized successfully");
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        // Return the list of camera views
        return cameraViews;
    }

    void getPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            enableCameraViews();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Camera permission granted");
                enableCameraViews();
            } else {
                Log.e(TAG, "Camera permission denied");
            }
        }
    }

    private void enableCameraViews() {
        if (cameraViews != null) {
            for (CameraBridgeViewBase cameraView : cameraViews) {
                cameraView.enableView();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableCameraViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraViews != null) {
            for (CameraBridgeViewBase cameraView : cameraViews) {
                cameraView.disableView();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraViews != null) {
            for (CameraBridgeViewBase cameraView : cameraViews) {
                cameraView.disableView();
            }
        }
    }

    // CvCameraViewListener2 methods

    @Override
    public void onCameraViewStarted(int width, int height) {
        Log.d(TAG, "Camera view started with width: " + width + " and height: " + height);
    }

    @Override
    public void onCameraViewStopped() {
        Log.d(TAG, "Camera view stopped");
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        // Access and process the camera frame
        Mat rgbaFrame = inputFrame.rgba();

        // Detect LED and decode its pattern
        String binarySequence = ledDetector.detectLED(rgbaFrame, 100, 100);

        if (ledDetector.isLedDetected()) {
            Log.d(TAG, "LED Detected");

            if (binarySequence != null && binarySequence.length() >= 8) {
                // Decode binary sequence to text
                String decodedText = binaryDecoder.decodeBinary(binarySequence);
                Log.d(TAG, "Decoded Text: " + decodedText);

                // Clear binary sequence for the next detection
                ledDetector.clearBinarySequence();
            }
        }

        Log.d(TAG, "Frame captured and processed");
        // Return the same frame or modify it
        return rgbaFrame;
    }
}
