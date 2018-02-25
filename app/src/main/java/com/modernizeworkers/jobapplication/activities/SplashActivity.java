package com.modernizeworkers.jobapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.util.PreferencesHandler;
public class SplashActivity extends AppCompatActivity {

    public static final long SPLASH_WAIT_TIME = 2500;
    private PreferencesHandler mPreferencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUi();
    }
    private void initUi() {
        mPreferencesHandler = PreferencesHandler.getInstance(SplashActivity.this);
        Thread waitThread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(SPLASH_WAIT_TIME);
                    if (mPreferencesHandler.getJobName() != null && mPreferencesHandler.getJobName().length() > 0 &&
                            mPreferencesHandler.getJobLocation() != null && mPreferencesHandler.getJobLocation().length() > 0) {
                        Intent intent = new Intent(getApplicationContext(), JobContainerActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        Intent intent = new Intent(getApplicationContext(), RequirementCollectionActivity.class);
                        startActivity(intent);
                        finish();


                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        waitThread.start();

    }


}
