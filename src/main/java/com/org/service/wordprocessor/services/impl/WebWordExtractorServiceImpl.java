package com.org.service.wordprocessor.services.impl;

import com.org.service.wordprocessor.services.WordExtractorService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.RateLimiter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WebWordExtractorServiceImpl implements WordExtractorService {

    Logger logger = LoggerFactory.getLogger(WebWordExtractorServiceImpl.class);

    private final ExecutorService fetchURLExecutor = Executors.newFixedThreadPool(5);

    @Autowired
    private RateLimiter rateLimiter ;


    @Override
    public List<String> fetchData(List<String> urls) {

        List<CompletableFuture<List<String>>> futures = urls.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> fetchEssayFromUrl(url), fetchURLExecutor))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<String>> allContents = allOf.thenApply(
                v -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );

        List<String> content;
        try {
            content = allContents.get();
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions as needed
            e.printStackTrace();
            return null;
        }
        return content;
    }

    private List<String> fetchEssayFromUrl(String url) {
        rateLimiter.acquire();
        List<String> paragraphList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements paragraphs = document.select("p");
                for (Element paragraph : paragraphs)
                    paragraphList.add(paragraph.text());
            } catch (IOException e) {
                logger.error("Unable to fetch - {} error - {}",url,e.getMessage());
        }
        return paragraphList;
    }

}
