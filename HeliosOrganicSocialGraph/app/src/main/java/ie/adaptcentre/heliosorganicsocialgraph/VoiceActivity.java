package ie.adaptcentre.heliosorganicsocialgraph;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


public class VoiceActivity extends AppCompatActivity {

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    private MediaRecorder recorder;
    private boolean isPlaying = false;
    private boolean isFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice);

        Button RecordButton = findViewById(R.id.Record_btn);
        Button BackToMain = findViewById(R.id.voice_to_main_btn);

        RecordButton.setOnClickListener((View view) -> {

            if(isPlaying)
            {
                //stopRecording();
                RecordButton.setText("Play");
                isPlaying = false;

            }
            else
            {
                //startRecording();
                RecordButton.setText("Stop");
                isPlaying = true;
                isFinished = true;

                if(isFinished)
                {
                    Intent UploadActivityIntent = new Intent(this,UploadActivity.class);
                    startActivity(UploadActivityIntent);
                }
            }

        });

    }

    public void startRecording() {
        if (CheckPermissions()) {

            String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/AudioRecording.3gp";
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(mFileName);

            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e("XXX", "prepare() failed");
            }

            recorder.start();
        }

        else{
            RequestPermissions();
        }

    }


    public void stopRecording()
    {
        recorder.stop();
        recorder.release();
        recorder = null;
        Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();

    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // this method is called when user will
        // grant the permission for audio recording.
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean CheckPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        // this method is used to request the
        // permission for audio recording and storage.
        ActivityCompat.requestPermissions(VoiceActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

}

