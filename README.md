5e Tools is a tool suite for me, a Dungeon Master running a 5th Edition Dungeons & Dragons campaign, where I can manage various things needed to run the game all in one convenient application. This repo is for the server application. [The Desktop version of the client application can be found here.](https://github.com/dargall91/5e-Tools-DesktopClient) [The Android version of the client application can be found here.](https://github.com/dargall91/5e-Tools-AndroidClient)

How does 5e Tools work?
Monsters and Combat Scenarios (AKA Encounters) are created on the client applications and saved on a server so that the data can be accessed from either application. When a Combat Scenario begins, the server will play a combat theme on the connected TV and dsiplay the turn order of all the player characters and monsters involved for easy reference by my players. When the encounter ends, the music will stop, and turn order display will close. Additionally, I have added a soundboard to the client that I use to play sound effects when certain events occur, such as critical hits, or when a monster is killed.

Features

 - Create Custom Monsters
   - All monsters have a certain set of traits that must be defined (name, ability scores, hit points, etc). Beyond that, they can be given any number of additional traits
   - A monster can be copied to be used as a template to create a new monster, which is ideal for creating realated monsters with similar traits (currently supported on the desktop client only)
   - Monsters can be given a different "display name" that can be used to hide the monster's true identity fromt he players. When a combat scenario is running, this display name is what will be shown to the players on the TV screen
 - Create Combat Scenarios
   - Monsters saved on the server can be added to combat scenarios
   - The number of players involved in the combat scenario, and their character levels, can be defined
   - The combat scenario tracks the difficulty of the encounter using the rules defined in the Dungeon Master's Guide
   - A battle theme from a list of tracks saved on the server can be chosen for an encounter
   - Each monsters' initative (used in determining turn order) can be pre-rolled and saved here
   - Monsters can be marked as a reinforcement, that is they will not be present when the encounter starts, but can join in later
   - Monsters can be marked as minions, which means that they are so trvially easy to defeat they do not influence the difficulty of the encounter
   - Monsters can be marked as invisible, which means that they start the encounter hidden from the players until they have been spotted (feature not yet added)
 - Manage Player Characters
   - Tracks a character's Armor Class (that is, how easy the character is to hit) and initiative bonus (used for breaking ties in turn order) for quick reference by me
   - When a combat scenario begins, characters from this list will be selected to participate in the encounter
 - Run Combat Scenarios
   - Plays the battle track selected when creating the encounter on a TV to which the server is connected
   - After getting all of my player's initative rolls, any ties will be resolved, then the turn order will be displayed on the Tv so my players can easily reference it
   - The client application will also display the turn order, as well as a few neccessary stats for all involved combatatants for easy tracking by me
   - When a monster is killed or added to the encounter, the lists on the TV and client will be updaded accordingly
   - Combat can be ended from the client, which stops the music, and removes the turn order screen from the TV
 - Sound Effects
   - The client contains a sound board for sound effects
   - There are several buttons that I can click when certain events occur, such as critical hits, killing monsters, triggering traps, etc

Why not use one of the many tools out there that other people have already made? Well, there are a couple reasons, the most import ones being:

 - I wanted to put into practice some of the things that I had recently learned in college in hopes of starting a protfolio
 - Other tools out there do not allow you to make custom monsters and inject them directly into combat scenarios, you have to use the predefined monsters
 - I wanted to implement features that other tools do not have, such as adding monster reinforcements mid combat
 - I wanted a tool that would automaitcally manage some things for me, such as automatically playing and looping combat music
 - I have a simplified custom rule for breaking ties in turn order that other tools do not follow

This project is currently undgoing a huge refactor:

 - I am changing json libraries from org.json to org.simple.json due to org.json's software license containing the line "The Software shall be used for Good, not Evil." It is well known amongst players of D&D that Dungeon Masters are inherently evil, and will do many evil things to mess with their players. Even ignoring the fact that this line means that org.json is technically not open source, I am evil, and therefore unqualified to continue using this library.
 - The Desktop Client and Server were once part of the same project build (this one). They are being separated
 - The Desktop Client currently has two separate parts: an application for creating the monsters and combat scenarios, and an application for running the combat scenarios. These will be combined into one application to match the android client
 - I am changing build tools from Ant to Gradle
 - I plan to add additional features to this project in the future
