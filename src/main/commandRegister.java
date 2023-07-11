package main;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class commandRegister extends ListenerAdapter {

        @Override
        public void onGuildReady(@Nonnull GuildReadyEvent event) {
                List<CommandData> commandData = new ArrayList<>();
                commandData.add(Commands.slash("leave", "Make the bot leave from voice"));
                commandData.add(Commands.slash("join", "Make the bot join"));
                event.getGuild().updateCommands().addCommands(commandData).queue();
        }

        @Override
        public void onGuildJoin(@Nonnull GuildJoinEvent event) {
                List<CommandData> commandData = new ArrayList<>();
                commandData.add(Commands.slash("leave", "Make the bot leave from voice"));
                commandData.add(Commands.slash("join", "Make the bot join"));
                event.getGuild().updateCommands().addCommands(commandData).queue();
        }
}
