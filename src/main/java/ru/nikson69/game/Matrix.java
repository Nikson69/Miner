package ru.nikson69.game;

class Matrix {
    private ImagesBox[][] matrix;

    Matrix(ImagesBox defaultBox) {
        matrix = new ImagesBox[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoords()) {
            matrix[coord.x][coord.y] = defaultBox;
        }
    }

    ImagesBox get(Coord coord) {
        if (Ranges.inRange(coord))
            return matrix[coord.x][coord.y];
        return null;
    }

    void set(Coord coord, ImagesBox imagesBox) {
        if (Ranges.inRange(coord))
            matrix[coord.x][coord.y] = imagesBox;
    }
}
