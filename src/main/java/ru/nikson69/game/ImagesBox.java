package ru.nikson69.game;

public enum ImagesBox {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB,
    INFORM;

    public Object image;

    ImagesBox nextNumberMinerImage() {
        return ImagesBox.values()[this.ordinal() + 1];
    }

    int getNumber() {
        return this.ordinal();
    }
}
