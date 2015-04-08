/* Decompiled by Jasmine from PictureHeader.class */
/* Originally compiled from PictureHeader.java */

package MPEGDecoder;

public class PictureHeader
{
    public int temporal_reference;
    public int picture_coding_type;
    public int vbv_delay;
    public boolean full_pel_forward_vector;
    public int forward_f_code;
    public int forward_r_size;
    public int forward_f;
    public boolean full_pel_backward_vector;
    public int backward_f_code;
    public int backward_r_size;
    public int backward_f;
    public boolean extra_bit_picture;
    public int extra_information_picture;

    public PictureHeader()
    {
    }
}
