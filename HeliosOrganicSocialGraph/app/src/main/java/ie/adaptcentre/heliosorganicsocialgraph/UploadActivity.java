package ie.adaptcentre.heliosorganicsocialgraph;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Spinner spinnerEnvironment = findViewById(R.id.spinnerEnv);
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        ImageButton UploadImageButton = findViewById(R.id.UploadButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> envAdapter = ArrayAdapter.createFromResource(this, R.array.EnvironmentStringArray, android.R.layout.simple_spinner_item);
        envAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnvironment.setAdapter(envAdapter);

        UploadImageButton.setOnClickListener((View view) -> {;
        Map<String, Object> Audio_data = new HashMap<>();
        Audio_data.put("DATE", currentDateTimeString);
        Audio_data.put("AudioFile", "Audio file");
        Audio_data.put("Location", 1815);
        Audio_data.put("Level of Noise", 1815);

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
    }

}
