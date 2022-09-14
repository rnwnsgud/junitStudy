package junitStudy.junit.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

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

    // 3. 책 한건 보기

    // 4. 책 수정

    // 5. 책 삭제

}