package com.mapbox.services.android.navigation.ui.v5.instruction;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.mapbox.api.directions.v5.models.BannerComponents;
import com.mapbox.api.directions.v5.models.BannerText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that can be used to load a given {@link BannerText} into the provided
 * {@link TextView}.
 * <p>
 * For each {@link BannerComponents}, either the text or given shield URL will be used (the shield
 * URL taking priority).
 * <p>
 * If a shield URL is found, {@link Picasso} is used to load the image.  Then, once the image is loaded,
 * a new {@link ImageSpan} is created and set to the appropriate position of the {@link Spannable}/
 */
class InstructionLoader {
  private TextView textView;
  private List<BannerComponentNode> bannerComponentNodes;
  private MasterCoordinator masterCoordinator;

  InstructionLoader(TextView textView, @NonNull List<BannerComponents> bannerComponents) {
    this(textView, bannerComponents, ImageCoordinator.getInstance(), new AbbreviationCoordinator(),
      new TextCoordinator());
  }

  InstructionLoader(TextView textView, @NonNull List<BannerComponents> bannerComponents,
                    ImageCoordinator imageCoordinator, AbbreviationCoordinator abbreviationCoordinator,
                    TextCoordinator textCoordinator) {
    this.textView = textView;
    bannerComponentNodes = new ArrayList<>();
    masterCoordinator = new MasterCoordinator(abbreviationCoordinator, imageCoordinator, textCoordinator);
    bannerComponentNodes = parseBannerComponents(bannerComponents);
  }

  /**
   * Takes the given components from the {@link BannerText} and creates
   * a new {@link Spannable} with text / {@link ImageSpan}s which is loaded
   * into the given {@link TextView}.
   */
  void loadInstruction() {
    masterCoordinator.loadInstruction(textView, bannerComponentNodes);
  }

  private List<BannerComponentNode> parseBannerComponents(List<BannerComponents> bannerComponents) {
    return masterCoordinator.parseBannerComponents(bannerComponents);
  }
}
