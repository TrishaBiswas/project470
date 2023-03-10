// Generated by view binder compiler. Do not edit!
package com.example.trisha.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.trisha.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDashBoardBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RecyclerView dashBoardRecView;

  @NonNull
  public final FloatingActionButton dashboardFloatingActionButton;

  @NonNull
  public final FloatingActionButton dashboardFloatingActionButtonPayment;

  private ActivityDashBoardBinding(@NonNull RelativeLayout rootView,
      @NonNull RecyclerView dashBoardRecView,
      @NonNull FloatingActionButton dashboardFloatingActionButton,
      @NonNull FloatingActionButton dashboardFloatingActionButtonPayment) {
    this.rootView = rootView;
    this.dashBoardRecView = dashBoardRecView;
    this.dashboardFloatingActionButton = dashboardFloatingActionButton;
    this.dashboardFloatingActionButtonPayment = dashboardFloatingActionButtonPayment;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDashBoardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDashBoardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_dash_board, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDashBoardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dashBoard_recView;
      RecyclerView dashBoardRecView = ViewBindings.findChildViewById(rootView, id);
      if (dashBoardRecView == null) {
        break missingId;
      }

      id = R.id.dashboard_floating_actionButton;
      FloatingActionButton dashboardFloatingActionButton = ViewBindings.findChildViewById(rootView, id);
      if (dashboardFloatingActionButton == null) {
        break missingId;
      }

      id = R.id.dashboard_floating_actionButton_payment;
      FloatingActionButton dashboardFloatingActionButtonPayment = ViewBindings.findChildViewById(rootView, id);
      if (dashboardFloatingActionButtonPayment == null) {
        break missingId;
      }

      return new ActivityDashBoardBinding((RelativeLayout) rootView, dashBoardRecView,
          dashboardFloatingActionButton, dashboardFloatingActionButtonPayment);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
