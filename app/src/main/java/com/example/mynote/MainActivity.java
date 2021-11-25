package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private NoteDataBase dataBase;
    List<Note> noteFromData;

    public static final ArrayList<Note> notes = new ArrayList<>();
    public static NoteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = NoteDataBase.getInstance(this);
        noteFromData = new ArrayList<>();


        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        adapter = new NoteAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
        getData();

        adapter.setListener(new Listener() {
            @Override
            public void click(int position) {
                Note note = notes.get(position);
                dataBase.noteDao().deleteNote(note);
                getData();
                adapter.notifyDataSetChanged();
            }
        });
    }


    public void onClickView(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        noteFromData = dataBase.noteDao().getAllNotes();
        notes.clear();
        notes.addAll(noteFromData);
        adapter.notifyDataSetChanged();
    }

    public void reset(View view) {
        dataBase.noteDao().deleteAllNotes();
        notes.clear();
        adapter.notifyDataSetChanged();
    }
}


