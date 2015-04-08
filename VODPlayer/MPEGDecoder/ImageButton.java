/* Decompiled by Jasmine from ImageButton.class */
/* Originally compiled from ImageButton.java */

package MPEGDecoder;

import java.awt.*;

public class ImageButton extends Canvas
{
    Image im1;
    Image im2;
    int width;
    int height;
    int type;
    int border;
    Color col1;
    Color col2;
    boolean down;
    boolean enable;
    String label;
    public static final int NONE = 1;
    public static final int TOGGLE = 2;
    public static final int CLICK = 3;
    public static final int CTOGGLE = 4;

    ImageButton(int i)
    {
        this(null, null, i, 2);
    }

    ImageButton(Image image, int i)
    {
        this(image, image, i, 2);
    }

    ImageButton(Image image1, Image image2, int i)
    {
        this(image1, image2, i, 0);
    }

    ImageButton(Image image1, Image image2, int i, int j)
    {
        im1 = image1;
        im2 = image2;
        type = i;
        if (i == 1)
            border = 0;
        else if (i == 2)
            border = 1;
        else
            border = j;
        down = false;
        enable = true;
        col1 = new Color(220, 220, 220);
        col2 = new Color(50, 50, 50);
        if (image1 == null || image2 == null)
        {
            width = getSize().width;
            height = getSize().height;
            return;
        }
        width = Math.max(im1.getWidth(this), im2.getWidth(this));
        height = Math.max(im1.getHeight(this), im2.getHeight(this));
    }

    public boolean check()
    {
        return down;
    }

    public Image getImage()
    {
        return im1;
    }

    public String getLabel()
    {
        return label;
    }

    public boolean isEnabled()
    {
        return enable;
    }

    public Dimension minimumSize()
    {
        return new Dimension(width + border * 2, height + border * 2);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        if (!isEnabled())
            return false;
        if (type == 3)
        {
            down = true;
            paint(getGraphics());
            return true;
        }
        if (type == 1)
            return false;
        else
            return false;
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if (!isEnabled() || type == 1)
            return false;
        if (type == 2)
        {
            if (down == true)
                down = false;
            else
                down = true;
        }
        else if (type == 3)
        {
            down = false;
            paint(getGraphics());
        }
        else if (type == 4)
        {
            if (down)
                down = false;
            else
                down = true;
            paint(getGraphics());
        }
        getParent().postEvent(new Event(this, 1001, null));
        return true;
    }

    public void paint(Graphics g)
    {
        if (g == null)
            return;
        int i1 = getSize().width;
        int j1 = getSize().height;
        switch (type)
        {
        case 3:
        case 4:
            if (im1 == im2)
            {
                g.setColor(down ? col2 : col1);
                for (int k1 = 0; k1 < border; k1++)
                {
                    g.drawLine(k1, k1, i1 - k1, k1);
                    g.drawLine(k1, k1, k1, j1 - k1);
                }
                g.setColor(down ? col1 : col2);
                for (int k2 = 0; k2 < border; k2++)
                {
                    g.drawLine(i1 - k2 - 1, j1 - k2, i1 - k2 - 1, k2);
                    g.drawLine(i1 - k2, j1 - k2 - 1, k2, j1 - k2 - 1);
                }
            }
            break;

        case 2:
            if (down)
            {
                g.setColor(Color.red);
                for (int i2 = 0; i2 < border; i2++)
                {
                    g.drawLine(i2, i2, i1 - i2, i2);
                    g.drawLine(i2, i2, i2, j1 - i2);
                    g.drawLine(i1 - i2 - 1, j1 - i2, i1 - i2 - 1, i2);
                    g.drawLine(i1 - i2, j1 - i2 - 1, i2, j1 - i2 - 1);
                }
            }
            else
            {
                g.setColor(getBackground());
                for (int j2 = 0; j2 < border; j2++)
                {
                    g.drawLine(j2, j2, i1 - j2, j2);
                    g.drawLine(j2, j2, j2, j1 - j2);
                    g.drawLine(i1 - j2 - 1, j1 - j2, i1 - j2 - 1, j2);
                    g.drawLine(i1 - j2, j1 - j2 - 1, j2, j1 - j2 - 1);
                }
            }
            break;
        }
        if (im1 == null || im2 == null)
            return;
        if (isEnabled())
            g.drawImage(down ? im2 : im1, border, border, i1 - border * 2, j1 - border * 2, this);
        else
        {
            g.setColor(Color.lightGray);
            g.fillRect(border, border, i1 - border * 2, j1 - border * 2);
        }
    }

    public void paintAll(Graphics g)
    {
        paint(getGraphics());
    }

    public Dimension preferredSize()
    {
        return minimumSize();
    }

    public void repaint()
    {
        paint(getGraphics());
    }

    public void setButton(boolean flag)
    {
        down = flag;
        paint(getGraphics());
    }

    public void setEnable(boolean flag)
    {
        enable = flag;
    }

    public void setImage1(Image image)
    {
        im1 = image;
    }

    public void setImage2(Image image)
    {
        im2 = image;
    }

    public void setLabel(String string)
    {
        label = string;
    }

    public void update(Graphics g)
    {
        paint(getGraphics());
    }
}
