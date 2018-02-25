
package com.modernizeworkers.jobapplication.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobSearchBaseModel {

    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("paginationPayload")
    @Expose
    private String paginationPayload;
    @SerializedName("dupefilter")
    @Expose
    private Boolean dupefilter;
    @SerializedName("highlight")
    @Expose
    private Boolean highlight;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("end")
    @Expose
    private Integer end;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("results")
    @Expose
    private List<JobSearchResultModel> results = null;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPaginationPayload() {
        return paginationPayload;
    }

    public void setPaginationPayload(String paginationPayload) {
        this.paginationPayload = paginationPayload;
    }

    public Boolean getDupefilter() {
        return dupefilter;
    }

    public void setDupefilter(Boolean dupefilter) {
        this.dupefilter = dupefilter;
    }

    public Boolean getHighlight() {
        return highlight;
    }

    public void setHighlight(Boolean highlight) {
        this.highlight = highlight;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<JobSearchResultModel> getResults() {
        return results;
    }

    public void setResults(List<JobSearchResultModel> results) {
        this.results = results;
    }

}
