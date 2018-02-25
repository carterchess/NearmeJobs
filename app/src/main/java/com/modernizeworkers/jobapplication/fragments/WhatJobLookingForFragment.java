package com.modernizeworkers.jobapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.application.AppController;
import com.modernizeworkers.jobapplication.util.PreferencesHandler;
import com.modernizeworkers.jobapplication.util.Utils;


public class WhatJobLookingForFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final long SPLASH_WAIT_TIME = 1000;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private View mRootView;
    private Button mNextButton;
    private TextView mWhatJobLookingTextView;
    private TextView mStepCount;
    private EditText mJobEditText;
    private PreferencesHandler mPreferencesHandler;
    private View mHorizontalView;
    private Typeface mTypeface;

    public WhatJobLookingForFragment() {
        // Required empty public constructor
    }

    public static WhatJobLookingForFragment newInstance(String param1, String param2) {
        WhatJobLookingForFragment fragment = new WhatJobLookingForFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_what_job_looking_for, container, false);
        initUi();
        return mRootView;
    }

    private void initUi() {


        mNextButton = (Button) mRootView.findViewById(R.id.nextButton);
        mWhatJobLookingTextView = (TextView) mRootView.findViewById(R.id.whatJobTextView);
        mStepCount = (TextView) mRootView.findViewById(R.id.stepCount);
        mJobEditText = (EditText) mRootView.findViewById(R.id.jobEditText);
        mHorizontalView = (View) mRootView.findViewById(R.id.horizontalView);
        mPreferencesHandler = PreferencesHandler.getInstance(getActivity());
        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cabin-Regular.ttf");
        mNextButton.setTypeface(mTypeface);
        mJobEditText.setTypeface(mTypeface);


        saveDefaultValues();

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_right);

        mWhatJobLookingTextView.startAnimation(anim);
        mNextButton.startAnimation(anim);
        mStepCount.startAnimation(anim);
        mJobEditText.startAnimation(anim);
        mHorizontalView.startAnimation(anim);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mJobEditText.length() > 0)

                {
                    mPreferencesHandler.saveJobName(mJobEditText.getText().toString());
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = WhereJobLookingForFragment.newInstance("", "");
                    if (fragment != null) {
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_left).replace(R.id.container, fragment).commit();
                    }
                } else {
                    Utils.showErrorToast(getActivity(), AppController.MESSAGE_JOB_MISSING);

                }

            }
        });
    }

    private void saveDefaultValues() {


        if (mPreferencesHandler.getSortOption() != null && mPreferencesHandler.getSortOption().length() > 0) {
            mPreferencesHandler.saveSortOption(mPreferencesHandler.getSortOption());
        } else {
            mPreferencesHandler.saveSortOption("date");
        }


        if (mPreferencesHandler.getDistance() != null && mPreferencesHandler.getDistance().length() > 0) {
            mPreferencesHandler.saveDistance(mPreferencesHandler.getDistance());
        } else {
            mPreferencesHandler.saveDistance("15");
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
