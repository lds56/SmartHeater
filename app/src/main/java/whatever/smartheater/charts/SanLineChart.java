package whatever.smartheater.charts;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.style.DashAnimation;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

import whatever.smartheater.R;

import static whatever.smartheater.Constants.*;

/**
 * Created by lds on 3/20/15.
 */
public class SanLineChart {

    final  int LINE_MAX = 10;
    final  int LINE_MIN = -10;

    private final TimeInterpolator enterInterpolator = new DecelerateInterpolator(1.5f);
    private final TimeInterpolator exitInterpolator = new AccelerateInterpolator();

    private String[] lineLabels;
    private LineChartView lineChartView;
    private HashMap<String, Integer> colorMap;
    private Paint mLineGridPaint = new Paint();
    private Context context;
    private TextView mLineTooltip;
    private Vector<float[]> lineValues = new Vector<float[]>(2);

    public void initColorMap(HashMap<String, Integer> aColorMap) {
        colorMap = new HashMap<String, Integer>(aColorMap);
        //colorMap = (HashMap) aColorMap.clone();
    }

    public SanLineChart(LineChartView lineChartView, HashMap<String, Integer> aColorMap) {
        this.lineChartView = lineChartView;
        colorMap.putAll(aColorMap);
    }

    public SanLineChart(LineChartView lineChartView, TextView mLineTooltip) {
        this.lineChartView = lineChartView;
        this.mLineTooltip = mLineTooltip;
    }

    public void appendNewColorMap(HashMap<String, Integer> newColorMap) {
        colorMap.putAll(newColorMap);
    }

    public void setLineLabels(String[] lineLabels) {
        this.lineLabels = lineLabels;
    }

    public void initPaint() {

        lineChartView.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {

                if (mLineTooltip == null)
                    showLineTooltip(setIndex, entryIndex, rect);
                else
                    dismissLineTooltip(setIndex, entryIndex, rect);
            }
        });

        lineChartView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mLineTooltip != null)
                    dismissLineTooltip(-1, -1, null);
            }
        });

        mLineGridPaint.setColor(colorMap.get(PAINT_COLOR));
        mLineGridPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        mLineGridPaint.setStyle(Paint.Style.STROKE);
        mLineGridPaint.setAntiAlias(true);
        mLineGridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        lineChartView.reset();
    }

    public void setEnvironmentTemp(float[] ETValues) {
        lineValues.add(ETValues);
        LineSet dataSet = new LineSet();
        dataSet.addPoints(lineLabels, ETValues);
        dataSet.setDots(true)
                .setDotsColor(colorMap.get(DOTS_COLOR))
                .setDotsRadius(Tools.fromDpToPx(5))
                .setDotsStrokeThickness(Tools.fromDpToPx(2))
                .setDotsStrokeColor(colorMap.get(DOTS_STRIKE_COLOR))
                .setLineColor(colorMap.get(LINE_COLOR))
                .setLineThickness(Tools.fromDpToPx(3))
                .beginAt(1).endAt(lineLabels.length - 1);
        lineChartView.addData(dataSet);
    }

    public void setWaterTemp(float[] WTValues) {
        lineValues.add(WTValues);
        LineSet dataSet = new LineSet();
        dataSet.addPoints(lineLabels, WTValues);
        dataSet.setLineColor(colorMap.get(DASH_LINE_COLOR))
                .setLineThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .setDashed(true);
        lineChartView.addData(dataSet);
    }

    public void setBoarder() {
        lineChartView.setBorderSpacing(Tools.fromDpToPx(4))
                .setGrid(LineChartView.GridType.HORIZONTAL, mLineGridPaint)
                .setXAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYAxis(false)
                .setYLabels(YController.LabelPosition.OUTSIDE)
                .setAxisBorderValues(LINE_MIN, LINE_MAX, 5)
                .setLabelsFormat(new DecimalFormat("##'u'"));

        //lineChartView.animateSet(1, new DashAnimation());
    }

    public void show() {
        //Log.i("Chart", "Width: " + lineChartView.getMeasuredWidth());
        lineChartView.setLayoutParams(new FrameLayout.LayoutParams(
                colorMap.get(SCREEN_WIDTH) - 2*colorMap.get(CARD_PADDING),
                colorMap.get(SCREEN_WIDTH) - 2*colorMap.get(CARD_PADDING)
        ));

        lineChartView.show();
    }

    private void showLineTooltip(int setIndex, int entryIndex, Rect rect){

        mLineTooltip.setText(Integer.toString((int)lineValues.get(setIndex)[entryIndex]));
        mLineTooltip.setTextColor(colorMap.get(HOVER_TEXT_COLOR));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)Tools.fromDpToPx(35), (int)Tools.fromDpToPx(35));
        layoutParams.leftMargin = rect.centerX() - layoutParams.width/2;
        layoutParams.topMargin = rect.centerY() - layoutParams.height/2;

        //Log.i("CHART", "centerXY: " + rect.centerX() + ", " + rect.centerY());
        Log.i("CHART", "margin: " + layoutParams.leftMargin + ", " + layoutParams.topMargin);

        mLineTooltip.setLayoutParams(layoutParams);

            mLineTooltip.setPivotX(layoutParams.width/2);
            mLineTooltip.setPivotY(layoutParams.height/2);
            mLineTooltip.setAlpha(0);
            mLineTooltip.setScaleX(0);
            mLineTooltip.setScaleY(0);
            mLineTooltip.animate()
                    .setDuration(150)
                    .alpha(1)
                    .scaleX(1).scaleY(1)
                    .rotation(360)
                    .setInterpolator(enterInterpolator);

        Log.i("CHART", "toolXY: " + mLineTooltip.getX() + ", " + mLineTooltip.getY());


        lineChartView.showTooltip(mLineTooltip);
    }


    private void dismissLineTooltip(final int setIndex, final int entryIndex, final Rect rect){

            mLineTooltip.animate()
                    .setDuration(100)
                    .scaleX(0).scaleY(0)
                    .alpha(0)
                    .setInterpolator(exitInterpolator).withEndAction(new Runnable(){
                @Override
                public void run() {
                    lineChartView.removeView(mLineTooltip);
                    //mLineTooltip = null;
                    if(entryIndex != -1)
                        showLineTooltip(setIndex, entryIndex, rect);
                }
            });
    }

}
