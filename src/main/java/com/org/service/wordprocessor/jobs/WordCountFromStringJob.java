package com.org.service.wordprocessor.jobs;


import com.org.service.wordprocessor.enums.ValidatorProcess;
import com.org.service.wordprocessor.rules.executor.ValidWordEvaluator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class WordCountFromStringJob implements Serializable {

    @Autowired
    JavaSparkContext sc;

    @Autowired
    ValidWordEvaluator validWordEvaluator;

    private static final Pattern SPACE = Pattern.compile(" ");


    public Map<String, Long> executeJob(List<String> essayList, ValidatorProcess process) {

        JavaRDD<String> essays = sc.parallelize(essayList);

        return essays
                .mapPartitionsToPair(iterator -> {
                    Stream<String> stream = StreamSupport.stream(((Iterable<String>) () -> iterator).spliterator(), false);
                    return stream
                            .flatMap(line -> Arrays.asList(SPACE.split(line.toLowerCase())).stream())
                            .map(word -> new Tuple2<>(word, 1))
                            .iterator();
                }).reduceByKey(Integer::sum).countByKey().entrySet().
                stream().filter( word -> validWordEvaluator.evaluateRules(process, word.getKey())).
                collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

    }

}
