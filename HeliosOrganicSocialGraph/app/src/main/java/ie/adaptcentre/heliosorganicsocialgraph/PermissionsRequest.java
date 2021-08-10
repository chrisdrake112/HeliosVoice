package ie.adaptcentre.heliosorganicsocialgraph;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;



public class PermissionsRequest extends AppCompatActivity {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    public boolean recordingPermission = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                recordingPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }

        if (!recordingPermission) {
            finish();
        }
    }


}
