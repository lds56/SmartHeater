package whatever.smartheater.roles;

/**
 * By  : Wentao Pan(daseinpwt@gmail.com)
 * Date : 2015-04-10
 * Time : 16:01
 */
public class HeatStatus {
    public boolean onHeating;
    public boolean onKeeping;
    public boolean onStopping;

    public void reset() {
        onHeating = false;
        onKeeping = false;
        onStopping = false;
    }
}  
