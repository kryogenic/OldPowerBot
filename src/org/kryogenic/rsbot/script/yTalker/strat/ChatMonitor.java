package org.kryogenic.rsbot.script.yTalker.strat;

import org.kryogenic.rsbot.script.yTalker.Session;
import org.kryogenic.rsbot.struct.kStrategy;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Kale
 * @date: 20/08/12
 * @version: 0.0
 */
public class ChatMonitor extends kStrategy {
    private final Session session;

    public ChatMonitor(Session session, kStrategy... dependencies) {
        super(dependencies);
        this.session = session;
    }

    @Override
    public void run() {
        if(!session.spamTimer.isRunning()) {
            session.canTalk = true;
        } else {
            WidgetChild chatbox =  Widgets.get(137, 57);
            for(int i = 0; i < 5; i++) {
                Pattern p = Pattern.compile(Players.getLocal().getName() + ": <col=[0-9a-fA-F]*>" + session.lastSaid);
                Matcher m = p.matcher(chatbox.getChild(i).getText());
                if(m.matches()) {
                    return;
                }
            }
            session.canTalk = true;
        }
    }

    @Override
    public boolean validate() {
        return !session.canTalk && !session.lastSaid.equals("") && session.spamTimer.getElapsed() > Random.nextInt(1850, 2300);
    }
}