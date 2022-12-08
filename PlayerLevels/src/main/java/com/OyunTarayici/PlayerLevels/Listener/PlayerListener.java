package com.OyunTarayici.PlayerLevels.Listener;

import java.io.File;

import org.BukkitApi.main.BukkitUtiles.ListenerUtils.BukkitListener;
import org.BukkitApi.main.BukkitUtiles.MesageUtils.PlayerUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.OyunTarayici.PlayerLevels.Manager.LevelManager;
import com.OyunTarayici.PlayerLevels.Profiles.LevelsControl;

public class PlayerListener extends BukkitListener{

	public PlayerListener(JavaPlugin plugin) {
		super(plugin);
	}

	public PlayerListener(JavaPlugin plugin, Listener listener) {
		super(plugin, listener);
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		LevelManager.createLevelAccount(e.getPlayer());
		LevelManager.saveLevelAccount(e.getPlayer());
		LevelManager.createLevelAccount(e.getPlayer());
		LevelManager.loadProgressLevels(e.getPlayer());
	}
	
	@EventHandler
	public void event(EntityDeathEvent e) {
		File file=new File(getPlugin().getDataFolder(),"config.yml");
		FileConfiguration c=YamlConfiguration.loadConfiguration(file);
 		Player player=(Player)e.getEntity().getKiller();
		LevelsControl levelsAccount=LevelsControl.getLevelMap().get(player.getUniqueId());
		
		if (c.getBoolean("animals-kill")) {
			if (e.getEntityType().equals(EntityType.SHEEP)) {
				LevelManager.addXp(levelsAccount, 15);
				LevelManager.loadProgressLevels(player);
				LevelManager.saveLevelAccount(player);
				LevelManager.createLevelAccount(player);
				PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e15 &a> Experience"));
			return;}else if (e.getEntityType().equals(EntityType.COW)){
				LevelManager.addXp(levelsAccount, 8);
				LevelManager.loadProgressLevels(player);
				LevelManager.saveLevelAccount(player);
				LevelManager.createLevelAccount(player);
				PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e8 &a> Experience"));
			return;}else if (e.getEntityType().equals(EntityType.CHICKEN)) {
				LevelManager.addXp(levelsAccount, 3);
				LevelManager.loadProgressLevels(player);
				LevelManager.saveLevelAccount(player);
				LevelManager.createLevelAccount(player);
				PlayerUtils.getPlayerMessage(player, PlayerUtils.getColoredMessage("&e3 &a> Experience"));
			return;}}else {
			return;}
	}

	@EventHandler
	public void event(PlayerQuitEvent e) {
		LevelManager.saveLevelAccount(e.getPlayer());
		LevelManager.createLevelAccount(e.getPlayer());
	}
	
}
