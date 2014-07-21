/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sha1coin.params;

import com.google.sha1coin.core.NetworkParameters;
import com.google.sha1coin.core.Sha256Hash;
import com.google.sha1coin.core.Utils;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the main production network on which people trade goods and services.
 */
public class MainNetParams extends NetworkParameters {
    public MainNetParams() {
        super();
        interval = INTERVAL;
        targetTimespan = TARGET_TIMESPAN;
        maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
        dumpedPrivateKeyHeader = 191; // This is always addressHeader + 128
        addressHeader = 63; // Sha1coin addresses start with S
        //p2shHeader = 5; //We don't have this TODO
        acceptableAddressCodes = new int[] { addressHeader };
        port = 9513;
        packetMagic = 0xfbc0b6dbL;
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(1388281107L);
        genesisBlock.setNonce(1110492);
        id = ID_MAINNET;
        subsidyDecreaseBlockCount = 210000; // TODO
        spendableCoinbaseDepth = 100;
        String genesisHash = genesisBlock.getHashAsString();
        checkState(genesisHash.equals("b394336b1e5a1134e264265df4955896fcc1b22276e7fa084017ad526f38bf43"),
                genesisHash);

        // This contains (at a minimum) the blocks which are not BIP30 compliant. BIP30 changed how duplicate
        // transactions are handled. Duplicated transactions could occur in the case where a coinbase had the same
        // extraNonce and the same outputs but appeared at different heights, and greatly complicated re-org handling.
        // Having these here simplifies block connection logic considerably.
        checkpoints.put(48191, new Sha256Hash("4499f469507e9dbd2850b65dd7db62429b3fecb207b1abbd785ae84c11c793cf"));
        checkpoints.put(114320, new Sha256Hash("9d962ec6f932c51f53f76c0f9a6bb6cb9a8f48c836a114789cbba2da0fddc990"));

        dnsSeeds = new String[] {
            "shaseed.sighash.info",
        };
    }

    private static MainNetParams instance;
    public static synchronized MainNetParams get() {
        if (instance == null) {
            instance = new MainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return PAYMENT_PROTOCOL_ID_MAINNET;
    }
}
