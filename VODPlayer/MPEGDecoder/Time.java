/* Decompiled by Jasmine from Time.class */
/* Originally compiled from Time.java */

package MPEGDecoder;

import java.awt.Label;
import java.util.Date;

public class Time extends Thread
{
    Label timer;

    public Time(Label label)
    {
        timer = label;
    }

    public void run()
    {
        Date date;
        String string;
        while (true)
        {
            date = new Date();
            string = ":" + date.getMinutes() + ":" + date.getSeconds();
            timer.setText(new StringBuffer("Time : ").append(string).toString());
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }
        }
    }
}
