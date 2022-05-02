import javax.swing.*; //Alt + Enter
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Objects;
import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

// 1. Наследуем класс для запуска программы
public class JavaSweeper extends JFrame {

    private Game game;
    private JPanel panel; //4. Добавляем панель комментарии
    private JLabel label;

    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {
        new JavaSweeper(); // остается только создание экземляра; setVisible(true); // запуск программы в окне
     }

     private JavaSweeper (){ // создаем приватный конструктор
       game = new Game (COLS, ROWS, BOMBS);
       game.start();
        setImages();
        initLabel();
        initPanel(); //6. Инициализируем панель
        initFrame(); //3. Вызываем инициализации из конструктора (инициализируем фрейм)
     }
     private void initLabel (){
        label = new JLabel("Welcom!");
        add(label, BorderLayout.SOUTH);
     }

    private void initPanel(){ // 5. Добавляем метод, который будет инициализировать панель
        panel = new JPanel() { // создали панель

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        //создание адаптера мышки
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText (getMessange ());
                panel.repaint();
            }
        });


        // было: panel.setPreferredSize(new Dimension(500,500)); // размеры панелы
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE)); // стало
        add (panel);  // добавляем панель на нашу форму
    }

    private String getMessange() {
        switch (game.getState()){
            case PLAYED: return "Tink twice!";
            case BOMBED: return "You lose!";
            case WINNER: return "Congratulation!";
            default:return "Welcome!";
        }
    }

    private void initFrame (){ // 2. Создаем функцию (инициализируем)

         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//это чтобы закрыть программу (керстик)
         setTitle("Сапер");//установка заголовка (название прогрммы)
         setResizable(false); // нам не надо будет изменять размер нашего окна (в игре)
         setVisible(true); // Чтобы нашу форму было видно
         pack(); // функция меняет форму
         setLocationRelativeTo(null); // чтобы Окно располагалось по центру
         setIconImage(getImage("icon"));// установка иконки


     }
     private void setImages(){
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
     }

     //7. Функция получения картинок
    private Image getImage (String name){// имя картинки
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename))); // использование ресурсов
        return icon.getImage();
    }
}
