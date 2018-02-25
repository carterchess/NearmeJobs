package com.modernizeworkers.jobapplication.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.modernizeworkers.jobapplication.R;
import com.modernizeworkers.jobapplication.intefaces.JobSearchItemClickListnerInterface;
import com.modernizeworkers.jobapplication.model.JobSearchResultModel;

import java.util.ArrayList;

/**
 * Created by Bino on 8/7/2017.
 */

public class JobSingleItemAdapter extends RecyclerView.Adapter<JobSingleItemAdapter.MyViewHolder> {
    private Context mContext;
    private FragmentManager mFragmentManager;
    private JobSearchItemClickListnerInterface mJobSearchItemClickListnerInterface;
    private ArrayList<JobSearchResultModel> mJobSearchResultModel = new ArrayList<>();
    private int lastPosition=-1;


    public JobSingleItemAdapter(ArrayList<JobSearchResultModel> jobSearchResultModel, Context context, FragmentManager fragmentManager, JobSearchItemClickListnerInterface jobSearchItemClickListnerInterface) {
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mJobSearchItemClickListnerInterface = jobSearchItemClickListnerInterface;
        this.mJobSearchResultModel = jobSearchResultModel;

    }

    @Override
    public JobSingleItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_single_item, parent, false);
        return new JobSingleItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.textViewTitle.setText(mJobSearchResultModel.get(position).getJobtitle());
        holder.textViewCompanyName.setText(mJobSearchResultModel.get(position).getCompany());
        holder.textViewLocation.setText(mJobSearchResultModel.get(position).getFormattedLocation());
        holder.mJobContainerLinearLayOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mJobSearchItemClickListnerInterface.onItemClickCallBack(position);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

    }


    @Override
    public int getItemCount() {
        return mJobSearchResultModel.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public TextView textViewCompanyName;
        public TextView textViewLocation;
        private LinearLayout mJobContainerLinearLayOut;


        public MyViewHolder(View view) {
            super(view);
            textViewTitle = (TextView) view.findViewById(R.id.jobTitleTextView);
            textViewCompanyName = (TextView) view.findViewById(R.id.companyNameTextView);
            textViewLocation = (TextView) view.findViewById(R.id.LocationTextView);
            mJobContainerLinearLayOut = (LinearLayout) view.findViewById(R.id.jobContainerLinearLayout);
        }

    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}
