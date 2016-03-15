package com.cognizant.quicknote.screen;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.cognizant.quicknote.R;


public class BaseActivity extends AppCompatActivity {

    protected ActionBar supportActionBar;
    private FloatingActionButton fab;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);

        ViewStub mainView = (ViewStub) findViewById(R.id.main_content);
        assert mainView != null;
        mainView.setLayoutResource(layoutResID);
        mainView.inflate();

        initUI();
    }

    public void setContentFragment(Fragment contentFragment) {
        super.setContentView(R.layout.activity_base);

        ViewStub mainView = (ViewStub) findViewById(R.id.main_content);
        assert mainView != null;
        mainView.setLayoutResource(R.layout.fragment_container);
        mainView.inflate();
        getSupportFragmentManager().beginTransaction().add(R.id.main_layout, contentFragment, contentFragment.getClass().getSimpleName()).commit();

        initUI();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        supportActionBar = getSupportActionBar();

        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    protected void setPrimaryActionClickListener(View.OnClickListener fabClickListener) {
        if(null != fab) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(fabClickListener);
        }
    }

    protected void hideActionBar() {
        if(null != supportActionBar) supportActionBar.hide();
    }

    protected void setBackEnabled(boolean backEnabled) {
        if(null != supportActionBar) supportActionBar.setDisplayHomeAsUpEnabled(backEnabled);
    }

    protected void setScreenTitle(@NonNull CharSequence title) {
        if(null != supportActionBar) supportActionBar.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
