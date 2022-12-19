// Generated by view binder compiler. Do not edit!
package com.example.trisha.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.trisha.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCommentPanelBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final RecyclerView commentRecView;

  @NonNull
  public final Button commentSubmitBtn;

  @NonNull
  public final EditText commentTextTxt;

  private ActivityCommentPanelBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull RecyclerView commentRecView, @NonNull Button commentSubmitBtn,
      @NonNull EditText commentTextTxt) {
    this.rootView = rootView;
    this.commentRecView = commentRecView;
    this.commentSubmitBtn = commentSubmitBtn;
    this.commentTextTxt = commentTextTxt;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCommentPanelBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCommentPanelBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_comment_panel, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCommentPanelBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.comment_recView;
      RecyclerView commentRecView = ViewBindings.findChildViewById(rootView, id);
      if (commentRecView == null) {
        break missingId;
      }

      id = R.id.comment_submit_Btn;
      Button commentSubmitBtn = ViewBindings.findChildViewById(rootView, id);
      if (commentSubmitBtn == null) {
        break missingId;
      }

      id = R.id.comment_text_txt;
      EditText commentTextTxt = ViewBindings.findChildViewById(rootView, id);
      if (commentTextTxt == null) {
        break missingId;
      }

      return new ActivityCommentPanelBinding((LinearLayoutCompat) rootView, commentRecView,
          commentSubmitBtn, commentTextTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}