/* Decompiled by Jasmine from Detail$1.class */
/* Originally compiled from Detail.java */

package MPEGDecoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class DetailListener1 implements ActionListener
{
    private Detail this$0 = null;

    DetailListener1(Detail detail)
    {
		this$0 = detail;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        this$0.button1_actionPerformed(actionEvent);
    }
}
