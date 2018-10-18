package example.lucas.myapplication.Utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import example.lucas.myapplication.MapsActivity;
import example.lucas.myapplication.R;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    NavigationView mNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mBottomSheetView = inflater.inflate(R.layout.nav_bottom_sheet, container, true);
        mNavigationView = mBottomSheetView.findViewById(R.id.navigationView);
        return mBottomSheetView;
    }

    //NOTE: a CoordinatorLayout is needed in order to display the NavigationView correctly. Otherwise it will be an empty or blank layout
    //displayed instead
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.location: {
                        Intent mMapActivityIntent = new Intent(getActivity(), MapsActivity.class);
                        startActivity(mMapActivityIntent);
                        BottomNavigationDrawerFragment.this.dismiss();
                    }
                    break;
                    case R.id.info: {
                        Intent mShowPictureIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                        startActivity(mShowPictureIntent);
                        BottomNavigationDrawerFragment.this.dismiss();
                        //TODO: implement validation in case of no application to open the url.
                    }
                    break;
                }
                return true;
            }
        });

    }
}
