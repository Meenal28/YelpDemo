package com.rbc.yelp.services.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rbc.yelp.databinding.ListItemSectionBinding;
import com.rbc.yelp.services.interfaces.OnItemClickListener;
import com.rbc.yelp.services.models.Business;

import java.util.List;
import java.util.TreeMap;

/**
 * Adapter to show Sections(Categories) list with business as sub-list grouped by category
 */
public class SectionListAdapter extends RecyclerView.Adapter<SectionListAdapter.SectionListViewHolder> {

    private final Context context;
    private final OnItemClickListener itemClickListener;
    TreeMap<String, List<Business>> businessList = new TreeMap<>();
    private String[] keysList;

    public SectionListAdapter(Context context, OnItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SectionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemSectionBinding listItemSection =
                ListItemSectionBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SectionListViewHolder(listItemSection);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionListViewHolder holder, int position) {
        holder.bindData(position, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    /**
     * Set Recycler view item and notify adapter
     */
    public void setItems(
            TreeMap<String, List<Business>> businessList
    ) {
        this.businessList = businessList;
        keysList = businessList.keySet().toArray(new String[businessList.isEmpty() ? 0 : businessList.size()]);
        notifyDataSetChanged();
    }


    public class SectionListViewHolder extends RecyclerView.ViewHolder {

        private final ListItemSectionBinding itemSectionBinding;

        public SectionListViewHolder(@NonNull ListItemSectionBinding itemView) {
            super(itemView.getRoot());
            itemSectionBinding = itemView;
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            itemSectionBinding.rvSections.setLayoutManager(layoutManager);
            itemSectionBinding.rvSections.setHasFixedSize(true);
        }

        public void bindData(int position, OnItemClickListener itemClickListener) {
            itemSectionBinding.setSection(keysList[position].toUpperCase());
            itemSectionBinding.setCount(businessList.get(keysList[position]).size());
            itemSectionBinding.executePendingBindings();
            BusinessItemAdapter listAdapter = new BusinessItemAdapter(context, businessList.get(keysList[position]), itemClickListener, keysList[position]);
            itemSectionBinding.rvSections.setAdapter(listAdapter);
        }
    }
}
