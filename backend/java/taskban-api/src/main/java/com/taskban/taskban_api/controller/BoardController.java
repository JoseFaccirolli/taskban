package com.taskban.taskban_api.controller;

import com.taskban.taskban_api.business.BoardService;
import com.taskban.taskban_api.controller.dto.CreateBoardRequest;
import com.taskban.taskban_api.controller.dto.UpdateBoardRequest;
import com.taskban.taskban_api.infrastructure.entities.Board;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> listBoards(@PathVariable Integer userId) {
        return boardService.searchBoardsByUser(userId);
    }

    @GetMapping("/{boardId}")
    public Board getBoardSpecific(
            @PathVariable Integer userId,
            @PathVariable Integer boardId
    ){
        return boardService.searchBoardById(userId, boardId);
    }

    @PostMapping
    public Board createBoard(
            @PathVariable Integer userId,
            @RequestBody CreateBoardRequest request){
        return boardService.createBoard(userId, request.getName());
    }

    @PutMapping("/{boardId}")
    public Board updateBoard(
            @PathVariable Integer userId,
            @PathVariable Integer boardId,
            @RequestBody UpdateBoardRequest request
    ) {
        return boardService.updateBoard(userId, boardId, request.getName());
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(
            @PathVariable Integer userId,
            @PathVariable Integer boardId
    ){
        boardService.deleteBoard(userId, boardId);
    }

}

