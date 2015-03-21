package whatever.smartheater.roles;

import java.util.EventListener;
import java.util.HashMap;

/**
 * Created by lds on 3/19/15.
 */
public interface StatusListener extends EventListener {
    void onChange(HashMap<String, Double> msgs);
}