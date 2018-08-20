package ru.nikson69.gui;

import ru.nikson69.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {

    private final Game game;
    private JPanel panel;
    private JLabel label;
    private final int IMAGE_SIZE = 50;

    public Window() {
        int COLS = 10;
        int ROWS = 10;
        int BOMBS = 13;
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initPanel();
        initMenu();
        initLabel();
        initFrame();
    }

    private void initMenu() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Игра");
        JMenuItem newGame = new JMenuItem("Новая игра");
        JMenuItem exit = new JMenuItem("Выход");

        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initLabel();
                game.start();
                label.setText(getMessage());
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

    private void initPanel() {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Cord cord : Ranges.getAllCords()) {
                    g.drawImage((Image) game.getImageBox(cord).image,
                            cord.x * IMAGE_SIZE, cord.y * IMAGE_SIZE, this);

                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                game.closedOpenToBox();
                panel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.closedOpenToBox();

                label.setText(getMessage());
                label.setBackground(getColorGameState());
                panel.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Cord cord = new Cord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.openToBox(cord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.flagAndCloseToBox(cord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.openBoxAround(cord);

                panel.repaint();
                label.setText(getMessage());
                label.setBackground(getColorGameState());
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сапер");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void initLabel() {
        label = new JLabel("Добро пожаловать!");
        label.setBackground(Color.blue);
        label.setOpaque(true);
        add(label, BorderLayout.SOUTH);
    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Пока все хорошо, продолжай в том же духе! Осталось мин:" + game.getBombCounter();
            case BOMBER:
                return "Проигрыш ): Попробуй еще раз (;";
            case WINNER:
                return "Победа!";
            case NEWGAME:
                return "Начата новая игра!";
            default:
                return "Добро пожаловать!";
        }
    }

    private Color getColorGameState() {
        switch (game.getState()) {
            case PLAYED:
                return Color.BLUE;
            case BOMBER:
                return Color.RED;
            case WINNER:
                return Color.GREEN;
            default:
                return Color.BLUE;
        }
    }

    private void setImages() {
        for (ImagesBox imagesBox : ImagesBox.values()) {
            imagesBox.image = getImage(imagesBox.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
