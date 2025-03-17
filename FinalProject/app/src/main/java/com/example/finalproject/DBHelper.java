package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.finalproject.Models.QuestionModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quizDB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabloyu oluşturuyoruz
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT, " +
                "option_a TEXT, " +
                "option_b TEXT, " +
                "option_c TEXT, " +
                "option_d TEXT, " +
                "correct_answer TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }

    // Soru ekleme metodu
    public void addQuestions() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM questions");
        ContentValues values = new ContentValues();

        // Soruları veritabanına ekliyoruz
        values.put("question", "Who was the first President of the United States?");
        values.put("option_a", "Benjamin Franklin");
        values.put("option_b", "George Washington");
        values.put("option_c", "Thomas Jefferson");
        values.put("option_d", " John Adams");
        values.put("correct_answer", "George Washington");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "What is the capital of France?");
        values.put("option_a", "Berlin");
        values.put("option_b", "Madrid");
        values.put("option_c", "Paris");
        values.put("option_d", "Rome");
        values.put("correct_answer", "Paris");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "In which year did the Titanic sink?");
        values.put("option_a", "1910");
        values.put("option_b", "1912");
        values.put("option_c", "1920");
        values.put("option_d", "1915");
        values.put("correct_answer", "1912");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Which planet is known as the Red Planet?");
        values.put("option_a", "Earth");
        values.put("option_b", "Mars");
        values.put("option_c", "Venus");
        values.put("option_d", "Jupiter");
        values.put("correct_answer", "Mars");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "What is the largest mammal?");
        values.put("option_a", "Elephant");
        values.put("option_b", "Whale");
        values.put("option_c", "Shark");
        values.put("option_d", "Lion");
        values.put("correct_answer", "Whale");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Who was the leader of Nazi Germany during World War II?");
        values.put("option_a", "Joseph Stalin");
        values.put("option_b", "Benito Mussolini");
        values.put("option_c", "Adolf Hitler");
        values.put("option_d", "Winston Churchill");
        values.put("correct_answer", "Adolf Hitler");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Who discovered America in 1492?");
        values.put("option_a", "Marco Polo");
        values.put("option_b", "Christopher Columbus");
        values.put("option_c", "Ferdinand Magellan");
        values.put("option_d", "Hernan Cortés");
        values.put("correct_answer", "Christopher Columbus");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "When did World War I take place?");
        values.put("option_a", "1910-1915");
        values.put("option_b", "1914-1918");
        values.put("option_c", "1915-1920");
        values.put("option_d", "1920-1925");
        values.put("correct_answer", "1914-1918");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Who was the first President of the Republic of Turkey?");
        values.put("option_a", "İsmet İnönü");
        values.put("option_b", "Mustafa Kemal Atatürk");
        values.put("option_c", "Recep Tayyip Erdoğan");
        values.put("option_d", "Abdullah Gül");
        values.put("correct_answer", "Mustafa Kemal Atatürk");
        db.insert("questions", null, values);

        values.put("question", "In what year did the Berlin Wall fall?");
        values.put("option_a", "1985");
        values.put("option_b", "1987");
        values.put("option_c", "1989");
        values.put("option_d", "1991");
        values.put("correct_answer", "1989");
        db.insert("questions", null, values);

        db.close();
    }





    // Veritabanındaki soruları çekme metodu
    public ArrayList<QuestionModel> getAllQuestions() {
        ArrayList<QuestionModel> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);



        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") QuestionModel question = new QuestionModel(
                        cursor.getString(cursor.getColumnIndex("question")),
                        cursor.getString(cursor.getColumnIndex("option_a")),
                        cursor.getString(cursor.getColumnIndex("option_b")),
                        cursor.getString(cursor.getColumnIndex("option_c")),
                        cursor.getString(cursor.getColumnIndex("option_d")),
                        cursor.getString(cursor.getColumnIndex("correct_answer"))
                );
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return questionList;

    }
}

