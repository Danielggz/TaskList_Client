/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pkg22212337_daniel_client;

/**
 * @author Daniel García
 * 15/10/2024
 */
public class IncorrectActionException extends Exception{
    String message = "Action selected is not valid. Please select 1 for Add, 2 for List or 3 to Stop";

    public IncorrectActionException() {
    }

    public IncorrectActionException(String message) {
        super(message);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
