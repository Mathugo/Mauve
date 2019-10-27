package musicplayer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.io.IOException;

public class main {
		public static void main(String[] args)
		{
			
			String host="82.64.162.133";
			int port=32889;
			Client cl = new Client(host,port);
			cl.download("instru.mp3");
		}
}


