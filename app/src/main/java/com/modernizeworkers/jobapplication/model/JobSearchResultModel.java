
package com.modernizeworkers.jobapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobSearchResultModel {

    @SerializedName("jobtitle")
    @Expose
    private String jobtitle;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("formattedLocation")
    @Expose
    private String formattedLocation;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("onmousedown")
    @Expose
    private String onmousedown;
    @SerializedName("jobkey")
    @Expose
    private String jobkey;
    @SerializedName("sponsored")
    @Expose
    private Boolean sponsored;
    @SerializedName("expired")
    @Expose
    private Boolean expired;
    @SerializedName("indeedApply")
    @Expose
    private Boolean indeedApply;
    @SerializedName("formattedLocationFull")
    @Expose
    private String formattedLocationFull;
    @SerializedName("formattedRelativeTime")
    @Expose
    private String formattedRelativeTime;
    @SerializedName("stations")
    @Expose
    private String stations;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFormattedLocation() {
        return formattedLocation;
    }

    public void setFormattedLocation(String formattedLocation) {
        this.formattedLocation = formattedLocation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOnmousedown() {
        return onmousedown;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public String getJobkey() {
        return jobkey;
    }

    public void setJobkey(String jobkey) {
        this.jobkey = jobkey;
    }

    public Boolean getSponsored() {
        return sponsored;
    }

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getIndeedApply() {
        return indeedApply;
    }

    public void setIndeedApply(Boolean indeedApply) {
        this.indeedApply = indeedApply;
    }

    public String getFormattedLocationFull() {
        return formattedLocationFull;
    }

    public void setFormattedLocationFull(String formattedLocationFull) {
        this.formattedLocationFull = formattedLocationFull;
    }

    public String getFormattedRelativeTime() {
        return formattedRelativeTime;
    }

    public void setFormattedRelativeTime(String formattedRelativeTime) {
        this.formattedRelativeTime = formattedRelativeTime;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
