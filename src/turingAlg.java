import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class turingAlg {
    public static void main(String[] args) throws IOException {
        int steps = 0;
        int currentState = 0;
        char currentValue;
        String[][] matrix;
        int startingPoint = 0;
        char[] values = new char[0];
        char[] charTape = new char[0];
        char[] tape = new char[200];
        FileReader file = new FileReader("src/input.txt");
        Scanner scan = new Scanner(file);
        String s = scan.nextLine();
        StringBuilder transitions = new StringBuilder("");
        String[] trans;
        int a = 1;
        int numberOfStates = 0;
        while (scan.hasNextLine()) {
            if (s.matches("(>>>).*")) {
                if (a == 1) {
                    values = s.substring(3).toCharArray();
                    a++;
                } else if (a == 2) {
                    numberOfStates = Integer.parseInt(s.substring(3));
                    a++;
                } else if (a == 3) {
                    s = scan.nextLine();
                    while (!s.matches("-+")) {
                        transitions.append(s).append("\n");
                        s = scan.nextLine();
                    }
                    a++;
                    //System.out.print(transitions);
                } else if (a == 4) {
                    charTape = s.substring(3).toCharArray();
                    a++;
                } else if (a == 5) {
                    startingPoint = Integer.parseInt(s.substring(3)) - 1;
                    a++;
                } else if (a == 6) {
                    steps = Integer.parseInt(s.substring(3));
                }
            }
            s = scan.nextLine();
        }
        file.close();
        trans = transitions.toString().split("\n");
        //System.out.println(Arrays.toString(trans));
        //System.out.println(values);
        String subStr;
        values[values.length - 1] = ' ';
        ArrayList<Character> valuess = new ArrayList<>();
        for (int p = 0; p < tape.length; p++) {
            if (p >= 100 && p <= charTape.length + 99) tape[p] = charTape[p - 100];
            else tape[p] = ' ';
        }
        for (char value : values) valuess.add(value);
        matrix = new String[values.length][numberOfStates];
        for (String tran : trans) {
            subStr = tran.substring(4);
            int i = Character.getNumericValue(tran.charAt(0)) - 1;
            char j = tran.charAt(2);
            matrix[valuess.indexOf(j)][i] = subStr;
        }

        int valueNumb;
        int currentPosition = startingPoint + 100;
        char[] command;
        int stepsCounter = 1;
        if (steps == -1) {
            while (currentState != -1) {
                currentValue = tape[currentPosition];
                valueNumb = valuess.indexOf(currentValue);
                command = matrix[valueNumb][currentState].toCharArray();
                tape[currentPosition] = command[0];
                if (command[2] == '>') currentPosition++;
                else if (command[2] == '<') currentPosition--;
                currentState = Character.getNumericValue(command[4]) - 1;
            }
        } else while (currentState != -1 || stepsCounter != steps) {
            currentValue = tape[currentPosition];
            valueNumb = valuess.indexOf(currentValue);
            command = matrix[valueNumb][currentState].toCharArray();
            tape[currentPosition] = command[0];
            if (command[2] == '>') currentPosition++;
            else if (command[2] == '<') currentPosition--;
            currentState = Character.getNumericValue(command[4]) - 1;
            stepsCounter++;
        }
        //System.out.println(tape);
        File result = new File("src/result.txt");
        result.createNewFile();
        FileWriter writer = new FileWriter(result);
        writer.write(tape);
        writer.flush();
        writer.close();
    }
}