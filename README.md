# 5e Tools

5e Tools is a tool suite for me, a Dungeon Master running a 5th Edition Dungeons & Dragons campaign, where I can 
manage various things needed to run the game all in one convenient application. This repo is for the server.

[The Android client can be found here.](https://github.com/dargall91/5e-Tools-AndroidClient)

[The Desktop version of the client application can be found here. (deprecated)](https://github.com/dargall91/5e-Tools-DesktopClient)

I no longer maintain the desktop client. It was originally the only client for this application, but once I made the 
android client I rarely used it, and once my laptop died I used it even less.

## Why not use the many tools that already exist?

The original goal of this project was simple: create an application that allowed me to manage everything I needed in 
one location. When I started this project, there were very few tools out there that allowed you to manage more than 
one aspect of the game at once. There were separate tools for creating encounters, creating monsters, and running 
combat (initiative), but they were mostly independent of each other. Additionally, these tools did have some of the 
features I wanted such as including custom monsters in the initiative tracker, automatically playing music when combat
began, automatically looping music, letting players see the initiative order without having their phones out, 
tracking player stats, etc. To keep track of everything you needed many browser tabs and applications open all at 
once, which gets very messy.

5e Tools accomplishes my goal. In one application, I can create monsters, create encounters using those monsters, and
track initiative. My players can see the turn order on a TV screen, and music automatically plays when combat starts.
5e Tools has the added bonus of allowing me to incorporate extra features that some other tools out there lack, such 
as a sound board and tracking Lair Actions and adding reinforcements mid-combat. Most importantly, unlikes some 
applications that exist today, it's completely free for me to use.

## How does 5e Tools work?

%e Tools has three main components: a Combat Tracker, a Monster Builder, and an Encounter Builder. Monsters 
and Encounters can be created in the client application and are saved in an SQL database on the server. 
When an Encounter is started, the server will display the turn order of all combatants on the screen and begin 
playing the selected battle track, and will be cleared when combat ends. The client also contains a sound board which
can be used when certain events occur, such as triggering a trap, killing an enemy, or landing a critical hit. It 
also features an interface for playing music on the server independently of combat.

### Monster Builder
 - Create, Edit, and Archive monsters stored on the server
 - Monsters can be fully customized from their stats to even selecting an alternate name that will be displayed to 
   the players to hide a monster's true identity
 - Create a monster from scratch, or copy an existing monster to quickly make variants of a monster with the same 
   core traits

### Encounter Builder
 - Create, Edit, and Archive encounters stored on the server
 - Features a difficulty calculator that utilizes the rules from the 5th Edition Dungeon Masters guide
 - Monsters created in the monster builder can be added to an encounter
 - Select a battle theme to be automatically played when combat starts
 - Pre-roll each monster's initiative to save time when it's time to start combat, and automatically generate an 
   initiative total using the selected monster's bonuses
 - Monsters can be flagged as reinforcements, minions, or invisible
   - Reinforcements do not show up in either initiative tracker when combat begins, and can be added in later at the 
     user's discretion
   - Minions are monsters which are weak in comparison to the players' levels and do not influence the difficulty ot 
     the encounter
   - Invisible monsters do not appear on the server's initiative tracker during combat until they are marked visible,
     allowing the user to keep their existence a surprise until they are discovered. Invisible monsters will still 
     show up in the client initiative tracker so the user doesn't forget about them
 - Add a Lair Action to an encounter, a special action that always occurs on initiative count 20 (losing all ties). 
   Lair actions are not displayed on the server initiative tracker to hide them from players
 - An encounter can be loaded with the click of a button to avoid the need to search for it on the combat tracker; 
   loading an encounter also starts playing the selected battle track

### Combat Tracker
 - The combat tracker consists of two views, the Pre-Combat View and the Combat View, and the Sound Board
 - Pre-Combat View:
   - The default view when the client loads
   - Displays a list of all Player Characters in the campaign
   - Display and modify each character's Armor Class, rolled initiative, and initiative bonus
   - Add additional Player Characters to the campaign
   - Flag characters as combatants to automatically add them to initiative order once combat starts
   - Load an encounter, which begins playing the encounter's battle track to signal to the players that combat is 
     about to begin
   - Begin a loaded encounter once ready, which transitions the view to the Combat View
 - Combat View
   - Once combat is started, the flagged combatant players and the monsters in the encounter are sent to the server 
     to be displayed on the server's initiative tracker in turn order. Reinforcements, Invisible monsters, and Lair 
     Actions are hidden from the players in the server initiative tracker
   - Displays the same initiative order on the client for the user, but includes Lair Actions and Invisible monsters
   - Add both pre and non-flagged reinforcements to the encounter at any time, updating both initiative trackers with 
     the new initiative order
   - Track each combatant's Armor Class for quick reference, and the Hit Points of monsters
   - Kill, and revive, monsters at any time, or remove them from combat entirely. Killing a monster plays a sound clip
   - Encounters can be ended at any time, with the option to also stop the music or let it keep playing
   - Ending an encounter transitions the view back to the Pre-Combat View
 - Sound Board:
   - Features a variety of sound effects that can be played when certain in-game events happen, such as the 
     triggering of a trap, the death of a player or monster, or even acquiring certain conditions
   - Features a controller for playing, stopping, and pausing the music on the server independently of combat

## Future Changes
   - Support for multiple campaigns in the client. A campaign will be selected on load, and changed later. Only 
     players, monsters, and encounters that are part of the selected campaign are displayed in the client
   - A web application for the players to add new characters to a campaign and track basic things such as hit points 
