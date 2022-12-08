package com.OyunTarayici.PlayerLevels.Profiles;

import java.util.HashMap;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LevelsControl {
	
	@Getter
	public static HashMap<UUID, LevelsControl> levelMap=new HashMap<>();

	private int level=0;
	private int xp=0;
	
	public LevelsControl(int level, int xp) {
		this.level=level;
		this.xp=xp;;
	}
}
