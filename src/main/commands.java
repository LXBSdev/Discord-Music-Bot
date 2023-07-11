package main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Nonnull;

public class commands extends ListenerAdapter {

    private String getActivities(List activitiesList) {
        String activitie = "";
        if (!activitiesList.isEmpty()) {
            Activity tempActiv = (Activity) activitiesList.get(0);
            for (int i = 1; i < activitiesList.size(); i++) {
                tempActiv = (Activity) activitiesList.get(i);
                activitie = activitie + ", " + tempActiv;
            }
        } else {
            activitie = "No activitie";
        }
        return activitie;
    }

    private String getRolesAsString(List rolesList) {
        String roles = "";
        if (!rolesList.isEmpty()) {
            Role tempRole = (Role) rolesList.get(0);
            roles = tempRole.getAsMention();
            for (int i = 1; i < rolesList.size(); i++) {
                tempRole = (Role) rolesList.get(i);
                roles = roles + ", " + tempRole.getAsMention();
            }
        } else {
            roles = "No roles";
        }
        return roles;
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String command = event.getName();

        if (command.equals("leave")) {
            AudioManager manager = event.getGuild().getAudioManager();
            EmbedBuilder emb = new EmbedBuilder();
            if (manager.isConnected()) {
                if (event.getMember().getVoiceState().getChannel() == manager.getConnectedChannel()) {
                    emb.setDescription("I already realise when I'm unwanted :/");
                    manager.closeAudioConnection();
                    event.replyEmbeds(emb.build()).queue();
                }
            }
        }
        
        if (command.equals("join")) {
            AudioManager manager = event.getGuild().getAudioManager();
            AudioChannel channel = event.getMember().getVoiceState().getChannel();
            EmbedBuilder emb = new EmbedBuilder();
            if (!manager.isConnected()) {
                emb.setDescription("Hello there!");
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
