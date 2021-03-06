package micdoodle8.mods.galacticraft.planets.asteroids.entities.player;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.planets.asteroids.dimension.WorldProviderAsteroids;
import micdoodle8.mods.galacticraft.planets.asteroids.entities.EntitySmallAsteroid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class AsteroidsPlayerHandler
{
    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            this.onPlayerLogin((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerLoggedOutEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            this.onPlayerLogout((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            this.onPlayerRespawn((EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event)
    {
        if (event.entity instanceof EntityPlayerMP && GCPlayerStats.get((EntityPlayerMP) event.entity) == null)
        {
            GCPlayerStats.register((EntityPlayerMP) event.entity);
        }
    }

    private void onPlayerLogin(EntityPlayerMP player)
    {
    }

    private void onPlayerLogout(EntityPlayerMP player)
    {

    }

    private void onPlayerRespawn(EntityPlayerMP player)
    {
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        if (event.entityLiving instanceof EntityPlayerMP)
        {
            this.onPlayerUpdate((EntityPlayerMP) event.entityLiving);
        }
    }

    private void onPlayerUpdate(EntityPlayerMP player)
    {
        if (!player.worldObj.isRemote && player.worldObj.provider instanceof WorldProviderAsteroids)
        {
            final int f = 50;

            if (player.worldObj.rand.nextInt(f) == 0)
            {
                final EntityPlayer closestPlayer = player.worldObj.getClosestPlayerToEntity(player, 100);

                if (closestPlayer == null || closestPlayer.getEntityId() <= player.getEntityId())
                {
                    double x, y, z;
                    double motX, motY, motZ;
                    double r = Math.PI * 2.0 * player.worldObj.rand.nextDouble();
                    x = player.posX + Math.cos(r) * 100;
                    y = player.posY + player.worldObj.rand.nextInt(5);
                    z = player.posZ + Math.sin(r) * 100;
                    motX = (player.posX + (player.worldObj.rand.nextDouble() - 0.5) * 50 - x) / 800.0F;
                    motY = (player.worldObj.rand.nextDouble() - 0.5) * 0.4;
                    motZ = (player.posZ + (player.worldObj.rand.nextDouble() - 0.5) * 50 - z) / 800.0F;

                    final EntitySmallAsteroid smallAsteroid = new EntitySmallAsteroid(player.worldObj);
                    smallAsteroid.setPosition(x, y, z);
                    smallAsteroid.motionX = motX;
                    smallAsteroid.motionY = motY;
                    smallAsteroid.motionZ = motZ;
                    smallAsteroid.spinYaw = player.worldObj.rand.nextFloat() * 4;
                    smallAsteroid.spinPitch = player.worldObj.rand.nextFloat() * 1;

                    if (!player.worldObj.isRemote)
                    {
                        player.worldObj.spawnEntityInWorld(smallAsteroid);
                    }
                }
            }
        }
    }
}
