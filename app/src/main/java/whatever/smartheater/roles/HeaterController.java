package whatever.smartheater.roles;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * By  : Wentao Pan(daseinpwt@gmail.com)
 * Date : 2015-04-10
 * Time : 14:26
 */
public class HeaterController {
    private static final String RaspBerryIP = "192.168.23.2";
    private static final int HEATER_PORT = 8002;
    private HeatStatus heatStatus;

    Runnable heatOnModule;
    Runnable heatOffModule;

    public HeaterController(HeatStatus heatStatus) {
        this.heatStatus = heatStatus;
    }

    public void open() {

        heatOnModule = new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket = new Socket(RaspBerryIP, HEATER_PORT);
                    PrintWriter os=new PrintWriter(socket.getOutputStream());
                    os.print("on");
                    os.flush();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        heatOffModule = new Runnable() {
            @Override
            public void run() {

                try {
                    Socket socket = new Socket(RaspBerryIP, HEATER_PORT);
                    PrintWriter os=new PrintWriter(socket.getOutputStream());
                    os.print("off");
                    os.flush();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    }

    public void startHeating() {
        new Thread(heatOnModule).start();
    }

    public void stopHeating() {
        new Thread(heatOffModule).start();
    }

    public void close() {

    }
}  
