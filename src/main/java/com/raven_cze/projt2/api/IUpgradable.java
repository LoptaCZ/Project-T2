package com.raven_cze.projt2.api;

public interface IUpgradable {
	boolean canAcceptUpgrade(byte parmByte);
	boolean hasUpgrade(byte paramByte);
	boolean setUpgrade(byte paramByte);
	boolean clearUpgrade(byte paramByte);
	
	int getUpgradeLimit();
	byte[] getUpgrades();
}
