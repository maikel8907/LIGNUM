package com.bachduong.core.coins.families;

import com.bachduong.core.messages.MessageFactory;
import com.bachduong.core.wallet.families.clams.ClamsTxMessage;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 *         <p>
 *         This family contains Clams
 */
public class ClamsFamily extends BitFamily {
    {
        family = Families.CLAMS;
    }

    @Override
    @Nullable
    public MessageFactory getMessagesFactory() {
        return ClamsTxMessage.getFactory();
    }
}
