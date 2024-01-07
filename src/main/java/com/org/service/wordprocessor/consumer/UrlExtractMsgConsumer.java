package com.org.service.wordprocessor.consumer;

import com.org.service.wordprocessor.services.WordExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlExtractMsgConsumer {

    @Value("${extracted.data}")
    private String bucketUrl;

    @Autowired
    private WordExtractorService wordExtractorService;

    public void consumeMessage(List<String> urls){

        List<String> data = wordExtractorService.fetchData(urls);

        writeDataToS3(data);
        notifyJobComplete();
    }

    private  void writeDataToS3(List<String> data){

    }

    private  void notifyJobComplete(){
    }
}
