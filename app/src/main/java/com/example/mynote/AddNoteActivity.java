package com.example.mynote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle,editTextDescription;
    private Spinner spinnerDaysOfWeek;
    private RadioGroup radioGroupPriority;
private static MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        editTextTitle = findViewById(R.id.edittextTitle);
        editTextDescription = findViewById(R.id.edittextDescription);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        spinnerDaysOfWeek  = findViewById(R.id.spinnerDayOfWeek);
    }
    public void onClickSaveNote(View view) {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dayOfWeek = spinnerDaysOfWeek.getSelectedItem().toString();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());
           if (isFull(title,description)){
        Note note = new Note(title,description,dayOfWeek,priority);

      viewModel.insertNote(note);
        MainActivity.adapter.notifyDataSetChanged();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
               finish();
           }  else
           {
               Toast.makeText(AddNoteActivity.this, "Заполните все строки", Toast.LENGTH_SHORT).show();
           }


    }
            private boolean isFull(String title, String describtion){
            return !title.isEmpty() && !describtion.isEmpty();
            }
  }
