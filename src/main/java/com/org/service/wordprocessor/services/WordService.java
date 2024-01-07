package com.org.service.wordprocessor.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

public interface WordService {

    void getTopWords(MultipartFile file, int topCount) throws JsonProcessingException;

}
