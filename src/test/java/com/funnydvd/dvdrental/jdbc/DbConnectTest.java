package com.funnydvd.dvdrental.jdbc;

import com.funnydvd.dvdrental.study.jdbc.JdbcBasic;
import com.funnydvd.dvdrental.study.jdbc.JdbcPractice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DbConnectTest {

    //데이터베이스 연결 접속정보
    private String id = "sqld";
    private String pw = "1234";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe"; //db서버 위치 : 1521
    private String driverClassName = "oracle.jdbc.driver.OracleDriver"; //오라클 연동클래스 이름

    @Test
    @DisplayName("데이터베이스 연결에 성공해야 한다.")
    void connectionTest() {

        try {
            //오라클 드라이버 클래스 로딩
            Class.forName(driverClassName);

            //연결정보 객체 생성 (Connection)
            Connection conn = DriverManager.getConnection(url, id, pw);
            System.out.println("연결 성공!");

        } catch (Exception e) {
            System.out.println("연결 실패!");
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("테이블에 저장을 성공적으로 수행해야 한다.")
    void insertTest() {

        JdbcBasic jb = new JdbcBasic();
        boolean result = jb.save(new JdbcPractice(4, "망둥어", "대전시 서구"));

        //단언
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("테이블에 수정을 성공적으로 수행해야 한다.")
    void updateTest() {

        JdbcBasic jb = new JdbcBasic();
        boolean result = jb.modify(new JdbcPractice(2, "수정테스트", "성남시 수정구"));

        //단언
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("테이블에 삭제를 성공적으로 수행해야 한다.")
    void deleteTest() {

        JdbcBasic jb = new JdbcBasic();
        boolean result = jb.remove(1);

        //단언
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("전체 행을 조회해야 한다.")
    void selectAllTest() {

        JdbcBasic jb = new JdbcBasic();
        List<JdbcPractice> list = jb.findAll();

        for (JdbcPractice jdbcPractice : list) {
            System.out.println("jdbcPractice = " + jdbcPractice);
        }

        Assertions.assertTrue(list.size() == 3);

    }

    @Test
    @DisplayName("특정 id를 가진 행을 조회해야 한다.")
    void selectOneTest() {

        JdbcBasic jb = new JdbcBasic();

        JdbcPractice findOne = jb.findOne(3);
        System.out.println("findOne = " + findOne);

        int count = jb.getCount();
        System.out.println("count = " + count);

        Assertions.assertTrue(findOne.getName().equals("고길동"));

    }

}
