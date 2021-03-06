/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.tools.idea.gradle.project.sync.errors;

import com.android.tools.idea.gradle.project.sync.hyperlink.InstallNdkHyperlink;
import com.android.tools.idea.project.hyperlink.NotificationHyperlink;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

import static com.intellij.openapi.util.text.StringUtil.isNotEmpty;

public class MissingNdkErrorHandler extends BaseSyncErrorHandler {
  @Nullable
  @Override
  protected String findErrorMessage(@NotNull Throwable rootCause, @NotNull Project project) {
    String message = rootCause.getMessage();
    if (isNotEmpty(message) && isMatch(getFirstLineMessage(message))) {
      updateUsageTracker();
      return "NDK not configured.";
    }
    return null;
  }

  private static boolean isMatch(@NotNull String errorMessage) {
    return errorMessage.startsWith("NDK not configured.") || errorMessage.startsWith("NDK location not found.");
  }

  @Override
  @NotNull
  protected List<NotificationHyperlink> getQuickFixHyperlinks(@NotNull Project project, @NotNull String text) {
    return Collections.singletonList(new InstallNdkHyperlink());
  }
}
