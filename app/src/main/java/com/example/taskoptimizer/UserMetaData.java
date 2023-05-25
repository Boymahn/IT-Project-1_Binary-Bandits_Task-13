package com.example.taskoptimizer;

import android.media.Image;

public class UserMetaData {

    private String email;
    private Image icon;
    private int[] prioritySetting;
    private int[] difficultySetting;

    private int timeVariable;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public int[] getPrioritySetting() {
        return prioritySetting;
    }

    public void setPrioritySetting(int[] prioritySetting) {
        this.prioritySetting = prioritySetting;
    }

    public int[] getDifficultySetting() {
        return difficultySetting;
    }

    public void setDifficultySetting(int[] difficultySetting) {
        this.difficultySetting = difficultySetting;
    }

    public int getTimeVariable() {
        return timeVariable;
    }

    public void setTimeVariable(int timeVariable) {
        this.timeVariable = timeVariable;
    }


}
