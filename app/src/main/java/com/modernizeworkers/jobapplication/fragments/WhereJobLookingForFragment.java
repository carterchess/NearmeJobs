package com.modernizeworkers.jobapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.activities.JobContainerActivity;
import com.modernizeworkers.jobapplication.application.AppController;
import com.modernizeworkers.jobapplication.util.PreferencesHandler;
import com.modernizeworkers.jobapplication.util.Utils;


public class WhereJobLookingForFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mRootView;
    private Button mfinishButton;
    private EditText mEditTextJobLocation;
    private PreferencesHandler mPreferencesHandler;
    private Typeface mTypeface;


    public WhereJobLookingForFragment() {
        // Required empty public constructor
    }


    public static WhereJobLookingForFragment newInstance(String param1, String param2) {
        WhereJobLookingForFragment fragment = new WhereJobLookingForFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_where_job_looking_for, container, false);
        initUi();
        return mRootView;
    }

    private void initUi() {

        mfinishButton = (Button) mRootView.findViewById(R.id.finishButton);
        mEditTextJobLocation = (EditText) mRootView.findViewById(R.id.whereJobEditText);
        mPreferencesHandler = PreferencesHandler.getInstance(getActivity());
        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cabin-Regular.ttf");
        mfinishButton.setTypeface(mTypeface);
        mEditTextJobLocation.setTypeface(mTypeface);
        mfinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isNetworkAvailable(getActivity())) {
                    if (mEditTextJobLocation.getText().length() > 0) {

                        mPreferencesHandler.saveJobLocation(mEditTextJobLocation.getText().toString());
                        Intent in = new Intent(getActivity(), JobContainerActivity.class);
                        startActivity(in);
                        getActivity().finish();

                    } else {
                        Utils.showErrorToast(getActivity(),AppController.MESSAGE_JOB_LOCATION_MISSSING);
                    }
                } else {
                    Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);

                }

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
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
