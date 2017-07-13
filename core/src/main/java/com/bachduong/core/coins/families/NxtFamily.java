package com.bachduong.core.coins.families;

import com.bachduong.core.coins.CoinType;
import com.bachduong.core.exceptions.AddressMalformedException;
import com.bachduong.core.messages.MessageFactory;
import com.bachduong.core.wallet.AbstractAddress;
import com.bachduong.core.wallet.families.nxt.NxtAddress;
import com.bachduong.core.wallet.families.nxt.NxtTxMessage;

import javax.annotation.Nullable;


/**
 * @author John L. Jegutanis
 *         <p>
 *         Coins that belong to this family are: NXT, Burst, etc
 */
public abstract class NxtFamily extends CoinType {
    public static final short DEFAULT_DEADLINE = 1440;

    {
        family = Families.NXT;
    }

    @Override
    public AbstractAddress newAddress(String addressStr) throws AddressMalformedException {
        return NxtAddress.fromString(this, addressStr);
    }

    @Override
    public boolean canHandleMessages() {
        return true;
    }

    @Override
    @Nullable
    public MessageFactory getMessagesFactory() {
        return NxtTxMessage.getFactory();
    }
}