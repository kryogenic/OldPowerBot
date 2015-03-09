package org.kryogenic.rsbot.struct;

import org.kryogenic.util.AutoIncInteger;
import org.kryogenic.util.IO;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;

import java.awt.*;
import java.io.File;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates a paint
 *
 * @author kryo
 * @version 1.0
 * @date 03/09/11
 */
public class kPaint {
    private final long startTime;
    private final kScript script;
    private final Manifest manifest;

    private Map<Skill, Integer> skills = new HashMap<Skill, Integer>();
    private Map<String, String> other = new HashMap<String, String>();
    private Font font;
    private Color color;

    public static class Builder {
        private final long startTime;
        private final kScript script;

        private Map<Skill, Integer> skills = new HashMap<Skill, Integer>();
        private Map<String, String> other = new HashMap<String, String>();
        private String font;
        private Color color = new Color(200, 0, 0);

        public Builder(kScript script) {
            startTime = System.currentTimeMillis();
            this.script = script;
        }

        public Builder skill(int skillIndex) {
            skills.put(Skill.valueOf(skillIndex), Skills.getExperience(skillIndex));
            return this;
        }

        public Builder font(String font) {
            this.font = font;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }
    }

    public kPaint(Builder builder) {
        this.startTime = builder.startTime;
        this.script = builder.script;
        this.manifest = script.getClass().getAnnotation(Manifest.class);

        this.skills = builder.skills;
        this.other = builder.other;
        this.color = builder.color;

        String fs = System.getProperty("file.separator");
        File sd = Environment.getStorageDirectory();
        File ld = new File(sd.getPath() + fs + manifest.name());
        if (sd.canWrite() && !ld.exists()) {
            if (!ld.mkdir()) {
                Logger.getLogger(script.getClass().getName()).log(Level.SEVERE, "Could not create storage directory");
            }
        }
        try {
            File fontFile = new File(ld.getPath() + fs + builder.font);
            if (!fontFile.exists() || fontFile.length() == 0) {
                if(fontFile.exists() && fontFile.length() == 0)
                    if(!fontFile.delete())
                        Logger.getLogger(script.getClass().getName()).log(Level.SEVERE, "Unable to delete old, 0-length font file at " + fontFile.getPath());
                IO.fileDownload(manifest.website() + "/" + manifest.name().replaceAll(" ", "").replaceAll("λ", "y") + "/" + builder.font, ld.getPath());
            }
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(12F);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(script.getClass().getName()).log(Level.INFO, "Unable to load run time font. Replacing with Helvetica");
            font = new Font("Helvetica", Font.PLAIN, 12);
        }
    }

    public void draw(Graphics g) {
        try {
            Rectangle gameScreen = new Rectangle(Game.getDimensions());
            Point mouseLoc = Mouse.getLocation();
            int cursorSize;
            if (System.currentTimeMillis() - Mouse.getPressTime() < 75) {
                g.setColor(Color.WHITE);
                cursorSize = 4;
            } else {
                g.setColor(color);
                cursorSize = 6;
            }
            int outerCursorMod = cursorSize / 2;
            g.drawRect(mouseLoc.x - cursorSize, mouseLoc.y - cursorSize, cursorSize * 2, cursorSize * 2);
            g.drawRect(mouseLoc.x - cursorSize - outerCursorMod, mouseLoc.y - cursorSize - outerCursorMod, (cursorSize + outerCursorMod) * 2,
                    (cursorSize + outerCursorMod) * 2);
            g.drawRect(mouseLoc.x - 1, mouseLoc.y - 1, 2, 2);
            g.setColor(color);
            g.drawLine(mouseLoc.x, 0, mouseLoc.x, mouseLoc.y - cursorSize);
            g.drawLine(mouseLoc.x, gameScreen.height, mouseLoc.x, mouseLoc.y + cursorSize);
            g.drawLine(0, mouseLoc.y, mouseLoc.x - cursorSize, mouseLoc.y);
            g.drawLine(gameScreen.width, mouseLoc.y, mouseLoc.x + cursorSize, mouseLoc.y);

            script.updatePaint(this);
            g.setFont(font);
            List<String> lines = new ArrayList<String>();
            lines.add(String.format("%s v%.2f by %s", manifest.name(), manifest.version(), manifest.authors()[0]));
            long runTime = System.currentTimeMillis() - startTime;
            double runHours = runTime / 3600000d;
            lines.add(String.format("Run time: %s", Time.format(runTime)));
            NumberFormat f = NumberFormat.getInstance(Locale.getDefault());
            for (String name : other.keySet())
                if (name.charAt(0) == 450)
                    lines.add(String.format("%s: %s | %s: %s", name.substring(1), other.get(name), "p/h", f.format((int) (Integer.parseInt(other.get(name)) / runHours))));
                else
                    lines.add(String.format("%s: %s ", name, other.get(name)));
            for (Skill skill : skills.keySet())
                lines.add(String.format("%s xp: %d | %s: %s", skill.toString(), Skills.getExperience(skill.getIndex()) - skills.get(skill), "p/h", f.format((int) ((Skills.getExperience(skill.getIndex()) - skills.get(skill)) / runHours))));
            int height = 0;
            int width = 0;
            FontMetrics fm = g.getFontMetrics();
            for (String line : lines) {
                width = fm.stringWidth(line) > width ? fm.stringWidth(line) : width;
                height = 4 + 10 + fm.getHeight() * (skills.size() + other.size() + 2) + (10) * skills.size();
            }

            Point chatTopLeft = Widgets.get(137).getChild(0).getAbsoluteLocation();
            int x = 15;
            AutoIncInteger y = new AutoIncInteger(chatTopLeft.y - 20 - height);
            y.setIncAmount(fm.getHeight());
            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
            g.fill3DRect(x - 5, y.incAndGet() - 1, width += 10, height, true);
            for (String line : lines) {
                g.setColor(Color.WHITE);
                if (line.contains(" xp: ")) {
                    Skill skill = Skill.valueOf(line.split(" ")[0].toUpperCase());
                    g.drawString(line, x, y.incAndGet());
                    y.inc();
                    int fillAmount = (width - 10) ;//* Skills.getPercentToNextLevel(skill.getIndex()) / 100; TODO
                    g.setColor(new Color(170, 170, 170));
                    g.drawRect(x, y.get() - 6, width - 10, 10);
                    g.fillRect(x + 1, y.get() - 5, fillAmount, 6);
                    g.setColor(new Color(120, 120, 120));
                    g.fillRect(x + 1, y.get(), fillAmount, 4);
                    g.setColor(Color.WHITE);
                    String barText = skill.toString() + " level: " + Skills.getRealLevel(skill.getIndex()) +
                            " (+" + (Skills.getLevel(skill.getIndex()) - Skills.getLevelAt(skills.get(skill))) + ")" +
                            " " ;//+ Skills.getPercentToNextLevel(skill.getIndex()) + "%"; TODO
                    int barTextXMod = ((width - 10) / 2) - (fm.stringWidth(barText) / 2);
                    g.drawString(barText, x + barTextXMod, y.get() + 2);
                    y.inc(5);
                } else {
                    g.drawString(line, x, y.incAndGet());
                    if (line.contains(manifest.name())) {
                        y.inc(3);
                        g.drawLine(x + 2, y.get(), width - 5, y.get());
                        y.inc(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private enum Skill {
        AGILITY(16), ATTACK(0), CONSTITUTION(3), CONSTRUCTION(22), COOKING(7), CRAFTING(12), DEFENSE(1), DUNGEONEERING(24),
        FARMING(19), FIREMAKING(11), FISHING(10), FLETCHING(9), HERBLORE(15), HUNTER(21), MAGIC(6), MINING(14), PRAYER(5),
        RANGE(4), RUNECRAFTING(20), SLAYER(18), SMITHING(13), STRENGTH(2), SUMMONING(23), THEIVING(17), WOODCUTTING(8);

        private int index;

        Skill(int index) {
            this.index = index;
        }

        @Override
        public String toString() {
            char[] chars = this.name().toLowerCase().toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return String.copyValueOf(chars);
        }

        public int getIndex() {
            return index;
        }

        public static Skill valueOf(int index) {
            for (Skill skill : values())
                if (skill.getIndex() == index)
                    return skill;
            return null;
        }
    }

    public void updateOther(String key, String value) {
        other.put(key, value);
    }

    public int getSkillExp(int skill) {
        return Skills.getExperience(skill) - skills.get(Skill.valueOf(skill));
    }
}