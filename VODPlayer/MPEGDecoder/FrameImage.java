/* Decompiled by Jasmine from FrameImage.class */
/* Originally compiled from FrameImage.java */

package MPEGDecoder;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class FrameImage extends Component
{
    int Y[];
    int Cb[];
    int Cr[];
    int IY[];
    int ICb[];
    int ICr[];
    int PY[];
    int PCb[];
    int PCr[];
    int tempY[];
    int tempC[];
    int imagepixel[];
    int ysize;
    int csize;
    int mb_width;
    int mb_height;
    int width;
    int height;
    int chrom_width;
    IDCT idct;
    boolean prev[];
    MemoryImageSource source;
    Image currentImg;
    static final int MAXYSIZE = 84480;
    static final int MAXCSIZE = 21120;
    static int clp[];
    static int clpR[];
    static int clpG[];
    static int clpB[];
    static int taY[];
    static int taCr1[];
    static int taCr2[];
    static int taCb1[];
    static int taCb2[];

    static 
    {
        clp = new int[2048];
        clpR = new int[2048];
        clpG = new int[2048];
        clpB = new int[2048];
        taY = new int[256];
        taCb1 = new int[256];
        taCb2 = new int[256];
        taCr1 = new int[256];
        taCr2 = new int[256];
        for (int i = -768; i < 1280; i++)
            clp[i + 768] = (i < 0) ? 0 : ((i > 255) ? 255 : i);
        for (int j = -768; j < 1280; j++)
        {
            clpR[j + 768] = (j < 0) ? 0 : ((j > 255) ? 16711680 : (j << 16));
            clpG[j + 768] = (j < 0) ? 0 : ((j > 255) ? 65280 : (j << 8));
            clpB[j + 768] = (j < 0) ? -16777216 : ((j > 255) ? -16776961 : (-16777216 | j));
        }
        for (int k = 0; k < 256; k++)
        {
            taY[k] = (int)(1.164 * (k - 16)) + 768;
            taCr1[k] = (int)(1.596 * (k - 128));
            taCr2[k] = (int)(0.813 * (128 - k));
            taCb1[k] = (int)(0.392 * (128 - k));
            taCb2[k] = (int)(2.017 * (k - 128));
        }
    }

    public FrameImage()
    {
        idct = new IDCT();
        prev = new boolean[2];
        Y = new int[84480];
        Cb = new int[21120];
        Cr = new int[21120];
        IY = new int[84480];
        ICb = new int[21120];
        ICr = new int[21120];
        PY = new int[84480];
        PCb = new int[21120];
        PCr = new int[21120];
        tempY = new int[84480];
        tempC = new int[21120];
        imagepixel = new int[84480];
    }

    final void addblock(int i1, int j1, int k1, boolean flag1, int an1[])
    {
        int k2;
        int j3;
        int an2[];
        int k3 = 768;
        if (!flag1)
            k3 += 128;
        //boolean flag2 = (i1 < 4) ? false : ((i1 & 1) + 1);
		boolean flag2 = (i1 < 4) ? false : true;		//TODO: changed blod to true
        if (!flag2)
        {
            j3 = width * (k1 + ((i1 & 2) << 2)) + j1 + ((i1 & 1) << 3);
            k2 = width - 7;
            an2 = Y;
        }
        else
        {
            j3 = chrom_width * ((k1 >> 1) + ((i1 & 2) << 2)) + (j1 >> 1);
            k2 = chrom_width - 7;
            if (i1 == 4)
                an2 = Cb;
            else
                an2 = Cr;
        }
        IDCT.fast_idct64_rowcol(an1);
        int i3 = 0;
        if (flag1)
        {
            for (int i2 = 0; i2 < 8; i2++)
            {
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3++] + k3];
                an2[j3] = clp[an1[i3++] + an2[j3] + k3];
                j3 += k2;
            }
        }
        else
        {
            for (int j2 = 0; j2 < 8; j2++)
            {
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3++] = clp[an1[i3++] + k3];
                an2[j3] = clp[an1[i3++] + k3];
                j3 += k2;
            }
        }
    }

    final void average(int i1, int j1, int an1[], int an2[])
    {
        int k = width * j1 + i1;
        int i2 = width - 15;
        for (int j2 = 0; j2 < 16; j2++)
        {
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            k += i2;
        }
    }

    final void chrom_average(int i1, int j1, int an1[], int an2[], int an3[], int an4[])
    {
        i1 >>= 1;
        j1 >>= 1;
        int k = chrom_width * j1 + i1;
        int i2 = chrom_width - 7;
        for (int j2 = 0; j2 < 8; j2++)
        {
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k++] >> 1;
            an1[k] = an1[k] + an2[k] >> 1;
            an3[k] = an3[k] + an4[k] >> 1;
            k += i2;
        }
    }

    public final void exchangepicture(int i)
    {
        if (i == 1)
        {
			if (prev[1] == true)			//TODO: changed 1 to true
            {
                int an1[] = IY;
                IY = PY;
                PY = Y;
                Y = an1;
                an1 = ICb;
                ICb = PCb;
                PCb = Cb;
                Cb = an1;
                an1 = ICr;
                ICr = PCr;
                PCr = Cr;
                Cr = an1;
            }
            else
            {
                int an2[] = IY;
                IY = Y;
                Y = an2;
                an2 = ICb;
                ICb = Cb;
                Cb = an2;
                an2 = ICr;
                ICr = Cr;
                Cr = an2;
                prev[0] = true;				//TODO: changed 1 to true
            }
        }
        else if (i == 2)
        {
            int an3[] = PY;
            PY = Y;
            Y = an3;
            an3 = PCb;
            PCb = Cb;
            Cb = an3;
            an3 = PCr;
            PCr = Cr;
            Cr = an3;
            prev[1] = true;			//TODO: change 1 to true
        }
    }

    public final Image getIImage()
    {
        getImage(IY, ICr, ICb);
        return currentImg;
    }

    public final Image getImage()
    {
        getImage(Y, Cr, Cb);
        return currentImg;
    }

    public final void getImage(int an1[], int an2[], int an3[])
    {
		int k1 = 0;			//TODO: var + value did not exist
        int k2 = width >> 1;
        int i3 = height >> 1;
        int j2 = width;
        int i2 = 0;
        for (int i1 = 0; i1 < i3; i1++)
        {
            for (int j1 = 0; j1 < k2; j1++)
            {
                int k3 = taCr1[an2[i2]];
                int i4 = taCb2[an3[i2]];
                int j4 = taCr2[an2[i2]] + taCb1[an3[i2]];
                int j3 = taY[an1[k1]];
                imagepixel[k1++] = clpR[j3 + k3] | clpG[j3 + j4] | clpB[j3 + i4];
                j3 = taY[an1[k1]];
                imagepixel[k1++] = clpR[j3 + k3] | clpG[j3 + j4] | clpB[j3 + i4];
                j3 = taY[an1[j2]];
                imagepixel[j2++] = clpR[j3 + k3] | clpG[j3 + j4] | clpB[j3 + i4];
                j3 = taY[an1[j2]];
                imagepixel[j2++] = clpR[j3 + k3] | clpG[j3 + j4] | clpB[j3 + i4];
                i2++;
            }
            k1 += width;
            j2 += width;
        }
        source.newPixels(0, 0, width, height);
    }

    public final Image getPImage()
    {
        getImage(PY, PCr, PCb);
        return currentImg;
    }

    public void newsequence(int i, int j)
    {
        ysize = i * j;
        csize = ysize >> 2;
        width = i;
        chrom_width = i >> 1;
        height = j;
        mb_width = i + 15 >> 4;
        mb_height = j + 15 >> 4;
        prev[0] = false;						//TODO: changed 0 to false
        prev[1] = false;			//TODO: changed 0 to false
        source = new MemoryImageSource(i, j, imagepixel, 0, i);
        source.setAnimated(true);
        currentImg = createImage(source);
    }

    final void recon(int i1, int j1, int an1[], int an2[], int an3[])
    {
        int k1 = an3[0] >> 1;
        int i2 = an3[1] >> 1;
        int k2 = width * j1 + i1;
        int i3 = k2 + width * i2 + k1;
        int j2 = width - 15;
        for (int j3 = 0; j3 < 16; j3++)
        {
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2++] = an1[i3++];
            an2[k2] = an1[i3];
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_chrom(int i1, int j1, int an1[], int an2[], int an3[], int an4[], int an5[])
    {
        int k1 = an5[0] >> 2;
        int i2 = an5[1] >> 2;
        i1 >>= 1;
        j1 >>= 1;
        int k2 = chrom_width * j1 + i1;
        int i3 = k2 + chrom_width * i2 + k1;
        int j2 = chrom_width - 7;
        for (int j3 = 0; j3 < 8; j3++)
        {
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2++] = an2[i3++];
            an3[k2] = an1[i3];
            an4[k2] = an2[i3];
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_chrom_down(int i1, int j1, int an1[], int an2[], int an3[], int an4[], int an5[])
    {
        int k1 = an5[0] >> 2;
        int i2 = an5[1] >> 2;
        i1 >>= 1;
        j1 >>= 1;
        int k2 = chrom_width * j1 + i1;
        int i3 = k2 + chrom_width * i2 + k1;
        int j2 = chrom_width - 7;
        for (int k3 = 0; k3 < 8; k3++)
        {
            int j3 = i3 + chrom_width;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2++] = an2[i3++] + an2[j3++] >> 1;
            an3[k2] = an1[i3] + an1[j3] >> 1;
            an4[k2] = an2[i3] + an2[j3] >> 1;
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_chrom_right(int i1, int j1, int an1[], int an2[], int an3[], int an4[], int an5[])
    {
        int k1 = an5[0] >> 2;
        int i2 = an5[1] >> 2;
        i1 >>= 1;
        j1 >>= 1;
        int k2 = chrom_width * j1 + i1;
        int i3 = k2 + chrom_width * i2 + k1;
        int j2 = chrom_width - 8;
        for (int j3 = 0; j3 < 8; j3++)
        {
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            an3[k2] = an1[i3] + an1[i3 + 1] >> 1;
            an4[k2++] = an2[i3++] + an2[i3] >> 1;
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_chrom_rightdown(int i1, int j1, int an1[], int an2[], int an3[], int an4[], int an5[])
    {
        int k1 = an5[0] >> 2;
        int i2 = an5[1] >> 2;
        i1 >>= 1;
        j1 >>= 1;
        int k2 = chrom_width * j1 + i1;
        int i3 = k2 + chrom_width * i2 + k1;
        int j2 = chrom_width - 8;
        for (int k3 = 0; k3 < 8; k3++)
        {
            int j3 = i3 + chrom_width;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            an3[k2] = an1[i3] + an1[i3 + 1] + an1[j3] + an1[j3 + 1] >> 2;
            an4[k2++] = an2[i3++] + an2[i3] + an2[j3++] + an2[j3] >> 2;
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_down(int i1, int j1, int an1[], int an2[], int an3[])
    {
        int k1 = an3[0] >> 1;
        int i2 = an3[1] >> 1;
        int k2 = width * j1 + i1;
        int i3 = k2 + width * i2 + k1;
        int j2 = width - 15;
        for (int k3 = 0; k3 < 16; k3++)
        {
            int j3 = i3 + width;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2++] = an1[i3++] + an1[j3++] >> 1;
            an2[k2] = an1[i3] + an1[j3] >> 1;
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_right(int i1, int j1, int an1[], int an2[], int an3[])
    {
        int k1 = an3[0] >> 1;
        int i2 = an3[1] >> 1;
        int k2 = width * j1 + i1;
        int i3 = k2 + width * i2 + k1;
        int j2 = width - 16;
        for (int j3 = 0; j3 < 16; j3++)
        {
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            an2[k2++] = an1[i3++] + an1[i3] >> 1;
            k2 += j2;
            i3 += j2;
        }
    }

    final void recon_rightdown(int i1, int j1, int an1[], int an2[], int an3[])
    {
        int k1 = an3[0] >> 1;
        int i2 = an3[1] >> 1;
        int k2 = width * j1 + i1;
        int i3 = k2 + width * i2 + k1;
        int j2 = width - 16;
        for (int k3 = 0; k3 < 16; k3++)
        {
            int j3 = i3 + width;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            an2[k2++] = an1[i3++] + an1[i3] + an1[j3++] + an1[j3] >> 2;
            k2 += j2;
            i3 += j2;
        }
    }

    public final void reconstruct(int i1, int j1, int k1, int aan[][])
    {
        if ((k1 & 4) == 0)
        {
            int k3 = aan[0][0] & 1;
            int k2 = aan[0][1] & 1;
            if (k2 != 0 && k3 != 0)
                recon_rightdown(i1, j1, IY, Y, aan[0]);
            else if (k2 != 0)
                recon_down(i1, j1, IY, Y, aan[0]);
            else if (k3 != 0)
                recon_right(i1, j1, IY, Y, aan[0]);
            else
                recon(i1, j1, IY, Y, aan[0]);
            k3 = aan[0][0] >> 1 & 1;
            k2 = aan[0][1] >> 1 & 1;
            if (k2 != 0 && k3 != 0)
                recon_chrom_rightdown(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            else if (k2 != 0)
                recon_chrom_down(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            else if (k3 != 0)
                recon_chrom_right(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            else
                recon_chrom(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
        }
        else if ((k1 & 8) != 0)
        {
            int i3 = aan[0][0] & 1;
            int i2 = aan[0][1] & 1;
            if (i2 != 0 && i3 != 0)
                recon_rightdown(i1, j1, IY, Y, aan[0]);
            else if (i2 != 0)
                recon_down(i1, j1, IY, Y, aan[0]);
            else if (i3 != 0)
                recon_right(i1, j1, IY, Y, aan[0]);
            else
                recon(i1, j1, IY, Y, aan[0]);
            i3 = aan[0][0] >> 1 & 1;
            i2 = aan[0][1] >> 1 & 1;
            if (i2 != 0 && i3 != 0)
                recon_chrom_rightdown(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            else if (i2 != 0)
                recon_chrom_down(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            else if (i3 != 0)
                recon_chrom_right(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            else
                recon_chrom(i1, j1, ICr, ICb, Cr, Cb, aan[0]);
            i3 = aan[1][0] & 1;
            i2 = aan[1][1] & 1;
            if (i2 != 0 && i3 != 0)
                recon_rightdown(i1, j1, PY, tempY, aan[1]);
            else if (i2 != 0)
                recon_down(i1, j1, PY, tempY, aan[1]);
            else if (i3 != 0)
                recon_right(i1, j1, PY, tempY, aan[1]);
            else
                recon(i1, j1, PY, tempY, aan[1]);
            average(i1, j1, Y, tempY);
            i3 = aan[1][0] >> 1 & 1;
            i2 = aan[1][1] >> 1 & 1;
            if (i2 != 0 && i3 != 0)
                recon_chrom_rightdown(i1, j1, PCr, PCb, tempY, tempC, aan[1]);
            else if (i2 != 0)
                recon_chrom_down(i1, j1, PCr, PCb, tempY, tempC, aan[1]);
            else if (i3 != 0)
                recon_chrom_right(i1, j1, PCr, PCb, tempY, tempC, aan[1]);
            else
                recon_chrom(i1, j1, PCr, PCb, tempY, tempC, aan[1]);
            chrom_average(i1, j1, Cr, tempY, Cb, tempC);
        }
        else
        {
            int j3 = aan[1][0] & 1;
            int j2 = aan[1][1] & 1;
            if (j2 != 0 && j3 != 0)
                recon_rightdown(i1, j1, PY, Y, aan[1]);
            else if (j2 != 0)
                recon_down(i1, j1, PY, Y, aan[1]);
            else if (j3 != 0)
                recon_right(i1, j1, PY, Y, aan[1]);
            else
                recon(i1, j1, PY, Y, aan[1]);
            j3 = aan[1][0] >> 1 & 1;
            j2 = aan[1][1] >> 1 & 1;
            if (j2 != 0 && j3 != 0)
                recon_chrom_rightdown(i1, j1, PCr, PCb, Cr, Cb, aan[1]);
            else if (j2 != 0)
                recon_chrom_down(i1, j1, PCr, PCb, Cr, Cb, aan[1]);
            else if (j3 != 0)
                recon_chrom_right(i1, j1, PCr, PCb, Cr, Cb, aan[1]);
            else
                recon_chrom(i1, j1, PCr, PCb, Cr, Cb, aan[1]);
        }
    }

    public final void startnewpicture(int i)
    {
        if (i == 2 && prev[1])
        {
            int an[] = IY;
            IY = PY;
            PY = an;
            an = ICb;
            ICb = PCb;
            PCb = an;
            an = ICr;
            ICr = PCr;
            PCr = an;
        }
    }
}
