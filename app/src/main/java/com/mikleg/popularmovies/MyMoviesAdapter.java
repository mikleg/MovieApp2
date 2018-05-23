package com.mikleg.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MyMoviesAdapter extends RecyclerView.Adapter<MyMoviesAdapter.ViewHolder> {

    private String[] mData ;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
  //  private String[] mMoviesData;

    // data is passed into the constructor
    MyMoviesAdapter(Context context, String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;

        //this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = mData[position];
        holder.myTextView.setText(data);
        //debug
        System.out.println(mData[position]);
        Picasso.with(this.mContext).load(mData[position]).into(holder.myImageView);


    }

    // total number of cells
    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.length;
    }

    public void setMoviesData(String[] moviesData) {
        mData = moviesData;
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.info_text);
            myImageView = (ImageView) itemView.findViewById(R.id.image_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
