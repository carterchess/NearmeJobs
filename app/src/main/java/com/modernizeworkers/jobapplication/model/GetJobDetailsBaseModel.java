package com.modernizeworkers.jobapplication.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJobDetailsBaseModel {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("results")
    @Expose
    private List<GetJobDetailsResultModel> results = null;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<GetJobDetailsResultModel> getResults() {
        return results;
    }

    public void setResults(List<GetJobDetailsResultModel> results) {
        this.results = results;
    }

}