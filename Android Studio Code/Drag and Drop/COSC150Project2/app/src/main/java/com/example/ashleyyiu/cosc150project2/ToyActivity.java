package com.example.ashleyyiu.cosc150project2;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ToyActivity extends AppCompatActivity {
    ArrayList<String> toyNameList;
    ArrayList<Integer> toyPriceList;
    ToyList toylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("print", "In ToyActivity: oncreate");
        setContentView(R.layout.activity_toy);

        ToyList newToyList;

//        Read toy info
        readToyInfo();

//        Log.d("print", "Toys in list: " + toylist.getNumOfToys());
////        Toy t1=(Toy) toylist.get(0);
//        Log.d("print", t1.getToyName() +" costs $" +t1.getPrice());
//
//
        ImageView photo1View = (ImageView) findViewById(R.id.photo1View);
//        ImageView photo2View = (ImageView) findViewById(R.id.photo2View);
//        photo2View.setImageBitmap(t1.getImage());
//        ImageView photo3View = (ImageView) findViewById(R.id.photo3View);
//
        photo1View.setOnLongClickListener(longListen);
//        photo2View.setOnLongClickListener(longListen);
//        photo3View.setOnLongClickListener(longListen);
        findViewById(R.id.shoppingCartView).setOnDragListener(DropListener);
    }

    View.OnLongClickListener longListen = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {
            ImageView pic = (ImageView) v;
            Log.d("print","Image clicked - " +pic.getId() );
            Toast.makeText(ToyActivity.this, "Image clicked - " +pic.getId() , Toast.LENGTH_SHORT).show();
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new MyShadowBuilder(v);

            v.startDrag(data, myShadowBuilder, v, 0);
            return true;
        }
    };

    private class MyShadowBuilder extends View.DragShadowBuilder
    {
        private ColorDrawable greyShadow;

        public MyShadowBuilder(View view) {
            super(view);
            greyShadow = new ColorDrawable(Color.LTGRAY);

        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            int height, width;
            height = (int) getView().getHeight();
            width = (int) getView().getWidth();

            greyShadow.setBounds(0, 0, width, height);
            shadowSize.set(width, height);
            shadowTouchPoint.set(width, height);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            greyShadow.draw(canvas);
        }
    }

    View.OnDragListener DropListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("Drag event", "Entered shopping cart");
                    Toast.makeText(getApplicationContext(), "Entering", Toast.LENGTH_LONG).show();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("Drag event", "Exited");
                    Toast.makeText(getApplicationContext(), "Exiting", Toast.LENGTH_LONG).show();
                    break;
                case DragEvent.ACTION_DROP:/
                    Log.d("Drag event", "Dropped");
                    Toast.makeText(getApplicationContext(), "You want to purchase a toy.", Toast.LENGTH_LONG).show();
                    updateCart(v);
                    break;
            }
            return true;
        }
    };

    private void updateCart(View v) {
        // v is an image with a tag
        String tag = v.getTag().toString();
        Log.d("print", "tag is "+tag);
        tag  = tag.replace("image", "");
        Log.d("print", "tag is now "+tag);
        int toyNum = Integer.parseInt(tag);
        Log.d("print", "Toy number is "+toyNum);

        Log.d("print", toyNameList.get(toyNum)+ " costs " +toyPriceList.get(toyNum));
        Toast.makeText(getApplicationContext(), toyNameList.get(toyNum)+ " costs " +toyPriceList.get(toyNum), Toast.LENGTH_LONG).show();

    }


    private void readToyInfo() {
//        TextView t = (TextView) findViewById(R.id.allToys);
        String tToPrint = "";

        toyNameList = new ArrayList<String>();
        toyPriceList = new ArrayList<Integer>();

        InputStream is = null;

        try {
            is = getAssets().open("toy.data");
            int size = is.available();
            byte[] buffer = new byte[size]; //declare the size of the byte array with size of the file
            is.read(buffer); //read file
            is.close(); //close file
            ToyList toyList = new ToyList(buffer, size);
            System.out.println("There are " + toyList.getNumOfToys() + " toys.");

            for (int i=0; i<toyList.getNumOfToys(); i++){

                String viewToFind = "imageView"+String.valueOf(i + 1);
                Log.d("print", "viewtofind: " + viewToFind);



                /**************NEW CODE****************************
                 * Goes inside For loop to load everything from file/URL
                 */
                Bitmap bmp = toyList.getToy(i).getImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapData = stream.toByteArray();

                // To convert byte array to Bitmap
                Bitmap bmpCopy = BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
                ImageView iv = (ImageView) findViewById(R.id.photo1View);
                iv.setImageBitmap(bmpCopy);

                //END NEW CODE*******************************************

                toyNameList.add(toyList.getToy(i).getToyName());
                toyPriceList.add(toyList.getToy(i).getPrice());
//                tToPrint += toyNameList.get(i) + " costs $"+ toyPriceList.get(i) + "\n";

            }
//            t.setText(tToPrint);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void readToyInfo() {
//        try {
//            Log.d("print", "About to read data");
//
//            URL url = new URL("http://people.cs.georgetown.edu/~wzhou/toy.data");
//            Log.d("print", "URL:: "+url);
//            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//
////            Read data into string first
//            String inputLine;
//            String allData="";
//            while ((inputLine = in.readLine()) != null)
//                allData.concat(inputLine);
//                Log.d("print", "Reading file");
//            in.close();
//
////            Convert string to byte array
//            Log.d("print", "Converting to byte array");
//
//            byte[] temp = allData.getBytes();
////            int length = (int) file.length();
////            byte[] temp = new byte[length];
////            file.readFully(temp);
////            file.close();
//            Log.d("print", "Byte array "+temp);
//
//            toylist = new ToyList(temp, temp.length);
//
//        } catch (MalformedURLException e1) {
//            e1.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }

}
