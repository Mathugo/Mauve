package musicplayer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

public class Client {

	   private int port;
	   private String host;
	   private Socket Client = null;
	   private String buffer;
	   
	   public Client(String phost, int pport)
	   {
		   port=pport;
		   host=phost;   
		   
		   try {
		       System.out.println("[*] Connecting to "+host+":"+port);

		       Client = new Socket(host, port);
		       System.out.println("[*] Connection done");

		      } catch (UnknownHostException e) {
		         e.printStackTrace();
			       System.exit(0);
		      } catch (IOException e) {
			       System.out.println("[*] The port "+port+" is not openned or the host in unreachable");
			       System.exit(0);
		      }
		   
	   }
	   
	   public void recv()
	      {
		   try 
		   {
			 BufferedReader in = new BufferedReader(new InputStreamReader(Client.getInputStream()));
			 buffer=in.readLine();
		   }
	       catch (Exception e)
		   {
	    	   System.out.println("[*] Can\'t recv data from the server");
	    	   e.printStackTrace();
	    	   buffer="null";
		   }
	    
	      }
	   
	   public void send(String sbuffer)
	      {
	    	  try
	    	  {
	    	      PrintStream out = new PrintStream(Client.getOutputStream());
	    	      out.println(sbuffer);
			        System.out.println("Sent");
			        
	    	  }
	    	  catch(Exception e)
	    	  {
	    		  System.out.println("[*] Can\'t send data to the server");
		    	   e.printStackTrace();
	    	  }
	      }
	      
	   public void printBuffer()
	      {
	        System.out.println(buffer);
	      }

	   public void download(String pbuffer)
	   {
		   this.send("download,"+pbuffer); // tell the server to 
		   this.recv();
		   if (buffer=="OK")
		   {
			   
		   }
	   }
}
