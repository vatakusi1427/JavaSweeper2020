package sweeper;

import java.util.concurrent.Callable;

class Matrix {
    private Box [] [] matrix;

    Matrix (Box defaultBox){
        matrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        //Цикл по перебору всех координат
        for (Coord coord : Ranges.getAllCoords())
            matrix [coord.x] [coord.y] = defaultBox;
            }
            //геттерт и сеттер указаных координат
            Box get (Coord coord){
       //проверяем нет ли переполнения массива, находится ли координата в пределах нашего экрана
        if (Ranges.inRange (coord)) // функцию inRange реализуем в классе  Range
        return matrix[coord.x] [coord.y];
        return null; // если не находится, тогда нулл
            }
            // установит нужное значение в указанную клетку
    void set (Coord coord, Box box){
        if (Ranges.inRange (coord)) // функцию inRange реализуем в классе  Range
        matrix [coord.x] [coord.y] = box;
    }



}
