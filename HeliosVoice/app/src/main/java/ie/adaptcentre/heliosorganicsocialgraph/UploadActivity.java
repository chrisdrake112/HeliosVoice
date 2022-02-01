package ie.adaptcentre.heliosorganicsocialgraph;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private final String noiseLevelHigh = "A lot of background noise";
    private final String noiseLevelMed = "Little background noise ";
    private final String noiseLevelLow = "No background noise";
    private final int low = 25;
    private final int med = 50;
    private final int high = 75;
    private int seekBarValue = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Spinner Environment = findViewById(R.id.spinnerEnv);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        ImageButton UploadImageButton = findViewById(R.id.UploadButton);
        SeekBar NoiseSeekBar = findViewById(R.id.noiseSeekBar);
        TextView SeekTextView = findViewById(R.id.seekTextView);
        Uri audioFile = Uri.fromFile(new File(VoiceActivity.uploadFile));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();


        ArrayAdapter<CharSequence> envAdapter = ArrayAdapter.createFromResource(this, R.array.EnvironmentStringArray, android.R.layout.simple_spinner_item);
        envAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Environment.setAdapter(envAdapter);
        String spinnerText = Environment.getSelectedItem().toString();

        UploadImageButton.setOnClickListener((View view) -> {

        Map<String, Object> Audio_data = new HashMap<>();
        Audio_data.put("Date", currentDateTimeString);
        Audio_data.put("AudioFile", VoiceActivity.recordFile);
        Audio_data.put("Location", spinnerText);
        Audio_data.put("Level of Noise", seekBarValue);

        // Add a new document with a generated ID
        db.collection("Audio_data")
                .add(Audio_data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("XXX", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("XXX", "Error adding document", e);
                    }
                });

            StorageReference audioRef = storage.getReference(VoiceActivity.uploadFile);
            UploadTask uploadTask = audioRef.putFile(audioFile);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(UploadActivity.this,"Audio File failed to upload",Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadActivity.this,"Audio File uploaded",Toast.LENGTH_SHORT).show();
                }
            });

            Intent VoiceActivityIntent = new Intent(this, VoiceActivity.class);
            startActivity(VoiceActivityIntent);
    });

        NoiseSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress <= low) {

                    SeekTextView.setText(noiseLevelLow);
                    seekBarValue = low;

                }

                else if(progress <= med){

                    SeekTextView.setText(noiseLevelMed);
                    seekBarValue = med;

                }
                
                else if(progress <= high){

                    SeekTextView.setText(noiseLevelHigh);
                    seekBarValue = high;

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
