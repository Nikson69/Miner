package ru.nikson69.game;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;



    int flagedCounter = 0;

    void start (){
        flagMap = new Matrix(MinerImages.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    MinerImages get (Coord coord){
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, MinerImages.OPENED);
        countOfClosedBoxes--;

    }

    void setFlagetToBox(Coord coord) {
        flagMap.set(coord,MinerImages.FLAGED);
    }

    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
//            case FLAGED: setInformToBox (coord); break;
//            case INFORM: setClosedToBox (coord); break;
//            case CLOSED: setFlagetToBox(coord); break;
            case FLAGED:
                flagedCounter--;
                setClosedToBox (coord);
                break;
            case CLOSED:
                flagedCounter++;
                setFlagetToBox(coord);
                break;
        }
    }

    public int getFlagedCounter() {
        return flagedCounter;
    }

    private void setInformToBox(Coord coord) {
        flagMap.set(coord,MinerImages.INFORM);
    }

    public void setClosedToBox(Coord coord) {
        flagMap.set(coord,MinerImages.CLOSED);
    }

    int getCountOfClosedBoxed() {
        return countOfClosedBoxes;
    }

    void setBobmedToBox(Coord coord) {
        flagMap.set(coord, MinerImages.BOMB);
    }

    void setOpenedToBombBox(Coord coord) {
        if (flagMap.get(coord) == MinerImages.CLOSED);
            flagMap.set(coord, MinerImages.OPENED);
    }

    void setNoBobmToFlagetToBox(Coord coord) {
        if (flagMap.get(coord) == MinerImages.FLAGED)
            flagMap.set(coord,MinerImages.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == MinerImages.FLAGED)
                count++;
        return count;
    }

    public void setZeroToBox(Coord coord) {
        flagMap.set(coord, MinerImages.ZERO);
    }
}
