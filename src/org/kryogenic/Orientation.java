package org.kryogenic;

import org.kryogenic.rsbot.struct.kPaint;
import org.kryogenic.rsbot.struct.kScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;

/**
 * @author: Kale
 * @date: 04/08/12
 * @version: 0.0
 */
@Manifest(
        authors = {"kryo"},
        description = "Tests stuff",
        hidden = false,
        name = "OrientationTest",
        topic = 0,
        version = 0.0,
        vip = false,
        website = "http://www.kryogenic.org"
)
public class Orientation extends kScript implements PaintListener {
    @Override
    public void updatePaint(kPaint paint) {
        
    }

    @Override
    protected void setup() {
        
    }

    @Override
    public void onRepaint(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.drawString(Players.getLocal().getOrientation() + "", 200, 200);
    }
}
