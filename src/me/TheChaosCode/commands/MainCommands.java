package me.TheChaosCode.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Files.Groups;
import Files.Locations;
import me.TheChaosCode.Utils.MessUtil;

public class MainCommands implements CommandExecutor{
	
    public static List <String> members = new ArrayList <String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("kl")){
			if(args.length < 1){
				p.sendMessage(MessUtil.Prefix + "Type: /kl help - for help!");
				return true;
			}
			
			if(args.length == 1){
				if(args[0].contains("help")){
					SendHelp(p);
					return true;
				}
				
				if(args.length == 1){
					if(args.length < 1){
						p.sendMessage(MessUtil.Prefix + "Use: /kl home");
						return true;
					}
					
				
					if(args[0].contains("home")){
						if(PlayerNotinGroup(p)){
							p.sendMessage(MessUtil.Prefix + "You are not in a Group!");
							return true;
						}else{
						TeleportToHome(p);
						return true;
					}
				}
			}
			}
			
			if(args[0].contains("join")){
				if(args.length < 1){
					p.sendMessage(MessUtil.Prefix + "Use: /kl join <group> - to join a group!");
					return true;
				}
				if(isPlayerInGroup(p) == true){
					p.sendMessage(MessUtil.Prefix + "You already in a group!");
					return true;
				}else{ 
					AddToGroup(args[1], p);
				}
			}
			
			if(args.length == 2){
				if(args[0].contains("create")){
					if(!p.hasPermission("kl.create")){
						p.sendMessage(MessUtil.noperm);
						return true;
					}else
						
						if(args.length < 2){
							p.sendMessage(MessUtil.Prefix + "Use: /kl create <groupname>");
						}else
					if(Groups.getConfig().contains(args[1] + ".members")){
						p.sendMessage(MessUtil.Prefix + "Group Already Created!");
						return true;
					}else
					createGroup(args[1]);
					p.sendMessage(MessUtil.Prefix + "Group Sucsesfully added!");
					return true;
				}
			}
			
			if(args[0].contains("sethome")){
				if(!p.hasPermission("kl.sethome")){
					p.sendMessage(MessUtil.noperm);
					return true;
				}else
				if(args.length < 2){
					p.sendMessage(MessUtil.Prefix + "Use: /kl sethome <group> - to set the home of a group!");
				    return true;
				}
					if(PlayerNotinGroup(p)){
						p.sendMessage(MessUtil.Prefix + "You are not in a Group!");
						return true;
					}else{

					setGroupLocation(args[1], p);
					p.sendMessage(MessUtil.Prefix + "Set groups home to this location!");
				    return true;

				}
			}
			
			if(args[0].contains("setrank")){
				Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
				if(!p.hasPermission("kl.setrank")){
					p.sendMessage(MessUtil.noperm);
					return true;
				}else{
					if(args.length < 2){
						p.sendMessage(MessUtil.Prefix + "Use: /kl setrank <player> <king/hertog> - to set a Rank!");
					}
					
					if(PlayerNotinGroup(targetPlayer)){
						p.sendMessage(MessUtil.Prefix + targetPlayer.getName() + " Is not in a group!");
						return true;
					}else{
					if(targetPlayer != null) {
						 String group = getPlayerGroup(targetPlayer.getName()).toString();
						 if(args[2].contains("king")){
							 Groups.getConfig().set(group + ".king", targetPlayer.getName());
							 Groups.save();
							    p.sendMessage(MessUtil.Prefix + "Player Rank setted to: " + args[2]);
							    return true;
							    
							    }
						 
						 if(args[2].contains("hertog")){
							 Groups.getConfig().set(group + ".hertog", targetPlayer.getName());
							 Groups.save();
							    p.sendMessage(MessUtil.Prefix + "Player Rank setted to: " + args[2]);
							    return true;
						 
						 }else
								sender.sendMessage(MessUtil.Prefix + targetPlayer + " is not online");		
						 return true;
									
				}
					}
				}
			}
			
			if(args[0].contains("delrank")){
				Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
				if(!p.hasPermission("kl.delrank")){
					p.sendMessage(MessUtil.noperm);
					return true;
				}else{
					if(args.length < 2){
						p.sendMessage(MessUtil.Prefix + "Use: /kl delrank <player> <king/hertog> - to remove a Rank!");
					}else{

					
					if(PlayerNotinGroup(targetPlayer)){
						p.sendMessage(MessUtil.Prefix + "Player Not in a Group!");
						return true;
					}else{
				}
					
					if(targetPlayer != null) {
						 if(args[2].contains("king")){
							 RemoveRank(targetPlayer, ranks.hertog);
							    p.sendMessage(MessUtil.Prefix + "Player Rank setted to: " + args[2]);
							    targetPlayer.sendMessage(ChatColor.GREEN  + "You have been removed from the: King Rank!");
							    return true;
							    
							    }
						 
						 if(args[2].contains("hertog")){
							 RemoveRank(targetPlayer, ranks.king);
							    p.sendMessage(MessUtil.Prefix + "Player Rank setted to: " + args[2]);
							    targetPlayer.sendMessage(ChatColor.GREEN  + "You have been removed from the: Hertog Rank!");
							    return true;
						 
						 }else
								sender.sendMessage(MessUtil.Prefix + targetPlayer + " is not online");					   
									
					
					}
					}
				}
				
			}
			
			if(args[0].contains("remove")){
				Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
			
				if(!p.hasPermission("kl.remove")){
					p.sendMessage(MessUtil.noperm);
					return true;
				}else
				if(args.length < 2){
					p.sendMessage(MessUtil.Prefix + "Use: /kl remove <player> <group>- to remove a player from his group");
				}else{
					
					if(PlayerNotinGroup(targetPlayer)){
						p.sendMessage(MessUtil.Prefix + targetPlayer.getName() + " Is not in a group!");
						return true;
					}else{
					
					if(targetPlayer != null) {
						RemovePlayer(targetPlayer ,args[2]);    
					    p.sendMessage(MessUtil.Prefix + "Player remvoed from his group!");					}
					else {
					sender.sendMessage(MessUtil.Prefix + targetPlayer + " is not online");
					return true;
					}
				}
			}
			}
			if(args.length == 3){
				if(args[0].contains("add")){
					Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
					if(!p.hasPermission("kl.add")){
						p.sendMessage(MessUtil.noperm);
						return true;

					}else{
						if(args.length < 3){
							p.sendMessage(MessUtil.Prefix + "Use: /kl add <player> <group>- to add a player to a land");
						    return true;
						}else{
						if(isPlayerInGroup(targetPlayer)){
							p.sendMessage(MessUtil.Prefix + "Player Already in a Group!");
							return true;
						}else{
					if(targetPlayer != null) {
						RemovePlayer(targetPlayer,args[2]);
					    AddToGroup(args[2], targetPlayer);    
					    p.sendMessage(MessUtil.Prefix + "Player Added To the Group!");
					    targetPlayer.sendMessage(ChatColor.GREEN + "You have Been added to a new Group!");
					    return true;
					}
					else {
					sender.sendMessage(targetPlayer + " is not online");
					return true;
					}
						}
						}
					}
				}
			}

		}
		return false;
		
		
		}


			
		
			
			
	
	
	public void SendHelp(Player p){
		p.sendMessage("               §c=+User-Commands+=                 ");
		p.sendMessage("                                                   ");
		p.sendMessage("§7Command: " + "§c/kl help - " + "§6For Help!");
		p.sendMessage( "§7Command: " + "§c/kl join <kingdom> - " + "§6To join a land");
		p.sendMessage("§7Command: " + "§c/kl home -" + "§6Teleport to home!");
		p.sendMessage( "§7Command: " + "§c/kl chat <global/land> #COMINGSOON#-" + "§6Switch from chat Channel");
		p.sendMessage("                                                   ");
		p.sendMessage("               §c=+Admin-Commands+=                 ");
		if(p.hasPermission("kl.admin")){
			p.sendMessage("§7Command: " + "§c/kl remove <LandName>" + "§6 - Remove a Player from a land!");
			p.sendMessage("§7Command: " + "§c/kl add <playername> <group>- " + "§6to add A person!");
			p.sendMessage("§7Command: " + "§c/kl create <KingdomName> - " + "§6to add A Rank!");
			p.sendMessage("§7Command: " + "§c/kl setrank <player>  <King/Hertog/Soldaat> - " + "§6to add A Rank!");
			p.sendMessage("§7Command: " + "§c/kl delrank <player> <king/hertog> - " + "§6Remove a rank!");
			p.sendMessage("§7Command: " + "§c/kl sethome <Kingdom> - " + "§6set a home of a Kingdom");
			


		}
		
		
	}
	
    public static void createGroup(String name) {   
    	Groups.getConfig().set(name + ".king","");
    	Groups.getConfig().set(name + ".hertog","");
    	Groups.getConfig().set(name + ".name", name);
    	Groups.getConfig().set(name.toLowerCase() + ".members", "");
    	Groups.getConfig().set(name.toLowerCase() + ".color", "");
    	Groups.getConfig().set(name.toLowerCase() + ".prefix","" );

    	Groups.save();
    }
    
    public static void AddToGroup(String group,Player p){
    	List<String> Group1 = Groups.getConfig().getStringList(group.toLowerCase() + ".members");
    	if(!Group1.contains(p.getName())){
    		Group1.add(p.getName());
    		Groups.getConfig().set(group.toLowerCase() + ".members", Group1);
    	    Groups.save();
    	    p.sendMessage(ChatColor.GREEN + "You have successfully joined the " + group.toLowerCase() + " Land!");
    }
	
    }
    
    public static String getPlayerGroup(String player) {
    	  for(String key : Groups.getConfig().getKeys(false)) {
    		  List<String> membersInGroup = Groups.getConfig().getStringList(key + ".members");
    	 
    	    if(membersInGroup.contains(player)) {
    	      return (String) Groups.getConfig().get(key + ".name");
    	    }
    	  }
    	 
    	  return null;
    	}
    
    public void RemovePlayer(Player p,String Group){
     	    List<String> membersInGroup = Groups.getConfig().getStringList(Group + ".members");
     	    if(membersInGroup.contains(p.getName())) {
     	    	membersInGroup.remove(p.getName());
     	    	RemoveRank(p, ranks.king);
     	    	RemoveRank(p, ranks.hertog);
     	    	Groups.getConfig().set(Group + ".members", membersInGroup);;
     	    	Groups.save();
     	    }
    	 }
    
    public static boolean isPlayerInGroup(Player p) {
		 String group = getPlayerGroup(p.getName());
          List<String> membersInGroup = Groups.getConfig().getStringList(group + ".members");
       
          if(membersInGroup.contains(p.getName())) {
            return true; 
          }else
          return false;       
}
    
    public static boolean PlayerNotinGroup(Player p) {
		 String group = getPlayerGroup(p.getName());
          List<String> membersInGroup = Groups.getConfig().getStringList(group + ".members");
       
          if(!membersInGroup.contains(p.getName())) {
            return true; 
          }else
          return false;       
}
    
    public static boolean isKing(Player p) {
		 String group = getPlayerGroup(p.getName());
         if(Groups.getConfig().getString(group + ".king").contains(p.getName())) {
           return true;
         }else
       
       return false;
     }
	 
    public static boolean isHertog(Player p) {
		 String group = getPlayerGroup(p.getName());
            if(Groups.getConfig().getString(group + ".hertog").contains(p.getName())) {
              return true;
            }else
          return false;
        }
	 
	 @SuppressWarnings("resource")
	public void TeleportToHome(Player p){
		 String group = getPlayerGroup(p.getName()).toString();
		 Scanner x = new Scanner(Locations.getConfig().getString(group + ".x"));
		 Scanner y = new Scanner(Locations.getConfig().getString(group + ".y"));
		 Scanner z = new Scanner(Locations.getConfig().getString(group + ".z"));
		 
		 World world = Bukkit.getServer().getWorld(Locations.getConfig().getString(group + ".world"));
		 Location tploc = new Location(world, x.nextDouble(), y.nextDouble(), z.nextDouble());
		 p.teleport(tploc);
		 p.sendMessage(MessUtil.Prefix + "Teleported to home!");
	 }
    
    public void setGroupLocation(String group,Player p){
    	double x = p.getLocation().getX();
    	double y = p.getLocation().getY();
    	double z = p.getLocation().getZ();
    	Locations.getConfig().set(group + ".name", group);
    	Locations.getConfig().set(group + ".world", p.getWorld().getName());
    	Locations.getConfig().set(group + ".x", x);
    	Locations.getConfig().set(group + ".y", y);
    	Locations.getConfig().set(group + ".z", z);
    	Locations.save();

    	
    }
    public static void RemoveRank(Player p, ranks r){
		 String group = getPlayerGroup(p.getName()).toString();
    	if(r == ranks.king){
    		Groups.getConfig().set(group + ".king", "");
    		Groups.save();
    		return;	
    	}
    	
    	if(r == ranks.hertog){
    		Groups.getConfig().set(group + ".hertog", "");
    		Groups.save();
    		return;	
    	}
    	
    }

}
