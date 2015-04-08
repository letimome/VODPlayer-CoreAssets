/* Decompiled by Jasmine from Video2.class */
/* Originally compiled from Video2.java */

package MPEGDecoder;

import java.io.*;
import java.awt.Label;
import java.net.URL;
import java.util.Date;

public class Video implements Runnable 
{
    BitInputStream instream;
    GOP gop;
    SequenceHeader header;
    DataStore store;
    URL movieurl;
    VODClient vod;
    Label fps;
    long starttime;
    //static float picture_rate[] = {0.000000f, 23.9760f, 24.0000f, 25.0000f, 29.9700f, 30.0000f, 50.0000f, 59.9400f, 60.0000f};
	
	static float picture_rate[] = {0.000000f, 12.00f, 24.0000f, 25.0000f, 0f, 15.0000f, 50.0000f, 59.9400f, 60.0000f};

    public Video(BorderPanel borderPanel, Label label1, Label label2, VODClient vODClient)
    {
        store = new DataStore(borderPanel);
        header = new SequenceHeader();
        store.setsequenceheader(header);
        gop = new GOP(instream, store, label2);
        instream = null;
        vod = vODClient;
        fps = label1;
    }
	
	public void destroy()
	{
		vod.dispose();
		if (instream != null) 
		{
			try {
			instream.reset(); 
			instream.close();
			} catch(Exception ex) { }
		}
		gop=null;
		header = null;
		store = null;
	}

    final boolean parse_sequence_header()
    {
        if (instream.readint() != 435)
            return false;
        header.horizontal_size = instream.readbits(12);
        header.mb_width = (header.horizontal_size + 15) / 16;
        header.vertical_size = instream.readbits(12);
        header.mb_height = (header.vertical_size + 15) / 16;
        header.pel_aspect_ratio = instream.readbits(4);
        header.picture_rate = picture_rate[instream.readbits(4)];
        header.picture_frequency = (int)(1.0F / header.picture_rate * 960.0);
        header.bit_rate = instream.readbits(18);
        header.marker_bit = instream.readbit();
        header.vbv_buffer_size = instream.readbits(10);
        header.constrained_parameter_flag = instream.readbit();
        header.load_intra_quantizer_matrix = instream.readbit();
        if (header.load_intra_quantizer_matrix)
        {
            System.out.println("load_intra_quantizer_matrix");
            for (int i = 0; i < 64; i++)
                store.intra_quantizer_matrix[DataStore.zig_zag_scan[i]] = instream.readbyte();
        }
        header.load_non_intra_quantizer_matrix = instream.readbit();
        if (header.load_non_intra_quantizer_matrix)
        {
            System.out.println("load_non_intra_quantizer_matrix");
            for (int j = 0; j < 64; j++)
                store.non_intra_quantizer_matrix[DataStore.zig_zag_scan[j]] = instream.readbyte();
            store.setnonintra(true);
        }
        else
            store.setnonintra(false);
        instream.next_start_code();
        if (instream.nextaligned(32) == 437)
        {
            instream.flushaligned(32);
            while (instream.nextbits(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        if (instream.nextaligned(32) == 434)
        {
            instream.flushaligned(32);
            while (instream.nextbits(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        store.newsquence();
        return true;
    }

    public final void run()
    {
       
    }

    public final void setratecontrol(boolean flag)
    {
        store.setratecontrol(flag);
    }

    public final void setvideostream(URL uRL)
    {
        movieurl = uRL;
    }

    public final void stopmovie()
    {
       
    }
}
