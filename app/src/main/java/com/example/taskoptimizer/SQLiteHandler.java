package com.example.taskoptimizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class SQLiteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TasksDB";
    private static final String TABLE_NAME = "Tasks";
    private static final String OPTIMIZED_TABLE = "optimizedTasks";
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
        String CREATE_TABLE_OPTIMIZED_TASKS = "CREATE TABLE optimizedTasks( id " +
                "INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER, description TEXT," +
                "start_date TEXT, end_date TEXT, priority INTEGER, " +
                "difficulty INTEGER, status INTEGER, initialTime " +
                "INTEGER, timeWorked INTEGER, recommendedTime INTEGER," +
                "FOREIGN KEY (userId) REFERENCES UserMeta(id))";
        String CREATE_TASK_TABLE = "CREATE TABLE Tasks( id " +
                "INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER, description TEXT," +
                "start_date TEXT, end_date TEXT, status INTEGER," +
                "FOREIGN KEY (userId) REFERENCES UserMeta(id))";
        String CREATE_META_DATA = "CREATE TABLE UserMeta(id INTEGER PRIMARY KEY AUTOINCREMENT, email " +
                "TEXT DEFAULT 'default',password TEXT DEFAULT 'default', priorityVar TEXT DEFAULT '30 60 90'," +
                " estimatedTimeVar TEXT DEFAULT '30 60 120', altVar INTEGER DEFAULT 30)";

        db.execSQL(CREATE_TABLE_OPTIMIZED_TASKS);
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_META_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS optimizedTasks");
        db.execSQL("DROP TABLE IF EXISTS Meta");
        this.onCreate(db);
    }
    public void deleteTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?",new String[] {String.valueOf(task.getId())});
    }

    public List<OptimizedTask> allTasks(){
        List<OptimizedTask> optimizedTasks = new LinkedList<OptimizedTask>();

        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                OptimizedTask optimizedTask = new OptimizedTask();
                //task.setId(Integer.parseInt(cursor.getString(0)));
                optimizedTask.setDescription(cursor.getString(1));
                optimizedTask.setStart(cursor.getString(2));
                optimizedTask.setEnd(cursor.getString(3));
                optimizedTask.setStatus(cursor.getInt(4));
                optimizedTasks.add(optimizedTask);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return optimizedTasks;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<OptimizedTask> getOptimizeTasks(){
        List<OptimizedTask> optimizedTasks = new ArrayList<OptimizedTask>();

        String query = "SELECT * FROM " + OPTIMIZED_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                OptimizedTask optimizedTask = new OptimizedTask();
                optimizedTask.setId(Integer.parseInt(cursor.getString(0)));
                optimizedTask.setDescription(cursor.getString(1));
                optimizedTask.setStart(cursor.getString(2));
                optimizedTask.setEnd(cursor.getString(3));
                optimizedTask.setPriority(cursor.getInt(4));
                optimizedTask.setDifficulty(cursor.getInt(5));
                optimizedTask.setStatus(cursor.getInt(6));
                optimizedTask.setTimeWorked(cursor.getLong(8));
                optimizedTask.setRecommendedTime(cursor.getLong(9));
                optimizedTasks.add(optimizedTask);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return optimizedTasks;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addOptimizedTask(OptimizedTask optimizedTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, optimizedTask.getDescription());
        values.put(START_DATE, optimizedTask.getStart());
        values.put(END_DATE, optimizedTask.getEnd());
        values.put(PRIORITY, optimizedTask.getPriority());
        values.put(DIFFICULTY, optimizedTask.getDifficulty());
        values.put(STATUS, optimizedTask.getStatus());
        optimizedTask.setInitialTime();
        values.put("initialTime", optimizedTask.getInitialTime());
        values.put("timeWorked", optimizedTask.getTimeWorked());
        optimizedTask.calcRecommendedTime(optimizedTask.getDifficulty(), optimizedTask.getPriority());
        values.put("recommendedTime", optimizedTask.getRecommendedTime());

        db.insert(OPTIMIZED_TABLE,null,values);

    }
    public void addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, task.getDescription());
        values.put(START_DATE, task.getStart());
        values.put(END_DATE, task.getEnd());
        values.put(STATUS, task.getStatus());
        db.insert(TABLE_NAME,null,values);
    }
    public int updateTask(OptimizedTask optimizedTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, optimizedTask.getDescription());
        values.put(END_DATE, optimizedTask.getEnd());
        values.put(STATUS, optimizedTask.getStatus());

        int i = db.update(TABLE_NAME, values,"id=?",new String[] {String.valueOf(optimizedTask.getId())});
        db.close();
        return i;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateOptimizedTasks(){
        List<OptimizedTask> optimizedTasks;
        Optimizer optimizer = new Optimizer(getOptimizeTasks());

        optimizedTasks = optimizer.getOptimizedTasks();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i =0; i<optimizedTasks.size(); i++ ){
            values.put("recommendedTime", optimizedTasks.get(i).getRecommendedTime());
            db.update(OPTIMIZED_TABLE, values,"id=?",new String[] {String.valueOf(optimizedTasks.get(i).getId())});
        }

        db.close();
    }
    public void deleteOptimizedTask(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        db.execSQL("DELETE FROM optimizedTasks WHERE id = "+id);

    }

    public void createDefault(){
        String getDefault = "SELECT COUNT(*) FROM UserMeta";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getDefault,null);

        if(cursor.moveToFirst()){

            int count = cursor.getInt(0);

            if(count == 0){
                db.close();
                SQLiteDatabase db1 = this.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("email", "default");
                values.put("password","default");
                values.put("priorityVar","30 60 90");
                values.put("estimatedTimeVar", "30 60 120");
                values.put("altVar", 30);
                db1.insert("UserMeta",null,values);
                db1.close();
            }

            cursor.close();
            db.close();
        }


    }
    public String[] getPriorities(){

            String[] priorities = {};
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT priorityVar FROM UserMeta",null);
            if(cursor.moveToFirst()){
                do{
                    priorities = cursor.getString(0).split(" ");

                }while(cursor.moveToNext());
                cursor.close();
            }
        return priorities;
    }
    public String[] getEstimatedTime(){

        String[] estimatedTime = {};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT estimatedTimeVar FROM UserMeta",null);
        if(cursor.moveToFirst()){
            do{
                estimatedTime = cursor.getString(0).split(" ");

            }while(cursor.moveToNext());
            cursor.close();
        }
        return estimatedTime;
    }
    public int getAltVar(){

        int altVar = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT altVar FROM UserMeta",null);
        if(cursor.moveToFirst()){
            do{
                altVar = cursor.getInt(0);

            }while(cursor.moveToNext());
            cursor.close();
        }
        return altVar;
    }
    public boolean isUser(String email, String password){
        boolean match = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email, password FROM UserMeta", null);
        if(cursor.moveToFirst()){
            do{
                if(Objects.equals(cursor.getString(0), email) && Objects.equals(cursor.getString(1), password)){
                    match = true;
                }else{
                    match = false;
                }
            }while(cursor.moveToNext());
        }

        return match;
    }

}
