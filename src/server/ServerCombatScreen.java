package server;

import encounter.*;
import monster.Monster;
import tracker.Combatant;
import java.util.*;
import java.io.File;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class ServerCombatScreen extends JFrame {
    //private DNDLibrary lib;
    //private Encounter enc;
    private ArrayList<Combatant> combatants;
    private final Dimension VERTICAL_GAP = new Dimension(0, 5);
    private final Dimension HORIZONTAL_GAP = new Dimension(5, 0);
    private final int INNER_HEIGHT = 25;

    public ServerCombatScreen(ArrayList<Combatant> combatants) {
        //this.lib = lib;
        //this.enc = enc;
        this.combatants = combatants;
        setTitle("Encounter");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int yesNo = (int) JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?",
                        "Exit Tracker", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (yesNo == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });

        initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMinimumSize(new Dimension(500, 500));
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setMaximumSize(new Dimension(500, 500));

        //add combatatants to panels, make sure to count for if a moster has multiples
        for (Combatant i : combatants) {
            for (int j = 0; j < i.getQuantity(); j++) {
                final int index = j;

                if (!i.isReinforcement() && !i.getHP(index).equals("0")) {
                    JPanel comPanel = new JPanel();
                    comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.X_AXIS));
                    comPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    JLabel name = new JLabel(i.getName());
                    name.setMinimumSize(new Dimension(120, INNER_HEIGHT));
                    name.setMaximumSize(new Dimension(120, INNER_HEIGHT));
                    name.setPreferredSize(new Dimension(120, INNER_HEIGHT));

                    comPanel.add(name);
                    comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
                    panel.add(comPanel);
                }
            }
        }

        add(panel);
        pack();
        setVisible(true);
    }

    public void update(ArrayList<Combatant> combatants) {
        this.combatants = combatants;
        getContentPane().removeAll();
        initialize();
    }
}