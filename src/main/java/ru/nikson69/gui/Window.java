package ru.nikson69.gui;

import ru.nikson69.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {

    private Game game;
    private JPanel panel;
    private JMenuBar jMenuBar;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public Window(){
        game = new Game(COLS,ROWS,BOMBS);
        game.start();
        setImages();
        initPanel();

        initMenu();
        initLabel();
        initFrame();
    }

    private void  initMenu(){
        jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Игра");
        JMenuItem newGame = new JMenuItem("Новая игра");
        JMenuItem exit = new JMenuItem("Выход");

        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initLabel();
                game.start();
                label.setText(getMessage ());
                label.setBackground(getColorGameState());
                panel.repaint();

            }
        });

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        menu.add(newGame);
        menu.addSeparator();
        menu.add(exit);

        jMenuBar.add(menu);
        setJMenuBar(jMenuBar);
    }
    private void initPanel(){
        panel = new JPanel()//создаем новую панель
        {
            @Override
            protected void paintComponent(Graphics g) { //рисуем форму
                super.paintComponent(g);
                for(Coord coord : Ranges.getAllCoords()){ //проходим по всем координатам
                    g.drawImage((Image) game.getMinerImages(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE,this);

                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                //if (e.getButton()==MouseEvent.MOUSE_RELEASED){
                game.pressTwoButton2();
                panel.repaint();//}
            }

            @Override
            public void mouseClicked(MouseEvent e){
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton()==MouseEvent.BUTTON2)
                    game.pressTwoButton2();

                label.setText(getMessage ());
                label.setBackground(getColorGameState());
                panel.repaint();




            }


            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton()==MouseEvent.BUTTON2)
                    game.pressTwoButton(coord);
                panel.repaint();
                label.setText(getMessage ());
                label.setBackground(getColorGameState());

            }

        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE)); //размер панели
        add(panel);//добавим панель на форуму add  наследует от JFrame
    }

    private Color getColorGameState() {
        switch (game.getState()){
            case PLAYED: return Color.BLUE;
            case BOMBER: return Color.RED;
            case WINNER: return Color.GREEN;
            default:     return Color.BLUE;
        }
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//программа закрывается при нажатие на х
        setTitle("Сапер");//заголовок программы
        setResizable(false);//не меняем размер
        setVisible(true);//форму видно
        pack(); //усанавливает размер окна
        setLocationRelativeTo(null);//открывается по центру
        setIconImage(getImage("icon"));
    }

    private void initLabel () {
        label = new JLabel("Добро пожаловать!");
        label.setBackground(Color.blue);
        label.setOpaque(true);
        add(label, BorderLayout.SOUTH);
    }

    private void setImages(){ //устанавливаем картинки
        for (MinerImages minerImages: MinerImages.values()){
            minerImages.image = getImage(minerImages.name().toLowerCase());
        }
    }

    private Image getImage(String name){ // получаем картинки
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    private String getMessage() {
        switch (game.getState()){
            case PLAYED: return "Пока все хорошо, продолжай в том же духе! Осталось мин:" + game.getBombCounter();
            case BOMBER: return "Проигрыш ): Попробуй еще раз (;";
            case WINNER: return "Победа!";
            case NEWGAME:return "Начата новая игра!";
            default:     return "Добро пожаловать!";
        }
    }
}
