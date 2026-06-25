package com.nainesh.java.FileOps;

import java.io.*;
import java.nio.file.Paths;

/**
 * @author Nainesh
 */
public class ReadWriteToFile {
    public static void main(String[] args) throws Exception{

//        InputStreamReader instream = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(instream);
//        System.out.println("Enter something");
//        int n = Integer.parseInt(br.readLine());
//        System.out.println("Entered "+n);

        File file = new File(".\\src\\main\\java\\com\\nainesh\\java\\FileOps\\input.txt");
//        for (String fileNames : file.list())
//            System.out.println(fileNames);
//        System.out.println(file.getCanonicalFile());
       FileReader fr = new FileReader(file);
       BufferedReader bfr = new BufferedReader(fr);
       String line ="";
       while((line = bfr.readLine())!=null){
           System.out.println(line);
       }

       FileWriter fw = new FileWriter(".\\src\\main\\java\\com\\nainesh\\java\\FileOps\\input.txt", true);
       BufferedWriter br = new BufferedWriter(fw);

       br.newLine();
       br.write("j");
       br.newLine();
       br.write("Third line");
       br.flush();

    }
}
