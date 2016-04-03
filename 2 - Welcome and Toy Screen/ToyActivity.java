package com.example.ashleyyiu.cosc150project2;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
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

public class ToyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("print", "In oncreate");
        setContentView(R.layout.activity_toy);

        ImageView photo1View = (ImageView) findViewById(R.id.photo1View);
        ImageView photo2View = (ImageView) findViewById(R.id.photo2View);
        ImageView photo3View = (ImageView) findViewById(R.id.photo3View);

        photo1View.setOnLongClickListener(longListen);
        photo2View.setOnLongClickListener(longListen);
        photo3View.setOnLongClickListener(longListen);
        findViewById(R.id.shoppingCartView).setOnDragListener(DropListener);
    }

    View.OnLongClickListener longListen = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {
            ImageView pic = (ImageView) v;
            Toast.makeText(ToyActivity.this, "Image clicked - " +pic.toString() , Toast.LENGTH_SHORT).show();
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
                    Log.d("print", "Entered");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("print", "Exited");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d("print", "Dropped");
                    Toast.makeText(getApplicationContext(), "You want to purchase a toy.", Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }
    };
}
