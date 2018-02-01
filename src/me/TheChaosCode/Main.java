package me.TheChaosCode;

import org.bukkit.plugin.java.JavaPlugin;

import Events.Chat;
import me.TheChaosCode.commands.MainCommands;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable(){  
		RegisterCommands();
		registerListeners();
	}
	
	@Override 
	public void onDisable(){
		
	}
	
	
	public void RegisterCommands(){
		this.getCommand("kl").setExecutor(new MainCommands());
	}
	
	public void registerListeners(){
		getServer().getPluginManager().registerEvents(new Chat(), this);
	}
	

}
