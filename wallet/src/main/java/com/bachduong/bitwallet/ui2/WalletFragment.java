package com.bachduong.bitwallet.ui2;

import android.support.v4.app.Fragment;

import com.bachduong.core.wallet.WalletAccount;

/**
 * @author John L. Jegutanis
 */
public abstract class WalletFragment extends Fragment {
    abstract public WalletAccount getAccount();
}
