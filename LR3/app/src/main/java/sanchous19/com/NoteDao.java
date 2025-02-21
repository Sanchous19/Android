package sanchous19.com;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note WHERE uid = :id")
    Note getById(long id);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Insert
    void insert(Note Note);

    @Update
    void update(Note Note);

    @Delete
    void delete(Note Note);
}