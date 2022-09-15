package junitStudy.junit.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
//@SpringBootTest
//@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    //@BeforeAll 테스트 시작 전에 한번만 실행
    @BeforeEach // 각 테스트 시작 전에 한번씩 실행
    public void dataSetting() {
        String title = "junit5";
        String author = "구준형";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);

    }

    // 1. 책 등록
    @Test
    void bookResister() {
        //given
        String title = "junit5";
        String author = "구준형";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        //when
        Book bookPS = bookRepository.save(book); //persistence 영구적인

        //then
        assertThat(bookPS.getTitle()).isEqualTo(title);
        assertThat(bookPS.getAuthor()).isEqualTo(author);

    }

    // 2. 책 목록 보기
    @Test
    void findBookList() {
        //given
        String title = "junit5";
        //when
        List<Book> booksPS = bookRepository.findAll();
        //then
        assertThat(booksPS.get(0).getTitle()).isEqualTo(title);
    }

    // 3. 책 한권 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    void findOneBook() {
        //given
        String title = "junit5";

        //when
        Book bookPS = bookRepository.findById(1L).get();

        //then
        assertThat(bookPS.getTitle()).isEqualTo(title);
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    void bookDelete() {
        //given
        Long id = 1L;

        //when
        bookRepository.deleteById(id);

        //then
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 5. 책 수정

}