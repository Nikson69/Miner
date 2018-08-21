package ru.nikson69.game;

class Flag {

    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(ImagesBox.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    ImagesBox get(Cord cord) {
        return flagMap.get(cord);
    }

    void setOpenedToBox(Cord cord) {
        flagMap.set(cord, ImagesBox.OPENED);
        countOfClosedBoxes--;
    }

    void setFlagToBox(Cord cord) {
        flagMap.set(cord, ImagesBox.FLAGED);
    }

    void toggleFlagToBox(Cord cord) {
        switch (flagMap.get(cord)) {
            case FLAGED:
                setInformToBox(cord);
                break;
            case INFORM:
                setClosedToBox(cord);
                break;
            case CLOSED:
                setFlagToBox(cord);
                break;
        }
    }

    private void setInformToBox(Cord cord) {
        flagMap.set(cord, ImagesBox.INFORM);
    }

    void setClosedToBox(Cord cord) {
        flagMap.set(cord, ImagesBox.CLOSED);
    }

    int getCountOfClosedBoxed() {
        return countOfClosedBoxes;
    }

    void setBombToBox(Cord cord) {
        flagMap.set(cord, ImagesBox.BOMB);
    }

    void setOpenedToBombBox(Cord cord) {
        flagMap.get(cord);
        flagMap.set(cord, ImagesBox.OPENED);
    }

    void setNoBombToFlagToBox(Cord cord) {
        if (flagMap.get(cord) == ImagesBox.FLAGED)
            flagMap.set(cord, ImagesBox.NOBOMB);
    }

    int getCountOfFlagBoxesAround(Cord cord) {
        int count = 0;
        for (Cord around : Ranges.getCordsAround(cord))
            if (flagMap.get(around) == ImagesBox.FLAGED)
                count++;
        return count;
    }

    void setZeroToBox(Cord cord) {
        flagMap.set(cord, ImagesBox.ZERO);
    }
}
