/* Decompiled by Mocha from ListFrame.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import MPEGDecoder.VODClient;
import MPEGDecoder.ServerReq;
import MPEGDecoder.ServerSelect;


public class ListFrame extends Frame
{
	Button buttonControl1;
	ServerSelect serverselect;
	
	public void init(ServerReq serverReq, VODClient vODClient){
		original(serverReq,vODClient);
		buttonControl1 = new Button();
		serverselect = new ServerSelect(this);
	
	}
	 
	private void jbInit() throws Exception
	{
		 original();
		 buttonControl1.setBounds(new Rectangle(12, 197, 53, 39));
	     buttonControl1.setLabel("Servers");
	     buttonControl1.addActionListener(new ListFrameListener1(this));
	     bevelPanel2.add(buttonControl1, null);		   
   }
	
	  void buttonControl1_actionPerformed(ActionEvent actionEvent)
	  {
			// new server
	        serverselect.show();
	  }
	  public void changeserver(String string)
	    {
	        server.connect(string);
	        hostreset();
	        parent.clearmovie();
	    }
	 /* public ListFrame(ServerReq serverReq, VODClient vODClient)
	    {
		  //original(serverReq,vODClie);
		  //serverselect = new ServerSelect(this);
		  //hostreset();
	    }*/

	
	   
}