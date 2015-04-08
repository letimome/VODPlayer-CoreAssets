/* Decompiled by Jasmine from SequenceHeader.class */
/* Originally compiled from SequenceHeader.java */

package MPEGDecoder;

public class SequenceHeader
{
    public int horizontal_size;
    public int vertical_size;
    public int mb_width;
    public int mb_height;
    public int pel_aspect_ratio;
    public float picture_rate;
    public int picture_frequency;
    public int bit_rate;
    public boolean marker_bit;
    public int vbv_buffer_size;
    public boolean constrained_parameter_flag;
    public boolean load_intra_quantizer_matrix;
    public boolean load_non_intra_quantizer_matrix;

    public SequenceHeader()
    {
    }
}
