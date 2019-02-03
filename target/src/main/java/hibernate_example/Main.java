package hibernate_example;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import hibernate_example.tableperclass.Person;
import hibernate_example.tableperclass.Author;
import hibernate_example.tableperclass.Book;
import hibernate_example.tableperclass.Publisher;

public class Main {

	public static void main(String[] args) {
		final Configuration configuration = new Configuration().configure();
		final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		final SessionFactory factory = configuration.buildSessionFactory(builder.build());
		final Session session = factory.openSession();

		final Publisher publisher = new Publisher();
		publisher.setName("Manning");
		publisher.setEmailAdress("info@manning.com");
		publisher.setTaxId("unknown");
		session.save(publisher);

		final Book book = new Book("9781617291999", "Java 8 in Action", new Date());
		session.beginTransaction();

		/*
		 * //JDK 1.8 feature : Streams
		 * Arrays.stream("Raoul-Gabriel Urma,Mario Fusco,Alan Mycroft".split(",")).
		 * map(name -> new Author(name)).forEach(author -> {
		 * author.getBooks().add(book); author.setPublisher(publisher);
		 * session.save(author); book.getAuthors().add(author); });
		 */

		String[] authors = { "Raoul-Gabriel Urma", "Mario Fusco", "Alan Mycroft" };
		for (String a : authors) {
			Author author = new Author(a);
			author.getBooks().add(book);
			author.setPublisher(publisher);
			session.save(author);
			book.getAuthors().add(author);
		}

		session.save(book);

		session.getTransaction().commit();
		/*
		 * final List<Book> books = (List<Book>)session.createQuery("from Book").list();
		 * System.out.println("\n"); System.out.println("Books size :  " +
		 * books.size()); for (final Book b : books) { System.out.println(b); }
		 */

		System.out.println("\n");
		/*
		 * System.out.println(MessageFormat.
		 * format("Storing {0} persons in the database.",
		 * session.createCriteria(AbstractPerson.class).list() .size()));
		 */

		List<Person> persons = (List<Person>) session.createQuery("from Person").list();

		for (Person p : persons) {
			System.out.println(p.getName() + " " + p.getEmailAdress());
			if (p instanceof Author) {

				Author a = (Author) p;
				System.out.println(a.getBooks() + " " + a.getPublisher().getName());
			}
		}

		session.close();
		factory.close();
	}
}
