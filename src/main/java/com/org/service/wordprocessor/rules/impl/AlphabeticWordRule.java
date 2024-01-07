package com.org.service.wordprocessor.rules.impl;

import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.rules.ValidWordRule;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AlphabeticWordRule implements ValidWordRule {

    private static final Pattern ALPHABETIC_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    @Override
    public boolean isRuleApplicable(ValidatorProcess process) {

        /*
        can store ValidatorProcess and applicable validation mapping to a persistent store
        this will enhance flexibility, eliminating the need for code changes when attaching or removing validations.
         */
        
        return process.equals(ValidatorProcess.LOAD_WORDS) || process.equals(ValidatorProcess.WORD_COUNT);
    }

    @Override
    public boolean executeEligibilityRule(ValidatorProcess process, String word) {
        if (this.isRuleApplicable(process))
            if(!ALPHABETIC_PATTERN.matcher(word).matches())
                return false;

        return true;
    }

}
