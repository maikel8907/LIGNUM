package com.bachduong.core.wallet.families.bitcoin;

import com.bachduong.core.network.AddressStatus;
import com.bachduong.core.network.ServerClient.UnspentTx;
import com.bachduong.core.network.interfaces.TransactionEventListener;

import java.util.List;

/**
 * @author John L. Jegutanis
 */
public interface BitTransactionEventListener extends TransactionEventListener<BitTransaction> {
    void onUnspentTransactionUpdate(AddressStatus status, List<UnspentTx> UnspentTxes);
}
