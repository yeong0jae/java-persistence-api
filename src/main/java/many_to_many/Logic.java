package many_to_many;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import many_to_many.entity.Member;
import many_to_many.entity.Product;

public class Logic {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-study");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        save(em);
        find(em);
        tx.commit();

        em.close();
        emf.close();
    }

    private static void save(EntityManager em) {

        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        member1.getProducts().add(productA); // 회원1이 상품A 구매 (연관관계 설정)
        em.persist(member1);

    }

    private static void find(EntityManager em) {
        Member member = em.find(Member.class, "member1");
        List<Product> products = member.getProducts(); // 객체 그래프 탐색
        for (Product product : products) {
            System.out.println("product.name = " + product.getName());
        }
    }
}
