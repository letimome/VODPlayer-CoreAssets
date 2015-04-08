/* Decompiled by Mocha from Vodclient.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.*;
import java.applet.Applet;
import java.net.URL;
import java.awt.event.*;

import MPEGDecoder.VODClientListener4;

public class VODClient extends Frame
{
  
	Button buttonControl4;
   

    public VODClient()
    {
		
        buttonControl4 = new Button();
        c = VODClientListener4.class;
        
    }

    void buttonControl4_actionPerformed(ActionEvent actionEvent)
    {
		int success = 0;
		
		if (server!=null)
		{
			success = server.terminateConnection();
		}
		
		//quit
		if (vthread != null)
			vthread.stop();
		
		if (video != null)
		{
			video.stopmovie();
			video.destroy();
		}
		
		vthread = null;
		video = null;
		
		super.dispose();
		
      System.exit(success);
    }

  

    private  void jbInit()
        throws Exception
    {
        original();
        buttonControl4.setBounds(new Rectangle(165, 22, 45, 45));
        buttonControl4.setLabel("Quit");
        buttonControl4.addActionListener(new VODClientListener4(this));
        bevelPanel2.add(buttonControl4, null);
       
    }

   
    

}
