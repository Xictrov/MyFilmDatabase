package com.example.victor.prueba;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Director, must select the appropriate columns
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_COUNTRY,
            MySQLiteHelper.COLUMN_YEAR_RELEASE, MySQLiteHelper.COLUMN_DIRECTOR,
            MySQLiteHelper.COLUMN_PROTAGONIST, MySQLiteHelper.COLUMN_CRITICS_RATE};

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Film createFilm(String title, String director, String country, Integer year,
                           String protagonist, Float rate) {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + title + " " + director);

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_DIRECTOR, director);

        // Invented data
        values.put(MySQLiteHelper.COLUMN_COUNTRY, country);
        values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, year);
        values.put(MySQLiteHelper.COLUMN_PROTAGONIST, protagonist);
        values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, rate);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void deleteFilm(long id) {
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> searchByActor(String protagonist) {
        List<Film> fp = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS, null,
                MySQLiteHelper.COLUMN_PROTAGONIST + "= '" + protagonist+"'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = cursorToFilm(cursor);
            fp.add(film);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return fp;
    }

    public void changeCriticsRate(long filmId, Float criticsRate) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS, null,
                MySQLiteHelper.COLUMN_ID + "= '" + filmId+"'", null, null, null, null);

        ContentValues values = new ContentValues();
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            values.put(MySQLiteHelper.COLUMN_TITLE, cursor.getString(1));
            values.put(MySQLiteHelper.COLUMN_COUNTRY, cursor.getString(2));
            values.put(MySQLiteHelper.COLUMN_YEAR_RELEASE, cursor.getInt(3));
            values.put(MySQLiteHelper.COLUMN_DIRECTOR, cursor.getString(4));
            values.put(MySQLiteHelper.COLUMN_PROTAGONIST, cursor.getString(5));
            values.put(MySQLiteHelper.COLUMN_CRITICS_RATE, criticsRate);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        database.update(MySQLiteHelper.TABLE_FILMS,values,
                MySQLiteHelper.COLUMN_ID + "= '" + filmId+"'",null);
    }

    public List<Film> getAllFilms() {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setCountry(cursor.getString(2));
        film.setYear(cursor.getInt(3));
        film.setDirector(cursor.getString(4));
        film.setProtagonist(cursor.getString(5));
        film.setCritics_rate(cursor.getInt(6));
        return film;
    }
}