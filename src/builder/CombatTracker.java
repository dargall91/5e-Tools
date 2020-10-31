package tracker;

import client.*;
import encounter.*;
import monster.Monster;
import player.*;
import ui.*;
//import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class CombatTracker extends JFrame {
	private JList selectList;
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setAlignmentY(Component.LEFT_ALIGNMENT);
		panel.setMinimumSize(new Dimension(500, 500));
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setMaximumSize(new Dimension(500, 500));
		
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
		panel.add(labels);
		panel.add(Box.createRigidArea(VERTICAL_GAP));
		
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
			
			panel.add(playerPanel);
			panel.add(Box.createRigidArea(VERTICAL_GAP));
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
		
		panel.add(add);
		panel.add(Box.createRigidArea(VERTICAL_GAP));
		panel.add(load);
		add(panel);
		pack();
		setVisible(true);
	}
	
	private void loadEncounter() {
		getContentPane().removeAll();
		repaint();
		//start music
		proxy.startEncounter(encounter.getName());

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMinimumSize(new Dimension(500, 500));
		panel.setMaximumSize(new Dimension(500, 500));
		panel.setPreferredSize(new Dimension(500, 500));
		
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
		
		panel.add(pcs());
		panel.add(Box.createRigidArea(VERTICAL_GAP));
		monsters();
		panel.add(start);
		add(panel);
		pack();
		setVisible(true);
		repaint();
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

	//TODO: most of this is defunct, really only needs parts related to adding monsters to monData array
	private void monsters() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);

		
		JPanel labels = new JPanel();
		labels.setMaximumSize(new Dimension(500, INNER_HEIGHT));
		labels.setLayout(new BoxLayout(labels, BoxLayout.X_AXIS));
		labels.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel monLabel = new JLabel("Monster:");
		JLabel initLabel = new JLabel("Initiative:");
		JLabel reinLabel = new JLabel("Reinforcement:");
		
		labels.add(monLabel);
		labels.add(Box.createRigidArea(new Dimension(60, 0)));
		labels.add(initLabel);
		labels.add(Box.createRigidArea(new Dimension(20, 0)));
		labels.add(reinLabel);
		panel.add(labels);
		
		ArrayList<MonsterData> monData = encounter.getMonsterData();
		
		for (int i = 0; i < monData.size(); i++) {
			final int index = i;
			JPanel monPanel = new JPanel();
			monPanel.setMaximumSize(new Dimension(500, INNER_HEIGHT));
			monPanel.setLayout(new BoxLayout(monPanel, BoxLayout.X_AXIS));
			monPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			int quantity = monData.get(i).getQuantity();
			
			JLabel name;
			if (quantity > 1)
				name = new JLabel(monData.get(i).getMonster() + " (x" + quantity + ")");
				
			else
				name = new JLabel(monData.get(i).getMonster());
			
			name.setMinimumSize(new Dimension(120, INNER_HEIGHT));
			name.setMaximumSize(new Dimension(120, INNER_HEIGHT));
			name.setPreferredSize(new Dimension(120, INNER_HEIGHT));
			
			JComboBox initiative = new JComboBox(initList);
			initiative.setMinimumSize(new Dimension(50, INNER_HEIGHT));
			initiative.setMaximumSize(new Dimension(50, INNER_HEIGHT));
			initiative.setPreferredSize(new Dimension(50, INNER_HEIGHT));
			initiative.setSelectedIndex(5);
			
			JCheckBox reinBox = new JCheckBox();
			reinBox.setSelected(monData.get(index).isReinforcement());
			reinBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == 1)
						encounter.setReinforcement(index, true);
					
					else
						encounter.setReinforcement(index, false);
				}
			});

			combatants.add(new Combatant(monData.get(index), proxy.getMonster(monData.get(index).getMonster())));
			
			monPanel.add(name);
			monPanel.add(Box.createRigidArea(HORIZONTAL_GAP));
			monPanel.add(initiative);
			monPanel.add(Box.createRigidArea(new Dimension(40, 0)));
			monPanel.add(reinBox);
			panel.add(monPanel);
		}
	}
	
	private void runEncounter() {
		getContentPane().removeAll();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setMinimumSize(new Dimension(500, 500));
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setMaximumSize(new Dimension(500, 500));
		
		//add combatatants to panels, make sure to count for if a moster has multiples
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

					JButton kill = new JButton("Kill");
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
					panel.add(comPanel);
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
				panel.add(comPanel);
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
				JLabel instrLabel = new JLabel("Check to box next to the reincforcements to add");
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
							if (!i.isReinforcement())
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

		panel.add(Box.createRigidArea(VERTICAL_GAP));
		panel.add(reinforcements);
		panel.add(Box.createRigidArea(VERTICAL_GAP));
		panel.add(newRein);
		panel.add(Box.createRigidArea(VERTICAL_GAP));
		panel.add(finish);

		add(panel);
		pack();
		setVisible(true);
		repaint();
	}
	
	private void encListValueChanged(ListSelectionEvent event) {
		if (!selectList.isSelectionEmpty())
			selected = selectList.getSelectedValue().toString();
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
