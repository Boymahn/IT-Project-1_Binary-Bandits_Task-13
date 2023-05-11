package com.example.taskoptimizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SQLiteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TasksDB";
    private static final String TABLE_NAME = "Tasks";
    private static final String KEY_ID = "id";
    private static final String KEY_USER_ID = "user";
    private static final String DESCRIPTION = "description";
    private static final String START_DATE = "start_date";
    private static final String END_DATE = "end_date";
    private static final String PRIORITY = "priority";
    private static final String DIFFICULTY = "difficulty";
    private static final String STATUS = "status";

    public SQLiteHandler( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE Tasks( id " +
                "INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT," +
                "start_date TEXT, end_date TEXT, priority INTEGER, " +
                "difficulty INTEGER, status INTEGER)";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        this.onCreate(db);
    }
    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?",new String[] {String.valueOf(task.getId())});
    }
    /*public Task getTasks(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {"description", "start", "end", "priority"};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null );
    }*/
    public List<Task> allTasks(){
        List<Task> tasks = new LinkedList<Task>();

        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                //task.setId(Integer.parseInt(cursor.getString(0)));
                task.setDescription(cursor.getString(1));
                task.setStart(cursor.getString(2));
                task.setEnd(cursor.getString(3));
                task.setPriority(cursor.getInt(4));
                task.setDifficulty(cursor.getInt(5));
                task.setStatus(cursor.getInt(6));
                tasks.add(task);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }
    public List<Task> getOptimizedTasks(){
        List<Task> tasks = new ArrayList<Task>();

        String query = "SELECT * FROM " + TABLE_NAME +" WHERE priority IS NOT NULL AND difficulty IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                //task.setId(Integer.parseInt(cursor.getString(0)));
                task.setDescription(cursor.getString(1));
                task.setStart(cursor.getString(2));
                task.setEnd(cursor.getString(3));
                task.setPriority(cursor.getInt(4));
                task.setDifficulty(cursor.getInt(5));
                task.setStatus(cursor.getInt(6));
                tasks.add(task);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return tasks;
    }
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, task.getDescription());
        values.put(START_DATE, task.getStart());
        values.put(END_DATE, task.getEnd());
        values.put(PRIORITY, task.getPriority());
        values.put(DIFFICULTY, task.getDifficulty());
        values.put(STATUS, task.getStatus());

        db.insert(TABLE_NAME,null,values);

    }
    public int updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, task.getDescription());
        values.put(END_DATE, task.getEnd());
        values.put(STATUS, task.getStatus());

        int i = db.update(TABLE_NAME, values,"id=?",new String[] {String.valueOf(task.getId())});
        db.close();

        return i;
    }

}
