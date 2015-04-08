/* Decompiled by Jasmine from Picture.class */
/* Originally compiled from Picture.java */

package MPEGDecoder;

public class Picture
{
    BitInputStream instream;
    MacroblockNew macro;
    PictureHeader header;
    DataStore store;
    int prev;
    boolean pflag;

    public Picture(BitInputStream bitInputStream, DataStore dataStore)
    {
        instream = bitInputStream;
        store = dataStore;
        header = new PictureHeader();
        dataStore.setpictureheader(header);
        macro = new MacroblockNew(bitInputStream, dataStore);
        prev = -1;
        pflag = false;
    }

    public final boolean read_picture()
    {
        if (instream.readaligned(32) != 256)
            return false;
        header.temporal_reference = instream.readbits(10);
        header.picture_coding_type = instream.readbits(3);
        header.vbv_delay = instream.readbits(16);
        store.startnewpicture(header.picture_coding_type);
        if (header.picture_coding_type == 2 || header.picture_coding_type == 3)
        {
            header.full_pel_forward_vector = instream.readbit();
            header.forward_f_code = instream.readbits(3);
            header.forward_r_size = header.forward_f_code - 1;
            header.forward_f = 1 << header.forward_r_size;
        }
        if (header.picture_coding_type == 3)
        {
            header.full_pel_backward_vector = instream.readbit();
            header.backward_f_code = instream.readbits(3);
            header.backward_r_size = header.backward_f_code - 1;
            header.backward_f = 1 << header.backward_r_size;
        }
        while (instream.nextbits(1) == 1)
        {
            instream.readbits(1);
            header.extra_information_picture = instream.readbyte();
        }
        instream.readbits(1);
        instream.next_start_code();
        if (instream.nextaligned(32) == 437)
        {
            instream.readaligned(32);
            while (instream.nextaligned(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        if (instream.nextaligned(32) == 434)
        {
            instream.readaligned(32);
            while (instream.nextaligned(24) != 1)
                instream.readbyte();
            instream.next_start_code();
        }
        store.newframe();
        do
            macro.read_slice();
        while (macro.is_nextslice());
        if (header.picture_coding_type == 1)
        {
            if (pflag)
                store.showPframe();
            else
                store.showframe();
            store.exchangeIframe();
        }
        else if (header.picture_coding_type == 2)
        {
            if (pflag)
                store.showIframe();
            else
                pflag = true;
            store.exchangePframe();
        }
        else
            store.showframe();
        return true;
    }

    public void setstream(BitInputStream bitInputStream)
    {
        instream = bitInputStream;
        macro.setstream(instream);
        pflag = false;
    }
}
