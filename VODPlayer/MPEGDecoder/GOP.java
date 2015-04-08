/* Decompiled by Jasmine from GOP.class */

package MPEGDecoder;

import java.awt.Label;

public class GOP
{
    int numofpic;
    BitInputStream instream;
    Picture picture;
    GOPHeader header;
    DataStore store;
    Label time;
    int type;
    static final int TIME = 0;
    static final int FRAME = 1;

    public GOP(BitInputStream bitInputStream, DataStore dataStore, Label label)
    {
        instream = bitInputStream;
        store = dataStore;
        header = new GOPHeader();
        dataStore.setgopheader(header);
        picture = new Picture(bitInputStream, dataStore);
        numofpic = 0;
        time = label;
        type = 0;
    }

    public void clearnumofpic()
    {
        numofpic = 0;
    }

    public int getnumofpic()
    {
        return numofpic;
    }

    public final boolean read_gop()
    {
        if (instream.readaligned(32) != 440)
            return false;
        header.drop_frame_flag = instream.readbit();
        header.time_code_hours = instream.readbits(5);
        header.time_code_minutes = instream.readbits(6);
        header.marker_bit = instream.readbit();
        header.time_code_seconds = instream.readbits(6);
        header.time_code_pictures = instream.readbits(6);
        if (type == 0)
            time.setText(header.time_code_hours + ":" + header.time_code_minutes + ":" + header.time_code_seconds);
        else
            time.setText(new StringBuffer("Frames   ").append(numofpic).toString());
        header.closed_gop = instream.readbit();
        header.broken_link = instream.readbit();
        instream.next_start_code();
        if (instream.nextaligned(32) == 437)
        {
            instream.flushaligned(32);
            while (instream.nextaligned(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        if (instream.nextaligned(32) == 434)
        {
            instream.readaligned(32);
            while (instream.nextaligned(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        do
        {
            picture.read_picture();
            numofpic++;
        }
        while (instream.nextaligned(32) == 256);
        return true;
    }

    public final void setstream(BitInputStream bitInputStream)
    {
        instream = bitInputStream;
        picture.setstream(bitInputStream);
        numofpic = 0;
    }
}
