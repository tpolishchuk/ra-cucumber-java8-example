package org.lrp.helpers;

import org.lrp.world.LastRequestData;

public class StepPlaceholdersHelper {

    public String replaceLastCreatedUserIdInStep(String step, LastRequestData lastRequestData) {
        if (step.contains("<LAST_CREATED_USER_ID>")) {
            step = step.replaceAll("<LAST_CREATED_USER_ID>",
                                   lastRequestData.getLastCreatedUser().getId().toString());
        }

        return step;
    }
}
