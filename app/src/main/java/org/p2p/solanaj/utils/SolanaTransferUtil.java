package org.p2p.solanaj.utils;

import com.econet.app.dictionary.Constant;

import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.programs.SystemProgram;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.RpcException;

import java.util.ArrayList;
import java.util.List;

public class SolanaTransferUtil {
    public static void main(String[]args)
    {
        System.out.println("ok int");
    }

    public static String transferSol(String fromPublicKey, String toPublickKey, String amount,String signerBase58String) throws RpcException {
        long decimal=1000000000L;
        long lamport= (long) (Double.parseDouble(amount)*decimal);
        PublicKey from = new PublicKey(fromPublicKey);
        PublicKey to = new PublicKey(toPublickKey);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(from, to, lamport,false));
        final RpcClient client = new RpcClient(Constant.Solana.solMainNet);
        String result= client.getApi().sendTransaction(transaction,signer,3);
        return result;
    }
    public static String transferSplToken(String fromPublicKey, String toPublickKey, String decimal,String amount,String signerBase58String) throws RpcException {
        int tmp=Integer.parseInt(decimal);
        double pow= Math.pow(10, tmp);
        long lamport= (long) (Double.parseDouble(amount)*pow);
        PublicKey from = new PublicKey(fromPublicKey);
        PublicKey to = new PublicKey(toPublickKey);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(from, to, lamport,true));
        final RpcClient client = new RpcClient(Constant.Solana.solMainNet);
        String result= client.getApi().sendTransaction(transaction,signer,1);
        return result;
    }
    public static String createAssociatedAccount(String address, String owner, String mint,String signerBase58String) throws RpcException {
        PublicKey mAddress = new PublicKey(address);
        PublicKey mOwner= new PublicKey(owner);
        PublicKey mMint = new PublicKey(mint);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.createAssociatedTokenAccount(mAddress, mOwner,mMint));
        final RpcClient client = new RpcClient(Constant.Solana.solMainNet);
        String result= client.getApi().sendTransaction(transaction,signer,2);
        return result;
    }

    public static String createMemo(String memoString, String signerBase58String)throws RpcException {
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.createMemo(memoString));
        final RpcClient client = new RpcClient(Constant.Solana.solMainNet);
        String result= client.getApi().sendTransaction(transaction,signer,4);
        return result;
    }
    //create the
    public static String createAnchor(String memoString, String signerBase58String)throws RpcException {
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(AnchorBasicTutorialProgram.initialize(signer));
        final RpcClient client = new RpcClient(Constant.Solana.solMainTestNet);
        String result= client.getApi().sendTransaction(transaction,signer,4);
        return result;
    }
    /**
     *
     * @param address  the destination associated account
     * @param owner    the owner account
     * @param mint     the mint of token address
     * @param fromPublicKey
     * @param toPublickKey
     * @param decimal
     * @param amount
     * @param signerBase58String
     * @return
     */

    public static String createAssociatedAccountAndTransferSplToken(String address, String owner, String mint,String fromPublicKey, String toPublickKey, String decimal,String amount,String signerBase58String)throws RpcException {
        PublicKey mAddress = new PublicKey(address);
        PublicKey mOwner= new PublicKey(owner);
        PublicKey mMint = new PublicKey(mint);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction(true);
        transaction.addInstruction(SystemProgram.createAssociatedTokenAccount(mAddress, mOwner,mMint));
        //the send transaction
        int tmp=Integer.parseInt(decimal);
        double pow= Math.pow(10, tmp);
        long lamport= (long) (Double.parseDouble(amount)*pow);
        PublicKey from = new PublicKey(fromPublicKey);
        PublicKey to = new PublicKey(toPublickKey);
        transaction.addInstruction(SystemProgram.transfer(from, to, lamport,true));
        //the rpc client
        final RpcClient client = new RpcClient(Constant.Solana.solMainNet);
        String result= client.getApi().sendTransaction(transaction,signer,2);
        return result;
    }

}
