package ua.griddynamics;

public class Cell {
    private boolean mine;
    private boolean checked;
    private boolean markedAsMine;
    private int neighborMines;

    public Cell() {
        this.mine = false;
        this.checked = false;
        this.markedAsMine = false;
        this.neighborMines = 0;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isMarkedAsMine() {
        return markedAsMine;
    }

    public void setMarkedAsMine(boolean markedAsMine) {
        this.markedAsMine = markedAsMine;
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
