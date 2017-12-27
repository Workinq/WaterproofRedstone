package kr.kieran.wpredstone;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Redstone extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);

		final File file = new File(this.getDataFolder(), "config.yml");
		if (!file.exists()) {
			this.saveDefaultConfig();
		}
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission(this.getConfig().getString("WaterproofRedstonePermission"))) {
			sender.sendMessage(Messages.format(this.getConfig().getString("NoPermission")));
		}

		if (cmd.getName().equalsIgnoreCase("wpredstone")) {
			if (args.length != 1) {
				sender.sendMessage(Messages.format(this.getConfig().getString("InvalidSyntax")));
				return true;
			}

			if (args[0].equalsIgnoreCase("reload"))
				this.reloadConfig();
			sender.sendMessage(Messages.format(this.getConfig().getString("ConfigReloaded")));
			return true;
		}

		return true;
	}

	@EventHandler
	public void onBlockFromTo(final BlockFromToEvent e) {
		if (this.getConfig().getBoolean("EnableWaterproofRedstone")) {
			if (e.getToBlock().getType().equals(Material.REDSTONE_WIRE)) {
				e.setCancelled(true);
			}
		}
	}
}
