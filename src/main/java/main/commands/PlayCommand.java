package main.commands;

import javax.annotation.Nonnull;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.io.MessageInput;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import main.main;
import main.music.AudioLoadResult;
import main.music.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand extends ListenerAdapter{

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals("play")) {
            Member member = event.getMember();
            AudioManager manager = event.getGuild().getAudioManager();
            AudioChannel channel = event.getMember().getVoiceState().getChannel();
            EmbedBuilder emb = new EmbedBuilder();
            MusicController controller = main.INSTANCE.playerManager.getController(member.getGuild().getIdLong());
            if (member.getVoiceState().getChannel() != null) {
                if (!manager.isConnected() | manager.getConnectedChannel() == channel) {
                    AudioPlayerManager apm = main.INSTANCE.audioPlayerManager;
                    if (!manager.isConnected()) {
                        manager.openAudioConnection(channel);
                        manager.setSelfDeafened(true);
                    }
                    if (event.getOption("url") != null) {
                        String url = event.getOption("url").getAsString();
                        if (!url.startsWith("http")) {
                            url = "ytsearch: " + url;
                        }
                        emb.setDescription("playing your track");
                        System.out.println(url);
                        apm.loadItem(url, new AudioLoadResult(controller, url));
                        event.replyEmbeds(emb.build()).queue();
                        return;
                    } else if (controller.getPlayer().isPaused()) {
                        controller.getPlayer().playTrack(null);
                    }
                } else {
                    emb.setDescription("Sorry, I'm already rickrolling someone else");
                    event.replyEmbeds(emb.build()).queue();
                }
            } else {
                emb.setDescription("You have to be in a voice channel dummy!");
                event.replyEmbeds(emb.build()).queue();
            }
        }
    }
}
