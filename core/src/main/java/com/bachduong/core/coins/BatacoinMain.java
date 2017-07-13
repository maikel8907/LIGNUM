package com.bachduong.core.coins;

import com.bachduong.core.coins.families.BitFamily;

/**
 * @author John L. Jegutanis
 */
public class BatacoinMain extends BitFamily {
    private static BatacoinMain instance = new BatacoinMain();

    private BatacoinMain() {
        id = "bata.main";

        addressHeader = 25;
        p2shHeader = 5;
        acceptableAddressCodes = new int[]{addressHeader, p2shHeader};
        spendableCoinbaseDepth = 100;
        dumpedPrivateKeyHeader = 153;

        name = "Bata (beta)";
        symbol = "BTA";
        uriScheme = "bata";
        bip44Index = 89;
        unitExponent = 8;
        feeValue = value(1000);
        minNonDust = value(1000); // 0.00001 LTC mininput
        softDustLimit = value(1000); // 0.001 LTC
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    public static synchronized BatacoinMain get() {
        return instance;
    }
}
