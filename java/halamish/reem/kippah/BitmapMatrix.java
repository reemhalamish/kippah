package halamish.reem.kippah;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Re'em on 10/10/2015.
 */
public class BitmapMatrix {
    public final static int PADDING = 4; // from every side. It means twice the padding between tiles
    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 200;
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;
    private ArrayList<ArrayList<Integer>> innerMatrix;

    public BitmapMatrix(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public BitmapMatrix() {}

    public Bitmap getBitmap() {
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                result.setPixel(x, y, getColorForXY(x, y));
            }
        }
        return result;
    }

    private int getColorForXY(int x, int y) {
        int A, R, G, B;
        // "color" is an int represented as (alpha << 24) | (red << 16) | (green << 8) | blue. Each component ranges between 0..255
        if (innerMatrix != null) {
            return innerMatrix.get(x).get(y);
        }
        return randomizedColor();
    }

    private int randomizedColor() {
        Random rnd = new Random();
        int R, G, B, A = 255; // alpha is 100%
        R = rnd.nextInt() % 256;
        G = rnd.nextInt() % 256;
        B = rnd.nextInt() % 256;
        return Color.argb(A, R, G, B);
    }

    public void setBitmapOnImageview(ImageView workAt) {
        workAt.setImageBitmap(getBitmap());
        workAt.setScaleType(ImageView.ScaleType.FIT_XY);
        workAt.invalidate();
    }

    public void setBitmapPixelOnImageview(ImageView workAt, int x, int y) {
        int color;
        if (innerMatrix != null) {
            color = innerMatrix.get(x).get(y);
        } else {
            color = randomizedColor();
        }
        ColorDrawable cd = new ColorDrawable(color);
        workAt.setImageDrawable(cd);
    }

    public int getHeight_RowsNeeded() {
        return height;
    }

    public int getWidth_TilesNeededInOneRow() {
        return width;
    }
}
