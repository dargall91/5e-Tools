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
    private final String musicDat = "Data/music.dat";
    private FileInputStream fileInputStream;
    private int trackPos = 0;
    private int trackLen = 0;

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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        JPanel comPanel = new JPanel();
        
        for (int i = 0; i < combatants.size(); i++) {
            if (i % 12 == 0) {
                comPanel = new JPanel();
                mainPanel.add(comPanel);
                comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
                comPanel.setAlignmentY(Component.TOP_ALIGNMENT);
            }
            
            JLabel name = new JLabel(combatants.get(i).getName());
            name.setFont(new Font(name.getFont().getName(), Font.BOLD, 60));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            comPanel.add(name);
            comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
        }

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Starts the music for the encounter
     *
     * @param encName The name of the encounter being run, determines what track will play
     */
    public void startEncounter(String encName) {
        Encounter encounter = lib.getEncounter(encName);
        trackPos = 0;
        playMusic(encounter.getTheme(), trackPos);
    }

    /**
     * Plays the specified song track
     *
     * @param track The file name of the track
     * @param startPos The starting position for the track
     */
    public void playMusic(String track, int startPos) {
        try {
            File musicFile = new File(musicDat);
            Scanner scan = new Scanner(musicFile);
            String path = scan.nextLine();

            if (music != null && !music.getName().equals(track)) {
                stopMusic();
            }

            if (play) {
                return;
            }

            music = new File(path + track);
            play = true;

            new Thread() {
                public void run() {
                    try {
                        while (play) {
                            fileInputStream = new FileInputStream(music);
                            trackLen = fileInputStream.available();
                            fileInputStream.skip(trackPos);
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

    public int pauseMusic() {
        try {
            play = false;
            trackPos = trackLen - fileInputStream.available();
            player.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trackPos;
    }

    public void stopMusic() {
        play = false;
        player.close();
        trackPos = 0;
    }

    /**
     * Updates the list of combatants
     *
     * @param jsonCombat the list of comabatants
     */
    public void updateEncounter(JSONArray jsonCombat) {
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
        trackPos = 0;
        player.close();
        dispose();
    }
}
