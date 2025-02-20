package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {
	public static void main(String [] args) {
	
		SellerDao sellerDao = DaoFactory.createSellerDao();
	
		
		System.out.println( "=== TEST 1: seller finfById ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		
		System.out.println( "\n=== TEST 2: seller finfById ===");
		Department department = new Department(2, null);
		List<Seller > list = sellerDao.findByDepartment(department);
		for(Seller obj : list ) {
			System.out.println(obj);
		}
		
		System.out.println( "\n=== TEST 3: seller finfAll ===");
		list = sellerDao.findAll();
		for(Seller obj : list ) {
			System.out.println(obj);
		}
		System.out.println( "\n=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "GAbiGol", "Gab@gmail.com", new Date(),4000.00 , department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new id = " + newSeller.getId() );
	
		System.out.println( "\n=== TEST 5: seller update ===");
		seller = sellerDao.findById(3);
		seller.setName("Joao");
		sellerDao.update(seller);
		System.out.println("Update Completed");
	
	}
		
		
	}

