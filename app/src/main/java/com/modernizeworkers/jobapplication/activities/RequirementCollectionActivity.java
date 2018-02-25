package com.modernizeworkers.jobapplication.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.fragments.WhatJobLookingForFragment;
import com.modernizeworkers.jobapplication.fragments.WhereJobLookingForFragment;

public class RequirementCollectionActivity extends AppCompatActivity implements WhatJobLookingForFragment.OnFragmentInteractionListener,
        WhereJobLookingForFragment.OnFragmentInteractionListener{
    private FrameLayout mContainerLayOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_collection);
        initUi();
    }

    private void initUi() {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContainerLayOut=(FrameLayout)findViewById(R.id.container);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = WhatJobLookingForFragment.newInstance("", "");
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
