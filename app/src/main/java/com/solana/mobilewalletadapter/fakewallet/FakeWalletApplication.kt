/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

package com.solana.mobilewalletadapter.fakewallet

import android.app.Application
import com.econet.app.uitl.MyApplication
import com.solana.mobilewalletadapter.fakewallet.data.Ed25519KeyRepository

class FakeWalletApplication : MyApplication() {
    val keyRepository: Ed25519KeyRepository by lazy {
        Ed25519KeyRepository(this)
    }
}