package com.org.service.wordprocessor.repositories;

public interface WordBankRepo {

    void loadWordBank();

    boolean isWordAvailable(String word);

    void add(String word);

    void add(String[] words);

    void remove(String[] words);

    void clear();
}
