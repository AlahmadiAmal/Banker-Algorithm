/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker;

import java.io.FileReader;
import java.io.*;




public class Banker {

    public static void main(String[] args) throws IOException {

        int numProcess, numResources, lineCount = 0, process;
        int max[][], need[][], needReq[][], allocation[][], allocationReq[][];
        int available[], availableReq[], availablePrint[], request[], work[];
        boolean[] finish;
        String fileIn;
        String[] s;

        fileIn = " "; // path to data txt file

        FileReader fr = new FileReader(fileIn);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        numProcess = Integer.parseInt(line); // read number of processes from the file

        line = br.readLine();

        line = br.readLine();
        numResources = Integer.parseInt(line); // read number of resources from the file 

        line = br.readLine();

        allocation = new int[numProcess][numResources];
        max = new int[numProcess][numResources];
        need = new int[numProcess][numResources];
        available = new int[numResources];
        availablePrint = new int[numResources];
        request = new int[numResources];
        needReq = new int[numProcess][numResources];
        availableReq = new int[numResources];
        allocationReq = new int[numProcess][numResources];
        finish = new boolean[numProcess];

        //building the allocation matrix
        while (lineCount < numProcess) {
            for (int i = 0; i < numProcess; i++) {
               line = br.readLine();
               s=line.split(" ");
                for (int j = 0; j < numResources; j++) {
                        allocation[i][j] = Integer.parseInt(s[j]);
                }
                lineCount++;
            }
        }

        line = br.readLine(); //empty line
        lineCount = 0;

        //building the Max matrix
        while (lineCount < numProcess) {
            for (int i = 0; i < numProcess; i++) {
                line = br.readLine();
                s = line.split(" ");
                for (int j = 0; j < numResources; j++) {
                    max[i][j] = Integer.parseInt(s[j]);
                }
                lineCount++;
            }
        }

        line = br.readLine(); //empty line

        //building the Available vector
        line = br.readLine();
        s = line.split(" ");
        for (int i = 0; i < numResources; i++) {
            availableReq[i] = available[i] = availablePrint[i] = Integer.parseInt(s[i]);
        }

        line = br.readLine();

        //building Need matrix
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
                needReq[i][j] = max[i][j] - allocation[i][j];
            }
        }

        //Request 
        line = br.readLine();
        s = line.split("");
        request[0] = Integer.parseInt(s[2]);
        //For saving process number
        process = Integer.parseInt(s[0]);

        s = line.split(" ");
        for (int i = 1; i < numResources; i++) {
            request[i] = Integer.parseInt(s[i]);
        }

        fr.close();

        //Check request
        int counter = 0;
        for (int i = 0; i < numResources; i++) {
            if (request[i] <= needReq[process][i] && request[i] <= availableReq[i]) { //request must be < need + < available
                counter++;
            } 
            else {
                break;
            }
        }

        //change name of available to work
        work = available;

        // logic for safety sequence
        int SafeSeqVar = 0, c = 0;
        boolean safe = true;
        String[] safeSequence = new String[numProcess];
        while (SafeSeqVar != numProcess) {
            c++;
            for (int i = 0; i < numProcess; i++) {

                if (finish[i] == false) { //process not finished 
                    int numResCount = 0;
                    for (int j = 0; j < numResources; j++) {

                        if (need[i][j] <= work[j]) {
                            numResCount++;

                        } else {
                            break;
                        }
                    }
                    if (numResCount == numResources) {
                        safeSequence[SafeSeqVar] = "P" + i;
                        // to show the safe sequence 
                        // System.out.println(safeSequence[SafeSeqVar]);
                        finish[i] = true;
                        for (int k = 0; k < numResources; k++) {
                            work[k] = work[k] + allocation[i][k];
                        }
                        SafeSeqVar++;
                    }
                }
            }

            if (c == numProcess) {

                for (int i = 0; i < numProcess; i++) {

                    if (finish[i] == false) {
                        for (int j = 0; j < numResources; j++) {

                            if (need[i][j] >= work[j]) {
                                safe = false;
                            }
                        }
                    }
                }
                                    break;

            }
        }
    
        //Display number of processes and resources
        System.out.println("There are " + numProcess + " processes in the system.\n");
        System.out.println("There are " + numResources + " resource types.\n");

        //Display allocation array
        System.out.println("The Allocation Matrix is...");
        System.out.print("   ");
        for (int i = 0; i < numResources; i++) { //print ABCD
            char ch = (char) ((char) 65 + i);
            System.out.print(ch + " ");
        }
        System.out.println("");
        for (int i = 0; i < numProcess; i++) {
            System.out.print("" + i + ": ");
            for (int j = 0; j < numResources; j++) {
                System.out.print(allocation[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.print("\n");

        //Display max array
        System.out.println("The Max Matrix is...");
        System.out.print("   ");
        for (int i = 0; i < numResources; i++) { //print ABCD
            char ch = (char) ((char) 65 + i);
            System.out.print(ch + " ");
        }
        System.out.println("");
        for (int i = 0; i < numProcess; i++) {
            System.out.print("" + i + ": ");
            for (int j = 0; j < numResources; j++) {
                System.out.print(max[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("");

        //Display need array
        System.out.println("The Need Matrix is...");
        System.out.print("   ");
        for (int i = 0; i < numResources; i++) { //print ABCD
            char ch = (char) ((char) 65 + i);
            System.out.print(ch + " ");
        }
        System.out.println("");
        for (int i = 0; i < numProcess; i++) {
            System.out.print("" + i + ": ");
            for (int j = 0; j < numResources; j++) {
                System.out.print(need[i][j] + " ");
            }
            System.out.println("");
        }

        System.out.println("");

        //Display available vector
        System.out.println("The Available Vector is... ");
        for (int i = 0; i < numResources; i++) { //print ABCD
            char ch = (char) ((char) 65 + i);
            System.out.print(ch + " ");
        }
        System.out.println("");
        for (int i = 0; i < numResources; i++) {
            System.out.print(availablePrint[i] + " ");
        }

        System.out.println("\n");
        if (safe) {
            System.out.println("THE SYSTEM IS IN A SAFE STATE!\n");
        } else {
            System.out.println("THE SYSTEM IS 'NOT' IN A SAFE STATE!\n");
        }

        //Display the request vector
        System.out.println("The Request Vector is...");
        System.out.print("  ");
        for (int i = 0; i < numResources; i++) { //print ABCD
            char ch = (char) ((char) 65 + i);
            System.out.print(ch + " ");
        }
        System.out.println("");
        System.out.print(process + ":");
        for (int i = 0; i < numResources; i++) {
            System.out.print(request[i] + " ");
        }

        System.out.println("\n");

        //Display the state of the request
        if (counter == numResources) {
            for (int i = 0; i < numResources; i++) {
                availableReq[i] = availableReq[i] - request[i];
                allocationReq[process][i] = allocationReq[process][i] + request[i];
                needReq[process][i] = needReq[process][i] - request[i];
            }
            System.out.println("THE REQUEST CAN BE GRANTED!\n");
        } 
        else {
            System.out.println("THE REQUEST CAN 'NOT' BE GRANTED!\n");
        }

        //Display available vector
        System.out.println("The Available Vector is...");
        for (int i = 0; i < numResources; i++) { //print ABCD
            char ch = (char) ((char) 65 + i);
            System.out.print(ch + " ");
        }
        System.out.println("");
        for (int i = 0; i < numResources; i++) {
            System.out.print(availableReq[i] + " ");
        }

        System.out.println("");

    }

    
}

