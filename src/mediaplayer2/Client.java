package mediaplayer2;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.util.*;

public class Client {

	   private int port;
	   private String host;
	   private Socket Client = null;
	   private String buffer;
	   private int BUFFER_FILE_SIZE=4096;
	   BufferedReader in_;
	   PrintStream out_;
	   
	   
	   public Client(String phost, int pport)
	   {
		   port=pport;
		   host=phost;

		   
		   try {
		       System.out.println("[*] Connecting to "+host+":"+port);

		       Client = new Socket(host, port);
		       System.out.println("[*] Connection done");
			   in_ = new BufferedReader(new InputStreamReader(Client.getInputStream()));
	    	   out_ = new PrintStream(Client.getOutputStream());


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
			 buffer=in_.readLine();
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
	    	      out_.println(sbuffer);
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
           
           
           
	   public String getBuffer()
	   {
		   return buffer;
	   }
           
           
           
	   public void getMetaData(String filename)
	   {
		   this.send("getMetaData,"+filename);
	   }
           
           
           
	   public ArrayList<String> listMusics()
	   {
               ArrayList<String> musics = new ArrayList<String>();
               
		   this.send("list_musics");
                   this.recv();
                   int nbMusics = Integer.parseInt(buffer);
                   for (int i = 0; i < nbMusics ; i++)
                   {
                       this.recv();
                       System.out.println("Music : "+buffer);
                       musics.add(buffer);
                   }
                   return musics;
	   }
           
           
	   public void download(String filename)

	   {

		   this.send("download,"+filename); // tell the server to 
		   this.recv();
		   this.printBuffer();
		   
		   if (buffer.equals("OK"))
		   {
			   InputStream in_d = null;
		       OutputStream out_d = null;
		  
			   try 
			   {
		         out_d = new FileOutputStream(filename);
			   }
			   catch(Exception e)
			   {
				   System.out.println("[!] Error while creating new file : "+filename);
				   e.printStackTrace();
			   }
			   try 
			   {
				   in_d = Client.getInputStream();
		       } 
			   catch (IOException ex) 
			   {
		           System.out.println("[!] Error creating input stream");
		       }
			   			   
			   byte[] buf = new byte[BUFFER_FILE_SIZE]; // BUFFER

			   int count=1;
			   
		        while (count > 0) 
		        {
		        	try 
		        	{
			        	count = in_d.read(buf);
			        	if (count == -1) {break;}
			        	out_d.write(buf, 0, count);
			        	System.out.println("[*] Downloading ..");
		        	}
		        	catch(Exception e)
		        	{
		        		System.out.println("[!] Error during the transfer");
		        		e.printStackTrace();
		        		break;
		        	}
		        }
		        try
		        {
		        	out_d.close();
			        in_d.close();
			        System.out.println("[*] Done closing stream");
			        System.out.println("[*] Done downloading : "+filename);
		        }
		        catch(Exception e)
		        {
		        	System.out.print("Error while closing the stream");
		        	e.printStackTrace();
		        }
			 
			   
		   }
		   
	   }
}
