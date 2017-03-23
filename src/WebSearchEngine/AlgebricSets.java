package WebSearchEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AlgebricSets {
    
    static public ArrayList<String> aDiffb(ArrayList<String>A,ArrayList<String>B){
        ArrayList<String> res = new ArrayList<>();
        Set<String> S = new HashSet<>();
        S.addAll(B);
        for(String word : A){
            if(!S.contains(word))
                res.add(word);
        }
        return res;
    }
}
