package org.kryogenic.rsbot.script.yTalker;

import org.kryogenic.rsbot.script.yTalker.strat.ChatMonitor;
import org.kryogenic.rsbot.script.yTalker.strat.Talk;
import org.kryogenic.rsbot.struct.kPaint;
import org.kryogenic.rsbot.struct.kScript;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.*;
import org.powerbot.game.api.util.Timer;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Kale
 * @date: 18/08/12
 * @version: 0.0
 */
@Manifest(
        authors = {"kryo"},
        description = "Speaks your mind",
        hidden = false,
        name = "λTalker",
        topic = 0,
        version = 1.1,
        vip = false,
        website = "http://www.kryogenic.org"
)
public class yTalker extends kScript {
    
    Session session = new Session();
    GUI gui;

    @Override
    public void updatePaint(kPaint paint) {
        paint.updateOther("Lines spoken", String.valueOf(session.linesSpoken));
    }

    @Override
    protected void setup() {
        final kScript script = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui = new GUI(script, session);
                gui.setVisible(true);
            }
        });

        kPaint.Builder builder = new kPaint.Builder(this);
        builder.font("sleep.ttf");
        paint = new kPaint(builder);

        session.spamTimer = new Timer(Random.nextInt(3500, 7500));

        provide(new ChatMonitor(session));
        provide(new Talk(session));
    }
    
    @Override
    public void onStop() {
        if(gui != null)
            gui.dispose();
    }
}
