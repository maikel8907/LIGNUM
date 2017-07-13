package com.bachduong.core.coins;

import com.bachduong.core.coins.families.NxtFamily;

/**
 * @author John L. Jegutanis
 */
public class BurstMain extends NxtFamily {

    private static BurstMain instance = new BurstMain();

    private BurstMain() {
        id = "burst.main";

        name = "Burst";
        symbol = "BURST";
        uriScheme = "burst";
        bip44Index = 30;
        unitExponent = 8;
        addressPrefix = "BURST-";
        feeValue = oneCoin();
        minNonDust = value(1);
        feePolicy = FeePolicy.FLAT_FEE;
    }

    public static synchronized CoinType get() {
        return instance;
    }
}
