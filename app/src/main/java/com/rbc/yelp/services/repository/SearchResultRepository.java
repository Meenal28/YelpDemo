package com.rbc.yelp.services.repository;

import static java.util.Collections.emptyList;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rbc.yelp.services.network.YelpApi;
import com.rbc.yelp.services.network.YelpRetrofit;
import com.rbc.yelp.services.models.SearchResult;
import com.rbc.yelp.services.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Search Result repository to fetch data as per User Searched Item and Location
 */
public class SearchResultRepository {

    private static SearchResultRepository searchResultRepository = null;
    private YelpApi yelpApi;

    private SearchResultRepository() {
        yelpApi =
                new YelpRetrofit().getRetrofitInstance().create(YelpApi.class);
    }

    public static SearchResultRepository getInstance() {
        if (searchResultRepository == null) {
            searchResultRepository = new SearchResultRepository();
        }

        return searchResultRepository;
    }

    /**
     * Method to fetch data based on user input
     */
    public LiveData<SearchResult> getSearchData(MutableLiveData<SearchResult> searchResults, String searchTerm, String location) {
        searchResults.postValue(new SearchResult(emptyList(), Constants.Status.LOADING));
        yelpApi.search(searchTerm, location).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                Log.d("TAG1", "Response"+response);
                if (response.body() != null && response.isSuccessful()) {
                    Log.d("TAG1", "Response"+response.body());
                    SearchResult searchResult = response.body();
                    searchResult.setBusinessGroupBy(searchResult.getBusinesses());
                    searchResult.setStatus(
                            searchResult.getBusinesses() != null && !searchResult.getBusinesses().isEmpty() ? Constants.Status.SUCCESS : Constants.Status.EMPTY);
                    searchResults.postValue(searchResult);
                } else {
                    searchResults.postValue(new SearchResult(emptyList(), Constants.Status.EMPTY));
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d("TAG1", "error : " + t.getMessage());
                searchResults.postValue(new SearchResult(emptyList(), Constants.Status.ERROR));
            }
        });
        return searchResults;
    }
}
