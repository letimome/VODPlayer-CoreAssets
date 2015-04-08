/* Decompiled by Jasmine from MacroHeader.class */
/* Originally compiled from MacroHeader.java */

package MPEGDecoder;

public class MacroHeader
{
    public int macroblock_address_increment;
    public int macroblock_type;
    public int quantizer_scale;
    public int motion_horizontal_foward_code;
    public int motion_horizontal_foward_r;
    public int motion_vertical_foward_code;
    public int motion_vertical_foward_r;
    public int motion_horizontal_backward_code;
    public int motion_horizontal_backward_r;
    public int motion_vertical_backward_code;
    public int motion_vertical_backword_r;
    public int coded_block_pattern;
    public int slice_start_code;
    public int slice_quantizer_scale;
    public boolean extra_bit_slice;
    public int extra_information_slice;
    public int dc_dct_pred[];
    public int PMV[][];
    public int pattern_code[];
    public boolean MBAreset;

    public MacroHeader()
    {
        PMV = new int[2][2];
        pattern_code = new int[6];
        dc_dct_pred = new int[3];
        MBAreset = false;
    }
}
