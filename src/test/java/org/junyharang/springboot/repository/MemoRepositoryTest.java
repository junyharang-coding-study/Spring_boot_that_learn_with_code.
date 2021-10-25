package org.junyharang.springboot.repository;

import org.junit.jupiter.api.Test;
import org.junyharang.springboot.entity.Memo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest public class MemoRepositoryTest {

    @Autowired MemoRepository memoRepository;

    @Test public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    } // testClass() 끝

    @Test public void testInsertDummies() { // 100개의 새로운 Memo 객체를 생성

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("테스트 중 입니다!" + i).build();
            memoRepository.save(memo);
        });
    } // testInsertDummies() 끝

    @Test public void testfindById() {

        //DB에 존재하는 mno
        long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("========================================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        } // if문 끝
    } // testSelect() 끝

    @Transactional @Test
    public void testGetOne() {

        // DB에 존재하는 mno;
        long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("=======================================");

        System.out.println(memo);
    } // testGetOne() 끝

    @Test public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("메모를 수정할게요!").build();

        System.out.println(memoRepository.save(memo));
    } // testUpdate() 끝

    @Test public void testDelete() {

        long mno = 100L;

        memoRepository.deleteById(mno);
    } // testDelete() 끝

    @Test public void testPageDefault() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageRequest);

        System.out.println(result);

    } // testPageDefault() 끝

    @Test public void testPageDefault_1() {
        PageRequest pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("==========================================");

        // 총 페이지 수 출력
        System.out.println("총 페이지 = " + result.getTotalPages());

        // 전체 개수 출력
        System.out.println("전체 개수 = " + result.getTotalElements());

        // 현재 페이지 번호 (0부터 시작)
        System.out.println("현재 페이지 번호 = " + result.getNumber());

        // 페이지 당 Data 개수
        System.out.println("Page 크기 = " + result.getSize());

        // 다음 페이지 존재 여부
        System.out.println("다음 페이지가 있니? = " + result.hasNext());

        // 시작 페이지(0)인지 여부
        System.out.println("첫번째 Page야?? " + result.isFirst());

        System.out.println("----------------------------------------");

        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        } // for 문 끝
    } // testPageDefault() 끝

    @Test public void testSort() {

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();

        // 위의 조건을 연결한다.
        Sort sortAll = sort1.and(sort2);

        PageRequest pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    } // testSort() 끝

    @Test public void testQuerymethods() {

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        } // for 문 끝

    } // testQuerymethods() 끝

    @Test public void testQueryMethodWithPageable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    } // testQueryMethodWithPageable() 끝

    @Commit @Transactional @Test
    public void testDeleteQueryMethods() {

        memoRepository.deleteMemoByMnoLessThan(10L);

    } // testDeleteQueryMethods() 끝
} // class 끝