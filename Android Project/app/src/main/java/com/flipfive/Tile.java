package com.flipfive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tile{
    private Context context;
    private MainActivity activity;
    private int imageId;
    private ImageSwitcher imageSwitcher;
    private List<Tile> neighbours;
    public Tile(final MainActivity activity, int imageSwitcherId, int imageId){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.neighbours = new ArrayList<>();
        this.imageSwitcher = activity.findViewById(imageSwitcherId);

        this.imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setLayoutParams(new Layout);
//                imageView.setAdjustViewBounds(true);
                return imageView;
            }
        });

        this.imageSwitcher.setImageDrawable(getScaledBitmap(imageId));
        this.imageId = imageId;


        this.imageSwitcher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                flipTileAndNeighbours();
                activity.userMoved();
            }
        });


    }
    public void addNeighbour(Tile neighbour){
        this.neighbours.add(neighbour);
    }

    public void addNeighbours(Tile[] neighbours){
        this.neighbours.addAll(Arrays.asList(neighbours));
    }
    public void flip(){
        if(this.imageId == R.drawable.white) {
            this.imageSwitcher.setImageDrawable(getScaledBitmap(R.drawable.black));
            this.imageId = R.drawable.black;
        }
        else if(this.imageId == R.drawable.black){
            this.imageSwitcher.setImageDrawable(getScaledBitmap(R.drawable.white));
            this.imageId = R.drawable.white;
        }
    }

    public void flipTileAndNeighbours(){
        this.flip();
        for(Tile neighbour : this.neighbours){
            neighbour.flip();
        }
    }

    private Bitmap addWhiteBorder(Bitmap bmp, int borderSize, int resource) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        if(resource == R.drawable.black)
            canvas.drawColor(Color.WHITE);
        else canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    private BitmapDrawable getScaledBitmap(int resource) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resource);

        int newWidth = screenWidth / 3;
        int newHeight = (int) ((screenHeight * 0.9) / 3);

        return new BitmapDrawable(addWhiteBorder(Bitmap.createScaledBitmap(bitmap, newWidth, screenHeight, true), 2, resource));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
        this.imageSwitcher.setImageDrawable(getScaledBitmap(imageId));
    }

    public ImageSwitcher getImageSwitcher() {
        return imageSwitcher;
    }

    public void setImageSwitcher(ImageSwitcher imageSwitcher) {
        this.imageSwitcher = imageSwitcher;
    }

    public List<Tile> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Tile> neighbours) {
        this.neighbours = neighbours;
    }
}
