package com.example.xyzreader.ui;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.ItemsContract;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity {

    private Cursor mCursor;
    private long mStartId;

    private long mSelectedItemId;
    private int mSelectedItemUpButtonFloor = Integer.MAX_VALUE;
    private int mTopInset;

    private CursorAdapter mPagerAdapter;
    private View mUpButtonContainer;
    private View mUpButton;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        }

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getData() != null) {
                mStartId = ItemsContract.Items.getItemId(getIntent().getData());
                mSelectedItemId = mStartId;

            }
        }
        else
        {

            mSelectedItemId = Long.valueOf(savedInstanceState.getString("SAVED_ID", null));
        }


        setContentView(R.layout.activity_article_detail);
        postponeEnterTransition();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, ArticleDetailFragment.newInstance(mSelectedItemId)).commit();





    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("SAVED_ID", String.valueOf(mSelectedItemId));
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onPause() {

        super.onPause();
        onSaveInstanceState(new Bundle());
    }
}
