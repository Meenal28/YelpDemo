package com.rbc.yelp.services.interfaces;

import com.rbc.yelp.services.models.Business;

/**
 * Item click interface
 */
public interface OnItemClickListener {

    /**
     * Method defined to add click event for items in recycler view.
     * @param business - object of item selected
     * @param categoryName - Section Name
     */
    void onItemClick(Business business, String categoryName);
}
