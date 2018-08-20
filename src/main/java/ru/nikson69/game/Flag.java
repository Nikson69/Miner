package ru.nikson69.game;

class Flag {

    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(ImagesBox.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    ImagesBox get(Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, ImagesBox.OPENED);
        countOfClosedBoxes--;
    }

    private void setFlagToBox(Coord coord) {
        flagMap.set(coord, ImagesBox.FLAGED);
    }

    void toggleFlagToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setInformToBox(coord);
                break;
            case INFORM:
                setClosedToBox(coord);
                break;
            case CLOSED:
                setFlagToBox(coord);
                break;
        }
    }

    private void setInformToBox(Coord coord) {
        flagMap.set(coord, ImagesBox.INFORM);
    }

    void setClosedToBox(Coord coord) {
        flagMap.set(coord, ImagesBox.CLOSED);
    }

    int getCountOfClosedBoxed() {
        return countOfClosedBoxes;
    }

    void setBombToBox(Coord coord) {
        flagMap.set(coord, ImagesBox.BOMB);
    }

    void setOpenedToBombBox(Coord coord) {
        if (flagMap.get(coord) == ImagesBox.CLOSED) ;
        flagMap.set(coord, ImagesBox.OPENED);
    }

    void setNoBombToFlagToBox(Coord coord) {
        if (flagMap.get(coord) == ImagesBox.FLAGED)
            flagMap.set(coord, ImagesBox.NOBOMB);
    }

    int getCountOfFlagBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if (flagMap.get(around) == ImagesBox.FLAGED)
                count++;
        return count;
    }

    void setZeroToBox(Coord coord) {
        flagMap.set(coord, ImagesBox.ZERO);
    }
}
