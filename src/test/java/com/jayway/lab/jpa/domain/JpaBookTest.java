package com.jayway.lab.jpa.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayway.lab.jpa.util.EntityManagerUtil;

public class JpaBookTest
{
   private EntityManager em;

   @Before
   public void beforeEach()
   {
      em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
   }

   @After
   public void afterEach()
   {
      em.close();
   }

   @Test
   public void testAutoIncrement()
   {
      EntityTransaction transaction = em.getTransaction();
      transaction.begin();

      Book firstBook = new Book();
      Book secondBook = new Book();

      // IDs start as null
      Assert.assertEquals((Long) null, firstBook.getId());
      Assert.assertEquals((Long) null, secondBook.getId());

      em.persist(firstBook);
      em.persist(secondBook);

      transaction.commit();

      System.out.println("Object 0");
      System.out.println("Generated ID is: " + firstBook.getId());
      System.out.println("Generated Version is: " + firstBook.getVersion());
      

      System.out.println("Object 1");
      System.out.println("Generated ID is: " + secondBook.getId());
      System.out.println("Generated Version is: " + secondBook.getVersion());

      Assert.assertEquals((Long) 1l, firstBook.getId());
      Assert.assertEquals((Long) 2l, secondBook.getId());
   }

   @Test
   public void testRollbackAutoIncrement()
   {
      EntityTransaction transaction = em.getTransaction();
      transaction.begin();

      Book firstBook = new Book();
      Book secondBook = new Book();

      Assert.assertEquals((Long) null, firstBook.getId());
      Assert.assertEquals((Long) null, secondBook.getId());

      em.persist(firstBook);
      transaction.rollback();

      // Called outside of a transactio
      em.persist(secondBook);

      System.out.println("Object 0");
      System.out.println("Generated ID is: " + firstBook.getId());
      System.out.println("Generated Version is: " + firstBook.getVersion());

      System.out.println("Object 1");
      System.out.println("Generated ID is: " + secondBook.getId());
      System.out.println("Generated Version is: " + secondBook.getVersion());

      Assert.assertEquals((Long) 1l, firstBook.getId());
      Assert.assertEquals((Long) null, secondBook.getId());
   }
}