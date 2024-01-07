package com.org.service.wordprocessor.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.exceptions.ValidationException;
import com.org.service.wordprocessor.jobs.WordCountFromFilesJob;
import com.org.service.wordprocessor.publisher.UrlExtractMsgPublisher;
import com.org.service.wordprocessor.repositories.WordBankRepo;
import com.org.service.wordprocessor.services.WordExtractorService;
import com.org.service.wordprocessor.services.WordService;
import com.org.service.wordprocessor.utils.UtilityService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("multi")
public class WordServiceMultiInstanceImpl implements WordService {


    @Autowired
    private WordCountFromFilesJob wordCountSparkJob;

    @Lazy
    @Autowired
    private WordBankRepo wordBankRepo;

    @Autowired
    private WordExtractorService wordExtractorService;

    @Autowired
    private UrlExtractMsgPublisher urlExtractMsgPublisher;

    @Autowired
    private UtilityService utilityService;

    @Value("")
    private String resultFilesPath;

    @Value("${urls.batch.size}")
    private int batchSize;


    @Override
    public void getTopWords(MultipartFile file, int topCount) {
        List<List<String>> urlBatches = utilityService.partitionList(getListOfURL(file), batchSize);
        urlBatches.forEach(msg -> urlExtractMsgPublisher.publishMessage(msg));
    }


    /*
        we push notification on each message execution and once all message process we invoke this function
     */
    private void allMessageConsumeTrigger(int topCount) throws JsonProcessingException {
        wordBankRepo.loadWordBank();

        Map<String, Long> totalWordCounts = wordCountSparkJob.executeJob(resultFilesPath, ValidatorProcess.WORD_COUNT);

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
