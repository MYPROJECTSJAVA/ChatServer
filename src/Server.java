import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    //a variable to save the output object for each client
    static ServerSocket server;
    static String inMessage="";
    static String outMessage="";
    public static void main(String args[]) {
        try {
            //create a server object
            ServerSocket server=new ServerSocket(5000);
            System.out.println("Server running at port 5000");
            //try and catch make a port as server

            //a infinite while loop to get as many client sockets as possible
            // launch a new thread for each client

            while(true) {




                //objects used to send and receive a particular input and output to the respective client
                new Thread(()->{try{
                    Socket clientSocket = server.accept();

                    System.out.println("ClientSocket connected");
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    output.println("Welcome to the Group Chat");
                    while ((inMessage = input.readLine()) != "stop") {
                        System.out.println("Read message");
                        System.out.println(inMessage);
                        output.println("Received");
                        System.out.println("Message sent");
                    }
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