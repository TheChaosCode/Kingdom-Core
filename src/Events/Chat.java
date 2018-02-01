package Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.TheChaosCode.commands.MainCommands;

public class Chat implements Listener{

	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		
		if(MainCommands.PlayerNotinGroup(e.getPlayer()) == true){
			e.setFormat("§7[§b§lZwerver§7] " + e.getPlayer().getName() + ": " + e.getMessage());
			return;
		}
		if(MainCommands.isKing(e.getPlayer()) == true){
			e.setFormat("§7[§c" + MainCommands.getPlayerGroup(e.getPlayer().getName()) + "§7] " + "§7[§6§lKing§7] " + e.getPlayer().getName() + ": " + e.getMessage());
			return;
		}
		
		if(MainCommands.isHertog(e.getPlayer()) == true){
			e.setFormat("§7[§c" + MainCommands.getPlayerGroup(e.getPlayer().getName()) + "§7] " + "§7[§5§lHertog§7] " + e.getPlayer().getName() + ": " + e.getMessage());
			return;
		

		}
		if(MainCommands.isPlayerInGroup(e.getPlayer()) == true){
			e.setFormat("§7[§c" + MainCommands.getPlayerGroup(e.getPlayer().getName()) + "§7] " + "§7[§a§lSoldaat§7] " + e.getPlayer().getName() + ": " + e.getMessage());
		return;
		}

		}
		
		
		
	}
