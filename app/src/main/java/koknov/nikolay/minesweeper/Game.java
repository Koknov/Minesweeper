package koknov.nikolay.minesweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState gameState;
    private boolean firstStep;
    private int col;
    private int row;
    int bombNum;

    public Game(int cols, int rows, int bombs){
        Field.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
        col = cols;
        row = rows;
        bombNum = bombs;
    }

    public Game(Bomb bombs) {
        bomb = bombs;
        flag = new Flag();
    }

    public Game() {
        bomb = new Bomb(Field.bombsCount);
        flag = new Flag();
    }

    public int getCols() { return col;}

    public int getRows() { return row;}

    public void startForTest() {
        flag.start();
        gameState = GameState.PLAYED;
    }

    Bomb getBomb() {
        return bomb;
    }

    Flag getFlag() {
        return flag;
    }

    GameState getGameState() {
        return gameState;
    }

    public void start(){
        bomb.start();
        flag.start();
        firstStep = true;
        gameState = GameState.PLAYED;
    }

    Cell getCell(Coord coord){
        if (flag.get(coord) == Cell.CELL)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    boolean open(Coord coord) {
        if(gameOver()) return false;
        if (isFirstStep()) {
            while (bomb.get(coord) == Cell.BOMB)
                bomb.start();
            openCell(coord);
            firstStep = false;
        } else
            openCell(coord);
        checkWin();
        return true;
    }

    boolean flagged(Coord coord) {
        if(gameOver()) return false;
        flag.toggleFlag(coord);
        return true;
    }


    public boolean move(Coord coord) {
        openCell(coord);
        checkWin();
        return gameOver();
    }

    private boolean isFirstStep() {
        return firstStep;
    }

    private void checkWin(){
        if (gameState == GameState.PLAYED)
            if (flag.getNumberOfClosedCell() == bomb.getTotalBombs())
                gameState = GameState.WINNER;
    }

    private void openCell(Coord coord){
        if(flag.get(coord) == null) return;
        switch (flag.get(coord)){
            case CELL : setOpenedToClosedCellsAroundNumber(coord); break;
            case FLAG : break;
            case CLOSEDCELL : switch (bomb.get(coord)){
                case ZERO : openCellsAround(coord); break;
                case BOMB : openBomb(coord); break;
                default : flag.setOpenedToCell(coord); break;
            }
        }
    }

    private void setOpenedToClosedCellsAroundNumber(Coord coord){
        if (bomb.get(coord) != Cell.BOMB)
            if (flag.getNumberOfFlaggedCellsAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Field.getCoordAround(coord))
                    if (flag.get(around) == Cell.CLOSEDCELL)
                        openCell(around);
    }

    private void openBomb(Coord exploded) {
        gameState = GameState.BOMBED;
        flag.setBombedToCell(exploded);
        for (Coord coord : Field.getAllCoords())
            if (bomb.get(coord) == Cell.BOMB)
                flag.setOpenedToClosedBombCell(coord);
            else
                flag.setNoBombToFlaggedCell(coord);
    }

    private void openCellsAround(Coord coord) {
        flag.setOpenedToCell(coord);
        for(Coord around : Field.getCoordAround(coord))
            if(flag.get(around) != Cell.CELL)
                openCell(around);
    }

    private boolean gameOver() {
        return gameState != GameState.PLAYED;
    }

}
