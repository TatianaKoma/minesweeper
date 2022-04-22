package ua.griddynamics;

import java.util.Arrays;
import java.util.Random;

public class Field {
    final int WIDTH = 9;
    final int HEIGHT = 9;
    public final char X = 'X';
    public final char FREE = '/';
    public final char MINE = '*';
    public final char POINT = '.';

    public void placeMines(char[][] mineField, int numberOfMines, Coordinate firstCoordinate) {
        Random random = new Random();
        int minesPlaced = 0;
        int userX = firstCoordinate.getX();
        int userY = firstCoordinate.getY();
        do {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            if (mineField[y][x] != X && x != userX && y != userY) {
                mineField[y][x] = X;
                minesPlaced++;
            }
        } while (minesPlaced < numberOfMines);
    }

    public void buildEmptyField(char[][] mineField) {
        for (int i = 0; i < mineField.length; i++) {
            Arrays.fill(mineField[i], POINT);
        }
    }

    public String getField(char[][] mineField) {
        StringBuilder sb = new StringBuilder();
        sb.append(" |123456789| ");
        sb.append(System.lineSeparator());
        sb.append("-|---------|");
        sb.append(System.lineSeparator());
        int rowCounter = 1;
        for (int i = 0; i < mineField.length; i++) {
            sb.append(rowCounter).append("|");

            for (int j = 0; j < mineField[i].length; j++) {
                sb.append(mineField[i][j]);
            }
            sb.append("|");
            sb.append(System.lineSeparator());
            rowCounter++;
        }
        sb.append("-|---------|");
        return sb.toString();
    }

    private int countSurround(int i, int j, char[][] mineField) {
        int count = 0;
        if (i + 1 != HEIGHT && mineField[i + 1][j] == X)
            count++;
        if (i + 1 != HEIGHT && j + 1 != WIDTH && mineField[i + 1][j + 1] == X)
            count++;
        if (i - 1 != -1 && j + 1 != WIDTH && mineField[i - 1][j + 1] == X)
            count++;
        if (i + 1 != HEIGHT && j - 1 != -1 && mineField[i + 1][j - 1] == X)
            count++;
        if (i - 1 != -1 && j - 1 != -1 && mineField[i - 1][j - 1] == X)
            count++;
        if (i - 1 != -1 && mineField[i - 1][j] == X)
            count++;
        if (j + 1 != WIDTH && mineField[i][j + 1] == X)
            count++;
        if (j - 1 != -1 && mineField[i][j - 1] == X)
            count++;
        return count;
    }

    public void numberGenerator(char[][] mineField) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (mineField[i][j] != X) {
                    mineField[i][j] = (char) (countSurround(i, j, mineField) + 48);
                }
                if (mineField[i][j] == '0') {
                    mineField[i][j] = POINT;
                }

            }
        }
    }

    public void floodFill(int x, int y, char[][] hiddenField, char[][] visibleField, boolean[][] visited) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9) return;

        if (visited[y][x]) return;
        visited[y][x] = true;

        if (hiddenField[y][x] == X)
            return;

        if (hiddenField[y][x] == POINT) {
            visibleField[y][x] = FREE;
        } else {
            visibleField[y][x] = hiddenField[y][x];
        }
        floodFill(x + 1, y, hiddenField, visibleField, visited);
        floodFill(x - 1, y, hiddenField, visibleField, visited);
        floodFill(x, y - 1, hiddenField, visibleField, visited);
        floodFill(x, y + 1, hiddenField, visibleField, visited);
//        floodFill(x - 1, y - 1, hiddenField, visibleField, visited);
//        floodFill(x - 1, y + 1, hiddenField, visibleField, visited);
//        floodFill(x + 1, y - 1, hiddenField, visibleField, visited);
//        floodFill(x + 1, y + 1, hiddenField, visibleField, visited);
    }


    public void putFree(int x, int y, char[][] hiddenField, char[][] visibleField) {
        if (hiddenField[y][x] == POINT) {
            visibleField[y][x] = FREE;
        } else {
            visibleField[y][x] = hiddenField[y][x];
        }
    }

    public void putMine(int x, int y, char[][] visibleField) {
        if (visibleField[y][x] == MINE) {
            visibleField[y][x] = POINT;
        } else {
            visibleField[y][x] = MINE;
        }
    }

    public boolean isFail(int x, int y, char[][] hiddenField, char mark) {
        return mark == FREE && hiddenField[y][x] == X;
    }

    public boolean isAllMinesFound(char[][] hiddenField, char[][] visibleField, int numberOfMines) {
        int countMines = 0;
        for (int i = 0; i < visibleField.length; i++) {
            for (int j = 0; j < visibleField[i].length; j++) {
                if (visibleField[i][j] == MINE && hiddenField[i][j] == X) {
                    countMines++;
                }
            }
        }
        return countMines == numberOfMines;
    }

    public boolean isAllSafeCellsFound(char[][] hiddenField, char[][] visibleField, int numberOfMines) {
        int countPoints = 0;
        for (int i = 0; i < visibleField.length; i++) {
            for (int j = 0; j < visibleField[i].length; j++) {
                if (visibleField[i][j] == POINT && hiddenField[i][j] == POINT) {
                    countPoints++;
                }
            }
        }
        return countPoints == numberOfMines;
    }
}
