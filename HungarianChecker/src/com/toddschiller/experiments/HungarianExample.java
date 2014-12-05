package com.toddschiller.experiments;

import com.toddschiller.checker.qual.Safe;
import com.toddschiller.checker.qual.Unsafe;

public class HungarianExample {

    @SuppressWarnings("hungarian") // Trust the return value of this method
    public @Safe String encode(String str) {
        return str.replaceAll("'", "\\'");
    }
    
    @SuppressWarnings("hungarian") // Trust the return value of this method
    public @Unsafe String getUserInput(){
        return "' drop tables;";
    }
    
    public void executeSqlQuery(String sQuery){
        System.out.println("Executing query: " + sQuery);
    }
    
    public void example(){
        String user = getUserInput();
        
        // WARNING: user is known to be @Unsafe
        executeSqlQuery("SELECT * FROM table WHERE user='" + user + "'");
        
        user = encode(user);
        
        // SAFE: user is known to be @Safe
        executeSqlQuery("SELECT * FROM table WHERE user='" + user + "'");
    }
    
    public static void main(String [] args) {
        // NOP
    }
}
