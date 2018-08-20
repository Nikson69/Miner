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

    ImagesBox get(Cord cord) {
        return bombMap.get(cord);
    }

    private void placeBomb() {
        while (true) {
            Cord cord = Ranges.getRandomCord();
            if (ImagesBox.BOMB == bombMap.get(cord))
                continue;
            bombMap.set(cord, ImagesBox.BOMB);
            incNumberAroundBomb(cord);
            break;
        }
    }

    private void incNumberAroundBomb(Cord cord) {
        for (Cord around : Ranges.getCordsAround(cord))
            if (ImagesBox.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).nextNumberMinerImage());
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
