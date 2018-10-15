package example.lucas.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.lucas.myapplication.DetailActivity;
import example.lucas.myapplication.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Bitmap> mImageList;
    private ArrayList<String> mImageDescriptions;
    Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<Bitmap> mImageBitmaps, ArrayList<String> mImageNames) {
        this.mContext = mContext;
        this.mImageList = mImageBitmaps;
        this.mImageDescriptions = mImageNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        viewHolder.mImage.setImageBitmap(mImageList.get(position));
        viewHolder.mImageName.setText(mImageDescriptions.get(position));

        viewHolder.mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap mImageBitmap = ((BitmapDrawable)viewHolder.mImage.getDrawable()).getBitmap();

                Intent detailActivityNavigation = new Intent(mContext, DetailActivity.class);
                detailActivityNavigation.putExtra("mImage", mImageBitmap);
                detailActivityNavigation.putExtra("mImageName", viewHolder.mImageName.getText());

                Pair<View, String> p1 = Pair.create((View)viewHolder.mImage, "imageTransition");
                Pair<View, String> p2 = Pair.create((View)viewHolder.mImageName, "textTransition");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity)mContext,
                                p1,
                                p2);
                mContext.startActivity(detailActivityNavigation, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mImageName;
        ImageView mImage;
        CardView mCardViewLayout;
        FloatingActionButton mFloatingActionButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageName = itemView.findViewById(R.id.imageDescription);
            mImage = itemView.findViewById(R.id.image);
            mCardViewLayout = itemView.findViewById(R.id.recyclerItemLayout);
            mFloatingActionButton = itemView.findViewById(R.id.bottomBarFloatingButton);
        }
    }
}
