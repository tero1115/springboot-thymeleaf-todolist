package com.example.my.domain.todo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.my.common.dto.LoginUserDTO;
import com.example.my.common.dto.ResponseDTO;
import com.example.my.common.exception.BadRequestException;
import com.example.my.domain.todo.dto.ReqTodoTableInsertDTO;
import com.example.my.domain.todo.dto.ReqTodoTableUpdateDoneYnDTO;
import com.example.my.model.todo.entity.TodoEntity;
import com.example.my.model.todo.repository.TodoRepository;
import com.example.my.model.user.entity.UserEntity;
import com.example.my.model.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceApiV1 {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> getTodoTableData(LoginUserDTO loginUserDTO) {
        // TODO : 리파지토리에서 유저 기본키로 삭제되지 않은 할 일 목록 찾기
        // TODO : 응답 데이터로 리턴하기 (할 일 목록 조회에 성공하였습니다.)
        return null;
    }

    @Transactional
    public ResponseEntity<?> insertTodoTableData(ReqTodoTableInsertDTO dto, LoginUserDTO loginUserDTO) {
        // TODO : 할 일을 입력했는지 확인
        if (dto == null) {
            throw new BadRequestException("할 일을 입력하지 않았습니다.");
        }

        // TODO : 리파지토리에서 유저 기본키로 삭제되지 않은 유저 찾기
        Optional<UserEntity> userEntitoyOptional = userRepository.findById(loginUserDTO.getUser().getIdx());
        if (!userEntitoyOptional.isPresent()) {
            throw new BadRequestException("유저 정보가 없습니다.");
        }
        UserEntity userEntity = userEntitoyOptional.get();

        // TODO : 할 일 엔티티 생성
        TodoEntity todoEntity = new TodoEntity().builder()
                .content(dto.getTodo().getContent())
                .doneYn('N')
                .createDate(LocalDateTime.now())
                .userEntity(userEntity)
                .build();
        // TODO : 할 일 엔티티 저장
        todoRepository.save(todoEntity);
        // TODO : 응답 데이터로 리턴하기 (할 일 추가에 성공하였습니다.)

        // return null;
        return new ResponseEntity<>(
            ResponseDTO.builder()
                    .code(0)
                    .message("할 일 추가에 성공하였습니다.")
                    .build(),
            HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateTodoTableData(Long todoIdx, ReqTodoTableUpdateDoneYnDTO dto,
            LoginUserDTO loginUserDTO) {
        // TODO : 리파지토리에서 할 일 기본키로 삭제되지 않은 할 일 찾기
        Optional<TodoEntity> todoEntityOptional = todoRepository.findByIdxAndDeleteDateIsNull(todoIdx);
        
        // TODO : 할 일이 null이면 (존재하지 않는 할 일입니다.) 리턴
        if (!todoEntityOptional.isPresent()) {
            throw new BadRequestException("존재하지 않는 할 일 입니다.");
        }
        TodoEntity todoEntity = todoEntityOptional.get();

        // TODO : 할 일 작성자와 로그인 유저가 다르면 (권한이 없습니다. )리턴
        if (!todoEntity.getUserEntity().getIdx().equals(loginUserDTO.getUser().getIdx())) {
            throw new BadRequestException("권한이 없습니다.");
        }

        // TODO : 할 일 doneYn 업데이트
        todoEntity.setDoneYn(dto.getTodo().getDoneYn());
        todoRepository.save(todoEntity);

        // TODO : 응답 데이터로 리턴하기 (할 일 수정에 성공하였습니다.)
        return new ResponseEntity<>(
            ResponseDTO.builder()
                    .code(0)
                    .message("할 일 수정에 성공하였습니다.")
                    .build(),
            HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteTodoTableData(Long todoIdx, LoginUserDTO loginUserDTO) {
        // TODO : 리파지토리에서 할 일 기본키로 삭제되지 않은 할 일 찾기
        Optional<TodoEntity> todoEntityOptional = todoRepository.findByIdxAndDeleteDateIsNull(todoIdx);

        // TODO : 할 일이 null이면 (존재하지 않는 할 일입니다.) 리턴
        if (!todoEntityOptional.isPresent()) {
            throw new BadRequestException("존재하지 않는 할 일 입니다.");
        }
        TodoEntity todoEntity = todoEntityOptional.get();

        // TODO : 할 일 작성자와 로그인 유저가 다르면 (권한이 없습니다.) 리턴
        if (!todoEntity.getUserEntity().getIdx().equals(loginUserDTO.getUser().getIdx())) {
            throw new BadRequestException("권한이 없습니다.");
        }

        // TODO : 할 일 deleteDate 업데이트
        todoEntity.setDeleteDate(LocalDateTime.now());


        // TODO : 응답 데이터로 리턴하기 (할 일 삭제에 성공하였습니다.)
        return new ResponseEntity<>(
            ResponseDTO.builder()
                    .code(0)
                    .message("할 일 삭제에 성공하였습니다.")
                    .build(),
            HttpStatus.OK);
    }

}
