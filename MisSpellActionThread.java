package assignment.dictionary;

import java.io.*;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */

public class MisSpellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    public MisSpellActionThread(DictionaryController controller) {
        this.controller = controller;
        textFileName = "src/main/resources/assignment/dictionary/check.txt";
        dictionaryFileName = "src/main/resources/assignment/dictionary/sampleDictionary.txt";
        myDictionary = new HashedMapAdaptor<>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;
    }

    @Override
    public void run() {
        loadDictionary(dictionaryFileName, myDictionary);
        Platform.runLater(() -> controller.SetMsg(dictionaryLoaded ? "The Dictionary has been loaded" : "No Dictionary is loaded"));
        checkWords(textFileName, myDictionary);
    }

    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
        try (Scanner input = new Scanner(new File(theFileName))) {
            while (input.hasNextLine()) {
                String word = input.nextLine().trim();
                if (!word.isEmpty()) {
                    theDictionary.add(word, word);  // Assuming word is both key and value for simplicity
                }
            }
            dictionaryLoaded = true;
        } catch (IOException e) {
            System.err.println("There was an error in reading or opening the file: " + theFileName);
            e.printStackTrace();
        }
    }

    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        try (Scanner input = new Scanner(new File(theFileName))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Scanner lineScanner = new Scanner(line);
                Line newLine = new Line();

                while (lineScanner.hasNext()) {
                    String word = lineScanner.next();
                    boolean correct = checkWord(word, theDictionary);
                    newLine.addWordlet(new Wordlet(word, correct));
                }

                myLines.addLine(newLine);
                showLines(myLines);

                lineScanner.close();
            }
        } catch (IOException e) {
            System.err.println("There was an error in reading or opening the file: " + theFileName);
            e.printStackTrace();
        }
    }

    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
        return theDictionary.contains(word);
    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> controller.UpdateView(lines));
        } catch (InterruptedException ignored) {}
    }
}

