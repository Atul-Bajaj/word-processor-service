package com.org.service.wordprocessor.jobs;



import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.rules.executor.ValidWordEvaluator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UniqueWordFromFileJob {


    @Autowired
    JavaSparkContext sc;

    @Autowired
    ValidWordEvaluator validWordEvaluator;


    private static final Pattern SPACE = Pattern.compile(" ");


    public Set<String> executeJob(String file) {
        JavaRDD<String> lines = sc.textFile(file);
        return lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator()).collect().stream().
                filter(word -> validWordEvaluator.evaluateRules(ValidatorProcess.LOAD_WORDS, word)).
                collect(Collectors.toSet());
    }

}
