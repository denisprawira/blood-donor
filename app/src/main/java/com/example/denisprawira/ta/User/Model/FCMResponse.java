package com.example.denisprawira.ta.User.Model;

import java.util.List;

public class FCMResponse {

    public String multicast_;
    public String success ;
    public String failure ;
    public String canonical_ids;
    public List<Result> results ;

    public FCMResponse() {
    }

    public FCMResponse(String multicast_, String success, String failure, String canonical_ids, List<Result> results) {
        this.multicast_ = multicast_;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
        this.results = results;
    }

    public String getMulticast_() {
        return multicast_;
    }

    public void setMulticast_(String multicast_) {
        this.multicast_ = multicast_;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(String canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
