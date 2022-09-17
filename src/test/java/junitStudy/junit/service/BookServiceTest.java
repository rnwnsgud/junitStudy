package junitStudy.junit.service;

import junitStudy.junit.domain.Book;
import junitStudy.junit.domain.BookRepository;
import junitStudy.junit.util.MailSender;
import junitStudy.junit.util.MailSenderStub;
import junitStudy.junit.web.dto.BookRespDto;
import junitStudy.junit.web.dto.BookSaveReqDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 가짜 메모리 환경 생성
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    // 가짜 환경에 띄우기
    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    void resisterBook() {
        //given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit강의");
        dto.setAuthor("메타코딩");

        //stub (가설)
        //실행결과를 미리 정의한다
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        //when
        BookRespDto bookRespDto = bookService.resisterBook(dto);
        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());

    }

    @Test
    void viewBookList() {

        //given

        //stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "겟인데어"));
        when(bookRepository.findAll()).thenReturn(books);

        //when
        List<BookRespDto> dtoList = bookService.checkBookList();
        //print
        dtoList.stream().forEach(dto -> {
            System.out.println("====================== 테스트");
            System.out.println("dto id= " + dto.getId());
            System.out.println("dto title= " + dto.getTitle());
        });

        //then
        assertThat("junit강의").isEqualTo(dtoList.get(0).getTitle());
    }

    @Test
    void viewOneBook() {
        //given
        Long id = 1L;
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        //stub
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.checkOneBook(id);

        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void editBook() {
        //given
        Long id = 1L;

        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("spring강의");
        bookSaveReqDto.setAuthor("겟인데어");
        //stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        //when
        BookRespDto bookRespDto = bookService.editBook(id, bookSaveReqDto);

        //then
        assertThat(bookRespDto.getTitle()).isEqualTo(bookSaveReqDto.getTitle());


    }

}