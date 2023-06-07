package com.example.taskoptimizer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OptimizedTask {

    SQLiteHandler db;
    private int id;
    private int user_id;
    private String start;
    private String end;
    private int priority;
    private int estimatedTime;
    private int status;

    private long initialTime, recommendedTime;

    private long timeWorked = 0;

    private String description;

    private int lowPriority,midPriority, highPriority, lowEstimated,midEstimated, highEstimated, altVariable;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public OptimizedTask(String description, String start, String end, int priority, int estimatedTime, int status, Context context) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.status = status;
        //setAltVar();
        this.db = new SQLiteHandler(context);
        calcRecommendedTime(priority,estimatedTime);
        setVariables();

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OptimizedTask() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDifficulty() {
        return estimatedTime;
    }

    public void setDifficulty(int difficulty) {
        this.estimatedTime = difficulty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getRemainingTime(){

        int endMonth,day,year;

        String[] endDate = end.split("\\s+");
        switch (endDate[0]){

            case "JAN":
                endMonth = 1;
                break;
            case "FEB":
                endMonth = 2;
                break;
            case "MAR":
                endMonth = 3;
                break;
            case "APR":
                endMonth = 4;
                break;
            case "MAY":
                endMonth = 5;
                break;
            case "JUN":
                endMonth = 6;
                break;
            case "JUL":
                endMonth = 7;
                break;
            case "AUG":
                endMonth = 8;
                break;
            case "SEP":
                endMonth = 9;
                break;
            case "OCT":
                endMonth = 10;
                break;
            case "NOV":
                endMonth = 11;
                break;
            case "DEC":
                endMonth = 12;
                break;
            default:
                endMonth =0;
        }
        day = Integer.parseInt(endDate[1]);
        year = Integer.parseInt(endDate[2]);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dueDate = LocalDateTime.of(year,endMonth,day,23,59);

        Duration duration = Duration.between(today,dueDate);

        return (long)duration.toDays();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setInitialTime(){

        String[] endDateString = end.split("\\s+");
        int endMonth = monthFormat(end);
        int endDay= Integer.parseInt(endDateString[1]);
        int endYear = Integer.parseInt(endDateString[2]);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.of(endYear,endMonth,endDay,23,59);

        Duration duration = Duration.between(startDate,endDate);

        initialTime = duration.toDays();

    }
    public long getInitialTime(){
        return initialTime;
    }
    private int monthFormat(String date){
        int endMonth;

        String[] endDate = date.split("\\s+");
        switch (endDate[0]){

            case "JAN":
                endMonth = 1;
                break;
            case "FEB":
                endMonth = 2;
                break;
            case "MAR":
                endMonth = 3;
                break;
            case "APR":
                endMonth = 4;
                break;
            case "MAY":
                endMonth = 5;
                break;
            case "JUN":
                endMonth = 6;
                break;
            case "JUL":
                endMonth = 7;
                break;
            case "AUG":
                endMonth = 8;
                break;
            case "SEP":
                endMonth = 9;
                break;
            case "OCT":
                endMonth = 10;
                break;
            case "NOV":
                endMonth = 11;
                break;
            case "DEC":
                endMonth = 12;
                break;
            default:
                endMonth = 1;
        }
        return endMonth;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calcRecommendedTime(int estimatedTime, int priority){
        int difficultyTime, priorityTime;

        switch (estimatedTime){
            case 1:
                difficultyTime = lowEstimated;
                break;
            case 2:
                difficultyTime = midEstimated;
                break;
            case 3:
                difficultyTime = highEstimated;
                break;
            default:
                difficultyTime = 20;
        }
        switch (priority){
            case 1:
                priorityTime = lowPriority;
                break;
            case 2:
                priorityTime = midPriority;
                break;
            case 3:
                priorityTime =highPriority;
                break;
            default:
                priorityTime = 10;
        }
        recommendedTime = difficultyTime + priorityTime;
    }
    public void setRecommendedTime(long recommendedTime){
        this.recommendedTime = recommendedTime;
    }
    public void alterRecommendedTime(long time,String operation){
        switch (operation){
            case "add":
                recommendedTime += time;
                break;
            case "subtract":
                recommendedTime-= time;
                break;
        }
    }

    public long getRecommendedTime(){
        return recommendedTime;
    }

    public long getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(long timeWorked) {
        this.timeWorked += timeWorked;
    }

    public int getLowPriority() {
        return lowPriority;
    }
    public void setLowPriority(int lowPriority) {
        this.lowPriority = lowPriority;
    }

    public int getMidPriority() {
        return midPriority;
    }

    public void setMidPriority(int midPriority) {
        this.midPriority = midPriority;
    }

    public int getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(int highPriority) {
        this.highPriority = highPriority;
    }

    public int getLowEstimated() {
        return lowEstimated;
    }

    public void setLowEstimated(int lowEstimated) {
        this.lowEstimated = lowEstimated;
    }

    public int getMidEstimated() {
        return midEstimated;
    }

    public void setMidEstimated(int midEstimated) {
        this.midEstimated = midEstimated;
    }

    public int getHighEstimated() {
        return highEstimated;
    }

    public void setHighEstimated(int highEstimated) {
        this.highEstimated = highEstimated;
    }

    public int getAltVariable() {
        return altVariable;
    }

    public void setAltVariable(int altVariable) {
        this.altVariable = altVariable;
    }

    private void setPriorityVars(){
        String[] priorities = db.getPriorities();

        setLowPriority(Integer.parseInt(priorities[0]));
        setMidPriority(Integer.parseInt(priorities[1]));
        setHighPriority(Integer.parseInt(priorities[2]));

    }
    private void setEstimatedTimeVars(){
        String[] estimatedTime = db.getEstimatedTime();

        setLowEstimated(Integer.parseInt(estimatedTime[0]));
        setMidEstimated(Integer.parseInt(estimatedTime[1]));
        setHighEstimated(Integer.parseInt(estimatedTime[2]));

    }
    private void setAltVar(){
        setAltVariable(db.getAltVar());
    }
    private void setVariables(){
        setPriorityVars();
        setEstimatedTimeVars();
        setAltVar();
    }
}
