package com.toddschiller.experiments;

import com.toddschiller.checker.qual.Encrypted;

public class HungarianExample {

    @SuppressWarnings("hungarian") // Trust the return value of this method
    public @Encrypted String encrypt(String str) {
        char xs[] = str.toCharArray();
        for (int i = 0; i < xs.length; i++){
            xs[i] = (char) (xs[i] + 2);
        }
        return String.valueOf(xs);
    }
    
    public void sendOverNetwork(String eMessage){
        System.out.println("Sending message: " + eMessage);
    }
    
    public static void main(String [] args){
        HungarianExample instance = new HungarianExample();
        
        String msg = "Secret message";
        
        // Warning! By default string literals are unencrypted
        String eMsg = msg;
        
        // Warning! By default string literals are unencrypted
        instance.sendOverNetwork(msg);
        
        msg = instance.encrypt(msg);
        
        // This is OK, the checker knows that msg now holds an encrypted value
        instance.sendOverNetwork(msg);
    }
}
