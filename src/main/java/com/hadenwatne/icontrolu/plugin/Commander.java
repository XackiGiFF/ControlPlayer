package com.hadenwatne.icontrolu.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Commander implements CommandExecutor{
	private iControlU plugin;
	public Commander(iControlU c){
		plugin=c;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("control")){
			if(sender.hasPermission("control.use")) {
				if(args.length == 0){
					//String pluginname = plugin.getDescription().getName();
					//String version = plugin.getDescription().getVersion();
					//String projectname = plugin.getMessages().getProjectName();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f========== &b&lHacker&f&lCraft &8| &bКОНТРОЛЬ &f=========="));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/control [controller] [player] &8- &fВойти в режим контроля."));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/control stop [controller] &8- Выйти из режима контроля."));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/control nearest &8- &fКонтролировать ближайшего игрока."));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/control forcechat [player] [message] &f- Написать сообщение от имени игрока."));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/control simple &8- &fВыйти в обычный режим."));
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f========== &b&lHacker&f&lCraft &8| &bКОНТРОЛЬ &f=========="));
				}else{
					if(args.length==1){
						//For /control <player>
						Player checkpl = Bukkit.getPlayer(args[0]);

						if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")){
							if(sender.hasPermission("control.reload")){
								plugin.getConfiguration().loadValues();
								sender.sendMessage(plugin.getMessages().pluginReloaded());
							}else{
								sender.sendMessage(plugin.getMessages().noPermission());
							}
						}else if(args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("s")){
							if(sender instanceof Player){
								Player p = (Player)sender;
								if(p.hasPermission("control")){
									if(this.isPlayerBusy(p)){
										for(Control cs : plugin.getControlSessions()){
											if(cs.getController().equals(p)){
												cs.stopControl();
												plugin.getControlSessions().remove(cs);
												p.sendMessage(plugin.getMessages().stopControl(cs.getTarget().getName()));
												
												if(plugin.getConfiguration().getShowMessages()){
													cs.getTarget().sendMessage(plugin.getMessages().stopControlVictim(p.getName()));
												}
												
												break;
											}
										}
									}else{
										sender.sendMessage(plugin.getMessages().playerIsNotControlling());
									}
								}else{
									sender.sendMessage(plugin.getMessages().noPermission());
								}
							}else{
								sender.sendMessage(plugin.getMessages().mustBePlayer());
							}
						}else if(args[0].equalsIgnoreCase("controlnearest") || args[0].equalsIgnoreCase("cn")){
							if(sender instanceof Player){
								Player p = (Player)sender;
								
								if(p.hasPermission("control.nearest")){
									if(!this.isPlayerBusy(p)){
										Player t = null;
										Location nearest = null;
										
										for(Player op : Bukkit.getOnlinePlayers()){
											if(!op.getName().equals(p.getName())) {
												if(op.getWorld().getName().equals(p.getWorld().getName())) {
													if(p.getLocation().distance(op.getLocation())<=plugin.getConfiguration().getMaxRadius()){
														if(!op.hasPermission("control.exempt")){
															if(!this.isPlayerBusy(op)){
																if(nearest == null){
																	nearest = op.getLocation();
																	t = op;
																}else{
																	if(op.getLocation().distanceSquared(p.getLocation()) < nearest.distanceSquared(p.getLocation())){
																		nearest = op.getLocation();
																		t = op;
																	}
																}
															}
														}
													}
												}
											}
										}
										
										if(t != null){
											this.startControlMode(p, t);
										}else{
											sender.sendMessage(plugin.getMessages().noNearbyPlayers());
										}
									}else{
										sender.sendMessage(plugin.getMessages().playerIsBusy());
									}
								}else{
									sender.sendMessage(plugin.getMessages().noPermission());
								}
							}else{
								sender.sendMessage(plugin.getMessages().mustBePlayer());
							}
						}else if(args[0].equalsIgnoreCase("simple") || args[0].equalsIgnoreCase("sim")){
							if(sender instanceof Player) {
								if(sender.hasPermission("control.simple")) {
									Player p = (Player)sender;
									
									if(p.hasMetadata("CP_SimpleMode")) {
										p.removeMetadata("CP_SimpleMode", plugin);
										p.sendMessage(plugin.getMessages().toggleSimpleMode(false));
									}else {
										p.setMetadata("CP_SimpleMode", new FixedMetadataValue(plugin, true));
										p.sendMessage(plugin.getMessages().toggleSimpleMode(true));
									}
								}else {
									sender.sendMessage(plugin.getMessages().noPermission());
								}
							}else {
								sender.sendMessage(plugin.getMessages().mustBePlayer());
							}
						//For /control <player>
						}else if (checkpl != null) {
							//Сюда плееров
							if(sender instanceof Player){
								Player p = (Player)sender;
								if(p.hasPermission("control")){
									Player t = Bukkit.getPlayer(args[0]);
									
									if(t != null){
										if(!p.equals(t)){
											if(!t.hasPermission("control.exempt")){
												if(!this.isPlayerBusy(p) && !this.isPlayerBusy(t)){
													if(t.isOnline()){
														this.startControlMode(p, t);
													}else{
														sender.sendMessage(plugin.getMessages().playerIsOffline());
													}
												}else{
													sender.sendMessage(plugin.getMessages().playerIsBusy());
												}
											}else{
												sender.sendMessage(plugin.getMessages().playerIsImmune());
											}
										}else{
											sender.sendMessage(plugin.getMessages().cantControlSelf());
										}
									}else{
										sender.sendMessage(plugin.getMessages().playerNotFound());
									}
								}else{
									sender.sendMessage(plugin.getMessages().noPermission());
								}
							}else{
								sender.sendMessage(plugin.getMessages().mustBePlayer());
							}
						} else {
							sender.sendMessage(plugin.getMessages().playerNotFound());
						}
					}else if(args.length == 2){
						Player checkpl = Bukkit.getPlayer(args[0]);
						Player checkt = Bukkit.getPlayer(args[1]);
						if(args[0].equalsIgnoreCase("control") || args[0].equalsIgnoreCase("c")){
							if(sender instanceof Player){
								Player p = (Player)sender;
								if(p.hasPermission("control")){
									Player t = Bukkit.getPlayer(args[1]);
									
									if(t != null){
										if(!p.equals(t)){
											if(!t.hasPermission("control.exempt")){
												if(!this.isPlayerBusy(p) && !this.isPlayerBusy(t)){
													if(t.isOnline()){
														this.startControlMode(p, t);
													}else{
														sender.sendMessage(plugin.getMessages().playerIsOffline());
													}
												}else{
													sender.sendMessage(plugin.getMessages().playerIsBusy());
												}
											}else{
												sender.sendMessage(plugin.getMessages().playerIsImmune());
											}
										}else{
											sender.sendMessage(plugin.getMessages().cantControlSelf());
										}
									}else{
										sender.sendMessage(plugin.getMessages().playerNotFound());
									}
								}else{
									sender.sendMessage(plugin.getMessages().noPermission());
								}
							}else{
								sender.sendMessage(plugin.getMessages().mustBePlayer());
							}
						}else if(args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("s")){
							if(sender.hasPermission("control.other")){
								Player p = Bukkit.getPlayer(args[1]);
								
								if(p != null){
									if(this.isPlayerBusy(p)){
										Control cs = null;
										for(Control c : plugin.getControlSessions()){
											if(c.getController().equals(p)){
												c.stopControl();
												cs = c;
												break;
											}
										}
										
										plugin.getControlSessions().remove(cs);
										sender.sendMessage(plugin.getMessages().stopControlAdmin(p.getName(), cs.getTarget().getName()));
										
										if(plugin.getConfiguration().getShowMessages()){
											if(plugin.getConfiguration().getShowMessages()){
												p.sendMessage(plugin.getMessages().stopControl(cs.getTarget().getName()));
												cs.getTarget().sendMessage(plugin.getMessages().stopControlVictim(p.getName()));
											}
										}
									}else{
										sender.sendMessage(plugin.getMessages().playerNotControlling());
									}
								}else{
									sender.sendMessage(plugin.getMessages().playerNotFound());
								}
							}else{
								sender.sendMessage(plugin.getMessages().noPermission());
							}
						
						//For /control <player>
					}else if (checkpl != null && checkt != null) {
						//Сюда плееров
						if(sender instanceof Player){
							Player p = Bukkit.getPlayer(args[0]);
							if(p.hasPermission("control")){
								Player t = Bukkit.getPlayer(args[1]);
								
								if(t != null){
									if(!p.equals(t)){
										if(!t.hasPermission("control.exempt")){
											if(!this.isPlayerBusy(p) && !this.isPlayerBusy(t)){
												if(t.isOnline()){
													this.startControlMode(p, t);
												}else{
													sender.sendMessage(plugin.getMessages().playerIsOffline());
												}
											}else{
												sender.sendMessage(plugin.getMessages().playerIsBusy());
											}
										}else{
											sender.sendMessage(plugin.getMessages().playerIsImmune());
										}
									}else{
										sender.sendMessage(plugin.getMessages().cantControlSelf());
									}
								}else{
									sender.sendMessage(plugin.getMessages().playerNotFound());
								}
							}else{
								sender.sendMessage(plugin.getMessages().noPermission());
							}
						}else{
							sender.sendMessage(plugin.getMessages().mustBePlayer());
						}
					} else if (checkt == null || checkt == null) {
						sender.sendMessage(plugin.getMessages().playerNotFound());
					} else {
							sender.sendMessage(plugin.getMessages().wrongCommand());
						}
					} else {
						if(args[0].equalsIgnoreCase("control") || args[0].equalsIgnoreCase("c")){
							Player p = Bukkit.getPlayer(args[1]);
							Player t = Bukkit.getPlayer(args[2]);
							
							if(sender.hasPermission("control.other")){
								if(p != null && t != null){
									if(!p.equals(t)){
										if(!t.hasPermission("control.exempt")){
											if(!this.isPlayerBusy(p) && !this.isPlayerBusy(t)){
												if(p.isOnline() && t.isOnline()){
													this.startControlMode(p, t, sender);
												}else{
													sender.sendMessage(plugin.getMessages().playerIsOffline());
												}
											}else{
												sender.sendMessage(plugin.getMessages().playerIsBusy());
											}
										}else{
											sender.sendMessage(plugin.getMessages().playerIsImmune());
										}
									}else{
										sender.sendMessage(plugin.getMessages().cantControlSelf());
									}
								}else{
									sender.sendMessage(plugin.getMessages().playerNotFound());
								}
							}else{
								sender.sendMessage(plugin.getMessages().noPermission());
							}
						}else if(args[0].equalsIgnoreCase("forcechat") || args[0].equalsIgnoreCase("fc")){
							if(sender.hasPermission("control.forcechat")){
								Player t = Bukkit.getPlayer(args[1]);
								
								if(t != null){
									if(t.isOnline()){
										if(!t.hasPermission("control.exempt")){
											StringBuilder msg = new StringBuilder();

											for(int i=2; i<args.length; i++){
												msg.append(args[i]);
												msg.append(" ");
											}
											
											t.chat(msg.toString());
										}else{
											sender.sendMessage(plugin.getMessages().playerIsImmune());
										}
									}else{
										sender.sendMessage(plugin.getMessages().playerIsOffline());
									}
								}else{
									sender.sendMessage(plugin.getMessages().playerNotFound());
								}
							}else{
								sender.sendMessage(plugin.getMessages().noPermission());
							}
						}else{
							sender.sendMessage(plugin.getMessages().wrongCommand());
						}
					}
				}
				return true;
			} else {
				sender.sendMessage("Неизвестная команда. Используйте \"/help\" для просмотра команд.");
			}
		}
		return false;
	}
	
	private boolean isPlayerBusy(Player p){
		for(Control c : plugin.getControlSessions()){
			if(c.getController().equals(p) || c.getTarget().equals(p)){
				return true;
			}
		}
		
		return false;
	}
	
	private void startControlMode(Player c, Player t){
		if(!plugin.getCooldowns().contains(c.getUniqueId().toString())){
			Control cs = new Control(plugin, t, c);
			cs.startControl();
			plugin.getControlSessions().add(cs);
			c.sendMessage(plugin.getMessages().startControl(t.getName()));
			
			if(plugin.getConfiguration().getShowMessages()){
				t.sendMessage(plugin.getMessages().startControlVictim(c.getName()));
			}
		}else{
			c.sendMessage(plugin.getMessages().mustCooldown());
		}
	}
	
	private void startControlMode(Player c, Player t, CommandSender admin){
		if(!plugin.getCooldowns().contains(c.getUniqueId().toString())){
			Control cs = new Control(plugin, t, c);
			cs.startControl();
			plugin.getControlSessions().add(cs);
			admin.sendMessage(plugin.getMessages().startControlAdmin(c.getName(), t.getName()));
			
			if(plugin.getConfiguration().getShowMessages()){
				c.sendMessage(plugin.getMessages().startControl(t.getName()));
				t.sendMessage(plugin.getMessages().startControlVictim(c.getName()));
			}
		}else{
			admin.sendMessage(plugin.getMessages().playerIsBusy());
		}
	}
}
