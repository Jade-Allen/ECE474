/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece474;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Thread.sleep;
import java.util.Scanner;

/**
 *
 * @author steve
 */
public class ECE474 {

    /**
     * @param args the command line arguments
     */
//    public void waitAddSub(String operator) throws InterruptedException { // 1 cycle = 50 ms (20 cycles = 1 second)
//        if (operator.equals("+") || operator.equals("-")) {
//            Thread.sleep(100);
//        } else if (operator.equals("*")) {
//            Thread.sleep(500);
//        } else if (operator.equals("/")) {
//            Thread.sleep(2000);
//        }
//    }
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
                    if (i == 0) {
                        iCount = Integer.parseInt(s);
                    }
                    nonInstr.enqueue(s);
                } else if (i > 1 && i < 2 + iCount) {
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
//        int j = instr.size();
//        for(int i = 0; i < tLines; i++ ){
//            if(i > 1 && i < j+2){
//                System.out.println(instr.dequeue());
//            }
//            else 
//                System.out.println(nonInstr.dequeue());
//        }
        int[] iNon = new int[nonInstr.size()];
        int count = 0;
        while (nonInstr.size() > 0) { //makes an array filled with the non instructions from the file
            String s = nonInstr.dequeue();
            try {
                iNon[count] = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("File contained a non-integer");
                break;
            }
            count++;
        }
        //initializing objects and variables
        int cycle = 1;
        int[] rf = new int[8];                   //RF and Register Alias Table
        for (int i = 0; i < 8; i++) {
            rf[i] = iNon[i + 2];
        }
        int[] rat = new int[8];
        for (int i = 0; i < 8; i++){
            rat[i] = 42;
        }
        RS[] rs = new RS[5];
        for (int i = 0; i < 5; i++) {
            rs[i] = new RS();
        }
        String[] opcode = new String[5];
        int[] rsCycle = new int[5];
        for (int i = 0; i < 5; i++) {
            rs[i].setBusy(false);
        }
        boolean[] regFull = new boolean[8];

        while (iNon[1] >= cycle) { //main loop for cycling
            if (instr.size() > 0) {
                String s = instr.dequeue();
                String[] split = new String[4];
                split = s.split(" ");
                int[] iSplit = new int[4];

                try {//parses string to int and ends program if any non ints are present
                    for (int i = 0; i < 4; i++) {
                        iSplit[i] = Integer.parseInt(split[i]); //inserts values of 1 instruction in iSplit
                    }
                } catch (NumberFormatException e) {
                    System.out.println("File contained a non-integer");
                    break;
                }

                if (iSplit[0] == 0) { //opcode decoding
                    split[0] = "Add";
                } else if (iSplit[0] == 1) {
                    split[0] = "Sub";
                } else if (iSplit[0] == 2) {
                    split[0] = "Mul";
                } else if (iSplit[0] == 3) {
                    split[0] = "Div";
                } else { //if any values other than 0-3, ends program
                    System.out.println("Bad opcode");
                    break;
                }
                opcode[cycle - 1] = split[0];
                if (iSplit[1] < 0 || iSplit[1] > 7) { //checks for register values are within R 0-7
                    System.out.println("Bad register");
                    break;
                }
                split[1] = "R" + iSplit[1]; //to make the output statement
                if (iSplit[2] < 0 || iSplit[2] > 7) {
                    System.out.println("Bad register");
                    break;
                }
                split[2] = "R" + iSplit[2];// output statement
                if (iSplit[3] < 0 || iSplit[3] > 7) {
                    System.out.println("Bad register");
                    break;
                }

                split[3] = "R" + iSplit[3]; //output
                System.out.println(split[0] + "   " + split[1] + ", " + split[2] + ", " + split[3]); //outputs the instructions

                if (iSplit[0] == 0 || iSplit[0] == 1) {  //for cycling, checks if opcode is add / sub and sets appropriate cycling value
                    for (int i = 0; i < 3; i++) {
                        if (!rs[i].getBusy()) { //sends instruction to RS if there is an open slot
                            regFull[iSplit[1]] = true; //destination register is still processiing
                            rs[i].setOp(iSplit[0]);
                            rs[i].setVj(iSplit[2]);
                            rs[i].setVk(iSplit[3]);
                            rs[i].setBusy(true); //changes rs[i] to busy
                            rs[i].setDisp(2 + cycle);
                            rs[i].setDest(iSplit[1]); //destination register
                            rs[i].setSource1(iSplit[2]);
                            rs[i].setSource2(iSplit[3]);
                            rat[iSplit[1]] = i;   //updates RAT slot of destination with value of RS
                            break;
                        }
                    }
                }
                if (iSplit[0] == 2) { //same but for multiplication
                    for (int i = 3; i < 5; i++) {
                        if (!rs[i].getBusy()) { //sends instruction to RS if there is an open slot
                            regFull[iSplit[1]] = true;//destination register is still processiing
                            rs[i].setOp(iSplit[0]);
                            rs[i].setVj(iSplit[2]);
                            rs[i].setVk(iSplit[3]);
                            rs[i].setBusy(true); //changes rs[i] to busy
                            rs[i].setDisp(10 + cycle);
                            rs[i].setDest(iSplit[1]);
                            rs[i].setSource1(iSplit[2]);
                            rs[i].setSource2(iSplit[3]);
                            rat[iSplit[1]] = i;   //updates RAT slot of destination with value of RS
                            break;
                        }
                    }
                }

                if (iSplit[0] == 3) { //same but for division
                    for (int i = 3; i < 5; i++) {
                        if (!rs[i].getBusy()) { //sends instruction to RS if there is an open slot
                            regFull[iSplit[1]] = true;//destination register is still processiing
                            rs[i].setOp(iSplit[0]);
                            rs[i].setVj(iSplit[2]);
                            rs[i].setVk(iSplit[3]);
                            rs[i].setBusy(true); //changes rs[i] to busy
                            rs[i].setDisp(40 + cycle);
                            rs[i].setDest(iSplit[1]);
                            rs[i].setSource1(iSplit[2]);
                            rs[i].setSource2(iSplit[3]);
                            rat[iSplit[1]] = i;   //updates RAT slot of destination with value of RS
                            break;
                        }
                    }
                }
            }

            //Start of execution unit"s" (actually one unit). Checks if RS is ready to execute/dispatch
            for (int i = 0; i < 5; i++) {
                boolean valid = true;
                if (rs[i].getDisp() == cycle) {
                    rs[i].setBusy(false);
                    for (int j = 0; j < 5; j++) {
                        if (i == j) {
                            break;
                        } else if ((rs[i].getSource1() == rs[j].getDest() && rs[j].getBusy()) || rs[i].getSource2() == rs[j].getDest() && rs[j].getBusy()) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        if (rs[i].getOp() == 0) {           //add
                            rf[rs[i].getDest()] = rf[rs[i].getSource1()] + rf[rs[i].getSource2()];
                        } else if (rs[i].getOp() == 1) {    //sub
                            rf[rs[i].getDest()] = rf[rs[i].getSource1()] - rf[rs[i].getSource2()];
                        } else if (rs[i].getOp() == 2) {    //mul
                            rf[rs[i].getDest()] = rf[rs[i].getSource1()] * rf[rs[i].getSource2()];
                        } else if (rs[i].getOp() == 3) {    //div
                            rf[rs[i].getDest()] = rf[rs[i].getSource1()] / rf[rs[i].getSource2()];
                        }
                    }
                }
            }

            cycle++;
        } //end of cycle loop
        System.out.format("+------+--------+------+------+------+------+------+--------+%n");
        System.out.format("|  RS  |  Busy  |  Op  |  VJ  |  VK  |  QJ  |  QK  |  Disp  |%n");
        System.out.format("+------+--------+------+------+------+------+------+--------+%n");
        String leftAlignFormat = "| %-3s |   %-1b  | %-4d | %-4s | %-4s | %-4s | %-4s | %-4s |%n";
        for (int i = 0; i < 5; i++) {
            String s = "";
            if (rs[i].getBusy()) {
                s = "Busy";
            }
            if (rs[i].getBusy()) {
                s = "Free";
            }

            System.out.format(leftAlignFormat, "RS" + i, rs[i].getBusy(), rs[i].getOp(),
                    rs[i].getVj(), rs[i].getVk(), rs[i].getQj(), rs[i].getQk(), rs[i].getDisp());
        }
        System.out.format("+------+--------+------+------+------+------+------+--------+%n");
        System.out.println("");
        System.out.format("+-----+------+-------+%n");
        System.out.format("| Reg |  RF  |  RAT  |%n");
        System.out.format("+-----+------+-------+%n");
        leftAlignFormat = "|  %-1d  | %-4s | %-4s  |%n";
        for (int i = 0; i < 8; i++) {
            System.out.format(leftAlignFormat, i, rf[i], "RS" + rat[i]); //this cannot run until this print has the same amount of parameters as leftAlignFormat
        }
        System.out.format("+-----+------+-------+%n");

    }
}
