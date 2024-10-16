import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Server{
    //a variable to save the output object for each client
    static ServerSocket server;
    static String inMessage="";
    static String outMessage="";
    static private Map<Socket, PrintWriter> clientWriters = new HashMap<>();
    public static void broadcast(Socket sender,String message){
        clientWriters.forEach((key,val)->{

            if(sender!=key) {
                System.out.println("sent to" + key + val);
                val.println(message);
                val.flush(cmd);
            }

        });
    }
    public static void main(String args[]) {
        try {
            //create a server object
            ServerSocket server=new ServerSocket(5000);
            System.out.println("Server running at port 5000");
            //try and catch make a port as server

            //a infinite while loop to get as many client sockets as possible
            // launch a new thread for each client

            while(true) {

                Socket clientSocket = server.accept();


                //objects used to send and receive a particular input and output to the respective client
                new Thread(()->{try{



                    System.out.println("ClientSocket connected");
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    clientWriters.put(clientSocket,output);
                    output.println("Welcome to the Group Chat");
                    while ((inMessage = input.readLine()) != "stop" && inMessage!=null) {
                        if(!Objects.equals(inMessage, "")) {
                            System.out.println("Read message");
                            System.out.println(inMessage);
                            //broadcast inMessage to everyone
                            broadcast(clientSocket, inMessage);

                            System.out.println("Message sent");
                        }
                    }
                    clientWriters.remove(clientSocket);

                    output.close();
                    input.close();
                    clientSocket.close();
                }catch(Exception e){

                }}).start();
            }





        } catch (Exception e) {
        }




        //a while loop to take messages from each client and send it to everyone else but not the sender
        //close the server once every one has disconnectd
    }

}