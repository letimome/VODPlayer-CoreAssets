/* Decompiled by Mocha from Vodclient.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.*;
import java.applet.Applet;
import java.net.URL;
import java.awt.event.*;

public class VODClient extends Frame
{
    
   
    Button buttonControl2;//button play
    
    public VODClient() {

		buttonControl2 = new Button();
		c = VODClientListener2.class;
	}
    
    private  void jbInit() throws Exception {
		original();
		buttonControl2.setBounds(new Rectangle(70, 22, 40, 45));
		buttonControl2.setLabel("PLAY");
		buttonControl2.addActionListener(new VODClientListener2(this));
		bevelPanel2.add(buttonControl2, null);
	}

    
    void buttonControl2_actionPerformed(ActionEvent actionEvent) {
		// play, pause, resume
		if (buttonControl2.getLabel().equals("PLAY")) {
			if (curmovie == null)
				return;
			vthread = new Thread(video);
			vthread.start();
		
			setLabelPause();//lo refinaPause si es necesario
					//buttonControl2.setLabel("PAUSE");	
			return;	
		}					
		ifActionPause();//lo refina la feature pause
		
	}
    
    private void ifActionPause(){}
    
    
    public final void destroy()//al hacer quit? R11
    {
        vthread.stop();
        video.stopmovie();
        buttonControl2.setLabel("PLAY");
        System.out.println("destroy");
		super.dispose();
    }
    
    void setButtonPlay(){//if feature play is included
    	original();
    	buttonControl2.setLabel("PLAY");
    }
    void setLabelPause(){  }//lo refina feature pause 
}
