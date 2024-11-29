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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Collections;
import java.util.List;

public class MainActivity extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    private static final String TAG = "OpenCV-APP";
    private CameraBridgeViewBase mOpenCvCameraView;
    private List<CameraBridgeViewBase> cameraViews; // List to manage camera views

    // Add LEDDetector and BinaryDecoder instances
    private leddetector ledDetector;
    private binarydecoder binaryDecoder;

    private static final int SAMPLING_INTERVAL_MS = 1000; // Sampling interval in milliseconds

    private long lastSampleTime = 0;
    private StringBuilder binarySequence1 = new StringBuilder();


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
        String binarySequence = ledDetector.detectLED(rgbaFrame, rgbaFrame.cols()/2, rgbaFrame.rows()/2);
        String decodedText;

        long currentTime = System.currentTimeMillis();

        if (ledDetector.isLedDetected()) {
            //Log.d(TAG, "LED Detected");
            if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                lastSampleTime = currentTime;

                // Append binary data based on brightness
                binarySequence1.append("1");
            }

        } else {
            if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                lastSampleTime = currentTime;

                // Append binary data based on brightness
                binarySequence1.append("0");
            }
        }

            //if (binarySequence != null && binarySequence.length() >= 8) {
                if (binarySequence1 != null) {
                    // Decode binary sequence to text
                    //Log.d(TAG, "Decoded Text: " + decodedText);                    // Clear binary sequence for the next detection
                }
                Imgproc.putText(rgbaFrame, "bin : " + binarySequence1, new Point(50, 500), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);
        if (binarySequence1.length() >= 8) {
            decodedText = binaryDecoder.decodeBinary(binarySequence1.toString());
            Imgproc.putText(rgbaFrame, "Decoded : " + decodedText, new Point(50, 700), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);
            //binarySequence1.setLength(0);
        }
                //Log.d(TAG, "Frame captured and processed");
        // Return the same frame or modify it
        return rgbaFrame;
    }
}
