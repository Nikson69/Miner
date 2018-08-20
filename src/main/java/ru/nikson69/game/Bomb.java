package ru.nikson69.game;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombCount();
    }

    void start() {
        bombMap = new Matrix(ImagesBox.ZERO);
        for (int i = 0; i < totalBombs; i++)
            placeBomb();
    }

    private void fixBombCount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    ImagesBox get(Coord coord) {
        return bombMap.get(coord);
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (ImagesBox.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, ImagesBox.BOMB);
            incNumberAroundBomb(coord);
            break;
        }
    }

    private void incNumberAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord))
            if (ImagesBox.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).nextNumberMinerImage());
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
