package halamish.reem.kippah;

/**
 * Created by Re'em on 10/3/2015.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.HorizontalScrollView;
import android.view.MotionEvent;
import android.content.Context;
import android.util.AttributeSet;

public class HScrollView extends HorizontalScrollView
{
    private boolean ignoreTouches = false;
    public VScrollView sv_child;
    public HScrollView(Context context)
    {
        super(context);
    }

    public HScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override public boolean onTouchEvent(MotionEvent event)
    {
        boolean ret = (!ignoreTouches) && super.onTouchEvent(event);
        boolean child_retval = sv_child.onTouchEvent(event);
        ret = ret | child_retval;
        return true;// ret;
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (ignoreTouches) { return sv_child.onInterceptTouchEvent(event);}
        boolean ret = super.onInterceptTouchEvent(event);
        boolean child_retval = sv_child.onInterceptTouchEvent(event);
        ret = ret | child_retval;
        return true;//ret;
    }

    public void setIgnoreTouches(boolean newState) {
        ignoreTouches = newState;
        sv_child.setIgnoreTouches(newState);
    }
}