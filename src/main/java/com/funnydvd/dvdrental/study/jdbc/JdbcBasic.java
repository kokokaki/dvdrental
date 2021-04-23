package com.funnydvd.dvdrental.study.jdbc;

//db관련 패키지: java.sql
import java.sql.*;
import java.util.*;

public class JdbcBasic {

    //데이터베이스 연결 접속정보
    private String id = "sqld";
    private String pw = "1234";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe"; //db서버 위치 : 1521
    private String driverClassName = "oracle.jdbc.driver.OracleDriver"; //오라클 연동클래스 이름

    //INSERT문을 실행하는 메서드
    public boolean save(JdbcPractice jp) {

        Connection connection = null;

        try {
            //드라이버 클래스 로딩
            Class.forName(driverClassName);

            //연결정보 객체 생성
            connection = DriverManager.getConnection(url, this.id, pw);

            //SQL실행
            String query = "INSERT INTO jdbc_practice VALUES (?, ?, ?)";
            //SQL실행을 위한 PreparedStatement객체
            PreparedStatement statement = connection.prepareStatement(query);

            // 쿼리의 ?값 세팅
            statement.setInt(1, jp.getId());
            statement.setString(2, jp.getName());
            statement.setString(3, jp.getAddr());

            //SQL실행 명령
            //SELECT : executeQuery()
            //INSERT, UPDATE, DELETE : executeUpdate()
            //resultNum : 성공하면 1, 실패하면 0
            int resultNum = statement.executeUpdate();
            return resultNum == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //DB접속 해제: 빈번한 데이터베이스 트랜잭션이 생길 때마다
            //메모리 과부하가 걸릴 가능성이 있음.
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //UPDATE
    public boolean modify(JdbcPractice jp) {

        // try with resource : 자원 해제를 자동처리 (Auto Closable)
        try (Connection connection = DriverManager.getConnection(url, this.id, pw)) {

            //드라이버 클래스 로딩
            Class.forName(driverClassName);

            //SQL실행
            String query = "UPDATE jdbc_practice " +
                            "SET name = ?, addr = ? " +
                            "WHERE id = ?";
            //SQL실행을 위한 PreparedStatement객체
            PreparedStatement statement = connection.prepareStatement(query);

            // 쿼리의 ?값 세팅
            statement.setString(1, jp.getName());
            statement.setString(2, jp.getAddr());
            statement.setInt(3, jp.getId());

            //SQL실행 명령
            //SELECT : executeQuery()
            //INSERT, UPDATE, DELETE : executeUpdate()
            //resultNum : 성공하면 1, 실패하면 0
            int resultNum = statement.executeUpdate();
            return resultNum == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //DELETE
    public boolean remove(int id) {

        // try with resource : 자원 해제를 자동처리 (Auto Closable)
        try (Connection connection = DriverManager.getConnection(url, this.id, pw)) {

            //드라이버 클래스 로딩
            Class.forName(driverClassName);

            //SQL실행
            String query = "DELETE FROM jdbc_practice WHERE id = ?";
            //SQL실행을 위한 PreparedStatement객체
            PreparedStatement statement = connection.prepareStatement(query);

            // 쿼리의 ?값 세팅
            statement.setInt(1, id);

            //SQL실행 명령
            //SELECT : executeQuery()
            //INSERT, UPDATE, DELETE : executeUpdate()
            //resultNum : 성공하면 1, 실패하면 0
            int resultNum = statement.executeUpdate();
            return resultNum == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //다중행 SELECT
    public List<JdbcPractice> findAll() {

        List<JdbcPractice> resultList = new ArrayList<>();

        // try with resource : 자원 해제를 자동처리 (Auto Closable)
        try (Connection connection = DriverManager.getConnection(url, this.id, pw)) {

            //드라이버 클래스 로딩
            Class.forName(driverClassName);

            //SQL실행
            String query = "SELECT * FROM jdbc_practice ORDER BY id";
            //SQL실행을 위한 PreparedStatement객체
            PreparedStatement statement = connection.prepareStatement(query);

            //SQL실행 명령
            //SELECT : executeQuery()
            //INSERT, UPDATE, DELETE : executeUpdate()
            //ResultSet :  SELECT 결과 2차원 테이블
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {

                JdbcPractice jb = new JdbcPractice(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("addr"));

                resultList.add(jb);
            }
            return resultList;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); //빈리스트 리턴
        }
    }

    //단일행 SELECT
    public JdbcPractice findOne(int id) {

        // try with resource : 자원 해제를 자동처리 (Auto Closable)
        try (Connection connection = DriverManager.getConnection(url, this.id, pw)) {

            //드라이버 클래스 로딩
            Class.forName(driverClassName);

            //SQL실행
            String query = "SELECT * FROM jdbc_practice WHERE id = ?";
            //SQL실행을 위한 PreparedStatement객체
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            //SQL실행 명령
            //SELECT : executeQuery()
            //INSERT, UPDATE, DELETE : executeUpdate()
            //ResultSet :  SELECT 결과 2차원 테이블
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                return new JdbcPractice(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("addr"));
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //테이블 행수 count 조회 :  SELECT COUNT(*) FROM jdbc_practice;
    public int getCount() {

        // try with resource : 자원 해제를 자동처리 (Auto Closable)
        try (Connection connection = DriverManager.getConnection(url, this.id, pw)) {

            //드라이버 클래스 로딩
            Class.forName(driverClassName);

            //SQL실행
            String query = "SELECT COUNT(*) AS cnt FROM jdbc_practice";
            //SQL실행을 위한 PreparedStatement객체
            PreparedStatement statement = connection.prepareStatement(query);

            //SQL실행 명령
            //SELECT : executeQuery()
            //INSERT, UPDATE, DELETE : executeUpdate()
            //ResultSet :  SELECT 결과 2차원 테이블
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                return rs.getInt("cnt");
            } else {
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
