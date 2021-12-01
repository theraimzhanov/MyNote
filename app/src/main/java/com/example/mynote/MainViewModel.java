package com.example.mynote;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
 private static NoteDataBase dataBase;
 private LiveData<List<Note>> notes;
    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBase = NoteDataBase.getInstance(getApplication());
        notes = dataBase.noteDao().getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
    public void insertNote(Note note){
 new InsertTask().execute(note);
    }

    public void deleteNote(Note note){
        new DeleteTask().execute(note);
    }
    public void deleteAllNote(){
        new DeleteAllTask().execute();
    }
    private static class InsertTask extends AsyncTask<Note,Void,Void>{

        @Override
        protected Void doInBackground(Note... notes) {
           if (notes != null && notes.length>0){
               dataBase.noteDao().insertNote(notes[0]);
           }
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<Note,Void,Void>{

        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length>0){
                dataBase.noteDao().deleteNote(notes[0]);
            }
            return null;
        }
    }
    private static class DeleteAllTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... notes) {
                dataBase.noteDao().deleteAllNotes();
            return null;
        }
    }
}
