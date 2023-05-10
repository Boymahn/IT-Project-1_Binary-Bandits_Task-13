package com.example.taskoptimizer;

public class Task {
    private int id;
    private int user_id;
    private String start;
    private String end;
    private int priority;
    private int difficulty;
    private int status;

    private String description;

    public Task(String description, String end, int status) {
        this.description = description;
        this.end = end;
        this.status = status;
    }

    public Task(String description, String start, String end, int priority, int difficulty, int status) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.priority = priority;
        this.difficulty = difficulty;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task() {
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

    public String getRemainingTime(){
        return end;
    }
}
