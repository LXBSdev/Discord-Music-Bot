package main.music;

import java.util.concurrent.ConcurrentHashMap;

import main.main;

public class PlayerManager {

    public ConcurrentHashMap<Long, MusicController> controller;
    
    public PlayerManager() {
        this.controller = new ConcurrentHashMap<Long, MusicController>();
    }

    public MusicController getController(long guildId) {
        MusicController mc = null;

        if (this.controller.containsKey(guildId)) {
            mc = this.controller.get(guildId);
        } else {
            mc = new MusicController(main.INSTANCE.shardManager.getGuildById(guildId));

            this.controller.put(guildId, mc);
        }

        return mc;
    }
    
}
