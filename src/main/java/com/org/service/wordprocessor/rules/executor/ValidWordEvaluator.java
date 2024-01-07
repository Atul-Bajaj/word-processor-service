package com.org.service.wordprocessor.rules.executor;

import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.rules.ValidWordRule;
import com.org.service.wordprocessor.rules.impl.AlphabeticWordRule;
import com.org.service.wordprocessor.rules.impl.ValidStringRule;
import com.org.service.wordprocessor.rules.impl.WordExistInBankRule;
import com.org.service.wordprocessor.rules.impl.WordSizeRule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ValidWordEvaluator {

    private final List<ValidWordRule> rules = new ArrayList<>();

    @Autowired
    private ValidStringRule validStringRule;

    @Autowired
    private WordSizeRule wordSizeRule;

    @Autowired
    private AlphabeticWordRule alphabeticWordRule;

    @Autowired
    private WordExistInBankRule wordExistInBankRule;


    @PostConstruct
    public void init() {
        rules.add(validStringRule);
        rules.add(wordSizeRule);
        rules.add(alphabeticWordRule);
        rules.add(wordExistInBankRule);
    }

    public boolean evaluateRules(ValidatorProcess process, String word) {
        for (ValidWordRule rule: rules)
            if(!rule.executeEligibilityRule(process,word))
                return false;
        return true;
    }
}
