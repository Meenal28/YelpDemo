package com.rbc.yelp.services.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rbc.yelp.R;
import com.rbc.yelp.databinding.ActivitySearchResultBinding;
import com.rbc.yelp.services.adapters.SectionListAdapter;
import com.rbc.yelp.services.interfaces.OnItemClickListener;
import com.rbc.yelp.services.models.Business;
import com.rbc.yelp.services.utils.Constants;
import com.rbc.yelp.services.viewmodel.SearchResultViewModel;

/**
 * Search Result activity to display searched result as per user input of item and location
 */
public class SearchResultActivity extends AppCompatActivity implements OnItemClickListener {

    private SearchResultViewModel searchResultViewModel;
    private ActivitySearchResultBinding viewBinding;
    private SectionListAdapter sectionListAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_result);
        viewBinding.setLifecycleOwner(this);
        initViews();
    }

    /**
     * To initialise views
     */
    private void initViews() {
        searchResultViewModel = ViewModelProviders.of(this).get(SearchResultViewModel.class);

        layoutManager = new LinearLayoutManager(this);
        viewBinding.rvBusiness.setLayoutManager(layoutManager);
        sectionListAdapter = new SectionListAdapter(this, this);
        viewBinding.rvBusiness.setAdapter(sectionListAdapter);
        viewBinding.btSearch.setOnClickListener(view -> {
            // Check if location is not empty, as its a mandatory field
            if (viewBinding.etLocation.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.error_message_location), Toast.LENGTH_SHORT).show();
            } else {
                //Hide keyboard to show searched result
                hideKeyboard(view);
                searchResultViewModel.getSearchedItemList(viewBinding.etSearchItem.getText().toString().trim(), viewBinding.etLocation.getText().toString().trim()).observe(this, searchResult -> {
                    viewBinding.setStatus(searchResult.getStatus());
                    if (searchResult.getStatus().equals(Constants.Status.ERROR)) {
                        viewBinding.layoutEmpty.tvMessage.setText(
                                getString(R.string.text_malformed_data));
                        viewBinding.layoutEmpty.tvTitle.setText(getString(R.string.dialogTitle));
                    } else if (searchResult.getStatus().equals(Constants.Status.SUCCESS)) {
                        sectionListAdapter.setItems(searchResult.getBusinessGroupBy());
                    }
                });
            }

        });
    }

    @Override
    public void onItemClick(Business business, String categoryName) {
        Intent intent = new Intent(this, SearchResultDetailActivity.class);
        intent.putExtra(Constants.KEY_BUSINESS_DATA, business);
        intent.putExtra(Constants.KEY_CATEGORY_NAME, categoryName);
        startActivity(intent);
    }

    /**
     * Hide keyboard on search button tap
     * @param v
     */
    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
