package com.bachduong.core.messages;

import com.bachduong.core.wallet.AbstractTransaction;

import java.io.Serializable;

/**
 * @author John L. Jegutanis
 */
public interface TxMessage extends Serializable {
    // TODO use an abstract transaction
    void serializeTo(AbstractTransaction transaction);

    Type getType();

    String toString();

    enum Type {
        PUBLIC, PRIVATE
    }
}
