package generator;

import generator.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Generator {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-study");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Board board = new Board();
        em.persist(board);
        System.out.println("board.id = " + board.getId());
        tx.commit();

        em.close();

        emf.close();
    }

}
