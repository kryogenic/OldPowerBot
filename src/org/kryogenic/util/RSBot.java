package org.kryogenic.util;

import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Author: Kale
 * Date: 24/07/12
 * Time: 11:22 PM
 */
public class RSBot {
    public static Tile randTileFromArea(Area a) {
        Tile[] t = a.getTileArray();
        return t[Random.nextInt(0, t.length)];
    }

    // Interesting variant of method overriding.
    // Does not explicitly specify which type of parameters it takes,
    // does not handle wrong parameters very well,
    // and is presumably faster
    // Nice and fast if you're the only one working on the code
    public static <E> E shake(E e) {
        if(e instanceof Point) {
            Point p = (Point) e;
            return (E) new Point(p.x + Random.nextInt(-2, 3), p.x + Random.nextInt(-2, 3));
        } if(e instanceof Tile) {
            Tile t = (Tile) e;
            ArrayList<Tile> good = new ArrayList<>();
            good.add(t);
            // Find adjacent tiles without objects on top
            for(int x = -1; x < 2; x++) {
                for(int y = -1; y < 2; y++) {
                    SceneObject o = SceneEntities.getAt(new Tile(t.getX() + x, t.getY() + y, t.getPlane()));
                    if(o == null) {
                        good.add(new Tile(t.getX() + x, t.getY() + y, t.getPlane()));
                    }
                }
            }
            Tile st = good.get(Random.nextInt(0, good.size())); // Shaken "t"
            return (E) new Tile(t.getX() + Random.nextInt(-2, 3), st.getY() + Random.nextInt(-2, 3), st.getPlane());
        } else {
            throw new IllegalArgumentException(String.format("Type %s not supported for method public static <E> shake(E e)", e.getClass().getName()));
        }
    }

}
