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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.application.AppController;
import com.modernizeworkers.jobapplication.model.GetJobDetailsBaseModel;
import com.modernizeworkers.jobapplication.model.JobSearchResultModel;
import com.modernizeworkers.jobapplication.util.PreferencesHandler;
import com.modernizeworkers.jobapplication.util.Utils;
import com.modernizeworkers.jobapplication.webservice.APIClient;
import com.modernizeworkers.jobapplication.webservice.APIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private static JobSearchResultModel mJobSearchResultModel;
    private TextView mJobTitleTextView;
    private TextView mJobCompanyTextView;
    private TextView mJobLocationTextView;
    private TextView mJobDescription;
    private TextView mPostedTextView;
    private View mRootView;
    private APIInterface mApiInterface;
    private GetJobDetailsBaseModel mGetJobDetailsBaseModel;
    private Button mApplyNowButton;
    private Typeface mTypeface;
    private TextView mCompanyNameTextView;
    private Button mViewMapButton;
    private ProgressBar mJobDetaislProgressBar;
    private FragmentManager fragmentManager;
   private Fragment mFragment;
    private PreferencesHandler mPreferencesHandler;
    private ImageView mBackArrow;
    private RelativeLayout mParentContanerLayout;
    public JobDetailsFragment() {
        // Required empty public constructor
    }

    public static JobDetailsFragment newInstance(JobSearchResultModel jobSearchResultModel, String param2) {
        JobDetailsFragment fragment = new JobDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        mJobSearchResultModel = jobSearchResultModel;
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
        mRootView = inflater.inflate(R.layout.fragment_job_details, container, false);
        initUi();
        return mRootView;
    }

    private void initUi() {
        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cabin-Regular.ttf");
        mJobTitleTextView = (TextView) mRootView.findViewById(R.id.TextViewJobHeading);
        mJobCompanyTextView = (TextView) mRootView.findViewById(R.id.TextViewJobCompany);
        mJobLocationTextView = (TextView) mRootView.findViewById(R.id.TextViewLocation);
        mJobDescription = (TextView) mRootView.findViewById(R.id.TextViewDescription);
        mPostedTextView = (TextView) mRootView.findViewById(R.id.TextViewPosted);
        mCompanyNameTextView = (TextView) mRootView.findViewById(R.id.companyNameTextView);
        mApplyNowButton = (Button) mRootView.findViewById(R.id.ButtonApplyNow);
        mViewMapButton = (Button) mRootView.findViewById(R.id.buttonViewMap);
        mJobDetaislProgressBar = (ProgressBar) mRootView.findViewById(R.id.jobDetailsProgressBar);
        mBackArrow=(ImageView)mRootView.findViewById(R.id.backArrow);
        mParentContanerLayout=(RelativeLayout)mRootView.findViewById(R.id.mParentContainerLayout);
        mPreferencesHandler=PreferencesHandler.getInstance(getActivity());
        mPreferencesHandler.saveJobLattitude(mJobSearchResultModel.getLatitude().toString());
        mPreferencesHandler.saveJobLongitude(mJobSearchResultModel.getLongitude().toString());
        mApplyNowButton.setTypeface(mTypeface);
        mViewMapButton.setTypeface(mTypeface);
        mParentContanerLayout.setVisibility(View.GONE);

        if (Utils.isNetworkAvailable(getActivity())) {

            fetchJobDetails();
        } else {
            Utils.showErrorToast(getActivity(),AppController.NO_NETWORK_CONNECTION_MESSAGE);
        }

        mApplyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mJobSearchResultModel.getUrl();
                if (url != null && url.length() > 0) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }

            }
        });
//        mViewMapButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                fragmentManager = getFragmentManager();
//                mFragment = LocationMapFragment.newInstance(mGetJobDetailsBaseModel.getResults().get(0).getCompany(), mGetJobDetailsBaseModel.getResults().get(0).getFormattedLocationFull());
//                if (mFragment != null) {
//                    fragmentManager.beginTransaction().replace(R.id.jobcontainer, mFragment).addToBackStack(null).commit();
//                }
//            }
//        });


        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

    }

    private void setValuesToView(GetJobDetailsBaseModel getJobDetailsBaseModel) {
        mJobTitleTextView.setText(getJobDetailsBaseModel.getResults().get(0).getJobtitle());
        mJobCompanyTextView.setText(getJobDetailsBaseModel.getResults().get(0).getCompany());
        mJobLocationTextView.setText(getJobDetailsBaseModel.getResults().get(0).getFormattedLocation());
        mJobDescription.setText(getJobDetailsBaseModel.getResults().get(0).getSnippet());
        mPostedTextView.setText(getJobDetailsBaseModel.getResults().get(0).getFormattedRelativeTime());
        mCompanyNameTextView.setText(getJobDetailsBaseModel.getResults().get(0).getCompany());
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


    private void fetchJobDetails() {

        mApiInterface = APIClient.getJobList().create(APIInterface.class);
        Call call;
        call = mApiInterface.jobSDetails(AppController.PUBLISHER_KEY, mJobSearchResultModel.getJobkey(), "2", "json");


        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                mParentContanerLayout.setVisibility(View.VISIBLE);
                mJobDetaislProgressBar.setVisibility(View.GONE);
                if (response.body() != null) {

                    mGetJobDetailsBaseModel = (GetJobDetailsBaseModel) response.body();
                    if (mGetJobDetailsBaseModel != null) {

                        setValuesToView(mGetJobDetailsBaseModel);

                    }
                } else {


                    if (response.errorBody() != null) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getContext(), jObjError.getString("msg"), Toast.LENGTH_LONG).show();
                            String msg = jObjError.getString("msg");

                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mParentContanerLayout.setVisibility(View.GONE);
                mJobDetaislProgressBar.setVisibility(View.GONE);

                call.cancel();
            }
        });

    }
}
