package ie.adaptcentre.heliosorganicsocialgraph;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Spinner spinnerEnvironment = findViewById(R.id.spinnerEnv);
        ArrayAdapter<CharSequence>envAdapter = ArrayAdapter.createFromResource(this,R.array.EnvironmentStringArray, android.R.layout.simple_spinner_item);
        envAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnvironment.setAdapter(envAdapter);

    }
}
