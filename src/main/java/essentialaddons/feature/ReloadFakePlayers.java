package essentialaddons.feature;

import carpet.CarpetSettings;
import carpet.fakes.ServerPlayerInterface;
import carpet.helpers.EntityPlayerActionPack;
import carpet.helpers.EntityPlayerActionPack.ActionType;
import carpet.patches.EntityPlayerMPFake;
import com.mojang.authlib.GameProfile;
import essentialaddons.EssentialSettings;
import essentialaddons.mixins.reloadFakePlayers.EntityPlayerActionPackAccessor;
import essentialaddons.mixins.reloadFakePlayers.EntityPlayerMPFakeInvoker;
import essentialaddons.utils.ConfigFakePlayerData;
import essentialaddons.utils.ducks.IFakePlayer;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.UserCache;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static carpet.helpers.EntityPlayerActionPack.Action;

public class ReloadFakePlayers {
    public static void loadFakePlayers(MinecraftServer server) {
        ConfigFakePlayerData.INSTANCE.readConfig(server);
    }

    public static void loadPlayer(MinecraftServer server, UUID uuid, String username, boolean sneaking, boolean sprinting, float forward, float strafing, Map<ActionType, Action> actionMap) {
        UserCache.setUseRemote(false);
        GameProfile gameProfile = server.getUserCache().getByUuid(uuid).orElse(CarpetSettings.allowSpawningOfflinePlayers ? new GameProfile(uuid, username) : null);
        UserCache.setUseRemote(server.isDedicated() && server.isOnlineMode());
        if (gameProfile == null) {
            return;
        }
        if (gameProfile.getProperties().containsKey("textures")) {
            //TODO: FIX THIS! DO NOT PR WITH THIS LIKE THIS!
            //#if MC < 12002
            AtomicReference<GameProfile> result = new AtomicReference<>();
            SkullBlockEntity.loadProperties(gameProfile, result::set);
            gameProfile = result.get();
            //#endif
        }
        //#if MC >= 11900 && MC < 11903
        //$$EntityPlayerMPFake instance = EntityPlayerMPFakeInvoker.init(server, server.getOverworld(), gameProfile, false, null);
        //#else
        EntityPlayerMPFake instance = EntityPlayerMPFakeInvoker.init(server, server.getOverworld(), gameProfile, false);
        //#endif
        if (EssentialSettings.reloadFakePlayerActions) {
            EntityPlayerActionPack actionPack = ((ServerPlayerInterface) instance).getActionPack();
            actionPack.setSneaking(sneaking).setSprinting(sprinting).setForward(forward).setStrafing(strafing);
            ((EntityPlayerActionPackAccessor) actionPack).getActions().putAll(actionMap);
        }
        ((IFakePlayer) instance).join(server);
    }
}
