/* Decompiled by Jasmine from Video2.class */
/* Originally compiled from Video2.java */

package MPEGDecoder;

import java.io.*;
import java.awt.Label;
import java.net.URL;
import java.util.Date;

public class Video implements Runnable 
{
    
	public final void run()
    {
        if (instream == null)
        {
            if (movieurl == null)
                return;
            try
            {
				if (movieurl.getProtocol().toLowerCase().equals("file"))
				{
					String fileName = movieurl.getHost()+":"+movieurl.getFile();
					instream = new BitInputStream(new FileInputStream(fileName));
				}
				else					
	                instream = new BitInputStream(movieurl.openStream());
				
                gop.setstream(instream);
            }catch (IOException e1)
            {
                System.out.println("Error during opening stream");
                return;
            }
            while(true){
                starttime = System.currentTimeMillis();
                gop.clearnumofpic();
                store.clearprevdisptime();
                instream.next_start_code();
                do
                {
                    parse_sequence_header();
                    do
                        gop.read_gop();
                    while (instream.nextaligned(32) == 440);
                }
                while (instream.nextaligned(32) == 435);
                Float floatx = new Float((float)(1000 * gop.getnumofpic()) / (System.currentTimeMillis() - starttime));
                String string = floatx.toString();
                if (string.length() > 5)
                    string = string.substring(0, 5);
                fps.setText(string + " fps");
               try{
		if (movieurl.getProtocol().toLowerCase().equals("file")){
			String fileName = movieurl.getHost()+":"+movieurl.getFile();
			instream = new BitInputStream(new FileInputStream(fileName));
		}
		else	instream = new BitInputStream(movieurl.openStream());
	        gop.setstream(instream);
	        }
	        catch (IOException e2){
	           System.out.println("Error");
	           stopmovie();
	           vod.stopmovie();
	           return;}
        }
      
    }

   
}
