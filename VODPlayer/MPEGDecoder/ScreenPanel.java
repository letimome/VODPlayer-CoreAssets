/* Decompiled by Jasmine from ScreenPanel.class */
/* Originally compiled from ScreenPanel.java */

package MPEGDecoder;

import java.awt.*;

public class ScreenPanel extends Panel
{
    BorderLayout borderLayout1;
    Color col1;
    Color col2;
    Image screenimg;
    int imgw;
    int imgh;

    public ScreenPanel()
    {
        borderLayout1 = new BorderLayout();
        col1 = Color.white;
        col2 = Color.black;
        screenimg = null;
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public final void clearscreen()
    {
        if (screenimg == null)
        {
            screenimg = createImage(352, 240);
            imgw = (getSize().width - 352) / 2;
            imgh = (getSize().height - 244) / 2;
        }
        else
        {
            int i = screenimg.getWidth(this);
            int j = screenimg.getHeight(this);
            screenimg = createImage(i, j);
        }
        Graphics g = screenimg.getGraphics();
        g.setColor(new Color(220, 220, 190));
        g.fillRect(0, 0, 352, 240);
        repaint();
    }

    public void drawBar(Graphics g, int i1, int j1, int k1, int i2)
    {
        int j2;
        int k2 = i2 >> 2;
        int i3 = i2;
        int j3 = i2 >> 1;
        int k3 = (int)((float)i3 * 0.8);
        int i4 = (int)((float)j3 * 0.8);
        int j4 = (int)((float)i3 * 0.1);
        int k4 = (int)((float)j3 * 0.1);
        int i5 = i1 + j3;
        int j5 = j1 + (i3 - k3 >> 1);
        float f = 50.0f / k2;
        for (j2 = 0; j2 < k2; j2++)
        {
            int k5 = (int)((float)j2 * f);
            g.setColor(new Color(k5, k5, k5));
            g.fillArc(i1 + j2, j1, j3, i3, 0, 360);
        }
        g.setColor(new Color(100, 100, 100));
        g.fillArc(i1 + j2, j1, j3, i3, 0, 360);
        k1 = k1 - k2 - j3;
        for (j2 = k2; j2 > 0; j2--)
        {
            int i6 = (int)((float)j2 * f);
            g.setColor(new Color(i6, i6, i6));
            g.fillArc(i1 + k1 + j2, j1, j3, i3, 0, 360);
        }
        g.setColor(new Color(100, 100, 100));
        g.fillArc(i1 + k1 + j2, j1, j3, i3, 0, 360);
        int k6 = j5 + (k3 >> 1);
        f = 100 / (k3 >> 1);
        int j6 = 10;
        for (j2 = j5; j2 < j5 + k3; j2++)
        {
            int i7;
            if (k6 > j2)
            {
                j6 += (int)f;
                i7 = (int)(2.0 * Math.sqrt((double)(j2 - j5)));
            }
            else
            {
                j6 -= (int)f;
                i7 = (int)(2.0 * Math.sqrt((double)(k3 + j5 - j2)));
            }
            g.setColor(new Color(j6, j6, j6));
            g.fillRect(i5 - i7, j2, k1 - k2 + (i7 << 1), 1);
        }
        if (screenimg != null)
            g.drawImage(screenimg, imgw, imgh, this);
    }

    void jbInit()
        throws Exception
    {
        setLayout(borderLayout1);
    }

    public void paint(Graphics g)
    {
        drawBar(g, 0, 0, 400, 30);
        drawBar(g, 0, 257, 400, 30);
        g.setColor(Color.black);
        g.fillRect(15, 20, 5, 247);
        g.fillRect(380, 20, 5, 247);
        g.setColor(new Color(220, 220, 190));
        g.fillRect(20, 20, 360, 247);
    }

    public final void setImage(Image image)
    {
        screenimg = image;
        repaint();
    }

    public final void setImageSize(int i, int j)
    {
        imgw = getSize().width - i >> 1;
        imgh = getSize().height - j - 4 >> 1;
    }

    public final void update(Graphics g)
    {
        if (screenimg != null)
            g.drawImage(screenimg, imgw, imgh, this);
    }
}
