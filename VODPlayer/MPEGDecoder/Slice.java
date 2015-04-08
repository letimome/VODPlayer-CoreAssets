/* Decompiled by Jasmine from Slice.class */
/* Originally compiled from Slice.java */

package MPEGDecoder;

public class Slice
{
    BitInputStream instream;
    MacroblockNew macro;
    SliceHeaderNew header;
    DataStore store;

    public Slice(BitInputStream instream, DataStore store)
    {
        this.instream = instream;
        this.store = store;
        header = new SliceHeaderNew();
		//store.setsliceheader(header);			//TODO: put away
        macro = new MacroblockNew(instream, store);
    }

    public void setstream(BitInputStream in)
    {
        instream = in;
        macro.setstream(instream);
    }

    public boolean read_slice()
    {
        int slice_start_code = instream.readint();
        if (slice_start_code < 257 || slice_start_code > 431)
            return false;
        header.quantizer_scale = instream.readbits(5);
        while (instream.nextbits(1) == 1)
        {
            instream.readbits(1);
            header.extra_information_slice = instream.readbyte();
        }
        instream.readbits(1);
        store.resetMBA();
        do
            macro.read_macroblock();
        while (instream.nextbits(23) != 0);
        instream.next_start_code();
        return true;
    }

    public boolean is_nextslice()
    {
        int slice_start_code = instream.nextbits(32);
        if (slice_start_code < 257 || slice_start_code > 431)
            return false;
        else
            return true;
    }
}
