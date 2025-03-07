package com.samson.projectserviceintelliserver.lib;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    
    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println(LocalDateTime.parse(formatter.format(now), formatter));
    }
    
}
