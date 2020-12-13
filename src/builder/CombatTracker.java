package tracker;

import client.*;
import encounter.*;
import monster.Monster;
import player.*;
import ui.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import javax.sound.sampled.*;

//TODO: combat tracker has evolved beyond a simple tracker to more of a home screen to be used during play, consider renaming and redefining packages
public class CombatTracker extends JFrame {
	private JList selectList;
	private JPanel combatPanel;
	private String selected;
	private DNDClientProxy proxy;
	private Encounter encounter;
	private ArrayList<String> players;
	private ArrayList<Combatant> combatants;
	private final Dimension VERTICAL_GAP = new Dimension(0, 5);
	private final Dimension HORIZONTAL_GAP = new Dimension(5, 0);
	private final Integer[] acList;
	private final Integer[] bonusList;
	private final Integer[] initList;
	private final Integer[] monInitList;
	private final int INNER_HEIGHT = 25;
	private final String PLAYER_CRIT = "Data/player_crit.dat";
	private final String MONSTER_CRIT = "Data/monster_crit.dat";
	private final String PLAYER_KILL = "Data/player_kill.dat";
	private final String MONSTER_KILL = "Data/monster_kill.dat";
	private final String TRAP = "Data/trap.dat";
	private final String FEAR = "Data/fear.dat";
	private final String AFFLICTION = "Data/affliction.dat";
	private final String VIRTUE = "Data/virtue.dat";
	private final String ABUSIVE = "Data/abusive.dat";
	private final String IRRATIONAL = "Data/irrational.dat";
	private final String PARANOID = "Data/paranoid.dat";
	private final String SELFISH = "Data/selfish.dat";
	private final String FEARFUL = "Data/fearful.dat";
	private final String HOPELESS = "Data/hopeless.dat";
	private final String MASOCHISTIC = "Data/masochistic.dat";
	private final String POWERFUL = "Data/powerful.dat";
	private final String COURAGEOUS = "Data/courageous.dat";
	private final String STALWART = "Data/stalwart.dat";
	private final String VIGOROUS = "Data/vigorous.dat";
	private final String FOCUSED = "Data/focused.dat";

	CombatTracker(DNDClientProxy proxy) {
		this.proxy = proxy;
		setTitle("D&D Combat Tracker");
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
		
		acList = new Integer[30];
		
		for (int i = 0; i < 30; i++)
			acList[i] = i + 1;
		
		bonusList = new Integer[21];
		
		for (int i = 0; i < 21; i++)
			bonusList[i] = i - 5;
			
		initList = new Integer[36];
		
		for (int i = 0; i < 36; i++)
			initList[i] = i - 5;

		monInitList = new Integer[20];

		for (int i = 0; i < 20; i++)
			monInitList[i] = i + 1;
		
		initialize();
	}
	
	private void initialize() {
		getContentPane().removeAll();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		combatPanel = new JPanel();

		panel.add(getCombatPanel());
		panel.add(getSoundPanel());
		add(panel);
		pack();
		setVisible(true);
	}

	private JPanel getCombatPanel() {
		combatPanel.setLayout(new BoxLayout(combatPanel, BoxLayout.Y_AXIS));
		combatPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		combatPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
		combatPanel.setMinimumSize(new Dimension(500, 900));
		combatPanel.setPreferredSize(new Dimension(500, 900));
		combatPanel.setMaximumSize(new Dimension(500, 900));

		JPanel labels = new JPanel();
		labels.setMaximumSize(new Dimension(500, INNER_HEIGHT));
		labels.setLayout(new BoxLayout(labels, BoxLayout.X_AXIS));
		labels.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel playLabel = new JLabel("PC:");
		JLabel acLabel = new JLabel("AC:");
		JLabel bonusLabel = new JLabel("Bonus:");

		labels.add(playLabel);
		labels.add(Box.createRigidArea(new Dimension(80, 0)));
		labels.add(acLabel);
		labels.add(Box.createRigidArea(new Dimension(35, 0)));
		labels.add(bonusLabel);
		combatPanel.add(labels);
		combatPanel.add(Box.createRigidArea(VERTICAL_GAP));

		players = proxy.getPlayerCharacterList();

		for (int i = 0; i < players.size(); i++) {
			PlayerCharacter pc = proxy.getPlayerCharacter(players.get(i));
			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
			playerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			JLabel name = new JLabel(pc.getName());
			name.setMinimumSize(new Dimension(100, INNER_HEIGHT));
			name.setMaximumSize(new Dimension(100, INNER_HEIGHT));
			name.setPreferredSize(new Dimension(100, INNER_HEIGHT));
			JComboBox ac = new JComboBox(acList);
			ac.setSelectedIndex(pc.getAC());
			ac.setMinimumSize(new Dimension(50, INNER_HEIGHT));
			ac.setMaximumSize(new Dimension(50, INNER_HEIGHT));
			ac.setPreferredSize(new Dimension(50, INNER_HEIGHT));

			ac.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pc.setAC((Integer) ac.getSelectedItem());
					proxy.updatePlayerCharacter(pc);
				}
			});

			JComboBox bonus = new JComboBox(bonusList);
			bonus.setSelectedIndex(pc.getBonus() + 5);
			bonus.setMinimumSize(new Dimension(50, INNER_HEIGHT));
			bonus.setMaximumSize(new Dimension(50, INNER_HEIGHT));
			bonus.setPreferredSize(new Dimension(50, INNER_HEIGHT));

			bonus.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					pc.setBonus((Integer) bonus.getSelectedItem());
					proxy.updatePlayerCharacter(pc);
				}
			});

			JButton delete = new JButton("Delete");
			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int yesNo = (int) JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete "
							+ name.getText() + "?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (yesNo == JOptionPane.YES_OPTION) {
						proxy.deletePlayerCharacter(name.getText());
						initialize();
					}
				}
			});

			playerPanel.add(name);
			playerPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
			playerPanel.add(ac);
			playerPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
			playerPanel.add(bonus);
			playerPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
			playerPanel.add(delete);

			combatPanel.add(playerPanel);
			combatPanel.add(Box.createRigidArea(VERTICAL_GAP));
		}

		JButton add = new JButton("Add PC");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = true;
				String message = "Enter the new PC's name:";
				String name;

				do {
					name = (String) JOptionPane.showInputDialog(null, message, "Add PC",
							JOptionPane.QUESTION_MESSAGE);

					if (name == null)
						break;

					else if (name.equals("")) {
						message = "Enter the new PC's name:";
						continue;
					}

					valid = proxy.addPlayerCharacter(name);

					if (valid) {
						initialize();
					}

					else {
						System.out.println("PC already exists");
						message = "There is already a PC named \"" + name + "\"\nEnter a different name:";
					}
				} while (!valid);
			}
		});

		JButton load = new JButton("Load Encounter");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel optionPanel = new JPanel();
				optionPanel.setMinimumSize(new Dimension(100, 500));
				optionPanel.setMaximumSize(new Dimension(100, 500));
				optionPanel.setPreferredSize(new Dimension(100, 500));
				optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));

				//contains search field and label
				JPanel searchPanel = new JPanel();
				searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
				JLabel searchLabel = new JLabel("Search:");
				JTextField searchField = new JTextField();
				searchField.setMinimumSize(new Dimension(100, INNER_HEIGHT));
				searchField.setMaximumSize(new Dimension(100, INNER_HEIGHT));
				searchField.setPreferredSize(new Dimension(100, INNER_HEIGHT));
				searchPanel.add(searchLabel);
				searchPanel.add(searchField);

				optionPanel.add(searchPanel);
				optionPanel.add(Box.createRigidArea(new Dimension(0, 5)));

				//contains list of monsters
				JScrollPane scroll = new JScrollPane();
				scroll.setPreferredSize(new Dimension(200, 200));

				selectList = new JList();
				ArrayList<String> encList = proxy.getEncounterList();

				selectList.setModel(new AbstractListModel() {
					public int getSize() {
						return encList.size();
					}

					public Object getElementAt(int i) {
						return encList.get(i);
					}
				});

				selectList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						encListValueChanged(event);
					}
				});

				scroll.setViewportView(selectList);
				optionPanel.add(scroll);

				//TODO: finish search bar
				//instead of using doclistener, use key listener, update list as each key is typed
				DeferredDocumentListener listener = new DeferredDocumentListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (searchField.getText().equals("")) {
							//display all
						}

						else {
							//display only those that contain the string
						}
					}
				});

				searchField.getDocument().addDocumentListener(listener);

				searchField.addFocusListener(new FocusListener() {
					@Override
					public void focusGained(FocusEvent e) {
						listener.start();
					}

					@Override
					public void focusLost(FocusEvent e) {
						listener.stop();
					}
				});

				int result = JOptionPane.showConfirmDialog(null, optionPanel, "Select an Encounter",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					encounter = proxy.getEncounter(selected);
					combatants = new ArrayList<Combatant>();
					loadEncounter();
				}
			}
		});

		combatPanel.add(add);
		combatPanel.add(Box.createRigidArea(VERTICAL_GAP));
		combatPanel.add(load);

		return combatPanel;
	}

	private JPanel getSoundPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentY(Component.LEFT_ALIGNMENT);
		panel.setMinimumSize(new Dimension(325, 900));
		panel.setPreferredSize(new Dimension(325, 900));
		panel.setMaximumSize(new Dimension(325, 900));

		JPanel crit = new JPanel();
		crit.setLayout(new BoxLayout(crit, BoxLayout.X_AXIS));
		crit.setMinimumSize(new Dimension(305, INNER_HEIGHT));
		crit.setPreferredSize(new Dimension(305, INNER_HEIGHT));
		crit.setMaximumSize(new Dimension(305, INNER_HEIGHT));

		JButton monCrit = new JButton("Enemy Critical");
		monCrit.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		monCrit.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		monCrit.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		monCrit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(MONSTER_CRIT);
			}
		});

		JButton pcCrit = new JButton("Player Critical");
		pcCrit.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		pcCrit.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		pcCrit.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		pcCrit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(PLAYER_CRIT);
			}
		});

		crit.add(monCrit);
		crit.add(Box.createRigidArea(new Dimension(5, 0)));
		crit.add(pcCrit);
		panel.add(crit);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel kill = new JPanel();
		kill.setLayout(new BoxLayout(kill, BoxLayout.X_AXIS));
		kill.setMinimumSize(new Dimension(305, INNER_HEIGHT));
		kill.setPreferredSize(new Dimension(305, INNER_HEIGHT));
		kill.setMaximumSize(new Dimension(305, INNER_HEIGHT));

		JButton monKill = new JButton("Enemy Death");
		monKill.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		monKill.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		monKill.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		monKill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(MONSTER_KILL);
			}
		});

		JButton pcKill = new JButton("Player Death");
		pcKill.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		pcKill.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		pcKill.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		pcKill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(PLAYER_KILL);
			}
		});

		kill.add(monKill);
		kill.add(Box.createRigidArea(new Dimension(5, 0)));
		kill.add(pcKill);
		panel.add(kill);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel trapFear = new JPanel();
		trapFear.setLayout(new BoxLayout(trapFear, BoxLayout.X_AXIS));
		trapFear.setMinimumSize(new Dimension(305, INNER_HEIGHT));
		trapFear.setPreferredSize(new Dimension(305, INNER_HEIGHT));
		trapFear.setMaximumSize(new Dimension(305, INNER_HEIGHT));

		JButton trap = new JButton("Trap");
		trap.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		trap.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		trap.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		trap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(TRAP);
			}
		});

		JButton fear = new JButton("Fear");
		fear.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		fear.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		fear.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		fear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(FEAR);
			}
		});

		trapFear.add(trap);
		trapFear.add(Box.createRigidArea(new Dimension(5, 0)));
		trapFear.add(fear);
		panel.add(trapFear);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));

		JPanel madness = new JPanel();
		madness.setLayout(new BoxLayout(madness, BoxLayout.X_AXIS));
		//madness.setMinimumSize(new Dimension(305, INNER_HEIGHT));
		//madness.setPreferredSize(new Dimension(305, INNER_HEIGHT));
		//madness.setMaximumSize(new Dimension(305, INNER_HEIGHT));

		JPanel afflictions = new JPanel();
		afflictions.setLayout(new BoxLayout(afflictions, BoxLayout.Y_AXIS));
		afflictions.setAlignmentY(Component.TOP_ALIGNMENT);
		//afflictions.setMinimumSize(new Dimension(305, INNER_HEIGHT));
		//afflictions.setPreferredSize(new Dimension(305, INNER_HEIGHT));
		//afflictions.setMaximumSize(new Dimension(305, INNER_HEIGHT));

		JButton randAffliction = new JButton("Affliction");
		randAffliction.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		randAffliction.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		randAffliction.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		randAffliction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(AFFLICTION);
			}
		});

		JButton irrational = new JButton("Irrational");
		irrational.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		irrational.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		irrational.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		irrational.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(IRRATIONAL);
			}
		});

		JButton paranoid = new JButton("Paranoid");
		paranoid.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		paranoid.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		paranoid.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		paranoid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(PARANOID);
			}
		});

		JButton selfish = new JButton("Selfish");
		selfish.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		selfish.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		selfish.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		selfish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(SELFISH);
			}
		});

		JButton abusive = new JButton("Abusive");
		abusive.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		abusive.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		abusive.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		abusive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(ABUSIVE);
			}
		});

		JButton fearful = new JButton("Fearful");
		fearful.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		fearful.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		fearful.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		fearful.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(FEARFUL);
			}
		});

		JButton hopeless = new JButton("Hopeless");
		hopeless.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		hopeless.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		hopeless.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		hopeless.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(HOPELESS);
			}
		});

		JButton masochistic = new JButton("Masochistic");
		masochistic.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		masochistic.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		masochistic.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		masochistic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(MASOCHISTIC);
			}
		});

		afflictions.add(randAffliction);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(irrational);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(paranoid);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(selfish);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(abusive);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(fearful);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(hopeless);
		afflictions.add(Box.createRigidArea(new Dimension(0, 5)));
		afflictions.add(masochistic);

		JPanel virtues = new JPanel();
		virtues.setLayout(new BoxLayout(virtues, BoxLayout.Y_AXIS));
		virtues.setAlignmentY(Component.TOP_ALIGNMENT);
		//virtues.setMinimumSize(new Dimension(305, INNER_HEIGHT));
		//virtues.setPreferredSize(new Dimension(305, INNER_HEIGHT));
		//virtues.setMaximumSize(new Dimension(305, INNER_HEIGHT));

		JButton randVirtue = new JButton("Virtue");
		randVirtue.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		randVirtue.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		randVirtue.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		randVirtue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(VIRTUE);
			}
		});

		JButton powerful = new JButton("Powerful");
		powerful.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		powerful.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		powerful.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		powerful.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(POWERFUL);
			}
		});

		JButton courageous = new JButton("Courageous");
		courageous.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		courageous.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		courageous.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		courageous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(COURAGEOUS);
			}
		});

		JButton stalwart = new JButton("Stalwart");
		stalwart.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		stalwart.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		stalwart.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		stalwart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(STALWART);
			}
		});

		JButton vigorous = new JButton("Vigorous");
		vigorous.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		vigorous.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		vigorous.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		vigorous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(VIGOROUS);
			}
		});

		JButton focused = new JButton("Focused");
		focused.setMinimumSize(new Dimension(150, INNER_HEIGHT));
		focused.setMaximumSize(new Dimension(150, INNER_HEIGHT));
		focused.setPreferredSize(new Dimension(150, INNER_HEIGHT));
		focused.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound(FOCUSED);
			}
		});

		virtues.add(randVirtue);
		virtues.add(Box.createRigidArea(new Dimension(0, 5)));
		virtues.add(powerful);
		virtues.add(Box.createRigidArea(new Dimension(0, 5)));
		virtues.add(courageous);
		virtues.add(Box.createRigidArea(new Dimension(0, 5)));
		virtues.add(stalwart);
		virtues.add(Box.createRigidArea(new Dimension(0, 5)));
		virtues.add(vigorous);
		virtues.add(Box.createRigidArea(new Dimension(0, 5)));
		virtues.add(focused);

		madness.add(afflictions);
		madness.add(Box.createRigidArea(new Dimension(5, 0)));
		madness.add(virtues);
		panel.add(madness);
		//panel.add(Box.createRigidArea(new Dimension(0, 5)));

		return panel;
	}

	//may not be needed
	private void refreshCombatScreen() {
		combatPanel.repaint();
	}

	//may not be needed
	private void refreshMusicScreen() {

	}
	
	private void loadEncounter() {
		combatPanel.removeAll();
		//start music
		proxy.startEncounter(encounter.getName());
		
		JButton start = new JButton("Start Encounter");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int yesNo = (int) JOptionPane.showConfirmDialog(null, "Begin Encounter?", "Begin",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (yesNo == JOptionPane.YES_OPTION) {
					for (int i = 0; i < combatants.size() - 1; i++)
						for (int j = i + 1; j < combatants.size(); j++)
							combatants.get(i).weigh(combatants.get(j));

					Collections.sort(combatants, Collections.reverseOrder());
					JSONArray combatArray = new JSONArray();
					for (Combatant i : combatants)
						for (int j = 0; j < i.getQuantity(); j++)
							if (!i.isReinforcement())
								combatArray.put(i.toSimpleJson());

					proxy.updateEncounter(combatArray);

					runEncounter();
				}
			}
		});
		
		combatPanel.add(pcs());
		combatPanel.add(Box.createRigidArea(VERTICAL_GAP));
		monsters();
		combatPanel.add(start);
		//add(combatPanel);
		//pack();
		//setVisible(true);
		combatPanel.repaint();
		pack();
		setVisible(true);
	}
	
	private JPanel pcs() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		int numPlayers = encounter.getTotalPlayers();
		
		JPanel labels = new JPanel();
		labels.setMaximumSize(new Dimension(500, INNER_HEIGHT));
		labels.setLayout(new BoxLayout(labels, BoxLayout.X_AXIS));
		labels.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel pcLabel = new JLabel("PC:");
		JLabel initLabel = new JLabel("Initiative:");
		
		labels.add(pcLabel);
		labels.add(Box.createRigidArea(new Dimension(100, 0)));
		labels.add(initLabel);
		panel.add(labels);
		
		for (int i = 0; i < numPlayers; i++) {
			final int index = i;
			JPanel playerPanel = new JPanel();
			playerPanel.setMaximumSize(new Dimension(500, INNER_HEIGHT));
			playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
			playerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JComboBox name = new JComboBox(players.toArray());
			name.setMinimumSize(new Dimension(120, INNER_HEIGHT));
			name.setMaximumSize(new Dimension(120, INNER_HEIGHT));
			name.setPreferredSize(new Dimension(120, INNER_HEIGHT));
			name.setSelectedIndex(i);
			
			name.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					combatants.get(index).setPlayerCharacter(proxy.getPlayerCharacter((String) name.getSelectedItem()));
				}
			});
			
			JComboBox initiative = new JComboBox(initList);
			initiative.setMinimumSize(new Dimension(50, INNER_HEIGHT));
			initiative.setMaximumSize(new Dimension(50, INNER_HEIGHT));
			initiative.setPreferredSize(new Dimension(50, INNER_HEIGHT));
			initiative.setSelectedIndex(5);
			
			initiative.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					combatants.get(index).setInitiative((Integer) initiative.getSelectedItem());
				}
			});
			
			combatants.add(new Combatant(proxy.getPlayerCharacter((String) name.getSelectedItem()),
				(Integer) initiative.getSelectedItem()));
			
			playerPanel.add(name);
			playerPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
			playerPanel.add(initiative);
			
			panel.add(playerPanel);
			panel.add(Box.createRigidArea(VERTICAL_GAP));
		}
		
		return panel;
	}

	private void monsters() {
		ArrayList<MonsterData> monData = encounter.getMonsterData();
		
		for (int i = 0; i < monData.size(); i++)
			combatants.add(new Combatant(monData.get(i), proxy.getMonster(monData.get(i).getMonster())));
	}
	
	private void runEncounter() {
		combatPanel.removeAll();
		
		//add combatatants to combatPanels, make sure to count for if a moster has multiples
		for (Combatant i : combatants) {
			if (i.isMonster() && !i.isReinforcement()) {
				for (int j = 0; j < i.getQuantity(); j++) {
					final int index = j;
					JPanel comPanel = new JPanel();
					comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.X_AXIS));
					comPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
					
					JLabel name = new JLabel(i.getName());
					name.setMinimumSize(new Dimension(120, INNER_HEIGHT));
					name.setMaximumSize(new Dimension(120, INNER_HEIGHT));
					name.setPreferredSize(new Dimension(120, INNER_HEIGHT));
					
					JLabel acLabel = new JLabel("AC:");
					JTextField ac = new JTextField(i.getAC(index));
					ac.setMinimumSize(new Dimension(50, INNER_HEIGHT));
					ac.setMaximumSize(new Dimension(50, INNER_HEIGHT));
					ac.setPreferredSize(new Dimension(50, INNER_HEIGHT));
					ac.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							i.setAC(index, ac.getText());
						}
					});
					
					JLabel hpLabel = new JLabel("HP:");
					JTextField hp = new JTextField(i.getHP(index));
					hp.setMinimumSize(new Dimension(50, INNER_HEIGHT));
					hp.setMaximumSize(new Dimension(50, INNER_HEIGHT));
					hp.setPreferredSize(new Dimension(50, INNER_HEIGHT));
					hp.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							i.setHP(index, hp.getText());
						}
					});

					JButton kill = new JButton();
					if (i.isAlive(index))
						kill.setText("Kill");

					else
						kill.setText("Revive");

					name.setEnabled(i.isAlive(index));
					ac.setEnabled(i.isAlive(index));
					hp.setEnabled(i.isAlive(index));

					kill.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (i.isAlive(index)) {
								i.kill(index);
								kill.setText("Revive");
							}

							else {
								i.revive(index);
								kill.setText("Kill");
							}

							name.setEnabled(i.isAlive(index));
							ac.setEnabled(i.isAlive(index));
							hp.setEnabled(i.isAlive(index));

							JSONArray combatArray = new JSONArray();
							for (Combatant c : combatants)
								for (int k = 0; k < c.getQuantity(); k++) {
									if (!c.isReinforcement() && c.isAlive(k))
										combatArray.put(c.toSimpleJson());
								}

							proxy.updateEncounter(combatArray);
						}
					});

					//if reinforcements from outside the encounter are added these are neccessary to update dead/alive
					//monsters, since all monsters are added to this list regardless of their status
					name.setEnabled(i.isAlive(index));
					ac.setEnabled(i.isAlive(index));
					hp.setEnabled(i.isAlive(index));
					
					comPanel.add(name);
					comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
					comPanel.add(acLabel);
					comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
					comPanel.add(ac);
					comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
					comPanel.add(hpLabel);
					comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
					comPanel.add(hp);
					comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
					comPanel.add(kill);
					combatPanel.add(comPanel);
				}
			}

			else if (!i.isMonster()) {
				JPanel comPanel = new JPanel();
				comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.X_AXIS));
				comPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
					
				JLabel name = new JLabel(i.getName());
				name.setMinimumSize(new Dimension(120, INNER_HEIGHT));
				name.setMaximumSize(new Dimension(120, INNER_HEIGHT));
				name.setPreferredSize(new Dimension(120, INNER_HEIGHT));
				
				JLabel acLabel = new JLabel("AC:");
				JTextField ac = new JTextField(i.getAC(1));
				ac.setMinimumSize(new Dimension(50, INNER_HEIGHT));
				ac.setMaximumSize(new Dimension(50, INNER_HEIGHT));
				ac.setPreferredSize(new Dimension(50, INNER_HEIGHT));
				ac.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						i.setAC(0, ac.getText());
					}
				});

				comPanel.add(name);
				comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
				comPanel.add(acLabel);
				comPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
				comPanel.add(ac);
				combatPanel.add(comPanel);
			}
		}
		
		JButton reinforcements = new JButton("Reinforcements");
		reinforcements.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel reinPanel = new JPanel();
				reinPanel.setLayout(new BoxLayout(reinPanel, BoxLayout.Y_AXIS));
				reinPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

				JPanel instructions = new JPanel();
				JLabel instrLabel = new JLabel("Check the box next to the reinforcements to add:");
				instructions.add(instrLabel);
				reinPanel.add(instructions);
				
				for (Combatant i : combatants) {
					if (i.isReinforcement()) {
						JPanel monPanel = new JPanel();
						JLabel reinName = new JLabel(i.getName() + " (x" + i.getQuantity() + ")");
						reinName.setMinimumSize(new Dimension(120, INNER_HEIGHT));
						reinName.setMaximumSize(new Dimension(120, INNER_HEIGHT));
						reinName.setPreferredSize(new Dimension(120, INNER_HEIGHT));

						JCheckBox addRein = new JCheckBox();
						addRein.addItemListener(new ItemListener() {
							@Override
							public void itemStateChanged(ItemEvent e) {
								if (e.getStateChange() == 1)
									i.setReinforcement(false);

								else
									i.setReinforcement(true);
							}
						});
						
						monPanel.add(reinName);
						monPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
						monPanel.add(addRein);

						reinPanel.add(Box.createRigidArea(VERTICAL_GAP));
						reinPanel.add(monPanel);
					}
				}
				
				Object[] options = {"DONE"};
				int result = JOptionPane.showOptionDialog(null, reinPanel, "Reinforcements", JOptionPane.PLAIN_MESSAGE,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				if (result == 0) {
					JSONArray combatArray = new JSONArray();
					for (Combatant i : combatants)
						for (int j = 0; j < i.getQuantity(); j++)
							if (!i.isReinforcement() && i.isAlive(j))
								combatArray.put(i.toSimpleJson());

					proxy.updateEncounter(combatArray);
					runEncounter();
				}
			}
		});
		
		//adds reinforcments from outside the predefined encounter
		JButton newRein = new JButton("New Reinforcements");
		newRein.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel reinPanel = new JPanel();
				reinPanel.setLayout(new BoxLayout(reinPanel, BoxLayout.Y_AXIS));
				reinPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

				JPanel instructions = new JPanel();
				JLabel instrLabel = new JLabel("Select a monster from the list and select the number to add");
				instructions.add(instrLabel);
				reinPanel.add(instructions);

				JPanel labels = new JPanel();
				labels.setLayout(new BoxLayout(labels, BoxLayout.X_AXIS));
				labels.setAlignmentX(Component.CENTER_ALIGNMENT);

				//TODO: adjust sizes to line up with jcomboboxes
				JLabel monLabel = new JLabel("Monster:");
				monLabel.setPreferredSize(new Dimension(120, INNER_HEIGHT));
				monLabel.setMinimumSize(new Dimension(120, INNER_HEIGHT));
				monLabel.setMaximumSize(new Dimension(120, INNER_HEIGHT));

				JLabel quanLabel = new JLabel("Quantity:");
				quanLabel.setPreferredSize(new Dimension(120, INNER_HEIGHT));
				quanLabel.setMinimumSize(new Dimension(120, INNER_HEIGHT));
				quanLabel.setMaximumSize(new Dimension(120, INNER_HEIGHT));

				JLabel initLabel = new JLabel("Initiative:");
				initLabel.setPreferredSize(new Dimension(120, INNER_HEIGHT));
				initLabel.setMinimumSize(new Dimension(120, INNER_HEIGHT));
				initLabel.setMaximumSize(new Dimension(120, INNER_HEIGHT));

				labels.add(monLabel);
				labels.add(quanLabel);
				labels.add(initLabel);

				reinPanel.add(Box.createRigidArea(VERTICAL_GAP));
				reinPanel.add(labels);

				JPanel monPanel = new JPanel();
				monPanel.setLayout(new BoxLayout(monPanel, BoxLayout.X_AXIS));
				monPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				ArrayList<String> monList = proxy.getMonsterList();

				JComboBox monBox = new JComboBox(monList.toArray());
				monBox.setPreferredSize(new Dimension(120, INNER_HEIGHT));
				monBox.setMinimumSize(new Dimension(120, INNER_HEIGHT));
				monBox.setMaximumSize(new Dimension(120, INNER_HEIGHT));

				//TODO: using aclist because it contains usable numbers for quantity, consider creating a new array
				JComboBox quantity = new JComboBox(acList);
				quantity.setPreferredSize(new Dimension(50, INNER_HEIGHT));
				quantity.setMinimumSize(new Dimension(50, INNER_HEIGHT));
				quantity.setMaximumSize(new Dimension(50, INNER_HEIGHT));
				quantity.setSelectedIndex(0);

				JComboBox initiative = new JComboBox(monInitList);
				initiative.setPreferredSize(new Dimension(50, INNER_HEIGHT));
				initiative.setMinimumSize(new Dimension(50, INNER_HEIGHT));
				initiative.setMaximumSize(new Dimension(50, INNER_HEIGHT));
				initiative.setSelectedIndex(0);

				monPanel.add(monBox);
				monPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
				monPanel.add(quantity);
				monPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
				monPanel.add(initiative);

				reinPanel.add(Box.createRigidArea(VERTICAL_GAP));
				reinPanel.add(monPanel);

				int result = JOptionPane.showConfirmDialog(null, reinPanel, "Outside Reinforcements", JOptionPane.OK_CANCEL_OPTION);

				if (result == 0) {
					Monster monster = proxy.getMonster((String) monBox.getSelectedItem());
					combatants.add(new Combatant(monster, (int) quantity.getSelectedItem(), (int) initiative.getSelectedItem()));

					for (Combatant i : combatants)
						i.reset();

					for (int i = 0; i < combatants.size() - 1; i++)
						for (int j = i + 1; j < combatants.size(); j++)
							combatants.get(i).weigh(combatants.get(j));

					Collections.sort(combatants, Collections.reverseOrder());
					JSONArray combatArray = new JSONArray();
					for (Combatant i : combatants)
						for (int j = 0; j < i.getQuantity(); j++)
							if (!i.isReinforcement() && i.isAlive(j))
								combatArray.put(i.toSimpleJson());

					proxy.updateEncounter(combatArray);
					runEncounter();
				}
			}
		});

		JButton finish = new JButton("Finish Encounter");
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int yesNo = (int) JOptionPane.showConfirmDialog(null, "End this encounter?",
					"End Encounter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
				if (yesNo == JOptionPane.YES_OPTION) {
					proxy.endEncounter();
					initialize();
				}
			}
		});

		combatPanel.add(Box.createRigidArea(VERTICAL_GAP));
		combatPanel.add(reinforcements);
		combatPanel.add(Box.createRigidArea(VERTICAL_GAP));
		combatPanel.add(newRein);
		combatPanel.add(Box.createRigidArea(VERTICAL_GAP));
		combatPanel.add(finish);

		combatPanel.repaint();
		pack();
		setVisible(true);
	}
	
	private void encListValueChanged(ListSelectionEvent event) {
		if (!selectList.isSelectionEmpty())
			selected = selectList.getSelectedValue().toString();
	}

	private void playSound(String lib) {
		try {
			//get the file directory
			File soundPath = new File(lib);
			Scanner scan = new Scanner(soundPath);
			String path = scan.nextLine();

			//select a clip at random from the directory
			File soundFiles = new File(path);
			String[] fileNames = soundFiles.list();
			Random rand = new Random();

			//load the randomly selected file
			File sound = new File(path + fileNames[rand.nextInt(fileNames.length)]);

			System.out.println(sound.getPath());

			//play the sound clip
			new Thread() {
				public void run() {
					try {
						//AudioClip audio = Applet.newAudioClip(sound.toURI().toURL());
						//audio.play();
						//URL url = this.getClass().getClassLoader().getResource("gameover.wav");
						AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound.toURI().toURL());
						// Get a sound clip resource.
						Clip clip = AudioSystem.getClip();
						// Open audio clip and load samples from the audio input stream.
						clip.open(audioIn);
						clip.start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			System.out.println("Error in ServerCombatScreen.start: " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		DNDClientProxy proxy = new DNDClientProxy(args[0], Integer.parseInt(args[1]));
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				CombatTracker tracker = new CombatTracker(proxy);
			}
		});
	}
}
