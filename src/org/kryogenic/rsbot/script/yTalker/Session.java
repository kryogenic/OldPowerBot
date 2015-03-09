package org.kryogenic.rsbot.script.yTalker;

import org.kryogenic.rsbot.struct.kSession;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;

/**
 * @author: Kale
 * @date: 18/08/12
 * @version: 0.0
 */
public class Session extends kSession {
    public Timer spamTimer;
    public boolean canTalk = false;

    public int linesSpoken = 0;

    public boolean linesChanged = false;
    public String lastSaid = "";
    public String[] speakLines;
    public int speakSpeed;
}
