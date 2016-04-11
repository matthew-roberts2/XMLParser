package xml;

import util.FileInput;
import util.FileOutput;

import java.util.Scanner;

public class XMLDriver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an XML file name to be checked.");
        String nextIn = scanner.nextLine();
        FileInput fileInput = new FileInput();
        FileOutput fileOutput = new FileOutput();
        while(!nextIn.equalsIgnoreCase("q")){
            while(!fileInput.setFile(nextIn)){
                System.out.println("Invalid file. Please check the name and location on disk and try again.");
                nextIn = scanner.nextLine();
                if(nextIn.equalsIgnoreCase("q")) return;
            }
            System.out.println("Enter an output XML file to be written to");
            nextIn = scanner.nextLine();
            while(!fileOutput.setFile(nextIn)){
                System.out.println("Invalid file. Please check the name and try again.");
                nextIn = scanner.nextLine();
                if(nextIn.equalsIgnoreCase("q")) return;
            }
            XMLCheck check = new XMLCheck(fileInput, fileOutput);
            if(check.doCheck()){
                System.out.println(nextIn + " is a well-formed XML file.");
            }else{
                System.out.println(nextIn + " is a malformed XML file. Error found on line " + check.getErrorLine());
            }

            System.out.println("Enter an XML file name to be checked.");
            nextIn = scanner.nextLine();
        }
    }
}
