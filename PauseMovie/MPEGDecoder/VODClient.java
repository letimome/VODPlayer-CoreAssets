/* Decompiled by Mocha from Vodclient.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.*;
import java.applet.Applet;
import java.net.URL;
import java.awt.event.*;

import MPEGDecoder.VODClientListener2;
import MPEGDecoder.VODClientListener3;

public class VODClient extends Frame {//play, pause, resume
	
	
	 private void ifActionPause(){//lo refina el pause para el starMov
		
		original();
		
		if (buttonControl2.getLabel().equals("PAUSE")) {
			vthread.suspend();
			buttonControl2.setLabel("RESUME");
			return;//
		} 
		
		if(buttonControl2.getLabel().equals("RESUME")) {
			vthread.resume();
			buttonControl2.setLabel("PAUSE");
			return;//
		}
	}

	
	 void setLabelPause(){
		 original();
		 buttonControl2.setLabel("PAUSE");
	 }
	
}