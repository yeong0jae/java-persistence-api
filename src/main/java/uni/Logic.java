package uni;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import uni.entity.Member;
import uni.entity.Team;

public class Logic {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-study");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        save(em);
        biDirection(em);
        tx.commit();

        em.close();

        emf.close();
    }

    private static void save(EntityManager em) {

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);

        team1.getMembers().add(member1); // 양방향 연관관계 설정 (일대다 단방향 추가)
        team1.getMembers().add(member2);
    }

    private static void findObjectGraph(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        Team team = member.getTeam(); // 객체 그래프 탐색
        System.out.println("팀 이름 = " + team.getName());
    }

    private static void queryLogicJoin(EntityManager em) {

        String jpql = "select m from Member m join m.team t where t.name=:teamName";

        List<Member> resultList = em.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    private static void updateRelation(EntityManager em) {

        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        member.setTeam(team2); // 연관관계 수정 member1 -> team2

    }

    private static void deleteRelation(EntityManager em) {

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // 연관관계 제거 member1 -> team1

    }

    private static void biDirection(EntityManager em) {

        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
    }

}
