package com.plusls.llsmanager.handler;

import com.plusls.llsmanager.LlsManager;
import com.plusls.llsmanager.data.Config;
import com.plusls.llsmanager.data.LlsPlayer;
import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;

public class PlayerChooseInitialServerEventHandler implements EventHandler<PlayerChooseInitialServerEvent> {
    private static LlsManager llsManager;

    public static void init(LlsManager llsManager) {
        llsManager.server.getEventManager().register(llsManager, PlayerChooseInitialServerEvent.class, new PlayerChooseInitialServerEventHandler());
        PlayerChooseInitialServerEventHandler.llsManager = llsManager;
    }

    @Override
    public void execute(PlayerChooseInitialServerEvent event) {
        LlsPlayer llsPlayer = llsManager.getLlsPlayer(event.getPlayer());
        if (llsPlayer.getOnlineMode()) {
            if (llsManager.config.getRememberLastServer()){
                llsManager.server.getServer(llsPlayer.getLastServerName()).ifPresent(event::setInitialServer);
            }
        } else {
            llsManager.server.getServer(llsManager.config.getAuthServerName()).ifPresent(event::setInitialServer);
        }

    }
}
