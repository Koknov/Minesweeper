package koknov.nikolay.minesweeper;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs){
        this.totalBombs = totalBombs;
        fixBombCounts();
    }

    void start(){
        bombMap = new Matrix(Cell.ZERO);
        for (int i = 0; i < totalBombs; i++)
            placeBomb();
    }

    Cell get(Coord coord){
        return bombMap.get(coord);
    }

    private void fixBombCounts(){
        int maxBomb = Field.getSize().x * Field.getSize().y / 2;
        if (totalBombs > maxBomb)
            totalBombs = maxBomb;
    }

    private void placeBomb(){
        while (true){
            Coord coord = Field.getRandomCoord();
            if(bombMap.get(coord) == Cell.BOMB)
                continue;
            bombMap.set(coord, Cell.BOMB);
            incNumberAround(coord);
            break;
        }
    }

    private void incNumberAround(Coord coord){
        for(Coord around : Field.getCoordAround(coord))
            if(Cell.BOMB != bombMap.get(around))
                if (bombMap.get(around) != null)
                    bombMap.set(around, bombMap.get(around).getNextNumberCell());

    }

    int getTotalBombs() {
        return totalBombs;
    }
}
