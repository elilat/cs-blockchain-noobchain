# NoobChain Documentation

## 1. Introduction
NoobChain is an educational blockchain implementation that demonstrates key concepts in blockchain technology, including transaction processing, mining, and cryptographic security. The system implements a basic cryptocurrency with multi-wallet support, designed to help understand blockchain fundamentals.

## 2. System Architecture

### 2.1 Core Components
- **User Management**: BCUser class managing multiple wallets
- **Wallet System**: Public/private key pairs and balance management
- **Transaction Processing**: UTXO model implementation
- **Block Creation**: Mining and transaction verification
- **Blockchain**: Chain of blocks with integrity verification

### 2.2 Transaction Flow
1. User initiates transaction
2. System collects UTXOs from sender's wallets
3. Transaction is signed with sender's private key
4. Transaction is added to a block
5. Block is mined using proof-of-work
6. Transaction outputs become new UTXOs

## 3. Implementation Details

### 3.1 User and Wallet Management
```java
public class BCUser {
    public String name;
    public ArrayList<Wallet> wallets;
    // User management functionality
}

public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;
    // Wallet functionality
}
```

### 3.2 Transaction System
```java
public class Transaction {
    public String transactionId;
    public PublicKey sender;
    public PublicKey recipient;
    public float value;
    public byte[] signature;
    // Transaction processing
}
```

### 3.3 Block Structure
```java
public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions;
    // Block functionality
}
```

## 4. Security Implementation

### 4.1 Cryptographic Elements
- ECDSA for digital signatures
- SHA-256 for hashing
- Merkle trees for transaction verification

### 4.2 Transaction Validation
- Signature verification
- UTXO validation
- Double-spending prevention
- Balance verification

## 5. Features

### 5.1 Multi-Wallet Support
- Multiple wallets per user
- Combined balance tracking
- Aggregated UTXO management

### 5.2 Mining System
- Proof-of-work implementation
- Difficulty adjustment
- Block validation

### 5.3 Transaction Types
- Single-wallet transactions
- Multi-wallet transactions
- Genesis block creation

## 6. Usage Guidelines

### 6.1 Setup
```java
// Initialize the blockchain
ArrayList<Block> blockchain = new ArrayList<Block>();
HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();

// Create users and wallets
BCUser user = new BCUser("username", "email", "address", "phone");
Wallet wallet = new Wallet();
user.addWallet(wallet);
```

### 6.2 Creating Transactions
```java
// Single wallet transaction
Transaction transaction = wallet.sendFunds(recipient.publicKey, value);

// Multi-wallet transaction
Transaction transaction = user.sendFundsFromMultipleWallets(recipient.publicKey, value);
```

### 6.3 Mining Blocks
```java
Block block = new Block(previousHash);
block.addTransaction(transaction);
block.mineBlock(difficulty);
```

## 7. Testing
The system includes a comprehensive test scenario demonstrating:
- User creation
- Wallet management
- Transaction processing
- Balance verification
- Blockchain integrity

## 8. Conclusion
NoobChain provides a foundational understanding of blockchain technology through practical implementation. While not suitable for production use, it serves as an excellent educational tool for understanding blockchain concepts and cryptocurrency systems.

For implementation examples and usage scenarios, refer to the MyNoobChain.java main class.