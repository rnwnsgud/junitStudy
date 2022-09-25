package junitStudy.junit.web;

import junitStudy.junit.service.BookService;
import junitStudy.junit.web.dto.response.BookListRespDto;
import junitStudy.junit.web.dto.response.BookRespDto;
import junitStudy.junit.web.dto.request.BookSaveReqDto;
import junitStudy.junit.web.dto.response.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        System.out.println("bindingResult = " + bindingResult.hasErrors());


        //AOP 처리하는 게 좋음
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage()); //(titleORAuthor, erroMsg)
                System.out.println("errorMap = " + errorMap.toString());
            }

            throw new RuntimeException(errorMap.toString()) ;

        }

        BookRespDto bookRespDto = bookService.resisterBook(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build()
                ,HttpStatus.CREATED); // 201  = insert

    }

    // 1. 책 등록
    @PostMapping("/api/v2/book")
    public ResponseEntity<?> saveBook2(@RequestBody BookSaveReqDto bookSaveReqDto) {

        BookRespDto bookRespDto = bookService.resisterBook(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookRespDto).build()
                ,HttpStatus.CREATED); // 201  = insert

    }

    // 2. 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookListRespDto = bookService.checkBookList();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록 가져오기 성공").body(bookListRespDto).build()
                ,HttpStatus.OK); // 200  = ok
    }

    // 3. 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getOneBook(@PathVariable Long id) {
        BookRespDto bookRespDto = bookService.checkOneBook(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 한건보기 성공").body(bookRespDto).build()
                ,HttpStatus.OK); // 200  = ok
    }

    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 한건보기 성공").body(null).build()
                ,HttpStatus.OK); // 204  = 요청했는데 보내줄 컨텐트 body?는 없음
    }

    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage()); //(titleORAuthor, erroMsg)
                System.out.println("errorMap = " + errorMap.toString());
            }

            throw new RuntimeException(errorMap.toString()) ;

        }

        BookRespDto bookRespDto = bookService.editBook(id, bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 수정정성공").body(bookSaveReqDto).build()
                ,HttpStatus.OK);
    }
}
