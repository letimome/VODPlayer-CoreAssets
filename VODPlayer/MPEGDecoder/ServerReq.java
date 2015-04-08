/* Decompiled by Mocha from ServerReq.class */
/* Originally compiled from ServerReq.java */

package MPEGDecoder;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class ServerReq
{
    Socket sock;
    DataInputStream instream;
    DataOutputStream outstream;
    boolean connected;
    String host;
    int port;

    public ServerReq(String string, int i)
    {
        connected = false;
        host = string;
        port = i;
        sock = null;
    }

    public void connect()
    {
        connect(host);
    }

    public void connect(String string)
    {
        if (sock != null && connected)
        {
            try
            {
                if (sock.getInetAddress() == InetAddress.getByName(string))
                    return;
            }
            catch (UnknownHostException e1)
            {
            }
            disconnect();
        }
        connected = false;
        try
        {
            sock = new Socket(string, port);
            instream = new DataInputStream(sock.getInputStream());
            outstream = new DataOutputStream(sock.getOutputStream());
            connected = true;
        }
        catch (IOException e2)
        {
            System.out.println("Can't connect to the host");
        }
        return;
    }

    public void disconnect()
    {
        try
        {
            instream.close();
            outstream.close();
            sock.close();
        }
        catch (Exception e)
        {
            System.out.println("There are some problems to disconnect the socket.");
        }
        connected = false;
    }

    public String gethost()
    {
        return sock.getInetAddress().getHostName();
    }

    public Movie getmovie(String string1)
    {
        Movie movie;
        if (!connected)
            return null;
        try
        {
            outstream.writeUTF("GET "+string1);
            outstream.flush();
            String string2 = instream.readUTF();
            if (!string2.equals("FOUND"))
                return null;
            String string3 = instream.readUTF();
            String string4 = instream.readUTF();
            String string5 = instream.readUTF();
            String string6 = instream.readUTF();
            movie = new Movie(string1, string3, string4, string5, string6);
            return movie;
        }
        catch (Exception e)
        {
            System.out.println("IOException during termination");
        }
        disconnect();
        return null;
    }

    public Vector getmovielist()
    {
        Vector vector;
        if (!connected)
            return null;
        try
        {
            outstream.writeUTF("LIST");
            outstream.flush();
            int i = instream.readInt();
            if (i <= 0)
                return null;
            vector = new Vector();
            for (int j = 0; j < i; j++)
            {
                String string = instream.readUTF();
                vector.addElement(string);
            }
            return vector;
        }
        catch (Exception e)
        {
            System.out.println("IOException during movie list");
        }
        disconnect();
        return null;
    }
	
	public int terminateConnection()
	{
        Vector vector;
        if (!connected)
            return 0;
        try
        {
            outstream.writeUTF("REQUEST_TERMINATION\n");
            outstream.flush();
            String string = instream.readUTF();
			
			if (string.indexOf("CONFIRM_TERMINATION") >= 0)
			{
				disconnect();
				return 0;
			}
			return -1;
		}
        catch (Exception e)
        {
            System.out.println("IOException during movie list");
        }
        disconnect();
		return 0;
	}

    public boolean isConnected()
    {
        return connected;
    }
}
