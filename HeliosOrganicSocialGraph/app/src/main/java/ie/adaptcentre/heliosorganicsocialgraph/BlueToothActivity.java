package ie.adaptcentre.heliosorganicsocialgraph;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class BlueToothActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        Button VoiceButton = findViewById(R.id.voice_btn);
        Button BluetoothButton = findViewById(R.id.bluetooth_btn);

    }

}
