# 5e Tools

5e Tools is a tool suite for me, a Dungeon Master running a 5th Edition 
Dungeons & Dragons campaign, where I can manage various things needed to run 
the game all in one convenient application. This repo is for the server.

[The Desktop version of the client application can be found here.](https://github.com/dargall91/5e-Tools-DesktopClient)

[The Android version of the client application can be found here.](https://github.com/dargall91/5e-Tools-AndroidClient)

The desktop client has fallen behind the android client as I no longer have 
a laptop, making it less useful.

## Why not use one of the many tools that already exist?

The original goal of this project was simple: create an application that 
allowed me to manage everything I needed in one location. When I started this
project, there were very few tools out there that allowed you to manage more 
than one aspect of the game at once. There were separate tools for creating 
encounters, creating monsters, and running combat (initiative). Most tools did 
not allow you to easily add your custom monsters to your encounters or
include them in the initiative list. Most initiative trackers also did not 
let your players see the turn order without them making an account on the 
website and keeping their phones on until combat was done. To keep track of 
all of these things you needed many browser tabs open all at once, and it 
gets very messy. Additionally, I like to use music when combat is running, 
which meant running YouTube at the same time. Unless you pay for YouTube 
Premium, or you have a laptop, the music will stop when you suspend the app 
to check one of the many browser tabs mentioned above.

5e Tools accomplishes my goal. In one application, I can create monsters, 
create encounters using those monsters, and track initiative. My players can 
see the turn order on a TV screen, and music automatically plays when 
combat starts. 5e Tools has the added bonus of allowing me to incorporate 
extra features that some other tools out there lack, such as a sound board 
and the ability to include Lair Actions in an encounter.

## How does 5e Tools work?

Monsters and Combat Scenarios (AKA Encounters) can be created on either client 
application and saved on the server. When an Encounter is started, the 
server will display the turn order of all combatants on the screen and 
begin playing the selected battle track. When the encounter ends, the music 
will stop, and turn order display will close. The clients also contain a 
sound board which can be used when certain events occur, such as falling 
into a trap or landing a critical hit.

### Monster Builder
 - Add and Delete monsters on the server
 - Fully customize a monster from scratch
 - Copy an existing monster, ideal for creating related monsters with 
   similar traits
 - Hide the true identity of a monster from the players by specifying a 
   "display name" to show up on the screen in combat

### Encounter Builder
 - Add and Delete encounters on the server
 - Create encounters out of the monsters saved on the server
 - Utilizes the rules from the Dungeon Master's Guide to calculate the 
   difficulty of the combat
 - Select a battle theme to be played when the encounter is started
 - Each monster's initiative is pre-rolled to save time when the encounter 
   begins
 - Monsters can be flagged as a reinforcement; reinforcements do not appear 
   at the start of combat and can be added later through the combat tracker
 - Monsters can be marked as minions; minions are monsters which are so weak 
   that they do not influence the difficulty of the encounter
 - Monsters can be marked as invisible; invisible monsters do not appear on 
   the screen for the players to see, keeping their presence hidden
 - Lair Actions can be added to an encounter to always occur at initiative 
   count 20

### Combat Tracker
 - Before an encounter is loaded, a list of all the players' characters are 
   shown
   - Add and Delete characters on the server
   - Track a character's Armor Class (how hard they are to hit) and their 
     Initiative Bonus (influences when they take their turn in combat and 
     used to break initiative ties)
 - After loading an encounter, the battle track selected for the 
   encounter plays through the TV connected to the server
 - Select which characters are in the combat, record their initiative rolls, 
   then start the encounter
 - Once started, the TV displays all the combatants that are not flagged 
   as a reinforcement or invisible; all non-reinforcement combatants are 
   displayed on the client for the user to track
 - Monsters flagged as reinforcements can be added any time once combat has 
   started; additional reinforcements can also be added
 - Invisible monsters can be set to visible mid-combat
 - Monsters can be killed or revived mid-combat, removing or adding them 
   back to the server display for the players to see
 - Killing a monster with the kill button will play a sound effect through 
   the client
 - Ending the combat returns the client display to the list of player 
   characters and removes the display on the server
 - When choosing to end the encounter, you can choose to also stop the music 
   or let it keep playing

### Music & Sound Effects
 - The client contains a sound board for sound effects such as critical hits,
   triggering traps, and more
 - The client also includes a full music player for controlling music 
   outside of combat or changing it mid-combat

## Future Changes

This project undergoes occasional updates to add new features and fix bugs. 
It is close to what I'd consider "complete" as nearly all the features I 
wanted to include have been added. The next and possibly last feature I plan to 
add is one that allows me to view a monsters stats from within the combat 
tracker for quicker reference.  
