package org.kryogenic.rsbot.struct;

import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.bot.event.listener.PaintListener;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.logging.Level;

/**
 * @author kryo
 * @version 1.0
 * @date 03/09/11
 */
public abstract class kScript extends ActiveScript implements PaintListener {
    
    protected kPaint paint;
    
    public void error(String s) {
        log(s, Level.SEVERE);
    }
    
    public void log(String s) {
        log(s, Level.INFO);
    }
    
    public void log(String s, Level l) {
        System.out.println("[" + this.getClass().getAnnotation(Manifest.class).name() + "] [" + l.getLocalizedName()+ "] " + s);
    }
    
    public void onRepaint(Graphics g) {
        if(paint != null) paint.draw(g);
    }
    
    public abstract void updatePaint(kPaint paint);
}
