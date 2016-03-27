package core.savesystem;

import java.util.ArrayList;

public class SaveConfigurationHandler {

	// all the saves the player has done
	public static ArrayList<SaveConfiguration> saves = new ArrayList<SaveConfiguration>();

	public void addSave(SaveConfiguration saveConf){
		saves.add(saveConf);
	}
	
	// or we can also get a save giving a name.-
	public SaveConfiguration getSave(int i){
		return saves.get(i);
	}
}
