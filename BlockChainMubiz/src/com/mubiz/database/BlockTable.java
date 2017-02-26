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
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
      
    		
//      "create table COFFEES " + "(COF_NAME varchar(32) NOT NULL, " +
//      "SUP_ID int NOT NULL, " + "PRICE numeric(10,2) NOT NULL, " +
//      "SALES integer NOT NULL, " + "TOTAL integer NOT NULL, " +
//      "PRIMARY KEY (COF_NAME), " +
//      "FOREIGN KEY (SUP_ID) REFERENCES SUPPLIERS (SUP_ID))";
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
      stmt.executeUpdate("insert into COFFEES " +
                         "values('Colombian', 00101, 7.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('French_Roast', 00049, 8.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('Espresso', 00150, 9.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('Colombian_Decaf', 00101, 8.99, 0, 0)");
      stmt.executeUpdate("insert into COFFEES " +
                         "values('French_Roast_Decaf', 00049, 9.99, 0, 0)");
    } catch (SQLException e) {
      MySQLUtilities.printSQLException(e);
    } finally {
      if (stmt != null) { stmt.close(); }
    }
  }


  public void updateBLOCKTableOptional(HashMap<String, Integer> salesForWeek) throws SQLException {

    PreparedStatement updateSales = null;
    PreparedStatement updateTotal = null;

    String updateString =
      "update BLOCK " + "set SALES = ? where COF_NAME = ?";

    String updateStatement =
      "update BLOCK " + "set TOTAL = TOTAL + ? where COF_NAME = ?";

    try {
      con.setAutoCommit(false);
      updateSales = con.prepareStatement(updateString);
      updateTotal = con.prepareStatement(updateStatement);

      for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
        updateSales.setInt(1, e.getValue().intValue());
        updateSales.setString(2, e.getKey());
        updateSales.executeUpdate();

        updateTotal.setInt(1, e.getValue().intValue());
        updateTotal.setString(2, e.getKey());
        updateTotal.executeUpdate();
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
      if (updateSales != null) { updateSales.close(); }
      if (updateTotal != null) { updateTotal.close(); }
      con.setAutoCommit(true);
    }
  }

  public void insertRow(String hash, 
		  				String confirmation,
		  				int strippedsize,
		  				String size , 
	    				String weight,
	    				int height , 
	    				String version , 
	    				String versionHex , 
	    				String merkleroot , 
//	    				String tx, 
	    				String time , 
	    				String mediantime , 
	    				String nonce , 
	    				String bits , 
	    				String difficulty , 
	    				String chainwork , 
	    				String previousblockhash , 
	    				String nextblockhash) throws SQLException 
  {
    Statement stmt = null;
    try {
      stmt =
          con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      ResultSet uprs = stmt.executeQuery("SELECT * FROM BLOCK");

      uprs.moveToInsertRow();

      uprs.updateString("hash", hash);
      uprs.updateString("confirmations", confirmation);
      uprs.updateInt("strippedsize", strippedsize);
      uprs.updateString("size", size);
      uprs.updateString("weight", weight);
      uprs.updateInt("height", height);
      uprs.updateString("version", version);
      uprs.updateString("versionHex", versionHex);
      uprs.updateString("merkleroot", merkleroot);
//      uprs.updateString("tx", tx);
      
      uprs.updateString("time", time);
      uprs.updateString("mediantime", mediantime);
      uprs.updateString("nonce", nonce);
      uprs.updateString("bits", bits);
      uprs.updateString("difficulty", difficulty);
      uprs.updateString("chainwork", chainwork);
      uprs.updateString("previousblockhash", previousblockhash);
      uprs.updateString("nextblockhash", nextblockhash);
      
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
      
//      stmt.addBatch("INSERT INTO COFFEES " +
//                    "VALUES('Hazelnut', 49, 9.99, 0, 0)");
   
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
    String query = "select * from COFFEES";
    try {
      stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        String coffeeName = rs.getString(1);
        int supplierID = rs.getInt(2);
        float price = rs.getFloat(3);
        int sales = rs.getInt(4);
        int total = rs.getInt(5);
        System.out.println(coffeeName + ", " + supplierID + ", " + price +
                           ", " + sales + ", " + total);
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

      // Java DB does not have an SQL create database command; it does require createDatabase
//      MySQLUtilities.createDatabase(myConnection,
//                                           myMySQLUtilities.dbName,
//                                           myMySQLUtilities.dbms);
//
//      MySQLUtilities.initializeTables(myConnection,
//                                             myMySQLUtilities.dbName,
//                                             myMySQLUtilities.dbms);

      BlockTable myBlockTable =
        new BlockTable(myConnection, myMySQLUtilities.dbName,
                         myMySQLUtilities.dbms);

      System.out.println("\nContents of BLOCK table:");
      BlockTable.viewTable(myConnection);

//      System.out.println("\nRaising coffee prices by 25%");
//      myCoffeeTable.modifyPrices(1.25f);
//
      System.out.println("\nInserting a new row in Table BLOCK:");
      myBlockTable.insertRow("000000000000000000c62e4871a59ba372dfb27c6fbc038b21ec9c4a96243259", 
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

      
      BlockTable.viewTable(myConnection);

//      System.out.println("\nUpdating sales of coffee per week:");
//      HashMap<String, Integer> salesCoffeeWeek =
//        new HashMap<String, Integer>();
//      salesCoffeeWeek.put("Colombian", 175);
//      salesCoffeeWeek.put("French_Roast", 150);
//      salesCoffeeWeek.put("Espresso", 60);
//      salesCoffeeWeek.put("Colombian_Decaf", 155);
//      salesCoffeeWeek.put("French_Roast_Decaf", 90);
//      myCoffeeTable.updateBLOCKales(salesCoffeeWeek);
//      BlockTable.viewTable(myConnection);
//
//      System.out.println("\nModifying prices by percentage");
//
//      myCoffeeTable.modifyPricesByPercentage("Colombian", 0.10f, 9.00f);
//      
//      System.out.println("\nBLOCK table after modifying prices by percentage:");
//      
//      BlockTable.viewTable(myConnection);

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
