package com.org.service.wordprocessor.repositories.impl;

import com.org.service.wordprocessor.jobs.UniqueWordFromFileJob;
import com.org.service.wordprocessor.repositories.WordBankRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Repository
public class WordBankRepoImpl implements WordBankRepo, Serializable {

    @Autowired
    private UniqueWordFromFileJob uniqueWordFromFileJob;

    @Value("${word-bank.file.path}")
    private String wordBankFilePath;

    private Set<String> wordBank = new HashSet<>();


    @Override
    public void loadWordBank() {
        wordBank = uniqueWordFromFileJob.executeJob(wordBankFilePath);
    }

    @Override
    public boolean isWordAvailable(String word) {
        return wordBank.contains(word);
    }

    @Override
    public void add(String word) {

    }

    @Override
    public void add(String[] words) {

    }

    @Override
    public void remove(String[] words) {

    }

    @Override
    public void clear() {
        wordBank = new HashSet<>();
    }
}
