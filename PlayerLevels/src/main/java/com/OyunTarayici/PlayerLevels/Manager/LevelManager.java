package com.OyunTarayici.PlayerLevels.Manager;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import com.OyunTarayici.PlayerLevels.LevelsMain;
import com.OyunTarayici.PlayerLevels.Profiles.LevelsControl;

public class LevelManager {

	public static void createLevelAccount(Player player) {
		File file=new File(LevelsMain.getLevelsMain().getDataFolder(),"players.yml");
		FileConfiguration c=YamlConfiguration.loadConfiguration(file);
		
		if (!player.hasPlayedBefore()) {
			LevelsControl.getLevelMap().put(player.getUniqueId(), new LevelsControl(0, 0));
			c.set("players."+player.getUniqueId()+".progress_level", 0);
			c.set("players."+player.getUniqueId()+".progress_xp", 0);
			try {
				c.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return;}else {
			int progress_level=c.getInt("players."+player.getUniqueId()+".progress_level");
			int progress_xp=c.getInt("players."+player.getUniqueId()+".progress_xp");
			LevelsControl.getLevelMap().put(player.getUniqueId(), new LevelsControl(progress_level, progress_xp));
		}
	}

	public static void saveLevelAccount(Player player) {
		File file=new File(LevelsMain.getLevelsMain().getDataFolder(),"players.yml");
		FileConfiguration c=YamlConfiguration.loadConfiguration(file);
		LevelsControl playerLevels=LevelsControl.getLevelMap().get(player.getUniqueId());
		if (LevelsControl.getLevelMap().containsKey(player.getUniqueId())) {
			c.set("players."+player.getUniqueId()+".progress_level", playerLevels.getLevel());
			c.set("players."+player.getUniqueId()+".progress_xp", playerLevels.getXp());
			try {
				c.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			LevelsControl.getLevelMap().remove(player.getUniqueId());
		}
	}
	
	public static void loadProgressLevels(Player player) {
		File file=new File(LevelsMain.getLevelsMain().getDataFolder(),"levels.yml");
		FileConfiguration levelConfig=YamlConfiguration.loadConfiguration(file);
		LevelsControl levelsAccount=LevelsControl.getLevelMap().get(player.getUniqueId());
		if (!levelConfig.contains("levels")) {
			Bukkit.getConsoleSender().sendMessage("Sorry but not search level up schematic!");
		return;}else {
			for (String keys:levelConfig.getConfigurationSection("levels").getKeys(false)) {
				keys=String.valueOf(levelsAccount);
				int progress_level=levelConfig.getInt("levels."+keys+".level");
				int progress_xp=levelConfig.getInt("levels."+keys+".xp");
				levelUp(player,progress_level, progress_xp);
			}
		}
	}
	
	public static void levelUp(Player player,int progressXp,int progressLevels) {
		File Levelfile=new File(LevelsMain.getLevelsMain().getDataFolder(),"levels.yml");
		FileConfiguration levelConfig=YamlConfiguration.loadConfiguration(Levelfile);
		LevelsControl levelsAccount=LevelsControl.getLevelMap().get(player.getUniqueId());
		
		if (levelConfig.contains("levels")) {
				progressLevels=levelConfig.getInt("levels."+levelsAccount.getLevel()+".level");
				progressXp=levelConfig.getInt("levels."+levelsAccount.getLevel()+".xp");

			if (levelsAccount.getXp()>=progressXp) {
				if (levelsAccount.getLevel()==progressLevels) {
				return;}else {
					setLevel(levelsAccount, progressLevels);
					saveLevelAccount(player);
					createLevelAccount(player);
				return;}
			}
		return;}
	}
	
	public static void addLevel(LevelsControl levels,int addLevelss) {
		levels.setLevel(levels.getLevel()+addLevelss);
	}
	
	public static void setLevel(LevelsControl levels,int setLevelss) {
		levels.setLevel(setLevelss);
	}

	public static void removeLevel(LevelsControl levels,int remLevelss) {
		levels.setLevel(levels.getLevel()-remLevelss);
	}

	public static void resetLevel(LevelsControl levels) {
		levels.setLevel(0);
	}
	
	public static void addXp(LevelsControl levels,int addXp) {
		levels.setXp(levels.getXp()+addXp);
	}

	public static void setXp(LevelsControl levels,int setXp) {
		levels.setXp(setXp);
	}

	public static void removeXp(LevelsControl levels,int remXp) {
		levels.setXp(levels.getXp()-remXp);
	}

	public static void resetXp(LevelsControl levels) {
		levels.setXp(0);
	}

	public static Integer getProgressLevels(LevelsControl levels) {
		return levels.getLevel();
	}
	
	public static Integer getProgressXp(LevelsControl levels) {
		return levels.getXp();
	}
	
	public static void loadLevelSetup() {
		File file=new File(LevelsMain.getLevelsMain().getDataFolder(),"levels.yml");
		FileConfiguration levelConfig=YamlConfiguration.loadConfiguration(file);
		levelConfig.addDefault("levels."+0+".level", 1);
		levelConfig.addDefault("levels."+0+".xp", 0);
		levelConfig.addDefault("levels."+1+".level", 2);
		levelConfig.addDefault("levels."+1+".xp", 500);
		levelConfig.addDefault("levels."+2+".level", 3);
		levelConfig.addDefault("levels."+2+".xp", 1000);
		levelConfig.addDefault("levels."+3+".level", 4);
		levelConfig.addDefault("levels."+3+".xp", 2000);
		levelConfig.addDefault("levels."+4+".level", 5);
		levelConfig.addDefault("levels."+4+".xp", 3500);
		levelConfig.addDefault("levels."+5+".level", 6);
		levelConfig.addDefault("levels."+5+".xp", 5500);
		levelConfig.addDefault("levels."+6+".level", 7);
		levelConfig.addDefault("levels."+6+".xp", 7000);
		levelConfig.addDefault("levels."+7+".level", 8);
		levelConfig.addDefault("levels."+7+".xp", 12000);
		levelConfig.addDefault("levels."+8+".level", 9);
		levelConfig.addDefault("levels."+8+".xp", 22000);
		levelConfig.addDefault("levels."+9+".level", 10);
		levelConfig.addDefault("levels."+9+".xp", 34000);
		levelConfig.addDefault("levels."+10+".level", 11);
		levelConfig.addDefault("levels."+10+".xp", 45000);
		levelConfig.options().copyDefaults(true);
		try {
			levelConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
