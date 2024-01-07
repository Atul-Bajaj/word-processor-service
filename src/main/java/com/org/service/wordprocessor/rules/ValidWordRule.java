package com.org.service.wordprocessor.rules;

import com.org.service.wordprocessor.enums.ValidatorProcess;

public interface ValidWordRule {

    boolean isRuleApplicable(ValidatorProcess process);

    boolean executeEligibilityRule(ValidatorProcess process, String word);

}
