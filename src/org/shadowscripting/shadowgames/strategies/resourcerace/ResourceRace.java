package org.shadowscripting.shadowgames.strategies.resourcerace;

import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.shadowscripting.shadowgames.C;
import org.shadowscripting.shadowgames.Session;
import org.kryogenic.util.RSBot;

import java.awt.*;

/**
 * Author: Kale
 * Date: 24/07/12
 * Time: 11:40 PM
 */
public class ResourceRace extends Strategy implements Task {

    Point f = new Point(-1, -1);
    
    public boolean validate() {
        return Widgets.get(C.WIDGET_RR_CONFIRM).getLocation() != f ||
                Widgets.get(C.WIDGET_RR_INSTRUCTIONS).getLocation() != f ||
                Widgets.get(C.WIDGET_RR_SCORE).getLocation() != f;
    }

    @Override
    public void run() {
        if (Widgets.get(C.WIDGET_RR_CONFIRM).getLocation() != f) {
            Widgets.get(C.WIDGET_RR_CONFIRM).getChild(C.WIDGET_RR_CONFIRM_YES).click(true);
        } else if (Widgets.get(C.WIDGET_RR_INSTRUCTIONS).getLocation() != f) {
            switch(Random.nextInt(0, 6)) {
                case 0: case 1: case 2: case 3:
                    Widgets.get(C.WIDGET_RR_INSTRUCTIONS).getChild(C.WIDGET_RR_INSTRUCTIONS_CONTINUE).click(true);
                    break;
                case 4:
                    Widgets.get(C.WIDGET_RR_INSTRUCTIONS).getChild(C.WIDGET_RR_INSTRUCTIONS_X).click(true);
                    break;
                case 5:
                    switch(Session.resourceRace) {
                        case MINING:
                            Walking.walk(RSBot.randTileFromArea(C.ROCKS_AREA)); // TODO rest of skills
                    }
            }
        } else if (Widgets.get(C.WIDGET_RR_SCORE).getLocation() != f) {
            Widget score = Widgets.get(C.WIDGET_RR_SCORE);
            String desc = score.getChild(C.WIDGET_RR_SCORE_DESCRIPTION).getText(); // TODO parse desc
            score.getChild(C.WIDGET_RR_SCORE_PLAY_AGAIN).click(true);
        }
    }
}
