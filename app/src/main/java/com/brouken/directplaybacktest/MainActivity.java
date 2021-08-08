package com.brouken.directplaybacktest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioFormat;
import android.media.AudioTrack;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_SAMPLE_RATE_HZ = 48_000;

    private static final int[] ALL_SURROUND_ENCODINGS =
            new int[] {
                    AudioFormat.ENCODING_AC3,
                    AudioFormat.ENCODING_E_AC3,
                    AudioFormat.ENCODING_E_AC3_JOC,
                    AudioFormat.ENCODING_AC4,
                    AudioFormat.ENCODING_DOLBY_TRUEHD,
                    AudioFormat.ENCODING_DTS,
                    AudioFormat.ENCODING_DTS_HD,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StringBuilder sb = new StringBuilder();

        TextView textView = findViewById(R.id.textView);
        for (int encoding : ALL_SURROUND_ENCODINGS) {
            sb.append(getEncodingName(encoding));
            sb.append(": ");
            sb.append(isSupported(encoding));
            sb.append('\n');
        }

        textView.setText(sb.toString());
    }

    private boolean isSupported(int encoding) {
        return AudioTrack.isDirectPlaybackSupported(
                new AudioFormat.Builder()
                        .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                        .setEncoding(encoding)
                        .setSampleRate(DEFAULT_SAMPLE_RATE_HZ)
                        .build(),
                new android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MOVIE)
                        .setFlags(0)
                        .build());
    }

    private String getEncodingName(int encoding) {
        switch (encoding) {
            case AudioFormat.ENCODING_AC3:
                return "AC3";
            case AudioFormat.ENCODING_E_AC3:
                return "E-AC3";
            case AudioFormat.ENCODING_E_AC3_JOC:
                return "E-AC3-JOC";
            case AudioFormat.ENCODING_AC4:
                return "AC4";
            case AudioFormat.ENCODING_DOLBY_TRUEHD:
                return "DOLBY TRUEHD";
            case AudioFormat.ENCODING_DTS:
                return "DTS";
            case AudioFormat.ENCODING_DTS_HD:
                return "DTS-HD";
            default:
                return "¯\\_(ツ)_/¯";
        }
    }
}