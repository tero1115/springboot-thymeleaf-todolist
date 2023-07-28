package com.example.my.domain.todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.common.exception.BadRequestException;
import com.example.my.domain.todo.dto.ReqTodoTableInsertDTO;
import com.example.my.domain.todo.dto.ReqTodoTableUpdateDoneYnDTO;
import com.example.my.domain.todo.service.TodoServiceApiV1;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoControllerApiV1 {

    private final TodoServiceApiV1 todoServiceApiV1;

    @GetMapping
    public ResponseEntity<?> getTodoTableData(HttpSession session) {
        // TODO : 서비스에서 할 일 목록 가져오기
        LoginUserDTO loginUserDTO = (LoginUserDTO)session.getAttribute("dto");

        return todoServiceApiV1.getTodoTableData(loginUserDTO);
    }

    @PostMapping
    public ResponseEntity<?> insertTodoTableData(
            @RequestBody ReqTodoTableInsertDTO dto,
            HttpSession session
    ) {
        // session에 dto가 없으면 badRequest처리
        LoginUserDTO userDTO = (LoginUserDTO)session.getAttribute("dto");
        if (userDTO == null) {
            new BadRequestException("유저 정보가 없습니다.");
        }

        // TODO : 서비스에서 할 일 추가하기
        return todoServiceApiV1.insertTodoTableData(dto, userDTO);
    }

    @PutMapping("/{todoIdx}")
    public ResponseEntity<?> updateTodoTableData(
            @PathVariable Long todoIdx,
            @RequestBody ReqTodoTableUpdateDoneYnDTO dto,
            HttpSession session
    ) {

        // session에 dto가 없으면 badRequest처리
        LoginUserDTO userDTO = (LoginUserDTO)session.getAttribute("dto");
        if (userDTO == null) {
            new BadRequestException("유저 정보가 없습니다.");
        }
        // TODO : 서비스에서 할 일 완료 수정하기
        return todoServiceApiV1.updateTodoTableData(todoIdx, dto, userDTO);

    }

    @DeleteMapping("/{todoIdx}")
    public ResponseEntity<?> deleteTodoTableData(
            @PathVariable Long todoIdx,
            HttpSession session
    ) {
        // session에 dto가 없으면 badRequest처리
        LoginUserDTO userDTO = (LoginUserDTO)session.getAttribute("dto");
        if (userDTO == null) {
            new BadRequestException("유저 정보가 없습니다.");
        }
        // TODO : 서비스에서 할 일 삭제하기
        return todoServiceApiV1.deleteTodoTableData(todoIdx, userDTO);
    }


}
