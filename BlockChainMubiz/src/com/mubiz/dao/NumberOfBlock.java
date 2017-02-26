package com.mubiz.dao;

public class NumberOfBlock {
	int blocks;

	public int getBlocks() {
		return blocks;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NumberOfBlock [nbOfBlock=").append(blocks).append("]");
		return builder.toString();
	}
}
