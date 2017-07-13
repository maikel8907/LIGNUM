// Generated code from Butter Knife. Do not modify!
package com.bachduong.bitwallet.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OverviewFragment$$ViewBinder<T extends com.bachduong.bitwallet.ui.OverviewFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558581, "field 'swipeContainer'");
    target.swipeContainer = finder.castView(view, 2131558581, "field 'swipeContainer'");
    view = finder.findRequiredView(source, 2131558595, "field 'accountRows', method 'onAmountClick', and method 'onAmountLongClick'");
    target.accountRows = finder.castView(view, 2131558595, "field 'accountRows'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onAmountClick(p2);
        }
      });
    ((android.widget.AdapterView<?>) view).setOnItemLongClickListener(
      new android.widget.AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          return target.onAmountLongClick(p2);
        }
      });
    view = finder.findRequiredView(source, 2131558512, "field 'mainAmount' and method 'onMainAmountClick'");
    target.mainAmount = finder.castView(view, 2131558512, "field 'mainAmount'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onMainAmountClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.swipeContainer = null;
    target.accountRows = null;
    target.mainAmount = null;
  }
}
