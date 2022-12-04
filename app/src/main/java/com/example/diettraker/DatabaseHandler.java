package com.example.diettraker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mealManager";
    private static final String TABLE_MEALS = "meals";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_CARBOHYDRATES = "carbohydrates";
    private static final String KEY_FATS = "fats";
    private static final String KEY_PROTEINS = "proteins";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MEALS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_CALORIES + " INT,"
                + KEY_CARBOHYDRATES + " INT," + KEY_FATS + " INT," + KEY_PROTEINS + " INT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);

        onCreate(db);
    }

    // code to add the new meal
    void addMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, meal.get_date());
        values.put(KEY_TIME, meal.get_time());
        values.put(KEY_CALORIES, meal.get_calories());
        values.put(KEY_CARBOHYDRATES, meal.get_carbohydrates());
        values.put(KEY_FATS, meal.get_fats());
        values.put(KEY_PROTEINS, meal.get_proteins());

        db.insert(TABLE_MEALS, null, values);

        db.close();
    }

    // code to get the single meal
    Meal getMeal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Meal meal = new Meal();

        Cursor cursor = db.query(TABLE_MEALS, new String[] {
                KEY_ID, KEY_DATE, KEY_TIME, KEY_CALORIES, KEY_CARBOHYDRATES, KEY_FATS, KEY_PROTEINS
                }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            meal = new Meal(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6))
            );

            cursor.close();
        }

        return meal;
    }

    // code to get all meals in a list view
    public List<Meal> getAllMeals() {
        List<Meal> mealList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MEALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Meal meal = new Meal();
                meal.set_id(Integer.parseInt(cursor.getString(0)));
                meal.set_date(cursor.getString(1));
                meal.set_time(cursor.getString(2));
                meal.set_calories(Integer.parseInt(cursor.getString(3)));
                meal.set_carbohydrates(Integer.parseInt(cursor.getString(4)));
                meal.set_fats(Integer.parseInt(cursor.getString(5)));
                meal.set_proteins(Integer.parseInt(cursor.getString(6)));

                mealList.add(meal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return mealList;
    }

    // code to update the single meal
    public void updateMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, meal.get_date());
        values.put(KEY_TIME, meal.get_time());
        values.put(KEY_CALORIES, meal.get_calories());
        values.put(KEY_CARBOHYDRATES, meal.get_carbohydrates());
        values.put(KEY_FATS, meal.get_fats());
        values.put(KEY_PROTEINS, meal.get_proteins());

        db.update(TABLE_MEALS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(meal.get_id()) });
    }

    // Deleting single meal
    public void deleteMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEALS, KEY_ID + " = ?",
                new String[] { String.valueOf(meal.get_id()) });
        db.close();
    }

    // code to get the summary of meals by day
    public List<Meal> getSummary() {
        List<Meal> mealList = new ArrayList<>();
        String selectQuery = "SELECT "+KEY_DATE+", sum("+KEY_CALORIES+"), sum("+KEY_CARBOHYDRATES+
                "), sum("+KEY_FATS+"), sum("+KEY_PROTEINS+") FROM " + TABLE_MEALS +
                " GROUP BY " + KEY_DATE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Meal meal = new Meal();
                meal.set_date(cursor.getString(0));
                meal.set_calories(Integer.parseInt(cursor.getString(1)));
                meal.set_carbohydrates(Integer.parseInt(cursor.getString(2)));
                meal.set_fats(Integer.parseInt(cursor.getString(3)));
                meal.set_proteins(Integer.parseInt(cursor.getString(4)));

                mealList.add(meal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return mealList;
    }
}
