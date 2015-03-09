package org.kryogenic;

import org.kryogenic.rsbot.struct.kPaint;
import org.kryogenic.rsbot.struct.kScript;
import org.kryogenic.util.astar.AStar;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;

/**
 * Author: Kale
 * Date: 26/07/12
 * Time: 4:34 AM
 */
@Manifest(
        authors = {"kryo"},
        description = "Tests stuff",
        hidden = false,
        name = "Sequence Test",
        topic = 0,
        version = 0.0,
        vip = false,
        website = "http://www.kryogenic.org"
)
public class SequenceTest extends kScript implements PaintListener {
    
    Tile[] toPaint = null;
    
    class Scanner extends Strategy implements Task {

        @Override
        public boolean validate() {
            log("bleh");
            return true;
        }

        @Override
        public void run() {
            try {
                log("blah");
                SceneObject[] ironRocks = SceneEntities.getLoaded(2092, 2093, 5773, 5774, 5775, 6943, 6944, 9717, 9718, 9719, 11954, 11955, 11956, 14856, 14857, 14858, 14913, 14914, 21281, 21282, 29221,
                        29222, 31071, 31072, 31073, 32443, 32441, 32442, 37307, 37308, 37309);
                SceneObject[] silverRocks = SceneEntities.getLoaded(37304, 37305, 37306);
                Tile[] tiles = new Tile[ironRocks.length + silverRocks.length];
                double[] scores = new double[ironRocks.length + silverRocks.length];
                int i = 0;
                for(; i < ironRocks.length; i++) {
                    tiles[i] = ironRocks[i].getLocation();
                    scores[i] = 0.6;
                }
                int offset = i;
                for(; i - offset < silverRocks.length; i++) {
                    tiles[i] = silverRocks[i - offset].getLocation();
                    scores[i] = 0.9;
                }

                toPaint = AStar.getSequence(Players.getLocal().getLocation(), tiles, scores);
                log(toPaint[2].toString());
                Time.sleep(2500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void updatePaint(kPaint paint) {
    }

    @Override
    protected void setup() {
        provide(new Scanner());
    }

    @Override
    public void onRepaint(Graphics g) {
        if(toPaint == null || toPaint.length < 3)
            return;
        g.setColor(Color.RED);
        for(int i = 1; i < toPaint.length; i++) {
            Point p1 = Calculations.worldToScreen(toPaint[i - 1].getX(), toPaint[i - 1].getY(), toPaint[i - 1].getPlane());
            Point p2 = Calculations.worldToScreen(toPaint[i].getX(), toPaint[i].getY(), toPaint[i].getPlane());
            g.drawString(p1.toString(), 10, 10);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
