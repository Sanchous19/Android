package sanchous19.com;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDB extends RoomDatabase {
    public abstract NoteDao noteDao();
}