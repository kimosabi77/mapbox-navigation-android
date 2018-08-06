package com.mapbox.services.android.navigation.ui.v5.instruction;

import com.mapbox.api.directions.v5.models.BannerComponents;

/**
 * This is the default text coordinator implementation to handle plain text components.
 */
class TextCoordinator extends NodeCoordinator<BannerComponentNode> {
  @Override
  boolean isNodeType(BannerComponents bannerComponents) {
    return bannerComponents.text() != null && !bannerComponents.text().isEmpty();
  }

  @Override
  BannerComponentNode setupNode(BannerComponents components, int index, int startIndex) {
    return new BannerComponentNode(components, startIndex);
  }
}
