/* Decompiled by Mocha from ListFrame$2.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class ListFrameListener2 implements ActionListener
{
    private ListFrame this$0 = null;

    ListFrameListener2(ListFrame listFrame)
    {
		this$0 = listFrame;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.buttonControl2_actionPerformed(actionEvent);
    }
}
