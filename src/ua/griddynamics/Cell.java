package ua.griddynamics;

public class Cell {
    boolean mine;
    boolean checked;
    boolean markedAsMine;
    boolean markAsFree;
    int neighborMines;

    public Cell() {
        this.mine = false;
        this.checked = false;
        this.markedAsMine = false;
        this.markAsFree = false;
        this.neighborMines = 0;
    }

    public boolean isMine() {
        return mine;
    }

    public boolean isChecked() {
        return checked;
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }

    public boolean isEmpty() {
        return !isMine() && neighborMines == 0;
    }
}
