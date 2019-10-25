package sanchous19.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String app_settings = "app_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(app_settings, MODE_PRIVATE);
    }

    public void showVersionNameClick(View view) {
        String versionName = "Version name: " + BuildConfig.VERSION_NAME;
        showNeutralAlertDialog(versionName);
    }

    public void showDeviceIdClick(View view) {
        String permission = Manifest.permission.READ_PHONE_STATE;
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                showRationaleAlertDialog(permission);
            }
            else {
                if (isFirstAsking(permission)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(permission, false);
                    editor.commit();

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, 1);
                }
                else {
                    showAlertDialogSettings();
                }
            }
        }
        else {
            showDeviceID();
        }
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

    private void showDeviceID() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = "Device ID: " + telephonyManager.getImei();
        showNeutralAlertDialog(deviceID);
    }

    private boolean isFirstAsking(String permission) {
        return sharedPreferences.getBoolean(permission, true);
    }

    private void showRationaleAlertDialog(final String permission) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage("Allow access to the telephone to see your device ID.");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, 1);
                }
            }
        );
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { }
            }
        );
        alertDialog.show();
    }

    private void showAlertDialogSettings() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage("Allow access to the telephone in the settings to see your device ID.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        );
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { }
            }
        );
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDeviceID();
                }
            }
        }
    }
}
