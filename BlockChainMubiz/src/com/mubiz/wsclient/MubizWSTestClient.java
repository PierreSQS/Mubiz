package com.mubiz.wsclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.dao.Hash;
import com.mubiz.dao.NumberOfBlock;
import com.mubiz.httpclient.MubizHttpClient;
import com.mubiz.jsonparser.MubizJsonParser;

public class MubizWSTestClient {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// get total number of blocks or the highest index
		String blockIndexMax = new MubizHttpClient(MubizHttpClient.MUBIZ_BLOCKS_URL).connectToWS();
				

		NumberOfBlock blocksCounter = MubizJsonParser.deserializeRespFromJSON(blockIndexMax, NumberOfBlock.class);
		
		// get hash
		String hashResp = new MubizHttpClient(MubizHttpClient.MUBIZ_BLOCK_INDEX_URL+blocksCounter.getBlocks()+"/").connectToWS();
				
		Hash hash = MubizJsonParser.deserializeRespFromJSON(hashResp, Hash.class);
		
		// get block data
		String blockResp = new MubizHttpClient(MubizHttpClient.MUBIZ_BLOC_HASH_URL+hash.getBlock_hash()+"/").connectToWS();
		
		

		
	}

}
