/* Decompiled by Mocha from VODClient$3.class */
/* Originally compiled from VODClient.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class VODClientListener4 implements ActionListener
{
    private VODClient this$0 = null;

    VODClientListener4(VODClient vODClient)
    {
		this$0 = vODClient;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.buttonControl4_actionPerformed(actionEvent);
    }
}
