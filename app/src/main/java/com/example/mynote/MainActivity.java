package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
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
 private MainViewModel viewModel;

    public static final ArrayList<Note> notes = new ArrayList<>();
    public static NoteAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        adapter = new NoteAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);
        getData();

    adapter.setListener(new NoteAdapter.onNoteClickListener() {
        @Override
        public void onClick(int position) {
        }

        @Override
        public void onLongClick(int position) {
            remove(position);
        }
    });
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
remove(viewHolder.getAdapterPosition());
            }
        });
        touchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void remove(int position){
        Note note = notes.get(position);
                viewModel.deleteNote(note);
    }


    public void onClickView(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
      LiveData<List<Note>>noteFromData = viewModel.getNotes();
        noteFromData.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notesFromLiveData) {
                 notes.clear();
                notes.addAll(notesFromLiveData);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void reset(View view) {
        viewModel.deleteAllNote();
        adapter.notifyDataSetChanged();
    }
}


