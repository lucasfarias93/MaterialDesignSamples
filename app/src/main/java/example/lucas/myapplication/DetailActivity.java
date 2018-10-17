package example.lucas.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.NotificationTarget;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import example.lucas.myapplication.Utils.BottomNavigationDrawerFragment;
import example.lucas.myapplication.Utils.CloseButtonReceiver;

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

        if( savedInstanceState != null) {
            byte[] byteArray = savedInstanceState.getByteArray("mImage");
            mImageBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mImageName = savedInstanceState.getString("mImageName");
        } else {
            Intent mTransitionIntent = getIntent();
            mImageBitmap = mTransitionIntent.getParcelableExtra("mImage");
            mImageName = mTransitionIntent.getStringExtra("mImageName");
        }

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

        mFloatingButtonBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleNotificationEvent();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("mImageName", mImageName);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] mBitmapArray = stream.toByteArray();

        outState.putByteArray("mImage", mBitmapArray);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.startPostponedEnterTransition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public void handleNotificationEvent() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence mChannelName = "Maps notification channel";
            String mChannelDescription = "Channel to handle notifications that will deliver the user to its google maps location";
            int mChannelImportance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel("1", mChannelName, mChannelImportance );
            mChannel.setDescription(mChannelDescription);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);

        }
        createNotification();
    }

    public void createNotification() {
        Intent mMapsIntent = new Intent(this, MapsActivity.class);

        TaskStackBuilder mStackBuilder = TaskStackBuilder.create(this);
        mStackBuilder.addNextIntentWithParentStack(mMapsIntent);

        PendingIntent mPendingIntent = mStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mCloseActionIntent = new Intent(this, CloseButtonReceiver.class);
        mCloseActionIntent.putExtra("notificationId", 0);

        PendingIntent mClosePendingIntent = PendingIntent.getBroadcast(this, 0, mCloseActionIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_android_debug_bridge_black_48dp)
                .setColor(ContextCompat.getColor(this, R.color.colorDark))
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_android_debug_bridge_black_48dp))
                .setContentTitle("MyApplication notification")
                .setContentText("Your place's location")
                .setSubText("Please tap over the notification to be redirected to the location of the place you selected")
                .addAction(R.mipmap.ic_close_black_48dp, "Close", mClosePendingIntent);

        mBuilder.setContentIntent(mPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
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
