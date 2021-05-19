package me.bobcatsss.cccondenser.handler;

import java.net.http.WebSocket.Listener;

import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDispenseEvent;

import me.bobcatsss.cccondenser.CCCondenser;
import me.bobcatsss.cccondenser.utils.BlockUtils;

public class CondenserPowerHandler implements Listener {
	
	public CCCondenser plugin;
	public BlockUtils blockUtils;
	public CondenserPowerHandler(CCCondenser pl) {
		this.plugin = pl;
		this.blockUtils = plugin.getBlockUtils();
	}
	
	@EventHandler
	public void onPower(BlockDispenseEvent event) {
		Block block = event.getBlock();
		if(!(block.getState() instanceof Dropper)) return;
		if(!blockUtils.isCondenser(block)) return;
		Dropper dropper = (Dropper) block.getState();
	}

}
