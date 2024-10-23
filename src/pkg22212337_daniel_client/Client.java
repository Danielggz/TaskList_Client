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
        int option = 0;
        String response;
        try 
        {
            link = new Socket(host,PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            PrintWriter out = new PrintWriter(link.getOutputStream(),true);
            BufferedReader bfReader =new BufferedReader(new InputStreamReader(System.in));
            do{
                String taskDescription, requestMessage, taskDate;
                taskDescription = taskDate = "";
                SimpleDateFormat sdf; //date formatter
                System.out.println("\nEnter action to request from server: "
                        + "\n 1. ADD"
                        + "\n 2. LIST"
                        + "\n 3. STOP");
                try{
                    option = Integer.parseInt(bfReader.readLine());
                    //Throw custom exception if option is not in range
                    if(option < 1 || option > 3 ){
                        throw new IncorrectActionException("Please only select 1 for Add, 2 for List or 3 to Stop");
                    }
                }catch(NumberFormatException nfe){
                    System.out.println("Option must be a number: " + nfe.getMessage());
                    continue;
                }catch(IncorrectActionException iae){
                    System.out.println(iae.getMessage());
                    continue;
                }
                switch(option){
                    case 1:
                        System.out.println("<-------NEW TASK------->");
                        //Loop while description is not correctly introduced by user
                        while(taskDescription.equals("")){
                            System.out.println("Enter description");
                            try{
                                taskDescription = bfReader.readLine();
                                if(taskDescription.equals("")){
                                    throw new IncorrectActionException("Description is a required field.");
                                }
                            }catch(IncorrectActionException iae){
                                System.out.println(iae.getMessage());
                            }
                        }
                        while(taskDate.equals("")){
                            System.out.println("Enter date (dd/mm/yyyy)");
                            //Format date to SimpleDateFormat to check if its introduced correctly
                            try{
                                sdf = new SimpleDateFormat("dd/MM/yyyy");
                                sdf.setLenient(false); //For strict parsing
                                taskDate = bfReader.readLine();
                                sdf.parse(taskDate);
                            }catch(ParseException pe){
                                System.out.println("Error found. " + taskDate + " is not a valid date. Date must be in dd/mm/yyyy ");
                                taskDate = ""; //Date is incorrect so its set to blank to continue loop
                            }
                        }
                        
                        //Construct String to send to server
                        requestMessage =  "ADD," + taskDescription + "," + taskDate;
                        out.println(requestMessage);
                        break;
                    case 2:
                        System.out.println("<-------LIST TASKS------->");
                        while(taskDate.equals("")){
                            System.out.println("Enter date (dd/mm/yyyy)");
                            //Format date to SimpleDateFormat to check if its introduced correctly
                            try{
                                sdf = new SimpleDateFormat("dd/MM/yyyy");
                                sdf.setLenient(false); //For strict parsing
                                taskDate = bfReader.readLine();
                                sdf.parse(taskDate);
                            }catch(ParseException pe){
                                System.out.println("Error found. " + taskDate + " is not a valid date. Date must be in dd/mm/yyyy ");
                                taskDate = ""; //Date is incorrect so its set to blank to continue loop
                            }
                        }
                        requestMessage = "LIST," + taskDate;
                        out.println(requestMessage);
                        break;
                    case 3:
                        out.println("STOP"); //Send just the stop request to end communication
                        break;
                }
                response = in.readLine();
                System.out.println("\nSERVER RESPONSE> " + response);
            }while(option != 3);
        } 
        catch(IOException e)
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                System.out.println("Closing connection...");
                link.close();
            }catch(IOException e)
            {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
    } // finish run method
}
