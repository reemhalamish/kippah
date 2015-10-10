package halamish.reem.kippah;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Re'em on 10/3/2015.
 */
public class VScrollView extends ScrollView {
    private boolean ignoreTouches = false;
    public VScrollView(Context context) {
        super(context);
    }

    public VScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ignoreTouches) { return super.onInterceptTouchEvent(ev);}
//        return (!ignoreTouches) && super.onInterceptTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return (!ignoreTouches) && super.onTouchEvent(ev);
    }

    public void setIgnoreTouches(boolean newState) {
        ignoreTouches = newState;
    }
}
