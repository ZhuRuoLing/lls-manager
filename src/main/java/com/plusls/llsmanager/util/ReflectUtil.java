package com.plusls.llsmanager.util;

import com.plusls.llsmanager.tabListSync.MyPlayerListItem;
import com.plusls.llsmanager.tabListSync.TabListSyncHandler;
import com.velocitypowered.proxy.protocol.StateRegistry;
import com.velocitypowered.proxy.protocol.packet.LegacyPlayerListItem;

import java.lang.reflect.Field;

public class ReflectUtil {
    public static Field getField(Class<?> clazz, String target) {
        try {
            Field field = clazz.getDeclaredField(target);
            field.setAccessible(true);
            return field;

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static StateRegistry.PacketRegistry accessStateRegistryPlayClientbound(){
        try {
            Class<?> clazz = StateRegistry.class;
            for (Field declaredField : StateRegistry.PLAY.getClass().getDeclaredFields()) {
                System.out.println(declaredField);
            }
            Field field = clazz.getDeclaredField("clientbound");
            field.setAccessible(true);
            return (StateRegistry.PacketRegistry) field.get(StateRegistry.PLAY);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
