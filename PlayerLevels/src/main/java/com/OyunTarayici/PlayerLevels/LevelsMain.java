package com.OyunTarayici.PlayerLevels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.OyunTarayici.PlayerLevels.Commands.CommandPlayerLevels;
import com.OyunTarayici.PlayerLevels.Files.FileConfig;
import com.OyunTarayici.PlayerLevels.Files.FileLevels;
import com.OyunTarayici.PlayerLevels.Files.FilePlayers;
import com.OyunTarayici.PlayerLevels.Listener.PlayerListener;
import com.OyunTarayici.PlayerLevels.Manager.LevelManager;

import lombok.Getter;

public class LevelsMain extends JavaPlugin implements Listener {

	@Getter
	private static LevelsMain levelsMain;       

	@Override
	public void onEnable() {
		levelsMain=this;
		
		for (Player allPlayer:Bukkit.getOnlinePlayers()) {
			LevelManager.createLevelAccount(allPlayer);
			LevelManager.loadProgressLevels(allPlayer);
		}
		
		LevelManager.loadLevelSetup();
		
		registerCommands();
		registerListener();
		registerFile();
	}
	
	private void registerFile() {
		new FilePlayers(levelsMain, getFile());
		new FileLevels(levelsMain, getFile());
		new FileConfig(levelsMain, getFile());
	}

	private void registerListener() {
		new PlayerListener(this,new PlayerListener(this,this));
	}

	private void registerCommands() {
		new CommandPlayerLevels(this,"playerlevels");
	}

	@Override
	public void onDisable() {
		for (Player allPlayer:Bukkit.getOnlinePlayers()) {
			LevelManager.saveLevelAccount(allPlayer);
			LevelManager.loadProgressLevels(allPlayer);
		}
	}
}