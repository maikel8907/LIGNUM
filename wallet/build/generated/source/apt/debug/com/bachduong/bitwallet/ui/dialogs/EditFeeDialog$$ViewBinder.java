// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui.dialogs;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class EditFeeDialog$$ViewBinder<T extends com.bachduong.bitwallet.ui.dialogs.EditFeeDialog> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558542, "field 'description'");
    target.description = finder.castView(view, 2131558542, "field 'description'");
    view = finder.findRequiredView(source, 2131558543, "field 'feeAmount'");
    target.feeAmount = finder.castView(view, 2131558543, "field 'feeAmount'");
  }

  @Override public void unbind(T target) {
    target.description = null;
    target.feeAmount = null;
  }
}
