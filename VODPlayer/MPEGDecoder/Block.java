/* Decompiled by Jasmine from Block.class */
/* Originally compiled from Block.java */

package MPEGDecoder;

import java.io.PrintStream;

public class Block
{
    BitInputStream in;
    boolean fault;
    boolean quiet;
    DataStore store;
    BlockHeader header;
    public static final int ERROR = -1;
	                            // 0      1      2      3      4      5      6      7      8      9      10     11     12     13     14     15     16     17     18     19     20     21     22     23     24     25     26     27     28     29     30     31       
	static int DClumtab0[][] =   { {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {0,3}, {0,3}, {0,3}, {0,3}, {3,3}, {3,3}, {3,3}, {3,3}, {4,3}, {4,3}, {4,3}, {4,3}, {5,4}, {5,4}, {6,5}, {-1,0} };
	                            // 0      1      2      3      4      5      6      7      8      9      10     11     12     13     14     15    
	static int DClumtab1[][] =   { {7,6}, {7,6}, {7,6}, {7,6}, {7,6}, {7,6}, {7,6}, {7,6}, {8,7}, {8,7}, {8,7}, {8,7}, {9,8}, {9,8}, {10,9},{11,9} };
	                            // 0      1      2      3      4      5      6      7      8      9      10     11     12     13     14     15     16     17     18     19     20     21     22     23     24     25     26     27     28     29     30     31       
    static int DCchromtab0[][] = { {0,2}, {0,2}, {0,2}, {0,2}, {0,2}, {0,2}, {0,2}, {0,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {2,2}, {3,3}, {3,3}, {3,3}, {3,3}, {4,4}, {4,4}, {5,5}, {-1,0} };
	                            // 0      1      2      3      4      5      6      7      8      9      10     11     12     13     14     15     16     17     18     19     20     21     22     23     24     25     26     27     28     29     30     31       
	static int DCchromtab1[][] = { {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {6,6}, {7,7}, {7,7}, {7,7}, {7,7}, {7,7}, {7,7}, {7,7}, {7,7}, {8,8}, {8,8}, {8,8}, {8,8}, {9,9}, {9,9}, {10,10},{11,10}} ;
	                            // 0        1        2        3        4        5        6        7        8        9        10       11   
	static int DCTtabfirst[][] = { {0,2,4}, {2,1,4}, {1,1,3}, {1,1,3}, {0,1,1}, {0,1,1}, {0,1,1}, {0,1,1}, {0,1,1}, {0,1,1}, {0,1,1}, {0,1,1} };
	                            // 0        1        2        3        4        5        6        7        8        9        10       11   
	static int DCTtabnext[][]  = { {0,2,4}, {2,1,4}, {1,1,3}, {1,1,3}, {64,0,2},{64,0,2},{64,0,2},{64,0,2},{0,1,2}, {0,1,2}, {0,1,2}, {0,1,2} };
	                            // 0        1        2        3        4        5        6        7        8        9        10       1        2        3        4        5        6        7        8        9        20      1        2        3        4        5        6        7        8        9        30        1        2        3        4        5        6        7        8        9        40       1        2        3        4        5        6        7        8        9        50       1        2        3        4        5        6        7        8        9        
	static int DCTtab0[][]     = { {65,0,6},{65,0,6},{65,0,6},{65,0,6},{2,2,7}, {2,2,7}, {9,1,7}, {9,1,7}, {0,4,7}, {0,4,7}, {8,1,7}, {8,1,7}, {7,1,6}, {7,1,6}, {7,1,6}, {7,1,6}, {6,1,6}, {6,1,6}, {6,1,6}, {6,1,6}, {1,2,6},{1,2,6}, {1,2,6}, {1,2,6}, {5,1,6}, {5,1,6}, {5,1,6}, {5,1,6}, {13,1,8},{0,6,8}, {12,1,8}, {11,1,8},{3,2,8}, {1,3,8}, {0,5,8}, {10,1,8},{0,3,5}, {0,3,5}, {0,3,5}, {0,3,5}, {0,3,5}, {0,3,5}, {0,3,5}, {0,3,5}, {4,1,5}, {4,1,5}, {4,1,5}, {4,1,5}, {4,1,5}, {4,1,5}, {4,1,5}, {4,1,5}, {3,1,5}, {3,1,5}, {3,1,5}, {3,1,5}, {3,1,5}, {3,1,5}, {3,1,5}, {3,1,5} };
	                            // 0         1        2        3        4        5         6         7        
	static int DCTtab1[][]     = { {16,1,10},{5,2,10},{0,7,10},{2,3,10},{1,4,10},{15,1,10},{14,1,10},{4,2,10} };
	                            // 0         1        2        3         4        5        6         7         8        9         10        1        2        3        4        5                       
	static int DCTtab2[][]     = { {0,11,12},{8,2,12},{4,3,12},{0,10,12},{2,4,12},{7,2,12},{21,1,12},{20,1,12},{0,9,12},{19,1,12},{18,1,12},{1,5,12},{3,3,12},{0,8,12},{6,2,12},{17,1,12} };
	                            // 0         1        2        3        4        5        6        7         8         9         10        1         2         3         4         5                       
	static int DCTtab3[][]     = { {10,2,13},{9,2,13},{5,3,13},{3,4,13},{2,5,13},{1,7,13},{1,6,13},{0,15,13},{0,14,13},{0,13,13},{0,12,13},{26,1,13},{25,1,13},{24,1,13},{23,1,13},{22,1,13} };
	                            // 0         1         2         3         4         5         6         7         8         9         10        1         2         3         4         5                       
	static int DCTtab4[][]     = { {0,31,14},{0,30,14},{0,29,14},{0,28,14},{0,27,14},{0,26,14},{0,25,14},{0,24,14},{0,23,14},{0,22,14},{0,21,14},{0,20,14},{0,19,14},{0,18,14},{0,17,14},{0,16,14} };
	                            // 0         1         2         3         4         5         6         7         8         9         10        1         2         3         4         5                       
	static int DCTtab5[][]     = { {0,40,15},{0,39,15},{0,38,15},{0,37,15},{0,36,15},{0,35,15},{0,34,15},{0,33,15},{0,32,15},{1,14,15},{1,13,15},{1,12,15},{1,11,15},{1,10,15},{1, 9,15},{1, 8,15} };
    static int DCTtab6[][] = { { 1, 18, 16 }, { 1, 17, 16 }, { 1, 16, 16 }, { 1, 15, 16 }, { 6, 3, 16 }, { 16, 2, 16 }, { 15, 2, 16 }, { 14, 2, 16 }, { 13, 2, 16 }, { 12, 2, 16 }, { 11, 2, 16 }, { 31, 1, 16 }, { 30, 1, 16 }, { 29, 1, 16 }, { 28, 1, 16 }, { 27, 1, 16 } };;

    public Block(BitInputStream bitInputStream, DataStore dataStore)
    {
        in = bitInputStream;
        store = dataStore;
        header = new BlockHeader();
    }

    final int getDCchrom()
    {
        int j=0;
        int k=0;
        int i = in.nextbits(5);
        if (i < 31)
        {
            j = DCchromtab0[i][0];
            in.flushbits(DCchromtab0[i][1]);
        }
        else
        {
            i = in.nextbits(10) - 992;
            j = DCchromtab1[i][0];
            in.flushbits(DCchromtab1[i][1]);
        }
        if (j == 0)
            k = 0;
        else
        {
            k = in.readbits(j);
            if ((k & 1 << j - 1) == 0)
                k -= (1 << j) - 1;
        }
        return k;
    }

    final int getDClum()
    {
        int j;
        int k;
        int i = in.nextbits(5);
        if (i < 31)
        {
            j = DClumtab0[i][0];
            in.flushbits(DClumtab0[i][1]);
        }
        else
        {
            i = in.nextbits(9) - 496;
            j = DClumtab1[i][0];
            in.flushbits(DClumtab1[i][1]);
        }
        if (j == 0)
            k = 0;
        else
        {
            k = in.readbits(j);
            if ((k & 1 << j - 1) == 0)
                k -= (1 << j) - 1;
        }
        return k;
    }

    final void getinterblock(int i1)
    {
        int an2[] = store.getblock(i1);
        int i2 = 0;
        while (true)
        {
            int k=0;
            boolean flag2=false;
            int an1[];
            int j2 = in.nextbits(16);
            if (j2 >= 16384)
            {
                if (i2 == 0)
                    an1 = DCTtabfirst[(j2 >> 12) - 4];
                else
                    an1 = DCTtabnext[(j2 >> 12) - 4];
            }
            else if (j2 >= 1024)
                an1 = DCTtab0[(j2 >> 8) - 4];
            else if (j2 >= 512)
                an1 = DCTtab1[(j2 >> 6) - 8];
            else if (j2 >= 256)
                an1 = DCTtab2[(j2 >> 4) - 16];
            else if (j2 >= 128)
                an1 = DCTtab3[(j2 >> 3) - 16];
            else if (j2 >= 64)
                an1 = DCTtab4[(j2 >> 2) - 16];
            else if (j2 >= 32)
                an1 = DCTtab5[(j2 >> 1) - 16];
            else
            {
                if (j2 < 16)
                {
                    if (!quiet)
                        System.out.println("invalid Huffman code in getinterblock()");
                    fault = true;
                    return;
                }
                an1 = DCTtab6[j2 - 16];
            }
            in.flushbits(an1[2]);
            if (an1[0] == 64)
                return;
            if (an1[0] == 65)
            {
                boolean flag1;
                i2 += in.readbits(6);
                int j1 = in.readbits(8);
                if (j1 == 0)
                    j1 = in.readbits(8);
                else if (j1 == 128)
                    j1 = in.readbits(8) - 256;
                else if (j1 > 128)
                    j1 -= 256;
                if (flag1 = j1 < 0)
                    k = -j1;
            }
            else
            {
                i2 += an1[0];
                k = an1[1];
                flag2 = in.readbit();
            }
            byte b = DataStore.zig_zag_scan[i2];
            k = ((k << 1) + 1) * store.getquant_scale() * store.non_intra_quantizer_matrix[b] >> 4;
            k = k - 1 | 1;
            if (k > 2047)
                k = 2047;
            an2[b] = flag2 ? -k : k;
            i2++;
        }
    }

    final void getintrablock(int i1, int an1[])
    {
        int an3[] = store.getblock(i1);
        if (i1 < 4)
            an3[0] = an1[0] += getDClum() << 3;
        else if (i1 == 4)
            an3[0] = an1[1] += getDCchrom() << 3;
        else
            an3[0] = an1[2] += getDCchrom() << 3;
        if (fault)
            return;
        int i2 = 1;
        while (true)
        {
            int k=0;
            boolean flag2=false;
            int an2[];
            int j2 = in.nextbits(16);
            if (j2 >= 16384)
                an2 = DCTtabnext[(j2 >> 12) - 4];
            else if (j2 >= 1024)
                an2 = DCTtab0[(j2 >> 8) - 4];
            else if (j2 >= 512)
                an2 = DCTtab1[(j2 >> 6) - 8];
            else if (j2 >= 256)
                an2 = DCTtab2[(j2 >> 4) - 16];
            else if (j2 >= 128)
                an2 = DCTtab3[(j2 >> 3) - 16];
            else if (j2 >= 64)
                an2 = DCTtab4[(j2 >> 2) - 16];
            else if (j2 >= 32)
                an2 = DCTtab5[(j2 >> 1) - 16];
            else
            {
                if (j2 < 16)
                {
                    if (!quiet)
                        System.out.println(new StringBuffer("invalid Huffman code in getintrablock()+").append(i2).toString());
                    fault = true;
                    return;
                }
                an2 = DCTtab6[j2 - 16];
            }
            in.flushbits(an2[2]);
            if (an2[0] == 64)
                return;
            if (an2[0] == 65)
            {
                boolean flag1;
                i2 += in.readbits(6);
                int j1 = in.readbits(8);
                if (j1 == 0)
                    j1 = in.readbits(8);
                else if (j1 == 128)
                    j1 = in.readbits(8) - 256;
                else if (j1 > 128)
                    j1 -= 256;
                if (flag1 = j1 < 0)
                    k = -j1;
            }
            else
            {
                i2 += an2[0];
                k = an2[1];
                flag2 = in.readbit();
            }
            byte b = DataStore.zig_zag_scan[i2];
            k = k * store.getquant_scale() * store.intra_quantizer_matrix[b] >> 3;
            k = k - 1 | 1;
            if (k > 2047)
                k = 2047;
            an3[b] = flag2 ? -k : k;
            i2++;
        }
    }

    public final void read_block(int i)
    {
        if ((store.getmacroblock_type() & 1) != 0)
            getintrablock(i, header.dc_dct_pred);
        else
            getinterblock(i);
    }

    public final void setstream(BitInputStream bitInputStream)
    {
        in = bitInputStream;
    }
}
