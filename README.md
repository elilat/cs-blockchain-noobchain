# NoobChain: Sample Blockchain Implementation

## Overview
This project implements a basic cryptocurrency blockchain system using Java. It features a multi-wallet architecture, transaction management, and proof-of-work mining. The implementation includes user management, wallet creation, transaction validation, and blockchain integrity verification. This project demonstrates fundamental concepts in blockchain technology, cryptography, and distributed systems. It was developed as part of the Blockchain Systems course in the MS Computer Science program at the Lebanese American University (LAU).

## Features
- Multi-wallet support per user
- Proof-of-work mining system
- Transaction validation and verification
- Public/private key cryptography using ECDSA
- UTXO (Unspent Transaction Output) model
- Merkle root implementation
- Blockchain integrity verification
- Support for multi-wallet transactions
- Balance tracking and verification

## Requirements
- Java 8+
- Eclipse IDE (or any Java IDE)
- Required Libraries:
  - Bouncy Castle (`bcprov-jdk15on-159.jar`) for cryptography
  - GSON (`gson-2.6.2.jar`) for JSON handling

## Installation

1. Ensure you have Java 8+ installed:
   ```sh
   java -version
   ```

2. Clone the repository:
   ```sh
   git clone https://github.com/ellisisaac/cs-blockchain-noobchain.git
   cd cs-blockchain-noobchain
   ```

3. Import required libraries:
   - Download Bouncy Castle [`bcprov-jdk15on-159.jar`](https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on/1.59)
   - Download GSON [`gson-2.6.2.jar`](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/)
   - Add both as user libraries in Eclipse

## Project Structure
- `BCUser.java`: Manages users and their multiple wallets
- `Wallet.java`: Handles cryptocurrency storage and transactions
- `Block.java`: Implements blockchain blocks with mining
- `Transaction.java`: Manages cryptocurrency transfers
- `TransactionInput.java`: Handles transaction inputs
- `TransactionOutput.java`: Manages transaction outputs
- `StringUtil.java`: Provides cryptographic utilities
- `MyNoobChain.java`: Main class demonstrating functionality

## Usage Example
```java
// Create users and wallets
BCUser user1 = new BCUser("User1", "user1@email.com", "Address1", "1234567890");
Wallet walletA1 = new Wallet();
user1.addWallet(walletA1);

// Create and process transactions
Block genesisBlock = new Block("0");
Transaction genesisTransaction = new Transaction(coinbase.publicKey, walletA1.publicKey, 1000f, null);
genesisBlock.addTransaction(genesisTransaction);

// Mine blocks
genesisBlock.mineBlock(difficulty);
```

## Security Features
- ECDSA key pairs for transaction signing
- SHA-256 hashing for block mining
- Proof-of-work implementation
- Transaction validation
- Blockchain integrity verification
- UTXO model for double-spending prevention

## Limitations
- Educational implementation - not for production use
- In-memory storage only
- Basic proof-of-work difficulty
- Limited transaction types
- No network implementation
- No persistence layer

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
Special thanks to Dr. Khaleel Mershad of the Computer Science department at the Lebanese American University (LAU) for his guidance throughout this project.

## Author
Ellis Isaac @ellisisaac

For detailed information about the project's architecture and implementation, please refer to the DOCUMENTATION.md file.