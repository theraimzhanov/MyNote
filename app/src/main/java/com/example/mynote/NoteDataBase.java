package com.example.mynote;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class NoteDataBase extends RoomDatabase {
      private static NoteDataBase dataBase;
      private static final String DATA_NAME = "NOTE_ROOM";
      private static final Object LOG = new Object();
      public  static  NoteDataBase getInstance(Context context){
          synchronized (LOG){
          if (dataBase == null){
                dataBase = Room.databaseBuilder(context,NoteDataBase.class,DATA_NAME).allowMainThreadQueries().build();
          }
          }
       return dataBase;
      }
   public abstract NoteDao noteDao();
}
