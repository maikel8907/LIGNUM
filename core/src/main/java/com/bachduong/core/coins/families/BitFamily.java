package com.bachduong.core.coins.families;

import com.bachduong.core.coins.CoinType;
import com.bachduong.core.exceptions.AddressMalformedException;
import com.bachduong.core.wallet.families.bitcoin.BitAddress;

/**
 * @author John L. Jegutanis
 *         <p>
 *         This is the classical Bitcoin family that includes Litecoin, Dogecoin, Dash, etc
 */
public abstract class BitFamily extends CoinType {
    {
        family = Families.BITCOIN;
    }

    @Override
    public BitAddress newAddress(String addressStr) throws AddressMalformedException {
        return BitAddress.from(this, addressStr);
    }
}
