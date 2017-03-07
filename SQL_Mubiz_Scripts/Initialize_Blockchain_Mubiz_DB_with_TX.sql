DROP DATABASE IF EXISTS Blockchain_MubizDB;
CREATE DATABASE Blockchain_MubizDB CHARACTER SET 'utf8';

use Blockchain_MubizDB;
SET NAMES utf8;

CREATE TABLE Block (
   hash VARCHAR(80) PRIMARY KEY, 
   confirmations VARCHAR(40),
   strippedsize INT,
   size VARCHAR(40),
   weight VARCHAR(40),
   height INT,
   version VARCHAR(40),
   versionHex VARCHAR(40),
   merkleroot VARCHAR(80),
   tx LONGTEXT,	
   time VARCHAR(40),
   mediantime VARCHAR(40),
   nonce VARCHAR(40),
   bits VARCHAR(40),
   difficulty VARCHAR(40),
   chainwork VARCHAR(80),
   previousblockhash VARCHAR(80),
   nextblockhash VARCHAR(80)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

describe block;