package com.OyunTarayici.PlayerLevels.Commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.BukkitApi.main.BukkitUtiles.CommandUtils.CommandCreator;
import org.BukkitApi.main.BukkitUtiles.MesageUtils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.OyunTarayici.PlayerLevels.AuthorsInfo;
import com.OyunTarayici.PlayerLevels.Manager.LevelManager;
import com.OyunTarayici.PlayerLevels.Profiles.LevelsControl;
import lombok.Getter;

public class CommandPlayerLevels extends CommandCreator {

	@Getter
	private LevelsControl levelsControl=null;
	@Getter
	private OfflinePlayer offlinePlayer=null;
	
	public CommandPlayerLevels(JavaPlugin plugin, String commandName) {
		super(plugin, "playerlevels");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void executeCommand(Player player, String arg1, String[] arg) {
		command=(Command)getPlugin().getCommand(arg1);
		if (player.hasPermission("levels.progress.player")) {
			if (command.getName().equals("playerlevels")) {
				
				if (arg.length==0) {
					levelsControl=LevelsControl.getLevelMap().get(player.getUniqueId());
					PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aYour progress levels: &9"+LevelManager.getProgressLevels(levelsControl)));
					PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aYour progress xp: &9"+LevelManager.getProgressXp(levelsControl)));
				return;}
				
				if (arg[0].equalsIgnoreCase("help")) {
					PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels &aLook your levels"));
					PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels look <username> &aLook player levels"));
					PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels info &aPlayerLevels info Plugins"));
					if (player.hasPermission("levels.progress.player")) {
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels levelup <username> <amount> &aLevelup command"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels reload &aPlayerLevels reload command!"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels addlevel <username> <amount> &aPlayer add level"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels setlevel <username> <amount> &aPlayer set level"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels removelevel <username> <amount> &aPlayer remove level"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels addxp <username> <amount> &aPlayer add xp"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels setxp <username> <amount> &aPlayer set xp"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels removexp <username> <amount> &aPlayer remove xp"));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels reset <username> &aPlayer progress levels reset"));
					}
				return;}
				
				if (arg[0].equalsIgnoreCase("info")) {
					AuthorsInfo.byAuthorsInfo(player);
				return;}
				
				if (arg[0].equalsIgnoreCase("look")) {
					offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
					if (!offlinePlayer.isOnline()) {
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
					return;}else if (offlinePlayer==null) {
					return;}else if (offlinePlayer==player) {
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cSorry you don't ritten your name!"));
					return;}else {
						levelsControl=LevelsControl.getLevelMap().get(offlinePlayer.getUniqueId());
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e"+offlinePlayer.getName()+" &aprogress levels &c"+levelsControl.getLevel()));
						PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e"+offlinePlayer.getName()+" &aprogress xp &c"+levelsControl.getXp()));
				return;}}

				if (player.hasPermission("levels.progress.admin")) {
					if (arg[0].equalsIgnoreCase("levelup")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								return;}else {
									File levelsFile=new File(getPlugin().getDataFolder(),"levels.yml");
									FileConfiguration c=YamlConfiguration.loadConfiguration(levelsFile); 
									int levels=Integer.parseInt(arg[2]);
									
									if (c.isSet("levels"+levels+".level")||!c.isSet("levels."+levels+".xp")) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cSorry not found levels!"));
										return;}else {
											levelsControl=LevelsControl.getLevelMap().get(offlinePlayer.getUniqueId());
											if (levelsControl.getXp()==c.getInt("levels."+levels+".xp")) {
												return;}else {
													LevelManager.setXp(levelsControl, c.getInt("levels."+levels+".xp"));
													LevelManager.setLevel(levelsControl, c.getInt("levels."+levels+".level"));
													LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
													LevelManager.createLevelAccount(offlinePlayer.getPlayer());
													PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level up complet &e"+c.getInt("levels."+levels+".level")));
					return;}}}}

					if (arg[0].equalsIgnoreCase("reload")) {
						if (arg.length==1) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels reload levels &aPlayerLevels reload levels"));
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e/playerlevels reload players &aPlayerLevels reload players"));
						return;}
						
						if (arg[1].equalsIgnoreCase("levels")) {
							File file=new File(getPlugin().getDataFolder(),"levels.yml");
							FileConfiguration c=YamlConfiguration.loadConfiguration(file);
							try {
								c.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull reloading &e&nlevels.yml"));
						return;}
						
						if (arg[1].equalsIgnoreCase("players")) {
							File file=new File(getPlugin().getDataFolder(),"players.yml");
							FileConfiguration c=YamlConfiguration.loadConfiguration(file);
							try {
								c.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull reloading &e&nplayers.yml"));
						return;}

						if (arg[1].equalsIgnoreCase("config")) {
							File file=new File(getPlugin().getDataFolder(),"config.yml");
							FileConfiguration c=YamlConfiguration.loadConfiguration(file);
							try {
								c.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull reloading &e&nconfig.yml"));
							return;}
					return;}
					
					if (arg[0].equalsIgnoreCase("addlevel")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
						return;}else if (offlinePlayer==null) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
						return;}else {
							if (levels<0||levels==0) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
							return;}else {
								LevelManager.addLevel(levelsControl, levels);
								LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
								LevelManager.createLevelAccount(offlinePlayer.getPlayer());
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level add &e"+offlinePlayer.getName()+" &aLevels &e"+levels));
					return;}}}
					
					if (arg[0].equalsIgnoreCase("setlevel")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
								return;}else {
									if (levels<0) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
										return;}else {
											LevelManager.setLevel(levelsControl, levels);
											LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
											LevelManager.createLevelAccount(offlinePlayer.getPlayer());
											PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level arranged &e"+offlinePlayer.getName()+" &aLevels &e"+levels));
											return;}}}
					

					if (arg[0].equalsIgnoreCase("removelevel")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
								return;}else {
									if (levels<0) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
										return;}else {
											LevelManager.setLevel(levelsControl, levels);
											LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
											LevelManager.createLevelAccount(offlinePlayer.getPlayer());
											PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level remove &e"+offlinePlayer.getName()+" &aLevels &e"+levels));
											return;}}}

					if (arg[0].equalsIgnoreCase("addxp")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
								return;}else {
									if (levels<0) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
										return;}else {
											LevelManager.addXp(levelsControl, levels);
											LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
											LevelManager.createLevelAccount(offlinePlayer.getPlayer());
											PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level add &e"+offlinePlayer.getName()+" &aXp &e"+levels));
											return;}}}

					if (arg[0].equalsIgnoreCase("setxp")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
								return;}else {
									if (levels<0) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
										return;}else {
											LevelManager.setXp(levelsControl, levels);
											LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
											LevelManager.createLevelAccount(offlinePlayer.getPlayer());
											PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level set &e"+offlinePlayer.getName()+" &aXp &e"+levels));
											return;}}}
					

					if (arg[0].equalsIgnoreCase("removexp")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
								return;}else {
									if (levels<0) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
										return;}else {
											LevelManager.setXp(levelsControl, levels);
											LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
											LevelManager.createLevelAccount(offlinePlayer.getPlayer());
											PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull level set &e"+offlinePlayer.getName()+" &aXp &e"+levels));
											return;}}}
					
					if (arg[0].equalsIgnoreCase("reset")) {
						offlinePlayer=Bukkit.getOfflinePlayer(arg[1]);
						int levels=Integer.parseInt(arg[2]);
						
						if (!offlinePlayer.isOnline()) {
							PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
							return;}else if (offlinePlayer==null) {
								PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cPlayer not online!"));
								return;}else {
									if (levels<0) {
										PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cInvalid argument don't ritten with &e"+arg[2]));
										return;}else {
											LevelManager.resetLevel(levelsControl);
											LevelManager.resetXp(levelsControl);
											LevelManager.saveLevelAccount(offlinePlayer.getPlayer());
											LevelManager.createLevelAccount(offlinePlayer.getPlayer());
											PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&aSuccessfull reseted &e"+offlinePlayer.getName()+" &aprogress levels"));
											return;}}}

				return;}else {
					PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cYou don't have the permission!"));
				}
				
			return;}
		}else {
			PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&cYou don't have the permission!"));
		}
	}

	@Override
	public List<String> executeTabCompleter(CommandSender sender, Command cmd, String label, String[] arg) {
		List<String> arguments=new ArrayList<>();

		if (sender instanceof Player) {
			Player player=(Player)sender;
			
			arguments.add("look");
			arguments.add("info");
			if (player.hasPermission("levels.progress.admin")) {
				arguments.add("levelup");
				arguments.add("reload");
				arguments.add("addlevel");
				arguments.add("setlevel");
				arguments.add("removelevel");
				arguments.add("addxp");
				arguments.add("setxp");
				arguments.add("removexp");
				arguments.add("reset");
			}
		}
		
		return arguments;
	}

}
