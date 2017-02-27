package spellchecker;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable {
    
    public static LinkedList<String>[] table;
    
    public static final int HASH_MAX 	 = SpellChecker.dictionary.size();
    public static int 		collisions   = 0;
    public static int 		longestChain = 0;
    public static int 		index;
    public static int		tempindex;
    
    HashTable (int n){      
        while (isPrime(n)){
            n++;
        }
        table 		= new LinkedList[n];
        this.table  = table;
    }
    
    public void putDictionary(ArrayList<String> a){
        for (String s : a){
        	int x; 
        	
        	s = s.toLowerCase();        
            x = getHashIndex(s); 
            if (table[x] == null){
                table[x] = new LinkedList<String>();
                table[x].add(s);
            } else {
                table[x].add(s);
                collisions++;
                
                if (table[x].size() > longestChain){
                    longestChain = table[x].size();
                    tempindex = x;
                }
            }
        }
    }
    
    // return true if table contains s
    public static boolean contains(String s){
        
        boolean contains = false;
        if (table[getHashIndex(s)] != null){
            contains = table[getHashIndex(s)].contains(s.toLowerCase());
        }
        
        return contains;
    }
    
    //generates unique hash number
    public static int getHashIndex(String s){
        int mult;
        int size;
        index 	= 0;
        s 		= s.toLowerCase();
        
        for (int i = 0; i < s.length(); i++){
            index = index * 923 + s.charAt(i);
        }
        
        index = Math.abs(index);
        return index % HASH_MAX;
    }
    
    //creates char numbers 1-26
    public static int assignCharNum(char a){
        int charNum;
        charNum = Character.toUpperCase(a) - 'A' + 1;
        return charNum;
    }
    
    public static boolean isPrime(int x){
        if (x % 2 == 0) {
            return false;
        }      
        for(int i = 3; i * i <= x; i += 2) {
            if( x % i == 0) {
                return false;
            }
        }       
        return true;
    }   
}

