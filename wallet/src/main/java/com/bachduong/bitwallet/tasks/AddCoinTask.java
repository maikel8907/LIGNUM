package com.bachduong.bitwallet.tasks;

import android.os.AsyncTask;

import com.bachduong.core.coins.CoinType;
import com.bachduong.core.wallet.Wallet;
import com.bachduong.core.wallet.WalletAccount;

import org.spongycastle.crypto.params.KeyParameter;

import javax.annotation.Nullable;

/**
 * @author John L. Jegutanis
 */
public final class AddCoinTask extends AsyncTask<Void, Void, Void> {
    protected final CoinType type;
    private final Listener listener;
    private final Wallet wallet;
    @Nullable
    private final String description;
    @Nullable
    private final CharSequence password;
    private WalletAccount newAccount;
    private Exception exception;

    public AddCoinTask(Listener listener, CoinType type, Wallet wallet, @Nullable String description, @Nullable CharSequence password) {
        this.listener = listener;
        this.type = type;
        this.wallet = wallet;
        this.description = description;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        listener.onAddCoinTaskStarted();
    }

    @Override
    protected Void doInBackground(Void... params) {
        KeyParameter key = null;
        exception = null;
        try {
            if (wallet.isEncrypted() && wallet.getKeyCrypter() != null) {
                key = wallet.getKeyCrypter().deriveKey(password);
            }
            newAccount = wallet.createAccount(type, true, key);
            if (description != null && !description.trim().isEmpty()) {
                newAccount.setDescription(description);
            }
            wallet.saveNow();
        } catch (Exception e) {
            exception = e;
        }

        return null;
    }

    @Override
    final protected void onPostExecute(Void aVoid) {
        listener.onAddCoinTaskFinished(exception, newAccount);
    }

    public interface Listener {
        void onAddCoinTaskStarted();

        void onAddCoinTaskFinished(Exception error, WalletAccount newAccount);
    }
}