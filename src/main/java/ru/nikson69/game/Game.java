package ru.nikson69.game;

import java.util.ArrayList;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private int bombCounter;
    private GameState state;
    private boolean statusOpening;
    private ArrayList<Coord> coords = new ArrayList<Coord>();

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bombCounter = bomb.getTotalBombs();
        bomb.start();
        flag.start();
        state = GameState.NEWGAME;
    }

    public int getBombCounter() {
        return bombCounter;
    }

    public GameState getState() {
        return state;
    }

    public ImagesBox getImageBox(Coord coord) {
        if (flag.get(coord) == ImagesBox.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    /**
     *   Action
     *   opens the cell
     */
    public void openToBox(Coord coord) {
        if (state == GameState.NEWGAME)
            state = GameState.PLAYED;
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxed() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
                return;
            case FLAGED:
                if (statusOpening) {
                    statusOpening = false;
                    setOpenedToClosedBoxesAroundNumber(coord);
                } else {
                    return;
                }
            case INFORM:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        statusOpening = true;
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBomb(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                        return;
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (bomb.get(coord) != ImagesBox.BOMB)
            if (flag.getCountOfFlagBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == ImagesBox.CLOSED)
                        openBox(around);
    }

    private void openBomb(Coord bombed) {
        state = GameState.BOMBER;
        flag.setBombToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == ImagesBox.BOMB)
                flag.setOpenedToBombBox(coord);
            else
                flag.setNoBombToFlagToBox(coord);
    }


    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    /**
     *   Action
     *   Sets the flag or closes the cell
     */
    public void flagAndCloseToBox(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlagToBox(coord);
        if (flag.get(coord) == ImagesBox.FLAGED)
            bombCounter--;
        else if (flag.get(coord) == ImagesBox.CLOSED)
            bombCounter++;
        else return;
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED | state == GameState.NEWGAME)
            return false;
        else {
            return true;
        }
    }

    /**
     *   Action
     *   Show range of coordinates
     */
    public void openBoxAround(Coord coord) {
        setOpenedToAroundNumber(coord);
    }

    private void setOpenedToAroundNumber(Coord coord) {
        if (flag.get(coord) == ImagesBox.CLOSED) {
            coords.add(coord);
            flag.setZeroToBox(coord);
        }
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (flag.get(around) == ImagesBox.CLOSED) {
                coords.add(around);
                flag.setZeroToBox(around);
            }
        }
    }

    /**
     * Action
     * Used to complete the openBoxAround method
     * Close the open cells
     */
    public void closedOpenToBox() {
        if (coords != null)
            for (Coord coord : coords)
                flag.setClosedToBox(coord);
        coords.clear();
    }
}
