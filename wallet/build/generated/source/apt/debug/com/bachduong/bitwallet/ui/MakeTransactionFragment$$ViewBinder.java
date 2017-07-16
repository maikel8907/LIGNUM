// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MakeTransactionFragment$$ViewBinder<T extends com.bachduong.bitwallet.ui.MakeTransactionFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558592, "field 'transactionInfo'");
    target.transactionInfo = finder.castView(view, 2131558592, "field 'transactionInfo'");
    view = finder.findRequiredView(source, 2131558546, "field 'passwordView'");
    target.passwordView = finder.castView(view, 2131558546, "field 'passwordView'");
    view = finder.findRequiredView(source, 2131558593, "field 'txVisualizer'");
    target.txVisualizer = finder.castView(view, 2131558593, "field 'txVisualizer'");
    view = finder.findRequiredView(source, 2131558594, "field 'tradeWithdrawSendOutput'");
    target.tradeWithdrawSendOutput = finder.castView(view, 2131558594, "field 'tradeWithdrawSendOutput'");
    view = finder.findRequiredView(source, 2131558596, "method 'onConfirmClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onConfirmClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.transactionInfo = null;
    target.passwordView = null;
    target.txVisualizer = null;
    target.tradeWithdrawSendOutput = null;
  }
}
