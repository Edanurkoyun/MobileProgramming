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
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Veritabanı adı sabiti
    private static final String DATABASE_NAME = "quizDB";
    // Veritabanı sürümü (değişiklik yapıldığında artırılmalı)
    private static final int DATABASE_VERSION = 2;

    // DBHelper sınıfı, SQLiteOpenHelper sınıfından kalıtım alıyor
    public DBHelper(Context context) {
        // Üst sınıfın (SQLiteOpenHelper) kurucusunu çağırarak veritabanı oluşturuluyor veya açılıyor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Veritabanı ilk kez oluşturulduğunda çağrılır
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Sorular tablosunu oluşturmak için SQL komutu
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Otomatik artan birincil anahtar
                "question TEXT, " +
                "option_a TEXT, " +
                "option_b TEXT, " +
                "option_c TEXT, " +
                "option_d TEXT, " +
                "correct_answer TEXT)";
        db.execSQL(CREATE_TABLE); // SQL komutunu çalıştır

        // Skorları tutacak tabloyu oluştur
        String CREATE_SCORES_TABLE = "CREATE TABLE IF NOT EXISTS scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nickname TEXT, " +
                "score INTEGER)";
        db.execSQL(CREATE_SCORES_TABLE); // Skor tablosunu oluştur
    }

    // Veritabanı sürümü değiştiğinde çağrılır
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Önceki tabloları sil
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS scores"); // Skor tablosunu da sil
        onCreate(db); // Tabloları yeniden oluştur
    }

    // Skor ekleme veya güncelleme metodu
    public void addScore(String nickname, int score) {
        SQLiteDatabase db = this.getWritableDatabase(); // Veritabanını yazılabilir modda aç

        // Kullanıcının daha önce kayıtlı olup olmadığını kontrol et
        Cursor cursor = db.rawQuery("SELECT score FROM scores WHERE nickname = ?", new String[]{nickname});

        if (cursor.moveToFirst()) {
            // Kullanıcı zaten varsa, mevcut skoru al ve yeni skoru ekle
            int existingScore = cursor.getInt(0);
            int newTotalScore = existingScore + score;

            // Yeni toplam skoru veritabanına güncelle
            ContentValues values = new ContentValues();
            values.put("score", newTotalScore);
            db.update("scores", values, "nickname = ?", new String[]{nickname});
        } else {
            // Kullanıcı daha önce eklenmemişse, yeni kayıt olarak ekle
            ContentValues values = new ContentValues();
            values.put("nickname", nickname);
            values.put("score", score);
            db.insert("scores", null, values);
        }

        cursor.close(); // Cursor'ü kapat
        db.close();     // Veritabanını kapat
    }

    // Tüm skorları sıfırlayan metot
    public void resetScores() {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı aç
        String resetQuery = "UPDATE scores SET score = 0"; // Tüm skorları sıfırla
        db.execSQL(resetQuery); // Sorguyu çalıştır
        db.close(); // Veritabanını kapat
    }

    // Skor listesini sıralı bir şekilde döndüren metot
    public List<String> getScores() {
        List<String> scoreList = new ArrayList<>(); // Skor listesini tutacak liste
        SQLiteDatabase db = this.getReadableDatabase(); // Okunabilir veritabanı aç
        Cursor cursor = db.rawQuery("SELECT nickname, score FROM scores ORDER BY score DESC", null);

        if (cursor.moveToFirst()) {
            // Tüm kayıtları sırayla listeye ekle
            do {
                String nickname = cursor.getString(0);
                int score = cursor.getInt(1);
                scoreList.add(nickname + " - " + score); // "isim - skor" formatında ekle
            } while (cursor.moveToNext());
        }

        cursor.close(); // Cursor'ü kapat
        db.close();     // Veritabanını kapat
        return scoreList; // Skor listesini döndür
    }

    // Soruları veritabanına ekleyen metot
    public void addQuestions() {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı aç
        db.execSQL("DELETE FROM questions"); // Önceki soruları sil

        ContentValues values = new ContentValues(); // Yeni veriler için ContentValues oluştur

        // İlk soruyu ekle
        values.put("question", "Who was the first President of the United States?");
        values.put("option_a", "Benjamin Franklin");
        values.put("option_b", "George Washington");
        values.put("option_c", "Thomas Jefferson");
        values.put("option_d", " John Adams");
        values.put("correct_answer", "George Washington");
        db.insert("questions", null, values); // Veriyi tabloya ekle

        values.clear(); // Önceki verileri temizle

        // İkinci soruyu ekle
        values.put("question", "What is the capital of France?");
        values.put("option_a", "Berlin");
        values.put("option_b", "Madrid");
        values.put("option_c", "Paris");
        values.put("option_d", "Rome");
        values.put("correct_answer", "Paris");
        db.insert("questions", null, values); // Veriyi tabloya ekle

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

        values.clear();
        values.put("question", "What is the capital city of Germany?");
        values.put("option_a", "Munich");
        values.put("option_b", "Berlin");
        values.put("option_c", "Frankfurt");
        values.put("option_d", "Hamburg");
        values.put("correct_answer", "Berlin");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Who wrote the play 'Romeo and Juliet'?");
        values.put("option_a", "Charles Dickens");
        values.put("option_b", "William Shakespeare");
        values.put("option_c", "Jane Austen");
        values.put("option_d", "Leo Tolstoy");
        values.put("correct_answer", "William Shakespeare");

        values.clear();
        values.put("question", "Which element has the chemical symbol 'O'?");
        values.put("option_a", "Oxygen");
        values.put("option_b", "Gold");
        values.put("option_c", "Osmium");
        values.put("option_d", "Hydrogen");
        values.put("correct_answer", "Oxygen");
        db.insert("questions", null, values);


        values.clear();
        values.put("question", "What is the largest continent on Earth?");
        values.put("option_a", "Africa");
        values.put("option_b", "Europe");
        values.put("option_c", "Asia");
        values.put("option_d", "North America");
        values.put("correct_answer", "Asia");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Which river is the longest in the world?");
        values.put("option_a", "Amazon River");
        values.put("option_b", "Yangtze River");
        values.put("option_c", "Mississippi River");
        values.put("option_d", "Nile River");
        values.put("correct_answer", "Nile River");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Who wrote 'The Little Prince'?");
        values.put("option_a", "J.K. Rowling");
        values.put("option_b", "George Orwell");
        values.put("option_c", "Antoine de Saint-Exupéry");
        values.put("option_d", "Mark Twain");
        values.put("correct_answer", "Antoine de Saint-Exupéry");
        db.insert("questions", null, values);

        values.clear();
        values.put("question", "Who painted the Mona Lisa?");
        values.put("option_a", "Leonardo da Vinci");
        values.put("option_b", "Michelangelo");
        values.put("option_c", "Vincent van Gogh");
        values.put("option_d", "Pablo Picasso");
        values.put("correct_answer", "Leonardo da Vinci");
        db.insert("questions", null, values);

        db.close();
    }





    // Veritabanındaki soruları çekme metodu
    public ArrayList<QuestionModel> getAllQuestions() {
        ArrayList<QuestionModel> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Veritabanını sadece okuma modunda açıyoruz
        // Veritabanından tüm soruları seçmek için rawQuery kullanıyoruz
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);


        // Eğer cursor geçerli bir veriye sahipse ve ilk satıra geçebiliyorsa
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Cursor'dan her bir sütunun verisini alıyoruz ve bir QuestionModel nesnesi oluşturuyoruz
                @SuppressLint("Range") QuestionModel question = new QuestionModel(
                        cursor.getString(cursor.getColumnIndex("question")),// 'question' sütunundan soruyu alıyoruz
                        cursor.getString(cursor.getColumnIndex("option_a")),
                        cursor.getString(cursor.getColumnIndex("option_b")),
                        cursor.getString(cursor.getColumnIndex("option_c")),
                        cursor.getString(cursor.getColumnIndex("option_d")),
                        cursor.getString(cursor.getColumnIndex("correct_answer"))
                );
                questionList.add(question); // Oluşturduğumuz QuestionModel nesnesini listeye ekliyoruz
            } while (cursor.moveToNext()); // Cursor bir sonraki satıra geçiyor
        }

        cursor.close(); // Cursor'ı kapatıyoruz
        db.close();
        return questionList; // Soru listesini döndürüyoruz

    }
}

