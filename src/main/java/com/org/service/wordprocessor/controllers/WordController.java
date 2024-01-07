package com.org.service.wordprocessor.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.org.service.wordprocessor.constants.AppConstants;
import com.org.service.wordprocessor.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/word")
@Validated
public class WordController {

    @Qualifier("multi")
    @Autowired
    private WordService wordService;

    @PostMapping("/fetch-top")
    public ResponseEntity<?> fetchTopUsedWords(@RequestParam("file") MultipartFile file,
                                                    @RequestParam(name = "topCount", defaultValue = AppConstants.TOP_WORD_COUNT_DEFAULT_VALUE) int topCount) throws JsonProcessingException {

        wordService.getTopWords(file,topCount);
        return ResponseEntity.ok(true);
    }


}
