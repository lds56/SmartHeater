package whatever.smartheater.activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import static whatever.smartheater.Constants.*;


import java.util.HashMap;

import whatever.smartheater.roles.StatusListener;
import whatever.smartheater.roles.StatusMonitor;
import whatever.smartheater.R;
import whatever.smartheater.Utils;
import whatever.smartheater.fragments.BluetoothDialogFragment;
import whatever.smartheater.fragments.TempPickerFragment;

public class TransitionFirstActivity extends ActionBarActivity {

    private View fabButton;

    private Button startButton;
    private Button endButton;
    private Toolbar mainToolbar;

    private View fireImg;
    private View stopImg;

    private View fatBar;
    private View shadowBar;

    private View waterContainer;
    private View waveView;

    private View contentList;

    //private DrawerLayout mDrawerLayout;
    //private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_first);

        // Set explode animation when enter and exit the activity
        // Utils.configureWindowEnterExitTransition(getWindow());

        // Fab Button
        fabButton = findViewById(R.id.fab_button);
        fabButton.setOnClickListener(fabClickListener);

        startButton = (Button) findViewById(R.id.start_position);
        endButton = (Button) findViewById(R.id.end_position);

        fireImg = findViewById(R.id.fire_img);
        stopImg = findViewById(R.id.stop_img);

        fatBar = findViewById(R.id.holder_view);
        shadowBar = findViewById(R.id.shadow_bar);

        waterContainer = findViewById(R.id.water_container);
        waveView = findViewById(R.id.wave_view);

        contentList = findViewById(R.id.content_list);

        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        //Utils.configureFab(fabButton);

        configureToolbar();
        configureAnimation();
        //configureDrawer();
        configureContent();
        initializeObjects();
    }


    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            float barGap = fatBar.getHeight() - mainToolbar.getHeight();
            float bottomGap = contentList.getHeight();
            Log.i("oo", "now: " + view.getX());
            Log.i("oo", "start: " + startButton.getX());

            if (Utils.getCenterX(view) == Utils.getCenterX(startButton)) { // Move down

                // FAB animation

                Utils.moveTo(view, endButton, APPEAR_ALPHA);
                Utils.moveTo(fireImg, endButton, DISAPPEAR_ALPHA);
                Utils.moveTo(stopImg, endButton, APPEAR_ALPHA);

                // Fat Bar animation

                waveView.animate().alpha(0).setDuration(ANIMATION_TIME);
                waterContainer.animate().alpha(0).setStartDelay(0).setDuration(ANIMATION_TIME / 2);

                Utils.slideUp(fatBar, barGap);
                Utils.slideUp(shadowBar, barGap);

                // Content
                Utils.slideUp(contentList, -bottomGap);

            } else if (Utils.getCenterX(view) == Utils.getCenterX(endButton)) { //Move up

                // FAB animation

                Utils.moveTo(view, startButton, APPEAR_ALPHA);
                Utils.moveTo(fireImg, startButton, APPEAR_ALPHA);
                Utils.moveTo(stopImg, startButton, DISAPPEAR_ALPHA);

                // Fat Bar animation

                waveView.animate().alpha(1).setDuration(ANIMATION_TIME);
                waterContainer.animate().alpha(1).setStartDelay(ANIMATION_TIME / 2).setDuration(ANIMATION_TIME / 2);

                Utils.slideDown(fatBar);
                Utils.slideDown(shadowBar);

                //Content
                Utils.slideDown(contentList);

            } else {
                Log.i("oo", "Invalid position");
            }

            //Intent i  = new Intent (TransitionFirstActivity.this, TransitionSecondActivity.class);

            //ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(TransitionFirstActivity.this,
            //        Pair.create(fabButton, "fab"));

            //startActivity(i, transitionActivityOptions.toBundle());
        }
    };


    private void configureToolbar() {
        mainToolbar.setTitle("SmartHeater");
        //mainToolbar.inflateMenu(R.menu.toolbar_menu);

        setSupportActionBar(mainToolbar);
        //getSupportActionBar().setTitle("Sliding");

        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.action_bluetooth:
                        //Toast.makeText(TransitionFirstActivity.this, "bluetooth", Toast.LENGTH_SHORT).show();

                        FragmentManager fm = getSupportFragmentManager();
                        BluetoothDialogFragment btDialog = new BluetoothDialogFragment();
                        btDialog.show(fm, "tag");

                        return true;
                    case R.id.action_equalizer:

                        Toast.makeText(TransitionFirstActivity.this, "equalizer", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_add_alarm:

                        FragmentManager fm2 = getSupportFragmentManager();
                        TempPickerFragment tpDialog = new TempPickerFragment();
                        tpDialog.show(fm2, "tag");
                        //Toast.makeText(TransitionFirstActivity.this, "add alarm", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });


        /*
        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                    mDrawerLayout.closeDrawer(Gravity.START);

                } else {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            }
        });
        */
    }

    /*
    private void configureDrawer() {
        // Configure drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_closed) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        //mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    */

    public void configureContent() {
        SwitchCompat themeSwitch = (SwitchCompat) findViewById(R.id.theme_switch);
        themeSwitch.setChecked(false);
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(TransitionFirstActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                else Toast.makeText(TransitionFirstActivity.this, "Not Checked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    } */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /*public void showTimePickerDialog(MenuItem menuItem) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }*/

    private void configureAnimation() {
        View waterTempView = findViewById(R.id.water_container);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(
                TransitionFirstActivity.this, R.animator.flucation);

        set.setTarget(waterTempView);
        set.start();
    }

    private void initializeObjects() {
        // TODO implement status listener
        StatusMonitor statusMonitor = StatusMonitor.getInstance();
        statusMonitor.setStatusLisenter(new StatusListener() {
            @Override
            public void onChange(HashMap<String, Double> msgs) {
                int waterPercent = Math.round(msgs.get(WATER_PERCENT).intValue());
            }
        });
    }
}
