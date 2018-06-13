package huang.zhiqin.attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import huang.zhiqin.attendance.Bean.*;

public class WelcomeActivity extends Activity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_layout);
        mTimer = new Timer();//Create a timer
        setTimerTask();//executed timer task
    }

    //setting up timer task
    private void setTimerTask() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences userInfo = getSharedPreferences("user", Context.MODE_PRIVATE);
                boolean isRemember = userInfo.getBoolean("check", false);//decide whether re-login
                if (!isRemember) {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    int type = userInfo.getInt("user", 3);
                    if (type == 0) {
                        Person stu = new Person(userInfo.getString("name", ""), userInfo.getString("surname", ""), userInfo.getInt("user", 0), userInfo.getInt("id", 0));
                        Intent intent1 = new Intent(WelcomeActivity.this, StudentActivity.class);
                        intent1.putExtra("key", stu);
                        startActivity(intent1);
                        finish();
                    } else if (type == 1) {
                        Person manager = new Person(userInfo.getString("name", ""), userInfo.getString("surname", ""), userInfo.getInt("user", 1), userInfo.getInt("id", 0));
                        Intent intent2 = new Intent(WelcomeActivity.this, ManagerActivity.class);
                        intent2.putExtra("key", manager);
                        startActivity(intent2);
                        finish();
                    }else if (type == 3){
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (type == 2) {
                        Person stu = new Person(userInfo.getString("name", ""), userInfo.getString("surname", ""), userInfo.getInt("user", 0), userInfo.getInt("id", 0));
                        Intent intent1 = new Intent(WelcomeActivity.this, StudentActivity.class);
                        intent1.putExtra("key", stu);
                        startActivity(intent1);
                        finish();
                    }
                }
            }
        }, 2000);
    }

    protected void onDestroy() {
        super.onDestroy();
        // cancel timer
        mTimer.cancel();
    }
}