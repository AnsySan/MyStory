package com.clone.twitter.userservice.service.csv;

import com.clone.twitter.userservice.model.user.User;
import com.json.student.Person;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public interface CSVFileService {

    void convertCsvFile(List<Person> persons);

    CompletableFuture<Void> processPersonAsync(Person person, ExecutorService executor);

    void setUpAndSaveToDB(User user);

    void assignCountryToUser(User user);
}