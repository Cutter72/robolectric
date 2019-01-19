package org.robolectric;

import android.os.Build;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import org.junit.runners.model.InitializationError;
import org.robolectric.pluginapi.ConfigurationStrategy.ConfigCollection;
import org.robolectric.pluginapi.Sdk;
import org.robolectric.pluginapi.SdkPicker;
import org.robolectric.pluginapi.UsesSdk;
import org.robolectric.util.TestUtil;
import org.robolectric.util.inject.Injector;

public class SingleSdkRobolectricTestRunner extends RobolectricTestRunner {

  private static final Injector INJECTOR = defaultInjector();

  public static Injector defaultInjector() {
    return RobolectricTestRunner.defaultInjector()
        .register(SdkPicker.class, SingleSdkPicker.class);
  }

  public SingleSdkRobolectricTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass, INJECTOR);
  }

  public SingleSdkRobolectricTestRunner(Class<?> testClass, Injector injector)
      throws InitializationError {
    super(testClass, injector);
  }

  @Override
  ResourcesMode getResourcesMode() {
    return ResourcesMode.binary;
  }

  public static class SingleSdkPicker implements SdkPicker {

    private final Sdk sdk;

    public SingleSdkPicker() {
      this(Build.VERSION_CODES.P);
    }

    SingleSdkPicker(int apiLevel) {
      this.sdk = TestUtil.getSdkProvider().getSdk(apiLevel);
    }

    @Nonnull
    @Override
    public List<Sdk> selectSdks(ConfigCollection configCollection, UsesSdk usesSdk) {
      return Collections.singletonList(sdk);
    }
  }
}