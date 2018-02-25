package com.modernizeworkers.jobapplication.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.fragments.JobDetailsFragment;
import com.modernizeworkers.jobapplication.fragments.JobListingFragment;


public class JobContainerActivity extends AppCompatActivity implements JobListingFragment.OnFragmentInteractionListener,
JobDetailsFragment.OnFragmentInteractionListener{

    private FrameLayout mConatinerFrameLayOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_container);
        intUi();


    }

    private void intUi() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mConatinerFrameLayOut = (FrameLayout) findViewById(R.id.jobcontainer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = JobListingFragment.newInstance("", "");
        if (fragment != null) {
            fragmentManager.beginTransaction().add(R.id.jobcontainer, fragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
