package com.org.service.wordprocessor;

import com.org.service.wordprocessor.config.SerializableCountWordRddCallback;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WordProcessorApplication {

	@Bean
	public JavaSparkContext javaSparkContext() {
		SparkConf conf = new SparkConf().setAppName("wordcount").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		return sc;
	}

	@Bean
	SerializableCountWordRddCallback count() {
		return new SerializableCountWordRddCallback();
	}

	public static void main(String[] args) {
		SpringApplication.run(WordProcessorApplication.class, args)
				.getBean(WordProcessorApplication.class);
	}



}