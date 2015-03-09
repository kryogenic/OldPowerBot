package org.kryogenic.rsbot.script.yLimpwurt.paths;

import org.kryogenic.rsbot.struct.walking.obstacles.Door;
import org.kryogenic.rsbot.struct.walking.Path;
import org.kryogenic.rsbot.struct.walking.WalkPath;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author: Kale
 * @date: 24/08/12
 * @version: 0.0
 */
public class BankToDungeon extends WalkPath {
    @Override
    public void init() {
        add(new Path(new Tile(3189, 3438, 0), new Tile(3115, 3449, 0)));
        add(new Door(1805, 4, "Open"));
        /*add(new Obstacle() { TODO fucking rsbot api...
            @Override
            public void pass() {
                SceneEntities.getNearest(12389).interact("Climb-Down");
            }
        });
        add(new Path(new Tile(3115, 9856, 0), new Tile(3104, 9826, 0)));
        add(new Obstacle() {
            @Override
            public void pass() {
                SceneEntities.getNearest(52853).interact("Enter");
            }
        });*/
        add(new Path(new Tile(1134, 4589, 0), new Tile(1119, 4573, 0)));
    }
}
