package Koroljov.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    public static GameWindow game_window;

    public static long last_frame_time;

    public static GameField game_field;

    public static  Image background, dropWaterFall, icon, gameOver, restart, car, kid;
    public static float drop_left = 200;
    //private static float drop_top = 200;
    public static float drop_top = -100;
    public static float drop_v = 200;
    public static int score = 0;
    public static boolean end;
    public static float dropWaterFall_width = 129;
    public static float dropWaterFall_height = 178;
    public static float car_width = 310;
    public static float car_height = 125;
    public static float kid_width = 170;
    public static float kid_height = 280;
    public static boolean pause = false;
    public static float drop_speed_save;

    public static double mousecordX = 0;
    public static double mousecordY = 0;
    public static int direction = -1;

    public static Entry nameEntry;
    public static Database db;

    public static boolean isRecorded = false;
    public static boolean drawRecords = false;
    public static ArrayList<String> recordsLast = new ArrayList<String>();

    public static void main(String[] args) throws IOException {

        /*try{
            String url = "jdbc:mysql://localhost/store?useLegacyDatetimeCode=false&serverTimezone=Europe/Helsinki";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            //String sqlCommand = "CREATE TABLE GameScore (idPlayer INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(45), Score INT, Date DATETIME)";

            try (Connection conn = DriverManager.getConnection(url, username, password)){

                //Statement statement = conn.createStatement();
                //statement.executeUpdate(sqlCommand);

                System.out.println("Connection to Store DB succesfull!");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }*/

        db = new Database("jdbc:mysql://localhost/store?useLegacyDatetimeCode=false&serverTimezone=Europe/Helsinki", "root", "");
        db.init();

        background = ImageIO.read(GameWindow.class.getResourceAsStream("towers.jpg")).getScaledInstance(906,478, Image.SCALE_DEFAULT);
        //dropWaterFall = ImageIO.read(GameWindow.class.getResourceAsStream("drop-of-water.png")).getScaledInstance((int) dropWaterFall_width, (int) dropWaterFall_height, Image.SCALE_DEFAULT);
        icon = ImageIO.read(GameWindow.class.getResourceAsStream("icon.png"));
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("GameOver.png"));
        restart = ImageIO.read(GameWindow.class.getResourceAsStream("restart.png")).getScaledInstance(50,50, Image.SCALE_DEFAULT);
        //car = ImageIO.read(GameWindow.class.getResourceAsStream("car.png")).getScaledInstance((int) car_width, (int) car_height, Image.SCALE_DEFAULT);
        kid = ImageIO.read(GameWindow.class.getResourceAsStream("kid.png")).getScaledInstance((int) kid_width, (int) kid_height, Image.SCALE_DEFAULT);



        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Когда прорамма закрывается, код останавливается
        game_window.setLocation(200,100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        game_field = new GameField();
        last_frame_time = System.nanoTime();
        game_window.add(game_field);
        game_window.setIconImage(icon);
        pause = false;
        game_window.setTitle("Drop of water in cocaine");
        game_field.addMouseListener(new mousePressed() {

        });
        nameEntry = new Entry();
        game_window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                nameEntry.keyPress(e);
                if (nameEntry.isActive && !isRecorded)
                {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        db.addRecord(nameEntry.text, score);
                        isRecorded = true;
                        recordsLast = db.getRecords();
                        drawRecords = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        game_window.setVisible(true);

    }
    public static void dropWaterFallResize() throws IOException {
        dropWaterFall = ImageIO.read(GameWindow.class.getResourceAsStream("drop-of-water.png")).getScaledInstance((int) dropWaterFall_width, (int) dropWaterFall_height, Image.SCALE_DEFAULT);
        car = ImageIO.read(GameWindow.class.getResourceAsStream("car.png")).getScaledInstance((int) car_width, (int) car_height, Image.SCALE_DEFAULT);
        car = ImageIO.read(GameWindow.class.getResourceAsStream("kid.png")).getScaledInstance((int) kid_width, (int) kid_height, Image.SCALE_DEFAULT);

    }

    public static int onDirection()
    {
        int rand = (int) (Math.random()*2+1);
        if (rand == 2) direction = 1;
        else direction = -1;
        System.out.println(direction);

        return direction;
    }

    public static class GameField extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Repaint.onRepaint(g);
            repaint();
        }
    }
}
