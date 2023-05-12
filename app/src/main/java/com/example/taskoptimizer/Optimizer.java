package com.example.taskoptimizer;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Optimizer {

    private final List<OptimizedTask> optimizedTasks;

    public Optimizer(List<OptimizedTask> optimizedTasks) {
        this.optimizedTasks = optimizedTasks;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void optimize(){

        for (int i = 0; i < optimizedTasks.size(); i++ ){
            for (int j = i+1; j< optimizedTasks.size(); j++){

                if( optimizedTasks.get(i).getInitialTime() == optimizedTasks.get(j).getInitialTime()
                        && optimizedTasks.get(i).getDifficulty() == optimizedTasks.get(j).getDifficulty()
                        && optimizedTasks.get(i).getPriority() != optimizedTasks.get(j).getPriority()){

                    if(optimizedTasks.get(i).getPriority() > optimizedTasks.get(j).getPriority()){
                        optimizedTasks.get(i).alterRecommendedTime(30,"add");
                        optimizedTasks.get(j).alterRecommendedTime(30,"subtract");
                    }else{
                        optimizedTasks.get(i).alterRecommendedTime(30,"subtract");
                        optimizedTasks.get(j).alterRecommendedTime(30,"add");
                    }
                }else if(optimizedTasks.get(i).getInitialTime() == optimizedTasks.get(j).getInitialTime()
                        && optimizedTasks.get(i).getDifficulty() != optimizedTasks.get(j).getDifficulty()
                        && optimizedTasks.get(i).getPriority() == optimizedTasks.get(j).getPriority()){

                    if(optimizedTasks.get(i).getDifficulty() > optimizedTasks.get(j).getDifficulty()){
                        optimizedTasks.get(i).alterRecommendedTime(30,"add");
                        optimizedTasks.get(j).alterRecommendedTime(30,"subtract");
                    }else{
                        optimizedTasks.get(i).alterRecommendedTime(30,"subtract");
                        optimizedTasks.get(j).alterRecommendedTime(30,"add");
                }
                }else if(optimizedTasks.get(i).getInitialTime() == optimizedTasks.get(j).getInitialTime()
                        && optimizedTasks.get(i).getDifficulty() != optimizedTasks.get(j).getDifficulty()
                        && optimizedTasks.get(i).getPriority() != optimizedTasks.get(j).getPriority()) {

                    if (optimizedTasks.get(i).getPriority() > optimizedTasks.get(j).getPriority()) {
                        optimizedTasks.get(i).alterRecommendedTime(30, "add");
                        optimizedTasks.get(j).alterRecommendedTime(30, "subtract");
                    } else {
                        optimizedTasks.get(i).alterRecommendedTime(30, "subtract");
                        optimizedTasks.get(j).alterRecommendedTime(30, "add");
                    }

                }
                else if(optimizedTasks.get(i).getRecommendedTime() != optimizedTasks.get(j).getRecommendedTime()
                        && optimizedTasks.get(i).getDifficulty() == optimizedTasks.get(j).getDifficulty()
                        && optimizedTasks.get(i).getPriority() == optimizedTasks.get(j).getPriority()) {

                    if (optimizedTasks.get(i).getRecommendedTime() > optimizedTasks.get(j).getRecommendedTime()) {
                        optimizedTasks.get(i).alterRecommendedTime(30, "add");
                        optimizedTasks.get(j).alterRecommendedTime(30, "subtract");
                    } else {
                        optimizedTasks.get(i).alterRecommendedTime(30, "subtract");
                        optimizedTasks.get(j).alterRecommendedTime(30, "add");
                    }

                }
                else if(optimizedTasks.get(i).getRecommendedTime() != optimizedTasks.get(j).getRecommendedTime()
                        && optimizedTasks.get(i).getDifficulty() != optimizedTasks.get(j).getDifficulty()
                        && optimizedTasks.get(i).getPriority() == optimizedTasks.get(j).getPriority()) {

                    if (optimizedTasks.get(i).getDifficulty() > optimizedTasks.get(j).getDifficulty()) {
                        optimizedTasks.get(i).alterRecommendedTime(30, "add");
                        optimizedTasks.get(j).alterRecommendedTime(30, "subtract");
                    } else {
                        optimizedTasks.get(i).alterRecommendedTime(30, "subtract");
                        optimizedTasks.get(j).alterRecommendedTime(30, "add");
                    }

                }
                else if(optimizedTasks.get(i).getRecommendedTime() != optimizedTasks.get(j).getRecommendedTime()
                        && optimizedTasks.get(i).getDifficulty() == optimizedTasks.get(j).getDifficulty()
                        && optimizedTasks.get(i).getPriority() != optimizedTasks.get(j).getPriority()) {

                    if (optimizedTasks.get(i).getPriority() > optimizedTasks.get(j).getPriority()) {
                        optimizedTasks.get(i).alterRecommendedTime(30, "add");
                        optimizedTasks.get(j).alterRecommendedTime(30, "subtract");
                    } else {
                        optimizedTasks.get(i).alterRecommendedTime(30, "subtract");
                        optimizedTasks.get(j).alterRecommendedTime(30, "add");
                    }

                }


            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<OptimizedTask> getOptimizedTasks(){
        optimize();
        return optimizedTasks;
    }

}
