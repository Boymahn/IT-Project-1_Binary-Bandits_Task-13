package com.example.taskoptimizer;


import java.util.ArrayList;

public class Optimizer {

    private ArrayList<Task> tasks;

    public Optimizer(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public void optimize(){

        for (int i = 0; i < tasks.size(); i++ ){
            if(tasks.get(i).getPriority()==tasks.get(i+1).getPriority() && tasks.get(i).getDifficulty() == tasks.get(i+1).getDifficulty()){
                //assign task to timeline
            }else if(tasks.get(i).getPriority() != tasks.get(i+1).getPriority() && tasks.get(i).getDifficulty() == tasks.get(i+1).getDifficulty()){

            }
        }
    }

}
