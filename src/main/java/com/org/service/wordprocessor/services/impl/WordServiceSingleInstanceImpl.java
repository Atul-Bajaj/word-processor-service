package com.org.service.wordprocessor.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.exceptions.ValidationException;
import com.org.service.wordprocessor.jobs.WordCountFromStringJob;
import com.org.service.wordprocessor.repositories.WordBankRepo;
import com.org.service.wordprocessor.services.WordExtractorService;
import com.org.service.wordprocessor.services.WordService;
import com.org.service.wordprocessor.utils.UtilityService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@Service("single")
public class WordServiceSingleInstanceImpl implements WordService {

    private final int MAX_THREADS = 10;

    @Value("${urls.batch.size}")
    private int batchSize;

    Logger logger = LoggerFactory.getLogger(WordServiceSingleInstanceImpl.class);

    @Autowired
    private WordCountFromStringJob wordCountSparkJob;

    @Lazy
    @Autowired
    private WordBankRepo wordBankRepo;

    @Autowired
    private WordExtractorService wordExtractorService;

    @Autowired
    private UtilityService utilityService;

    @Override
    public void getTopWords(MultipartFile file, int topCount) throws JsonProcessingException {
        wordBankRepo.loadWordBank();
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        List<List<String>> urlBatches = utilityService.partitionList(getListOfURL(file).subList(1,10), batchSize);

        List<CompletableFuture<Map<String, Long>>> futures = urlBatches.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> wordCountSparkJob.executeJob(wordExtractorService.fetchData(url), ValidatorProcess.WORD_COUNT),executorService))
                .collect(Collectors.toList());

        Map<String, Long> totalWordCounts = futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return Collections.<String, Long>emptyMap(); // Handle errors gracefully
                    }
                })
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(entry -> entry.getKey(), Map.Entry::getValue, Long::sum));

        List<Map.Entry<String, Long>> topWords = totalWordCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topCount)
                .collect(Collectors.toList());

        wordBankRepo.clear();

        utilityService.printPrettyJson(topWords);
    }


    private List<String> getListOfURL(MultipartFile file) {
        List<String> urlList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                urlList.add(line.trim());
            }

            return urlList;

        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidationException("");
        }
    }



}
