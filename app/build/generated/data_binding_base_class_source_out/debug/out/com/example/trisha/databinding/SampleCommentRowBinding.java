// Generated by view binder compiler. Do not edit!
package com.example.trisha.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.trisha.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SampleCommentRowBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView cudt;

  @NonNull
  public final CircleImageView cuimage;

  @NonNull
  public final TextView cumessage;

  @NonNull
  public final TextView cuname;

  private SampleCommentRowBinding(@NonNull CardView rootView, @NonNull TextView cudt,
      @NonNull CircleImageView cuimage, @NonNull TextView cumessage, @NonNull TextView cuname) {
    this.rootView = rootView;
    this.cudt = cudt;
    this.cuimage = cuimage;
    this.cumessage = cumessage;
    this.cuname = cuname;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static SampleCommentRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SampleCommentRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.sample_comment_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SampleCommentRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cudt;
      TextView cudt = ViewBindings.findChildViewById(rootView, id);
      if (cudt == null) {
        break missingId;
      }

      id = R.id.cuimage;
      CircleImageView cuimage = ViewBindings.findChildViewById(rootView, id);
      if (cuimage == null) {
        break missingId;
      }

      id = R.id.cumessage;
      TextView cumessage = ViewBindings.findChildViewById(rootView, id);
      if (cumessage == null) {
        break missingId;
      }

      id = R.id.cuname;
      TextView cuname = ViewBindings.findChildViewById(rootView, id);
      if (cuname == null) {
        break missingId;
      }

      return new SampleCommentRowBinding((CardView) rootView, cudt, cuimage, cumessage, cuname);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}