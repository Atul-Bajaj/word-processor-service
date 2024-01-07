package com.org.service.wordprocessor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface UtilityService {

    <T> List<List<T>> partitionList(List<T> list, int batchSize);

    void printPrettyJson(List<Map.Entry<String, Long>> entryList) throws JsonProcessingException;
}
