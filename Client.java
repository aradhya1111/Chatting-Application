import java.net.*;

import java.io.*;


public class Client 
{ 

	Socket socket;

	BufferedReader br;
	PrintWriter out;


   public Client()
   {
   	try 
   	{
   	 System.out.println("Sending request to server");	
     socket=new Socket("127.0.0.1",7777); //server is on the same computer and hence the port no. is also the same.
     System.out.println("connection done");

     br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
     out=new PrintWriter(socket.getOutputStream());

     //Functions for reading and writing
    startReading();
    startWriting();
   
   	}
   	catch(Exception e)
   	{
     e.printStackTrace();
   	}
   }
   
   
   //Method for start reading
   public void startReading()
   {
   	//thread for giving us the data after reading
   	Runnable r1=() -> {
    System.out.println("The reader started...");
       try{
   		while(true)
   		{

             String message=br.readLine();
             if(message.equals("exit"))
             {
             	System.out.println("Server terminated the chat");
             	socket.close();
             	break;
             }
             System.out.println("Server : "+ message);
   			
   		}
            

   	   }

   	   catch(Exception e)
   	   {
   	   	System.out.println("Connection is closed");
   	   }

   	};
   	new Thread(r1).start();
   } 


   //Method for start Writing
   public void startWriting()
   {
   	Runnable r2=() -> {
   		System.out.println("The writer started...");
   		try{
   		while(!socket.isClosed())
   		{
   			
   			 BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
             String content=br1.readLine();
             out.println(content);
             out.flush();

             if(content.equals("exit"))
             {
             	socket.close();
             	break;
             }
   			
   		}
   		System.out.println("Connection is closed");
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}

   	};
   	new Thread(r2).start(); 
   }

	public static void main(String[] args)
	{
		System.out.println("This is client...");
		new Client();
	}
}