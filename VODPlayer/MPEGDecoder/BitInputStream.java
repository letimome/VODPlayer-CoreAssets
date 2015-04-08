/* Decompiled by Jasmine from BitInputStream.class */
/* Originally compiled from BitInputStream.java */

package MPEGDecoder;
import java.io.*;

public class BitInputStream extends BufferedInputStream
{
    static int bitposition;
    static short mask_last[] = { 255, 255, 127, 63, 31, 15, 7, 3, 1 };
    static final int BUFFERMAX = 256;
    static final int MAX = 255;
    static int buffer[];
    static byte tbuf[];
    static int bufferindex;
    static int buffersize;
	static InputStream inputStream;

    static 
    {
        buffer = new int[256];
        tbuf = new byte[256];
    }

    public BitInputStream(InputStream stream)
    {
        super(stream);
		inputStream = stream;
        buffersize = 0;
        bufferindex = 0;
        bitposition = 1;
    }
	
	public void close() throws IOException
	{
		inputStream.reset();
		inputStream.close();
	}

    public final void bytealign()
    {
        if (bitposition == 1)
            return;
        while (buffersize <= 1)
            readbuffer();
        bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
        bitposition = 1;
        buffersize--;
    }

    final boolean bytealigned()
    {
        if (bitposition == 1)
            return true;
        else
            return false;
    }

    public final void flushaligned(int i)
    {
        if (buffersize < 5)
            for (int j = 4; j > 0; j -= readbuffer()) /* null body */ ;
        while (i >= 8)
        {
            i -= 8;
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
            buffersize--;
        }
    }

    public final void flushbits(int i)
    {
        if (buffersize < 5)
            for (int j = 4; j > 0; j -= readbuffer()) /* null body */ ;
        if (bitposition != 1)
        {
            int k = 9 - bitposition;
            if (k >= i)
            {
                if (k > i)
                    bitposition += i;
                else
                {
                    bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
                    buffersize--;
                    bitposition = 1;
                }
                return;
            }
            i -= k;
            bitposition = 1;
            buffersize--;
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
        }
        while (i >= 8)
        {
            i -= 8;
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
            buffersize--;
        }
        if (i > 0)
            bitposition = i + 1;
    }

    public final void next_start_code()
    {
        if (buffersize < 4)
            for (int i = 3; i > 0; i -= readbuffer()) /* null body */ ;
        if (bitposition != 1)
        {
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
            bitposition = 1;
            for (buffersize--; buffersize == 0; )
                readbuffer();
        }
        while (nextbits(24) != 1)
        {
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
            for (buffersize--; buffersize == 0; )
                readbuffer();
        }
    }

    public final int nextaligned(int i1)
    {
        int j1 = 0;
        int k = bufferindex;
        int i2 = bitposition;
        if (buffersize < 5)
            for (int j2 = 4; j2 > 0; j2 -= readbuffer()) /* null body */ ;
        while (i1 >= 8)
        {
            j1 <<= 8;
            j1 += buffer[k];
            i1 -= 8;
            k = (k >= 255) ? 0 : (k + 1);
        }
        return j1;
    }

    public final int nextbits(int i1)
    {
        int j1 = 0;
        int k1 = bufferindex;
        int i2 = bitposition;
        if (buffersize < 5)
            for (int j2 = 4; j2 > 0; j2 -= readbuffer()) /* null body */ ;
        if (bitposition != 1)
        {
            int k2 = 9 - i2;
            if (k2 >= i1)
            {
                if (k2 > i1)
                {
                    j1 = mask_last[i2] & buffer[k1];
                    i2 += i1;
                    j1 >>= 9 - i2;
                }
                else
                    j1 = mask_last[i2] & buffer[k1];
                return j1;
            }
            j1 = mask_last[i2] & buffer[k1];
            i1 -= k2;
            k1 = (k1 >= 255) ? 0 : (k1 + 1);
        }
        while (i1 >= 8)
        {
            j1 <<= 8;
            j1 += buffer[k1];
            i1 -= 8;
            k1 = (k1 >= 255) ? 0 : (k1 + 1);
        }
        if (i1 > 0)
        {
            j1 <<= i1;
            j1 += buffer[k1] >> 8 - i1;
        }
        return j1;
    }

    public final int readaligned(int i)
    {
        int j = 0;
        if (buffersize < 5)
            for (int k = 4; k > 0; k -= readbuffer()) /* null body */ ;
        while (i >= 8)
        {
            j <<= 8;
            j += buffer[bufferindex];
            i -= 8;
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
            buffersize--;
        }
        return j;
    }

    public final boolean readbit()
    {
        return readbits(1) == 1;
    }

    public final int readbits(int i1)
    {
        int j = 0;
        if (buffersize < 5)
            for (int k = 4; k > 0; k -= readbuffer()) /* null body */ ;
        if (bitposition != 1)
        {
            int i2 = 9 - bitposition;
            if (i2 >= i1)
            {
                if (i2 > i1)
                {
                    j = mask_last[bitposition] & buffer[bufferindex];
                    bitposition += i1;
                    j >>= 9 - bitposition;
                    return j;
                }
                j = mask_last[bitposition] & buffer[bufferindex];
                bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
                buffersize--;
                bitposition = 1;
                return j;
            }
            j = mask_last[bitposition] & buffer[bufferindex];
            i1 -= 9 - bitposition;
            bitposition = 1;
            buffersize--;
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
        }
        while (i1 >= 8)
        {
            j <<= 8;
            j += buffer[bufferindex];
            i1 -= 8;
            bufferindex = (bufferindex >= 255) ? 0 : (bufferindex + 1);
            buffersize--;
        }
        if (i1 > 0)
        {
            j <<= i1;
            j += buffer[bufferindex] >> 8 - i1;
            bitposition = i1 + 1;
        }
        return j;
    }

    final int readbuffer()
    {
        int k = 0;
        int j1 = 256 - buffersize;
        int i1 = bufferindex + buffersize;
        if (i1 >= 256)
            i1 -= 256;
        try
        {
            k = read(tbuf, 0, j1);
        }
        catch (IOException e)
        {
            System.out.println("IOException");
            System.exit(-1);
        }
        if (k == -1)
        {
            for (int i2 = 0; i2 < j1; i2++)
            {
                buffer[i1++] = 0;
                if (i1 >= 256)
					i1 = 0;			//TODO: it said i1=false
            }
        }
        else
        {
            j1 = k;
            for (int j2 = 0; j2 < j1; j2++)
            {
                buffer[i1++] = (short)(tbuf[j2] & 255);
                if (i1 >= 256)
                    i1 = 0;		//TODO: it said i1=false
            }
        }
        buffersize += j1;
        return j1;
    }

    public final int readbyte()
    {
        return readbits(8);
    }

    public int readint()
    {
        int i = readbits(32);
        return i;
    }

    public final int readshort()
    {
        return readbits(16);
    }
}
