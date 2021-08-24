package ie.adaptcentre.heliosorganicsocialgraph;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class UploadActivity extends AppCompatActivity {

    private static String noiseLevelHigh = "A lot of background noise";
    private static String noiseLevelMed = "Little background noise ";
    private static String noiseLevelLow = "Next to no background noise";
    private static int low = 25;
    private static int med = 50;
    private static int high = 75;
    private int seekBarValue = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Spinner spinnerEnvironment = findViewById(R.id.spinnerEnv);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        ImageButton UploadImageButton = findViewById(R.id.UploadButton);
        SeekBar NoiseSeekBar = findViewById(R.id.noiseSeekBar);
        TextView SeekTextView = findViewById(R.id.seekTextView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String spinnerText = spinnerEnvironment.getSelectedItem().toString();

        ArrayAdapter<CharSequence> envAdapter = ArrayAdapter.createFromResource(this, R.array.EnvironmentStringArray, android.R.layout.simple_spinner_item);
        envAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnvironment.setAdapter(envAdapter);

        UploadImageButton.setOnClickListener((View view) -> {;
        Map<String, Object> Audio_data = new HashMap<>();
        Audio_data.put("DATE", currentDateTimeString);
        Audio_data.put("AudioFile", "Audio file");
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
    });

        NoiseSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(Integer.valueOf(progress) <= low) {

                    SeekTextView.setText(noiseLevelLow);
                    seekBarValue = low;

                }

                else if(Integer.valueOf(progress) <= med){

                    SeekTextView.setText(noiseLevelMed);
                    seekBarValue = med;

                }
                else if(Integer.valueOf(progress) <= high){

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
