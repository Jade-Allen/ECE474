/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece474;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author steve
 */
public class ECE474 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("C:\\data\\data.txt");
        Queue<String> nonInstr = new LinkedQueue();
        Queue<String> instr = new LinkedQueue();
        int tLines = 0;
        int iCount = 0;
        try {
            Scanner scan = new Scanner(file);
            int i = 0;
            String s = null;
            while (scan.hasNextLine()) {
                s = scan.nextLine();
                if (i < 2) {
                    if (i==0){
                        iCount = Integer.parseInt(s);
                    }
                    nonInstr.enqueue(s);
                } else if (i > 1 && i < 2+iCount) {
                    instr.enqueue(s);
                } else {
                    nonInstr.enqueue(s);
                }
                i++;
            }
            tLines = i;
        } catch (FileNotFoundException e) { //catches FNF exceptions
            System.out.println("File not found.");
        }
        int j = instr.size();
        for(int i = 0; i < tLines; i++ ){
            if(i > 1 && i < j+2){
                System.out.println(instr.dequeue());
            }
            else 
                System.out.println(nonInstr.dequeue());
        }
    }

}
