package ua.griddynamics;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? >");
        int numberOfMines = scanner.nextInt();
        Field field = new Field();

        char[][] visibleField = new char[field.HEIGHT][field.WIDTH];
        field.buildEmptyField(visibleField);
        System.out.println(field.getField(visibleField));

        char[][] hiddenField = new char[field.HEIGHT][field.WIDTH];
        CoordinateChecker coordinateChecker = new CoordinateChecker();
        String[] userAnswer = coordinateChecker.scanAndCheckUserInput();

        Coordinate firstCoordinate = coordinateChecker.getCoordinate(userAnswer);
        field.placeMines(hiddenField, numberOfMines, firstCoordinate);
        field.numberGenerator(hiddenField);
//        System.out.println(field.getField(hiddenField));
//        System.out.println("------");

        field.floodFill(firstCoordinate.getX(), firstCoordinate.getY(), hiddenField, visibleField,
                new boolean[field.HEIGHT][field.WIDTH]);
        System.out.println(field.getField(visibleField));

        do {
            userAnswer = coordinateChecker.scanAndCheckUserInput();
            Coordinate coordinate = coordinateChecker.getCoordinate(userAnswer);
            int x = coordinate.getX();
            int y = coordinate.getY();
            char mark = coordinateChecker.getMark(userAnswer);
            if (field.isFail(x, y, hiddenField, mark)) {
                System.out.println(field.getField(hiddenField));
                System.out.println("You stepped on a mine and failed!");
                break;
            }
            if (mark == field.FREE) {
                field.putFree(x, y, hiddenField, visibleField);
            }
            if (mark == field.MINE) {
                field.putMine(x, y, visibleField);
            }
            if (field.isAllMinesFound(hiddenField, visibleField, numberOfMines) ||
                    field.isAllSafeCellsFound(hiddenField, visibleField, numberOfMines)) {
                System.out.println("Congratulations! You found all mines!");
                break;
            }
            System.out.println(field.getField(visibleField));

        } while (!field.isAllMinesFound(hiddenField, visibleField, numberOfMines) ||
                !field.isAllSafeCellsFound(hiddenField, visibleField, numberOfMines));
    }
}
