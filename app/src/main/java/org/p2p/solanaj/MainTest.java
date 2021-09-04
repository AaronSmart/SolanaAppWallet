package org.p2p.solanaj;

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

public class MainTest {
    public static void main(String[]args)
    {
        System.out.println("ok int");
    }
    public static void main2(String[]args) throws RpcException {
        List<String> memwords=new ArrayList<String>();
        memwords.add("pencil");
        memwords.add("comfort");
        memwords.add("black");
        memwords.add("humor");
        memwords.add("ritual");
        memwords.add("fuel");
        memwords.add("bounce");
        memwords.add("erase");
        memwords.add("member");
        memwords.add("situate");
        memwords.add("vicious");
        memwords.add("improve");
        Account account=Account.fromMnemonic(memwords,"");
        //2LNx8jHZFQVUphhGdPUzfDicFGg2APfw1D3Dk6fVnMk2xJQSTLbE3XDJcNimSt7JubR5z5NTHHHWYio5upi4JYX6
        PublicKey fromPublicKey = new PublicKey("Gwz377J8Uy5U3oQW81aYroLQSNJSJ8ZEDPyY1jexQfFz");
        PublicKey toPublickKey = new PublicKey("8gdimNnQB9XM7xWW5GSrvBFkdbRX17aL6AVcUV6BmAQR");

        //4vfLmAwY1dfypCyBkwz44xcmdpdGPBwEMsP6hmJPbj9ABrRnm99ZhC5CY1ixuoQEycyBteM3fg8pRiaXvToCzmQW
        //PublicKey fromPublicKey = new PublicKey("GY65d8UMdo2CWA5NjoTkmWu8rdwEzTHeGaimrgxUhmNH");
        //PublicKey toPublickKey = new PublicKey("EUABhhqXW3DfNWH8xj9k6JNUEbRkmecCsMErMZic64hD");

        int lamports = 1;


        PublicKey address = new PublicKey("9j22RV8FC25DPGg9wAMgpukz9C9WivxtQoiHF4s9578R");
        PublicKey owner = new PublicKey("8gdimNnQB9XM7xWW5GSrvBFkdbRX17aL6AVcUV6BmAQR");
        PublicKey mint = new PublicKey("3GECTP7H4Tww3w8jEPJCJtXUtXxiZty31S9szs84CcwQ");

        Account signer = new Account(Base58.decode("2LNx8jHZFQVUphhGdPUzfDicFGg2APfw1D3Dk6fVnMk2xJQSTLbE3XDJcNimSt7JubR5z5NTHHHWYio5upi4JYX6"));
        Transaction transaction = new Transaction();
        //transaction.addInstruction(SystemProgram.transfer(fromPublicKey, toPublickKey, lamports,false));
        transaction.addInstruction(SystemProgram.createAssociatedTokenAccount(address, owner,mint));
        final RpcClient client = new RpcClient(Cluster.MAINNET);
        String result= client.getApi().sendTransaction(transaction,signer,2);
        System.out.println(result);
    }

    public String transferSol(String fromPublicKey, String toPublickKey, String amount,String signerBase58String) throws RpcException {
        long decimal=1000000000L;
        //need to test
        long lamports= (long) (Double.parseDouble(amount)*decimal);
        PublicKey from = new PublicKey(fromPublicKey);
        PublicKey to = new PublicKey(toPublickKey);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(from, to, lamports,false));
        final RpcClient client = new RpcClient(Cluster.MAINNET);
        String result= client.getApi().sendTransaction(transaction,signer,3);
        return result;
    }
    public String transferSplToken(String fromPublicKey, String toPublickKey, String decimalStr,String amount,String signerBase58String) throws RpcException {
        long decimal=Long.parseLong(decimalStr);
        //need to test
        long lamports= (long) (Double.parseDouble(amount)*decimal);
        PublicKey from = new PublicKey(fromPublicKey);
        PublicKey to = new PublicKey(toPublickKey);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.transfer(from, to, lamports,true));
        final RpcClient client = new RpcClient(Cluster.MAINNET);
        String result= client.getApi().sendTransaction(transaction,signer,1);
        return result;
    }
    public String createAssociatedAccount(String address, String owner, String mint,String signerBase58String) throws RpcException {
        PublicKey mAddress = new PublicKey(address);
        PublicKey mOwner= new PublicKey(owner);
        PublicKey mMint = new PublicKey(mint);
        Account signer = new Account(Base58.decode(signerBase58String));
        Transaction transaction = new Transaction();
        transaction.addInstruction(SystemProgram.createAssociatedTokenAccount(mAddress, mOwner,mMint));
        final RpcClient client = new RpcClient(Cluster.MAINNET);
        String result= client.getApi().sendTransaction(transaction,signer,2);
        return result;
    }
}
