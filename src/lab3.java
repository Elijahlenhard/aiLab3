import java.io.*;
import java.util.List;
import java.util.Scanner;

public class lab3 {
    public static void main(String[] args) {
        if(args.length<1) return;

        if(args[0].equals("train")){
            if (args.length!= 4) return;
            String examples = args[1];
            String hypothesisOut = args[2];
            String type = args[3];

            switch (type.toLowerCase()) {
                case "dt" -> DecisionTreeGenerator.dtTrain(examples, hypothesisOut);
                case "ada" -> AdaBoost.adaTrain(examples, hypothesisOut);
            }

        }
        if(args[0].equals("predict")){
            if (args.length!=3) return;
            String hypothesis = args[1];
            String inputData = args[2];

            predict(hypothesis, inputData);
        }

    }

    public static void predict(String inputHyp, String inputData){
        try {
            FileInputStream hypInput = new FileInputStream(inputHyp);
            ObjectInputStream hypObjectInput = new ObjectInputStream(hypInput);
            Object hyp = hypObjectInput.readObject();
            if(hyp.getClass() == DecisionTree.class){
                DecisionTree dt = (DecisionTree) hyp;

                FileInputStream inputStream = new FileInputStream(inputData);
                Scanner sc = new Scanner(inputStream);
                List<Case> caseList = Case.generateCases(sc);
                double total = 0;
                double correct = 0;

                for(Case next : caseList){
                    System.out.println(dt.getDecision(next));
                    if(dt.checkCase(next)){
                        correct++;
                    }
                    total++;
                }
                System.out.println(correct/total);

            }

        } catch (IOException ex){
            System.err.println(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}