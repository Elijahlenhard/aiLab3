import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Case implements Serializable {
    boolean isEng;


    /**
     * indexes:
     * 0 - Case contains word "is"
     * 1 - Case contains 2 instances of "en" substring
     * 2 - Case contains a word with double vowel
     * 3 - Case contains the substring the
     * 4 - Case contains the word "de"
     * 5 - Case contains the word "to"
     */
    boolean[] data;


    public Case(boolean isEng ){
        this.isEng = isEng;
        data = new boolean[6];
    }

    public static List<Case> generateCases(Scanner scanner){

        ArrayList<Character> vowels = new ArrayList<>();

        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');


        List<Case> casesGen = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            boolean isEng = line.charAt(0) == 'e';

            Case next = new Case(isEng);


            String[] words = line.substring(3).split(" ");

            int enInstances =0;

            //Get attributes
            for (int i = 0; i < words.length; i++) {
                if(words[i].equalsIgnoreCase("is")){
                    next.data[0] = true;
                }
                if(words[i].toLowerCase().contains("en")){
                    enInstances++;
                }
                for (int j = 0; j < words[i].length()-1; j++) {
                    char char1 = words[i].toLowerCase().charAt(j);
                    char char2 = words[i].toLowerCase().charAt(j+1);
                    if(char1 == char2){
                        if (vowels.contains(char1)){
                            next.data[2] = true;
                        }
                    }
                }
                if(words[i].equalsIgnoreCase("de"))
                    next.data[4] = true;
                if(words[i].equalsIgnoreCase("to"))
                    next.data[5] = true;

            }
            if(line.toLowerCase().contains("the"))
                next.data[3] = true;

            if(enInstances>=2){
                next.data[1] = true;
            }

            casesGen.add(next);


        }
        return casesGen;

    }

}