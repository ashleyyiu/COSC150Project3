package com.example.chiang.toystore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView t = (TextView) findViewById(R.id.textBox1);
        String tToPrint = "";
        ArrayList<String> toyNameList = new ArrayList<String>();
        ArrayList<Integer> toyPriceList = new ArrayList<Integer>();

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
                //BufferedImage bi = toyList.getToy(i).getImage();
                //File outputfile = new File(i+".png");
                //ImageIO.write(bi, "png", outputfile);

                toyNameList.add(toyList.getToy(i).getToyName());
                toyPriceList.add(toyList.getToy(i).getPrice());
                tToPrint += toyNameList.get(i) + " costs $"+ toyPriceList.get(i) + "\n";

            }
            t.setText(tToPrint);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
