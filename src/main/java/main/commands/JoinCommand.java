package main.commands;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equals("join")) {
            AudioManager manager = event.getGuild().getAudioManager();
            AudioChannel channel = event.getMember().getVoiceState().getChannel();
            EmbedBuilder emb = new EmbedBuilder();
            if (!manager.isConnected()) {
                emb.setDescription("Let's have some fun");
                manager.openAudioConnection(channel);
                manager.setSelfDeafened(true);
                event.replyEmbeds(emb.build()).queue();
            } else {
                emb.setDescription("Sorry, I'm already rickrolling someone else");
                event.replyEmbeds(emb.build()).queue();
            }
        }
    }
}
