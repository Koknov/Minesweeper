package koknov.nikolay.minesweeper;

class Flag {

    private Matrix flagMap;
    private int numberOfClosedCells;

    void start(){
        flagMap = new Matrix(Cell.CLOSEDCELL);
        numberOfClosedCells = Field.getSize().x * Field.getSize().y;
    }

    Cell get(Coord coord){
        return flagMap.get(coord);
    }

    void setOpenedToCell(Coord coord) {
        flagMap.set(coord, Cell.CELL);
        numberOfClosedCells--;
    }

    void toggleFlag(Coord coord) {
        if (flagMap.get(coord) == null) return;
        switch (flagMap.get(coord)) {
            case FLAG : setClosedToCell(coord); break;
            case CLOSEDCELL: setFlagToCell(coord); break;
        }
    }

    void setFlagToCell(Coord coord) {
        flagMap.set(coord, Cell.FLAG);
    }

    private void setClosedToCell(Coord coord) {
        flagMap.set(coord, Cell.CLOSEDCELL);
    }

    int getNumberOfClosedCell() {
        return numberOfClosedCells;
    }

    void setBombedToCell(Coord coord) {
        flagMap.set(coord, Cell.EXPLODEDBOMB);
    }

    void setOpenedToClosedBombCell(Coord coord) {
        if (flagMap.get(coord) == Cell.CLOSEDCELL)
            flagMap.set(coord, Cell.CELL);
    }

    void setNoBombToFlaggedCell(Coord coord) {
        if (flagMap.get(coord) == Cell.FLAG)
            flagMap.set(coord, Cell.NOBOMB);
    }

    int getNumberOfFlaggedCellsAround(Coord coord) {
        int number = 0;
        for (Coord around : Field.getCoordAround(coord))
            if (flagMap.get(around) == Cell.FLAG)
                number++;
        return number;
    }

    int getNumberOfClosedOrFlaggedCellsAround(Coord coord) {
        int number = 0;
        for (Coord around : Field.getCoordAround(coord))
            if (flagMap.get(around) == Cell.CLOSEDCELL || flagMap.get(around) == Cell.FLAG)
                number++;
        return number;
    }
}
