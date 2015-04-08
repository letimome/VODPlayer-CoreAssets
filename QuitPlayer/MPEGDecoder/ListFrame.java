/* Decompiled by Mocha from ListFrame.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import MPEGDecoder.ListFrameListener4;
import MPEGDecoder.ServerReq;
import MPEGDecoder.VODClient;

public class ListFrame extends Frame {
	Button buttonControl4;

	public void init(ServerReq serverReq, VODClient vODClient){
		original(serverReq,vODClient);
		buttonControl4 = new Button();
	}

	private void jbInit() throws Exception {
		original();

		buttonControl4.setBounds(new Rectangle(199, 196, 53, 39));
		buttonControl4.setLabel("Cancel");
		buttonControl4.addActionListener(new ListFrameListener4(this));

	}

	void buttonControl4_actionPerformed(ActionEvent actionEvent) {
		// cancel
		setVisible(false);
	}

}