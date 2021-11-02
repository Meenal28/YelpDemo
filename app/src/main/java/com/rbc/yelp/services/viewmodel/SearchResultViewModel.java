package com.rbc.yelp.services.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rbc.yelp.services.models.SearchResult;
import com.rbc.yelp.services.repository.SearchResultRepository;

public class SearchResultViewModel extends ViewModel {

    private SearchResultRepository searchResultRepository = SearchResultRepository.getInstance();

    private MutableLiveData<SearchResult> searchResultLiveData = new MutableLiveData<>();

    /**
     * Call to fetch result as per user input of Item and Location
     * @param searchItem
     * @param location
     * @return
     */

    public LiveData<SearchResult> getSearchedItemList(String searchItem, String location) {
        return searchResultRepository.getSearchData(searchResultLiveData, searchItem, location);
    }
}
