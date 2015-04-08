/* Decompiled by Jasmine from Video2.class */
/* Originally compiled from Video2.java */

package MPEGDecoder;

import java.io.*;
import java.awt.Label;
import java.net.URL;
import java.util.Date;

public class Video implements Runnable 
{
    


    public final void stopmovie()
    {
        if (instream == null)
            return;
        Date date = new Date();
        Float floatx= new Float((float)(1000 * gop.getnumofpic()) / (date.getTime() - starttime));
        String string = floatx.toString();
        if (string.length() > 5)
            string = string.substring(0, 5);
        fps.setText(string + " fps");
        try
        {
            instream.close();
        }
        catch (IOException e)
        {
        }
        gop.setstream(null);
        instream = null;
    }
}
