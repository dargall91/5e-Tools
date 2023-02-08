package com.server.controllers;

import com.server.entities.Music;
import com.server.repositories.MusicRepository;
import javazoom.jl.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Plays the music file with the specified musicId.
     * @param musicId
     * @return
     */
    @PostMapping("play")
    public ResponseEntity<?> playMusic(@RequestParam int musicId) {
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

                        //on complete, reset track pos to 0, otherwise it will resume from last pause point
                        if (musicPlayer.isComplete()) {
                            trackPos = 0;
                        }
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

    @PostMapping("pause")
    public ResponseEntity<?> pauseMusic() {
        if (!playing) {
            return ResponseEntity.noContent().build();
        }

        try {
            playing = false;
            trackPos = trackLength - fis.available();
            musicPlayer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occured while pausing the music");
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("stop")
    public ResponseEntity<?> stopMusic() {
        if (!playing) {
            return ResponseEntity.noContent().build();
        }

        playing = false;
        musicPlayer.close();
        trackPos = 0;
        musicFile = null;

        return ResponseEntity.noContent().build();
    }

    @GetMapping("list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(musicRepo.findByOrderByNameAsc());
    }
}
