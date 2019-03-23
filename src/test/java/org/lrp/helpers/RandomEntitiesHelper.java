package org.lrp.helpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomEntitiesHelper {

    public String generateRandomEntitiesForStep(String step) {
        step = generateRandomIntegersForStep(step);
        return generateRandomStringsForStep(step);
    }

    public String generateRandomIntegersForStep(String step) {
        while (step.contains("<RANDOM_INT>")) {
            step = step.replaceFirst("<RANDOM_INT>", RandomStringUtils.randomNumeric(4, 6));
        }
        return step;
    }

    public String generateRandomStringsForStep(String step) {
        Pattern pattern = Pattern.compile("<RANDOM_STRING_LENGTH_\\d+>");
        Matcher matcher = pattern.matcher(step);

        while (matcher.find()) {
            String stringToBeReplaced = matcher.group();
            int length = Integer.parseInt(stringToBeReplaced.replaceAll("\\D+",""));

            step = step.replaceFirst(stringToBeReplaced, RandomStringUtils.randomAlphabetic(length));
        }

        return step;
    }
}
