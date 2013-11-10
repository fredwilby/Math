package com.fredwilby.math.disspress;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

public class EwkDownloader
{

    public static void main(String[] args)
    {
        String URI = "http://reddit.com/user/ewk/comments.json?sort=new&limit=100&after=";
        String after = "";
        boolean done = false;
        int i = 0; 
        
        StringBuilder ewkbabble = new StringBuilder();
        
        while(i < 1000 && !done)
        {
        try
        {
        HttpURLConnection huc = (HttpURLConnection) new URL(URI + after).openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        
        BufferedReader bf = new BufferedReader(new InputStreamReader(huc.getInputStream()));
        Scanner input = new Scanner(bf);
        
        StringBuilder result = new StringBuilder();        
        while(input.hasNextLine())
        {
            result.append(input.nextLine()+"\n");
        }
        input.close();
        bf.close();
        
        if(result.length() == 0)
        {
            done = true;
        }
        
        JsonStreamParser jsp = new JsonStreamParser(result.toString());
        
        JsonArray elem = jsp.next().getAsJsonObject().getAsJsonObject("data").getAsJsonArray("children");


        
        for(int x = 0; x < elem.size(); x++)
        {
            ewkbabble.append(elem.get(x).getAsJsonObject().get("data").getAsJsonObject().get("body"));
            after = elem.get(x).getAsJsonObject().get("data").getAsJsonObject().get("name").getAsString();
        }
        
        System.out.println(i++);
        Thread.sleep(2000);
        
        
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        }
        
        try {
        PrintWriter bfw = new PrintWriter(new File("ewk.txt"));
        
        bfw.println(ewkbabble.toString());
        bfw.flush();
        
        bfw.close();
        
        } catch(Exception e) {}
        
    }
    
    private static RedditComment parseComment(JsonObject comment)
    {
        RedditComment result = new RedditComment(comment.get("body_html").getAsString(), comment.get("author").getAsString(),
                comment.get("ups").getAsInt(), comment.get("downs").getAsInt(), comment.get("created_utc").getAsString());
        
        return result;
    }
    
    
    
}
