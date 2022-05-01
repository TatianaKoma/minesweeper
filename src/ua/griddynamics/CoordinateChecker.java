package ua.griddynamics;

import java.util.Scanner;

public class CoordinateChecker {
    private final Scanner scanner = new Scanner(System.in);
    private final String USER_FREE = "free";
    private final String USER_MINE = "mine";

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
        return mark.equals(USER_FREE) || mark.equals(USER_MINE);
    }

    private boolean isCoordinateValid(String[] userInput) {
        int x = Integer.parseInt(userInput[1]) - 1;
        int y = Integer.parseInt(userInput[0]) - 1;
        return x >= 0 && x < 9 && y >= 0 && y < 9;
    }

    private static boolean isCoordinateDigit(String value) {
        return value.matches("\\d+");
    }

    public Coordinate getCoordinate(String[] userInput, Game game) {
        int x = Integer.parseInt(userInput[1]) - 1;
        int y = Integer.parseInt(userInput[0]) - 1;
        String mark = userInput[2];
        char markCoordinate = 0;
        if (mark.equals("free")) {
            markCoordinate = game.FREE;
        } else if (mark.equals("mine")) {
            markCoordinate = game.MINE;
        }
        return new Coordinate(x, y, markCoordinate);
    }
}
