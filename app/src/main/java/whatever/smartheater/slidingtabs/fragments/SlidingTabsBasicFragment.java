/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package whatever.smartheater.slidingtabs.fragments;


import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.view.LineChartView;
import whatever.smartheater.charts.SanLineChart;
import whatever.smartheater.R;
import whatever.smartheater.slidingtabs.views.SlidingTabLayout;

import java.util.HashMap;

import static whatever.smartheater.Constants.*;


public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    /**
     * A custom {@link android.support.v4.view.ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link android.support.v4.view.ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link android.view.View} which will be displayed by this {@link android.app.Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding, container, false);
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)} has finished.
     * Here we can pick out the {@link android.view.View}s we need to configure from the content view.
     *
     * We set the {@link android.support.v4.view.ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link android.support.v4.view.ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {
        final String [] TITLES = {"Day", "Week", "Year"};

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(android.view.ViewGroup, int)} is the
         * same object as the {@link android.view.View} added to the {@link android.support.v4.view.ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link android.view.View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.chart_layout,
                    container, false);
            // Add the newly created View to the ViewPager

            /**
             * Line
             */
             final  int LINE_MAX = 10;
             final  int LINE_MIN = -10;
             final  String[] lineLabels = {"", "ANT", "GNU", "OWL", "APE", "JAY", ""};
             final  float[][] lineValues = { {-5f, 6f, 2f, 9f, 0f, 1f, 5f},
                    {-9f, -2f, -4f, -3f, -7f, -5f, -3f}};

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            LineChartView mLineChart = (LineChartView) view.findViewById(R.id.linechart_day);
            TextView mTV = (TextView) getActivity().getLayoutInflater().inflate(R.layout.circular_tooltip, null);

            HashMap<String, Integer> colorMap = new HashMap<String, Integer>();
            colorMap.put(SCREEN_WIDTH, size.x);
            colorMap.put(CARD_PADDING, (int) getResources().getDimension(R.dimen.chart_card_margin));
            colorMap.put(PAINT_COLOR, getResources().getColor(R.color.white_color));  //axis
            colorMap.put(DOTS_COLOR, getResources().getColor(R.color.white_color));
            colorMap.put(DOTS_STRIKE_COLOR, getResources().getColor(R.color.white_color));
            colorMap.put(LINE_COLOR, getResources().getColor(R.color.white_color));
            colorMap.put(DASH_LINE_COLOR, getResources().getColor(R.color.white_color));
            colorMap.put(HOVER_TEXT_COLOR, getResources().getColor(R.color.chart_color_day));

            SanLineChart dayLineChart = new SanLineChart(mLineChart, mTV);
            dayLineChart.initColorMap(colorMap);
            dayLineChart.setLineLabels(lineLabels);
            dayLineChart.initPaint();
            dayLineChart.setEnvironmentTemp(lineValues[0]);
            dayLineChart.setWaterTemp(lineValues[1]);
            dayLineChart.setBoarder();
            dayLineChart.show();

            LineChartView mLineChart2 = (LineChartView) view.findViewById(R.id.linechart_night);
            TextView mTV2 = (TextView) getActivity().getLayoutInflater().inflate(R.layout.circular_tooltip, null);

            colorMap.put(HOVER_TEXT_COLOR, getResources().getColor(R.color.chart_color_night));
            SanLineChart nightLineChartView = new SanLineChart(mLineChart2, mTV2);
            nightLineChartView.initColorMap(colorMap);
            nightLineChartView.setLineLabels(lineLabels);
            nightLineChartView.initPaint();
            nightLineChartView.setEnvironmentTemp(lineValues[0]);
            nightLineChartView.setWaterTemp(lineValues[1]);
            nightLineChartView.setBoarder();
            nightLineChartView.show();

            container.addView(view);

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link android.support.v4.view.ViewPager}. In our case this is simply removing the
         * {@link android.view.View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }


    }
}
