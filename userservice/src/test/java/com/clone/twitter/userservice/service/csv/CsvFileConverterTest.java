package com.clone.twitter.userservice.service.csv;

import com.clone.twitter.userservice.exception.DataValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvFileConverterTest {

    @Mock
    private MultipartFile file;

    @InjectMocks
    CsvFileConverter converter;

    @Test
    void testCallCreateUserServiceWhenCreateUserIsInvoked() {
        when(file.isEmpty()).thenReturn(true);
        assertThrows(DataValidationException.class, () -> converter.convertCsvToPerson(file));
    }
}
