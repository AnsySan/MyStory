package com.clone.twitter.userservice.service.csv;

import com.clone.twitter.userservice.exception.DataValidationException;
import com.clone.twitter.userservice.exception.NotFoundException;
import com.clone.twitter.userservice.mapper.person.PersonMapper;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.json.student.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvFileConverter {

    private final CsvMapper csvMapper;
    private final CsvSchema schema = CsvSchema.emptySchema().withHeader();

    public List<Person> convertCsvToPerson(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DataValidationException("File cannot be empty");
        }
        try {
            MappingIterator<Map<String, String>> mappingIterator = csvMapper
                    .readerFor(Map.class)
                    .with(schema)
                    .readValues(file.getInputStream());

            List<Map<String, String>> csvData = mappingIterator.readAll();

            return mapToPersons(csvData);
        } catch (IOException e) {
            throw new NotFoundException("File not found: " + e);
        }
    }

    private List<Person> mapToPersons(List<Map<String, String>> csvData) {
        return csvData.stream()
                .map(PersonMapper.INSTANCE::toPerson)
                .collect(Collectors.toList());
    }
}