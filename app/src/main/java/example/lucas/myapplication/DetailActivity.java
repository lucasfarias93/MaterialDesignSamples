package example.lucas.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import example.lucas.myapplication.Utils.BottomNavigationDrawerFragment;

public class DetailActivity extends AppCompatActivity {

    private Bitmap mImageBitmap;
    private String mImageName;
    private TextView mImageExtenseDescription;

    //BottomAppBar will work as a supportActionBar with its same methods. Do not use it together with current
    //android support action bar.
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton mFloatingButtonBar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list_item);
        postponeEnterTransition();

        Intent mTransitionIntent = getIntent();
        mImageBitmap = mTransitionIntent.getParcelableExtra("mImage");
        mImageName = mTransitionIntent.getStringExtra("mImageName");

        final ImageView mImageView = findViewById(R.id.imageView);
        TextView mTextView = findViewById(R.id.textTitle);
        mBottomAppBar = findViewById(R.id.detailAppBar);
        mImageExtenseDescription = findViewById(R.id.textImageExtenseDescription);
        mFloatingButtonBar = findViewById(R.id.detailsBottomBarFloatingButton);
        mFloatingButtonBar.setVisibility(View.VISIBLE);
        mImageExtenseDescription.setVisibility(View.VISIBLE);
        mBottomAppBar.setVisibility(View.VISIBLE);
        setSupportActionBar(mBottomAppBar);

        mImageView.setImageBitmap(mImageBitmap);
        mTextView.setText(mImageName);

        //Include values in order to avoid fade effect before and after transition for some elements
        getWindow().setEnterTransition(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.startPostponedEnterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                BottomNavigationDrawerFragment mBottomNavigationDrawerFragment = new BottomNavigationDrawerFragment();
                mBottomNavigationDrawerFragment.show(getSupportFragmentManager(), "BottomNavigationFragment");
            }
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mImageExtenseDescription.setVisibility(View.GONE);
        mBottomAppBar.setVisibility(View.GONE);
        mFloatingButtonBar.setVisibility(View.GONE);
        supportFinishAfterTransition();
    }
}
