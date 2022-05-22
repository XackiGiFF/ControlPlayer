package com.hadenwatne.icontrolu.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandGUI implements Listener{
	String cmd;
	private Inventory gui;
	private Control c;
	
	CommandGUI(String command, Control co){
		cmd=command;
		c=co;
		
		gui = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', "&8&ki &8Выполнить команду &ki"));
		gui.setItem(2, this.runAsTarget(c.getTarget().getName()));
		gui.setItem(6, this.runAsSelf());
		
		c.getController().openInventory(gui);
	}
	
	@EventHandler
	public void clickGUI(InventoryClickEvent e){
		if(e.getClickedInventory() != null && e.getClickedInventory().equals(gui)){
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			
			if(e.getCurrentItem() != null){
				if(e.getCurrentItem().isSimilar(this.runAsTarget(c.getTarget().getName()))){
					p.closeInventory();
					c.getTarget().performCommand(cmd);
				}else{
					if(e.getCurrentItem().isSimilar(this.runAsSelf())){
						p.closeInventory();
						p.performCommand(cmd);
					}
				}
			}
		}
	}
	
	private ItemStack runAsTarget(String name){
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lВыполнить как ")+name);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Запускает команду от имени игрока"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Команда: &6")+cmd);
		im.setLore(lore);
		item.setItemMeta(im);
		
		return item;
	}
	
	private ItemStack runAsSelf(){
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta im2 = item.getItemMeta();
		im2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lВыполнить как ")+c.getController().getName());
		List<String> lore2 = new ArrayList<String>();
		lore2.add(ChatColor.translateAlternateColorCodes('&', "&7Запустите команду от своего имени"));
		lore2.add(ChatColor.translateAlternateColorCodes('&', "&7Команда: &6")+cmd);
		im2.setLore(lore2);
		item.setItemMeta(im2);
		
		return item;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e){
		if(e.getInventory().equals(gui)){
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryCloseEvent.getHandlerList().unregister(this);
		}
	}
}