package com.flipfive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Tile tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9;
    List<Tile> tiles;
    private int moveCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.hideStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tile1 = new Tile(this, R.id.tile1, R.drawable.white);
        tile2 = new Tile(this, R.id.tile2, R.drawable.black);
        tile3 = new Tile(this, R.id.tile3, R.drawable.white);
        tile4 = new Tile(this, R.id.tile4, R.drawable.black);
        tile5 = new Tile(this, R.id.tile5, R.drawable.white);
        tile6 = new Tile(this, R.id.tile6, R.drawable.black);
        tile7 = new Tile(this, R.id.tile7, R.drawable.white);
        tile8 = new Tile(this, R.id.tile8, R.drawable.black);
        tile9 = new Tile(this, R.id.tile9, R.drawable.white);

        tile1.addNeighbours(new Tile[]{tile2, tile4});
        tile2.addNeighbours(new Tile[]{tile1, tile3, tile5});
        tile3.addNeighbours(new Tile[]{tile2, tile6});
        tile4.addNeighbours(new Tile[]{tile1, tile5, tile7});
        tile5.addNeighbours(new Tile[]{tile2, tile4, tile6, tile8});
        tile6.addNeighbours(new Tile[]{tile5, tile3, tile9});
        tile7.addNeighbours(new Tile[]{tile4, tile8});
        tile8.addNeighbours(new Tile[]{tile7, tile5, tile9});
        tile9.addNeighbours(new Tile[]{tile8, tile6});

        tiles = new ArrayList<>();
        tiles.addAll(Arrays.asList(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9));

        resetBoard();
    }

    public void checkBoard(){
        boolean finished = true;
        for(Tile t : tiles){
            if(t.getImageId() == R.drawable.black){
                finished = false;
                break;
            }
        }

        if(finished){
            userWon();
//            resetBoard();
        }

    }

    public void userWon(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        builder.setTitle("Congratulations!");
        builder.setMessage("You completed the board in " + this.moveCount + " moves!");

        // add the buttons
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetBoard();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void userMoved(){
        this.moveCount++;
        updateMoveText();
        checkBoard();
    }

    @SuppressLint("SetTextI18n")
    public void updateMoveText(){
        TextView moveText = findViewById(R.id.moves_text);
        moveText.setText("Moves: " + this.moveCount);
    }
    public void resetBoard(){
        Random r = new Random();
        int whiteTileCount = r.nextInt(4) + 1;
        for(Tile t : tiles){
            t.setImageId(R.drawable.black);
        }
        for(int i = 0; i < whiteTileCount; i++){
            int index = r.nextInt(9);
            while(tiles.get(index).getImageId() == R.drawable.white)
                index = r.nextInt(9);
            tiles.get(index).setImageId(R.drawable.white);
        }

        this.moveCount = 0;
        TextView moveText = findViewById(R.id.moves_text);
        moveText.setText(R.string.moves_text_initial);
    }

    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        if(actionBar != null)
            actionBar.hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
    }

    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }
}
