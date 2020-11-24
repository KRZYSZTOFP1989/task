package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoardsTest() {

        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1", "Test list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "testList", trelloListsDto));

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDto);

        //Than
        assertEquals(1, trelloBoards.size());
        assertEquals(1, trelloBoards.stream().mapToLong(board -> board.getLists().size()).sum());
    }

    @Test
    public void mapToBoardsDtoTest() {

        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "Test list", false));

        List<TrelloBoard> trelloBoard = new ArrayList<>();
        trelloBoard.add(new TrelloBoard("1", "testList", trelloLists));

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoard);

        //Than
        assertEquals(1, trelloBoardsDto.size());
        assertEquals(1, trelloBoardsDto.stream().mapToLong(board -> board.getLists().size()).sum());

    }

    @Test
    public void mapToListTest() {

        //Given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1", "Test list", false));
        trelloListsDto.add(new TrelloListDto("2", "Test list2", false));

        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListsDto);

        //Than
        assertEquals(2, trelloList.size());
        assertEquals(trelloListsDto.get(0).getId(), trelloList.get(0).getId());
        assertEquals(trelloListsDto.get(0).getName(), trelloList.get(0).getName());
        assertEquals(trelloListsDto.get(0).isClosed(), trelloList.get(0).isClosed());

    }

    @Test
    public void mapToListDtoTest() {

        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "Test list", false));
        trelloLists.add(new TrelloList("2", "Test list2", false));

        //When
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListDto(trelloLists);

        //Than
        assertEquals(2, trelloListsDto.size());
        assertEquals(trelloLists.get(0).getId(), trelloListsDto.get(0).getId());
        assertEquals(trelloLists.get(0).getName(), trelloListsDto.get(0).getName());
        assertEquals(trelloLists.get(0).isClosed(), trelloListsDto.get(0).isClosed());

    }

    @Test
    public void mapToCardDtoTest() {

        //Given
        TrelloCard trelloCard = new TrelloCard("Test", "Test description", "1", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Than
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getLisId(), trelloCardDto.getListId());
    }

    @Test
    public void mapToCardTest() {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Test description", "1", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Than
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getLisId());
    }

}