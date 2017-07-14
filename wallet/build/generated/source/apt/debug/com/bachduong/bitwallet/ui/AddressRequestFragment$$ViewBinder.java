// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddressRequestFragment$$ViewBinder<T extends com.bachduong.bitwallet.ui.AddressRequestFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558602, "field 'addressLabelView'");
    target.addressLabelView = finder.castView(view, 2131558602, "field 'addressLabelView'");
    view = finder.findRequiredView(source, 2131558603, "field 'addressView'");
    target.addressView = finder.castView(view, 2131558603, "field 'addressView'");
    view = finder.findRequiredView(source, 2131558607, "field 'sendCoinAmountView'");
    target.sendCoinAmountView = finder.castView(view, 2131558607, "field 'sendCoinAmountView'");
    view = finder.findRequiredView(source, 2131558609, "field 'previousAddressesLink' and method 'onPreviousAddressesClick'");
    target.previousAddressesLink = view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onPreviousAddressesClick();
        }
      });
    view = finder.findRequiredView(source, 2131558606, "field 'qrView'");
    target.qrView = finder.castView(view, 2131558606, "field 'qrView'");
    view = finder.findRequiredView(source, 2131558601, "method 'onAddressClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onAddressClick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.addressLabelView = null;
    target.addressView = null;
    target.sendCoinAmountView = null;
    target.previousAddressesLink = null;
    target.qrView = null;
  }
}
