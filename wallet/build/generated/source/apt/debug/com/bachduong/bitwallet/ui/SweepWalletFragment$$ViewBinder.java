// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SweepWalletFragment$$ViewBinder<T extends com.bachduong.bitwallet.ui.SweepWalletFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558662, "field 'privateKeyInputView'");
    target.privateKeyInputView = view;
    view = finder.findRequiredView(source, 2131558663, "field 'privateKeyText', method 'onPrivateKeyInputFocusChange', and method 'onPrivateKeyInputTextChange'");
    target.privateKeyText = finder.castView(view, 2131558663, "field 'privateKeyText'");
    view.setOnFocusChangeListener(
      new android.view.View.OnFocusChangeListener() {
        @Override public void onFocusChange(
          android.view.View p0,
          boolean p1
        ) {
          target.onPrivateKeyInputFocusChange(p1);
        }
      });
    ((android.widget.TextView) view).addTextChangedListener(
      new android.text.TextWatcher() {
        @Override public void onTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void beforeTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void afterTextChanged(
          android.text.Editable p0
        ) {
          target.onPrivateKeyInputTextChange();
        }
      });
    view = finder.findRequiredView(source, 2131558664, "field 'passwordView'");
    target.passwordView = view;
    view = finder.findRequiredView(source, 2131558666, "field 'errorΜessage'");
    target.errorΜessage = finder.castView(view, 2131558666, "field 'errorΜessage'");
    view = finder.findRequiredView(source, 2131558665, "field 'password'");
    target.password = finder.castView(view, 2131558665, "field 'password'");
    view = finder.findRequiredView(source, 2131558667, "field 'sweepLoadingView'");
    target.sweepLoadingView = view;
    view = finder.findRequiredView(source, 2131558668, "field 'sweepStatus'");
    target.sweepStatus = finder.castView(view, 2131558668, "field 'sweepStatus'");
    view = finder.findRequiredView(source, 2131558619, "field 'nextButton' and method 'verifyKeyAndProceed'");
    target.nextButton = finder.castView(view, 2131558619, "field 'nextButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.verifyKeyAndProceed();
        }
      });
    view = finder.findRequiredView(source, 2131558612, "method 'handleScan'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.handleScan();
        }
      });
  }

  @Override public void unbind(T target) {
    target.privateKeyInputView = null;
    target.privateKeyText = null;
    target.passwordView = null;
    target.errorΜessage = null;
    target.password = null;
    target.sweepLoadingView = null;
    target.sweepStatus = null;
    target.nextButton = null;
  }
}
