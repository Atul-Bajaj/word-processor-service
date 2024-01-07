package com.org.service.wordprocessor.rules.impl;

import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.repositories.WordBankRepo;
import com.org.service.wordprocessor.rules.ValidWordRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordExistInBankRule implements ValidWordRule {

    @Autowired
    private WordBankRepo wordBankRepo;

    @Override
    public boolean isRuleApplicable(ValidatorProcess process) {
        return process.equals(ValidatorProcess.WORD_COUNT);
    }

    @Override
    public boolean executeEligibilityRule(ValidatorProcess process, String word) {
        if (this.isRuleApplicable(process))
            if(!wordBankRepo.isWordAvailable(word))
                return false;
        return true;
    }
}
