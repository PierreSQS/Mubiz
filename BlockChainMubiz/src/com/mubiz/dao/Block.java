package com.mubiz.dao;

import java.util.Arrays;

public class Block {
	String hash;
	String confirmations;
	int strippedsize;
	String size;
	String weight;
	int height;
	String version;
	String versionHex;
	String merkleroot;
	String[] tx;
	String time;
	String mediantime;
	String nonce;
	String bits;
	String difficulty;
	String chainwork;
	String previousblockhash;
	String nextblockhash;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Block [hash=").append(hash).append(", confirmations=").append(confirmations)
				.append(", strippedsize=").append(strippedsize).append(", size=").append(size).append(", weight=")
				.append(weight).append(", heigth=").append(height).append(", version=").append(version)
				.append(", versionHex=").append(versionHex).append(", merkleroot=").append(merkleroot)
				.append(", tx=").append(Arrays.toString(tx))
				.append(", time=").append(time).append(", mediantime=").append(mediantime).append(", nonce=")
				.append(nonce).append(", bits=").append(bits).append(", difficulty=").append(difficulty)
				.append(", chainwork=").append(chainwork).append(", previousblockhash=").append(previousblockhash)
				.append(", nextblockhash=").append(nextblockhash).append("]");
		return builder.toString();
	}
	
}
