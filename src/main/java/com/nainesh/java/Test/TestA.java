package com.nainesh.java.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nainesh
 */
public class TestA {
    public static void main(String[] args) {
        String a = "#";
        int n = a.length();
        List<String> strs = new ArrayList<>();
        String pre = "";
        for(char ch: a.toCharArray()){
            if(ch!='#'){
                pre+=ch;
            }else{
                strs.add(pre);
                pre="";
            }
        }
        strs.add(pre);

        System.out.println(strs.size());
    }
}
