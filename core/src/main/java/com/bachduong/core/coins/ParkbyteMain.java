package com.bachduong.core.coins;

import com.bachduong.core.coins.families.PeerFamily;

/**
 * @author John L. Jegutanis
 */
public class ParkbyteMain extends PeerFamily {
    private static ParkbyteMain instance = new ParkbyteMain();

    private ParkbyteMain() {
        id = "parkbyte.main";

        addressHeader = 55;
        p2shHeader = 28;
        acceptableAddressCodes = new int[]{addressHeader, p2shHeader};
        spendableCoinbaseDepth = 5;
        dumpedPrivateKeyHeader = 183;

        name = "ParkByte";
        symbol = "PKB";
        uriScheme = "parkbyte";
        bip44Index = 36;
        unitExponent = 8;
        feeValue = value(1000); // 0.0001PKB
        minNonDust = value(1000); // 0.01PKB
        softDustLimit = minNonDust;
        softDustPolicy = SoftDustPolicy.NO_POLICY;
        signedMessageHeader = toBytes("ParkByte Signed Message:\n");
    }

    public static synchronized ParkbyteMain get() {
        return instance;
    }
}
