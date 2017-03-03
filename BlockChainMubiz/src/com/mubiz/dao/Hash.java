package com.mubiz.dao;

public class Hash {
	private String block_hash;

	public String getBlock_hash() {
		return block_hash;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Hash [block_hash=").append(block_hash).append("]");
		return builder.toString();
	}

}
