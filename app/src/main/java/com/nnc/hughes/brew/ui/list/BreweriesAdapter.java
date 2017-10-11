package com.nnc.hughes.brew.ui.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nnc.hughes.brew.R;
import com.nnc.hughes.brew.data.models.Datum;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Marcus on 10/10/2017.
 */

public class BreweriesAdapter extends RecyclerView.Adapter<BreweriesAdapter.BreweriesViewHolder> {

    CustomItemClickListener listener;
    private Context context;
    private List<Datum> breweriesList;

    public BreweriesAdapter(Context context, List<Datum> brewList, CustomItemClickListener listener) {
        this.context = context;
        setList(brewList);
        this.listener = listener;
    }

    @Override
    public BreweriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brewery_item, parent, false);
        final BreweriesViewHolder viewHolder = new BreweriesViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(breweriesList.get(viewHolder.getAdapterPosition())));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BreweriesViewHolder holder, int position) {
        Datum datumObject = breweriesList.get(position);
        holder.breweryName.setText(datumObject.getName());
        if (datumObject.getImages() != null) {
            Picasso.with(context).load(datumObject.getImages().getIcon()).error(R.mipmap.ic_no_image).
                    placeholder(R.mipmap.ic_no_image)
                    .into(holder.breweryIcon);
        } else {
            holder.breweryIcon.setImageResource(R.mipmap.ic_no_image);
        }

    }

    public void replaceData(List<Datum> data) {
        setList(data);
        notifyDataSetChanged();
    }

    @SuppressLint("RestrictedApi")
    private void setList(List<Datum> data) {
        breweriesList = checkNotNull(data);
    }

    @Override
    public int getItemCount() {
        return breweriesList.size();
    }

    public class BreweriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        public TextView breweryName;
        @BindView(R.id.icon_image)
        public ImageView breweryIcon;


        public BreweriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }
}
