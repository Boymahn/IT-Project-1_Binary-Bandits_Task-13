package com.example.taskoptimizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NavBarControl extends AppCompatActivity {

    boolean nightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navibar_control);

        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("night", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        FloatingActionButton fab = findViewById(R.id.add_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.chatBox:
                openChatBox();
                return true;
            case R.id.settings:
                openSettings();
                return true;
            case R.id.about:
                openAbout();
                return true;
            case R.id.feedback:
                openFeedback();
                return true;
            case R.id.darktheme:
                toggleTheme();
                return true;
            case R.id.logout:
                openLoginPage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(NavBarControl.this, Settings.class);
        startActivity(intent);
    }

    private void openChatBox() {
        Intent intent = new Intent(NavBarControl.this, ChatBox.class);
        startActivity(intent);
    }

    private void openAbout() {
        Intent intent = new Intent(NavBarControl.this, About.class);
        startActivity(intent);
    }

    private void openFeedback() {
        Intent intent = new Intent(NavBarControl.this, Feedback.class);
        startActivity(intent);
    }

    private void openLoginPage() {
        Intent intent = new Intent(NavBarControl.this, MainActivity.class);
        startActivity(intent);
    }

    private void toggleTheme() {
        nightMode = !nightMode;
        editor = sharedPreferences.edit();
        editor.putBoolean("night", nightMode);
        editor.apply();
        recreate(); // Recreate the activity to apply the new theme
    }

    public void openDialog() {
        AddTaskDialog dialog = new AddTaskDialog();
        dialog.show(getSupportFragmentManager(), "Add Task Dialog");
    }
}
