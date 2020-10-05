package koknov.nikolay.minesweeper;

class Matrix {

    private Cell[][] matrix;

    Matrix(Cell cell){
        matrix = new Cell[Field.getSize().x][Field.getSize().y];
        for (Coord coord : Field.getAllCoords())
            matrix[coord.x] [coord.y] = cell;
    }

    Cell get(Coord coord){
        if (Field.inRange(coord))
            return matrix[coord.x] [coord.y];
        return null;
    }

    void set(Coord coord, Cell cell){
        if (Field.inRange(coord))
            matrix[coord.x] [coord.y] = cell;
    }

}
