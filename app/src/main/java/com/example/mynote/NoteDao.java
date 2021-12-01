package com.example.mynote;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Notes ORDER BY dayOfWeek")
   LiveData<List<Note>> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM Notes")
    void deleteAllNotes();
}
