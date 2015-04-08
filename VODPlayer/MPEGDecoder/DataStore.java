/* Decompiled by Jasmine from DataStore.class */
/* Originally compiled from DataStore.java */

package MPEGDecoder;

import java.io.PrintStream;

public class DataStore
{
    SequenceHeader sequence=null;
    GOPHeader gop=null;
    PictureHeader picture=null;
    MacroHeader macro=null;
    long current_pf=0;
    long prev_disp_time=0;
    long wait_time=0;
    boolean rate_control=false;
	public int intra_quantizer_matrix[] = {8,16,19,22,26,27,29,34,16,16,22,24,27,29,34,37,19,22,26,27,29,34,34,38,22,22,26,27,29,34,37,40,22,26,27,29,32,35,40,48,26,27,29,32,35,40,48,58,26,27,29,34,38,46,56,69,27,29,35,38,46,56,69,83};
	public int non_intra_quantizer_matrix[] = {16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16,16};
    boolean non_intra=false;;
    int skip=0;
    int scount=0;
	int bp[][] = { {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0} };
	int clkblock[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	                       //   0 1 2 3 4 5 6 7 8 9 10  2 3 4 5 6 7 8 9 20  2 3 4 5 6 7 8 9 30  2 3 4 5 6 7 8 9 40  2 3 4 5 6 7 8 9 50  2 3 4 5 6 7 8 9 60  2 3                
	public int temp_matrix[] = {1,2,2,2,3,3,3,4,2,2,2,3,3,3,4,4,2,2,3,3,3,4,4,4,2,2,3,3,3,4,4,5,2,3,3,3,4,4,5,6,3,3,3,4,4,5,6,7,3,3,3,4,4,5,7,8,3,3,4,4,5,7,8,10};
    int old_quantizer=1;
    int MBA=0;
    int MBAMax=0;
    FrameImage current=null;
    java.awt.Frame frame=null;
    BorderPanel screen=null;
	public static byte zig_zag_scan[] = {0,1,8,16,9,2,3,10,17,24,32,25,18,11,4,5,12,19,26,33,40,48,41,34,27,20,13,6,7,14,21,28,35,42,49,56,57,50,43,36,29,22,15,23,30,37,44,51,58,59,52,45,38,31,39,46,53,60,61,54,47,55,62,63};
    public static byte default_intra_quantizer_matrix[] = { 8, 16, 19, 22, 26, 27, 29, 34, 16, 16, 22, 24, 27, 29, 34, 37, 19, 22, 26, 27, 29, 34, 34, 38, 22, 22, 26, 27, 29, 34, 37, 40, 22, 26, 27, 29, 32, 35, 40, 48, 26, 27, 29, 32, 35, 40, 48, 58, 26, 27, 29, 34, 38, 46, 56, 69, 27, 29, 35, 38, 46, 56, 69, 83 };
	public static byte frame_skip[][] = { {1,1,1,1,1,1,1,1,1,1,1,1}, {1,1,1,1,1,0,1,1,1,1,1,0}, {1,1,1,0,1,1,1,0,1,1,1,0}, {1,1,0,1,1,0,1,1,0,1,1,0}, {1,0,1,0,1,0,1,0,1,0,1,0}, {1,0,0,1,0,0,1,0,0,1,0,0}, {1,0,0,0,1,0,0,0,1,0,0,0}, {1,0,0,0,0,0,1,0,0,0,0,0}, {1,0,0,0,0,0,0,0,0,0,0,0} };
	
    public DataStore(BorderPanel borderPanel)
    {
        current = new FrameImage();
        intra_quantizer_matrix = new int[64];
        non_intra_quantizer_matrix = new int[64];
        non_intra = false;
        temp_matrix = new int[64];
        old_quantizer = 1;
        bp = new int[6][64];
        clkblock = new int[64];
        for (int i = 0; i < 64; i++)
        {
            intra_quantizer_matrix[i] = default_intra_quantizer_matrix[i];
            temp_matrix[i] = default_intra_quantizer_matrix[i] >> 3;
            non_intra_quantizer_matrix[i] = 16;
        }
        screen = borderPanel;
        skip = 0;
        scount = 0;
        wait_time = 0L;
        rate_control = false;
    }

    public void MBAinc(int i)
    {
        MBA += i;
        if (MBA > MBAMax)
            System.out.println("Error");
    }

    public final void change_quantierscale(int i)
    {
        if (old_quantizer == i)
            return;
        old_quantizer = i;
        for (int j = 0; j < 64; j++)
            temp_matrix[j] = i * intra_quantizer_matrix[j] >> 3;
    }

    public final void clearblock(int i)
    {
        System.arraycopy(clkblock, 0, bp[i], 0, 64);
    }

    public final void clearprevdisptime()
    {
    }

    public final void decodeblock()
    {
        int i = 16 * (MBA % sequence.mb_width);
        int j = 16 * (MBA / sequence.mb_width);
        boolean flag = (macro.macroblock_type & 1) == 0;
        if (flag)
            current.reconstruct(i, j, macro.macroblock_type, macro.PMV);
        for (int k = 0; k < 6; k++)
            if (macro.pattern_code[k] == 1)
                current.addblock(k, i, j, flag, bp[k]);
    }

    public final void exchangeIframe()
    {
        current.exchangepicture(1);
    }

    public final void exchangePframe()
    {
        current.exchangepicture(2);
    }

    public final void exchangeframe()
    {
        current.exchangepicture(picture.picture_coding_type);
    }

    public int getMBA()
    {
        return MBA;
    }

    public int getbackward_r_size()
    {
        return picture.backward_r_size;
    }

    public int[] getblock(int i)
    {
        return bp[i];
    }

    public int getforward_r_size()
    {
        return picture.forward_r_size;
    }

    public boolean getfull_pel_backward_vector()
    {
        return picture.full_pel_backward_vector;
    }

    public boolean getfull_pel_forward_vector()
    {
        return picture.full_pel_forward_vector;
    }

    public int getmacroblock_type()
    {
        return macro.macroblock_type;
    }

    public int getpicture_coding_type()
    {
        return picture.picture_coding_type;
    }

    public int getquant_scale()
    {
        return macro.slice_quantizer_scale;
    }

    public final void newframe()
    {
        MBA = -1;
        macro.macroblock_address_increment = 0;
        macro.PMV[0][0] = macro.PMV[0][1] = macro.PMV[1][0] = macro.PMV[1][1] = 0;
        macro.dc_dct_pred[0] = macro.dc_dct_pred[1] = macro.dc_dct_pred[2] = 0;
    }

    public final void newslice(int i)
    {
        macro.MBAreset = false;
    }

    public final void newsquence()
    {
        if (MBAMax == sequence.mb_width * sequence.mb_height)
            return;
        MBAMax = sequence.mb_width * sequence.mb_height;
        current.newsequence(sequence.horizontal_size, sequence.vertical_size);
        screen.setImageSize(sequence.horizontal_size, sequence.vertical_size);
    }

    public final void resetMBA()
    {
        macro.MBAreset = true;
        macro.PMV[0][0] = macro.PMV[0][1] = macro.PMV[1][0] = macro.PMV[1][1] = 0;
        macro.dc_dct_pred[0] = macro.dc_dct_pred[1] = macro.dc_dct_pred[2] = 0;
    }

    public final void resetpastdct()
    {
        macro.dc_dct_pred[0] = macro.dc_dct_pred[1] = macro.dc_dct_pred[2] = 0;
    }

    public final void setgopheader(GOPHeader gOPHeader)
    {
        gop = gOPHeader;
    }

    public final void setmacroheader(MacroHeader macroHeader)
    {
        macro = macroHeader;
    }

    public final void setnonintra(boolean flag)
    {
        non_intra = flag;
    }

    public final void setpictureheader(PictureHeader pictureHeader)
    {
        picture = pictureHeader;
    }

    public final void setratecontrol(boolean flag)
    {
        rate_control = flag;
    }

    public final void setsequenceheader(SequenceHeader sequenceHeader)
    {
        sequence = sequenceHeader;
    }

    public final void showIframe()
    {
        if (scount == 0)
            prev_disp_time = System.currentTimeMillis();
        if (wait_time > 0L)
        {
            try
            {
                Thread.currentThread();
                Thread.sleep(wait_time);
            }
            catch (InterruptedException e)
            {
            }
        }
        if (frame_skip[skip][scount] == 1)
            screen.setImage(current.getIImage());
        if (rate_control && ++scount > 11)
        {
            current_pf = (System.currentTimeMillis() - prev_disp_time) / 12;
            if (wait_time > 0L)
                wait_time = wait_time + sequence.picture_frequency - current_pf;
            else if ((long)sequence.picture_frequency < current_pf)
                if (skip < 8)
                    skip++;
            else if (skip == 0)
                wait_time = (long)sequence.picture_frequency - current_pf;
            else if ((long)sequence.picture_frequency - current_pf > 4)
                skip--;
            scount = 0;
        }
    }

    public final void showPframe()
    {
        if (scount == 0)
            prev_disp_time = System.currentTimeMillis();
        if (wait_time > 0L)
        {
            try
            {
                Thread.currentThread();
                Thread.sleep(wait_time);
            }
            catch (InterruptedException e)
            {
            }
        }
        if (frame_skip[skip][scount] == 1)
            screen.setImage(current.getPImage());
        if (rate_control && ++scount > 11)
        {
            current_pf = (System.currentTimeMillis() - prev_disp_time) / 12;
            if (wait_time > 0L)
                wait_time = wait_time + sequence.picture_frequency - current_pf;
            else if ((long)sequence.picture_frequency < current_pf)
			{
                if (skip < 8)
                    skip++;
			}
            else if (skip == 0)
                wait_time = (long)sequence.picture_frequency - current_pf;
            else if ((long)sequence.picture_frequency - current_pf > 4)
                skip--;
            scount = 0;
        }
    }

    public final void showframe()
    {
        if (scount == 0)
            prev_disp_time = System.currentTimeMillis();
        if (wait_time > 0L)
        {
            try
            {
                Thread.currentThread();
                Thread.sleep(wait_time);
            }
            catch (InterruptedException e)
            {
            }
        }
        if (frame_skip[skip][scount] == 1)
            screen.setImage(current.getImage());
        if (rate_control && ++scount > 11)
        {
            current_pf = (System.currentTimeMillis() - prev_disp_time) / 12;
            if (wait_time > 0L)
                wait_time = wait_time + sequence.picture_frequency - current_pf;
            else if ((long)sequence.picture_frequency < current_pf)
                if (skip < 8)
                    skip++;
            else if (skip == 0)
                wait_time = (long)sequence.picture_frequency - current_pf;
            else if ((long)sequence.picture_frequency - current_pf > 4)
                skip--;
            scount = 0;
        }
    }

    public final void skipblock()
    {
        int i = 16 * (MBA % sequence.mb_width);
        int j = 16 * (MBA / sequence.mb_width);
        current.reconstruct(i, j, macro.macroblock_type, macro.PMV);
    }

    public final void startnewpicture(int i)
    {
        if (i == 3)
            return;
        current.startnewpicture(i);
    }
}
