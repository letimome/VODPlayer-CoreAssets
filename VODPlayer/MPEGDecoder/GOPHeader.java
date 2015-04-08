/* Decompiled by Jasmine from GOPHeader.class */
/* Originally compiled from GOPHeader.java */

package MPEGDecoder;

public class GOPHeader
{
    public boolean drop_frame_flag;
    public int time_code_hours;
    public int time_code_minutes;
    public boolean marker_bit;
    public int time_code_seconds;
    public int time_code_pictures;
    public boolean closed_gop;
    public boolean broken_link;

    public GOPHeader()
    {
    }
}
