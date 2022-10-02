package org.sobadfish.tntrun.event;


import cn.nukkit.plugin.Plugin;
import org.sobadfish.tntrun.room.GameRoom;

/**
 * 房间游戏开始事件
 * @author SoBadFish
 * 2022/1/15
 */
public class GameRoomStartEvent extends GameRoomEvent {

    public GameRoomStartEvent(GameRoom room, Plugin plugin) {
        super(room, plugin);
    }
}
