/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pkg22212337_daniel_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Daniel García
 * 12/10/2024
 */
public class Client {
    private static InetAddress host;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try 
        {
           host = InetAddress.getLocalHost();
        } 
        catch(UnknownHostException e) 
        {
           System.out.println("Host ID not found!");
           System.exit(1);
        }
        run();
    }
    
   private static void run() 
   {
        Socket link = null;
        String message=null;
        String response= null;
        try 
        {
            link = new Socket(host,PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));//Step 2.
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);

            //Set up stream for keyboard entry...
            BufferedReader userEntry =new BufferedReader(new InputStreamReader(System.in));
            
            do{
                System.out.println("Enter message to be sent to server: ");
                message = userEntry.readLine();
                out.println(message);
                response = in.readLine();
                System.out.println("\nSERVER RESPONSE> " + response);
            }while(!message.equalsIgnoreCase("STOP"));
        } 
        catch(IOException e)
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                System.out.println("\n* Closing connection... *");
                link.close();
            }catch(IOException e)
            {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
    } // finish run method
}
