package com.bachduong.core.wallet.families.bitcoin;

import com.bachduong.core.network.AddressStatus;
import com.bachduong.core.network.interfaces.BlockchainConnection;

/**
 * @author John L. Jegutanis
 */
public interface BitBlockchainConnection extends BlockchainConnection<BitTransaction> {
    void getUnspentTx(AddressStatus status, BitTransactionEventListener listener);
}
