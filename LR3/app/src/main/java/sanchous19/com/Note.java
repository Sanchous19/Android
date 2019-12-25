package sanchous19.com;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Comparator;
import java.util.Date;

@Entity
class Note {
    @PrimaryKey(autoGenerate = true)
    public long uid = 0;

    public String content;
    public String title;
    public String tags;

    @TypeConverters({DateConverter.class})
    public Date date;

    Note() {
        date = new Date();
    }


    static Comparator<Note> titleComparator = new Comparator<Note>() {
        @Override
        public int compare(Note n1, Note n2) {
            return n1.title.compareTo(n2.title);
        }
    };

    static Comparator<Note> dateComparator = new Comparator<Note>() {
        @Override
        public int compare(Note n1, Note n2) {
            return n2.date.compareTo(n1.date);
        }
    };
}
