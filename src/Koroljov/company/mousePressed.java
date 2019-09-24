package Koroljov.company;

import Koroljov.company.GameWindow;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class mousePressed extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            if(GameWindow.pause)
            {
                GameWindow.pause=false;
                GameWindow.drop_v = GameWindow.drop_speed_save;
                try
                {
                    Robot r = new Robot();
                    r.mouseMove((int)GameWindow.mousecordX, (int)GameWindow.mousecordY);
                }
                catch (AWTException ee)
                {

                }
            }
            else {
                GameWindow.drop_speed_save = GameWindow.drop_v;
                GameWindow.drop_v = 0;
                GameWindow.mousecordX = MouseInfo.getPointerInfo().getLocation().getX();
                GameWindow.mousecordY = MouseInfo.getPointerInfo().getLocation().getY();
                GameWindow.pause = true;
            }
        }

        if(e.getButton() == MouseEvent.BUTTON1)
        {
            if(GameWindow.pause) return;

            int x = e.getX();
            int y = e.getY();



            float drop_right = GameWindow.drop_left + GameWindow.kid.getWidth(null);
            float drop_bottom = GameWindow.drop_top + GameWindow.kid.getHeight(null);
            boolean is_drop = x >= GameWindow.drop_left && x <= drop_right && y >= GameWindow.drop_top && y <= drop_bottom;
            if(is_drop){
                if(!(GameWindow.dropWaterFall_height > 25 && GameWindow.dropWaterFall_width > 50)) {
                    GameWindow.dropWaterFall_width = GameWindow.dropWaterFall_width -1;
                    GameWindow.dropWaterFall_height = GameWindow.dropWaterFall_height -2;
                    try {
                        GameWindow.dropWaterFallResize();
                    }
                    catch (IOException ioe){

                    }
                }

                //drop_top = 200;
                GameWindow.drop_top = -100;
                GameWindow.drop_left = (int) (Math.random() * (GameWindow.game_field.getWidth() - GameWindow.kid.getWidth(null)));
                GameWindow.drop_v = GameWindow.drop_v + 20;
                GameWindow.score++;
                GameWindow.onDirection();
                GameWindow.game_window.setTitle("Score: " + GameWindow.score);
            }

            if(GameWindow.end){
                boolean isRestart = x>=0 & x <= 0 + GameWindow.restart.getWidth(null) && y >= 0 && y <= 0 + GameWindow.restart.getHeight(null);
                if(isRestart){
                    GameWindow.end = false;
                    GameWindow.score = 0;
                    GameWindow.game_window.setTitle("Score: " + GameWindow.score);
                    GameWindow.drop_top=-100;
                    //drop_top=200;
                    GameWindow.drop_left = (int) (Math.random()*(GameWindow.game_field.getWidth()- GameWindow.kid.getWidth(null)));
                    GameWindow.drop_v = 200;
                    GameWindow.dropWaterFall_width = 129;
                    GameWindow.dropWaterFall_height = 178;
                    GameWindow.kid_width = 310;
                    GameWindow.kid_height = 125;
                    GameWindow.isRecorded = false;
                    GameWindow.drawRecords = false;
                }
            }
        }
    }
}
