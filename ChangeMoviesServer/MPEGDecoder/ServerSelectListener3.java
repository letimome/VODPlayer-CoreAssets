/* Decompiled by Jasmine from ServerSelect$3.class */
/* Originally compiled from ServerSelect.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class ServerSelectListener3 implements ActionListener
{
    private ServerSelect this$0 = null;

    ServerSelectListener3(ServerSelect serverSelect)
    {
		this$0 = serverSelect;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.textField1_actionPerformed(actionEvent);
    }
}
