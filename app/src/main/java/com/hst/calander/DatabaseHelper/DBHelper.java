package com.hst.calander.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.hst.calander.Model.NoteModel;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final String NOTE_COLUMN_DATE = "date";
    public static final String NOTE_COLUMN_DESCRIPTION = "description";
    public static final String NOTE_COLUMN_ID = "id";
    public static final String NOTE_COLUMN_MILLI = "milliseconds";
    public static final String NOTE_COLUMN_TIME = "time";
    public static final String NOTE_COLUMN_TITLE = "title";
    public static final String NOTE_TABLE_NAME = "tbl_notes";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table tbl_notes (id integer primary key AUTOINCREMENT, title text,description text,date text, time text, milliseconds text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_notes");
        onCreate(sQLiteDatabase);
    }

    public boolean insertNote(String str, String str2, String str3, String str4, String str5) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_COLUMN_TITLE, str);
        contentValues.put(NOTE_COLUMN_DESCRIPTION, str2);
        contentValues.put(NOTE_COLUMN_DATE, str3);
        contentValues.put(NOTE_COLUMN_TIME, str4);
        contentValues.put(NOTE_COLUMN_MILLI, str5);
        writableDatabase.insert(NOTE_TABLE_NAME, null, contentValues);
        return true;
    }

    public List<NoteModel> getAllNote() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = writableDatabase.rawQuery("select * from tbl_notes", null);
        rawQuery.moveToFirst();
        while (!rawQuery.isAfterLast()) {
            NoteModel noteModel = new NoteModel();
            noteModel.setId(Integer.parseInt(rawQuery.getString(0)));
            noteModel.setTitle(rawQuery.getString(1));
            noteModel.setDescription(rawQuery.getString(2));
            noteModel.setDate(rawQuery.getString(3));
            noteModel.setTime(rawQuery.getString(4));
            noteModel.setMilli(rawQuery.getString(5));
            arrayList.add(noteModel);
            rawQuery.moveToNext();
        }
        rawQuery.close();
        return arrayList;
    }
}
