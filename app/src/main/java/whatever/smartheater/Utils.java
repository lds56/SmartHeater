package whatever.smartheater;

//import android.graphics.Outline;
//import android.transition.Explode;
//import android.transition.Slide;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import static whatever.smartheater.Constants.*;
//import android.view.animation.PathInterpolator;

public class Utils {

    /*public static void configureWindowEnterExitTransition (Window w) {

        Explode ex = new Explode();
        ex.setInterpolator(new PathInterpolator(0.4f, 0, 1, 1));
        w.setExitTransition(ex);
        w.setEnterTransition(ex);
    }*/

   /* public static void configureFab (View fabButton) {

        int fabSize = fabButton.getContext().getResources()
            .getDimensionPixelSize(R.dimen.fab_size);

        Outline fabOutLine = new Outline();
        fabOutLine.setOval(0, 0, fabSize, fabSize);
    }
    */

    public static float getCenterX(View theView) {
        return theView.getX() + theView.getWidth()/2;
    }

    public static float getCenterY(View theView) {
        return theView.getY() + theView.getHeight()/2;
    }

    public static float getAvgX(View theView, View anotherView) {
        return (theView.getX() + anotherView.getX())/2;
    }

    public static float getAvgY(View theView, View anotherView) {
        return (theView.getY() + anotherView.getY())/2;
    }

    public static void slideUp(View theView, float delta) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(-delta)
                .setDuration(ANIMATION_TIME);
    }

    public static void slideDown(View theView) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .setDuration(ANIMATION_TIME);
    }

    public static void moveTo(View theView, View theTargetView, float aimAlpha) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .x(Utils.getCenterX(theTargetView) - theView.getWidth() / 2)
                .y(Utils.getCenterY(theTargetView) - theView.getHeight() / 2)
                .alpha(aimAlpha)
                .setDuration(ANIMATION_TIME);
    }

    public static void moveWith(final View theView, final View theTargetView, final float aimScale1, final float aimScale2) {

        Runnable endAction = new Runnable() {
            public void run() {
                theView.animate().setInterpolator(new LinearInterpolator())
                        .x(theTargetView.getX())
                        .y(theTargetView.getY())
                        .alpha(aimScale2).alpha(aimScale2)
                        .setDuration(ANIMATION_TIME / 2);
            }
        };

        theView.animate().setInterpolator(new LinearInterpolator())
                .x( Utils.getAvgX(theView, theTargetView) )
                .y( Utils.getAvgY(theView, theTargetView) )
                .alpha(aimScale1).alpha(aimScale1)
                .setDuration(ANIMATION_TIME / 2)
                .withEndAction(endAction);

    }

}
