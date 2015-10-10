package halamish.reem.kippah;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class KippahGridViewActivity extends AppCompatActivity {
//    private static final int SAME_WEIGHT_ALL_VIEWS = 1;
    private static final String TAG = "GridView";
    private static final int TILE_SIZE_FOR_PREVIEW = 16;
    private static final int TILE_SIZE_FOR_BIG_LAYOUT = 96;

    private BitmapMatrix matrix;
    private int width, height;
//    private GridView gv_table;
//    private GridViewTileAdapter gva_aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kippah_grid_view);
//        gv_table = (GridView) findViewById(R.id.gv_grid_table);
        matrix = new BitmapMatrix();
        updateScreenResolution();
//        drawMatrixOnTable();

        createSaveMySpotButton();

        /* TODO http://stackoverflow.com/questions/6661261/adding-content-to-a-linear-layout-dynamically
        make sure there is enough space to put all your ImageButtons (16x16 for every tile at least!)
        if not enough place, create a dialog with "something went wrong - screen too small"
        (LATERON, maybe some option to zoom in with a multitouch?)
        add dynamically ImageButtons, set their source to be the wahtever color i want.
        LATERON, when creating a layout to edit this, instead of imagevies i can just use imagebuttons :)
        LATERON, at the title's place, put an option to change the default background TILES as well as the bg COLOR of the layout
         */



    }

    private void createSaveMySpotButton() {
        ImageButton ib_saveMySpot = (ImageButton) findViewById(R.id.ib_grid_save_my_spot);
        ib_saveMySpot.setOnClickListener(new View.OnClickListener() {
            boolean toSave = false;

            @Override
            public void onClick(View view) {
                toSave = toSave ^ true; // xor
                if (toSave) {
                    ((ImageButton) view).setImageResource(R.drawable.mr_ic_pause_light);
                    switchTheScrollsListeners(false); // shut them down
                } else {
                    ((ImageButton) view).setImageResource(R.drawable.mr_ic_play_light);
                    switchTheScrollsListeners(true); // turn them on
                }
            }
        });
    }

    private void switchTheScrollsListeners(boolean shouldListen) {
        ((HScrollView) findViewById(R.id.wsv_grid_horizontal_sv)).setIgnoreTouches(!shouldListen); // ignore or listen
    }

//    private void drawMatrixOnTable() {
//        gva_aa = new GridViewTileAdapter(this, matrix);
//        int columns = gva_aa.columnsNeeded();
//        int tileSize = gva_aa.getDefaultTileSize();// TODO needed ?matrix.getTile_size();
//        gv_table.setNumColumns(columns);
//        gv_table.setColumnWidth(tileSize);
//        gv_table.setAdapter(gva_aa);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kippah_grid_view, menu);
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
        } else if (id == R.id.action_preview) {
            openPreviewDialog2();
//            Toast.makeText(this, "preview called!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ImageButton getOneTileNormal(final int x, final int y) {
//        View retval = getLayoutInflater().inflate(R.layout.grid_view_one_tile, null);
        final ImageButton retval = new ImageButton(this);
        int tileSize = TILE_SIZE_FOR_BIG_LAYOUT;
        ViewGroup.LayoutParams lp = retval.getLayoutParams();
        if (lp != null) {
            lp.height = tileSize;
            lp.width = tileSize;
            retval.requestLayout();
        } else {
            retval.setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
        }
        retval.setScaleType(ImageButton.ScaleType.FIT_XY);

        int P = BitmapMatrix.PADDING;
        retval.setPadding(P, P, P, P);

        matrix.setBitmapPixelOnImageview(retval, x, y);

        retval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "changing color for "+x + ", " + y);
                matrix.setBitmapPixelOnImageview(retval, x, y);
            }
        });


        return retval;
    }


    protected void updateScreenResolution() {
        final int version = android.os.Build.VERSION.SDK_INT;
        Display display = getWindowManager().getDefaultDisplay();
        if (version >= 13) {
            _updateNewAPI(display);
        } else {
            _updateOldAPI(display);
        }
//        if (!matrix.init_screenSupportsThisPattern(width - 32, height/2)) { // using only [+-] half the screen for the grid
//            Log.e(TAG, "not enough space to view the pattern");
//            // TODO nice massage on the screen
//        }
        drawColorMatrix();
    }

    private void drawColorMatrix() {
        ((HScrollView) findViewById(R.id.wsv_grid_horizontal_sv)).sv_child = (VScrollView) findViewById(R.id.sv_grid_parent_sv);


//        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.rl_kippah_grid_view_root);
        LinearLayout viewTilesLayout = (LinearLayout) findViewById(R.id.ll_grid_placeholder);
        // = new LinearLayout(this);
//        viewTilesLayout.setOrientation(LinearLayout.VERTICAL);
//        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        viewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        viewParams.addRule(RelativeLayout.BELOW, R.id.tv_grid_title);
//        viewTilesLayout.setLayoutParams(viewParams);


        int
                rows = matrix.getHeight_RowsNeeded(),
                tiles_in_row = matrix.getWidth_TilesNeededInOneRow();
        for (int i = 0; i < rows; i++) {
            LinearLayout oneRow = new LinearLayout(this);
            oneRow.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < tiles_in_row; j++) {
                oneRow.addView(getOneTileNormal(j, i));
            }
            viewTilesLayout.addView(oneRow);
        }
//        rootLayout.addView(viewTilesLayout);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void _updateNewAPI(Display display) {
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }

    @SuppressWarnings("deprecation")
    private void _updateOldAPI(Display display) {
        width = display.getWidth();
        height = display.getHeight();
    }

//    protected void openPreviewDialog() {
//        final int tileSizePreview = matrix.calculateTileSize(width - 256, height - 512, false);
//        if (tileSizePreview < 1) {
//            Log.e(TAG, "pattern is too big for showing without any scroller");
//            return;
//        }
////        LayoutInflater factory = LayoutInflater.from(this);
////        final View previewDialogView = factory.inflate(
////                R.layout.dialog_kippah_grid_view_preview, null);
////        final AlertDialog previewDialog = new AlertDialog.Builder(this).create();
////        previewDialog.setView(previewDialogView);
//
//        final Dialog previewDialog = new Dialog(this);
//        previewDialog.setContentView(R.layout.dialog_kippah_grid_view_preview);
//        previewDialog.setTitle("Preview");
//
////        RelativeLayout rootLayout = (RelativeLayout) previewDialog.findViewById(R.id.rl_kippah_grid_view_preview_root);
//        LinearLayout viewTilesLayout = (LinearLayout) previewDialog.findViewById(R.id.ll_preview_placeholder);//new LinearLayout(this);
////        viewTilesLayout.setOrientation(LinearLayout.VERTICAL);
////        RelativeLayout.LayoutParams viewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
////        viewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
////        viewParams.addRule(RelativeLayout.ABOVE, R.id.tv_grid_uppermargin);
////        viewTilesLayout.setLayoutParams(viewParams);
//
//        // TODO: instead all of that, just load it as a bitmap into just ONE IMAGEVIEW, scale it nice on the monitor and finish!!
//
//        int
//                rows = matrix.getHeight_RowsNeeded(),
//                tiles_in_row = matrix.getWidth_TilesNeededInOneRow();
//        for (int i = 0; i < rows; i++) {
//            LinearLayout oneRow = new LinearLayout(this);
//            oneRow.setOrientation(LinearLayout.HORIZONTAL);
//
//            for (int j = 0; j < tiles_in_row; j++) {
//                oneRow.addView(getOneTilePreview(j, i, tileSizePreview));
//            }
//            viewTilesLayout.addView(oneRow);
//        }
////        rootLayout.addView(viewTilesLayout);
//
//        Button okayButton = (Button) previewDialog.findViewById(R.id.btn_preview_ok);
//        okayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                previewDialog.dismiss();
//            }
//        });
//
//        previewDialog.show();
//    }

    protected void openPreviewDialog2() {
        final Dialog previewDialog = new Dialog(this);
        previewDialog.setContentView(R.layout.dialog_kippah_grid_view_preview_ver_2);
        previewDialog.setTitle("Preview");


        // set the scrollers (if needed)
        ((HScrollView) previewDialog.findViewById(R.id.hsv_preview2_main)).sv_child = (VScrollView) previewDialog.findViewById(R.id.vsv_preview2_second);

        // set the dismiss button
        Button okayButton = (Button) previewDialog.findViewById(R.id.btn_preview2_ok);
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previewDialog.dismiss();
            }
        });

        // set the actual preview into the ImageView
        ImageView iv_preview = (ImageView) previewDialog.findViewById(R.id.iv_preview2_placeholder);
        matrix.setBitmapOnImageview(iv_preview);
        int width, height;
        width = matrix.getWidth_TilesNeededInOneRow() * TILE_SIZE_FOR_PREVIEW;
        height = matrix.getHeight_RowsNeeded() * TILE_SIZE_FOR_PREVIEW;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        iv_preview.setLayoutParams(params);

        previewDialog.show();
    }
//
//    private ImageView getOneTilePreview(int j, int i, int tileSize) {
//        final ImageView retval = new ImageView(this);
//        ViewGroup.LayoutParams lp = retval.getLayoutParams();
//        if (lp != null) {
//            lp.height = tileSize;
//            lp.width = tileSize;
//            retval.requestLayout();
//        } else {
//            retval.setLayoutParams(new LinearLayout.LayoutParams(tileSize, tileSize));
//        }
//        retval.setScaleType(ImageButton.ScaleType.FIT_XY);
//        retval.setPadding(0,0,0,0);
//        matrix.drawView(retval, width, height);
//        return retval;
//    }
}
