package org.kryogenic.rsbot.script.yTalker.strat;

import org.kryogenic.rsbot.script.yTalker.Session;
import org.kryogenic.rsbot.struct.kStrategy;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

/**
 * @author: Kale
 * @date: 18/08/12
 * @version: 0.0
 */
public class Talk extends kStrategy {
    private final Session session;
    private int index;

    public Talk(Session session, kStrategy... dependencies) {
        super(dependencies);
        this.session = session;
    }

    @Override
    public void run() {
        if(session.linesChanged) {
            index = 0;
            session.linesChanged = false;
        } else if (index >= session.speakLines.length) {
            index = 0;
        }
        session.lastSaid = session.speakLines[index];
        Keyboard.sendText(session.speakLines[index], true, session.speakSpeed - 2, session.speakSpeed + 2);
        System.out.println(session.speakLines[index] + " : " + session.spamTimer.getElapsed());
        session.linesSpoken++;
        session.spamTimer = new Timer(Random.nextInt(3500, 7500));
        index++;
        session.canTalk = false;
    }

    @Override
    public boolean validate() {
        return session.canTalk && session.speakLines != null;
    }
}