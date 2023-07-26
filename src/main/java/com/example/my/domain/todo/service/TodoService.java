package com.example.my.domain.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.domain.todo.dto.ResTodoTableDTO;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public ResTodoTableDTO getTodoTableData(LoginUserDTO loginUserDTO) {
        // TODO : 리파지토리에서 유저 기본키로 삭제되지 않은 할 일 목록 찾기
        List<TodoEntity> todoEntityList = todoRepository.findByUserEntity_IdxAndDeleteDateIsNull(loginUserDTO.getUser().getIdx());
        
        ResTodoTableDTO dto = ResTodoTableDTO.of(todoEntityList);
        return dto;
    }

}
