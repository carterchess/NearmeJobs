package com.modernizeworkers.jobapplication.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;
import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.adapters.JobSingleItemAdapter;
import com.modernizeworkers.jobapplication.application.AppController;
import com.modernizeworkers.jobapplication.customviews.JAEditText;
import com.modernizeworkers.jobapplication.customviews.JATextView;
import com.modernizeworkers.jobapplication.intefaces.JobSearchItemClickListnerInterface;
import com.modernizeworkers.jobapplication.model.JobSearchBaseModel;
import com.modernizeworkers.jobapplication.model.JobSearchResultModel;
import com.modernizeworkers.jobapplication.util.PreferencesHandler;
import com.modernizeworkers.jobapplication.util.Utils;
import com.modernizeworkers.jobapplication.webservice.APIClient;
import com.modernizeworkers.jobapplication.webservice.APIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JobListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobListingFragment extends Fragment implements JobSearchItemClickListnerInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private JobSingleItemAdapter mJobSingleItemAdapter;
    private LinearLayout mJobItemContainerLinearLayout;
    private APIInterface mApiInterface;
    private PreferencesHandler mPreferencesHandler;
    private ArrayList<JobSearchResultModel> mJobSearchResultModel = new ArrayList<>();

    private JobSearchBaseModel mJobSearchBaseModel;
    int startCount = 0;

    private ImageView mImageViewFilter;


    private LinearLayout mLayoutViewAll;
    private LinearLayout mLayoutFullTime;
    private LinearLayout mLayoutpartTime;
    private LinearLayout mLayoutFreelance;
    private LinearLayout mLayoutInternship;
    private Dialog dialog;


    private ImageView closeImageView;
    private Button dateButton;
    private Button relevanceButton;
    private Button applyFilterButton;
    private TextView kmTextView;
    private RangeSliderView rangeSliderView;
    private ProgressBar mProgressBar;

    private Typeface typeFace;
    private boolean isScrollEnabled = true;
    private int mEnd = 0;
    private int mTotalResult = 0;
    private String mJobType = "";
    private TextView mNoJobsAvailableTextView;
    private ImageView mEditImageView;
    private JAEditText mJobTypeEditText;
    private JAEditText mJobLocationEditText;
    private Button mApplyButton;
    private TextView mJobNameTextView;
    private TextView mJobLocationTextView;


    private ImageView mViewAllImageView;
    private ImageView mFullTimeImageView;
    private ImageView mPartTimeImageView;
    private ImageView mFreeLanceImageView;
    private ImageView mInternShipImageView;


    private JATextView mViewAllTextView;
    private JATextView mFullTimeTextView;
    private JATextView mPartTimeTextView;
    private JATextView mFreeLanceTextView;
    private JATextView mInternShipTextView;

    private Window w;


    public JobListingFragment() {
        // Required empty public constructor
    }

    public static JobListingFragment newInstance(String param1, String param2) {
        JobListingFragment fragment = new JobListingFragment();
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
        mRootView = inflater.inflate(R.layout.fragment_job_listing, container, false);
        initUi();
        return mRootView;
    }

    private void initUi() {

        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.jobListRecyclerView);
        mPreferencesHandler = PreferencesHandler.getInstance(getActivity());
        mLayoutViewAll = (LinearLayout) mRootView.findViewById(R.id.layOutViewAll);
        mLayoutFullTime = (LinearLayout) mRootView.findViewById(R.id.layOutFullTime);
        mLayoutpartTime = (LinearLayout) mRootView.findViewById(R.id.layOutpartTime);
        mLayoutFreelance = (LinearLayout) mRootView.findViewById(R.id.layOutFreelance);
        mLayoutInternship = (LinearLayout) mRootView.findViewById(R.id.layOutInternShip);
        mImageViewFilter = (ImageView) mRootView.findViewById(R.id.filterImageView);
        mNoJobsAvailableTextView = (TextView) mRootView.findViewById(R.id.noJobsTextView);
        mJobNameTextView = (TextView) mRootView.findViewById(R.id.jobNameTextView);
        mJobLocationTextView = (TextView) mRootView.findViewById(R.id.jobLocationTextView);

        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.mJobListProgressBar);
        mEditImageView = (ImageView) mRootView.findViewById(R.id.editImageView);


        mViewAllImageView = (ImageView) mRootView.findViewById(R.id.viewAllImageView);
        mFullTimeImageView = (ImageView) mRootView.findViewById(R.id.fullTimeImageView);
        mPartTimeImageView = (ImageView) mRootView.findViewById(R.id.partTimeImageView);
        mFreeLanceImageView = (ImageView) mRootView.findViewById(R.id.freelanceImageView);
        mInternShipImageView = (ImageView) mRootView.findViewById(R.id.internShipImageView);


        mViewAllTextView = (JATextView) mRootView.findViewById(R.id.viewAllTextView);
        mFullTimeTextView = (JATextView) mRootView.findViewById(R.id.fullTimeTextView);
        mPartTimeTextView = (JATextView) mRootView.findViewById(R.id.partTimeTextView);
        mFreeLanceTextView = (JATextView) mRootView.findViewById(R.id.freeLanceTextView);
        mInternShipTextView = (JATextView) mRootView.findViewById(R.id.internShipTextView);
        setValueForViewAll();


        typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/Cabin-Regular.ttf");
        mJobSearchResultModel.clear();

        mLayoutViewAll.setOnClickListener(onClickListener);
        mLayoutFullTime.setOnClickListener(onClickListener);
        mLayoutpartTime.setOnClickListener(onClickListener);
        mLayoutFreelance.setOnClickListener(onClickListener);
        mLayoutInternship.setOnClickListener(onClickListener);
        mImageViewFilter.setOnClickListener(onClickListener);
        mEditImageView.setOnClickListener(onClickListener);
        mJobNameTextView.setOnClickListener(onClickListener);
        mJobLocationTextView.setOnClickListener(onClickListener);


        setDataToAdapter();
        if (Utils.isNetworkAvailable(getActivity())) {
            mProgressBar.setVisibility(View.VISIBLE);
            fetchJoblist(startCount);
        } else {
            Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
            mNoJobsAvailableTextView.setVisibility(View.VISIBLE);
            mNoJobsAvailableTextView.setText(AppController.NO_NETWORK_CONNECTION_MESSAGE);

        }


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 2 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if (mEnd == mTotalResult) {
                        isScrollEnabled = true;
                    } else {

                        if (Utils.isNetworkAvailable(getActivity())) {
                            isScrollEnabled = false;
                            mProgressBar.setVisibility(View.VISIBLE);
                            fetchJoblist(startCount);
                        } else {
                            isScrollEnabled = true;
                            mProgressBar.setVisibility(View.GONE);
                            Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);

                        }

                    }

                }
            }
        });


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layOutViewAll:


                    if (Utils.isNetworkAvailable(getActivity())) {

                        setValueForViewAll();
                        resetValues();
                        mJobType = "";
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }


                    break;
                case R.id.layOutFullTime:

                    if (Utils.isNetworkAvailable(getActivity())) {
                        setValueForFulltime();
                        resetValues();

                        mJobType = "fulltime";
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }

                    break;

                case R.id.layOutpartTime:

                    if (Utils.isNetworkAvailable(getActivity())) {
                        setValuesForPartTime();
                        resetValues();
                        mJobType = "parttime";
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }
                    break;
                case R.id.layOutFreelance:
                    if (Utils.isNetworkAvailable(getActivity())) {
                        setValuesForFreeLance();
                        resetValues();
                        mJobType = "contract";
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }
                    break;
                case R.id.layOutInternShip:


                    if (Utils.isNetworkAvailable(getActivity())) {
                        setValuesForInternShip();
                        resetValues();
                        mJobType = "internship";
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }

                    break;


                case R.id.filterImageView:


                    showDialogFilter();

                    break;


                case R.id.editImageView:


                    showDialogEdit();

                    break;


                case R.id.jobNameTextView:


                    showDialogEdit();

                    break;


                case R.id.jobLocationTextView:


                    showDialogEdit();

                    break;


                default:
                    break;
            }

        }


    };

    private void fetchSmoothly() {


        w = getActivity().getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void setValuesForInternShip() {
        mViewAllImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_icon_home));
        mFullTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fulltime));
        mPartTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.parttime));
        mFreeLanceImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.freelance));
        mInternShipImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.internship_selected));

        mViewAllTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFullTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mPartTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFreeLanceTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mInternShipTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorSelected));

    }

    private void setValuesForFreeLance() {

        mViewAllImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_icon_home));
        mFullTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fulltime));
        mPartTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.parttime));
        mFreeLanceImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.freelance_selected));
        mInternShipImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.internship));

        mViewAllTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFullTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mPartTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFreeLanceTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorSelected));
        mInternShipTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
    }

    private void setValuesForPartTime() {
        mViewAllImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_icon_home));
        mFullTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fulltime));
        mPartTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.parttime_selected));
        mFreeLanceImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.freelance));
        mInternShipImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.internship));

        mViewAllTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFullTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mPartTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorSelected));
        mFreeLanceTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mInternShipTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));

    }

    private void setValueForFulltime() {
        mViewAllImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_icon_home));
        mFullTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fulltime_selected));
        mPartTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.parttime));
        mFreeLanceImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.freelance));
        mInternShipImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.internship));

        mViewAllTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFullTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorSelected));
        mPartTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFreeLanceTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mInternShipTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
    }

    private void setValueForViewAll() {


        mViewAllImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_icon_home_selected));
        mFullTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fulltime));
        mPartTimeImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.parttime));
        mFreeLanceImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.freelance));
        mInternShipImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.internship));

        mViewAllTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorSelected));
        mFullTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mPartTimeTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mFreeLanceTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));
        mInternShipTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.bottomItemColorUnSelected));

    }

    private void resetValues() {

        mJobSearchResultModel.clear();
        isScrollEnabled = true;
        mEnd = 0;
        mTotalResult = 0;
        startCount = 0;
    }


    private void setDataToAdapter() {

        mJobSingleItemAdapter = new JobSingleItemAdapter(mJobSearchResultModel, getActivity(), getFragmentManager(), this);
        RecyclerView.LayoutManager mLayoutManager = new CustomGridLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mJobSingleItemAdapter);
    }


    private void fetchJoblist(int startCountForSearch) {

        fetchSmoothly();
        mApiInterface = APIClient.getJobList().create(APIInterface.class);
        Call call;
        call = mApiInterface.jobSearch(AppController.PUBLISHER_KEY, mPreferencesHandler.getJobName(), mPreferencesHandler.getJobLocation(), mPreferencesHandler.getSortOption(), mPreferencesHandler.getDistance(), "", mJobType, String.valueOf(startCountForSearch), "", "", "", "1", AppController.COUNTRY_CODE, "", "", "", "2", "json");


        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                isScrollEnabled = true;
                mProgressBar.setVisibility(View.GONE);
                mJobNameTextView.setText(mPreferencesHandler.getJobName());
                mJobLocationTextView.setText(mPreferencesHandler.getJobLocation());
                if (response.body() != null) {
                    mJobSearchBaseModel = (JobSearchBaseModel) response.body();
                    if (mJobSearchBaseModel != null) {


                        mEnd = mJobSearchBaseModel.getEnd();
                        mTotalResult = mJobSearchBaseModel.getTotalResults();
                        startCount = mEnd;
                        int jobListSize = mJobSearchBaseModel.getResults().size();
                        for (int i = 0; i < jobListSize; i++) {
                            mJobSearchResultModel.add(mJobSearchBaseModel.getResults().get(i));
                        }


                        if (mEnd == mTotalResult) {

                            Utils.showSuccesToast(getActivity(), AppController.MESSAGE_ALL_JOBS_LISTED);
                        }
                        checkJobIsAvailable();

                        mJobSingleItemAdapter.notifyDataSetChanged();
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
                w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mProgressBar.setVisibility(View.GONE);
                isScrollEnabled = true;
                call.cancel();
            }
        });

    }

    private void checkJobIsAvailable() {

        if (mJobSearchResultModel.size() > 0) {
            mNoJobsAvailableTextView.setVisibility(View.GONE);
        } else {
            mNoJobsAvailableTextView.setVisibility(View.VISIBLE);
        }
    }


    public class CustomGridLayoutManager extends LinearLayoutManager {


        public CustomGridLayoutManager(Context context) {
            super(context);
        }


        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
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

    @Override
    public void onItemClickCallBack(int position) {


        if (Utils.isNetworkAvailable(getActivity())) {
            FragmentManager fragmentManager = getFragmentManager();
            if (mJobSearchResultModel.size() > 0) {
                Fragment fragment = JobDetailsFragment.newInstance(mJobSearchResultModel.get(position), "");
                if (fragment != null) {
                    fragmentManager.beginTransaction().add(R.id.jobcontainer, fragment).addToBackStack(null).commit();
                }
            }
        } else {
            Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void showDialogFilter() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_pop_up);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);


        closeImageView = (ImageView) dialog.findViewById(R.id.closePopupImageView);
        dateButton = (Button) dialog.findViewById(R.id.dateButton);
        relevanceButton = (Button) dialog.findViewById(R.id.relevanceButton);
        applyFilterButton = (Button) dialog.findViewById(R.id.applyFilterButton);
        kmTextView = (TextView) dialog.findViewById(R.id.kmTextView);
        rangeSliderView = (RangeSliderView) dialog.findViewById(R.id.kmLimitSeekbar);
        dateButton.setTypeface(typeFace);
        relevanceButton.setTypeface(typeFace);
        applyFilterButton.setTypeface(typeFace);

        closeImageView.setOnClickListener(dlgClick);
        dateButton.setOnClickListener(dlgClick);
        relevanceButton.setOnClickListener(dlgClick);
        applyFilterButton.setOnClickListener(dlgClick);
        rangeSliderView.setOnSlideListener(listener);

        checkCurrentPositionOfSlider();
        checkCurrentPositionOfSortButton();

        dialog.show();

    }


    private void showDialogEdit() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.edit_pop_up);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);


        closeImageView = (ImageView) dialog.findViewById(R.id.popupCloseView);
        mJobTypeEditText = (JAEditText) dialog.findViewById(R.id.jobTypeEditText);
        mJobLocationEditText = (JAEditText) dialog.findViewById(R.id.jobLocationEditText);
        mApplyButton = (Button) dialog.findViewById(R.id.applyEditButton);

        mJobTypeEditText.setText(mPreferencesHandler.getJobName());
        mJobLocationEditText.setText(mPreferencesHandler.getJobLocation());

        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mJobTypeEditText.length() > 0 && mJobLocationEditText.length() > 0) {

                    dialog.dismiss();
                    if (Utils.isNetworkAvailable(getActivity())) {
                        mPreferencesHandler.saveJobName(mJobTypeEditText.getText().toString());
                        mPreferencesHandler.saveJobLocation(mJobLocationEditText.getText().toString());
                        resetValues();
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }
                } else {

                    Utils.showErrorToast(getActivity(), AppController.MESSAGE_JOB_TYPE_LOCATION_MISSING);
                }

            }
        });

        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void checkCurrentPositionOfSortButton() {

        if (mPreferencesHandler.getSortOption().equals("date")) {
            dateEnabled();
        } else {
            relevenceEnabled();
        }

    }

    private void checkCurrentPositionOfSlider() {

        String kmValue = mPreferencesHandler.getDistance();

        switch (kmValue) {


            case "1":
                rangeSliderView.setInitialIndex(0);
                kmTextView.setText("Jobs within 1 Km");
                break;

            case "15":
                rangeSliderView.setInitialIndex(1);
                kmTextView.setText("Jobs within 15 Km");
                break;

            case "30":
                rangeSliderView.setInitialIndex(2);
                kmTextView.setText("Jobs within 30 Km");
                break;
            case "45":
                rangeSliderView.setInitialIndex(3);
                kmTextView.setText("Jobs within 45 Km");
                break;

            case "60":
                rangeSliderView.setInitialIndex(4);
                kmTextView.setText("Jobs within 60 Km");
                break;
        }
    }


    final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
        @Override
        public void onSlide(int index) {


            switch (index) {
                case 0:

                    mPreferencesHandler.saveDistance("1");
                    kmTextView.setText("Jobs within 1 Km");
                    break;
                case 1:
                    mPreferencesHandler.saveDistance("15");
                    kmTextView.setText("Jobs within 15 Km");
                    break;
                case 2:
                    mPreferencesHandler.saveDistance("30");
                    kmTextView.setText("Jobs within 30 Km");
                    break;
                case 3:
                    mPreferencesHandler.saveDistance("45");
                    kmTextView.setText("Jobs within 45 Km");
                    break;

                case 4:
                    mPreferencesHandler.saveDistance("60");
                    kmTextView.setText("Jobs within 60 Km");
                    break;
            }
        }
    };

    private View.OnClickListener dlgClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closePopupImageView:
                    dialog.dismiss();
                    break;
                case R.id.dateButton:

                    dateEnabled();
                    break;

                case R.id.relevanceButton:

                    relevenceEnabled();

                    break;
                case R.id.applyFilterButton:


                    dialog.dismiss();
                    if (Utils.isNetworkAvailable(getActivity())) {
                        resetValues();
                        mProgressBar.setVisibility(View.VISIBLE);
                        fetchJoblist(startCount);
                    } else {
                        Utils.showErrorToast(getActivity(), AppController.NO_NETWORK_CONNECTION_MESSAGE);
                    }

                    break;


                default:
                    break;
            }

        }


    };

    private void relevenceEnabled() {


        relevanceButton.setBackground(getResources().getDrawable(R.drawable.sorting_selected_button));
        relevanceButton.setTextColor(getResources().getColor(R.color.textColor));
        dateButton.setTextColor(getResources().getColor(R.color.textColorButton));
        dateButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        mPreferencesHandler.saveSortOption("relevance");
    }

    private void dateEnabled() {

        dateButton.setBackground(getResources().getDrawable(R.drawable.sorting_selected_button));
        dateButton.setTextColor(getResources().getColor(R.color.textColor));
        relevanceButton.setTextColor(getResources().getColor(R.color.textColorButton));
        relevanceButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        mPreferencesHandler.saveSortOption("date");
    }


}
