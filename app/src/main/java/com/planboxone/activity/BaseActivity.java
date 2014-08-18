package com.planboxone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.planboxone.R;


/**
 * **************************************************************
 * *********    User : SuLinger(462679107@qq.com) .      *********
 * *********    Date : 2014-08-12 .                      *********
 * *********    Time:  11:03 .                。。       *********
 * *********    Project name : PBOX.                     *********
 * *********    Copyright @ 2014, SuLinger, All Rights Reserved  *
 * **************************************************************
 */


public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            overridePendingTransition(R.anim.zoin, R.anim.slide_out_right);
            finish();;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        overridePendingTransition(R.anim.zoin, R.anim.slide_out_right);
        finish();
        return super.onKeyDown(keyCode, event);

    }


}
