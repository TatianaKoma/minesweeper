package ua.griddynamics;

import java.util.Random;

public class Game {
    static final int WIDTH = 9;
    static final int HEIGHT = 9;

    public final char X = 'X';
    public final char FREE = '/';
    public final char MINE = '*';
    public final char POINT = '.';
    public final char ZERO = '0';

    private final int numberOfMines;
    private final Cell[][] fields;

    public Game(int numberOfMines) {
        this.fields = new Cell[WIDTH][HEIGHT];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j] = new Cell();
            }
        }
        this.numberOfMines = numberOfMines;
    }

    public void placeMines(Coordinate coordinate) {
        Random random = new Random();
        int minesPlaced = 0;
        int userX = coordinate.getX();
        int userY = coordinate.getY();
        while (minesPlaced < numberOfMines) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            if (!fields[y][x].isMine() && (x != userX || y != userY)) {
                fields[y][x].setMine(true);
                minesPlaced++;
            }
        }
        numberGenerator();
    }

    public String getField() {
        StringBuilder sb = new StringBuilder();
        sb.append(" |123456789| ");
        sb.append(System.lineSeparator());
        sb.append("-|---------|");
        sb.append(System.lineSeparator());
        int rowCounter = 1;
        for (Cell[] field : fields) {
            sb.append(rowCounter).append("|");

            for (int j = 0; j < fields.length; j++) {
                sb.append(getSymbol(field[j]));
            }
            sb.append("|");
            sb.append(System.lineSeparator());
            rowCounter++;
        }
        sb.append("-|---------|");
        return sb.toString();
    }

    private char getSymbol(Cell cell) {
        if (cell.isChecked()) {
            if (cell.isMarkedAsMine()) {
                return MINE;
            }
            if (cell.isEmpty()) {
                return FREE;
            }
            if (cell.isMine()) {
                return X;
            }
            if (!cell.isEmpty() && !cell.isMine()) {
                return (char) (cell.getNeighborMines() + ZERO);
            }
        }
        return POINT;
    }

    private int countSurround(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < WIDTH && j >= 0 && j < HEIGHT) {
                    if (fields[i][j].isMine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void numberGenerator() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (!fields[i][j].isMine()) {
                    fields[i][j].setNeighborMines(countSurround(i, j));
                }
            }
        }
    }

    public void floodFill(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return;
        }
        if (visited[y][x]) {
            return;
        }

        visited[y][x] = true;

        if (fields[y][x].isMine()) {
            fields[x][y].setChecked(false);
            return;
        }
        if (fields[y][x].isEmpty()) {
            fields[y][x].isEmpty();
            fields[y][x].setChecked(true);

        } else {
            fields[y][x].setChecked(true);
            return;
        }

        if (!fields[y][x].isEmpty()) {
            fields[y][x].setChecked(false);
            return;
        }

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                floodFill(i, j, visited);
            }
        }
    }

    public boolean putAnswer(Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        char mark = coordinate.getMark();
        if (mark == FREE && fields[y][x].isMine()) {
            return false;
        }

        if (mark == FREE) {
            if (fields[y][x].isEmpty()) {
                fields[y][x].setChecked(true);
                fields[y][x].isEmpty();
            }
            if (fields[y][x].isMarkedAsMine()) {
                fields[y][x].setMarkedAsMine(false);
                fields[y][x].setChecked(true);
            }
            floodFill(x, y, new boolean[HEIGHT][WIDTH]);
        }
        if (mark == MINE) {
            if (fields[y][x].isMarkedAsMine()) {
                fields[y][x].setMarkedAsMine(false);
            } else {
                fields[y][x].setMarkedAsMine(true);
                fields[y][x].setChecked(true);
            }
        }
        return true;
    }

    public void showAllMines() {
        for (Cell[] field : fields) {
            for (Cell cell : field) {
                if (cell.isMine()) {
                    cell.setChecked(true);
                }
                if (cell.isMarkedAsMine() && cell.isMine()) {
                    cell.setMarkedAsMine(false);
                    cell.setChecked(true);
                }
            }
        }
    }

    public boolean isWin() {
        int countMines = 0;
        int countPoints = 0;
        for (Cell[] field : fields) {
            for (Cell cell : field) {
                if (cell.isMine() && cell.isMarkedAsMine()) {
                    countMines++;
                } else if (!cell.isMine() && cell.isMarkedAsMine()) {
                    countMines--;
                }
                if (cell.isMine() && !cell.isChecked()) {
                    countPoints++;
                } else if (!cell.isMine() && !cell.isChecked()) {
                    countPoints--;
                } else if (!cell.isMine() && cell.isMarkedAsMine()) {
                    countPoints--;
                }
            }
        }
        return countMines == numberOfMines || countPoints == numberOfMines;
    }
}
