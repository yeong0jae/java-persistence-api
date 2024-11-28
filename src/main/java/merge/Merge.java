package merge;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import merge.entity.Member;

public class Merge {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-study");

    public static void main(String[] args) {

        Member member = createMember("memberA", "회원1");

        member.setUsername("회원명변경");

        mergeMember(member);
    }

    private static Member createMember(String id, String username) {
        // 영속성 컨텍스트1 시작
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setAge(2);

        em1.persist(member);
        tx1.commit();

        em1.close(); // 영속성 컨텍스트1 종료, member 엔티티는 준영속 상태가 된다.

        return member; // 준영속 상태의 member 엔티티 반환
    }

    private static void mergeMember(Member member) {
        // 영속성 컨텍스트2 시작
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        Member mergeMember = em2.merge(member); // 준영속 상태의 엔티티를 영속 상태로 변경하고 반환한다. (select 쿼리가 나간다.)
        tx2.commit(); // 변경 감지(Dirty Checking)으로 인한 update 쿼리가 이 때 나간다.

        // 준영속 상태
        System.out.println("member = " + member.getUsername());

        // 영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());

        // 동일성 비교
        System.out.println("member == mergeMember: " + (member == mergeMember));

        System.out.println("em2 contains member = " + em2.contains(member));
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));

        em2.close(); // 영속성 컨텍스트2 종료
    }
}
