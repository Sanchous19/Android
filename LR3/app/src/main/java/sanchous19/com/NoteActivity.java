package sanchous19.com;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class NoteActivity extends AppCompatActivity {
    Bundle bundle;
    TextView titleTextView;
    TextView contentTextView;
    TextView tagsTextView;
    Note note;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        bundle = getIntent().getExtras();
        titleTextView = findViewById(R.id.title);
        contentTextView = findViewById(R.id.content);
        tagsTextView = findViewById(R.id.tags);

        if(bundle == null) {
            note = new Note();
            titleTextView.setHint(dateFormat.format(note.date));
        }
        else {
            note = App.getInstance().getDao().getById(bundle.getLong("uid"));
            titleTextView.setText(note.title);
            contentTextView.setText(note.content);
            tagsTextView.setText(note.tags);
        }
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }

    public void saveClick(View view) {
        note.content = contentTextView.getText().toString();
        if(note.content.isEmpty()){
            InstantToast.showText(getApplicationContext(), "Note is empty");
            return;
        }

        note.title = titleTextView.getText().toString();
        if(note.title.isEmpty()){
            note.title = dateFormat.format(note.date);
        }

        note.tags = tagsTextView.getText().toString();
        if(bundle == null) {
            App.getInstance().getDao().insert(note);
            InstantToast.showText(getApplicationContext(), "Note is added");
        }
        else {
            App.getInstance().getDao().update(note);
            InstantToast.showText(getApplicationContext(), "Note is saved");
        }

        finish();
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(NoteActivity.this).create();
        alertDialog.setMessage("Are you sure you want to go back without saving data?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
        alertDialog.show();
    }
}
