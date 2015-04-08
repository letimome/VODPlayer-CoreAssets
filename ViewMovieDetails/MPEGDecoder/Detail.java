/* Decompiled by Mocha from Detail.class */
/* Originally compiled from Detail.java */

package MPEGDecoder;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Detail extends Frame
{
    BorderPanel bevelPanel1;
    BorderPanel bevelPanel2;
    Button button1;
    Label label1;
    Label label2;
    Label label3;
    Label label4;
    Label label5;

    public Detail()
    {
        bevelPanel1 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        bevelPanel2 = new BorderPanel(1, new Color(220, 220, 220), new Color(50, 50, 50));
        button1 = new Button();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        label5 = new Label();
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void button1_actionPerformed(ActionEvent actionEvent)
    {
        setVisible(false);
    }

    private final void jbInit()
        throws Exception
    {
        setSize(new Dimension(347, 293));
        setLayout(null);
        button1.setBounds(new Rectangle(93, 226, 157, 30));
        button1.setLabel("OK");
        button1.addActionListener(new DetailListener1(this));
        label1.setFont(new Font("Dialog", 3, 15));
        label1.setBounds(new Rectangle(17, 6, 292, 25));
        label1.setText("Title :");
        label2.setFont(new Font("Dialog", 0, 14));
        label2.setBounds(new Rectangle(24, 16, 265, 24));
        label2.setText("Actor :");
        label3.setFont(new Font("Dialog", 0, 14));
        label3.setBounds(new Rectangle(22, 39, 269, 31));
        label3.setText("Director :");
        label4.setFont(new Font("Dialog", 0, 14));
        label4.setBounds(new Rectangle(21, 67, 288, 24));
        label4.setText("Description :");
        label5.setFont(new Font("Dialog", 0, 14));
        label5.setBounds(new Rectangle(37, 87, 280, 31));
        setBackground(Color.lightGray);
        setTitle("Movie Information");
        bevelPanel1.setLayout(null);
        bevelPanel1.setBounds(new Rectangle(8, 30, 324, 40));
        bevelPanel2.setLayout(null);
        bevelPanel2.setBounds(new Rectangle(7, 77, 324, 139));
        add(bevelPanel1, null);
        bevelPanel1.add(label1, null);
        add(bevelPanel2, null);
        bevelPanel2.add(label2, null);
        bevelPanel2.add(label3, null);
        bevelPanel2.add(label4, null);
        bevelPanel2.add(label5, null);
        add(button1, null);
    }

    public final void setmovie(Movie movie)
    {
        label1.setText(new StringBuffer("Title : ").append(movie.gettitle()).toString());
        label2.setText(new StringBuffer("Actor : ").append(movie.getactor()).toString());
        label3.setText(new StringBuffer("Director : ").append(movie.getdirector()).toString());
        label5.setText(movie.getdescription());
    }
}
