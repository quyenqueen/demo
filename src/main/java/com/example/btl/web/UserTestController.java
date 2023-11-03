package com.example.btl.web;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserTestController {
	@GetMapping("/users")
	@ResponseBody
	public List<String> getEmployees() throws IOException{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<String> results= new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from btl.users");
			while (resultSet.next()) {
				String account = resultSet.getString("account");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				results.add(account+" , "+ password+", "+ email);
				}
			} // End of try block
		catch (Exception e) {
			e.printStackTrace();
			}
		return results;
	}
	
	@GetMapping("/books")
	@ResponseBody
	public List<String> getBooks() throws IOException{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<String> results= new ArrayList<String>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from btl.book");
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String describe = resultSet.getString("describe");
				Date nph = resultSet.getDate("nph");
				int nop = resultSet.getInt("nop");
				String category = resultSet.getString("category");
				String url = resultSet.getString("url");
				int slb = resultSet.getInt("slb");
				results.add(title+" , "+ author+", "+ describe+", "+nph+", "+nop+", "+category+", "+url+", "+slb);
				}
			} // End of try block
		catch (Exception e) {
			e.printStackTrace();
			}
		return results;
	}
}
