/* Decompiled by Mocha from ListFrame$1.class */
/* Originally compiled from ListFrame.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class ListFrameListener1 implements ActionListener
{
    private ListFrame this$0 = null;

    ListFrameListener1(ListFrame listFrame)
    {
		this$0 = listFrame;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.buttonControl1_actionPerformed(actionEvent);
    }
}
