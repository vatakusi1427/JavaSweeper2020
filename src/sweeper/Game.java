package sweeper;

import java.awt.*;

public class Game
{
    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }



    public Game (int cols, int rows, int bombs){

        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb (bombs);
        flag = new Flag();
    }

    public void start (){

        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Box getBox (Coord coord){
if (flag.get(coord) == Box.OPENED)
    return bomb.get(coord);
else
        return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox (coord);
        checkWinner(); // после каждого нажатия левой кнопки идет проверка а не победили ли мы
    }
    // проверка на победу
    private void checkWinner (){
        if (state == GameState.PLAYED) //проверяем, что мы еще играем
            if (flag.getCountOfClosedBoxes () == bomb.getTotalBombs ()) // количество закрытых клеток равно бомбам
                state = GameState.WINNER;
    }

    private void openBox (Coord coord){
        switch (flag.get(coord)){
            case OPENED: setOpenedToClosedBoxesAroundNumber (coord); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord)){
                    case ZERO: openBoxesAround (coord); return;
                    case BOMB: openBombs(coord); return;
                    default: flag.setOpenedToBox(coord); return;
                }
        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord)
    {
        if (bomb.get(coord) != Box.BOMB)
         if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
             for (Coord around : Ranges.getCoordAround(coord))
                 if (flag.get(around) == Box.CLOSED)
                     openBox(around);

        }


    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox (bombed);
        // нарисовать все остальные бомбы, перебираем все остальные клетки
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.get(coord) == Box.BOMB)
                flag.setOpenedToCloseBombBox (coord);// установка открытой клетки на закрытую бомбу
        else
                flag.setNoBombToFlagedSafeBox (coord); // если стоит флажок, а бомбы нет
    }

    private void openBoxesAround(Coord coord) { // открывает все пустые клетки
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
