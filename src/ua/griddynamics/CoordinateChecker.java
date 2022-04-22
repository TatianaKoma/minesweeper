package ua.griddynamics;

import java.util.Scanner;

public class CoordinateChecker {
    private final Scanner scanner = new Scanner(System.in);
    Field field = new Field();

    public String[] scanAndCheckUserInput() {
        String[] userInput;
        do {
            System.out.print("Set/delete mines marks (x and y coordinates): > ");
            userInput = scanner.nextLine().split(" ");
        } while (userInput.length != 3
                || !isCoordinateDigit(userInput[0]) && !isCoordinateDigit(userInput[1])
                || !isCoordinateValid(userInput)
                || !isMarkValid(userInput));
        return userInput;
    }

    private boolean isMarkValid(String[] userInput) {
        String mark = userInput[2];
        return mark.equals("free") || mark.equals("mine");
    }

    private boolean isCoordinateValid(String[] userInput) {
        int x = Integer.parseInt(userInput[1]) - 1;
        int y = Integer.parseInt(userInput[0]) - 1;
        return x >= 0 && x < 9 && y >= 0 && y < 9;
    }

    private static boolean isCoordinateDigit(String value) {
        return value.matches("\\d+");
    }

    public Coordinate getCoordinate(String[] userInput) {
        int x = Integer.parseInt(userInput[1]) - 1;
        int y = Integer.parseInt(userInput[0]) - 1;
        return new Coordinate(x, y);
    }

    public char getMark(String[] userInput) {

        String mark = userInput[2];
        if (mark.equals("free")) {
            return field.FREE;
        } else {
            return field.MINE;
        }
    }
}
