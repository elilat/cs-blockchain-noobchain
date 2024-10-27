package noobchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class MyNoobChain {
    // Blockchain storage
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    // All unspent transactions
    public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
    
    // Mining difficulty and minimum transaction amount
    public static int difficulty = 5;
    public static float minimumTransaction = 0.1f;
    
    // Wallet to create initial coins
    public static Wallet coinbase;
    public static Transaction genesisTransaction;
    
    // Create all wallet objects as static variables for easy access
    public static Wallet walletA1, walletA2;  // User1's wallets
    public static Wallet walletB1, walletB2;  // User2's wallets
    public static Wallet walletC1, walletC2, walletC3;  // User3's wallets
    
    public static void main(String[] args) {
        // Setup Bouncy Castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        // Create users
        BCUser user1 = new BCUser("User1", "user1@email.com", "Address1", "1234567890");
        BCUser user2 = new BCUser("User2", "user2@email.com", "Address2", "2345678901");
        BCUser user3 = new BCUser("User3", "user3@email.com", "Address3", "3456789012");
        
        // Create and assign wallets to users
        walletA1 = new Wallet();
        walletA2 = new Wallet();
        user1.addWallet(walletA1);
        user1.addWallet(walletA2);
        
        walletB1 = new Wallet();
        walletB2 = new Wallet();
        user2.addWallet(walletB1);
        user2.addWallet(walletB2);
        
        walletC1 = new Wallet();
        walletC2 = new Wallet();
        walletC3 = new Wallet();
        user3.addWallet(walletC1);
        user3.addWallet(walletC2);
        user3.addWallet(walletC3);
        
        // Create Genesis Transaction
        coinbase = new Wallet();
        genesisTransaction = new Transaction(coinbase.publicKey, walletA1.publicKey, 1000f, null);
        genesisTransaction.generateSignature(coinbase.privateKey);
        genesisTransaction.transactionId = "0"; // Manually set the transaction id
        
        // Manually add the genesis transaction output
        genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, 
                genesisTransaction.value, genesisTransaction.transactionId));
        UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
        
        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis);
        printBalances("After Genesis Transaction");
        
        // Block 1 Transactions
        Block block1 = new Block(genesis.hash);
        System.out.println("\nWalletA1's balance is: " + walletA1.getBalance());
        
        // Transaction 1: WalletA1 sends 330 to WalletB1
        System.out.println("\nTransaction 1: WalletA1 sending 330 to WalletB1...");
        block1.addTransaction(walletA1.sendFunds(walletB1.publicKey, 330f));
        printBalances("After Transaction 1");
        
        // Transaction 2: WalletA1 sends 330 to WalletC1
        System.out.println("\nTransaction 2: WalletA1 sending 330 to WalletC1...");
        block1.addTransaction(walletA1.sendFunds(walletC1.publicKey, 330f));
        addBlock(block1);
        printBalances("After Transaction 2");
        
        // Block 2 Transactions
        Block block2 = new Block(block1.hash);
        
        // Transaction 3: WalletA1 sends 110 to WalletB2
        System.out.println("\nTransaction 3: WalletA1 sending 110 to WalletB2...");
        block2.addTransaction(walletA1.sendFunds(walletB2.publicKey, 110f));
        printBalances("After Transaction 3");
        
        // Transaction 4: WalletB1 sends 150 to WalletC2
        System.out.println("\nTransaction 4: WalletB1 sending 150 to WalletC2...");
        block2.addTransaction(walletB1.sendFunds(walletC2.publicKey, 150f));
        printBalances("After Transaction 4");
        
        // Transaction 5: WalletC1 sends 150 to WalletA2
        System.out.println("\nTransaction 5: WalletC1 sending 150 to WalletA2...");
        block2.addTransaction(walletC1.sendFunds(walletA2.publicKey, 150f));
        printBalances("After Transaction 5");
        
        // Transaction 6: WalletA1 sends 110 to WalletC3
        System.out.println("\nTransaction 6: WalletA1 sending 110 to WalletC3...");
        block2.addTransaction(walletA1.sendFunds(walletC3.publicKey, 110f));
        addBlock(block2);
        printBalances("After Transaction 6");
        
        // Block 3 Transactions
        Block block3 = new Block(block2.hash);
        
        // Transaction 7: User2 sends 200 from multiple wallets to WalletA1
        System.out.println("\nTransaction 7: User2 sending 200 from multiple wallets to WalletA1...");
        block3.addTransaction(user2.sendFundsFromMultipleWallets(walletA1.publicKey, 200f));
        printBalances("After Transaction 7");
        
        // Transaction 8: User3 sends 350 from multiple wallets to WalletB2
        System.out.println("\nTransaction 8: User3 sending 350 from multiple wallets to WalletB2...");
        block3.addTransaction(user3.sendFundsFromMultipleWallets(walletB2.publicKey, 350f));
        addBlock(block3);
        printBalances("After Transaction 8");
        
        // Verify blockchain integrity
        System.out.println("\nBlockchain is Valid: " + isChainValid());
    }
    
    // Add new block to blockchain after mining
    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }
    
    // Check if blockchain is valid
    public static Boolean isChainValid() {
        Block currentBlock; 
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>();
        tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
        
        // Loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            
            // Compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ) {
                System.out.println("#Current Hashes not equal");
                return false;
            }
            
            // Compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            
            // Check if hash is solved
            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("#This block hasn't been mined");
                return false;
            }
            
            // Loop through blockchain transactions:
            TransactionOutput tempOutput;
            for(int t=0; t < currentBlock.transactions.size(); t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);
                
                // Verify signature
                if(!currentTransaction.verifySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }
                
                // Verify inputs equal outputs
                if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
                    System.out.println("#Inputs are not equal to outputs on Transaction(" + t + ")");
                    return false;
                }
                
                // Check each input in transaction
                for(TransactionInput input: currentTransaction.inputs) {
                    tempOutput = tempUTXOs.get(input.transactionOutputId);
                    
                    if(tempOutput == null) {
                        System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
                        return false;
                    }
                    
                    if(input.UTXO.value != tempOutput.value) {
                        System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
                        return false;
                    }
                    
                    tempUTXOs.remove(input.transactionOutputId);
                }
                
                // Add outputs to temporary UTXO list
                for(TransactionOutput output: currentTransaction.outputs) {
                    tempUTXOs.put(output.id, output);
                }
                
                // Check if transaction output recipients are correct
                if(currentTransaction.outputs.get(0).recipient != currentTransaction.recipient) {
                    System.out.println("#Transaction(" + t + ") output recipient is not who it should be");
                    return false;
                }
                if(currentTransaction.outputs.get(1).recipient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }
            }
        }
        return true;
    }
    
    // Print balances of all wallets and users
    private static void printBalances(String message) {
        System.out.println("\n" + message + ":");
        System.out.println("WalletA1 balance: " + walletA1.getBalance());
        System.out.println("WalletA2 balance: " + walletA2.getBalance());
        System.out.println("WalletB1 balance: " + walletB1.getBalance());
        System.out.println("WalletB2 balance: " + walletB2.getBalance());
        System.out.println("WalletC1 balance: " + walletC1.getBalance());
        System.out.println("WalletC2 balance: " + walletC2.getBalance());
        System.out.println("WalletC3 balance: " + walletC3.getBalance());
        
        // Print user total balances
        float user1Balance = walletA1.getBalance() + walletA2.getBalance();
        float user2Balance = walletB1.getBalance() + walletB2.getBalance();
        float user3Balance = walletC1.getBalance() + walletC2.getBalance() + walletC3.getBalance();
        
        System.out.println("\nUser balances:");
        System.out.println("User1 total balance: " + user1Balance);
        System.out.println("User2 total balance: " + user2Balance);
        System.out.println("User3 total balance: " + user3Balance);
    }
}