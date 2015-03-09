package org.kryogenic.rsbot.script.yTalker;

import org.kryogenic.rsbot.struct.kScript;
import org.powerbot.game.api.Manifest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Random;

/**
 * @author: Kale
 * @date: 18/08/12
 * @version: 0.0
 */
public class GUI extends JFrame {

    private final kScript script;
    private final Session session;

    public GUI(kScript script, Session session) {
        this.script = script;
        this.session = session;
        initComponents();
    }

    private void initComponents() {
        final JLabel title = new JLabel();
        final JTextArea speakTA = new JTextArea(5, 60);
        final JScrollPane scrollPane = new JScrollPane(speakTA);
        final JButton start = new JButton();
        final JButton stop = new JButton();
        final JSlider speedSlider = new JSlider();
        final JLabel speedSliderLabel = new JLabel();

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints GBC = new GridBagConstraints();

        title.setText(script.getClass().getAnnotation(Manifest.class).name());
        title.setFont(new Font("Arial", Font.BOLD, 28));
        GBC.gridx = 0;
        GBC.gridy = 0;
        // Format for Insets is (bottom, left, right, top)
        GBC.insets = new Insets(5, 5, 5, 0);
        GBC.anchor = GridBagConstraints.CENTER;
        contentPane.add(title, GBC);

        speakTA.setText("Put what you want to say here.\nPut the next thing you want to say here.");
        speakTA.setEnabled(true);
        speakTA.setLineWrap(true);
        speakTA.setWrapStyleWord(true);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setMinimumSize(new Dimension(375, 150));
        scrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("What to say:"),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        scrollPane.getBorder()));
        GBC.gridx = 0;
        GBC.gridy = 1;
        // Format for Insets is (bottom, left, right, top)
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.anchor = GridBagConstraints.CENTER;
        contentPane.add(scrollPane, GBC);

        speedSliderLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        speedSliderLabel.setText("Typing Speed (WPM)");
        speedSliderLabel.setEnabled(true);
        GBC.gridx = 0;
        GBC.gridy = 3;
        // Format for Insets is (bottom, left, right, top)
        GBC.insets = new Insets(0, 5, 5, 5);
        GBC.anchor = GridBagConstraints.CENTER;
        contentPane.add(speedSliderLabel, GBC);

        speedSlider.setMinimumSize(new Dimension(350, 60));
        speedSlider.setMaximum(100);
        speedSlider.setMinimum(25);
        speedSlider.setBorder(null);
        speedSlider.setOpaque(false);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setSnapToTicks(false);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setValue(new Random().nextInt(10) + 30);
        speedSlider.setFocusable(false);
        speedSlider.setEnabled(true);
        GBC.gridx = 0;
        GBC.gridy = 4;
        // Format for Insets is (bottom, left, right, top)
        GBC.insets = new Insets(5, 5, 5, 0);
        GBC.anchor = GridBagConstraints.CENTER;
        contentPane.add(speedSlider, GBC);

        start.setText("Start");
        start.setMinimumSize(new Dimension(300, 100));
        start.setFocusable(false);
        start.setEnabled(true);
        GBC.gridx = 0;
        GBC.gridy = 5;
        // Format for Insets is (bottom, left, right, top)
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.anchor = GridBagConstraints.CENTER;
        contentPane.add(start, GBC);
        start.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                start(speakTA.getText().split("\r|\n"), (int) (Math.pow(speedSlider.getValue() * 8, -1) * 60 * 1000));
                start.setText("Change Settings");
            }
        });

        stop.setText("Stop");
        stop.setMinimumSize(new Dimension(300, 50));
        stop.setFocusable(false);
        stop.setEnabled(true);
        GBC.gridx = 0;
        GBC.gridy = 6;
        // Format for Insets is (bottom, left, right, top)
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.anchor = GridBagConstraints.CENTER;
        contentPane.add(stop, GBC);
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
                stop();
                start.setText("Start");
            }
        });

        setTitle(title.getText());
        setResizable(false);
        //setSize(w, h);
        setSize(400, 600);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });
    }

    private void cancel() {
        session.canTalk = false;
        dispose();
        script.stop();
    }

    private void start(String[] lines, int speed) {
        session.speakSpeed = speed;
        session.linesChanged = true;
        session.speakLines = lines;
        session.canTalk = true;
    }

    private void stop() {
        session.canTalk = false;
        session.speakLines = null;
    }
}