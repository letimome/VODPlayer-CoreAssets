package MPEGDecoder;

import java.io.PrintStream;

public class Macroblock
{
  BitInputStream in;
  boolean quiet;
  boolean fault;
  MacroHeader header;
  DataStore store;
  public static final int ERROR = -1;
  static int[][] MBAtab1 = { { -1 }, { -1 }, { 7, 5 }, { 6, 5 }, { 5, 4 }, { 5, 4 }, { 4, 4 }, { 4, 4 }, 
    { 3, 3 }, { 3, 3 }, { 3, 3 }, { 3, 3 }, { 2, 3 }, { 2, 3 }, { 2, 3 }, { 2, 3 } };

  static int[][] MBAtab2 = { 
    { 33, 11 }, { 32, 11 }, { 31, 11 }, { 30, 11 }, { 29, 11 }, { 28, 11 }, { 27, 11 }, { 26, 11 }, 
    { 25, 11 }, { 24, 11 }, { 23, 11 }, { 22, 11 }, { 21, 10 }, { 21, 10 }, { 20, 10 }, { 20, 10 }, 
    { 19, 10 }, { 19, 10 }, { 18, 10 }, { 18, 10 }, { 17, 10 }, { 17, 10 }, { 16, 10 }, { 16, 10 }, 
    { 15, 8 }, { 15, 8 }, { 15, 8 }, { 15, 8 }, { 15, 8 }, { 15, 8 }, { 15, 8 }, { 15, 8 }, 
    { 14, 8 }, { 14, 8 }, { 14, 8 }, { 14, 8 }, { 14, 8 }, { 14, 8 }, { 14, 8 }, { 14, 8 }, 
    { 13, 8 }, { 13, 8 }, { 13, 8 }, { 13, 8 }, { 13, 8 }, { 13, 8 }, { 13, 8 }, { 13, 8 }, 
    { 12, 8 }, { 12, 8 }, { 12, 8 }, { 12, 8 }, { 12, 8 }, { 12, 8 }, { 12, 8 }, { 12, 8 }, 
    { 11, 8 }, { 11, 8 }, { 11, 8 }, { 11, 8 }, { 11, 8 }, { 11, 8 }, { 11, 8 }, { 11, 8 }, 
    { 10, 8 }, { 10, 8 }, { 10, 8 }, { 10, 8 }, { 10, 8 }, { 10, 8 }, { 10, 8 }, { 10, 8 }, 
    { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, 
    { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, { 9, 7 }, 
    { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, 
    { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 } };
  public static final int MB_INTRA = 1;
  public static final int MB_PATTERN = 2;
  public static final int MB_BACKWARD = 4;
  public static final int MB_FORWARD = 8;
  public static final int MB_QUANT = 16;
  public static final int MB_WEIGHT = 32;
  public static final int MB_CLASS4 = 64;
  static int[][] PMBtab0 = { 
    { -1 }, 
    { 8, 3 }, 
    { 2, 2 }, { 2, 2 }, 
    { 10, 1 }, { 10, 1 }, 
    { 10, 1 }, { 10, 1 } };

  static int[][] PMBtab1 = { 
    { -1 }, 
    { 17, 6 }, 
    { 18, 5 }, { 18, 5 }, 
    { 26, 5 }, { 26, 5 }, 
    { 1, 5 }, { 1, 5 } };

  static int[][] BMBtab0 = { 
    { -1 }, { -1 }, 
    { 8, 4 }, 
    { 10, 4 }, 
    { 4, 3 }, { 4, 3 }, 
    { 6, 3 }, { 6, 3 }, 
    { 12, 2 }, { 12, 2 }, 
    { 12, 2 }, { 12, 2 }, 
    { 14, 2 }, 
    { 14, 2 }, 
    { 14, 2 }, 
    { 14, 2 } };

  static int[][] BMBtab1 = { 
    { -1 }, 
    { 17, 6 }, 
    { 22, 6 }, 
    { 26, 6 }, 
    { 30, 5 }, 
    { 30, 5 }, 
    { 1, 5 }, { 1, 5 } };

  static int[][] MVtab0 = { { -1 }, { 3, 3 }, { 2, 2 }, { 2, 2 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } };

  static int[][] MVtab1 = { { -1 }, { -1 }, { -1 }, { 7, 6 }, { 6, 6 }, { 5, 6 }, { 4, 5 }, { 4, 5 } };

  static int[][] MVtab2 = { { 16, 9 }, { 15, 9 }, { 14, 9 }, { 13, 9 }, 
    { 12, 9 }, { 11, 9 }, { 10, 8 }, { 10, 8 }, 
    { 9, 8 }, { 9, 8 }, { 8, 8 }, { 8, 8 } };

  static int[][] CBPtab0 = { { -1 }, { -1 }, { -1 }, { -1 }, 
    { -1 }, { -1 }, { -1 }, { -1 }, 
    { 62, 5 }, { 2, 5 }, { 61, 5 }, { 1, 5 }, { 56, 5 }, { 52, 5 }, { 44, 5 }, { 28, 5 }, 
    { 40, 5 }, { 20, 5 }, { 48, 5 }, { 12, 5 }, { 32, 4 }, { 32, 4 }, { 16, 4 }, { 16, 4 }, 
    { 8, 4 }, { 8, 4 }, { 4, 4 }, { 4, 4 }, { 60, 3 }, { 60, 3 }, { 60, 3 }, { 60, 3 } };

  static int[][] CBPtab1 = { { -1 }, { -1 }, { -1 }, { -1 }, 
    { 58, 8 }, { 54, 8 }, { 46, 8 }, { 30, 8 }, 
    { 57, 8 }, { 53, 8 }, { 45, 8 }, { 29, 8 }, { 38, 8 }, { 26, 8 }, { 37, 8 }, { 25, 8 }, 
    { 43, 8 }, { 23, 8 }, { 51, 8 }, { 15, 8 }, { 42, 8 }, { 22, 8 }, { 50, 8 }, { 14, 8 }, 
    { 41, 8 }, { 21, 8 }, { 49, 8 }, { 13, 8 }, { 35, 8 }, { 19, 8 }, { 11, 8 }, { 7, 8 }, 
    { 34, 7 }, { 34, 7 }, { 18, 7 }, { 18, 7 }, { 10, 7 }, { 10, 7 }, { 6, 7 }, { 6, 7 }, 
    { 33, 7 }, { 33, 7 }, { 17, 7 }, { 17, 7 }, { 9, 7 }, { 9, 7 }, { 5, 7 }, { 5, 7 }, 
    { 63, 6 }, { 63, 6 }, { 63, 6 }, { 63, 6 }, { 3, 6 }, { 3, 6 }, { 3, 6 }, { 3, 6 }, 
    { 36, 6 }, { 36, 6 }, { 36, 6 }, { 36, 6 }, { 24, 6 }, { 24, 6 }, { 24, 6 }, { 24, 6 } };

  static int[][] CBPtab2 = { { -1 }, { 0, 9 }, { 39, 9 }, { 27, 9 }, { 59, 9 }, { 55, 9 }, { 47, 9 }, { 31, 9 } };

  static int[][] DClumtab0 = { { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, 
    { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, 
    { 0, 3 }, { 0, 3 }, { 0, 3 }, { 0, 3 }, { 3, 3 }, { 3, 3 }, { 3, 3 }, { 3, 3 }, 
    { 4, 3 }, { 4, 3 }, { 4, 3 }, { 4, 3 }, { 5, 4 }, { 5, 4 }, { 6, 5 }, { -1 } };

  static int[][] DClumtab1 = { { 7, 6 }, { 7, 6 }, { 7, 6 }, { 7, 6 }, { 7, 6 }, { 7, 6 }, { 7, 6 }, { 7, 6 }, 
    { 8, 7 }, { 8, 7 }, { 8, 7 }, { 8, 7 }, { 9, 8 }, { 9, 8 }, { 10, 9 }, { 11, 9 } };

  static int[][] DCchromtab0 = { { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, { 0, 2 }, 
    { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, { 1, 2 }, 
    { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, { 2, 2 }, 
    { 3, 3 }, { 3, 3 }, { 3, 3 }, { 3, 3 }, { 4, 4 }, { 4, 4 }, { 5, 5 }, { -1 } };

  static int[][] DCchromtab1 = { { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, 
    { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, { 6, 6 }, 
    { 7, 7 }, { 7, 7 }, { 7, 7 }, { 7, 7 }, { 7, 7 }, { 7, 7 }, { 7, 7 }, { 7, 7 }, 
    { 8, 8 }, { 8, 8 }, { 8, 8 }, { 8, 8 }, { 9, 9 }, { 9, 9 }, { 10, 10 }, { 11, 10 } };

  static int[][] DCTtabfirst = { 
    { 0, 2, 4 }, { 2, 1, 4 }, { 1, 1, 3 }, { 1, 1, 3 }, 
    { 0, 1, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, 
    { 0, 1, 1 }, { 0, 1, 1 }, { 0, 1, 1 }, { 0, 1, 1 } };

  static int[][] DCTtabnext = { 
    { 0, 2, 4 }, { 2, 1, 4 }, { 1, 1, 3 }, { 1, 1, 3 }, 
    { 64, 0, 2 }, { 64, 0, 2 }, { 64, 0, 2 }, { 64, 0, 2 }, 
    { 0, 1, 2 }, { 0, 1, 2 }, { 0, 1, 2 }, { 0, 1, 2 } };

  static int[][] DCTtab0 = { 
    { 65, 0, 6 }, { 65, 0, 6 }, { 65, 0, 6 }, { 65, 0, 6 }, 
    { 2, 2, 7 }, { 2, 2, 7 }, { 9, 1, 7 }, { 9, 1, 7 }, 
    { 0, 4, 7 }, { 0, 4, 7 }, { 8, 1, 7 }, { 8, 1, 7 }, 
    { 7, 1, 6 }, { 7, 1, 6 }, { 7, 1, 6 }, { 7, 1, 6 }, 
    { 6, 1, 6 }, { 6, 1, 6 }, { 6, 1, 6 }, { 6, 1, 6 }, 
    { 1, 2, 6 }, { 1, 2, 6 }, { 1, 2, 6 }, { 1, 2, 6 }, 
    { 5, 1, 6 }, { 5, 1, 6 }, { 5, 1, 6 }, { 5, 1, 6 }, 
    { 13, 1, 8 }, { 0, 6, 8 }, { 12, 1, 8 }, { 11, 1, 8 }, 
    { 3, 2, 8 }, { 1, 3, 8 }, { 0, 5, 8 }, { 10, 1, 8 }, 
    { 0, 3, 5 }, { 0, 3, 5 }, { 0, 3, 5 }, { 0, 3, 5 }, 
    { 0, 3, 5 }, { 0, 3, 5 }, { 0, 3, 5 }, { 0, 3, 5 }, 
    { 4, 1, 5 }, { 4, 1, 5 }, { 4, 1, 5 }, { 4, 1, 5 }, 
    { 4, 1, 5 }, { 4, 1, 5 }, { 4, 1, 5 }, { 4, 1, 5 }, 
    { 3, 1, 5 }, { 3, 1, 5 }, { 3, 1, 5 }, { 3, 1, 5 }, 
    { 3, 1, 5 }, { 3, 1, 5 }, { 3, 1, 5 }, { 3, 1, 5 } };

  static int[][] DCTtab1 = { 
    { 16, 1, 10 }, { 5, 2, 10 }, { 0, 7, 10 }, { 2, 3, 10 }, 
    { 1, 4, 10 }, { 15, 1, 10 }, { 14, 1, 10 }, { 4, 2, 10 } };

  static int[][] DCTtab2 = { 
    { 0, 11, 12 }, { 8, 2, 12 }, { 4, 3, 12 }, { 0, 10, 12 }, 
    { 2, 4, 12 }, { 7, 2, 12 }, { 21, 1, 12 }, { 20, 1, 12 }, 
    { 0, 9, 12 }, { 19, 1, 12 }, { 18, 1, 12 }, { 1, 5, 12 }, 
    { 3, 3, 12 }, { 0, 8, 12 }, { 6, 2, 12 }, { 17, 1, 12 } };

  static int[][] DCTtab3 = { 
    { 10, 2, 13 }, { 9, 2, 13 }, { 5, 3, 13 }, { 3, 4, 13 }, 
    { 2, 5, 13 }, { 1, 7, 13 }, { 1, 6, 13 }, { 0, 15, 13 }, 
    { 0, 14, 13 }, { 0, 13, 13 }, { 0, 12, 13 }, { 26, 1, 13 }, 
    { 25, 1, 13 }, { 24, 1, 13 }, { 23, 1, 13 }, { 22, 1, 13 } };

  static int[][] DCTtab4 = { 
    { 0, 31, 14 }, { 0, 30, 14 }, { 0, 29, 14 }, { 0, 28, 14 }, 
    { 0, 27, 14 }, { 0, 26, 14 }, { 0, 25, 14 }, { 0, 24, 14 }, 
    { 0, 23, 14 }, { 0, 22, 14 }, { 0, 21, 14 }, { 0, 20, 14 }, 
    { 0, 19, 14 }, { 0, 18, 14 }, { 0, 17, 14 }, { 0, 16, 14 } };

  static int[][] DCTtab5 = { 
    { 0, 40, 15 }, { 0, 39, 15 }, { 0, 38, 15 }, { 0, 37, 15 }, 
    { 0, 36, 15 }, { 0, 35, 15 }, { 0, 34, 15 }, { 0, 33, 15 }, 
    { 0, 32, 15 }, { 1, 14, 15 }, { 1, 13, 15 }, { 1, 12, 15 }, 
    { 1, 11, 15 }, { 1, 10, 15 }, { 1, 9, 15 }, { 1, 8, 15 } };

  static int[][] DCTtab6 = { 
    { 1, 18, 16 }, { 1, 17, 16 }, { 1, 16, 16 }, { 1, 15, 16 }, 
    { 6, 3, 16 }, { 16, 2, 16 }, { 15, 2, 16 }, { 14, 2, 16 }, 
    { 13, 2, 16 }, { 12, 2, 16 }, { 11, 2, 16 }, { 31, 1, 16 }, 
    { 30, 1, 16 }, { 29, 1, 16 }, { 28, 1, 16 }, { 27, 1, 16 } };

  public Macroblock(BitInputStream paramBitInputStream, DataStore paramDataStore)
  {
    this.in = paramBitInputStream;
    this.store = paramDataStore;
    this.header = new MacroHeader();
    paramDataStore.setmacroheader(this.header);
  }

  static final int calcMV(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    int i = 16 << paramInt2;
    int j = (paramBoolean) ? paramInt1 >> 1 : paramInt1;

    if (paramInt3 > 0)
    {
      j += (paramInt3 - 1 << paramInt2) + paramInt4 + 1;
      if (j >= i)
        j -= i + i;
    }
    else if (paramInt3 < 0)
    {
      j -= (-paramInt3 - 1 << paramInt2) + paramInt4 + 1;
      if (j < -i)
        j += i + i;
    }
    return ((paramBoolean) ? j << 1 : j);
  }

  final int getBMBtype()
  {
    int i;
    if ((i = this.in.nextbits(6)) >= 8)
    {
      i >>= 2;
      this.in.flushbits(BMBtab0[i][1]);

      return BMBtab0[i][0];
    }

    if (i == 0)
    {
      if (!(this.quiet))
        System.out.println("Invalid macroblock_type code (B) \n");
      this.fault = true;
      return 0;
    }

    this.in.flushbits(BMBtab1[i][1]);

    return BMBtab1[i][0];
  }

  final int getCBP()
  {
    int i;
    if ((i = this.in.nextbits(9)) >= 128)
    {
      i >>= 4;
      this.in.flushbits(CBPtab0[i][1]);

      return CBPtab0[i][0];
    }

    if (i >= 8)
    {
      i >>= 1;
      this.in.flushbits(CBPtab1[i][1]);

      return CBPtab1[i][0];
    }

    if (i < 1)
    {
      if (!(this.quiet))
        System.out.println("Invalid coded_block_pattern code\n");
      this.fault = true;
      return 0;
    }

    this.in.flushbits(CBPtab2[i][1]);

    return CBPtab2[i][0];
  }

  final int getDCchrom()
  {
    int j;
    int k;
    int i = this.in.nextbits(5);

    if (i < 31)
    {
      j = DCchromtab0[i][0];
      this.in.flushbits(DCchromtab0[i][1]);
    }
    else
    {
      i = this.in.nextbits(10) - 992;
      j = DCchromtab1[i][0];
      this.in.flushbits(DCchromtab1[i][1]);
    }

    if (j == 0) {
      k = 0;
    }
    else {
      k = this.in.readbits(j);
      if ((k & 1 << j - 1) == 0) {
        k -= (1 << j) - 1;
      }
    }
    return k;
  }

  final int getDClum()
  {
    int j;
    int k;
    int i = this.in.nextbits(5);

    if (i < 31)
    {
      j = DClumtab0[i][0];
      this.in.flushbits(DClumtab0[i][1]);
    }
    else
    {
      i = this.in.nextbits(9) - 496;
      j = DClumtab1[i][0];
      this.in.flushbits(DClumtab1[i][1]);
    }

    if (j == 0) {
      k = 0;
    }
    else {
      k = this.in.readbits(j);
      if ((k & 1 << j - 1) == 0) {
        k -= (1 << j) - 1;
      }
    }
    return k;
  }

  final int getDMBtype()
  {
    if (!(this.in.readbit()))
    {
      if (!(this.quiet))
        System.out.println("Invalid macroblock_type code (D)\n");
      this.fault = true;
    }

    return 1;
  }

  final int getIMBtype()
  {
    if (this.in.readbit())
    {
      return 1;
    }

    if (!(this.in.readbit()))
    {
      if (!(this.quiet))
        System.out.println("Invalid macroblock_type code (I)");
      this.fault = true;
    }

    return 17;
  }

  final int getMBA()
  {
    int i;
    int j = 0;

    while ((i = this.in.nextbits(11)) < 24)
    {
      if (i != 15)
      {
        if (i == 8)
        {
          j += 33;
        }
        else
        {
          if (!(this.quiet))
            System.out.println("Invalid macroblock_address_increment code\n");
          this.fault = true;
          return 1;
        }
      }
      this.in.flushbits(11);
    }

    if (i >= 1024)
    {
      this.in.flushbits(1);
      return (j + 1);
    }

    if (i >= 128)
    {
      i >>= 6;
      this.in.flushbits(MBAtab1[i][1]);

      return (j + MBAtab1[i][0]);
    }

    i -= 24;
    this.in.flushbits(MBAtab2[i][1]);

    return (j + MBAtab2[i][0]);
  }

  int getMV()
  {
    int i;
    if (this.in.readbit())
    {
      return 0;
    }

    if ((i = this.in.nextbits(9)) >= 64)
    {
      i >>= 6;
      this.in.flushbits(MVtab0[i][1]);

      return ((this.in.readbit()) ? -MVtab0[i][0] : MVtab0[i][0]);
    }

    if (i >= 24)
    {
      i >>= 3;
      this.in.flushbits(MVtab1[i][1]);

      return ((this.in.readbit()) ? -MVtab1[i][0] : MVtab1[i][0]);
    }

    if ((i -= 12) < 0)
    {
      if (!(this.quiet))
        System.out.println("Invalid motion_vector code\n");
      this.fault = true;
      return 0;
    }

    this.in.flushbits(MVtab2[i][1]);
    return ((this.in.readbit()) ? -MVtab2[i][0] : MVtab2[i][0]);
  }

  int getPMBtype()
  {
    int i;
    if ((i = this.in.nextbits(6)) >= 8)
    {
      i >>= 3;
      this.in.flushbits(PMBtab0[i][1]);
      return PMBtab0[i][0];
    }

    if (i == 0)
    {
      if (!(this.quiet))
        System.out.println("Invalid macroblock_type code (P)\n");
      this.fault = true;
      return 0;
    }

    this.in.flushbits(PMBtab1[i][1]);
    return PMBtab1[i][0];
  }

  final void getinterblock(int paramInt)
  {
    int[] arrayOfInt2 = this.store.getblock(paramInt);

    for (int j = 0; ; ++j)
    {
      boolean bool;
      int[] arrayOfInt1;
      int l = this.in.nextbits(16);
      if (l >= 16384)
      {
        if (j == 0)
          arrayOfInt1 = DCTtabfirst[((l >> 12) - 4)];
        else
          arrayOfInt1 = DCTtabnext[((l >> 12) - 4)];
      }
      else if (l >= 1024) {
        arrayOfInt1 = DCTtab0[((l >> 8) - 4)];
      } else if (l >= 512) {
        arrayOfInt1 = DCTtab1[((l >> 6) - 8)];
      } else if (l >= 256) {
        arrayOfInt1 = DCTtab2[((l >> 4) - 16)];
      } else if (l >= 128) {
        arrayOfInt1 = DCTtab3[((l >> 3) - 16)];
      } else if (l >= 64) {
        arrayOfInt1 = DCTtab4[((l >> 2) - 16)];
      } else if (l >= 32) {
        arrayOfInt1 = DCTtab5[((l >> 1) - 16)];
      } else if (l >= 16) {
        arrayOfInt1 = DCTtab6[(l - 16)];
      }
      else {
        if (!(this.quiet))
          System.out.println("invalid Huffman code in getinterblock()");
        this.fault = true;
        return;
      }

      this.in.flushbits(arrayOfInt1[2]);

      if (arrayOfInt1[0] == 64) {
        return;
      }
      int i;
      if (arrayOfInt1[0] == 65)
      {
        j += this.in.readbits(6);

        i = this.in.readbits(8);
        if (i == 0)
          i = this.in.readbits(8);
        else if (i == 128)
          i = this.in.readbits(8) - 256;
        else if (i > 128) {
          i -= 256;
        }
        if ((bool = (i >= 0) ? false : true) != false)
          i = -i;
      }
      else
      {
        j += arrayOfInt1[0];
        i = arrayOfInt1[1];

        bool = this.in.readbit();
      }

      int k = DataStore.zig_zag_scan[j];

      i = ((i << 1) + 1) * this.header.quantizer_scale;

      i = i - 1 | 0x1;
      if (i > 2047) i = 2047;
      arrayOfInt2[k] = ((bool) ? -i : i);
    }
  }

  final void getinterblock1(int paramInt)
  {
    int[] arrayOfInt2 = this.store.getblock(paramInt);

    for (int j = 0; ; ++j)
    {
      boolean bool;
      int[] arrayOfInt1;
      int l = this.in.nextbits(16);
      if (l >= 16384)
      {
        if (j == 0)
          arrayOfInt1 = DCTtabfirst[((l >> 12) - 4)];
        else
          arrayOfInt1 = DCTtabnext[((l >> 12) - 4)];
      }
      else if (l >= 1024) {
        arrayOfInt1 = DCTtab0[((l >> 8) - 4)];
      } else if (l >= 512) {
        arrayOfInt1 = DCTtab1[((l >> 6) - 8)];
      } else if (l >= 256) {
        arrayOfInt1 = DCTtab2[((l >> 4) - 16)];
      } else if (l >= 128) {
        arrayOfInt1 = DCTtab3[((l >> 3) - 16)];
      } else if (l >= 64) {
        arrayOfInt1 = DCTtab4[((l >> 2) - 16)];
      } else if (l >= 32) {
        arrayOfInt1 = DCTtab5[((l >> 1) - 16)];
      } else if (l >= 16) {
        arrayOfInt1 = DCTtab6[(l - 16)];
      }
      else {
        if (!(this.quiet))
          System.out.println("invalid Huffman code in getinterblock()");
        this.fault = true;
        return;
      }

      this.in.flushbits(arrayOfInt1[2]);

      if (arrayOfInt1[0] == 64) {
        return;
      }
      int i;
      if (arrayOfInt1[0] == 65)
      {
        j += this.in.readbits(6);

        i = this.in.readbits(8);
        if (i == 0)
          i = this.in.readbits(8);
        else if (i == 128)
          i = this.in.readbits(8) - 256;
        else if (i > 128) {
          i -= 256;
        }
        if ((bool = (i >= 0) ? false : true) != false)
          i = -i;
      }
      else
      {
        j += arrayOfInt1[0];
        i = arrayOfInt1[1];

        bool = this.in.readbit();
      }

      int k = DataStore.zig_zag_scan[j];
      i = ((i << 1) + 1) * this.header.quantizer_scale * this.store.non_intra_quantizer_matrix[k] >> 4;

      i = i - 1 | 0x1;
      if (i > 2047) i = 2047;
      arrayOfInt2[k] = ((bool) ? -i : i);
    }
  }

  final void getintrablock(int paramInt, int[] paramArrayOfInt)
  {
    int[] arrayOfInt2 = this.store.getblock(paramInt);

    if (paramInt < 4)
      arrayOfInt2[0] = (paramArrayOfInt[0] += (getDClum() << 3));
    else if (paramInt == 4)
      arrayOfInt2[0] = (paramArrayOfInt[1] += (getDCchrom() << 3));
    else {
      arrayOfInt2[0] = (paramArrayOfInt[2] += (getDCchrom() << 3));
    }
    if (this.fault) return;

    for (int j = 1; ; ++j)
    {
      boolean bool;
      int[] arrayOfInt1;
      int l = this.in.nextbits(16);
      if (l >= 16384) {
        arrayOfInt1 = DCTtabnext[((l >> 12) - 4)];
      } else if (l >= 1024) {
        arrayOfInt1 = DCTtab0[((l >> 8) - 4)];
      } else if (l >= 512) {
        arrayOfInt1 = DCTtab1[((l >> 6) - 8)];
      } else if (l >= 256) {
        arrayOfInt1 = DCTtab2[((l >> 4) - 16)];
      } else if (l >= 128) {
        arrayOfInt1 = DCTtab3[((l >> 3) - 16)];
      } else if (l >= 64) {
        arrayOfInt1 = DCTtab4[((l >> 2) - 16)];
      } else if (l >= 32) {
        arrayOfInt1 = DCTtab5[((l >> 1) - 16)];
      } else if (l >= 16) {
        arrayOfInt1 = DCTtab6[(l - 16)];
      }
      else {
        if (!(this.quiet))
          System.out.println("invalid Huffman code in getintrablock()+" + j);
        this.fault = true;
        return;
      }

      this.in.flushbits(arrayOfInt1[2]);

      int i;
      if (arrayOfInt1[0] == 64) {
        return;
      }
      if (arrayOfInt1[0] == 65)
      {
        j += this.in.readbits(6);

        i = this.in.readbits(8);
        if (i == 0)
          i = this.in.readbits(8);
        else if (i == 128)
          i = this.in.readbits(8) - 256;
        else if (i > 128) {
          i -= 256;
        }
        if ((bool = (i >= 0) ? false : true) != false)
          i = -i;
      }
      else
      {
        j += arrayOfInt1[0];
        i = arrayOfInt1[1];

        bool = this.in.readbit();
      }

      int k = DataStore.zig_zag_scan[j];

      i *= this.store.temp_matrix[k];

      i = i - 1 | 0x1;
      if (i > 2047) i = 2047;
      arrayOfInt2[k] = ((bool) ? -i : i);
    }
  }

  public final boolean is_nextslice()
  {
    int i = this.in.nextaligned(32);

    return ((i >= 257) && 
      (i <= 431));
  }

  final void motion_vector(int[] paramArrayOfInt, int paramInt, boolean paramBoolean)
  {
    int i = getMV();
    int j = ((paramInt != 0) && (i != 0)) ? this.in.readbits(paramInt) : 0;

    paramArrayOfInt[0] = calcMV(paramArrayOfInt[0], paramInt, i, j, paramBoolean);

    i = getMV();
    j = ((paramInt != 0) && (i != 0)) ? this.in.readbits(paramInt) : 0;

    paramArrayOfInt[1] = calcMV(paramArrayOfInt[1], paramInt, i, j, paramBoolean);
  }

  public final void read_macroblock()
  {
    this.header.macroblock_address_increment = getMBA();

    if (this.header.MBAreset) {
      this.store.newslice(this.header.macroblock_address_increment);
      this.header.macroblock_address_increment = 1;
    }

    int i = this.store.getpicture_coding_type();

    if (this.header.macroblock_address_increment != 1) {
      if (i == 2) {
        this.header.macroblock_type = 8;
        this.header.PMV[0][0] = (this.header.PMV[0][1] = 0);
        this.header.PMV[1][0] = (this.header.PMV[1][1] = 0);
      }
      for (int j = 1; j < this.header.macroblock_address_increment; ++j) {
        this.store.MBAinc(1);
        this.store.skipblock();
      }
      this.store.resetpastdct();
    }
    this.store.MBAinc(1);

    if (i == 1) this.header.macroblock_type = getIMBtype();
    else if (i == 2) this.header.macroblock_type = getPMBtype();
    else if (i == 3) this.header.macroblock_type = getBMBtype();
    else if (i == 4) this.header.macroblock_type = getDMBtype();

    if ((this.header.macroblock_type & 0x10) != 0) {
      this.header.quantizer_scale = this.in.readbits(5);
      this.store.change_quantierscale(this.header.quantizer_scale); } else {
      this.header.quantizer_scale = this.header.slice_quantizer_scale;
    }

    if ((this.header.macroblock_type & 0x8) != 0)
      motion_vector(this.header.PMV[0], this.store.getforward_r_size(), this.store.getfull_pel_forward_vector());
    else if (i == 2) this.header.PMV[0][0] = (this.header.PMV[0][1] = 0);

    if ((this.header.macroblock_type & 0x4) != 0) {
      motion_vector(this.header.PMV[1], this.store.getbackward_r_size(), this.store.getfull_pel_backward_vector());
    }
    if ((this.header.macroblock_type & 0x2) != 0)
      this.header.coded_block_pattern = getCBP();
    else if ((this.header.macroblock_type & 0x1) != 0)
      this.header.coded_block_pattern = 63;
    else this.header.coded_block_pattern = 0;

    for (int j = 0; j < 6; ++j) {
      this.header.pattern_code[j] = 0;
      if ((this.header.coded_block_pattern & 1 << 5 - j) != 0) this.header.pattern_code[j] = 1;
      if ((this.header.macroblock_type & 0x1) == 0) continue; this.header.pattern_code[j] = 1;
    }

    for (int j = 0; j < 6; ++j) {
      if (this.header.pattern_code[j] == 1) {
        this.store.clearblock(j);
        if ((this.store.getmacroblock_type() & 0x1) != 0) getintrablock(j, this.header.dc_dct_pred);
        else getinterblock(j);
      }
    }

    if ((this.header.macroblock_type & 0x1) == 0) { this.store.resetpastdct();
    } else {
      this.header.PMV[1][0] = (this.header.PMV[1][1] = 0);
      if (i == 3) this.header.PMV[0][0] = (this.header.PMV[0][1] = 0);
    }

    this.store.decodeblock();

    if (i == 4)
      this.in.readbit();
  }

  public final boolean read_slice()
  {
    int i = this.in.readaligned(32);
    if ((i < 257) || 
      (i > 431)) return false;

    this.header.slice_quantizer_scale = this.in.readbits(5);
    this.store.change_quantierscale(this.header.slice_quantizer_scale);

    while (this.in.nextbits(1) == 1) {
      this.in.readbits(1);
      this.header.extra_information_slice = this.in.readbyte();
    }
    this.in.readbits(1);

    this.store.resetMBA();
    do
    {
      read_macroblock(); }
    while (this.in.nextbits(23) != 0);
    this.in.next_start_code();

    return true;
  }

  public final void setstream(BitInputStream paramBitInputStream)
  {
    this.in = paramBitInputStream;
  }
}