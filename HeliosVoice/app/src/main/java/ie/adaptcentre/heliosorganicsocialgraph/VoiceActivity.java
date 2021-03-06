package ie.adaptcentre.heliosorganicsocialgraph;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public class VoiceActivity extends AppCompatActivity {

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    private MediaRecorder recorder;
    public static String recordFile;
    public static String uploadFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice);

        ImageButton RecordImageButton = findViewById(R.id.PlayImageButton);
        ImageButton StopImageButton = findViewById(R.id.StopImageButton);
        StopImageButton.setEnabled(false);

        RecordImageButton.setOnClickListener((View view) -> {

                startRecording();
                RecordImageButton.setEnabled(false);
                StopImageButton.setEnabled(true);
        });

        StopImageButton.setOnClickListener((View view) -> {

                stopRecording();
                StopImageButton.setEnabled(false);
                Intent UploadActivityIntent = new Intent(this, UploadActivity.class);
                startActivity(UploadActivityIntent);

        });

    }

    public void startRecording() {
        if (CheckPermissions()) {

            String path = this.getExternalFilesDir("/").getAbsolutePath();
            recordFile = UUID.randomUUID() + ".3gp";
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(path + "/" + recordFile);
            uploadFile = path + "/" + recordFile;
            Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();

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