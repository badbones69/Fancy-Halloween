package me.BadBones69.halloween;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.BadBones69.halloween.controls.HalloweenControl;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(new HalloweenControl(), this);
		HalloweenControl.startParticles();
		HalloweenControl.startLightning();
		HalloweenControl.startPumpkinMask();
	//	HalloweenControl.startFlyingPumpkins();
		HalloweenControl.startPumpkinLighting();
	}
	
	@Override
	public void onDisable(){
		HalloweenControl.onStop();
	}
	
}