package com.clone.twitter.userservice.service.csv;

import com.clone.twitter.userservice.exception.ConversionException;
import com.clone.twitter.userservice.exception.TimeOutException;
import com.clone.twitter.userservice.mapper.person.PersonMapper;
import com.clone.twitter.userservice.model.country.Country;
import com.clone.twitter.userservice.model.user.User;
import com.clone.twitter.userservice.repository.country.CountryRepository;
import com.clone.twitter.userservice.repository.user.UserRepository;
import com.clone.twitter.userservice.validator.csv.CsvValidator;
import com.json.student.Person;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CSVFileServiceImpl implements CSVFileService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final CsvValidator csvValidator;
    private final PersonMapper personMapper;

    @Qualifier("threadPoolForConvertCsvFile")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    @Transactional
    public void convertCsvFile(List<Person> persons) {
        ExecutorService executor = threadPoolTaskExecutor.getThreadPoolExecutor();
        List<CompletableFuture<Void>> futures = persons.stream()
                .map(person -> processPersonAsync(person, executor))
                .toList();

        waitForCompletion(futures, executor);
    }

    @Override
    @Transactional
    public CompletableFuture<Void> processPersonAsync(Person person, ExecutorService executor) {
        try {
            csvValidator.validate(person);
            User user = personMapper.toEntity(person);
            return CompletableFuture.runAsync(() -> setUpAndSaveToDB(user), executor);
        } catch (RejectedExecutionException e) {
            throw new TaskRejectedException("Task rejected due to executor termination", e);
        }
    }

    private void waitForCompletion(List<CompletableFuture<Void>> futures, ExecutorService executor) {
        try {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new ConversionException("Error during CSV file conversion, try again");
        } catch (TimeoutException e) {
            throw new TimeOutException("CSV file conversion timed out");
        } finally {
            shutdownExecutor(executor);
        }
    }

    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    @Transactional
    public void setUpAndSaveToDB(User user) {
        user.setPassword(generatePassword());
        assignCountryToUser(user);
        synchronized (this) {
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public void assignCountryToUser(User user) {
        String countryName = user.getCountry().getTitle();
        Country country = countryRepository.findByName(countryName)
                .orElseGet(() -> countryRepository.save(Country.builder().title(countryName).build()));

        country.getResidents().add(user);
        user.setCountry(country);

        if (country.getId() == 0) {
            countryRepository.save(country);
        }
    }

    private String generatePassword() {
        String upperCaseLetters = RandomStringUtils.random(4, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(4, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(4);
        String specialChar = RandomStringUtils.random(4, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(4);

        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + specialChar + totalChars;

        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.shuffle(pwdChars, RANDOM);

        return pwdChars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}