package ru.nikson69.game;

class Matrix {
    private MinerImages [] [] matrix;

    Matrix (MinerImages defaltBox){
        matrix = new MinerImages[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoords()){
            matrix [coord.x] [coord.y] = defaltBox;
        }
    }

    MinerImages get (Coord coord){
        if (Ranges.inRange (coord))
            return matrix [coord.x] [coord.y];
        return null;
    }

    void set (Coord coord, MinerImages minerImages){
        if (Ranges.inRange (coord))
        matrix [coord.x] [coord.y] = minerImages;
    }
}
