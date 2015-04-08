/* Decompiled by Mocha from ServerSelect.class */
/* Originally compiled from ServerSelect.java */

//
//
//
//

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;

public class ServerSelect extends Frame
{
    Panel panel1;
    BorderPanel bevelPanel1;
    Label label2;
    Button button1;
    BorderPanel bevelPanel2;
    TextField textField1;
    Label label1;
    Button button2;
    ListFrame listframe;

    public ServerSelect(ListFrame listFrame)
    {
        panel1 = new Panel();
        bevelPanel1 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        label2 = new Label();
        button1 = new Button();
        bevelPanel2 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        textField1 = new TextField();
        label1 = new Label();
        button2 = new Button();
        try
        {
            jbInit();
            pack();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        listframe = listFrame;
    }

    void button1_actionPerformed(ActionEvent actionEvent)
    {
        setVisible(false);
        String string = textField1.getText();
        textField1.setText("");
        listframe.changeserver(string);
    }

    void button2_actionPerformed(ActionEvent actionEvent)
    {
        setVisible(false);
    }

    void jbInit()
        throws Exception
    {
        setSize(new Dimension(344, 225));
        setBackground(Color.lightGray);
        setLayout(null);
        panel1.setBackground(Color.lightGray);
        panel1.setBounds(new Rectangle(7, 30, 328, 167));
        panel1.setSize(new Dimension(338, 249));
        bevelPanel1.setLayout(null);
        bevelPanel1.setBounds(new Rectangle(6, 2, 316, 41));
        label2.setFont(new Font("Dialog", 3, 15));
        label2.setBounds(new Rectangle(7, 7, 294, 26));
        label2.setText("Change VOD Server");
        button1.setBounds(new Rectangle(43, 127, 111, 29));
        button1.setLabel("OK");
        bevelPanel2.setLayout(null);
        bevelPanel2.setBounds(new Rectangle(6, 51, 315, 62));
        button1.addActionListener(new ServerSelectListener1(this));
        label1.setFont(new Font("Dialog", 0, 14));
        label1.setBounds(new Rectangle(7, 15, 96, 28));
        label1.setText("Server Name :");
        button2.setBounds(new Rectangle(177, 125, 111, 29));
        button2.setLabel("CANCEL");
        button2.addActionListener(new  ServerSelectListener2(this));
        textField1.setFont(new Font("Dialog", 0, 14));
        textField1.setBounds(new Rectangle(105, 16, 194, 25));
        textField1.addActionListener(new ServerSelectListener3(this));
        panel1.setLayout(null);
        add(panel1);
        panel1.add(bevelPanel1, null);
        bevelPanel1.add(label2, null);
        panel1.add(button1, null);
        panel1.add(bevelPanel2, null);
        bevelPanel2.add(textField1, null);
        bevelPanel2.add(label1, null);
        panel1.add(button2, null);
    }

    void textField1_actionPerformed(ActionEvent actionEvent)
    {
        setVisible(false);
        String string = textField1.getText();
        textField1.setText("");
        listframe.changeserver(string);
    }
}
