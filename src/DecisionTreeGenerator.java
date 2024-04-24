import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecisionTreeGenerator {

    static List<Case> cases;
    static int defaultDepth = 5;



    public static void dtTrain(String inputFile, String outputFile){


        try{
            File input = new File(inputFile);
            FileInputStream fileInputStream = new FileInputStream(input);
            Scanner scanner = new Scanner(fileInputStream);
            cases = Case.generateCases(scanner);
        } catch (FileNotFoundException fe) {
            System.out.println("file could not be found");
        }
        DecisionTree decisionTree = genDecisionTree(defaultDepth);
        try{
            File output = new File(outputFile);
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(decisionTree);
        } catch (IOException ex){
            System.err.println(ex);
        }


    }

    private static DecisionTree genDecisionTree(int depth){

        DecisionTree decisionTree = new DecisionTree(depth, cases);


        double total = 0;
        double correct = 0;

        for (Case current: cases){
            total++;
            if (decisionTree.checkCase(current))
                correct++;
        }



        return decisionTree;
    }





}



