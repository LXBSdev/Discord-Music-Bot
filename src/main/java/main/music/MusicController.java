package main.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import main.Main;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {

    private Guild guild;
    private AudioPlayer player;

    public MusicController(Guild guild) {
        this.guild = guild;
        this.player = Main.INSTANCE.audioPlayerManager.createPlayer();

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
        this.player.setVolume(50);
    }
    
    public AudioPlayer getPlayer() {
        return player;
    }
}
