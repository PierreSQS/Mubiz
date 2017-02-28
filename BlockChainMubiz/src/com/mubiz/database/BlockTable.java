/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mubiz.database;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mubiz.dao.Block;

public class BlockTable {

  private String dbName;
  private Connection con;
  private String dbms;


  public BlockTable(Connection connArg, String dbNameArg, String dbmsArg) {
    super();
    this.con = connArg;
    this.dbName = dbNameArg;
    this.dbms = dbmsArg;

  }

  public void createTable() throws SQLException {
    String createString =
      "CREATE TABLE Block "+
         "(hash VARCHAR(80)  PRIMARY KEY,"+
    	 "confirmations VARCHAR(40),"+
    	 "strippedsize INT,"+
    	 "size VARCHAR(40),"+
    	 "weight VARCHAR(40),"+
    	 "height INT,"+
    	 "version VARCHAR(40),"+
    	 "versionHex VARCHAR(40),"+
    	 "merkleroot VARCHAR(80),"+
 //   	 "tx VARCHAR(),"+
    	 "time VARCHAR(40),"+
    	 "mediantime VARCHAR(40),"+
    	 "nonce VARCHAR(40),"+
    	 "bits VARCHAR(40),"+
    	 "difficulty VARCHAR(40),"+
    	 "chainwork VARCHAR(80),"+
    	 "previousblockhash VARCHAR(80),"+
    	 "nextblockhash VARCHAR(80))";   	 
      
    		
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      stmt.executeUpdate(createString);
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public void populateTable() throws SQLException {
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      stmt.executeUpdate("insert into BLOCK " +
                         "(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
                         "values ('000000000000000001b4bea1fdc7fdc0df119a5cdb52e88f914f90a505f89436' , '1','998202','998202','3992808','455161','536870914','pierrottest','d77311ab249ad94c414d0b2a467976d52229ce5eadef072424ab6d5d43637190')");
      
      stmt.executeUpdate("insert into BLOCK " +
                         "(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
                         "values ('00000000000000000053f5b44e0f7325aae817288a2c5c2a45b9da8dc0674ba5' , '1','998135','998135','3992540','455152','536870914','pierrottest','3269f91f966762974ab7f74d9ac374fb9b1fc409f4e5900f9b4013796e0b2a83')");
     
      stmt.executeUpdate("insert into BLOCK " +
                         "(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
                         "values ('0000000000000000011d05921074441ee3902d8dc0bd3849dcd44191ea90a4f0' , '3','999959','999959','3999836','455153','536870912','pierrottest','af5c75d81322758d3956ee72341c9e4c6751d2b7e8e0382560f4968bf445a4b8')");
      
      stmt.executeUpdate("insert into BLOCK " +
                         "(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
                         "values ('0000000000000000000a37f63c3966e9d247738d25b7325c31379812a54054ea' , '11','998162','998162','3992648','455145','536870912','pierrottest','61c3edc4c8d4f8691beee58081b5f958295ca8299006c1e30e10cbae239adeb4')");
      stmt.executeUpdate("insert into BLOCK " +
                         "(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
                         "values ('000000000000000000751bd118d586d9e908f45ef9046194365de072e8adde70' , '10','998254','998254','3993016','455150','536870914','pierrottest','109bf5b0d918ef1f1a4a53a4f02e2526259e8acfbaf9109fcfd8b03ae71dfc70')");
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }


  public void updateBLOCKTableOptional(HashMap<String, Integer> salesForWeek) throws SQLException {

    PreparedStatement updateConfirmations = null;
    PreparedStatement updateDifficulty = null;

    String updateString =
      "update BLOCK " + "set confirmation = ? where hash = ?";

    String updateStatement =
      "update BLOCK " + "set difficulty = difficulty + ? where COF_NAME = ?";

    try {
      con.setAutoCommit(false);
      updateConfirmations = con.prepareStatement(updateString);
      updateDifficulty = con.prepareStatement(updateStatement);

      for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
        updateConfirmations.setInt(1, e.getValue().intValue());
        updateConfirmations.setString(2, e.getKey());
        updateConfirmations.executeUpdate();

        updateDifficulty.setInt(1, e.getValue().intValue());
        updateDifficulty.setString(2, e.getKey());
        updateDifficulty.executeUpdate();
        con.commit();
      }
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
      if (con != null) {
        try {
          System.err.print("Transaction is being rolled back");
          con.rollback();
        } catch (SQLException excep) {
          MySQLUtilities.printSQLException(excep);
        }
      }
    } finally {
      if (updateConfirmations != null) { updateConfirmations.close(); }
      if (updateDifficulty != null) { updateDifficulty.close(); }
      con.setAutoCommit(true);
    }
  }

  public void insertRow(Block block) throws SQLException 
  {
    Statement stmt = null;
    try {
      stmt =
          con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      ResultSet uprs = stmt.executeQuery("SELECT * FROM BLOCK");

      uprs.moveToInsertRow();

      uprs.updateString("hash", block.getHash());
      uprs.updateString("confirmations", block.getConfirmations());
      uprs.updateInt("strippedsize", block.getStrippedsize());
      uprs.updateString("size", block.getSize());
      uprs.updateString("weight", block.getWeight());
      uprs.updateInt("height", block.getHeight());
      uprs.updateString("version", block.getVersion());
      uprs.updateString("versionHex", block.getVersionHex());
      uprs.updateString("merkleroot", block.getMerkleroot());
//      uprs.updateString("tx", tx);
      
      uprs.updateString("time", block.getTime());
      uprs.updateString("mediantime", block.getMediantime());
      uprs.updateString("nonce", block.getNonce());
      uprs.updateString("bits", block.getBits());
      uprs.updateString("difficulty", block.getDifficulty());
      uprs.updateString("chainwork", block.getChainwork());
      uprs.updateString("previousblockhash", block.getPreviousblockhash());
      uprs.updateString("nextblockhash", block.getNextblockhash());
      
      //uprs.updateFloat("PRICE", price);
      
      uprs.insertRow();
      uprs.beforeFirst();

    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public void batchUpdate() throws SQLException {

    Statement stmt = null;
    try {

      this.con.setAutoCommit(false);
      stmt = this.con.createStatement();

      stmt.addBatch("INSERT INTO BLOCK " +"(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
              "VALUES ('0000000000000000021252bdd31542c06ed522ebef2bed1b3605d29b601c0818' , '266','998036','998036','3992144','453616','536870912','20000000','804560392e7417a08e93188200573a55fb1ec5e1d29ce36fb0b4d78ee4e74bde')");
      
      stmt.addBatch("INSERT INTO BLOCK " +"(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
              "VALUES ('0000000000000000021252bdd31542c06ed522ebef2bed1b3605d29b601c0819' , '267','998036','998036','3992144','453616','536870912','20000000','804560392e7417a08e93188200573a55fb1ec5e1d29ce36fb0b4d78ee4e74bde')");
      stmt.addBatch("INSERT INTO BLOCK " +"(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
              "VALUES ('0000000000000000021252bdd31542c06ed522ebef2bed1b3605d29b601c0820' , '268','998036','998036','3992144','453616','536870912','20000000','804560392e7417a08e93188200573a55fb1ec5e1d29ce36fb0b4d78ee4e74bde')");
      stmt.addBatch("INSERT INTO BLOCK " +"(hash, confirmations, strippedsize, size, weight, height,version, versionHex, merkleroot)"+
              "VALUES ('0000000000000000021252bdd31542c06ed522ebef2bed1b3605d29b601c0821' , '269','998036','998036','3992144','453616','536870912','20000000','804560392e7417a08e93188200573a55fb1ec5e1d29ce36fb0b4d78ee4e74bde')");
      
   
      int[] updateCounts = stmt.executeBatch();
      this.con.commit();

    } catch (BatchUpdateException b) {
      MySQLUtilities.printBatchUpdateException(b);
    } catch (SQLException ex) {
      MySQLUtilities.printSQLException(ex);
    } finally {
      if (stmt != null) { stmt.close(); }
      this.con.setAutoCommit(true);
    }
  }
  
  public static void viewTable(Connection con) throws SQLException {
    Statement stmt = null;
    String query = "select * from BLOCK";
    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
    	String hash = rs.getString("hash");
    	String confirmations = rs.getString("confirmations");
    	int strippedsize = rs.getInt("strippedsize");
    	String size = rs.getString("size");
    	String weight = rs.getString("weight");
    	int height = rs.getInt("height");
    	String version = rs.getString("version");
    	String versionHex = rs.getString("versionHex");
    	String merkleroot = rs.getString("merkleroot");
//    	String tx = rs.getString("tx");
    	String time = rs.getString("time");
    	String mediantime = rs.getString("mediantime");
    	String nonce = rs.getString("nonce");
    	String bits = rs.getString("bits");
    	String difficulty = rs.getString("difficulty");
    	String chainwork = rs.getString("chainwork");
    	String previousblockhash = rs.getString("previousblockhash");
    	String nextblockhash = rs.getString("nextblockhash");
    	
//    	float price = rs.getFloat("PRICE");
        System.out.println("Block#"+hash);
    	System.out.println(hash + ", " + confirmations + ", " + strippedsize +
                           ", " + size + ", " + weight+
                           ", " + height+", " + version+ 
                           ", " + versionHex+ ", " + merkleroot+
//                           ", " + tx+
                           ", " + time+", " + mediantime+", " + 
                           ", "+nonce+", "+bits+", "+difficulty+
                           ", "+chainwork+", "+previousblockhash+", "+nextblockhash);
      }

    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public static void alternateViewTable(Connection con) throws SQLException {
    Statement stmt = null;
    String query = "select * from BLOCK";
    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String hash = rs.getString(1);
        String confirmations = rs.getString(2);
        int strippedsize = rs.getInt(3);
        String size = rs.getString(4);
        String weight = rs.getString(5);
        int height = rs.getInt(6);
        String version = rs.getString(7);
        String versionHex = rs.getString(8);
        String merkleroot = rs.getString(9);
//      String tx = rs.getString(10);
        String time = rs.getString(11);
        String mediantime = rs.getString(12);
        String nonce = rs.getString(13);
        String bits = rs.getString(14);
        String difficulty = rs.getString(15);
        String chainwork = rs.getString(16);
        String previousblockhash = rs.getString(17);
        String nextblockhash = rs.getString(18);

        System.out.println(hash + ", " + confirmations + ", " + strippedsize +
        		          ", " +size + ", " + weight + ", " + height +
        		          ", " +version + ", " + versionHex + ", " + merkleroot +
//        		          ", " +tx+
			          ", " +time + ", " + mediantime + ", " + nonce +
			          ", " + bits + ", " + difficulty+", "+chainwork+
			          ", " + previousblockhash + ", " + nextblockhash);
      }
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }
  
  public Set<String> getKeys() throws SQLException {
    HashSet<String> keys = new HashSet<String>();
    Statement stmt = null;
    String query = "select hash from BLOCK";
    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        keys.add(rs.getString(1));
      }
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
    return keys;
    
  }


  public void dropTable() throws SQLException {
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      if (this.dbms.equals("mysql")) {
        stmt.executeUpdate("DROP TABLE IF EXISTS BLOCK");
      } 
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }

  public static void main(String[] args) {
    MySQLUtilities myMySQLUtilities;
    Connection myConnection = null;

    if (args[0] == null) {
      System.err.println("Properties file not specified at command line");
      return;
    } else {
      try {
        myMySQLUtilities = new MySQLUtilities(args[0]);
      } catch (Exception e) {
        System.err.println("Problem reading properties file " + args[0]);
        e.printStackTrace();
        return;
      }
    }

    try {
      myConnection = myMySQLUtilities.getConnection();


      BlockTable myBlockTable =
        new BlockTable(myConnection, myMySQLUtilities.dbName,
                         myMySQLUtilities.dbms);

      System.out.println("\nContents of BLOCK table:");
      BlockTable.viewTable(myConnection);

      System.out.println("\nPopulating BLOCK table with Test Data");
      myBlockTable.populateTable();
      
      Block block = new Block("000000000000000000c62e4871a59ba372dfb27c6fbc038b21ec9c4a96243259", 
				 "1",
				 998189,
				 "998189",
				 "3992756",
				 454641,
				 "536870914",
				 "20000002",
				 "60fad08048df5212cd1fd21ad60c87541a48e57bf832e9dab67c5d3dca752773",
				 "1488024093", 
				 "1488021960", 
				 "1963317726", 
				 "18027e93", 
				 "440779902286.5892", 
				 "00000000000000000000000000000000000000000041949f2bccb9048b88e888", 
				 "00000000000000000235fcb0dd78a6c5155ee1e207bc14e8b893bb1122604a87",
				 "0000000000000000003477efce8cecd5ce7c62f5768aee466ed8b8f9a236dbcc");
      

      System.out.println("\nInserting a new row in Table BLOCK:");
      myBlockTable.insertRow(block);

      
      BlockTable.viewTable(myConnection);


      System.out.println("\nPerforming batch updates; adding new BLOCKS");
      myBlockTable.batchUpdate();
      BlockTable.viewTable(myConnection);

//      System.out.println("\nDropping Block table:");
//      
//      myBlockTable.dropTable();

    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      MySQLUtilities.closeConnection(myConnection);
    }
  }
}
