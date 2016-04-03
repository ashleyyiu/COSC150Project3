package com.example.ashleyyiu.cosc150project2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    SearchView sv;
    ListView lv;
    ToyList toylist;
    String[] toyNames = {"Lego Technic Crawler Crane costs $150","Lego Technic Volvo L350F costs $250","Lego Star Wars Millennium Falcon costs $150"};
    ArrayAdapter <String> adapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Read toy info
//        readToyInfo();

//        Set up the actionlisteners
        lv = (ListView) findViewById(R.id.toyListView);
        sv =  (SearchView) findViewById(R.id.toySearchView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toyNames);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapter.getFilter().filter(text);
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Object o = lv.getItemAtPosition(position);
                  String toy = o.toString();
                  Toast.makeText(getApplicationContext(), "You have chosen the item "+toy, Toast.LENGTH_LONG).show();

                  Intent intent = new Intent(MainActivity.this, ToyActivity.class);
                  startActivity(intent);
              }
              });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewToysOnClick(View view) {
        Log.d("print", "onclick");
        Intent intent = new Intent(MainActivity.this, ToyActivity.class);
        startActivity(intent);
    }
}
