package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private TrelloBadgesDto trelloBadgesDto;

    @Test
    public void shouldFetchTrelloBoards() {

        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "Test list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "Test list", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloService.fetchTrelloBoards();

        //Than
        assertNotNull(trelloBoardsDto);
        assertEquals(1, trelloBoardsDto.size());

    }

    @Test
    public void shouldFetchEmptyList() {

        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "Test list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "Test list", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloService.fetchTrelloBoards();

        //Than
        assertNotNull(trelloBoardsDto);
        assertEquals(0, trelloBoardsDto.size());

    }

    @Test
    public void shouldCreateTrelloCard() {

        //Given
        TrelloCardDto trelloCard = new TrelloCardDto("Test", "Test description,", "1", "1", null);
        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto("1", "Test", "testurl.com");

        when(trelloClient.createNewCard(trelloCard)).thenReturn(createdTrelloCard);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");

        //When
        CreatedTrelloCardDto createdTrelloCardDto = trelloService.createTrelloCard(trelloCard);

        //Than
        assertEquals("1", createdTrelloCardDto.getId());
        assertEquals("Test", createdTrelloCardDto.getName());
        assertEquals("testurl.com", createdTrelloCardDto.getShortUrl());

    }

}