package whatever.smartheater.roles;

import java.util.HashMap;

/**
 * Created by lds on 3/19/15.
 */
public class StatusMonitor {
    private StatusListener statusListener;

    private static StatusMonitor smSingleton = new StatusMonitor();

    public static StatusMonitor getInstance() { return smSingleton; }

    public void setStatusLisenter(StatusListener statusListener) {
        this.statusListener = statusListener;
    }

    public void invokeListener(HashMap<String, Double> msgs) {
        statusListener.onChange(msgs);
    }
    /**
     * TODO status listener
     * When status changed, call statusListener.onChange([Some Changing Information])
     * Inspect StatusListener for more information
     */

}
