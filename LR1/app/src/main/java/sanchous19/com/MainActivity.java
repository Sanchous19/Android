package sanchous19.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;

import android.content.DialogInterface;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void showNeutralAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { }
            }
        );
        alertDialog.show();
    }

    public void showVersionNameClick(View view) {
        String versionName = "Version name: " + BuildConfig.VERSION_NAME;
        showNeutralAlertDialog(versionName);
    }

    public void showDeviceIdClick(View view) {
        String versionName = "Device ID: ";
        showNeutralAlertDialog(versionName);
    }
}
