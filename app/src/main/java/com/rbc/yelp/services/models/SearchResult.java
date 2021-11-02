package com.rbc.yelp.services.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rbc.yelp.services.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class SearchResult implements Serializable {

    @SerializedName("businesses")
    @Expose
    private List<Business> businesses = null;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("region")
    @Expose
    private Region region;
    private transient Constants.Status status;
    private TreeMap<String, List<Business>> businessGroupBy = new TreeMap<>();

    public SearchResult(List<Business> businessList, Constants.Status status) {
        this.businesses = businessList;
        this.status = status;
    }

    public SearchResult() {
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Business> businesses) {
        this.businesses = businesses;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Constants.Status getStatus() {
        return status;
    }

    public void setStatus(Constants.Status status) {
        this.status = status;
    }

    public TreeMap<String, List<Business>> getBusinessGroupBy() {
        return businessGroupBy;
    }

    public void setBusinessGroupBy(List<Business> businessList) {
        for (Business business :
                businessList) {
            for (Category category :
                    business.getCategories()) {
                List<Business> businessListAsPerCategory = new ArrayList<>();
                if (this.businessGroupBy.containsKey(category.getTitle())) {
                    businessListAsPerCategory = this.businessGroupBy.get(category.getTitle());
                }
                if (businessListAsPerCategory != null) {
                    businessListAsPerCategory.add(business);
                }

                this.businessGroupBy.put(category.getAlias(), businessListAsPerCategory);
            }
        }
    }
}
