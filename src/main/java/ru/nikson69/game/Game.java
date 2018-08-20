package ru.nikson69.game;

import java.util.ArrayList;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;
    private boolean statusOpening;
    private ArrayList <Coord> coords = new ArrayList<Coord>();

    public int getBombCounter() {
        return bombCounter;
    }

    private int bombCounter;
    private boolean statusOpeningInform;


    public GameState getState() {
        return state;
    }

    public Game (int cols, int rows, int bombs){
        Ranges.setSize(new Coord(cols,rows));

        bomb = new Bomb(bombs);
        flag = new Flag();

    }

    public void setState(GameState state) {
        this.state = state;
    }


    public  void start(){
        bombCounter = bomb.getTotalBombs();
        bomb.start();
        flag.start();
        state = GameState.NEWGAME;
    }

    public MinerImages getMinerImages (Coord coord){
        if (flag.get(coord)== MinerImages.OPENED)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (state == GameState.NEWGAME)
            state = GameState.PLAYED;
        if (gameOver()) return;
        openBox (coord);
        checkWinner();
    }

    private void checkWinner (){
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxed() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox(Coord coord){
        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord);return;
            case FLAGED: if (statusOpening) {
                statusOpening = false;
                setOpenedToClosedBoxesAroundNumber(coord);
            } else {
                return;
            }
            //case INFORM:
            case CLOSED:
                //openBoxClosedAndInform(coord);
                switch (bomb.get(coord)){
                    case ZERO:
                        statusOpening = true;
                        openBoxesAround (coord);
                        return;
                    case BOMB:
                        openBomb(coord);return;
                    default:
                        flag.setOpenedToBox(coord);return;
                }


        }
    }

//    private void openBoxClosedAndInform(Coord coord) {
//        switch (bomb.get(coord)){
//            case ZERO: statusOpening = true;
//                openBoxesAround (coord);
//                return;
//            case BOMB: openBomb(coord);return;
//            default: flag.setOpenedToBox(coord);return;
//        }

 //   }

    void setOpenedToClosedBoxesAroundNumber (Coord coord){
        if(bomb.get(coord) != MinerImages.BOMB)
            if(flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around: Ranges.getCoordsAround(coord))
                    if (flag.get(around) == MinerImages.CLOSED)
                        openBox(around);
    }

    private void openBomb(Coord bombed) {
        state = GameState.BOMBER;
        flag.setBobmedToBox (bombed);
        for (Coord coord: Ranges.getAllCoords())
            if (bomb.get(coord) == MinerImages.BOMB)
                flag.setOpenedToBombBox(coord);
            else
                flag.setNoBobmToFlagetToBox(coord);
    }


    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around: Ranges.getCoordsAround(coord)){
            openBox(around);
        }
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlagedToBox(coord);
        if (flag.get(coord) == MinerImages.FLAGED)
        bombCounter--;
        else if (flag.get(coord) == MinerImages.CLOSED)
            bombCounter++;
        else return;
    }
    public void pressTwoButton(Coord coord) {
        setOpenedToAroundNumber(coord);
    }

    private void setOpenedToAroundNumber(Coord coord) {

                if (flag.get(coord) == MinerImages.CLOSED){
                    coords.add(coord);
                    flag.setZeroToBox(coord);
                }
                for (Coord around: Ranges.getCoordsAround(coord)) {
                    if (flag.get(around) == MinerImages.CLOSED){
                        coords.add(around);
                        flag.setZeroToBox(around);
                    }
                }
    }




    private boolean gameOver() {
        if (state == GameState.PLAYED | state == GameState.NEWGAME)
            return false;
        else {
            //start();
            return true;
        }
    }

    public void pressTwoButton2() {
        if (coords != null)
        for (Coord coord: coords)
            flag.setClosedToBox(coord);
        coords.clear();
    }
}
