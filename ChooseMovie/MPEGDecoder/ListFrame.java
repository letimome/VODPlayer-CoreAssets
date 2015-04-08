/* Decompiled by Mocha from ListFrame.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import MPEGDecoder.ServerReq;
import MPEGDecoder.VODClient;

public class ListFrame extends Frame // movie list frame
{
	BorderPanel bevelPanel1;
	BorderPanel bevelPanel2;
	List listControl1;
	ServerReq server;

	Button buttonControl2;// select movie

	Button buttonControl4;// cancel, exit movie list
	Label label1;

	Vector movielist;
	VODClient parent;

	public ListFrame(ServerReq serverReq, VODClient vODClient) {
		
		init(serverReq,vODClient);
		
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init(ServerReq serverReq, VODClient vODClient) {
		bevelPanel1 = new BorderPanel(1, new Color(220, 220, 220), new Color(
				50, 50, 50));
		bevelPanel2 = new BorderPanel(1, new Color(220, 220, 220), new Color(
				50, 50, 50));
		listControl1 = new List();

		buttonControl2 = new Button();

		buttonControl4 = new Button();
		label1 = new Label();
		movielist = null;
		server = serverReq;
		parent = vODClient;
		hostreset();	
		
	}

	void buttonControl2_actionPerformed(ActionEvent actionEvent) {
		// select movie
		String string = listControl1.getSelectedItem();
		if (string != null)
			parent.selectmovie(string);
	}
	 public void hostreset()
	    {
	        if (server.isConnected())
	            label1.setText(new StringBuffer("Connected to ").append(server.gethost()).toString());
	        else
	            label1.setText("Disconnected");
	        listControl1.removeAll();
	        if (!server.isConnected())
	            return;
	        movielist = server.getmovielist();
	        if (movielist == null)
	            return;
	        for (Enumeration enumeration = movielist.elements(); enumeration.hasMoreElements(); )
	        {
	            String string = (String)enumeration.nextElement();
	            listControl1.addItem(string);
	        }
	    }

	void buttonControl4_actionPerformed(ActionEvent actionEvent) {
		// cancel
		setVisible(false);
	}

	private void jbInit() throws Exception {
		setBackground(Color.lightGray);
		setSize(new Dimension(284, 360));
		setTitle("Movie List");
		bevelPanel1.setLayout(null);
		bevelPanel1.setBounds(new Rectangle(9, 30, 262, 37));
		bevelPanel2.setLayout(null);
		bevelPanel2.setBounds(new Rectangle(9, 72, 262, 249));
		listControl1.setBounds(new Rectangle(15, 15, 231, 171));

		buttonControl2.setBounds(new Rectangle(76, 196, 53, 39));
		buttonControl2.setLabel("Select");
		buttonControl2.addActionListener(new ListFrameListener2(this));

		buttonControl4.setBounds(new Rectangle(199, 196, 53, 39));
		buttonControl4.setLabel("Cancel");
		buttonControl4.addActionListener(new ListFrameListener4(this));
		label1.setFont(new Font("Dialog", 2, 14));
		label1.setBounds(new Rectangle(9, 4, 247, 28));
		// parent.setLayout(null);
		setLayout(null);
		add(bevelPanel1, null);
		bevelPanel1.add(label1, null);
		add(bevelPanel2, null);
		bevelPanel2.add(listControl1, null);
		bevelPanel2.add(buttonControl4, null);

		bevelPanel2.add(buttonControl2, null);
		

	}
}
