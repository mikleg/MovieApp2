package com.mikleg.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mikle on 5/15/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private static final String LOGNAME = MovieAdapter.class.getSimpleName();

    final private MoviesAdapterOnClickHandler mOnClickListener;
    private final Context mContext;
    private static int viewHolderCount;
    private String[] mMoviesData;
    private int mNumberItems;


    public interface MoviesAdapterOnClickHandler {
        void onListItemClick(int clickedItemIndex);
    }


    public MovieAdapter(@NonNull Context context, int numberOfItems, MoviesAdapterOnClickHandler listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
        mContext = context;


    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutIdForListItem = R.layout.movie_el;
        boolean shouldAttachToParentImmediately = false;
        int backgroundColorForViewHolder = 100;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
       // viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);
        viewHolder.viewHolderIndex.setText("ViewHolder index: " + "999");
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);
        viewHolderCount++;
        Log.d(LOGNAME, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
       // String movie = mMoviesData[position];
       // holder.listItemNumberView.setText(movie);



        Log.d(LOGNAME, "#" + position);
        //holder.bind(position);
        holder.bind(56);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public void setMoviesData(String[] moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }


//    /** use it for filter
//     * Returns an integer code related to the type of View we want the ViewHolder to be at a given
//     * position. This method is useful when we want to use different layouts for different items
//     * depending on their position. In Sunshine, we take advantage of this method to provide a
//     * different layout for the "today" layout. The "today" layout is only shown in portrait mode
//     * with the first item in the list.
//     *
//     * @param position index within our RecyclerView and Cursor
//     * @return the view type (today or future day)
//     */
//    @Override
//    public int getItemViewType(int position) {
//        if (mUseTodayLayout && position == 0) {
//            return VIEW_TYPE_TODAY;
//        } else {
//            return VIEW_TYPE_FUTURE_DAY;
//        }
//    }




    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView listItemNumberView;
        TextView viewHolderIndex;

        public MovieViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_name);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }


}


