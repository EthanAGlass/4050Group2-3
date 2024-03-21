package assignment.dictionary;

import javafx.scene.shape.Line;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;


/**
 * A class that will be used to display the lines of text that are corrected.
 *
 */

public class LinesToDisplay {

    public static final int LINES = 10;     // Display 10 lines
    private AList<AList<Wordlet>> lines;  // Each line contains a list of Wordlets
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay
     */
    public LinesToDisplay() {
        lines = new AList<>(LINES);
        for (int i = 0; i < LINES; i++) {
            lines.add(new AList<>());  // Initialize each line as an empty list of Wordlets
        }
        currentLine = 0;
    }

    /**
     * Add a new wordlet to the current line.
     */
    public void addWordlet(Wordlet w) {
        lines.getEntry(currentLine).add(w);  // Add the Wordlet to the current line
    }

    /**
     * Go to the next line, if the number of lines has exceeded LINES, shift
     * them all up by one
     */
    public void nextLine() {
        currentLine++;
        if (currentLine >= LINES) {
            lines.remove(0);  // Remove the oldest line
            lines.add(new AList<>());  // Add a new empty line at the end
            currentLine = LINES - 1;  // Reset current line to the last position
        }
    }


    public int getCurrentLine() {
        return currentLine;
    }

    public AList<AList<Wordlet>> getLines() {
        return lines;
    }

    public void addLine(Line newLine) {
    }
}

