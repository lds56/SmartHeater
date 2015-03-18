package whatever.smartheater;

//import android.graphics.Outline;
//import android.transition.Explode;
//import android.transition.Slide;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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

    public static void slideUp(View theView, float delta) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(-delta)
                .setDuration(500);
    }

    public static void slideDown(View theView) {
        theView.animate().setInterpolator(new AccelerateDecelerateInterpolator())
                .translationY(0)
                .setDuration(500);
    }
}
