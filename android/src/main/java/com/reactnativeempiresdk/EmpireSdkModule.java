package com.reactnativeempiresdk;

import android.app.Activity;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueException;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;
import com.truecaller.android.sdk.clients.VerificationCallback;
import com.truecaller.android.sdk.clients.VerificationDataBundle;

@ReactModule(name = EmpireSdkModule.NAME)
public class EmpireSdkModule extends ReactContextBaseJavaModule {
  public static final String NAME = "EmpireSdk";

  private Promise promise = null;

  private final ITrueCallback sdkCallback = new ITrueCallback() {
    @Override
    public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {

      if (promise != null) {
        WritableMap map = Arguments.createMap();
        map.putBoolean("successful", true);
        map.putString("firstName", trueProfile.firstName);
        map.putString("lastName", trueProfile.lastName);
        map.putString("phoneNumber", trueProfile.phoneNumber);
        map.putString("gender", trueProfile.gender);
        map.putString("street", trueProfile.street);
        map.putString("city", trueProfile.city);
        map.putString("zipcode", trueProfile.zipcode);
        map.putString("countryCode", trueProfile.countryCode);
        map.putString("facebookId", trueProfile.facebookId);
        map.putString("twitterId", trueProfile.twitterId);
        map.putString("email", trueProfile.email);
        map.putString("url", trueProfile.url);
        map.putString("avatarUrl", trueProfile.avatarUrl);
        map.putBoolean("isVerified", trueProfile.isTrueName);
        map.putBoolean("isAmbassador", trueProfile.isAmbassador);
        map.putString("companyName", trueProfile.companyName);
        map.putString("jobTitle", trueProfile.jobTitle);
        map.putString("payload", trueProfile.payload);
        map.putString("signature", trueProfile.signature);
        map.putString("signatureAlgorithm", trueProfile.signatureAlgorithm);
        map.putString("requestNonce", trueProfile.requestNonce);
        map.putBoolean("isBusiness", trueProfile.isBusiness);
        promise.resolve(map);
      }
    }

    @Override
    public void onFailureProfileShared(@NonNull final TrueError trueError) {
      Log.d("TruecallerAuthModule", Integer.toString(trueError.getErrorType()));
      if (promise != null) {
        String errorReason = null;
        switch (trueError.getErrorType()) {
          case TrueError.ERROR_TYPE_INTERNAL:
            errorReason = "ERROR_TYPE_INTERNAL";
            break;
          case TrueError.ERROR_TYPE_NETWORK:
            errorReason = "ERROR_TYPE_NETWORK";
            break;
          case TrueError.ERROR_TYPE_USER_DENIED:
            errorReason = "ERROR_TYPE_USER_DENIED";
            break;
          case TrueError.ERROR_PROFILE_NOT_FOUND:
            errorReason = "ERROR_TYPE_UNAUTHORIZED_PARTNER";
            break;
          case TrueError.ERROR_TYPE_UNAUTHORIZED_USER:
            errorReason = "ERROR_TYPE_UNAUTHORIZED_USER";
            break;
          case TrueError.ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY:
            errorReason = "ERROR_TYPE_TRUECALLER_CLOSED_UNEXPECTEDLY";
            break;
          case TrueError.ERROR_TYPE_TRUESDK_TOO_OLD:
            errorReason = "ERROR_TYPE_TRUESDK_TOO_OLD";
            break;
          case TrueError.ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION:
            errorReason = "ERROR_TYPE_POSSIBLE_REQ_CODE_COLLISION";
            break;
          case TrueError.ERROR_TYPE_RESPONSE_SIGNATURE_MISMATCH:
            errorReason = "ERROR_TYPE_RESPONSE_SIGNATURE_MISSMATCH";
            break;
          case TrueError.ERROR_TYPE_REQUEST_NONCE_MISMATCH:
            errorReason = "ERROR_TYPE_REQUEST_NONCE_MISSMATCH";
            break;
          case TrueError.ERROR_TYPE_INVALID_ACCOUNT_STATE:
            errorReason = "ERROR_TYPE_INVALID_ACCOUNT_STATE";
            break;
          case TrueError.ERROR_TYPE_TC_NOT_INSTALLED:
            errorReason = "ERROR_TYPE_TC_NOT_INSTALLED";
            break;
          case TrueError.ERROR_TYPE_ACTIVITY_NOT_FOUND:
            errorReason = "ERROR_TYPE_ACTIVITY_NOT_FOUND";
            break;
        }
        WritableMap map = Arguments.createMap();
        map.putString("error", errorReason != null ? errorReason : "ERROR_TYPE_NULL");
        promise.resolve(map);
      }
    }

    @Override
    public void onVerificationRequired(TrueError trueError) {
      // The statement below can be ignored incase of one-tap flow integration
      // TruecallerSDK.getInstance().requestVerification("IN", "PHONE-NUMBER-STRING",
      // apiCallback,(FragmentActivity) getCurrentActivity());
    }
  };

  public EmpireSdkModule(ReactApplicationContext reactContext) {
    super(reactContext);
    TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(reactContext, sdkCallback)
        .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
        .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
        .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
        .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
        .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
        .privacyPolicyUrl("https://www.gamezy.com/privacy-policy.html")
        .termsOfServiceUrl("https://www.gamezy.com/terms.html")
        .footerType(TruecallerSdkScope.FOOTER_TYPE_CONTINUE)
        .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
        .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
        .build();
    TruecallerSDK.init(trueScope);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void init() {
    System.out.println("Truecaller Auth Module");
  }

}
