/*
 * Name: Paul Reisch
 * Matrikelnummer: 1529144
 */

package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import domain.product.IProduct;
import domain.product.Product;
import domain.product.SimpleProduct;
import domain.product.JointProduct;
import domain.product.ExtendedProduct;

import domain.Table;
import domain.Order;

public class Restaurant {

	// Attribute
	private String rest_name;
	private List<IProduct> Li_Pro = new ArrayList<IProduct>();
	private List<Table> Li_Tab = new ArrayList<Table>();
	private List<Order> Li_Ord = new ArrayList<Order>();
	private int Orderid = 0;

	// Konstruktor
	Restaurant(String name) {
		this.rest_name = name;
	}

	// Methoden
	public String getName() {
		return this.rest_name;
	}

	public boolean createTable(String tableIdentifier) {
		for (Iterator<Table> iterator = this.Li_Tab.iterator(); iterator.hasNext();) {
			Table it = iterator.next();
			if (it.getTableIdentifier() == tableIdentifier)
				return false;
		}
		this.Li_Tab.add(new Table(tableIdentifier));
		return true;
	}

	public Table getSpecificTable(String identifier) {
		for (Iterator<Table> iterator = this.Li_Tab.iterator(); iterator.hasNext();) {
			Table t = iterator.next();
			if (t.getTableIdentifier() == identifier)
				return t;
		}
		return null;
	}

	public boolean addProduct(IProduct product) throws DuplicateProductException {
		if (product == null)
			return false;

		else if (containsProduct(product) == true)
			throw new DuplicateProductException(product);

		else {
			this.Li_Pro.add(product);
			return true;
		}
	}

	public boolean addProduct(Collection<IProduct> products) throws DuplicateProductException {

		for (Iterator<IProduct> iterator = products.iterator(); iterator.hasNext();) {
			IProduct Ip = iterator.next();
			if (Ip == null)

				if (containsProduct(Ip))
					throw new DuplicateProductException(Ip);

				else {
					this.Li_Pro.add(Ip);
					return true;
				}
		}
		return false;
	}

	public List<IProduct> getProducts() {
		List<IProduct> list = new ArrayList<IProduct>();

		for (Iterator<IProduct> iterator = this.Li_Pro.iterator(); iterator.hasNext();) {
			list.add(iterator.next());
		}
		return list;
	}

	public boolean orderProductForTable(Table table, IProduct product) {
		if (table == null || product == null)
			return false;
		else if (this.Li_Pro.contains(product) && this.Li_Tab.contains(table)) {
			List<IProduct> list = new ArrayList<IProduct>();
			list.add(product);
			Order newOrder = new Order(this.Orderid++, table, list);
			Li_Ord.add(newOrder);
			return true;
		}
		return false;
	}

	public boolean orderProductForTable(Table table, IProduct product, int count) {
		if (table == null || product == null || count == 0)
			return false;
		else if (this.Li_Pro.contains(product) && this.Li_Tab.contains(table)) {
			List<IProduct> list = new ArrayList<IProduct>();
			for (int i = 0; i < count; i++)
				list.add(product);
			Order newOrder = new Order(this.Orderid++, table, list);
			Li_Ord.add(newOrder);
			return true;
		}
		return false;
	}

	public boolean containsProduct(IProduct compareProduct) {

		for (Iterator<IProduct> iterator = this.Li_Pro.iterator(); iterator.hasNext();) {
			IProduct it = iterator.next();

			if (compareProduct.equals(it) && compareProduct.getName() == it.getName()) {
				return true;
			} else
				continue;
		}
		return false;
	}

	public IProduct findProduct(String productName) {
		for (Iterator<IProduct> iterator = this.Li_Pro.iterator(); iterator.hasNext();) {
			IProduct element = iterator.next();
			if (element.getName().equals(productName))
				return element;
		}
		return null;
	}

	public static List<IProduct> generateSimpleProducts() {
		List<IProduct> simpleprod = new ArrayList<IProduct>();
		simpleprod.add(new SimpleProduct("Hamburger", (float) 7.90));
		simpleprod.add(new SimpleProduct("Schnitzel", (float) 8.90));
		simpleprod.add(new SimpleProduct("Bernerw�rstel", (float) 6.90));
		simpleprod.add(new SimpleProduct("Pommes", (float) 2.90));
		simpleprod.add(new SimpleProduct("Salat", (float) 3.90));

		//System.out.println(simpleprod);
		return simpleprod;
	}

	public static List<IProduct> generateJointProducts() {

		List<IProduct> jointprod = new ArrayList<IProduct>();
		List<Product> hp1 = new ArrayList<Product>();
		List<Product> hp2 = new ArrayList<Product>();
		List<Product> hp3 = new ArrayList<Product>();

		// Vorspeisen:
		SimpleProduct vs1 = new SimpleProduct("Gulaschsuppe", (float) 3.90);
		SimpleProduct vs2 = new SimpleProduct("Frittatensuppe", (float) 2.90);
		SimpleProduct vs3 = new SimpleProduct("gemischter Salat", (float) 3.40);

		// Hauptspeisen:
		SimpleProduct shp1 = new SimpleProduct("Hamburger", (float) 7.90);
		SimpleProduct shp2 = new SimpleProduct("Schnitzel", (float) 8.90);
		SimpleProduct shp3 = new SimpleProduct("Bernerw�rstel", (float) 6.90);


		// Beilagen:
		SimpleProduct bl1 = new SimpleProduct("Pommes", (float) 1.09);
		SimpleProduct bl2 = new SimpleProduct("Salat", (float) 1.09);

		// Getr�nke:
		SimpleProduct g1 = new SimpleProduct("Apfelsaft", (float) 1.90);
		SimpleProduct g2 = new SimpleProduct("Orangensaft", (float) 1.90);

		// Hauptspeisen-kombo:
		hp1.add(shp1);
		hp1.add(bl1);
		hp1.add(g1);
		JointProduct jp1 = new JointProduct("Hamburger und Pommes", 5, hp1);

		hp2.add(shp2);
		hp2.add(bl1);
		hp2.add(bl2);
		hp2.add(g2);
		JointProduct jp2 = new JointProduct("Schnitzel mit Pommes und Salat", 5, hp2);

		hp3.add(shp3);
		hp2.add(bl1);
		hp1.add(g1);
		JointProduct jp3 = new JointProduct("Bernerw�rstel mit Pommes", 5, hp2);

		// Nachspeisen:
		SimpleProduct ns1 = new SimpleProduct("Chocolademousse", (float) 2.50);
		SimpleProduct ns2 = new SimpleProduct("Sachertorte", (float) 3.50);
		SimpleProduct ns3 = new SimpleProduct("Kaiserschmarrn", (float) 2.90);

		JointProduct jpl1 = new JointProduct("Men� 1", 5);
		jpl1.addProduct(jp1);
		jpl1.addProduct(vs1);
		jpl1.addProduct(ns1);

		JointProduct jpl2 = new JointProduct("Men� 2", 5);
		jpl2.addProduct(jp2);
		jpl2.addProduct(vs2);
		jpl2.addProduct(ns2);

		JointProduct jpl3 = new JointProduct("Men� 3", 5);
		jpl3.addProduct(jp3);
		jpl3.addProduct(vs3);
		jpl3.addProduct(ns3);

		jointprod.add(jpl1);
		jointprod.add(jpl2);
		jointprod.add(jpl3);
		
		System.out.println(jointprod);
		return jointprod;
	}

	public static void main(String[] args){

		boolean end = true;
		Restaurant rest1 = new Restaurant("Test_Restaurant");
		
		try{
		rest1.addProduct(Restaurant.generateSimpleProducts());
		}
		catch(DuplicateProductException e)
		{
			System.out.println(e);
		}
		
		// 5xExtendedProduct:
		try{
			rest1.addProduct(new ExtendedProduct("Marmelade Brot", (float) 3.50));
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}
		try{
			rest1.addProduct(new ExtendedProduct("Tiramesu", (float) 2.50));
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}
		try{
			rest1.addProduct(new ExtendedProduct("Milcheis", (float) 1.50));
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}
		try{
			rest1.addProduct(new ExtendedProduct("200g Steak", (float) 9.90));
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}
		try{
			rest1.addProduct(new ExtendedProduct("Kartoffelsalat", (float) 2.90));
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}

		// JointProduct SimpleProduct only:
		JointProduct JPSP = new JointProduct("OnlySP", 10);

		JPSP.addProduct(new SimpleProduct("Fleischlaberl klein", (float) 3.90));
		JPSP.addProduct(new SimpleProduct("Fleischlaberl gro�", (float) 4.90));
		JPSP.addProduct(new SimpleProduct("Fleischlaberl large", (float) 6.90));
		

		try{
			rest1.addProduct(JPSP);
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}

		// JointProduct ExtendedProduct only:
		JointProduct JPEP = new JointProduct("OnlyEP", 15);

		JPEP.addProduct(new ExtendedProduct("200g Steak", (float) 9.90));
		JPEP.addProduct(new ExtendedProduct("300g Steak", (float) 15.90));
		JPEP.addProduct(new ExtendedProduct("450g Steak", (float) 19.90));

		try{
			rest1.addProduct(JPEP);
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}

		// Create at least 2 JointProduct containing JointProduct:
		try{
			rest1.addProduct(Restaurant.generateJointProducts());
			}
			catch(DuplicateProductException e)
			{
				System.out.println(e);
			}

		// Try to add a duplicate IProduct:
			try{
				 rest1.addProduct(new SimpleProduct("Kartoffelsalat", (float) 4.90));
				}
				catch(DuplicateProductException e)
				{
					System.out.println(e);
				}
		// //should invoke an Error

		// Create 3 Tables:
		rest1.createTable("T1");
		rest1.createTable("T2");
		rest1.createTable("T3");
		// System.out.println(rest1.Li_Tab);

		// at least 2 Orders per Table

		// orderProductForTable(Table table, IProduct product):
		rest1.orderProductForTable(rest1.getSpecificTable("T1"), rest1.findProduct("Risotto"));
		rest1.orderProductForTable(rest1.getSpecificTable("T2"), rest1.findProduct("Milcheis"));
		rest1.orderProductForTable(rest1.getSpecificTable("T3"), rest1.findProduct("Kartoffelsalat"));

		// orderProductForTable(Table table, IProduct product, int count):
		rest1.orderProductForTable(rest1.getSpecificTable("T1"), rest1.findProduct("OnlyEP"), 5);
		rest1.orderProductForTable(rest1.getSpecificTable("T2"), rest1.findProduct("OnlySP"), 5);
		rest1.orderProductForTable(rest1.getSpecificTable("T3"), rest1.findProduct("OnlyEP"), 5);

		//System.out.println(rest1.Li_Ord);

		// CommandLine Men�:
		Scanner scanner = new Scanner(System.in);

		while (end) {

			System.out.println("Menue");
			System.out.println("1.		Produktsuche");
			System.out.println("2.		Produkt hinzuf�gen");
			System.out.println("3.		Beenden");

			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Nach welchem Produkt m�chten Sie suchen?");
				String eingabe = scanner.next();
				if (rest1.findProduct(eingabe) != null)
					System.out.print(rest1.findProduct(eingabe));
				else
					System.out.println("Nicht gefunden!");

				break;

			case 2:
				System.out.println("Wie soll das neue Produkt hei�en?");
				String name = scanner.next();
				System.out.println("Wie viel so das neue Produkt kosten?");
				float price = scanner.nextFloat();
				try{
					rest1.addProduct(new SimpleProduct(name, price));
					}
					catch(DuplicateProductException e)
					{
						System.out.println(e);
					}
				break;

			case 3:
				scanner.close();
				end = false;
				break;

			default:
				System.out.println("Falsche Eingabe!");

			}
		}
	}
}
