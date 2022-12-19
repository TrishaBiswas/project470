// Generated by view binder compiler. Do not edit!
package com.example.trisha.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.trisha.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityUserProfileUpdateBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView userNameTxt;

  @NonNull
  public final CircleImageView userProfileUserImage;

  @NonNull
  public final EditText userProfileUserName;

  @NonNull
  public final Button userProfileUserSaveBTN;

  private ActivityUserProfileUpdateBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView userNameTxt, @NonNull CircleImageView userProfileUserImage,
      @NonNull EditText userProfileUserName, @NonNull Button userProfileUserSaveBTN) {
    this.rootView = rootView;
    this.userNameTxt = userNameTxt;
    this.userProfileUserImage = userProfileUserImage;
    this.userProfileUserName = userProfileUserName;
    this.userProfileUserSaveBTN = userProfileUserSaveBTN;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityUserProfileUpdateBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityUserProfileUpdateBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_user_profile_update, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityUserProfileUpdateBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.userName_txt;
      TextView userNameTxt = ViewBindings.findChildViewById(rootView, id);
      if (userNameTxt == null) {
        break missingId;
      }

      id = R.id.userProfile_userImage;
      CircleImageView userProfileUserImage = ViewBindings.findChildViewById(rootView, id);
      if (userProfileUserImage == null) {
        break missingId;
      }

      id = R.id.userProfile_userName;
      EditText userProfileUserName = ViewBindings.findChildViewById(rootView, id);
      if (userProfileUserName == null) {
        break missingId;
      }

      id = R.id.userProfile_userSaveBTN;
      Button userProfileUserSaveBTN = ViewBindings.findChildViewById(rootView, id);
      if (userProfileUserSaveBTN == null) {
        break missingId;
      }

      return new ActivityUserProfileUpdateBinding((RelativeLayout) rootView, userNameTxt,
          userProfileUserImage, userProfileUserName, userProfileUserSaveBTN);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
