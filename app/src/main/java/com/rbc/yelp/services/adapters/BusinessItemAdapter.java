package com.rbc.yelp.services.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rbc.yelp.databinding.ListItemBusinessBinding;
import com.rbc.yelp.services.interfaces.OnItemClickListener;
import com.rbc.yelp.services.models.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to show list of Business as per Category.
 */
public class BusinessItemAdapter extends RecyclerView.Adapter<BusinessItemAdapter.BusinessItemViewHolder> {

    private final Context context;
    private final OnItemClickListener itemClickListener;
    private final String categoryName;
    List<Business> businessList = new ArrayList<>();

    public BusinessItemAdapter(Context context, List<Business> businessList, OnItemClickListener itemClickListener, String categoryName) {
        this.context = context;
        this.businessList.addAll(businessList);
        this.itemClickListener = itemClickListener;
        this.categoryName = categoryName;
    }

    @NonNull
    @Override
    public BusinessItemAdapter.BusinessItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBusinessBinding listItemBusinessBinding =
                ListItemBusinessBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BusinessItemViewHolder(listItemBusinessBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessItemAdapter.BusinessItemViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    public class BusinessItemViewHolder extends RecyclerView.ViewHolder {

        private final ListItemBusinessBinding itemBusinessBinding;

        public BusinessItemViewHolder(@NonNull ListItemBusinessBinding itemView) {
            super(itemView.getRoot());
            itemBusinessBinding = itemView;
        }

        public void bindData(int position) {
            itemBusinessBinding.setName(businessList.get(position).getName());
            itemBusinessBinding.executePendingBindings();
            // Open detail page to view info about selected category
            itemView.setOnClickListener(v -> itemClickListener.onItemClick(businessList.get(position), categoryName));
        }
    }
}
