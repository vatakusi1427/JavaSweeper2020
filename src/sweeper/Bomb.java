package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb (int totalBombs){
       this.totalBombs = totalBombs;
       fixBombCout();
    }
    void start ()
    {
        bombMap = new Matrix (Box.ZERO);
        for (int j = 0; j < totalBombs; j ++) // цикл для размещения всех бомб
        placeBomb ();
    }
    Box get (Coord coord)
    {
        return bombMap.get(coord);

    }
    private void fixBombCout(){
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }
    private void placeBomb ()
    {
        while (true){
            Coord coord = Ranges.getRamdomCoord(); // размещение бомбы в случайном месте координат
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set (coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }
    private void incNumbersAroundBomb (Coord coord){
        for (Coord around : Ranges.getCoordAround(coord))
            if (Box.BOMB != bombMap.get(around))
            bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
