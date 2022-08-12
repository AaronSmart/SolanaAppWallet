package com.kikenn.ed25519;

public class KeyData {
    byte[] key;

    public KeyData(byte[] key, byte[] chainCode) {
        this.key = key;
        this.chainCode = chainCode;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getChainCode() {
        return chainCode;
    }

    public void setChainCode(byte[] chainCode) {
        this.chainCode = chainCode;
    }

    byte[] chainCode;
}
