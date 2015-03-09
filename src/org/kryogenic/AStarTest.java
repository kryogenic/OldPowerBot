package org.kryogenic;

import org.kryogenic.rsbot.struct.kPaint;
import org.kryogenic.rsbot.struct.kScript;
import org.kryogenic.util.astar.AStar;
import org.kryogenic.util.astar.Node;
import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Author: Kale
 * Date: 01/08/12
 * Time: 1:47 AM
 */
@Manifest(
        authors = {"kryo"},
        description = "Tests stuff",
        hidden = false,
        name = "A* Test",
        topic = 0,
        version = 0.0,
        vip = false,
        website = "http://www.kryogenic.org"
)
public class AStarTest extends kScript implements PaintListener, MouseListener {
    
    private kPaint paint;
    private FindPath findPath;
    
    @Override
    public void updatePaint(kPaint paint) {
        
    }

    @Override
    protected void setup() {
        kPaint.Builder builder = new kPaint.Builder(this);
        builder.skill(Skills.MINING).font("visitor.ttf");
        paint = new kPaint(builder);
        
        findPath = new FindPath();
        findPath.setTask(findPath);
        provide(findPath);
    }

    @Override
    public void onRepaint(Graphics g) {
        g.setColor(Color.BLUE);
        findPath.destination.draw(g);
        if(findPath.path != null) {
            for(int i = 0; i < findPath.path.length; i++) {
                g.setColor(i == 0 ? Color.YELLOW : i == findPath.path.length - 1 ? Color.BLUE : Color.GREEN);
                findPath.path[i].draw(g);
            }
        }
        paint.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        findPath.path = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    class FindPath extends Strategy implements Task {
        
        public Tile[] path = null;
        public Tile destination = null;
        
        @Override
        public boolean validate() {
            return path == null;
        }
        
        @Override
        public void run() {
            try {
                while(SceneEntities.getAt(destination = Players.getLocal().getLocation().randomize(5, 5), SceneEntities.TYPE_BOUNDARY | SceneEntities.TYPE_INTERACTIVE) != null);
                log(String.format("Destination: %s", destination.toString()));
                path = AStar.pathTo(destination);
                log(""+Node.instances);
                Node.instances = 0;
                if(path == null)
                    error("Fail!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
