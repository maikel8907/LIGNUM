// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MakeTransactionFragment$$ViewBinder<T extends com.bachduong.bitwallet.ui.MakeTransactionFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558589, "field 'transactionInfo'");
    target.transactionInfo = finder.castView(view, 2131558589, "field 'transactionInfo'");
    view = finder.findRequiredView(source, 2131558543, "field 'passwordView'");
    target.passwordView = finder.castView(view, 2131558543, "field 'passwordView'");
    view = finder.findRequiredView(source, 2131558590, "field 'txVisualizer'");
    target.txVisualizer = finder.castView(view, 2131558590, "field 'txVisualizer'");
    view = finder.findRequiredView(source, 2131558591, "field 'tradeWithdrawSendOutput'");
    target.tradeWithdrawSendOutput = finder.castView(view, 2131558591, "field 'tradeWithdrawSendOutput'");
    view = finder.findRequiredView(source, 2131558593, "method 'onConfirmClick'");
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
