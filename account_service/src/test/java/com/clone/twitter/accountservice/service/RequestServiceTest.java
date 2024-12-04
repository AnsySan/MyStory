package com.clone.twitter.accountservice.service;

import com.clone.twitter.accountservice.dto.RequestDto;
import com.clone.twitter.accountservice.mapper.RequestMapper;
import com.clone.twitter.accountservice.model.Request;
import com.clone.twitter.accountservice.model.RequestStatus;
import com.clone.twitter.accountservice.model.RequestType;
import com.clone.twitter.accountservice.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;

    @Mock
    private RequestRepository repository;

    @Spy
    private RequestMapper requestMapper;

    private RequestDto requestDto;
    private Request request;

    @BeforeEach
    void setUp() {
        requestDto = new RequestDto();
        requestDto.setRequestId(3L);
        requestDto.setUserId(2L);
        requestDto.setRequestStatus(RequestStatus.PENDING);
        requestDto.setVersion(1L);
        requestDto.setActive(true);
        requestDto.setLockValue("2L");
        requestDto.setRequestType(RequestType.TEST);
        requestDto.setInputData(new HashMap<>());

        request = new Request();
        request.setId(requestDto.getRequestId());
        request.setUserId(requestDto.getUserId());
        request.setRequestType(requestDto.getRequestType());
        request.setRequestStatus(requestDto.getRequestStatus());
        request.setVersion(requestDto.getVersion());
        request.setActive(request.isActive());
        request.setLockValue(requestDto.getLockValue());
        request.setInputData(requestDto.getInputData());
    }

    @Test
    void getRequest() {
        Mockito.when(repository.findById(requestDto.getRequestId()))
                .thenReturn(Optional.of(request));
        Mockito.when(requestMapper.toDto(request))
                .thenReturn(requestDto);

        RequestDto req = requestService.getRequest(requestDto.getRequestId());
        assertNotNull(req);
        assertEquals(requestDto, req);

    }

    @Test
    void getRequest_IdNotExist() {
        Mockito.when(repository.findById(requestDto.getRequestId()))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> requestService.getRequest(requestDto.getRequestId()));
    }

    @Test
    void getOrSaveRequest() {
        Mockito.when(repository.findById(requestDto.getRequestId()))
                .thenReturn(Optional.empty());
        Mockito.when(requestMapper.toDto(request))
                .thenReturn(requestDto);

        requestService.getOrSaveRequest(request);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void changeStatus() {
        Mockito.when(repository.findById(requestDto.getRequestId()))
                .thenReturn(Optional.of(request));
        Mockito.when(requestMapper.toDto(request))
                .thenReturn(requestDto);

        RequestDto expected = requestService.changeStatus(request);
        assertEquals(RequestStatus.PENDING, expected.getRequestStatus());

        Mockito.verify(repository, Mockito.times(1)).save(request);
    }
}