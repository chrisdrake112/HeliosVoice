package ie.adaptcentre.heliosorganicsocialgraph;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.util.Log;

import java.io.IOException;

public class VoiceActivity extends AppCompatActivity {

    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean permissionToRecord = false;
    private MediaRecorder recorder;
    private String fileName = "test";

    Button PlayButton = findViewById(R.id.Record_btn);
    Button StopButton = findViewById(R.id.StopRecord_btn);
    Button BackToMain = findViewById(R.id.voice_to_main_btn);
    //MediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS);

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

    }


}

