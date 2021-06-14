package ie.adaptcentre.heliosorganicsocialgraph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button VoiceButton = findViewById(R.id.voice_btn);
        Button BluetoothButton = findViewById(R.id.bluetooth_btn);

        VoiceButton.setOnClickListener(this);
        BluetoothButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.voice_btn:
                Intent VoiceButtonIntent = new Intent(this, VoiceActivity.class);
                break;

            case R.id.bluetooth_btn:
                Intent BluetoothButtonIntent = new Intent(this, BlueToothActivity.class);
                break;

        }
    }
}