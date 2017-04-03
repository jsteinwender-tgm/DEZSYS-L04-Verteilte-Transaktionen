package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * client
 * make connection to server
 * will receive msg from others
 * input is outsourced to KeyboardInput class
 * <p>
 * Created by steinwender on 26.11.2016.
 */
public class Client {
    private String hostName;
    private int portNumber;

    /**
     * constructor
     */
    public Client() {
    }

    /**
     * start socket connection to server
     * get msg sent from other clients
     * start a new thread for user input
     */
    public void connection() {
        try (
                Socket socket = new Socket(hostName, portNumber);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {

            String fromServer;
            String input = null;
            while (true) {
                input = sc.next();
                //send to server
                out.println(input);

                //get from server
                fromServer = in.readLine();
                System.out.println(fromServer);

                // receive doCommit or doAbort
                fromServer = in.readLine();
                System.out.println(fromServer);

                if (fromServer.equals("doCommit")) {
                    // receive ACK or NCK
                    fromServer = in.readLine();
                    System.out.println(fromServer);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    /**
     * main method
     * create new object and call connection()
     *
     * @param args hostname(ip) and port number to share
     */
    public static void main(String[] args) {
        Client c1 = new Client();
        c1.connection();
    }
}