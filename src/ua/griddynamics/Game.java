package ua.griddynamics;

import java.util.Arrays;
import java.util.Random;

public class Game {
    static final int WIDTH = 9;
    static final int HEIGHT = 9;

    public final char X = 'X';
    public final char FREE = '/';
    public final char MINE = '*';
    public final char POINT = '.';

    private final char[][] hiddenField;
    private final char[][] visibleField;

    public Game(char[][] hiddenField, char[][] visibleField) {
        this.hiddenField = hiddenField;
        this.visibleField = visibleField;
    }

    public void placeMines(Coordinate coordinate, int numberOfMines) {
        Random random = new Random();
        int minesPlaced = 0;
        int userX = coordinate.getX();
        int userY = coordinate.getY();
        do {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            if (hiddenField[y][x] != X && x != userX && y != userY) {
                hiddenField[y][x] = X;
                minesPlaced++;
            }
        } while (minesPlaced < numberOfMines);
    }

    public void buildEmptyField() {
        for (int i = 0; i < visibleField.length; i++) {
            Arrays.fill(visibleField[i], POINT);
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

    private int countSurround(int i, int j) {
        int count = 0;
        if (i + 1 != HEIGHT && hiddenField[i + 1][j] == X)
            count++;
        if (i + 1 != HEIGHT && j + 1 != WIDTH && hiddenField[i + 1][j + 1] == X)
            count++;
        if (i - 1 != -1 && j + 1 != WIDTH && hiddenField[i - 1][j + 1] == X)
            count++;
        if (i + 1 != HEIGHT && j - 1 != -1 && hiddenField[i + 1][j - 1] == X)
            count++;
        if (i - 1 != -1 && j - 1 != -1 && hiddenField[i - 1][j - 1] == X)
            count++;
        if (i - 1 != -1 && hiddenField[i - 1][j] == X)
            count++;
        if (j + 1 != WIDTH && hiddenField[i][j + 1] == X)
            count++;
        if (j - 1 != -1 && hiddenField[i][j - 1] == X)
            count++;
        return count;
    }

    public void numberGenerator() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (hiddenField[i][j] != X) {
                    hiddenField[i][j] = (char) (countSurround(i, j) + 48);
                }
                if (hiddenField[i][j] == '0') {
                    hiddenField[i][j] = POINT;
                }

            }
        }
    }

    public void floodFill(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= 9 || y < 0 || y >= 9) return;

        if (visited[y][x]) return;
        visited[y][x] = true;

        if (hiddenField[y][x] == X && hiddenField[x][y] != POINT)
            return;

//        if (visibleField[y][x] == MINE)
//            return;

//        if (hiddenField[y][x] != POINT)
//            return;

        if (hiddenField[y][x] == POINT) {
            visibleField[y][x] = FREE;
        } else {
            visibleField[y][x] = hiddenField[y][x];
            return;
        }
        floodFill(x + 1, y, visited);
        floodFill(x - 1, y, visited);
        floodFill(x, y - 1, visited);
        floodFill(x, y + 1, visited);
    }

    public void putAnswer(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        char mark = coordinate.getMark();

        if (mark == FREE) {
            if (hiddenField[y][x] == POINT) {
                visibleField[y][x] = FREE;
            } else {
                visibleField[y][x] = hiddenField[y][x];
            }
            floodFill(x, y, new boolean[HEIGHT][WIDTH]);
        }
        if (mark == MINE) {
            if (visibleField[y][x] == MINE) {
                visibleField[y][x] = POINT;
            } else {
                visibleField[y][x] = MINE;
            }
        }
    }

    public boolean isFail(Coordinate coordinate) {
        return coordinate.getMark() == FREE && hiddenField[coordinate.getY()][coordinate.getX()] == X;
    }

    public void showAllMines() {
        for (int i = 0; i < hiddenField.length; i++) {
            for (int j = 0; j < hiddenField[i].length; j++) {
                if (hiddenField[i][j] == X) {
                    visibleField[i][j] = hiddenField[i][j];
                }
            }
        }
    }

    public boolean isWin(int numberOfMines) {
        int countMines = 0;
        int countPoints = 0;
        for (int i = 0; i < visibleField.length; i++) {
            for (int j = 0; j < visibleField[i].length; j++) {
                if (visibleField[i][j] == MINE && hiddenField[i][j] == X) {
                    countMines++;
                } else if (visibleField[i][j] == MINE && hiddenField[i][j] != X) {
                    countMines--;
                }
                if (visibleField[i][j] == POINT && hiddenField[i][j] == X) {
                    countPoints++;
                } else if (visibleField[i][j] == POINT && hiddenField[i][j] != X) {
                    countPoints--;
                }
            }
        }
        return countMines == numberOfMines || countPoints == numberOfMines;
    }
}
