package sanchous19.com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ArrayList<Note> notes;
    ArrayList<Note> filteredNotes;
    AbsListView notesView;
    EditText searchBar;
    NoteAdapter adapter;
    NoteDao dao;
    Comparator<Note> comparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = App.getInstance().getDao();
        notesView = findViewById(R.id.notes);

        notesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("uid", Objects.requireNonNull(adapter.getItem(position)).uid);
                startActivity(intent);
            }
        });

        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(position);
                return true;
            }
        });

        searchBar = findViewById(R.id.search);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                search();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        notes = new ArrayList<>(dao.getAll());
        filteredNotes = new ArrayList<>(notes);
        comparator = Note.dateComparator;
        Collections.sort(notes, comparator);
        adapter = new NoteAdapter(this, R.layout.list_note, filteredNotes);
        notesView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        notes = new ArrayList<>(dao.getAll());
        search();
        super.onResume();
    }

    public void addClick(View view) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        startActivity(intent);
    }

    public void clearClick(View view) {
        searchBar.setText("");
        search();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String toastText = "";
        if (id == R.id.sortByDateItem) {
            comparator = Note.dateComparator;
            toastText = "Sorted by date";
        }
        else if (id == R.id.sortByTitleItem) {
            comparator = Note.titleComparator;
            toastText = "Sorted by title";
        }
        InstantToast.showText(getApplicationContext(), toastText);
        setSorted();
        return super.onOptionsItemSelected(item);
    }

    private void search(){
        filteredNotes.clear();

        if(searchBar.getText().toString().isEmpty()) {
            filteredNotes.addAll(notes);
            setSorted();
            return;
        }

        List<String> tags = new ArrayList<>(Arrays.asList(searchBar.getText().toString().split(" ")));
        String lastTag = tags.get(tags.size() - 1);
        tags.remove(tags.size() - 1);

        for(Note note : notes) {
            List<String> noteTags = Arrays.asList(note.tags.split(" "));

            if(noteTags.containsAll(tags)) {
                Boolean isTrue = false;

                for (String tag : noteTags) {
                    if (tag.startsWith(lastTag)) {
                        isTrue = true;
                        break;
                    }
                }

                if (isTrue) {
                    filteredNotes.add(note);
                }
            }
        }
        setSorted();
    }

    private void setSorted() {
        Collections.sort(filteredNotes, comparator);
        adapter.notifyDataSetChanged();
    }

    private void showAlertDialog(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setMessage("Are you sure you want to delete the note?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.delete(adapter.getItem(position));
                        adapter.remove(adapter.getItem(position));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Note is deleted", Toast.LENGTH_SHORT)
                                .show();
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
