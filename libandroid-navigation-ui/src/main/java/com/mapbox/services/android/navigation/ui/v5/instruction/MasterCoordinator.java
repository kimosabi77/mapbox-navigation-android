package com.mapbox.services.android.navigation.ui.v5.instruction;

import android.widget.TextView;

import com.mapbox.api.directions.v5.models.BannerComponents;

import java.util.ArrayList;
import java.util.List;

public class MasterCoordinator {
  private final NodeCoordinator[] nodeCoordinators;

  /**
   * Creates a master coordinator to make sure the coordinators passed in are used appropriately
   *
   * @param nodeCoordinators coordinators in the order that they should process banner components
   */
  MasterCoordinator(NodeCoordinator... nodeCoordinators) {
    this.nodeCoordinators = nodeCoordinators;
  }

  /**
   * Parses the banner components and processes them using the nodeCoordinators in the order they
   * were originally passed
   *
   * @param bannerComponents to parse
   * @return the list of nodes representing the bannerComponents
   */
  List<BannerComponentNode> parseBannerComponents(List<BannerComponents> bannerComponents) {
    int length = 0;
    List<BannerComponentNode> bannerComponentNodes = new ArrayList<>();

    for (BannerComponents components : bannerComponents) {
      BannerComponentNode node = null;

      for (NodeCoordinator nodeCoordinator : nodeCoordinators) {
        if (nodeCoordinator.isNodeType(components)) {
          node = nodeCoordinator.setupNode(components, bannerComponentNodes.size(), length - 1);
          break;
        }
      }

      if (node != null) {
        bannerComponentNodes.add(node);
        length += components.text().length() + 1;
      }
    }

    return bannerComponentNodes;
  }

  /**
   * Loads the instruction into the given text view. If things have to be done in a particular order,
   * the coordinator methods preProcess and postProcess can be used. PreProcess should be used to
   * load text into the textView (so there should only be one coordinator calling this method), and
   * postProcess should be used to make changes to that text, i.e., to load images into the textView.
   *
   * @param textView in which to load text and images
   * @param bannerComponentNodes containing instructions
   */
  void loadInstruction(TextView textView, List<BannerComponentNode> bannerComponentNodes) {
    for (NodeCoordinator nodeCoordinator : nodeCoordinators) {
      nodeCoordinator.preProcess(textView, bannerComponentNodes);
    }

    for (NodeCoordinator nodeCoordinator : nodeCoordinators) {
      nodeCoordinator.postProcess(textView, bannerComponentNodes);
    }
  }
}
