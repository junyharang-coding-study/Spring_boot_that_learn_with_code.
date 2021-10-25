package org.junyharang.springboot.entity;

import lombok.*;

import javax.persistence.*;

@ToString @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "tbl_memo")
public class Memo {

    // DB Primary Key로 사용될 값을 설정하고, 키 생성 전략은 DB가 생성하도록 한다.(MariaDB인 경우 Auto increment)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long mno;

    // 해당 컬럼의 길이는 200으로 제한하고, null을 허용하지 않는다.
    @Column(length = 200, nullable = false) private String memoText;

} // class 끝
