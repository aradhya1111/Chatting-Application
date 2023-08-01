import java.net.*;
import java.io.*;
class Server
{

   ServerSocket server;
   Socket socket;

   //variables for reading and writing
    BufferedReader br;
    PrintWriter out;


    //constructor
	public Server()
	{
	try
	{
    server=new ServerSocket(7777);
    //server will accept the request now
    System.out.println("Server is ready to accept connection");
    System.out.println("waiting...");
    socket=server.accept(); //here server is accepting connection of client i.e of Socket so it will return the object of same.

    br=new BufferedReader(new InputStreamReader(socket.getInputStream()));   
    out=new PrintWriter(socket.getOutputStream()); //for writing data

    //Functions for reading and writing
    startReading();
    startWriting();

    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
	}
   
   //We have to do two task simultaneously in a single program so we are using MultiThreading here.
	public void startReading()
	{
    // thread for giving us the data after reading
		Runnable r1=()->{
        System.out.println("The reader started");
        try
        {
        while(true)
        {
        	
        	String message=br.readLine();
        	if(message.equals("exit"))         //if client has send this particular message then we have to stop
        	{
            System.out.println("Client terminated the chat");
            socket.close();  //connection is closed
            break;
        	}
        	System.out.println("Client : "+ message);  
           
        }
        System.out.println("Connection is closed");
        }

        catch(Exception e)
        {
        	System.out.println("Connection is closed");
        }

		};
		//starting the thread
		 new Thread(r1).start();
	}
   
    public void startWriting()
    {
    // thread for taking the data from user and sending it to the client side.
    	Runnable r2=()->{
    		System.out.println("Writer started");
    		try{
    		while(!socket.isClosed())   // it will only run when socket is working and not closed.
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
    	} 
    	catch(Exception e)
    	{
    		System.out.println("Connection is closed");
    	}
    	

    	};
    	//Starting the thread
    	new Thread(r2).start();
    }


	public static void main(String[] args)
	{
	System.out.println("This is server....going to start the Server");
	//server's object(constructor will execute)
	new Server();
	}
}