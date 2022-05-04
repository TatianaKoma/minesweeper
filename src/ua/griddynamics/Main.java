package ua.griddynamics;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? >");
        int numberOfMines = scanner.nextInt();

        Game game = new Game(numberOfMines);
        System.out.println(game.getField());
        CoordinateChecker coordinateChecker = new CoordinateChecker();

        boolean isMinesPlaced = false;
        while (!game.isWin(numberOfMines)) {
            String[] userAnswer = coordinateChecker.scanAndCheckUserInput();
            Coordinate coordinate = coordinateChecker.getCoordinate(userAnswer, game);
            if (!isMinesPlaced) {
                game.placeMines(coordinate);
            }
            isMinesPlaced = true;
            game.putAnswer(coordinate);
            if (!game.putAnswer(coordinate)) {
                game.showAllMines();
                System.out.println(game.getField());
                System.out.println("You stepped on a mine and failed!");
                break;
            } else {
                game.putAnswer(coordinate);
            }
            if (game.isWin(numberOfMines)) {
                System.out.println(game.getField());
                System.out.println("Congratulations! You found all mines!");
                break;
            }
            System.out.println(game.getField());
        }
    }
}
