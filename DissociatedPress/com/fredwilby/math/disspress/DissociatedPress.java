package com.fredwilby.math.disspress;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class DissociatedPress
{
    private String text; 
    private int n;
    private Map<String, ArrayList<Integer>> map1;
    private Map<Integer, String> map2;
    
    public DissociatedPress(String text, int n_words)
    {
        this.text = text;
        this.n = n_words;
        
        map1 = new HashMap<String, ArrayList<Integer>>();
        map2 = new HashMap<Integer, String>();
        
        genMaps();
    }
    
    private void genMaps()
    {
        String[] words = text.split(" ");
        
        for(int x = 0; x < words.length - n + 1; x++)
        {
            StringBuilder ng = new StringBuilder();
            
            for(int y = 0; y < n; y++)
                ng.append(words[x+y]+" ");
            
            map2.put(x, ng.toString());
            
            if(map1.containsKey(ng.toString()))
            {
                map1.get(ng.toString()).add(x);
            } else
            {
                ArrayList<Integer> nList = new ArrayList<Integer>();
                nList.add(x);
                
                map1.put(ng.toString(), nList);
            }
            
        }
        
    }
    
    public String get(int length)
    {
        StringBuilder result = new StringBuilder();
        
        Random r = new Random();
        
        int len = 0, index = (Integer) map2.keySet().toArray()[r.nextInt(map2.keySet().size())];
        
        result.append(map2.get(index));
        
        while(len < length)
        {
            ArrayList<Integer> inds = map1.get(map2.get(index));
            index = inds.get(r.nextInt(inds.size()));
            
            if(index + n < map2.keySet().size())
            {
                index += n;
                result.append(map2.get(index));
                len++;
            } else
            {
                index = r.nextInt(map2.keySet().size());
            }
            
        }
        
        return result.toString();
    }
    
    
    public static void main(String[] args) throws FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        Scanner bfr = new Scanner(new File("ewk.txt"));
        
        while(bfr.hasNextLine())
        {
            String line = bfr.nextLine();
            
            line = line.replace("\\n", "");
            line = line.replace("*", "");
            line = line.replace("\\\"", "\"");
            
            text.append(line + " ");
        }
        
        bfr.close();
        
        DissociatedPress dp = new DissociatedPress(text.toString(), 3);
        
        String comm = "";
        
        for(int x = 0; x < 10; x++)
        {
            while(!comm.toLowerCase().contains("eradicated"))
                comm = dp.get(500)+"\n";
            
            System.out.println(comm);
            comm="";
        }

        
        
    }

}
