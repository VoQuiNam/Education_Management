package com.joctopus.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

//lớp HibernateUtil, được sử dụng để cấu hình và tạo đối tượng SessionFactory của Hibernate.
public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				// Thiết lập các thuộc tính cấu hình của Hibernate bằng cách sử dụng đối tượng
				// Properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
				settings.put(Environment.URL,
						"jdbc:sqlserver://DESKTOP-J6KOQBC\\SQLEXPRESS:1433;databaseName=EducationManagement;encrypt=false");
				settings.put(Environment.USER, "sa");
				settings.put(Environment.PASS, "123456789");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");// đề cập đến loại sql mà
																							// hibernate sẽ dùng và tạo
																							// ra

				settings.put(Environment.SHOW_SQL, "true");// Cấu hình để hiển thị các câu lệnh SQL được tạo ra bởi
															// Hibernate trong console.
				settings.put(Environment.FORMAT_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");// Chỉ định kiểu session context sẽ
																					// được sử dụng.

				configuration.setProperties(settings);
				configuration.addAnnotatedClass(com.joctopus.model.User.class);// Thêm lớp User vào cấu hình để
																				// Hibernate biết về các thực
				configuration.addAnnotatedClass(com.joctopus.model.Classes.class);
				configuration.addAnnotatedClass(com.joctopus.model.Ucl.class);
				configuration.addAnnotatedClass(com.joctopus.model.Notification.class);
				// thể cần ánh xạ.

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);

				return sessionFactory;

			} 

		return sessionFactory;
	}
}