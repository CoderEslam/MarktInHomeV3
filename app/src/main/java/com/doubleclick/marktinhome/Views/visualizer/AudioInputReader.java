package com.doubleclick.marktinhome.Views.visualizer;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Build;

import com.doubleclick.marktinhome.R;

public class AudioInputReader {
    private final VisualizerView mVisualizerView;
    private final Context mContext;
    private MediaPlayer mPlayer;
    private Visualizer mVisualizer;
    private AudioListner audioListner;

    public AudioInputReader(VisualizerView visualizerView, AudioListner audioListner, Context context) {
        this.mVisualizerView = visualizerView;
        this.mContext = context;
        this.audioListner = audioListner;
    }

    public void initVisualizer(Uri uri) {
        // Setup media player
        mPlayer = MediaPlayer.create(mContext, uri);
        // to stop loop
        mPlayer.setLooping(false);

        // Setup the Visualizer
        // Connect it to the media player
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mVisualizer.setMeasurementMode(Visualizer.MEASUREMENT_MODE_PEAK_RMS);
            mVisualizer.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
        }

        // Set the size of the byte array returned for visualization
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        // Whenever audio data is available, update the visualizer view
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {

                        // Do nothing, we are only interested in the FFT (aka fast Fourier transform)
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                        // If the Visualizer is ready and has data, send that data to the VisualizerView
                        if (mVisualizer != null && mVisualizer.getEnabled()) {
                            mVisualizerView.updateFFT(bytes);
                        }

                    }
                },
                Visualizer.getMaxCaptureRate(), false, true);

        // Start everything
        mVisualizer.setEnabled(true);
        mPlayer.start();
        complete();
        PreparedListener();
    }

    public void shutdown(boolean isFinishing) {
        if (mPlayer != null) {
            mPlayer.pause();
            if (isFinishing) {
                mVisualizer.release();
                mPlayer.release();
                mPlayer = null;
                mVisualizer = null;
            }
        }

        if (mVisualizer != null) {
            mVisualizer.setEnabled(false);
        }
    }

    public void restart() {
        if (mPlayer != null) {
            mPlayer.start();
        }
        mVisualizer.setEnabled(true);
        mVisualizerView.restart();
    }

    public void complete() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audioListner.onComplete(mp);
            }
        });
    }

    public void PreparedListener() {
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                audioListner.OnPreparedListener(mp);
            }
        });
    }

    public interface AudioListner {
        void onComplete(MediaPlayer mp);

        void OnPreparedListener(MediaPlayer mp);
    }
}
