/* Decompiled by Mocha from Vodclient.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.*;
import java.applet.Applet;
import java.net.URL;
import java.awt.event.*;

import MPEGDecoder.ListFrameListener2;
import MPEGDecoder.VODClientListener3;

public class VODClient extends Frame
{
    
    Button buttonControl1;//button Movie
    ListFrame listframe;

    public VODClient()
    {

        buttonControl1 = new Button();
        c = ListFrame.class;
    	c = ListFrameListener4.class;
    	c = ListFrameListener2.class;
        c = VODClientListener1.class;
        
   
    }

    public final void init(){
    	original();
    	listframe = new ListFrame(server, this);
    }
    void buttonControl1_actionPerformed(ActionEvent actionEvent)
    {
		//movies
        listframe.show();
    }

  
    public final void selectmovie(String string)
    {
        curmovie = server.getmovie(string);
        listframe.setVisible(false);
        if (curmovie == null)
            return;
        if (vthread != null && vthread.isAlive())
        {
            vthread.stop();
            video.stopmovie();
            setButtonPlay();
        }
        panel1.clearscreen();
        video.setvideostream(curmovie.getstream_url());
        label3.setText(new StringBuffer("Title : ").append(string).toString());
   	buttonControl2_actionPerformed(null);    
    }
    
    void setButtonPlay(){} 
    private final void jbInit()throws Exception{
        original();
        buttonControl1.setBounds(new Rectangle(5, 22, 60, 45));
        buttonControl1.setLabel("Movies");
        buttonControl1.addActionListener(new VODClientListener1(this));
        bevelPanel2.add(buttonControl1, null);
    }

  
}
