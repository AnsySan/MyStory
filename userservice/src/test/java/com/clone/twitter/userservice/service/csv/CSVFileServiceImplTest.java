package com.clone.twitter.userservice.service.csv;

import com.clone.twitter.userservice.validator.csv.CsvValidator;
import com.json.student.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CSVFileServiceImplTest {

    @Mock
    private CsvValidator csvValidator;

    @InjectMocks
    private CSVFileServiceImpl csvFileService;

    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        executorService = mock(ExecutorService.class);
    }

    @Test
    public void testProcessPersonAsync_ValidationFailed() {
        Person person = new Person();
        doThrow(new IllegalArgumentException()).when(csvValidator).validate(person);

        assertThrows(IllegalArgumentException.class, () -> csvFileService.processPersonAsync(person, executorService));
    }

    @Test
    public void testGeneratePassword() throws Exception {
        Method generatePasswordMethod = CSVFileServiceImpl.class.getDeclaredMethod("generatePassword");
        generatePasswordMethod.setAccessible(true);
        String password = (String) generatePasswordMethod.invoke(csvFileService);

        assertNotNull(password);
        assertEquals(20, password.length());
    }
}
