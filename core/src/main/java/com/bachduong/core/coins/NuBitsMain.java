package com.bachduong.core.coins;

import com.bachduong.core.coins.families.NuFamily;

/**
 * @author John L. Jegutanis
 */
public class NuBitsMain extends NuFamily {
    private static NuBitsMain instance = new NuBitsMain();

    private NuBitsMain() {
        id = "nubits.main";

        addressHeader = 25;
        p2shHeader = 26;
        acceptableAddressCodes = new int[]{addressHeader, p2shHeader};
        dumpedPrivateKeyHeader = 150;
        tokenId = 0x42;

        name = "NuBits";
        symbol = "NBT";
        uriScheme = "nu";
        bip44Index = 12;
        unitExponent = 4;
        feeValue = value(100); // 0.02NBT, careful NuBits has 10000 units per coin
        minNonDust = value(100); // 0.01NBT
        softDustLimit = minNonDust;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
        signedMessageHeader = toBytes("Nu Signed Message:\n");
    }

    public static synchronized CoinType get() {
        return instance;
    }
}
