package com.taskban.taskban_api.business;

import com.taskban.taskban_api.business.exceptions.BoardNotFoundException;
import com.taskban.taskban_api.business.exceptions.UnauthorizedAccessException;
import com.taskban.taskban_api.business.exceptions.UserNotFoundException;
import com.taskban.taskban_api.infrastructure.entities.Board;
import com.taskban.taskban_api.infrastructure.entities.User;
import com.taskban.taskban_api.infrastructure.repository.BoardRepository;
import com.taskban.taskban_api.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public BoardService(UserRepository userRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    public Board createBoard(Integer userId, String boardName) {

        User user = getUserOrThrow(userId);

        Board board = new Board();
        board.setUser(user);
        board.setName(boardName);

        return boardRepository.save(board);
    }

    public Board searchBoardById(Integer userId, Integer boardId) {

        getUserOrThrow(userId);

        Board board = getBoardOrThrow(boardId);

        if (!board.getUser().getId().equals(userId)) {
            throw new UnauthorizedAccessException(
                    "You are not allowed to access this board"
            );
        }

        return board;
    }


    public List<Board> searchBoardsByUser(Integer userId) {
        User user = getUserOrThrow(userId);

        return boardRepository.findAllByUserWithUser(userId);
    }

    public Board updateBoard(Integer userId, Integer boardId, String newName){
        User user = getUserOrThrow(userId);

        Board board = getBoardOrThrow(boardId);

        if(!board.getUser().getId().equals(user.getId())){
            throw new UnauthorizedAccessException("You are not allowed to perform this action on this board");
        }

        board.setName(newName);

        return boardRepository.save(board);
    }

    public void deleteBoard(Integer userId, Integer boardId) {
        User user = getUserOrThrow(userId);

        Board board = getBoardOrThrow(boardId);

        if (!board.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You are not allowed to perform this action on this board");
        }

        boardRepository.delete(board);
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id " + userId)
                );
    }

    private Board getBoardOrThrow(Integer boardId) {
        return boardRepository.findByIdWithUser(boardId)
                .orElseThrow(() ->
                        new BoardNotFoundException("Board not found with id " + boardId)
                );
    }

}


