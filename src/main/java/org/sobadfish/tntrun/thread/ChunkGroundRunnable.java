package org.sobadfish.tntrun.thread;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.scheduler.Task;
import org.sobadfish.tntrun.TntRunMain;
import org.sobadfish.tntrun.manager.TotalManager;
import org.sobadfish.tntrun.player.PlayerInfo;
import org.sobadfish.tntrun.room.GameRoom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sobadfish
 * 18:24
 */
public class ChunkGroundRunnable extends PluginTask<TntRunMain> {
    public ChunkGroundRunnable(TntRunMain tntRunMain) {
        super(tntRunMain);
    }
    public static ArrayList<Vector3> positions = new ArrayList<>();

    @Override
    public void onRun(int i) {
        for(GameRoom room: TotalManager.getRoomManager().getRooms().values()){
            if(room.getType() == GameRoom.GameType.START) {
                for (PlayerInfo info : room.getLivePlayers()) {
                    if (info.isStart) {
                        //TODO 清除方块
                        if (info.getPlayer().isOnGround()) {
                            int y = info.getPlayer().getFloorY() - 1;

                            Server.getInstance().getScheduler().scheduleDelayedTask(TotalManager.getPlugin()
                                    , new BlockSetRunnable((TntRunMain) TotalManager.getPlugin(),
                                    info.player.getLevel(),getAllBlocks(info.player,y)),10);

                        }

                    }
                }
            }
        }

    }

    private List<Block> getAllBlocks(Entity player, int y) {
        ArrayList<Block> collisionBlocks = new ArrayList<>();
        List var1 = this.getCollisionBlocks(player, y);
        for (Object o : var1) {
            Block var3 = (Block) o;
            if (var3.collidesWithBB(player.boundingBox, true)) {
                collisionBlocks.add(var3);
            }

        }

        return collisionBlocks;
    }


    private List<Block> getCollisionBlocks(Entity player, int y){
        List<Block> collisionBlocks = new ArrayList<>();
        int var1 = NukkitMath.floorDouble(player.boundingBox.minX);
        int var3 = NukkitMath.floorDouble(player.boundingBox.minZ);
        int var4 = NukkitMath.ceilDouble(player.boundingBox.maxX);
        int var6 = NukkitMath.ceilDouble(player.boundingBox.maxZ);
        for(int var7 = var3; var7 <= var6; ++var7) {
            for(int var8 = var1; var8 <= var4; ++var8) {
                Block var10 = player.level.getBlock(player.chunk, var8, y, var7, false);
                collisionBlocks.add(var10);

            }
        }
        return collisionBlocks;

    }

    private class BlockSetRunnable extends PluginTask<TntRunMain>{

        private List<Block> v3;
        private Level level;
        BlockSetRunnable(TntRunMain plugin, Level level, List<Block> v3){
            super(plugin);
            this.v3 = v3;
            this.level = level;
        }



        @Override
        public void onRun(int i) {
            for(Vector3 v3: v3){
                level.setBlock(v3,new BlockAir());
                level.setBlock(v3.add(0,-1),new BlockAir());
            }
        }
    }
}
