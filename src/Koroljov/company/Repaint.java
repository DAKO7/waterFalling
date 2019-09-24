package Koroljov.company;

import java.awt.*;

public class Repaint {
    public static void onRepaint(Graphics g)
    {
        long current_time = System.nanoTime();
        float delta_time = (current_time - GameWindow.last_frame_time) * 0.000000001f;
        GameWindow.last_frame_time = current_time;

        GameWindow.drop_top = GameWindow.drop_top + GameWindow.drop_v * delta_time;
        GameWindow.drop_left = GameWindow.drop_left + (GameWindow.direction*GameWindow.drop_v) * delta_time;
        //drop_left = drop_left + drop_v * delta_time;
        g.drawImage(GameWindow.background, 0,0,null);
        g.drawImage(GameWindow.dropWaterFall, (int) GameWindow.drop_left,(int) GameWindow.drop_top,null);
        g.drawImage(GameWindow.car, (int) GameWindow.drop_left,(int) GameWindow.drop_top,null);
        g.drawImage(GameWindow.kid, (int) GameWindow.drop_left,(int) GameWindow.drop_top,null);

        if (GameWindow.drop_top > GameWindow.game_window.getHeight()) {
            g.drawImage(GameWindow.gameOver, 300, 100, null);
            g.drawImage(GameWindow.restart, 0, 0, null);
            GameWindow.end = true;
        }
        if(GameWindow.drop_left <= 0.0 || GameWindow.drop_left + GameWindow.dropWaterFall_width > GameWindow.game_window.getWidth()){
            if(GameWindow.direction == -1) GameWindow.direction = 1;
            else GameWindow.direction = -1;
        }
        if (GameWindow.drawRecords)
        {
            for (int i = 0; i < GameWindow.recordsLast.size(); i++)
            {
                g.drawString(GameWindow.recordsLast.get(i), 200, 25 + 25 * i);
            }
        }

        GameWindow.nameEntry.isActive = GameWindow.end;
        GameWindow.nameEntry.update(g);
    }
}
