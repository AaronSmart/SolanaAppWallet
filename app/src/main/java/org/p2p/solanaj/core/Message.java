package org.p2p.solanaj.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.bitcoinj.core.Base58;

import org.p2p.solanaj.utils.ShortvecEncoding;

public class Message {
    private class MessageHeader {
        static final int HEADER_LENGTH = 3;

        byte numRequiredSignatures = 0;
        byte numReadonlySignedAccounts = 0;
        byte numReadonlyUnsignedAccounts = 0;

        byte[] toByteArray() {
            return new byte[] { numRequiredSignatures, numReadonlySignedAccounts, numReadonlyUnsignedAccounts };
        }
    }

    private class CompiledInstruction {
        byte programIdIndex;
        byte[] keyIndicesCount;
        byte[] keyIndices;
        byte[] dataLength;
        byte[] data;

        int getLength() {
            // 1 = programIdIndex length
            return 1 + keyIndicesCount.length + keyIndices.length + dataLength.length + data.length;
        }
    }

    private static final int RECENT_BLOCK_HASH_LENGTH = 32;

    private MessageHeader messageHeader;
    private String recentBlockhash;
    private AccountKeysList accountKeys;
    private List<TransactionInstruction> instructions;
    private Account feePayer;

    public Message() {
        this.accountKeys = new AccountKeysList();
        this.instructions = new ArrayList<TransactionInstruction>();
    }

    public Message addInstruction(TransactionInstruction instruction) {
        accountKeys.addAll(instruction.getKeys());
        accountKeys.add(new AccountMeta(instruction.getProgramId(), false, false));
        instructions.add(instruction);

        return this;
    }

    public void setRecentBlockHash(String recentBlockhash) {
        this.recentBlockhash = recentBlockhash;
    }

    public byte[] serialize(int flag) {

        if (recentBlockhash == null) {
            throw new IllegalArgumentException("recentBlockhash required");
        }

        if (instructions.size() == 0) {
            throw new IllegalArgumentException("No instructions provided");
        }

        messageHeader = new MessageHeader();
        List<AccountMeta> keysList=null;
        if(3==flag)//special check for transfer sol
        {
            keysList= getAccountKeysForSol();
        }else
        {
            keysList= getAccountKeys();
        }

        int accountKeysSize = keysList.size();

        byte[] accountAddressesLength = ShortvecEncoding.encodeLength(accountKeysSize);

        int compiledInstructionsLength = 0;
        List<CompiledInstruction> compiledInstructions = new ArrayList<CompiledInstruction>();
        System.out.println("=================================指令个数=============="+instructions.size());
        for (TransactionInstruction instruction : instructions) {
            System.out.println("===============================================进入指令区");
            int keysSize = instruction.getKeys().size();
            int keyLength;
            byte[] keyIndices;
            if(1==flag) //spl token
            {
                keyLength=keysSize+1;
                keyIndices = new byte[keysSize+1];//add fee payer to the last [transfer spl token]
                keyIndices[keysSize]=(byte) findAccountIndex(keysList,feePayer.getPublicKey());
                for (int i = 0; i < keysSize; i++) {
                    System.out.println("===============================================进入keyindices区");
                    keyIndices[i] = (byte) findAccountIndex(keysList, instruction.getKeys().get(i).getPublicKey());
                    System.out.println("===============================================离开keyindices区");
                }

            }else if(2==flag) //associated account
            {
                keyLength=keysSize+1;
                keyIndices = new byte[keysSize+1];//add fee payer to the first [create associated account]
                keyIndices[0]=(byte) findAccountIndex(keysList,feePayer.getPublicKey());
                for (int i = 0; i < keysSize; i++) {
                    System.out.println("===============================================进入keyindices区");
                    keyIndices[i+1] = (byte) findAccountIndex(keysList, instruction.getKeys().get(i).getPublicKey());
                    System.out.println("===============================================离开keyindices区");
                }
            }
            else  //sol
            {
                keyLength=keysSize;   // transfer sol  no need fee payer
                keyIndices = new byte[keysSize];
                for (int i = 0; i < keysSize; i++) {
                    System.out.println("===============================================进入keyindices区");
                    keyIndices[i] = (byte) findAccountIndex(keysList, instruction.getKeys().get(i).getPublicKey());
                    System.out.println("===============================================离开keyindices区");
                }
            }
            CompiledInstruction compiledInstruction = new CompiledInstruction();
            System.out.println("===============================================进入programIdIndex区");
            compiledInstruction.programIdIndex = (byte) findAccountIndex(keysList, instruction.getProgramId());
            System.out.println("===============================================compiledInstruction.programIdIndex"+compiledInstruction.programIdIndex);
            System.out.println("===============================================进入programIdIndex区");
            compiledInstruction.keyIndicesCount = ShortvecEncoding.encodeLength(keyLength);
            compiledInstruction.keyIndices = keyIndices;
            compiledInstruction.dataLength = ShortvecEncoding.encodeLength(instruction.getData().length);
            compiledInstruction.data = instruction.getData();

            compiledInstructions.add(compiledInstruction);

            compiledInstructionsLength += compiledInstruction.getLength();
        }

        byte[] instructionsLength = ShortvecEncoding.encodeLength(compiledInstructions.size());

        int bufferSize = MessageHeader.HEADER_LENGTH + RECENT_BLOCK_HASH_LENGTH + accountAddressesLength.length
                + (accountKeysSize * PublicKey.PUBLIC_KEY_LENGTH) + instructionsLength.length
                + compiledInstructionsLength;

        ByteBuffer out = ByteBuffer.allocate(bufferSize);

        ByteBuffer accountKeysBuff = ByteBuffer.allocate(accountKeysSize * PublicKey.PUBLIC_KEY_LENGTH);
        for (AccountMeta accountMeta : keysList) {
            accountKeysBuff.put(accountMeta.getPublicKey().toByteArray());
            System.out.println("=======================no good=================================="+accountMeta.getPublicKey().toByteArray().length+" "+accountMeta.getPublicKey().toBase58());

            if (accountMeta.isSigner()) {
                messageHeader.numRequiredSignatures += 1;
                if (!accountMeta.isWritable()) {
                    messageHeader.numReadonlySignedAccounts += 1;
                }
            } else {
                if (!accountMeta.isWritable()) {
                    messageHeader.numReadonlyUnsignedAccounts += 1;
                }
            }
        }

        System.out.println("============================header====================="+messageHeader.numRequiredSignatures);
        System.out.println("============================header====================="+messageHeader.numReadonlySignedAccounts);
        System.out.println("============================header====================="+messageHeader.numReadonlyUnsignedAccounts);
        out.put(messageHeader.toByteArray());

        out.put(accountAddressesLength);
        out.put(accountKeysBuff.array());

        out.put(Base58.decode(recentBlockhash));

        out.put(instructionsLength);
        for (CompiledInstruction compiledInstruction : compiledInstructions) {
            out.put(compiledInstruction.programIdIndex);
            out.put(compiledInstruction.keyIndicesCount);
            out.put(compiledInstruction.keyIndices);
            out.put(compiledInstruction.dataLength);
            out.put(compiledInstruction.data);
        }

        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        byte[] result=bytebuffer2ByteArray(out);
        for(int i=0;i<result.length;i++)
        {
            System.out.print((result[i]& 0xff)+" ");
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        return out.array();
    }
    public static byte[] bytebuffer2ByteArray(ByteBuffer buffer) {
        //重置 limit 和postion 值
        buffer.flip();
        //获取buffer中有效大小
        int len=buffer.limit() - buffer.position();

        byte [] bytes=new byte[len];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i]=buffer.get();

        }

        return bytes;
    }

    protected void setFeePayer(Account feePayer) {
        this.feePayer = feePayer;
    }

    //this is no need specified fee payer a little different from others
    private List<AccountMeta> getAccountKeysForSol() {
        List<AccountMeta> keysList = accountKeys.getList();
        int feePayerIndex = findAccountIndex(keysList, feePayer.getPublicKey());
        List<AccountMeta> newList = new ArrayList<AccountMeta>();
        AccountMeta feePayerMeta = keysList.get(feePayerIndex);
        newList.add(new AccountMeta(feePayerMeta.getPublicKey(), true, true));
        keysList.remove(feePayerIndex);
        newList.addAll(keysList);
        return newList;
    }

    private List<AccountMeta> getAccountKeys() {
        List<AccountMeta> keysList = new ArrayList<>();
        keysList.add(new AccountMeta(feePayer.getPublicKey(), true, true));
        keysList.addAll(accountKeys.getList());
        return keysList;
    }
    private List<AccountMeta> getAccountKeysForTest() {
        List<AccountMeta> keysList = new ArrayList<>();
        keysList.add(new AccountMeta(feePayer.getPublicKey(), true, true));
        keysList.add(new AccountMeta(new PublicKey("FD9Q9FsZxjunLqvSjLAFTghg17qQNvQEvi3uSxJBYkWp"), false, true));
        keysList.add(new AccountMeta(new PublicKey("8gdimNnQB9XM7xWW5GSrvBFkdbRX17aL6AVcUV6BmAQR"), false, false));
        keysList.add(new AccountMeta(new PublicKey("kinXdEcpDQeHPEuQnqmUgtYykqKGVFq6CeVX5iAHJq6"), false, false));
        keysList.add(new AccountMeta(new PublicKey("11111111111111111111111111111111"), false, false));
        keysList.add(new AccountMeta(new PublicKey("TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA"), false, false));
        keysList.add(new AccountMeta(new PublicKey("SysvarRent111111111111111111111111111111111"), false, false));
        keysList.add(new AccountMeta(new PublicKey("ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL"), false, false));
        return keysList;
    }
    private int findAccountIndex(List<AccountMeta> accountMetaList, PublicKey key) {
        for (int i = 0; i < accountMetaList.size(); i++) {
            if (accountMetaList.get(i).getPublicKey().equals(key)) {
                return i;
            }
        }
        throw new RuntimeException("unable to find account index");
    }
}
