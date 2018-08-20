package ru.nikson69.game;

import java.util.ArrayList;

public class Game {

    private final Bomb bomb;
    private final Flag flag;
    private int bombCounter;
    private GameState state;
    private boolean statusOpening;
    private final ArrayList<Cord> cords = new ArrayList<>();

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Cord(cols, rows));
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

    public ImagesBox getImageBox(Cord cord) {
        if (flag.get(cord) == ImagesBox.OPENED)
            return bomb.get(cord);
        else
            return flag.get(cord);
    }

    /**
     *   Action
     *   opens the cell
     */
    public void openToBox(Cord cord) {
        if (state == GameState.NEWGAME)
            state = GameState.PLAYED;
        if (gameOver()) return;
        openBox(cord);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxed() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox(Cord cord) {
        switch (flag.get(cord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(cord);
                return;
            case FLAGED:
                if (statusOpening) {
                    statusOpening = false;
                    setOpenedToClosedBoxesAroundNumber(cord);
                } else {
                    return;
                }
            case INFORM:
                return;
            case CLOSED:
                switch (bomb.get(cord)) {
                    case ZERO:
                        statusOpening = true;
                        openBoxesAround(cord);
                        return;
                    case BOMB:
                        openBomb(cord);
                        return;
                    default:
                        flag.setOpenedToBox(cord);
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Cord cord) {
        if (bomb.get(cord) != ImagesBox.BOMB)
            if (flag.getCountOfFlagBoxesAround(cord) == bomb.get(cord).getNumber())
                for (Cord around : Ranges.getCordsAround(cord))
                    if (flag.get(around) == ImagesBox.CLOSED)
                        openBox(around);
    }

    private void openBomb(Cord bombed) {
        state = GameState.BOMBER;
        flag.setBombToBox(bombed);
        for (Cord cord : Ranges.getAllCords())
            if (bomb.get(cord) == ImagesBox.BOMB)
                flag.setOpenedToBombBox(cord);
            else
                flag.setNoBombToFlagToBox(cord);
    }


    private void openBoxesAround(Cord cord) {
        flag.setOpenedToBox(cord);
        for (Cord around : Ranges.getCordsAround(cord)) {
            openBox(around);
        }
    }

    /**
     *   Action
     *   Sets the flag or closes the cell
     */
    public void flagAndCloseToBox(Cord cord) {
        if (gameOver()) return;
        flag.toggleFlagToBox(cord);
        if (flag.get(cord) == ImagesBox.FLAGED)
            bombCounter--;
        else if (flag.get(cord) == ImagesBox.CLOSED)
            bombCounter++;
    }

    private boolean gameOver() {
        return !(state == GameState.PLAYED | state == GameState.NEWGAME);
    }

    /**
     *   Action
     *   Show range of coordinates
     */
    public void openBoxAround(Cord cord) {
        setOpenedToAroundNumber(cord);
    }

    private void setOpenedToAroundNumber(Cord cord) {
        if (flag.get(cord) == ImagesBox.CLOSED) {
            cords.add(cord);
            flag.setZeroToBox(cord);
        }
        for (Cord around : Ranges.getCordsAround(cord)) {
            if (flag.get(around) == ImagesBox.CLOSED) {
                cords.add(around);
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
        for (Cord cord : cords)
            flag.setClosedToBox(cord);
        cords.clear();
    }
}
