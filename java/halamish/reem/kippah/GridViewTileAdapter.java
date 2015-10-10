//package halamish.reem.kippah;
//
//import android.content.Context;
//import android.media.Image;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//
///**
// * Created by Re'em on 10/3/2015.
// */
//public class GridViewTileAdapter extends BaseAdapter {
//    public static final int TILE_SIZE_DEFALUT = 64;
//    private final Context context;
//    private MyColorMatrix matrix;
//    private int tilesInsideRow;
//
//
//    public GridViewTileAdapter(Context context, MyColorMatrix matrix) {
//        super();
//        this.matrix = matrix;
//        this.context = context;
//        tilesInsideRow = matrix.getWidth_TilesNeededInOneRow();
//    }
//
//    @Override
//    public int getCount() {
//        return matrix.getCount();
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            view = inflater.inflate(R.layout.grid_view_tile, viewGroup, false);
//        }
//        ImageView tile = (ImageView) view.findViewById(R.id.iv_tile);
//        int tile_size = TILE_SIZE_DEFALUT;
//        matrix.drawView(tile, getColumnFromIndex(i), getRowFromIndex(i));
//        ViewGroup.LayoutParams lp = tile.getLayoutParams();
//        lp.height = tile_size;
//        lp.width = tile_size;
//        tile.requestLayout();
////        tile.setLayoutParams(new GridView.LayoutParams(tile_size, tile_size));
//        tile.setScaleType(ImageView.ScaleType.FIT_XY);
//        return view;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return matrix.getItem(getColumnFromIndex(i), getRowFromIndex(i));
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    public int columnsNeeded() {
//        return tilesInsideRow;
//    }
//
//    private int getRowFromIndex(int index) {
//        return index / tilesInsideRow;
//    }
//    private int getColumnFromIndex(int index) {
//        return index % tilesInsideRow;
//    }
//
//    public int getDefaultTileSize() {
//        return TILE_SIZE_DEFALUT;
//    }
//}
