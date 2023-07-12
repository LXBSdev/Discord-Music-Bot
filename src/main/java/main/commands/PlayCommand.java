package main.commands;

import javax.annotation.Nonnull;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import main.Main;
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
        if (event.getName().equals("join")) {
            Member member = event.getMember();
            AudioManager manager = event.getGuild().getAudioManager();
            AudioChannel channel = event.getMember().getVoiceState().getChannel();
            EmbedBuilder emb = new EmbedBuilder();
            MusicController controller = Main.INSTANCE.playerManager.getController(member.getGuild().getIdLong());
            if (member.getVoiceState().getChannel() != null) {
                if (!manager.isConnected() | manager.getConnectedChannel() == channel) {
                    AudioPlayerManager apm = Main.INSTANCE.audioPlayerManager;
                    if (!manager.isConnected()) {
                        emb.setDescription("Let's have some fun");
                        manager.openAudioConnection(channel);
                        manager.setSelfDeafened(true);
                        event.replyEmbeds(emb.build()).queue();
                    }
                    if (event.getOption("url") != null) {
                        String url = event.getOption("url").toString();
                        emb.setDescription("Let's have some fun");
                        event.replyEmbeds(emb.build()).queue();
                        if (!url.startsWith("http")) {
                            url = "ytsearch" + url;
                        }
                        apm.loadItem(url, new AudioLoadResult(controller, url));
                    } else {

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
