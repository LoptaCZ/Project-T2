package com.raven_cze.projt2.common.content.items;

public class ItemArtifact extends PT2Item{
	int type=0;

	public ItemArtifact(Properties properties){
		super(properties);
	}

	public void setType(int newType){this.type=newType;}
}
