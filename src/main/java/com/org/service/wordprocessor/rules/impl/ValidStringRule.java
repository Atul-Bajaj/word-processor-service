package com.org.service.wordprocessor.rules.impl;

import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.rules.ValidWordRule;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ValidStringRule implements ValidWordRule {

    @Override
    public boolean isRuleApplicable(ValidatorProcess process) {
        return true;
    }

    @Override
    public boolean executeEligibilityRule(ValidatorProcess process, String word) {
        if(StringUtils.isEmpty(word))
            return false;
        return true;
    }
}
