package edu.gwu.cs6461.project1.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhaoq
 */
import java.util.HashMap;
public class Compiler {
    private HashMap<String,Integer> map = new HashMap<>();
    // insert the instruction indentifiers to a hash table
    Compiler(){
        map.put("LDR",1);
        map.put("STR",2);
        map.put("LDA",3);
        map.put("LDX",33);
        map.put("STX",34);
        map.put("JZ",8);
        map.put("JNE",9);
        map.put("JCC",10);
        map.put("JMA",11);
        map.put("JSR",12);
        map.put("RFS",13);
        map.put("SOB",14);
        map.put("JGE",15);
        map.put("AMR",4);
        map.put("SMR",5);
        map.put("AIR",6);
        map.put("SIR",7);
        map.put("MLT",16);
        map.put("DVD",17);
        map.put("TRR",18);
        map.put("AND",19);
        map.put("ORR",20);
        map.put("NOT",21);
        map.put("SRC",25);
        map.put("RRC",26);
        map.put("IN",49);
        map.put("OUT",50);
        map.put("CHK",51);
        map.put("FADD",27);
        map.put("FSUB",28);
        map.put("VADD",29);
        map.put("VSUB",30);
        map.put("CNVRT",31);
        map.put("LDFR",40);
        map.put("STFR",41);
    }
    // compile the instructions
    public String compile(String instruction){
        String binaryRepresentation=""; //store the return value
        //split the instructions to single-line instruction
        instruction = instruction.replace("\n", ""); //eliminate the "\n" in the instruction lines
        String[] instruction_lines = instruction.split(";");
        //compile each instruction and conbine the result
        for(String instruction_line: instruction_lines){
            // split the single-line instruction to multiple components
            String[] instruction_components = instruction_line.split(",");
            //extract the opcode and operand from the first component
            String[] firstComponent = instruction_components[0].split(" ");
            String[] firstPart = new String[2];
            int j = 0;
            for(String element:firstComponent){
                if(!element.equals("")){
                    firstPart[j++] = element;
                }
            }
            //select the coresponding way to compile the single-line instruction
            int opCode = map.get(firstPart[0]);
            switch(opCode){
                //the number of various structures of instructions is 10
                case 1:
                case 2:
                case 3:
                case 8:
                case 9:
                case 14:
                case 15:
                case 4:
                case 5:
                    String binaryRe1;//store the binary representation of single-line instruction
                    String element11 = Integer.toBinaryString(opCode);;//binary representation of first element of the instruction
                    String element12 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));
                    // eliminate all the " " in the string
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    String element13 = Integer.toBinaryString(Integer.parseInt(instruction_components[1]));
                    instruction_components[2] = instruction_components[2].replaceAll("\\s", "");
                    instruction_components[2] = instruction_components[2].replace("[", "");
                    String element14 = Integer.toBinaryString(Integer.parseInt(instruction_components[2]));
                    String element15 = "0";
                    if(instruction_components.length == 4){
                        instruction_components[3] = instruction_components[3].replaceAll("\\s", "");
                        instruction_components[3] = instruction_components[3].replace("]", "");
                        element15 = Integer.toBinaryString(Integer.parseInt(instruction_components[3]));
                    }
                    // complement 0 to make each element's length equal regualted length
                    while(element11.length()<6){
                        element11 = "0" + element11;
                    }
                    while(element12.length()<2){
                        element12 = "0" + element12;
                    }
                    while(element13.length()<2){
                        element13 = "0" + element13;
                    }
                    while(element14.length()<5){
                        element14 = "0" + element14;
                    }
                    // combine the element to one line
                    binaryRe1 = element11 + element12 + element13 + element15 + element14;
                    binaryRepresentation = binaryRepresentation + binaryRe1 + "\n";
                    break;
                case 33:
                case 34:
                case 11:
                case 12:
                    String binaryRe2;//store the binary representation of single-line instruction
                    String element21 = Integer.toBinaryString(opCode);;//binary representation of first element of the instruction
                    String element22 = "00";
                    String element23 = Integer.toBinaryString(Integer.parseInt(firstPart[1]));
                    // eliminate all the " " in the string
                    instruction_components[1] = instruction_components[1].replaceAll("\\s", "");
                    instruction_components[1] = instruction_components[1].replace("[", "");
                    String element24 = Integer.toBinaryString(Integer.parseInt(instruction_components[1]));
                    String element25 = "0";
                    if(instruction_components.length == 3){
                        instruction_components[2] = instruction_components[2].replaceAll("\\s", "");
                        instruction_components[2] = instruction_components[2].replace("]", "");
                        element25 = Integer.toBinaryString(Integer.parseInt(instruction_components[2]));
                    }
                    // complement 0 to make each element's length equal regualted length
                    while(element21.length()<6){
                        element21 = "0" + element21;
                    }
                    while(element22.length()<2){
                        element22 = "0" + element22;
                    }
                    while(element23.length()<2){
                        element23 = "0" + element23;
                    }
                    while(element24.length()<5){
                        element24 = "0" + element24;
                    }
                    // combine the element to one line
                    binaryRe2 = element21 + element22 + element23 + element25 + element24;
                    binaryRepresentation = binaryRepresentation + binaryRe2 + "\n";
                    break;
                case 10: break;
                case 13: break;
                case 6:
                case 7: break;
                case 16:
                case 17:
                case 18:
                case 19:
                case 20: break;
                case 21: break;
                case 25:
                case 26: break;
                case 49:
                case 50:
                case 51: break;
                case 27:
                case 28:
                case 29:
                case 30:
                case 40:
                case 41: break;
                case 31: break;

            }
        }
        return binaryRepresentation;

    }
}


