package ua.griddynamics;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? >");
        int numberOfMines = scanner.nextInt();

        char[][] hiddenField = new char[Game.HEIGHT][Game.WIDTH];
        char[][] visibleField = new char[Game.HEIGHT][Game.WIDTH];
        Game game = new Game(hiddenField, visibleField);

        game.buildEmptyField();
        System.out.println(game.getField(visibleField));
        CoordinateChecker coordinateChecker = new CoordinateChecker();

        boolean isMinesPlaced = false;
        while (!game.isWin(numberOfMines)) {
            String[] userAnswer = coordinateChecker.scanAndCheckUserInput();
            Coordinate coordinate = coordinateChecker.getCoordinate(userAnswer, game);
            if (!isMinesPlaced) {
                game.placeMines(coordinate, numberOfMines);
                game.numberGenerator();
                System.out.println(game.getField(hiddenField));
                System.out.println("------");
            }
            isMinesPlaced = true;
            game.putAnswer(coordinate);

            if (game.isFail(coordinate)) {
                game.showAllMines();
                System.out.println(game.getField(visibleField));
                System.out.println("You stepped on a mine and failed!");
                break;
            }
            if (game.isWin(numberOfMines)) {
                System.out.println(game.getField(visibleField));
                System.out.println("Congratulations! You found all mines!");
                break;
            }
            System.out.println(game.getField(visibleField));
        }
    }
}
