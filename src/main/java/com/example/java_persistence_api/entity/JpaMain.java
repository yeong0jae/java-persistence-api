package com.example.java_persistence_api.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-study");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();
            logic(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void logic(EntityManager em) {

        // 등록
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("이름");
        member.setAge(2);

        em.persist(member);

        // 수정
        member.setAge(20);

        // 삭제
        em.remove(member);

        // 한 건 조회
        Member findMember = em.find(Member.class, id);

        // 목록 조회
        TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
        List<Member> members = query.getResultList();

    }

}
