5e Tools is a tool suite me, a Dungeon Master running a 5th Edition Dungeons and Dragons campaign, to create monsters and combat scenarios, and run the combat scenarios, all in one place. All monsters and combat scenarios are created in a client app, and are then saved to a server running on a Raspberry Pi. When combat is started, the turn order is displayed on a TV connected to my Raspberry Pi for all my players to see, as well play a combat music track. From the client, I can easily track all of the monsters' stats to better manage the flow of combat.

[The Desktop version of the client application can be found here.](https://github.com/dargall91/5e-Tools-DesktopClient)
[The Android version of the client application can be found here.](https://github.com/dargall91/5e-Tools-AndroidClient)

Why not use one of the many tools out there that other people have already made? Well, there are a couple reasons, the most import ones being:
 - Why should I pay for someone elses product, or give them ad revenue, when I can make my own?
 - I wanted to put into practice some of the things that I had recently learned in college in hopes of starting a protfolio
 - Other tools out there do not allow you to make custom monsters and inject them directly into combat scenarios
 - I wanted to implement features that other tools do not have, such as adding reinforcements mid combat
 - I wanted a tool that would automaitcally manage some things for me, such as automatically playing and looping combat music
 - I have a simplified custom rule for breaking ties in turn order that other tools do not follow

This project is currently undgoing a huge refactor:
 - I am changing json libraries from org.json to org.simple.json due to org.json's software license containing the line "The Software shall be used for Good, not Evil." It is well known amongst players of D&D that Dungeon Masters are inherently evil, and will do many evil things to mess with their players. Even ignoring the fact that this line means that org.json is technically not open source, I am evil, and therefore unqualified to continue usinging this library.
 - The Desktop Client and Server were once part of the same project build (this one). They are being separated
 - The Desktop Client currently has two separate parts: an application for creating the monsters and combat scenarios, and an application for running the combat scenarios. These will be combined into one application to match the android client
 - I am changing build tools from Ant to Gradle
 - I plan to add additional features to this project, such a custom item creator, and the ability to make monsters begin combat with the "invisible" status, thus hiding them from players in turn order until they have been spotted
