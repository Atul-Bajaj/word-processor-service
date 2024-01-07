package com.org.service.wordprocessor.utils.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.service.wordprocessor.utils.UtilityService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UtilityServiceImpl implements UtilityService {

    @Override
    public  <T> List<List<T>> partitionList(List<T> list, int batchSize) {
        return IntStream.range(0, (list.size() + batchSize - 1) / batchSize)
                .mapToObj(i -> list.subList(i * batchSize, Math.min((i + 1) * batchSize, list.size())))
                .collect(Collectors.toList());
    }

    @Override
    public void printPrettyJson(List<Map.Entry<String, Long>> entryList) throws JsonProcessingException {
        List<Map<String, String>> serializedList = entryList.stream()
                .map(entry -> Collections.singletonMap("key", entry.getKey()))
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serializedList);

        System.out.println(prettyJson);
    }

}
