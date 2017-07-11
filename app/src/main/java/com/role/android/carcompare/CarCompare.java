package com.role.android.carcompare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class CarCompare extends AppCompatActivity {

    static InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i)
            {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches())
                {
                    return "";
                }
            }

            return null;
        }
    };

    static InputFilter uppercase = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i)
            {
                if (Pattern.compile("[abcdefghijklmnopqrstuvwxyz]*").matcher(String.valueOf(source.charAt(i))).matches())
                {
                    return String.valueOf(source.charAt(i)).toUpperCase();
                }
            }

            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_compare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText tx = (EditText)findViewById(R.id.editText1);
        EditText tx2 = (EditText)findViewById(R.id.editText2);
        tx.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        tx2.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Kenteken.ttf");

        tx.setFilters(new InputFilter[]{filter, uppercase, new InputFilter.LengthFilter(6)});
        tx2.setFilters(new InputFilter[]{filter, uppercase, new InputFilter.LengthFilter(6)});
        tx.setTypeface(custom_font);
        tx2.setTypeface(custom_font);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String license = prefs.getString("license","00AABB");

        tx.setText(license);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarCompare.this, Information.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_compare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(CarCompare.this, Settings.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
