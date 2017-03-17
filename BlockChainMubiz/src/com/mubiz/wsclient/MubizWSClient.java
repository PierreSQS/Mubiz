package com.mubiz.wsclient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.httpclient.MubizHttpClient;
import com.mubiz.jsonparser.MubizJsonParser;
import com.mubiz.dao.Block;
import com.mubiz.dao.Hash;
import com.mubiz.dao.NumberOfBlocks;
import com.mubiz.database.BlockTable;
import com.mubiz.database.MySQLUtilities;

public class MubizWSClient {
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException {

		// Initializing the Http Client
		MubizHttpClient myMubizHttpClient = new MubizHttpClient();

		// Initializing Database Connection

		MySQLUtilities myMySQLUtilities = new MySQLUtilities("mysql-sample-properties.xml");
		Connection myConnection = myMySQLUtilities.getConnection();

		BlockTable myBlockTable = new BlockTable(myConnection, myMySQLUtilities.dbName, myMySQLUtilities.dbms);

		// get total number of blocks or the highest index
		String blocksResp = myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCKS_URL);

		NumberOfBlocks blocksCounter = MubizJsonParser.deserializeRespFromJSON(blocksResp, NumberOfBlocks.class);

		// counts the actual block in the WS
		long actualBlocksInWS = blocksCounter.getBlocks();

		// for test purposes
		// long actualBlocksInWS = 25;

		// counts the actual block in the DB
		long actualBlocksInDB = BlockTable.getNumberOfRecord(myConnection);

		// for test purposes
		// long actualBlocksInDB = 455540;

		System.out.println("the number of blocks actually in the WS: " + actualBlocksInWS);
		System.out.println("the number of blocks actually in the DB: " + actualBlocksInDB);

		// just for test purposes
		for (long i = actualBlocksInWS; i > actualBlocksInWS - 40; i--) {
//		for (long i = 160; i > actualBlocksInDB; i--) {

			// CAUTION!! the loop should only insert the blocks which are new to
			// the
			// DB!!!!
//      this should be the reality			
//      for (long i = actualBlocksInWS; i > actualBlocksInDB; i--) {

			// get JSON Hash from WS
			String hashResp = myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCK_INDEX_URL + i + "/");
			Hash hash = MubizJsonParser.deserializeRespFromJSON(hashResp, Hash.class);
			System.out.println("the data of the Block (block#" + i + ")");

			// get block data
			String blockResp = myMubizHttpClient
					.connectToWS(MubizHttpClient.MUBIZ_BLOCK_HASH_URL + hash.getBlock_hash() + "/");
			Block block = MubizJsonParser.deserializeRespFromJSON(blockResp, Block.class);
			System.out.println("inserting block : block#" + i);
			myBlockTable.insertRow(block);
		}
	}
}
