package com.example.taskoptimizer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OptimizedTask {
    private int id;
    private int user_id;
    private String start;
    private String end;
    private int priority;
    private int difficulty;
    private int status;

    private long initialTime, recommendedTime;

    private long timeWorked = 0;

    private String description;



    @RequiresApi(api = Build.VERSION_CODES.O)
    public OptimizedTask(String description, String start, String end, int priority, int difficulty, int status) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.difficulty = difficulty;
        this.status = status;
        setRecommendedTime();
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
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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
        String[] startDateString = start.split("\\s+");

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
                endMonth =0;
        }
        return endMonth;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setRecommendedTime(){
        int difficultyTime, priorityTime;

        switch (difficulty){
            case 1:
                difficultyTime = 30;
                break;
            case 2:
                difficultyTime = 60;
                break;
            case 3:
                difficultyTime = 120;
                break;
            default:
                difficultyTime = 0;
        }
        switch (priority){
            case 1:
                priorityTime = 10;
                break;
            case 2:
                priorityTime = 60;
                break;
            case 3:
                priorityTime = 90;
                break;
            default:
                priorityTime = 0;
        }
        recommendedTime = initialTime * (difficultyTime + priorityTime);
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

}
