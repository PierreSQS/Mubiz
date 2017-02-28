package com.mubiz.dao;

import java.util.Arrays;

public class Block {
	private String hash;
	private String confirmations;
	private int strippedsize;
	private String size;
	private String weight;
	private int height;
	private String version;
	private String versionHex;
	private String merkleroot;
	private String[] tx  = {"Default"};
	private String time;
	private String mediantime;
	private String nonce;
	private String bits;
	private String difficulty;
	private String chainwork;
	private String previousblockhash;

	public Block(String hash, 
			String confirmations, int strippedsize,
			String size, String weight, int height,
			String version, String versionHex, String merkleroot, 
//			String[] tx, 
			String time, String mediantime,
			String nonce, String bits, String difficulty, 
			String chainwork, String previousblockhash,
			String nextblockhash) {
		super();
		this.hash = hash;
		this.confirmations = confirmations;
		this.strippedsize = strippedsize;
		this.size = size;
		this.weight = weight;
		this.height = height;
		this.version = version;
		this.versionHex = versionHex;
		this.merkleroot = merkleroot;
//		this.tx = tx;
		this.time = time;
		this.mediantime = mediantime;
		this.nonce = nonce;
		this.bits = bits;
		this.difficulty = difficulty;
		this.chainwork = chainwork;
		this.previousblockhash = previousblockhash;
		this.nextblockhash = nextblockhash;
	}

	private String nextblockhash;

	public String getHash() {
		return hash;
	}

	public String getConfirmations() {
		return confirmations;
	}

	public int getStrippedsize() {
		return strippedsize;
	}

	public String getSize() {
		return size;
	}

	public String getWeight() {
		return weight;
	}

	public int getHeight() {
		return height;
	}

	public String getVersion() {
		return version;
	}

	public String getVersionHex() {
		return versionHex;
	}

	public String getMerkleroot() {
		return merkleroot;
	}

	public String[] getTx() {
		return tx;
	}

	public String getTime() {
		return time;
	}

	public String getMediantime() {
		return mediantime;
	}

	public String getNonce() {
		return nonce;
	}

	public String getBits() {
		return bits;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public String getChainwork() {
		return chainwork;
	}

	public String getPreviousblockhash() {
		return previousblockhash;
	}

	public String getNextblockhash() {
		return nextblockhash;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Block [hash=").append(hash).append(", confirmations=").append(confirmations)
				.append(", strippedsize=").append(strippedsize).append(", size=").append(size).append(", weight=")
				.append(weight).append(", heigth=").append(height).append(", version=").append(version)
				.append(", versionHex=").append(versionHex).append(", merkleroot=").append(merkleroot).append(", tx=")
				.append(Arrays.toString(tx)).
				append(", time=").append(time).append(", mediantime=").append(mediantime)
				.append(", nonce=").append(nonce).append(", bits=").append(bits).append(", difficulty=")
				.append(difficulty).append(", chainwork=").append(chainwork).append(", previousblockhash=")
				.append(previousblockhash).append(", nextblockhash=").append(nextblockhash).append("]");
		return builder.toString();
	}

}
