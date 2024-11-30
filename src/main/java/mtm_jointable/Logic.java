package mtm_jointable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import mtm_jointable.entity.Member;
import mtm_jointable.entity.MemberProduct;
import mtm_jointable.entity.MemberProductId;
import mtm_jointable.entity.Product;

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

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        em.persist(member1);

        Product productA = new Product();
        productA.setId("productA");
        productA.setName("상품A");
        em.persist(productA);

        MemberProduct memberProduct = new MemberProduct();
        memberProduct.setMember(member1);
        memberProduct.setProduct(productA);
        memberProduct.setOrderAmount(2);

        em.persist(memberProduct);
    }

    private static void find(EntityManager em) {

        MemberProductId memberProductId = new MemberProductId();
        memberProductId.setMember("member1");
        memberProductId.setProduct("productA");

        MemberProduct memberProduct = em.find(MemberProduct.class, memberProductId);

        Member member = memberProduct.getMember();
        Product product = memberProduct.getProduct();

        System.out.println("member = " + member.getUsername());
        System.out.println("product = " + product.getName());
        System.out.println("orderAmount = " + memberProduct.getOrderAmount());
    }
}
