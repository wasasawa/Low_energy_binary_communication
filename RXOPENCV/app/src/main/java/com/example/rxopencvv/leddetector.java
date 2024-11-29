package com.example.rxopencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class leddetector {

    private static final int BRIGHTNESS_THRESHOLD = 200; // Adjust based on environment
    private static final int ROI_SIZE = 100; // Size of the region of interest (ROI)
    private static final int SAMPLING_INTERVAL_MS = 1000; // Sampling interval in milliseconds

    private long lastSampleTime = 0;
    private StringBuilder binarySequence = new StringBuilder();
    private boolean isLedDetected = false;

    /**
     * Detects the LED's blinking pattern in the given frame and returns the binary sequence.
     *
     * @param frame   The camera frame (RGBA format).
     * @param centerX The expected X-coordinate of the LED in the frame.
     * @param centerY The expected Y-coordinate of the LED in the frame.
     * @return The binary sequence as a String, or null if no LED is detected.
     * */

    public String detectLED(Mat frame, int centerX, int centerY) {
        // Define the ROI (Region of Interest)
        Point center = new Point(centerX, centerY);
        Rect roi = new Rect((int) center.x - ROI_SIZE / 2, (int) center.y - ROI_SIZE / 2, ROI_SIZE, ROI_SIZE);

        // Validate ROI dimensions to avoid exceptions
        if (roi.x < 0 || roi.y < 0 || roi.x + roi.width > frame.cols() || roi.y + roi.height > frame.rows()) {
            isLedDetected = false;
            return null;
        }

        Mat roiMat = new Mat(frame, roi);

        // Calculate the average brightness in the ROI
        Scalar avgBrightness = Core.mean(roiMat);
        double brightness = avgBrightness.val[0]; // Average intensity

        // Draw the ROI on the frame for visualization
        Imgproc.rectangle(frame, roi.tl(), roi.br(), new Scalar(0, 255, 0), 2);


        // Detect if brightness exceeds the threshold
        if (brightness > BRIGHTNESS_THRESHOLD) {
            isLedDetected = true;

            // Log LED detection for debugging
            Imgproc.putText(frame, "LED Detected", new Point(50, 50), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);


        } else {
            isLedDetected = false;
        }

        return binarySequence.length() >= 8 ? binarySequence.toString() : null;
    }




    /**
     * Detects the LED's blinking pattern in the a frame and returns the binary sequence.
     *
     * @param frame    The camera frame (RGBA format).
     * @param tileSize The ROI size
     * @param stepSize The stepsize for the loop iterating through the tiles, to potentially allow for overlap
     * @return The binary sequence as a String, or null if no LED is detected.
     * */

    /*
    public String detectLED(Mat frame, int tileSize, int stepSize) {

        boolean isLedDetected = false;

        // Get the dimensions of the frame
        int frameWidth = frame.cols();
        int frameHeight = frame.rows();

        // Loop through the frame in a grid of small tiles (with overlap if desired)
        // We should add a binary sequence for each detected LED to eliminate the false LED detections

        for (int y = 0; y < frameHeight - tileSize; y += stepSize) {
            for (int x = 0; x < frameWidth - tileSize; x += stepSize) {
                // Define the ROI (Region of Interest) as a small tile
                Rect roi = new Rect(x, y, tileSize, tileSize);

                // Extract the ROI from the frame
                Mat roiMat = new Mat(frame, roi);

                // Calculate the average brightness in the ROI
                Scalar avgBrightness = Core.mean(roiMat);
                double brightness = avgBrightness.val[0]; // Average intensity

                // Detect if brightness exceeds the threshold
                if (brightness > BRIGHTNESS_THRESHOLD) {
                    isLedDetected = true;
                    binarySequence.append("1");

                    // Draw the detected tile on the frame for visualization
                    Imgproc.putText(frame, "LED Detected", new Point(50, 50), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(0, 255, 0), 2);
                    Imgproc.rectangle(frame, roi.tl(), roi.br(), new Scalar(0, 255, 0), 2); // Green rectangle for detected LED

                    // Sample brightness at intervals to detect blinking
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastSampleTime >= SAMPLING_INTERVAL_MS) {
                        lastSampleTime = currentTime;

                        // Append binary data based on brightness
                        binarySequence.append("1");
                    }
                } else {
                    isLedDetected = false;
                    binarySequence.append("0");
                }
            }
        }

        // Return the binary sequence that encodes LED detection across the entire screen
        return binarySequence.toString();
    }

    */

    /**
     * Checks if the LED is currently detected.
     *
     * @return True if LED is detected, otherwise false.
     */
    public boolean isLedDetected() {
        return isLedDetected;
    }

    /**
     * Clears the binary sequence for the next detection.
     */
    public void clearBinarySequence() {
        binarySequence.setLength(0);
    }
}
