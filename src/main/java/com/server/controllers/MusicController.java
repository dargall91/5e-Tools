package com.server.controllers;

import com.server.entities.encounter.Music;
import com.server.repositories.MusicRepository;
import javazoom.jl.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("5eTools/api/music")
@RestController
public class MusicController {
    @Autowired
    private MusicRepository musicRepo;
    private Player musicPlayer;
    private int trackPos;
    private int trackLength;
    private File musicFile;
    private boolean playing;
    private FileInputStream fis;

    @PostMapping("{musicId}/play")
    public ResponseEntity<?> playMusic(@PathVariable int musicId) {
        Optional<Music> result = musicRepo.findById(musicId);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Music with id " + musicId + " not found.");
        }

        //stop current track if it is not the one that is playing
        if (musicFile != null && !musicFile.getName().equals(result.get().getFileName())) {
            stopMusic();
        }

        //if it is the same track and music is already playing, no action needed
        if (playing) {
            return ResponseEntity.ok().build();
        }

        try {
            musicFile = new File("/home/derek/Music/" + result.get().getFileName());
            playing = true;
            new Thread(() -> {
                try {
                    while (playing) {
                        fis = new FileInputStream(musicFile);
                        trackLength = fis.available();
                        fis.skip(trackPos);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        musicPlayer = new Player(bis);
                        musicPlayer.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while starting track with id " + musicId);
        }

        return ResponseEntity.ok().build();
    }

    private void pauseMusic() {
        try {
            playing = false;
            trackPos = trackLength - fis.available();
            musicPlayer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopMusic() {
        playing = false;
        musicPlayer.close();
        trackPos = 0;
        musicFile = null;
    }
}
