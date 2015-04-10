package whatever.smartheater.roles;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.TimerTask;

/**
 * By  : Wentao Pan(daseinpwt@gmail.com)
 * Date : 2015-04-10
 * Time : 13:29
 */
public class TempListener {
    private static final String RaspBerryIP = "192.168.23.2";
    private static final int TEMP_PORT = 8001;
    public static final int TEMP = 0;
    public static final int TIMER = 1;
    private int pastSeconds;

    private int HEAT_TOP = 40;
    private int HEAT_BOT = 30;

    private TextView tmView = null;
    private TextView tmhView = null;
    private TextView hstView = null;
    private TextView timerView = null;

    private Thread tmThread = null;
    private Handler myHandler = null;

    private HeatStatus heatStatus;
    private HeaterController heaterController;

    private java.util.Timer timer = new java.util.Timer(true);

    public TempListener(HeatStatus heatStatus, TextView tmView, TextView tmhView, TextView hstView, TextView timerView, HeaterController heaterController) {
        this.heatStatus = heatStatus;
        this.tmView = tmView;
        this.tmhView = tmhView;
        this.hstView = hstView;
        this.timerView = timerView;
        this.heaterController = heaterController;
    }

    public void open() {

        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TempListener.TEMP:
                        setTemperature(msg.getData().getString("tm"));
                        break;
                    case TempListener.TIMER:
                        setTimer();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        tmThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket = new Socket(RaspBerryIP, TEMP_PORT);
                    BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String temp;
                    while (true) {
                        temp = is.readLine();
                        Message msg = new Message();
                        msg.what = TempListener.TEMP;
                        Bundle bundle = new Bundle();
                        bundle.putString("tm", temp);
                        msg.setData(bundle);
                        TempListener.this.myHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        tmThread.start();

        pastSeconds = 0;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                pastSeconds++;
                Message msg = new Message();
                msg.what = TempListener.TIMER;
                TempListener.this.myHandler.sendMessage(msg);
            }
        };
        timer.schedule(task, 0, 1000);
    }

    public void resetTimer() {
        pastSeconds = 0;
    }

    public void close() {

    }

    public void setTemperature(String temp) {
        temp = temp.substring(0, temp.length() - 3);

        tmView.setText(temp);
        tmhView.setText(temp + "°C");

        int temperature = Integer.parseInt(temp);

        if (heatStatus.onHeating && temperature >= HEAT_TOP) {
            heatStatus.reset();
            heatStatus.onKeeping = true;
            heaterController.stopHeating();
        }

        if (heatStatus.onKeeping && temperature <= HEAT_BOT) {
            heaterController.startHeating();
        }

        if (heatStatus.onKeeping && temperature >= HEAT_TOP) {
            heaterController.stopHeating();
        }

        if (heatStatus.onHeating) {
            hstView.setText("Heating");
        }

        if (heatStatus.onKeeping) {
            hstView.setText("Keeping");
        }
    }

    public void setTimer() {
        int temp = pastSeconds;

        int second = temp % 60;
        temp /= 60;
        int minute = temp % 60;
        temp /= 60;
        int hour = temp % 60;

        String pastTime = dts(hour) + ":" + dts(minute) + ":" + dts(second);
        timerView.setText(pastTime);
    }

    private String dts(int x) {
        if (x < 10)
            return "0" + x;
        else
            return "" + x;
    }

}
