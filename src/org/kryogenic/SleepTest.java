package org.kryogenic;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Time;

/**
 * @author: Kale
 * @date: 02/08/12
 * @version: 0.0
 */
@Manifest(
        authors = {"kryo"},
        description = "Tests stuff",
        hidden = false,
        name = "SleepTest",
        topic = 0,
        version = 0.0,
        vip = false,
        website = "http://www.kryogenic.org"
)
public class SleepTest extends ActiveScript {

    @Override
    protected void setup() {
        provide(new Strategy(
                new Task() {
                    @Override
                    public void run() {
                        while(Widgets.get(335, 49).getText() == null)
                            Time.sleep(2500);
                        System.out.println("Trade window found!");
                    }
                }
        ) {
            public boolean validate() {
                return true;
            }
        });
    }
}
