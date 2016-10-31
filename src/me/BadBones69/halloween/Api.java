package me.BadBones69.halloween;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Api {
	public static String color(String msg){
		msg = ChatColor.translateAlternateColorCodes('&', msg);
		return msg;
	}
	public static String removeColor(String msg){
		msg = ChatColor.stripColor(msg);
		return msg;
	}
	public static ItemStack makeItem(String type, int amount, String name){
		int ty = 0;
		if(type.contains(":")){
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		ItemMeta me = item.getItemMeta();
		me.setDisplayName(color(name));
		item.setItemMeta(me);
		return item;
	}
	public static ItemStack makeItem(String type, int amount, String name, List<String> lore){
		ArrayList<String> l = new ArrayList<String>();
		int ty = 0;
		if(type.contains(":")){
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		ItemMeta me = item.getItemMeta();
		me.setDisplayName(color(name));
		for(String L:lore)l.add(color(L));
		me.setLore(l);
		item.setItemMeta(me);
		return item;
	}
	public static ItemStack makeItem(Material material, int amount, int type, String name){
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(color(name));
		item.setItemMeta(m);
		return item;
	}
	public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore){
		ArrayList<String> l = new ArrayList<String>();
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(color(name));
		for(String L:lore)l.add(color(L));
		m.setLore(l);
		item.setItemMeta(m);
		return item;
	}
	public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore, Map<Enchantment, Integer> enchants){
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		item.setItemMeta(m);
		item.addUnsafeEnchantments(enchants);
		return item;
	}
	public static ItemStack addLore(ItemStack item, String i){
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta m = item.getItemMeta();
		if(item.getItemMeta().hasLore()){
			lore.addAll(item.getItemMeta().getLore());
		}
		lore.add(i);
		m.setLore(lore);
		item.setItemMeta(m);
		return item;
	}
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	public static Player getPlayer(String name){
		return Bukkit.getServer().getPlayer(name);
	}
	public static Location getLoc(Player player){
		return player.getLocation();
	}
	public static void runCMD(Player player, String CMD){
		player.performCommand(CMD);
	}
	public static boolean isOnline(String name, CommandSender p){
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			if(player.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		p.sendMessage(color("&cThat player is not online at this time."));
		return false;
	}
	public static boolean hasPermission(Player player, String perm){
		if(!player.hasPermission("Perm." + perm)){
			player.sendMessage(color("&cYou need permission to use this command."));
			return false;
		}
		return true;
	}
	public static boolean hasPermission(CommandSender sender, String perm){
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(!player.hasPermission("Perm." + perm)){
				player.sendMessage(color("&cYou need permission to use this command."));
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	public static boolean randomPicker(int min, int max){
		if(max==min)return true;
		Random number = new Random();
		int chance = 1 + number.nextInt(max);
		if(chance>=1&&chance<=min){
			return true;
		}
		return false;
	}
	public static Boolean hasNearByBlock(Material type, int radius, Location loc){
		for(int i = -radius; i <= radius; i++) {
		    for(int j = -radius; j <= radius; j++) {
		        for(int k = -radius; k <= radius; k++) {
		            if(loc.getBlock().getRelative(i, j, k).getType() == type)
		                return true;
		        }
		    }
		}
		return false;
	}
	public static ArrayList<Location> getNearByBlock(Material type, int radius, Location loc){
		ArrayList<Location> locs = new ArrayList<Location>();
		for(int i = -radius; i <= radius; i++) {
		    for(int j = -radius; j <= radius; j++) {
		        for(int k = -radius; k <= radius; k++) {
		            if(loc.getBlock().getRelative(i, j, k).getType() == type)
		                locs.add(loc.getBlock().getRelative(i, j, k).getLocation());
		        }
		    }
		}
		return locs;
	}
}