import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

// TODO: 4/8/2020 put better comments on getWords and fix bugs of adding the same word many times (adds the word everytime it occurs) -> compare new written method and old, throw stuff in hash sooner, so they can truly be compared
    //put all words, with repeats, in wordList, then avoid adding repeats in addToTable method

public class WordScanner {
    String contents;
    HashTable table = new HashTable();
    ArrayList<String> wordList = new ArrayList<>();

    public WordScanner(String inputFile) throws IOException {
        contents = fileContents(inputFile + ".txt");
    }

    public boolean test(String word) {
        return table.inTable(word);
    }


    public void getWords() {
        StringTokenizer token = new StringTokenizer(contents);
        //while there are more contents to iterate over
        while (token.hasMoreTokens()) {
            String curWord = token.nextToken();

            //check if the word is already in the table
            if (!table.inTable(curWord)) {

                //if this isn't just one word
                if (!isSingleWord(curWord)) {
                    //Get only the alphabetical characters
                    String[] words = curWord.split("\\P{Alpha}");
                    for (String word : words) {
                        //keep replaced characters from being added
                        if (word.length() > 0 && !table.inTable(word)) {
                            //addToTable(curWord);
                            wordList.add(word);
                        }
                    }
                }
                else {
                    //addToTable(curWord);
                    wordList.add(curWord);
                }
            }
        }
    }

    public void addToTable() {
        for (String s : wordList) {
            if (!table.inTable(s)) {
                int frequency = 0;
                StringTokenizer token = new StringTokenizer(contents);
                //while there are more contents to iterate over
                while (token.hasMoreTokens()) {
                    //word currently being examined
                    String curWord = token.nextToken();
                    //if it is a single word and it equals the word being searched for, increase frequency
                    if (isSingleWord(curWord) && curWord.equals(s)) {
                        frequency++;
                    }
                    //if not a single word, break up into single words
                    else if (!isSingleWord(curWord)) {
                        String[] multiWord = curWord.split("\\P{Alpha}");
                        //compare all of these words from the broken string to the word being searched for. If it matches, increase frequency
                        for (String singleWord : multiWord) {
                            if (singleWord.equals(s))
                                frequency++;
                        }
                    }
                }
                table.add(s, frequency);
            }
        }
    }


    //Read file contents
    //I did just take this from my submission of programming project 2, since we had to do this exact same step
    public String fileContents(String InputFile) throws IOException {
        FileReader fr = new FileReader(InputFile);

        int index;

        //start with nothing
        String st = "";
        //scan while there are characters left to scan, add to end of string
        while ((index = fr.read()) != -1) {
            st += (char) index;
        }
        //return everything lowercase
        return st.toLowerCase();
    }

    //checks to see if a word is just letters
    public boolean isSingleWord(String s) {
        s = s.toLowerCase();
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 'a' || arr[i] > 'z')
                return false;
        }
        return true;
    }


    /*
     public void getWords() {
        StringTokenizer token = new StringTokenizer(contents);
        //while there are more contents to iterate over
        while (token.hasMoreTokens()) {
            ArrayList<String> wordList = new ArrayList<>();
            String curWord = token.nextToken();

            //check if the word is already in the table
            if (!table.inTable(curWord)) {

                //if this isn't just one word
                if (!isSingleWord(curWord)) {
                    //Get only the alphabetical characters
                    String[] words = curWord.split("\\P{Alpha}");
                    for (String word : words) {
                        //keep replaced characters from being added
                        if (word.length() > 0 && !table.inTable(word)) {
                            addToTable(curWord);
                            wordList.add(word);
                        }
                    }
                }
                else {
                    addToTable(curWord);
                    wordList.add(curWord);
                }
            }
        }
    }

    public void addToTable(String word) {
            int frequency = 0;
            StringTokenizer token = new StringTokenizer(contents);
            //while there are more contents to iterate over
            while (token.hasMoreTokens()) {
                //word currently being examined
                String curWord = token.nextToken();
                //if it is a single word and it equals the word being searched for, increase frequency
                if (isSingleWord(curWord) && curWord.equals(word)) {
                        frequency++;
                }
                //if not a single word, break up into single words
                else if (!isSingleWord(curWord)) {
                    String[] multiWord = curWord.split("\\P{Alpha}");
                    //compare all of these words from the broken string to the word being searched for. If it matches, increase frequency
                    for (String singleWord : multiWord) {
                        if (singleWord.equals(word))
                            frequency++;
                        }
                }
            table.add(word, frequency);
        }
    }
     */
}
