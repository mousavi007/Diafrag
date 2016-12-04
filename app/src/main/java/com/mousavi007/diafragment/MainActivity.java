package com.mousavi007.diafragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;

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

    public void createPalette(Uri imageUri) {
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Picasso.with(this).load(imageUri).into(imageView);

        // Do this async on activity
        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    HashMap<String, Integer> swatches = processPalette(palette);
                    Object[] entries = swatches.entrySet().toArray();
                    swatchAdapter = new SwatchAdapter(getApplicationContext(), entries);

                    gridView.setAdapter(swatchAdapter);
                }
            });



        } catch (Exception ex) {
            Log.e("MainActivity", "error in creating palette");
        }
    }

    HashMap<String,Integer> processPalette (Palette p) {
        HashMap<String, Integer> map = new HashMap<>();

        if (p.getVibrantSwatch() != null)
            map.put("Vibrant", p.getVibrantSwatch().getRgb());
        if (p.getDarkVibrantSwatch() != null)
            map.put("DarkVibrant", p.getDarkVibrantSwatch().getRgb());
        if (p.getLightVibrantSwatch() != null)
            map.put("LightVibrant", p.getLightVibrantSwatch().getRgb());

        if (p.getMutedSwatch() != null)
            map.put("Muted", p.getMutedSwatch().getRgb());
        if (p.getDarkMutedSwatch() != null)
            map.put("DarkMuted", p.getDarkMutedSwatch().getRgb());
        if (p.getLightMutedSwatch() != null)
            map.put("LightMuted", p.getLightMutedSwatch().getRgb());

        return map;
    }
}
