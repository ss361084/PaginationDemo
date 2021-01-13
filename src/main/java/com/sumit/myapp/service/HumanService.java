package com.sumit.myapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sumit.myapp.model.Human;
import com.sumit.myapp.repository.HumanRepo;

@Service
public class HumanService {

	@Autowired
	private HumanRepo humanRepo;
	@Autowired
	private EntityManager entityManager;
	
	public void saveHuman(Human human) {
		humanRepo.save(human);
	}
	
	public List<Human> getAllHuman() {
		List<Human> list = new ArrayList<>();
		list = humanRepo.findAll();
		return list.isEmpty() ? null : list;
	}
	
	public Human getHumanById(long humanId) {
		return humanRepo.findById(humanId).get();
	}
	
	public void deleteHuman(Human human) {
		humanRepo.delete(human);
	}
	
	public List<Human> getAllHumanByDescOrder(){
		List<Human> list = new ArrayList<>();
		try {
			Session session = entityManager.unwrap(Session.class);
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Human> criteriaQuery = criteriaBuilder.createQuery(Human.class);
			Root<Human> root = criteriaQuery.from(Human.class);
			criteriaBuilder.selectCase(root);
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("humanId")));
			list = session.createQuery(criteriaQuery).list();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return list.isEmpty() ? null : list ;
	}
}
