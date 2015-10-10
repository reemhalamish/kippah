package halamish.reem.kippah;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Re'em on 10/1/2015.
 *
 * this class is used to store the pattern by storing a matrix of the dots
 */
public class MyColorMatrix {
    private static final String TAG = "ColorMatrix";
    private ArrayList<ArrayList<Integer>> innerMatrix;
    private int width_tilesInRow, height_rows, tile_size;

    public final static int MIN_SIZE_FOR_TILE = 24;
    public final static int PADDING = 4; // from every side. It means twice the padding between tiles


    public MyColorMatrix() {
        width_tilesInRow = 100;
        height_rows = 20;
    }

//    public boolean init_screenSupportsThisPattern(int screenWidth, int screenHeight) {
//        boolean retval;
//
//        int heightMaxPixels, widthMaxPixels;
//        widthMaxPixels = (screenWidth - ((width_tilesInRow * 2 * PADDING))) / width_tilesInRow;
//        heightMaxPixels= (screenHeight - ((height_rows * 2 * PADDING))) / height_rows;
//        tile_size = Math.min(widthMaxPixels, heightMaxPixels);
//        Log.i(TAG, "tile size: " + tile_size);
//        retval = tile_size >= MIN_SIZE_FOR_TILE;
//        return retval;
//    }
    public int getWidth_TilesNeededInOneRow() {
        return width_tilesInRow;
    }
    public int getHeight_RowsNeeded() {
        return height_rows;
    }

    public int calculateTileSize(int screenWidth, int screenHeight, boolean withPadding) {
        int heightMaxPixels, widthMaxPixels, usePadding;
        usePadding = withPadding ? 2 * PADDING : 0; // i.e. if using padding, this calculates to 2*PADDING, else 0

        widthMaxPixels = (screenWidth - ((width_tilesInRow * usePadding))) / width_tilesInRow;
        heightMaxPixels= (screenHeight - ((height_rows * usePadding))) / height_rows;
        return Math.min(widthMaxPixels, heightMaxPixels);
    }

    public void drawView(ImageView view, int width_index, int height_index) {
        int colorToDraw;
        if (innerMatrix != null)
            colorToDraw = innerMatrix.get(width_index).get(height_index);
        else
            colorToDraw = randomizeColor();
        view.setImageResource(R.drawable.white_square_full);
        view.setColorFilter(colorToDraw);
    }

    private int randomizeColor() {
        Random rnd = new Random();
        int i = ((rnd.nextInt() % 9) + 9) % 9;
        switch (i) {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.RED;
            case 2:
                return Color.MAGENTA;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.CYAN;
            case 6:
                return Color.GRAY;
            case 7:
                return Color.BLACK;
            case 8:
                return Color.WHITE;
            default: // never reach that
                return Color.TRANSPARENT;
        }
    }

    public int getCount() {
        return width_tilesInRow * height_rows;
    }

    public Integer getItem(int column, int row) {
        int colorToDraw;
        if (innerMatrix != null)
            colorToDraw = innerMatrix.get(column).get(row);
        else
            colorToDraw = randomizeColor();
        return colorToDraw;
    }
}
