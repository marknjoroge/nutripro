package com.virtualeatery.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.virtualeatery.Constants;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    Constants constants = new Constants();

    private static final String DB_NAME = "NutriProDB";

    private static final int DB_VERSION = 1;


    private static final String NUTRIENTS_TABLE_NAME = "nutrientsTable";

    private static final String ID_COL = "id";
    private static final String MEAL_COL = "meal";
    private static final String NUTRIENT_1 = "calories";
    private static final String NUTRIENT_2 = "carbs";
    private static final String NUTRIENT_3 = "fats";
    private static final String NUTRIENT_4 = "proteins";


    private static final String MEALS_TABLE_NAME = "mealsTable";

    private static final String MEAL_DATE = "date";
    private static final String MEAL = "meal";

    String create_nutrients_table_query = "CREATE TABLE " + MEALS_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEAL_DATE + " TEXT,"
            + MEAL + " TEXT)";
    String create_consumed_food_table_query = "CREATE TABLE " + NUTRIENTS_TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEAL_COL + " TEXT,"
            + NUTRIENT_1 + " TEXT,"
            + NUTRIENT_2 + " TEXT,"
            + NUTRIENT_3 + " TEXT,"
            + NUTRIENT_4 + " TEXT)";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_nutrients_table_query);
        db.execSQL(create_consumed_food_table_query);
    }

    public void addNewNutrientInfo(String meal, String calories, String carbs, String fats, String proteins) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MEAL_COL, meal);
        values.put(NUTRIENT_1, calories);
        values.put(NUTRIENT_2, carbs);
        values.put(NUTRIENT_3, fats);
        values.put(NUTRIENT_4, proteins);

        db.insert(NUTRIENTS_TABLE_NAME, null, values);

        db.close();
    }

    public void addNewMealInfo(String meal1) {

        String date1 = constants.dateToPlain;
        Log.v("CheckDate", date1);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MEAL_DATE, date1);
        values.put(MEAL, meal1);

        db.insert(MEALS_TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<MealsTakenModel> getEatingInfo(String mealDate) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + MEALS_TABLE_NAME;

        if (!mealDate.equals(""))
            query += " WHERE " + MEAL_DATE + " LIKE '" + mealDate + "'";

        Cursor mealsCursor = db.rawQuery( query , null);

        ArrayList<MealsTakenModel> mealsTakenModelArrayList = new ArrayList<>();

        if (mealsCursor.moveToFirst()) {
            do {
                mealsTakenModelArrayList.add(new MealsTakenModel(
                        mealsCursor.getString(0),
                        mealsCursor.getString(1),
                        mealsCursor.getString(2)));
            } while (mealsCursor.moveToNext());
        }

        mealsCursor.close();
        return mealsTakenModelArrayList;
    }

    public NutrientsModel getFoodInfo(String meal) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor nutrientsCursor = db.rawQuery("SELECT * FROM "
                + NUTRIENTS_TABLE_NAME + " WHERE " + MEAL_COL
                + "='" + meal + "'", null);

        NutrientsModel nutrientsModels = new NutrientsModel();

        if (nutrientsCursor.moveToFirst()) {
            do {
                nutrientsModels.setCalories(nutrientsCursor.getString(2));
                nutrientsModels.setCarbs(nutrientsCursor.getString(3));
                nutrientsModels.setFats(nutrientsCursor.getString(4));
                nutrientsModels.setProteins(nutrientsCursor.getString(5));
            } while (nutrientsCursor.moveToNext());
        }

        nutrientsCursor.close();
        return nutrientsModels;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, MEALS_TABLE_NAME);
        dropTable(db, NUTRIENTS_TABLE_NAME);
        onCreate(db);
    }

    public void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    public boolean isDBEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor mCursor = db.rawQuery("SELECT * FROM " + MEALS_TABLE_NAME, null)) {

            return !mCursor.moveToFirst();
        }
    }

    public boolean isDataInDB(String tableName, String column, String fieldValue) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName + " WHERE " + column + " ='" + fieldValue + "'";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void deleteFoodEntry(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MEALS_TABLE_NAME, "id = ?", new String[]{id});
    }
}
