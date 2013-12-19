package com.jayway.lab.jpa.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	public static EntityManagerFactory getEntityManagerFactory() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("hsqldb-ds");
		return emf;
	}
}