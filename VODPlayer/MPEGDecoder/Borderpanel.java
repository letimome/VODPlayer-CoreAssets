/* Decompiled by Mocha from Borderpanel.class */
/* Originally compiled from BorderPanel.java */

package MPEGDecoder;

import java.awt.*;

class BorderPanel extends Panel
{
    int border;
    Color col1;
    Color col2;
    Image screenimg;
    int imgw;
    int imgh;

    BorderPanel()
    {
        border = 5;
        col1 = Color.white;
        col2 = Color.black;
        screenimg = null;
        imgw = (getSize().width - screenimg.getWidth(this)) / 2;
        imgh = (getSize().height - screenimg.getHeight(this)) / 2;
    }

    BorderPanel(int i)
    {
        border = 5;
        col1 = Color.white;
        col2 = Color.black;
        border = i;
        screenimg = null;
    }

    BorderPanel(int i, Color color1, Color color2)
    {
        border = 5;
        col1 = Color.white;
        col2 = Color.black;
        border = i;
        col1 = color1;
        col2 = color2;
    }

    BorderPanel(Color color1, Color color2)
    {
        border = 5;
        col1 = Color.white;
        col2 = Color.black;
        col1 = color1;
        col2 = color2;
    }

    public final void clearscreen()
    {
        if (screenimg == null)
        {
            screenimg = createImage(getSize().width - 4, getSize().height - 4);
            imgw = (getSize().width - screenimg.getWidth(this)) / 2;
            imgh = (getSize().height - screenimg.getHeight(this)) / 2;
        }
        else
        {
            int i = screenimg.getWidth(this);
            int j = screenimg.getHeight(this);
            screenimg = createImage(i, j);
        }
        repaint();
    }

    public Insets insets()
    {
        return new Insets(border + 2, border + 2, border + 2, border + 2);
    }

    public final void paint(Graphics g)
    {
        super.paint(g);
        int i1 = getSize().width - 1;
        int j = getSize().height - 1;
        g.setColor(col1);
        for (int k = 0; k < border; k++)
        {
            g.drawLine(k, k, i1 - k, k);
            g.drawLine(k, k, k, j - k);
        }
        g.setColor(col2);
        for (int i2 = 0; i2 < border; i2++)
        {
            g.drawLine(i1 - i2, j - i2, i1 - i2, i2);
            g.drawLine(i1 - i2, j - i2, i2, j - i2);
        }
        if (screenimg != null)
            g.drawImage(screenimg, imgw, imgh, this);
    }

    public final void setImage(Image image)
    {
        screenimg = image;
        repaint();
    }

    public final void setImageSize(int i, int j)
    {
        imgw = getSize().width - i >> 1;
        imgh = getSize().height - j >> 1;
    }

    public final void update(Graphics g)
    {
        if (screenimg != null)
            g.drawImage(screenimg, imgw, imgh, this);
    }
}
