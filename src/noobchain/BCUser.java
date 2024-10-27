package noobchain;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.security.PublicKey;

public class BCUser {
    public String name;
    public String email;
    public String address;
    public String phone;
    public ArrayList<Wallet> wallets;
    
    public BCUser(String name, String email, String address, String phone) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.wallets = new ArrayList<Wallet>();
    }
    
    public void addWallet(Wallet wallet) {
        wallets.add(wallet);
    }
    
    public float getTotalBalance() {
        float total = 0;
        for(Wallet wallet : wallets) {
            total += wallet.getBalance();
        }
        return total;
    }
    
    public Transaction sendFundsFromMultipleWallets(PublicKey recipient, float value) {
        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        float total = 0;
        Wallet primaryWallet = null;
        
        // Collect UTXOs from all wallets
        for(Wallet wallet : wallets) {
            if(primaryWallet == null) primaryWallet = wallet;
            
            for (Entry<String, TransactionOutput> item: wallet.UTXOs.entrySet()) {
                TransactionOutput UTXO = item.getValue();
                total += UTXO.value;
                inputs.add(new TransactionInput(UTXO.id));
                if(total >= value) break;
            }
            if(total >= value) break;
        }
        
        if(total < value) {
            System.out.println("#Not Enough funds across all wallets to send transaction. Transaction Discarded.");
            return null;
        }
        
        Transaction newTransaction = new Transaction(primaryWallet.publicKey, recipient, value, inputs);
        newTransaction.generateSignature(primaryWallet.privateKey);
        
        // Remove spent UTXOs from wallets
        for(Wallet wallet : wallets) {
            for(TransactionInput input: inputs){
                wallet.UTXOs.remove(input.transactionOutputId);
            }
        }
        
        return newTransaction;
    }
}