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
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        String action;
        String response = null;
        try 
        {
            link = new Socket(host,PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));//Step 2.
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);

            //Set up stream for keyboard entry...
            BufferedReader bfReader =new BufferedReader(new InputStreamReader(System.in));
            
            do{
                String taskName, taskDescription, requestMessage, taskDate;
                taskName = taskDescription = requestMessage = taskDate = "";
                SimpleDateFormat sdf = null;
                boolean noAction = false;
                System.out.println("Enter action to request from server: ADD/LIST/STOP");
                action = bfReader.readLine();
                switch(action.toUpperCase()){
                    case "ADD":
                        System.out.println("<-------NEW TASK------->");
                        System.out.println("Enter name");
                        taskName = bfReader.readLine();
                        System.out.println("Enter description");
                        taskDescription = bfReader.readLine();
                        System.out.println("Enter date (dd/mm/yyyy)");
                        //Format date to SimpleDateFormat to check if its introduced correctly
                        try{
                            sdf = new SimpleDateFormat("dd/MM/yyyy");
                            taskDate = bfReader.readLine();
                            sdf.parse(taskDate);
                        }catch(ParseException pe){
                            System.out.println("Error found." + taskDate + " is not a valid date: " + pe.getMessage());
                        }
                        //Construct String to send to server
                        requestMessage = action + "," + taskName + "," + taskDescription + "," + taskDate;
                        out.println(requestMessage);
                        break;
                    case "LIST":
                        System.out.println("<-------LIST TASKS------->");
                        System.out.println("Enter date (dd/mm/yyyy)");
                        //Format date to SimpleDateFormat to check if its introduced correctly
                        try{
                            sdf = new SimpleDateFormat("dd/MM/yyyy");
                            taskDate = bfReader.readLine();
                            sdf.parse(taskDate);
                        }catch(ParseException pe){
                            System.out.println("Error found." + taskDate + " is not a valid date: " + pe.getMessage());
                        }
                        requestMessage = action + "," + taskDate;
                        out.println(requestMessage);
                        break;
                    case "STOP":
                        out.println("STOP"); //Send just the stop request to end communication
                        break;
                    default:
                        noAction = true;
                        break;
                }
                
                if(!noAction){
                    response = in.readLine();
                    System.out.println("\nSERVER RESPONSE> " + response);
                }else{
                    System.out.println("Please introduce a valid action");
                }
                
            }while(!action.equalsIgnoreCase("STOP"));
        } 
        catch(IOException e)
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                System.out.println("\nClosing connection...");
                link.close();
            }catch(IOException e)
            {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
    } // finish run method
}
