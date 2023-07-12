package main.commands;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals("leave")) {
            AudioManager manager = event.getGuild().getAudioManager();
            EmbedBuilder emb = new EmbedBuilder();
            if (manager.isConnected()) {
                if (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel()) {
                    emb.setDescription("_Music stopped playing_");
                    manager.closeAudioConnection();
                    event.replyEmbeds(emb.build()).queue();
                }
            }
        }
    }
}
