package com.example.rxopencv;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class MainActivity extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OpenCV-APP";
    private CameraBridgeViewBase mOpenCvCameraView;
    private List<CameraBridgeViewBase> cameraViews;

    private leddetector ledDetector;
    private binarydecoder binaryDecoder;

    private static final int SAMPLING_INTERVAL_MS = 1000;
    private long lastSampleTime = 0;

    private StringBuilder binarySequence1 = new StringBuilder();
    private StringBuilder binarySequence2 = new StringBuilder();

    private static boolean ACK = false;
    private static boolean ST = false;

    private String persistentDecodedText = ""; // Persistent decoded text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = findViewById(R.id.camera_view);
        mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        cameraViews = Collections.singletonList(mOpenCvCameraView);

        ledDetector = new leddetector();
        binaryDecoder = new binarydecoder();

        getPermission();

        if (!OpenCVLoader.initDebug()) {
            Log.e(TAG, "OpenCV initialization failed");
        } else {
            Log.d(TAG, "OpenCV initialized successfully");
        }
    }

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
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
        Mat rgbaFrame = inputFrame.rgba();
        long currentTime = System.currentTimeMillis();

        if (ledDetector.detectST(rgbaFrame, rgbaFrame.cols() / 2, rgbaFrame.rows() / 2)) {
            ST = true;
        }

        if (ST) {
            if (!ACK) {
                if (ledDetector.isLedDetected()) {
                    if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                        lastSampleTime = currentTime;
                        binarySequence2.append("1");
                    }
                } else {
                    if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                        lastSampleTime = currentTime;
                        binarySequence2.append("0");
                    }
                }

                Imgproc.putText(rgbaFrame, "bin : " + binarySequence2, new Point(50, 500), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);

                if (binarySequence2.length() >= 6 && binarySequence2.toString().equals("100100")) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Synchronized, ready to read", Toast.LENGTH_SHORT).show();
                        binarySequence2.setLength(0);
                        ACK = true;
                    });
                } if (binarySequence2.length() >= 6 && !binarySequence2.toString().equals("100100")){
                    ACK = false;
                    binarySequence2.setLength(0);
                    ST = false;
                }
            } else {
                if (ledDetector.isLedDetected()) {
                    if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                        lastSampleTime = currentTime;
                        binarySequence1.append("1");
                    }
                } else {
                    if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                        lastSampleTime = currentTime;
                        binarySequence1.append("0");
                    }
                }

                Imgproc.putText(rgbaFrame, "bin : " + binarySequence1, new Point(50, 500), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);

                if (binarySequence1.length() >= 6) {
                    int type = Integer.parseInt(binarySequence1.substring(0, 2), 2);
                    int length = Integer.parseInt(binarySequence1.substring(2, 6), 2);

                    if (binarySequence1.length() >= 6 + length * 8) {
                        String value = binarySequence1.substring(6, 6 + length * 8);
                        persistentDecodedText = "Type: " + type + ", Length: " + length + ", Value: " + binaryDecoder.decodeBinary(value, length);

                        Imgproc.putText(rgbaFrame, "TLV : " + persistentDecodedText, new Point(50, 700), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);

                        binarySequence1.delete(0, 6 + length * 8);
                        ACK = false;
                    }
                }
            }
        } else {
            binarySequence1.setLength(0);
            binarySequence2.setLength(0);
        }

        Imgproc.putText(rgbaFrame, "TLV : " + persistentDecodedText, new Point(50, 900), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);

        return rgbaFrame;
    }
}