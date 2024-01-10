# word-processor-service

🤖 Designed to handle and analyze large datasets, performing diverse operations in a distributed and efficient manner.

- [Tech Stack Used](#tech-stack-used)
- [Key Features](#key-features)
- [Getting Started](#getting-started)
- [Problem Statement and Solution](#problem-statement-and-solution)
- [Code Walkthrough](#code-walkthrough)

## Tech Stack Used

![Languages](https://skillicons.dev/icons?i=java,spring)
<img src="https://www.vectorlogo.zone/logos/apache_spark/apache_spark-ar21.svg" />


## Key Features

1. **Retrieve blog data from a collection of URLs** ( Two implementations )
    - Implementation with a single instance and multiple threads (Suitable for vertical scaling but may not be efficient as URLs grow)
    - Implementation with multiple instances using a consumer-producer model (Ideal for handling growing URLs and highly scalable)
2. **Count Words Occurrence**
    - Count occurrences in file/folder
    - Count occurrences in a large string
3. **Create Word Bank ( CRUD )**
   
4. **Rule Engine for validations**


## Getting Started

To run the service, follow these simple steps:

1. **Installation**:

```shell
mvn clean install
```

2. **Run**
```shell
run docker/Dockerfile
```

## Problem Statement and Solution



**Detail Document:-  <a href="https://docs.google.com/document/d/11LU7BgeFNsn4AYcFJ3fQsnOjFdf0oSK8_JVCNgtK1zs/edit?usp=sharing">Link</a>**

## Code Walkthrough




<meta name="google-site-verification" content="ELzrozM10QuultXORSnMTPaz4NAAnm8EvJt_6fqaAe4" />
