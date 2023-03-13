package com.raven_cze.projt2.api;

public interface IUpgradable {
	boolean canAcceptUpgrade(byte upgradeID);
	boolean hasUpgrade(byte upgradeID);
	boolean setUpgrade(byte upgradeID);
	boolean clearUpgrade(byte index);
	
	int getUpgradeLimit();
	byte[] getUpgrades();
}
