/* Decompiled by Mocha from Vodclient.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.*;
import java.applet.Applet;
import java.net.URL;
import java.awt.event.*;

import MPEGDecoder.VODClientListener3;

public class VODClient extends Frame
{
    
    Button buttonControl3;
   

    public VODClient()
    {

        buttonControl3 = new Button();
        c = VODClientListener3.class;
   
    }

    void buttonControl3_actionPerformed(ActionEvent actionEvent)
    {
		//stop
        if (curmovie == null)
            return;
        vthread.stop();
        video.stopmovie();
        buttonControl2.setLabel("PLAY");
    }

  

    private  void jbInit()
        throws Exception
    {
        original();
        buttonControl3.setBounds(new Rectangle(115, 22, 40, 45));
        buttonControl3.setLabel("STOP");
        buttonControl3.addActionListener(new VODClientListener3(this));
        bevelPanel2.add(buttonControl3, null);
    }

   public  void stopmovie()
    {
        original();
        buttonControl2.setLabel("PLAY");
    }
  
}