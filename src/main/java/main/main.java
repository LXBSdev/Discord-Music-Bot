package main;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import io.github.cdimascio.dotenv.Dotenv;
import main.commands.CommandRegister;
import main.commands.JoinCommand;
import main.commands.LeaveCommand;
import main.commands.PlayCommand;
import main.music.PlayerManager;

public class main {

    public static main INSTANCE;

    public ShardManager shardManager;
    public AudioPlayerManager audioPlayerManager;
    public PlayerManager playerManager;

    private final Dotenv config = Dotenv.configure().load();
    String token = config.get("TOKEN");
    String status = config.get("STATUS");

    public main() throws LoginException {
        INSTANCE = this;

        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.playerManager = new PlayerManager();

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing(status));
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
        builder.addEventListeners(new CommandRegister(), new JoinCommand(), new LeaveCommand(), new PlayCommand());
        shardManager = builder.build();
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            main bot = new main();
            System.out.println("SUCCESS: The LXBS Music Bot is now online");
        } catch (LoginException exception) {
            System.out.println("ERROR: Provided bot token is invalid");
        }
    }
}
