package ru.nikson69.game;

import ru.nikson69.game.ImagesBox;

class Matrix {
    private final ImagesBox[][] matrix;

    Matrix(ImagesBox defaultBox) {
        matrix = new ImagesBox[Ranges.getSize().x][Ranges.getSize().y];
        for (Cord cord : Ranges.getAllCords()) {
            matrix[cord.x][cord.y] = defaultBox;
        }
    }

    ImagesBox get(Cord cord) {
        if (Ranges.inRange(cord))
            return matrix[cord.x][cord.y];
        return null;
    }

    void set(Cord cord, ImagesBox imagesBox) {
        if (Ranges.inRange(cord))
            matrix[cord.x][cord.y] = imagesBox;
    }
}
