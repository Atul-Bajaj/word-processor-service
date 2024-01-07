package com.org.service.wordprocessor.rules.impl;

import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.rules.ValidWordRule;
import org.springframework.stereotype.Component;

@Component
public class WordSizeRule implements ValidWordRule {

    @Override
    public boolean isRuleApplicable(ValidatorProcess process) {
        return process.equals(ValidatorProcess.WORD_COUNT);
    }

    @Override
    public boolean executeEligibilityRule(ValidatorProcess process, String word) {
        if (this.isRuleApplicable(process))
            if(word.length() < 3)
                return false;

        return true;
    }
}
