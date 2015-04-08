/* Decompiled by Jasmine from Movie.class */
/* Originally compiled from Movie.java */

package MPEGDecoder;

import java.io.Serializable;
import java.net.URL;
import java.util.Vector;

public class Movie implements Serializable
{
    String title;
    String actor;
    String director;
    String description;
    Vector screen_shut;
    String stream_url;
    URL url;
    static final String default_string = "no info";

    public Movie(String string1, String string2)
    {
        this(string1, "no info", "no info", "no info", string2);
    }

    public Movie(String string1, String string2, String string3, String string4, String string5)
    {
        title = string1;
        actor = string2;
        director = string3;
        description = string4;
        stream_url = string5;
        screen_shut = new Vector();
        try
        {
            url = new URL(string5);
        }
        catch (Exception e)
        {
            url = null;
        }
    }

    public void addscreen_shut(String string)
    {
        screen_shut.addElement(string);
    }

    public String getactor()
    {
        return actor;
    }

    public String getdescription()
    {
        return description;
    }

    public String getdirector()
    {
        return director;
    }

    public Vector getscreen_shut()
    {
        return screen_shut;
    }

    public URL getstream_url()
    {
        return url;
    }

    public String gettitle()
    {
        return title;
    }
}
