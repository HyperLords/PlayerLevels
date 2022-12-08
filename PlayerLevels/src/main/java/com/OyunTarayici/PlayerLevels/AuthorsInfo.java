package com.OyunTarayici.PlayerLevels;

import org.BukkitApi.main.BukkitUtiles.MesageUtils.PlayerUtils;
import org.bukkit.entity.Player;

public class AuthorsInfo {

	public static String getAuthorsUsername() {
		return PlayerUtils.getColoredMessage("&a&lByEZberCime");
	}

	public static String getAuthorsDiscord() {
		return PlayerUtils.getColoredMessage("&a&lEZberCime#1532");
	}

	public static String getAuthorsSpigotMC() {
		return PlayerUtils.getColoredMessage("&a&lOyunTarayici");
	}

	public static String getAuthorsGithub() {
		return PlayerUtils.getColoredMessage("&a&lHyperLords");
	}

	public static String getPluginName() {
		return PlayerUtils.getColoredMessage("&aPlayerLevels");
	}

	public static String getVersion() {
		return PlayerUtils.getColoredMessage("&aLevelAddons");
	}

	public static String getPluginAPI() {
		return PlayerUtils.getColoredMessage("&aBukkitApi");
	}

	public static String getBukkitVerisons() {
		return PlayerUtils.getColoredMessage("&a[1.8 - 1.12]");
	}
	
	public static Player byAuthorsInfo(Player player) {
		PlayerUtils.getPlayerMessage(player, "Plugins Original MC accounts "+getAuthorsUsername());
		PlayerUtils.getPlayerMessage(player, "Plugin discord dm. "+getAuthorsDiscord());
		PlayerUtils.getPlayerMessage(player, "Plugin github name "+getAuthorsGithub());
		PlayerUtils.getPlayerMessage(player, "Plugin spigotmc name "+getAuthorsSpigotMC());
		PlayerUtils.getPlayerMessage(player, "Plugin name "+getPluginName());
		PlayerUtils.getPlayerMessage(player, "Plugin support api "+getPluginAPI());
		PlayerUtils.getPlayerMessage(player, "Support verisons "+getBukkitVerisons());
		PlayerUtils.getPlayerMessage(player, "Plugin versions "+getVersion());
	return player;}
}