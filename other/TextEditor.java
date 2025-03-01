package Practice.other;

import java.util.*;

public class TextEditor {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.addText("This is an example text that will be wrapped after 40 characters per line.");
        editor.printText();
        System.out.println("--- Insert Text ---");
        editor.insertText("INSERTED ", 0, 5);
        editor.printText();
        System.out.println("--- Delete Text ---");
        editor.deleteText(0, 5, 8);
        editor.printText();
    }
    private static final int LINE_WIDTH = 40;

    private List<String> lines;

    public TextEditor() {
        lines = new ArrayList<>();
    }
    public void addText(String text) {
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > LINE_WIDTH) {
                lines.add(currentLine.toString().trim());
                currentLine.setLength(0);  // Clear the current line
            }
            currentLine.append(word).append(" ");
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }
    }
    public void insertText(String text, int line, int position) {
        if (line < lines.size()) {
            String currentLine = lines.get(line);
            String updatedLine = currentLine.substring(0, position) + text + currentLine.substring(position);
            lines.set(line, updatedLine);
        }
    }
    public void deleteText(int line, int position, int length) {
        if (line < lines.size()) {
            String currentLine = lines.get(line);
            String updatedLine = currentLine.substring(0, position) + currentLine.substring(position + length);
            lines.set(line, updatedLine);
        }
    }
    public void printText() {
        for (String line : lines) {
            System.out.println(line);
        }
    }
}