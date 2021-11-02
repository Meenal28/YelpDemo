package com.rbc.yelp.services.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.rbc.yelp.R;
import com.rbc.yelp.databinding.ActivitySearchDetailBinding;
import com.rbc.yelp.services.models.Business;
import com.rbc.yelp.services.utils.Constants;

/**
 * Activity to display details about selected business/shop/restaurants
 */
public class SearchResultDetailActivity extends AppCompatActivity {

    private ActivitySearchDetailBinding activitySearchDetailBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_detail);

        activitySearchDetailBinding.setCategoryName(getIntent().hasExtra(Constants.KEY_CATEGORY_NAME) ? getIntent().getStringExtra(Constants.KEY_CATEGORY_NAME) : "NA");
        Business business = getIntent().hasExtra(Constants.KEY_BUSINESS_DATA) ? (Business) getIntent().getSerializableExtra(Constants.KEY_BUSINESS_DATA) : new Business();
        activitySearchDetailBinding.setBusiness(business);

        if (business.getImageUrl() != null && !business.getImageUrl().isEmpty()) {
            Glide.with(this).load(
                    business.getImageUrl()
            ).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(activitySearchDetailBinding.ivBusiness);
        }
    }
}
