package com.hadenwatne.icontrolu.files;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.hadenwatne.icontrolu.plugin.iControlU;

public class Messages {
	iControlU plugin;
	private FileConfiguration messages;
	private File messagesFile;
	private HashMap<String, String> vals;
	private final String colorChar = Character.toString(ChatColor.COLOR_CHAR);
	
	
	public Messages(iControlU c){
		plugin=c;
		this.messages = null;
	    this.messagesFile = null;
	    
	    vals = new HashMap<String, String>();
	    this.reloadMessages();
	    
	    if(this.getMessages().contains("plugin-title")){
	    	this.loadValues();
	    }else{
	    	this.saveDefaultValues();
	    }
	    
	    plugin.getServer().getLogger().log(Level.INFO, "Загруженны пользовательские сообщения");
	}
	
	public void saveDefaultValues(){
		this.getMessages().set("plugin-title", "&8[&7ControlPlayer&8]");
		this.getMessages().set("project-name", "&bProjectName");
		this.getMessages().set("wrong-command", "&cНеправильная команда или использование!");
		this.getMessages().set("no-permission", "&cНет доступа!");
		this.getMessages().set("must-be-player", "&cВы должны быть игроком, чтобы выполнить эту команду!");
		this.getMessages().set("must-cooldown", "&cПодождите!");
		this.getMessages().set("cooldown-ended", "&aОжидание завершено!");
		this.getMessages().set("player-is-immune", "&cУ этого игрока иммунитет!");
		this.getMessages().set("player-is-offline", "&cЭтого игрока нет в сети!");
		this.getMessages().set("player-is-busy", "&cВы не можете сделать это прямо сейчас!");
		this.getMessages().set("player-is-not-controlling", "&cЭтот игрок никого не контролирует!");
		this.getMessages().set("cannot-control-self", "&cЭтот игрок уже контролирует себя!");
		this.getMessages().set("not-controlling", "&cВы никого не контролируете!");
		this.getMessages().set("troll-ended-other", "&c%CONTROLLER% закончил контролировать.");
		this.getMessages().set("player-not-found", "&cЭтот игрок не может быть найден!");
		this.getMessages().set("plugin-reloaded", "&aПлагин был перезагружен!");
		this.getMessages().set("you-died", "&cВы умерли, находясь под контролем!");
		this.getMessages().set("helmet-blocked-control", "&cЖертва была невосприимчива к контролю!");
		this.getMessages().set("now-hidden", "Вы &eскрыли себя &aот игроков!");
		this.getMessages().set("now-visible", "&aВы &eбольше не скрываетесь &aот игроков!");
		this.getMessages().set("no-nearby-players", "&cПоблизости нет игроков!");
		this.getMessages().set("custom-control-started", "&aВы &eактивировали Режим Контроля с &a%VICTIM%");
		this.getMessages().set("custom-control-started-victim", "&a%CONTROLLER% &eактивировал Режим Контроля над &aВами");
		this.getMessages().set("custom-control-started-other", "&a%CONTROLLER% &eактивировал Режим Контроля над &a%VICTIM%");
		this.getMessages().set("custom-control-ended", "&aВы &eдеактевировали Режим Контроля над &a%VICTIM%");
		this.getMessages().set("custom-control-ended-victim", "&a%CONTROLLER% &eдеактевировали Режим Контроля над &aВами");
		this.getMessages().set("custom-control-ended-other", "&a%CONTROLLER% &eдеактевировали Режим Контроля над &a%VICTIM%");
		this.getMessages().set("custom-victim-died", "&a%VICTIM% &cумер, находясь под контролем!");
		this.getMessages().set("custom-victim-health", "У &a%VICTIM% &eосталось &c%HEALTH% &eHP!");
		this.getMessages().set("toggle-simple-mode", "&aПереключить на &7Обычный Режим &a%STATUS%");

		this.saveMessages();
		this.loadValues();
	}
	
	public void loadValues(){
		vals.clear();
		
		// Introduced v2.3.9
		if(!getMessages().contains("toggle-simple-mode")) {
			this.getMessages().set("toggle-simple-mode", "&aПереключить на &7Обычный Режим &a%STATUS%");
			this.saveMessages();
		}
		
		vals.put("plugin-title", getMessages().getString("plugin-title").replaceAll("&", colorChar)+" ");
		vals.put("project-name", getMessages().getString("project-name").replaceAll("&", colorChar)+" ");
		vals.put("wrong-command", getMessages().getString("wrong-command").replaceAll("&", colorChar));
		vals.put("no-permission", getMessages().getString("no-permission").replaceAll("&", colorChar));
		vals.put("must-be-player", getMessages().getString("must-be-player").replaceAll("&", colorChar));
		vals.put("must-cooldown", getMessages().getString("must-cooldown").replaceAll("&", colorChar));
		vals.put("cooldown-ended", getMessages().getString("cooldown-ended").replaceAll("&", colorChar));
		vals.put("player-is-immune", getMessages().getString("player-is-immune").replaceAll("&", colorChar));
		vals.put("player-is-offline", getMessages().getString("player-is-offline").replaceAll("&", colorChar));
		vals.put("player-is-busy", getMessages().getString("player-is-busy").replaceAll("&", colorChar));
		vals.put("player-is-not-controlling", getMessages().getString("player-is-not-controlling").replaceAll("&", colorChar));
		vals.put("cannot-control-self", getMessages().getString("cannot-control-self").replaceAll("&", colorChar));
		vals.put("not-controlling", getMessages().getString("not-controlling").replaceAll("&", colorChar));
		vals.put("troll-ended-other", getMessages().getString("troll-ended-other").replaceAll("&", colorChar));
		vals.put("player-not-found", getMessages().getString("player-not-found").replaceAll("&", colorChar));
		vals.put("plugin-reloaded", getMessages().getString("plugin-reloaded").replaceAll("&", colorChar));
		vals.put("you-died", getMessages().getString("you-died").replaceAll("&", colorChar));
		vals.put("helmet-blocked-control", getMessages().getString("helmet-blocked-control").replaceAll("&", colorChar));
		vals.put("now-hidden", getMessages().getString("now-hidden").replaceAll("&", colorChar));
		vals.put("now-visible", getMessages().getString("now-visible").replaceAll("&", colorChar));
		vals.put("no-nearby-players", getMessages().getString("no-nearby-players").replaceAll("&", colorChar));
		vals.put("custom-control-started", getMessages().getString("custom-control-started").replaceAll("&", colorChar));
		vals.put("custom-control-started-victim", getMessages().getString("custom-control-started-victim").replaceAll("&", colorChar));
		vals.put("custom-control-started-other", getMessages().getString("custom-control-started-other").replaceAll("&", colorChar));
		vals.put("custom-control-ended", getMessages().getString("custom-control-ended").replaceAll("&", colorChar));
		vals.put("custom-control-ended-victim", getMessages().getString("custom-control-ended-victim").replaceAll("&", colorChar));
		vals.put("custom-control-ended-other", getMessages().getString("custom-control-ended-other").replaceAll("&", colorChar));
		vals.put("custom-victim-died", getMessages().getString("custom-victim-died").replaceAll("&", colorChar));
		vals.put("custom-victim-health", getMessages().getString("custom-victim-health").replaceAll("&", colorChar));
		vals.put("toggle-simple-mode", getMessages().getString("toggle-simple-mode").replaceAll("&", colorChar));
	}
	
	public String getTitle(){
		return vals.get("plugin-title");
	}
	
	public String wrongCommand(){
		return vals.get("plugin-title")+vals.get("wrong-command");
	}
	public String getProjectName(){
		return vals.get("project-name");
	}
	public String noPermission(){
		return vals.get("plugin-title")+vals.get("no-permission");
	}
	public String mustBePlayer(){
		return vals.get("plugin-title")+vals.get("must-be-player");
	}
	public String mustCooldown(){
		return vals.get("plugin-title")+vals.get("must-cooldown");
	}
	public String playerIsImmune(){
		return vals.get("plugin-title")+vals.get("player-is-immune");
	}
	public String playerIsOffline(){
		return vals.get("plugin-title")+vals.get("player-is-offline");
	}
	public String playerIsBusy(){
		return vals.get("plugin-title")+vals.get("player-is-busy");
	}
	public String playerNotControlling(){
		return vals.get("plugin-title")+vals.get("player-is-not-controlling");
	}
	public String cantControlSelf(){
		return vals.get("plugin-title")+vals.get("cannot-control-self");
	}
	public String playerIsNotControlling(){
		return vals.get("plugin-title")+vals.get("not-controlling");
	}
	public String playerNotFound(){
		return vals.get("plugin-title")+vals.get("player-not-found");
	}
	public String startControl(String vname){
		return vals.get("plugin-title")+vals.get("custom-control-started").replaceAll("%VICTIM%", vname);
	}
	public String startControlVictim(String cname){
		return vals.get("plugin-title")+vals.get("custom-control-started-victim").replaceAll("%CONTROLLER%", cname);
	}
	public String startControlAdmin(String cname, String vname){
		return vals.get("plugin-title")+vals.get("custom-control-started-other").replaceAll("%CONTROLLER%", cname).replaceAll("%VICTIM%", vname);
	}
	public String stopControl(String vname){
		return vals.get("plugin-title")+vals.get("custom-control-ended").replaceAll("%VICTIM%", vname);
	}
	public String stopControlVictim(String cname){
		return vals.get("plugin-title")+vals.get("custom-control-ended-victim").replaceAll("%CONTROLLER%", cname);
	}
	public String stopControlAdmin(String cname, String vname){
		return vals.get("plugin-title")+vals.get("custom-control-ended-other").replaceAll("%CONTROLLER%", cname).replaceAll("%VICTIM%", vname);
	}
	public String pluginReloaded(){
		return vals.get("plugin-title")+vals.get("plugin-reloaded");
	}
	public String victimDied(String vname){
		return vals.get("plugin-title")+vals.get("custom-victim-died").replaceAll("%VICTIM%", vname);
	}
	public String victimDiedVictim(){
		return vals.get("plugin-title")+vals.get("you-died");
	}
	public String helmetBlocked(){
		return vals.get("plugin-title")+vals.get("helmet-blocked-control");
	}
	public String cooldownEnded(){
		return vals.get("plugin-title")+vals.get("cooldown-ended");
	}
	public String victimHealth(String vname, double h){
		return vals.get("plugin-title")+vals.get("custom-victim-health").replaceAll("%VICTIM%", vname).replaceAll("%HEALTH%", Long.toString(Math.round(h)));
	}
	public String controllerHidden(){
		return vals.get("plugin-title")+vals.get("now-hidden");
	}
	public String controllerVisible(){
		return vals.get("plugin-title")+vals.get("now-visible");
	}
	public String noNearbyPlayers(){
		return vals.get("plugin-title")+vals.get("no-nearby-players");
	}
	public String trollEndedOther(String name){
		return vals.get("plugin-title")+vals.get("troll-ended-other").replaceAll("%CONTROLLER%", name);
	}
	public String toggleSimpleMode(boolean on){
		String s = on ? colorChar+"aON" : colorChar+"cOFF";
		
		return vals.get("plugin-title")+vals.get("toggle-simple-mode").replaceAll("%STATUS%", s);
	}
	
	public void reloadMessages(){
		if (this.messagesFile == null){
			this.messagesFile = new File(plugin.getDataFolder()+"/messages.yml");
			this.messages = YamlConfiguration.loadConfiguration(this.messagesFile);
		}
	}
	 
	public FileConfiguration getMessages(){
		if (this.messages == null) {
			reloadMessages();
		}
		return this.messages;
	}
	 
	public void saveMessages(){
		if ((this.messages == null) || (this.messagesFile == null)) {
			return;
		}
		
		try{
			getMessages().save(this.messagesFile);
		} catch (Exception ex){
			plugin.getLogger().log(Level.SEVERE, "Не удалось сохранить конфигурацию в " + this.messagesFile, ex);
		}
	}
}
