package me.BadBones69.halloween.controls;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BadBones69.halloween.Api;
import me.BadBones69.halloween.ParticleEffect;

public class HalloweenControl implements Listener{
	
	private static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Fancy-Halloween");
	
	private static ArrayList<Entity> bats = new ArrayList<Entity>();
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Entity> skeletons = new ArrayList<Entity>();
	private static ArrayList<Location> pumpkins = new ArrayList<Location>();
	
	public static void startPumpkinMask(){
		Random time = new Random();
		int timenumber = 15 + time.nextInt(21);
		for(final Player player : Bukkit.getOnlinePlayers()){
			if(player.getEquipment().getHelmet() == null){
				ItemStack pumpkin = new ItemStack(Material.PUMPKIN);
				ItemMeta pm = pumpkin.getItemMeta();
				pm.setDisplayName(Api.color("&6&lPumpkin Mask"));
				pumpkin.setItemMeta(pm);
				player.getEquipment().setHelmet(pumpkin);
				player.updateInventory();
				players.add(player);
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Player player : players){
					player.getEquipment().setHelmet(null);
				}
				players.clear();
			}
			
		}, 5);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
			@Override
			public void run() {
				startPumpkinMask();
			}
			
		}, timenumber*20);
	}
	
	public static void startFlyingPumpkins(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					for(Entity bat : bats){
						bat.remove();
					}
					for(Entity skel : skeletons){
						skel.remove();
					}
					bats.clear();
					skeletons.clear();
					Random r = new Random();
					for(int i = 0 ; i < 3 ; i++){
						Bat bat = player.getWorld().spawn(player.getLocation().add(-5 + r.nextInt(10), r.nextInt(5), -5 + r.nextInt(10)), Bat.class);
						final Skeleton skel = player.getWorld().spawn(player.getLocation(), Skeleton.class);
						bats.add(bat);
						skeletons.add(skel);
						bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 55555*20, 1));
						bat.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 55555*20, 2));
						skel.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 55555*20, 1));
						skel.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
						skel.setCustomName(Api.color("&6&lHappy Halloween"));
						skel.setCustomNameVisible(true);
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
							@Override
							public void run() {
								skel.getEquipment().setItemInHand(new ItemStack(Material.AIR));
							}
						}, 2);
						bat.setPassenger(skel);
					}
				}
			}
		}, 10, 60*20);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
    		public void run(){
				for(Entity skel : skeletons){
					ParticleEffect.FLAME.display((float).2, (float).2, (float).2, 0, 10, skel.getLocation().add(0, 1.7, 0), 100);
				}
    		}
    	}, 0, 3);
	}
	
	public static void startParticles(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					ParticleEffect.FLAME.display(10, 10, 10, 0, 40, player.getLocation().add(0, 1, 0), 100);
					ParticleEffect.SMOKE_LARGE.display(10, 10, 10, 0, 40, player.getLocation().add(0, 1, 0), 100);
				}
			}
		}, 0, 1*20);
	}
	
	public static void startLightning(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					World world = player.getWorld();
					Location loc = player.getLocation();
					Random x = new Random();
					Random z = new Random();
					int X = -100 + x.nextInt(200);
					int Z = -100 + z.nextInt(200);
					loc.add(X, 0, Z);
					world.strikeLightningEffect(loc);
					player.playSound(loc, Sound.AMBIENCE_THUNDER, 1, 0);
				}
			}
		}, 0, 5*20);
	}
	
	@SuppressWarnings("deprecation")
	public static void startPumpkinLighting(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					if(Api.hasNearByBlock(Material.PUMPKIN, 6, player.getLocation())){
						for(Location loc : Api.getNearByBlock(Material.PUMPKIN, 6, player.getLocation())){
							if(Api.randomPicker(1, 7)){
								if(loc.getBlock() != null){
									if(loc.getBlock().getType() == Material.PUMPKIN){
										Byte d = loc.getBlock().getData();
										loc.getBlock().setType(Material.JACK_O_LANTERN);
										loc.getBlock().setData(d);
										pumpkins.add(loc);
									}
								}
							}
						}
					}
				}
			}
		}, 0, 2*20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			@Override
			public void run() {
				for(Location loc : pumpkins){
					if(loc.getBlock() != null){
						if(loc.getBlock().getType() == Material.JACK_O_LANTERN){
							Byte d = loc.getBlock().getData();
							loc.getBlock().setType(Material.PUMPKIN);
							loc.getBlock().setData(d);
						}
					}
				}
				pumpkins.clear();
			}
		}, 0, 2*20);
	}
	
	public static void kill(){
		for(Entity bat : bats){
			bat.remove();
		}
		for(Entity skel : skeletons){
			skel.remove();
		}
		bats.clear();
		skeletons.clear();
	}
	
	@SuppressWarnings("deprecation")
	public static void onStop(){
		kill();
		for(Player player : players){
			player.getEquipment().setHelmet(null);
		}
		for(Location loc : pumpkins){
			if(loc.getBlock() != null){
				if(loc.getBlock().getType() == Material.JACK_O_LANTERN){
					Byte d = loc.getBlock().getData();
					loc.getBlock().setType(Material.PUMPKIN);
					loc.getBlock().setData(d);
				}
			}
		}
		players.clear();
		pumpkins.clear();
	}
	
/*	@EventHandler
	public void onDamage(EntityDamageEvent e){
		Entity en = e.getEntity();
		if(bats.contains(en) || skeletons.contains(en)){
			e.setCancelled(true);
		}
	}*/
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		if(players.contains(player)){
			e.setCancelled(true);
		}
	}
	
}