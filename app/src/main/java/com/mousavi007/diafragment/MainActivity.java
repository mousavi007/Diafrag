package com.mousavi007.diafragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fab)FloatingActionButton fab;
    @BindView(R.id.grid_view) GridView gridView;
    @BindView(R.id.tool_bar) Toolbar toolbar;
    SwatchAdapter swatchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(getString(R.string.app_name));
    }
    @OnClick(R.id.fab)
    public void click(View view){
        Snackbar.make(findViewById(R.id.fragment), "Clicked FAB.", Snackbar.LENGTH_LONG)
                //.setAction("Action", this)
                .show();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Pickerfragment pickerFragment = new Pickerfragment();
        pickerFragment.show(getFragmentManager(), "dialog");
        ft.commit();
    }
}
