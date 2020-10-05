package koknov.nikolay.minesweeper;

public enum Cell {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    CELL,
    CLOSEDCELL,
    FLAG,
    EXPLODEDBOMB,
    NOBOMB;

    public Object image;

    Cell getNextNumberCell(){
        return Cell.values()[this.ordinal() + 1];
    }

    public int getNumber() {
        return this.ordinal();
    }
}
