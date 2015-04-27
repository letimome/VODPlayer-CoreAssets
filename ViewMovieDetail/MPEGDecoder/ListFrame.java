/* Decompiled by Mocha from ListFrame.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import MPEGDecoder.Detail;
import MPEGDecoder.ServerReq;
import MPEGDecoder.VODClient;

public class ListFrame extends Frame {
	 Detail detail;

	Button buttonControl3;

	public void init(ServerReq serverReq, VODClient vODClient){
		original(serverReq,vODClient);
		buttonControl3 = new Button();
		detail = new Detail();
	}

	void buttonControl3_actionPerformed(ActionEvent actionEvent) {
		// show detail window
		String string = listControl1.getSelectedItem();
		if (string != null) {
			Movie movie = server.getmovie(string);
			if (movie != null) {
				detail.setmovie(movie);
				detail.show();
			}
		}
	}

	private void jbInit() throws Exception {
		original();
		buttonControl3.setBounds(new Rectangle(139, 196, 53, 39));
		buttonControl3.setLabel("Detail");
		buttonControl3.addActionListener(new ListFrameListener3(this));
		bevelPanel2.add(buttonControl3, null);
      
	}
	 

}