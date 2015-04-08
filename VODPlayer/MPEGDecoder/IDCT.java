/* Decompiled by Jasmine from IDCT.class */
/* Originally compiled from IDCT.java */

package MPEGDecoder;

public class IDCT
{
    static final int inormalize_coefficient = 741455;
    static final int iconst1 = 741455;
    static final int iconst11 = 724;
    static final int iconst12 = 91;
    static final int iconst13 = 11585;
    static final int iconst2 = 554;
    static final int iconst3 = 1338;
    static final int iconst21 = 69;
    static final int iconst31 = 167;
    static final int iconst4 = 33;
    static final int iconst5 = 38;
    static final int iconst6 = 164;
    static final int iconst7 = 58;
    static final float fconst1 = 0.70710677f;
    static final float fconst2 = 0.5411961f;
    static final float fconst3 = 1.306563f;
    static final float fconst4 = 0.5097956f;
    static final float fconst5 = 0.6013449f;
    static final float fconst6 = 2.5629156f;
    static final float fconst7 = 0.8999762f;
    static int z1;
    static int z2;
    static int z3;
    static int z4;
    static int z5;
    static int z6;
    static int z7;
    static int it10;
    static int it11;
    static int it12;
    static int it13;
    static int it14;
    static int it15;
    static int it16;
    static int it17;
    static int it20;
    static int it21;
    static int it22;
    static int it23;
    static int it24;
    static int it25;
    static int it26;
    static int it27;
    static float fz1;
    static float fz2;
    static float fz3;
    static float fz4;
    static float fz5;
    static float fz6;
    static float fz7;
    static float ft10;
    static float ft11;
    static float ft12;
    static float ft13;
    static float ft14;
    static float ft15;
    static float ft16;
    static float ft17;
    static float ft20;
    static float ft21;
    static float ft22;
    static float ft23;
    static float ft24;
    static float ft25;
    static float ft26;
    static float ft27;

    public IDCT()
    {
    }

    public static final void fast_fidct64_rowcol(int an[])
    {
        fidct_col(an, 0);
        fidct_col(an, 1);
        fidct_col(an, 2);
        fidct_col(an, 3);
        fidct_col(an, 4);
        fidct_col(an, 5);
        fidct_col(an, 6);
        fidct_col(an, 7);
        fidct_row(an, 0);
        fidct_row(an, 8);
        fidct_row(an, 16);
        fidct_row(an, 24);
        fidct_row(an, 32);
        fidct_row(an, 40);
        fidct_row(an, 48);
        fidct_row(an, 56);
    }

    public static final void fast_idct64_rowcol(int an[])
    {
        iidct_col(an, 0);
        iidct_col(an, 1);
        iidct_col(an, 2);
        iidct_col(an, 3);
        iidct_col(an, 4);
        iidct_col(an, 5);
        iidct_col(an, 6);
        iidct_col(an, 7);
        iidct_row(an, 0);
        iidct_row(an, 8);
        iidct_row(an, 16);
        iidct_row(an, 24);
        iidct_row(an, 32);
        iidct_row(an, 40);
        iidct_row(an, 48);
        iidct_row(an, 56);
    }

    public static final void fidct_col(int an[], int i)
    {
        ft10 = 0.70710677f * an[i];
        fz1 = an[i + 8];
        fz2 = an[i + 16];
        fz3 = an[i + 24];
        fz4 = an[i + 32];
        fz5 = an[i + 40];
        fz6 = an[i + 48];
        fz7 = an[i + 56];
        ft16 = fz3 + fz1;
        ft11 = fz4 * 0.70710677f;
        ft13 = (fz6 + fz2) * 0.70710677f;
        ft15 = (fz5 + fz3) * 0.70710677f;
        ft17 = (fz7 + fz5 + ft16) * 0.70710677f;
        ft20 = ft10 + ft11;
        ft21 = ft10 - ft11;
        ft22 = (fz2 + ft13) * 0.5411961f;
        ft23 = (fz2 - ft13) * 1.306563f;
        ft24 = fz1 + ft15;
        ft25 = fz1 - ft15;
        ft26 = (ft16 + ft17) * 0.5411961f;
        ft27 = (ft16 - ft17) * 1.306563f;
        ft10 = ft20 + ft22;
        ft11 = ft21 + ft23;
        ft12 = ft20 - ft22;
        ft13 = ft21 - ft23;
        ft14 = (ft24 + ft26) * 0.5097956f;
        ft15 = (ft25 + ft27) * 0.6013449f;
        ft16 = (ft24 - ft26) * 2.5629156f;
        ft17 = (ft25 - ft27) * 0.8999762f;
        an[i] = (int)(ft10 + ft14);
        an[i + 8] = (int)(ft11 + ft15);
        an[i + 16] = (int)(ft13 + ft17);
        an[i + 24] = (int)(ft12 + ft16);
        an[i + 32] = (int)(ft12 - ft16);
        an[i + 40] = (int)(ft13 - ft17);
        an[i + 56] = (int)(ft10 - ft14);
        an[i + 48] = (int)(ft11 - ft15);
    }

    public static final void fidct_row(int an[], int i)
    {
        ft10 = 0.70710677f * an[i];
        fz1 = an[i + 1];
        fz2 = an[i + 2];
        fz3 = an[i + 3];
        fz5 = an[i + 5];
        ft16 = fz3 + fz1;
        ft11 = (float)an[i + 4] * 0.70710677f;
        ft13 = ((float)an[i + 6] + fz2) * 0.70710677f;
        ft15 = (fz5 + fz3) * 0.70710677f;
        ft17 = ((float)an[i + 7] + fz5 + ft16) * 0.70710677f;
        ft20 = ft10 + ft11;
        ft21 = ft10 - ft11;
        ft22 = (fz2 + ft13) * 0.5411961f;
        ft23 = (fz2 - ft13) * 1.306563f;
        ft24 = fz1 + ft15;
        ft25 = fz1 - ft15;
        ft26 = (ft16 + ft17) * 0.5411961f;
        ft27 = (ft16 - ft17) * 1.306563f;
        ft10 = ft20 + ft22;
        ft11 = ft21 + ft23;
        ft12 = ft20 - ft22;
        ft13 = ft21 - ft23;
        ft14 = (ft24 + ft26) * 0.5097956f;
        ft15 = (ft25 + ft27) * 0.6013449f;
        ft16 = (ft24 - ft26) * 2.5629156f;
        ft17 = (ft25 - ft27) * 0.8999762f;
        an[i] = (int)(ft10 + ft14) >> 2;
        an[i + 1] = (int)(ft11 + ft15) >> 2;
        an[i + 3] = (int)(ft12 + ft16) >> 2;
        an[i + 2] = (int)(ft13 + ft17) >> 2;
        an[i + 7] = (int)(ft10 - ft14) >> 2;
        an[i + 6] = (int)(ft11 - ft15) >> 2;
        an[i + 4] = (int)(ft12 - ft16) >> 2;
        an[i + 5] = (int)(ft13 - ft17) >> 2;
    }

    public static final void iidct_col(int an[], int i)
    {
        it10 = 741455 * an[i];
        z1 = an[i + 8];
        z2 = an[i + 16];
        z3 = an[i + 24];
        z4 = an[i + 32];
        z5 = an[i + 40];
        z6 = an[i + 48];
        z7 = an[i + 56];
        if ((it10 | z1) == 0 && (z2 | z3 | z4 | z5 | z6 | z7) == 0)
            return;
        it16 = z3 + z1;
        it11 = z4 * 741455;
        it13 = (z6 + z2) * 724;
        it15 = (z5 + z3) * 11585;
        it17 = (z7 + z5 + it16) * 91;
        z2 <<= 10;
        z1 <<= 14;
        it16 <<= 7;
        it20 = it10 + it11;
        it21 = it10 - it11;
        it22 = (z2 + it13) * 554;
        it23 = (z2 - it13) * 1338;
        it24 = z1 + it15;
        it25 = z1 - it15;
        it26 = (it16 + it17) * 69;
        it27 = (it16 - it17) * 167;
        it10 = it20 + it22;
        it11 = it21 + it23;
        it12 = it20 - it22;
        it13 = it21 - it23;
        it14 = (it24 + it26) * 33;
        it15 = (it25 + it27) * 38;
        it16 = (it24 - it26) * 164;
        it17 = (it25 - it27) * 58;
        an[i] = it10 + it14 >> 20;
        an[i + 8] = it11 + it15 >> 20;
        an[i + 16] = it13 + it17 >> 20;
        an[i + 24] = it12 + it16 >> 20;
        an[i + 32] = it12 - it16 >> 20;
        an[i + 40] = it13 - it17 >> 20;
        an[i + 56] = it10 - it14 >> 20;
        an[i + 48] = it11 - it15 >> 20;
    }

    public static final void iidct_row(int an[], int i)
    {
        it10 = 741455 * an[i];
        z1 = an[i + 1];
        z2 = an[i + 2];
        z3 = an[i + 3];
        z5 = an[i + 5];
        it16 = z3 + z1;
        it11 = an[i + 4] * 741455;
        it13 = (an[i + 6] + z2) * 724;
        it15 = (z5 + z3) * 11585;
        it17 = (an[i + 7] + z5 + it16) * 91;
        z2 <<= 10;
        z1 <<= 14;
        it16 <<= 7;
        it20 = it10 + it11;
        it21 = it10 - it11;
        it22 = (z2 + it13) * 554;
        it23 = (z2 - it13) * 1338;
        it24 = z1 + it15;
        it25 = z1 - it15;
        it26 = (it16 + it17) * 69;
        it27 = (it16 - it17) * 167;
        it10 = it20 + it22;
        it11 = it21 + it23;
        it12 = it20 - it22;
        it13 = it21 - it23;
        it14 = (it24 + it26) * 33;
        it15 = (it25 + it27) * 38;
        it16 = (it24 - it26) * 164;
        it17 = (it25 - it27) * 58;
        an[i] = it10 + it14 >> 22;
        an[i + 1] = it11 + it15 >> 22;
        an[i + 3] = it12 + it16 >> 22;
        an[i + 2] = it13 + it17 >> 22;
        an[i + 7] = it10 - it14 >> 22;
        an[i + 6] = it11 - it15 >> 22;
        an[i + 4] = it12 - it16 >> 22;
        an[i + 5] = it13 - it17 >> 22;
    }
}
