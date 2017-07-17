// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BalanceFragment$$ViewBinder<T extends com.bachduong.bitwallet.ui.BalanceFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558573, "field 'transactionRows' and method 'onItemClick'");
    target.transactionRows = finder.castView(view, 2131558573, "field 'transactionRows'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p2);
        }
      });
    view = finder.findRequiredView(source, 2131558572, "field 'swipeContainer'");
    target.swipeContainer = finder.castView(view, 2131558572, "field 'swipeContainer'");
    view = finder.findRequiredView(source, 2131558576, "field 'emptyPocketMessage'");
    target.emptyPocketMessage = view;
    view = finder.findRequiredView(source, 2131558512, "field 'accountBalance' and method 'onMainAmountClick'");
    target.accountBalance = finder.castView(view, 2131558512, "field 'accountBalance'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onMainAmountClick();
        }
      });
    view = finder.findRequiredView(source, 2131558574, "field 'accountExchangedBalance' and method 'onLocalAmountClick'");
    target.accountExchangedBalance = finder.castView(view, 2131558574, "field 'accountExchangedBalance'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onLocalAmountClick();
        }
      });
    view = finder.findRequiredView(source, 2131558575, "field 'connectionLabel'");
    target.connectionLabel = finder.castView(view, 2131558575, "field 'connectionLabel'");
  }

  @Override public void unbind(T target) {
    target.transactionRows = null;
    target.swipeContainer = null;
    target.emptyPocketMessage = null;
    target.accountBalance = null;
    target.accountExchangedBalance = null;
    target.connectionLabel = null;
  }
}
