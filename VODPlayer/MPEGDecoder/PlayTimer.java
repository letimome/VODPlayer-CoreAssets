/* Decompiled by Jasmine from PlayTimer.class */
/* Originally compiled from PlayTimer.java */

package MPEGDecoder;

import java.awt.Label;
import java.util.Date;

public class PlayTimer extends Thread
{
    Label timer;

    public PlayTimer(Label label)
    {
        timer = label;
    }

    public void run()
    {
        Date date2 = new Date();
        long i = date2.getTime();
        while (true)
        {
            date2 = new Date();
            Date date1 = new Date(date2.getTime() - i);
            String string = ":" + date1.getSeconds();
            timer.setText(new StringBuffer("Current : ").append(string).toString());
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }
        }
    }

    public void stoptime()
    {
        timer.setText("Current : 0:0");
        stop();
    }
}
