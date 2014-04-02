package teleportEffects;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.vanish.staticaccess.VanishNoPacket;

@SuppressWarnings("deprecation")
public class TeleportEffects extends JavaPlugin implements Listener{
	
	Plugin vanish;
	boolean useVanish = false;
	
	@Override
	public void onEnable(){
		getLogger().info("TeleportEffects has been enabled");
		
		vanish = Bukkit.getPluginManager().getPlugin("VanishNoPacket");
		if(vanish != null){ useVanish = true; getLogger().info("Hooked to VanishNoPacket");}
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable(){
		getLogger().info("TeleportEffects has been disabled");
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void teleport(PlayerTeleportEvent e){
		Player player = e.getPlayer();
		if(!e.getCause().equals(TeleportCause.UNKNOWN))
			if(useVanish){
				if(!isVanished(player)){
					doEffect(player);
				}
			}else{
				doEffect(player);
			}
	}
	
	private synchronized void doEffect(final Player p){
		 this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			  public void run() {	
				  p.playEffect(p.getLocation().add(0.0, 1.3, 0.0), Effect.ENDER_SIGNAL, 1);
				  p.getWorld().playSound(p.getLocation(),Sound.ENDERMAN_TELEPORT,5, 1);
			  }
		 }, 3L);
	}
	
	private synchronized boolean isVanished(Player p){
		try{
			return VanishNoPacket.isVanished(p.getName());
		}catch (Exception e){
			return false;
		}
	}
}
