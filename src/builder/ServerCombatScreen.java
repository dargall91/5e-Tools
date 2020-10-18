package tracker;

import encounter.*;
import monster.Monster;
import library.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import org.json.JSONObject;
import org.json.JSONArray;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class ServerCombatScreen extends JFrame {
    private DNDLibrary lib;
    private ArrayList<SimpleCombatant> combatants;
    private final Dimension VERTICAL_GAP = new Dimension(0, 15);
    private final Dimension HORIZONTAL_GAP = new Dimension(15, 0);
    private final int INNER_HEIGHT = 25;
    private File music;
    private Player player;
    private boolean play;

    public ServerCombatScreen(DNDLibrary lib) {
        this.lib = lib;
        combatants = new ArrayList<SimpleCombatant>();

        setTitle("Encounter");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int yesNo = (int) JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?",
                        "Exit Tracker", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (yesNo == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
    }

    /**
     * Initializes the display of combatants
     */
    private void initialize() {
        getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMinimumSize(new Dimension(500, 500));
        panel.setPreferredSize(new Dimension(500, 500));
        panel.setMaximumSize(new Dimension(500, 500));

        for (SimpleCombatant i : combatants) {
            JPanel comPanel = new JPanel();
            comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.X_AXIS));
            comPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            //TODO: maybe make text bigger after testing on big screen
            JLabel name = new JLabel(i.getName());
            name.setFont(new Font(name.getFont().getName(), Font.BOLD, 20));

            comPanel.add(name);
            comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
            panel.add(comPanel);
        }

        add(panel);
        pack();
        setVisible(true);
    }

    /**
     * Starts the music for the encounter
     *
     * @param encName The name of the encounter being run, determines what track will play
     */
    public void start(String encName) {
        play = true;
        Encounter encounter = lib.getEncounter(encName);
        music = new File("Music/" + encounter.getTheme());

        try {
            //TODO: thread might not be needed once served is moved to RPi
            new Thread() {
                public void run() {
                    try {
                        while (play) {
                            FileInputStream fileInputStream = new FileInputStream(music);
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                            player = new Player(bufferedInputStream);
                            player.play();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            System.out.println("Error in ServerCombatScreen.start: " + e.getMessage());
        }
    }

    /**
     * Updates the list of combatants
     *
     * @param jsonCombat the list of comabatants
     */
    public void update(JSONArray jsonCombat) {
        combatants.clear();

        for (int i = 0; i < jsonCombat.length(); i++)
            combatants.add(new SimpleCombatant(jsonCombat.getJSONObject(i)));

        initialize();
    }

    /**
     * Ends the encounter, stops the music
     */
    public void endEncounter() {
        play = false;
        setVisible(false);
        player.close();
        dispose();
    }
}