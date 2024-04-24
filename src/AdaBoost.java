import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdaBoost {
    public static void adaTrain(String inputFile, String outputFile){

        List<Case> cases;

        try {
            File input = new File(inputFile);
            FileInputStream inputStream = new FileInputStream(input);
            Scanner sc = new Scanner(inputStream);
            cases = Case.generateCases(sc);

            
        } catch (IOException ex){

        }


    }

}
