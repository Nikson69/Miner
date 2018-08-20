package ru.nikson69.game;

public class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs){
        this.totalBombs = totalBombs;
        fixBombCount();
    }

    void start(){
        bombMap = new Matrix(MinerImages.ZERO);
        for (int i = 0; i < totalBombs; i++)
        placeBomb();
    }

    MinerImages get (Coord coord){
        return bombMap.get(coord);
    }

    private void fixBombCount(){
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y/2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
}

    private void placeBomb(){
        while (true){
            Coord coord = Ranges.getRandomCoord();
            if (MinerImages.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord,MinerImages.BOMB);
            incNumberAroundBomb(coord);
            break;
        }
    }

    private void incNumberAroundBomb (Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord))
            if (MinerImages.BOMB != bombMap.get(around))
                bombMap.set(around,bombMap.get(around).nextNumberMinerImage());
    }

     int getTotalBombs() {
        return totalBombs;
    }
}
