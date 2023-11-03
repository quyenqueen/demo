package com.example.btl.web;



import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;








@Controller
public class WebController {
	
	@GetMapping("/TrangChu")
    public String TrangChu() {
        return "TrangChu";
    }
    @GetMapping("/DangKy")
    public String showRegistrationForm() {
        return "DangKy";
    }

    @PostMapping("/DangKy")
    public String registerUser(@RequestParam("account") String account,
                               @RequestParam("password") String password,
                               @RequestParam("email") String email,
                               Model model) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
            int result = 0;

            // Kiểm tra xem tên tài khoản đã tồn tại trong cơ sở dữ liệu hay chưa
            String countQuery = "SELECT COUNT(*) FROM btl.users WHERE account = ?";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setString(1, account);
            ResultSet countResult = countStatement.executeQuery();
            countResult.next();
            int count = countResult.getInt(1);
            countStatement.close();

            if (count > 0) {
                model.addAttribute("error", "Tài khoản đã tồn tại!");
            } else {
                // Thêm tài khoản vào cơ sở dữ liệu
                String insertQuery = "INSERT INTO btl.users (account, password, email) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, account);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                result = insertStatement.executeUpdate();
                insertStatement.close();

                connection.close();
                model.addAttribute("message", "Đăng ký thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình đăng ký.");
        }

        return "DangKy";
    }

    @GetMapping("/loginUser")
    public String showLoginForm() {
        return "loginUser";
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("account") String account,
                            @RequestParam("password") String password,
                            Model model) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
            String query = "SELECT * FROM btl.users WHERE account = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                if (password.equals(dbPassword)) {
                    model.addAttribute("message", "Đăng nhập thành công!");
                    return "redirect:/books3";
                } else {
                    model.addAttribute("error", "Mật khẩu không hợp lệ!");
                }
            } else {
                model.addAttribute("error", "Tài khoản không tồn tại!");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình đăng nhập.");
        }

        return "loginUser";
    }
    
    @GetMapping("/loginAdmin")
    public String showLoginForm2() {
        return "loginAdmin";
    }

    @PostMapping("/loginAdmin")
    public String loginAdmin(@RequestParam("account") String account,
                            @RequestParam("password") String password,
                            Model model) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
            String query = "SELECT * FROM btl.admin WHERE account = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                if (password.equals(dbPassword)) {
                    model.addAttribute("message", "Đăng nhập thành công!");
                    return "redirect:/books2";
                } else {
                    model.addAttribute("error", "Mật khẩu không hợp lệ!");
                }
            } else {
                model.addAttribute("error", "Tài khoản không tồn tại!");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình đăng nhập.");
        }

        return "loginAdmin";
    }
    
    @GetMapping("/books1")
	public String getBooks(Model model) throws IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from btl.book");
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				Date nph = resultSet.getDate("nph");
				int nop = resultSet.getInt("nop");
				String category = resultSet.getString("category");
				String url = resultSet.getString("url");
				int slb = resultSet.getInt("slb");
				books.add(new Book(title, author, description, nph, nop,category, url, slb));
				}
			} 
		catch (Exception e) {
			e.printStackTrace();
			}
		model.addAttribute("books1", books);
		return "books1";
		}
    
    @GetMapping("/books2")
	public String getBooks2(Model model) throws IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from btl.book");
			while (resultSet.next()) {
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String description = resultSet.getString("description");
				Date nph = resultSet.getDate("nph");
				int nop = resultSet.getInt("nop");
				String category = resultSet.getString("category");
				String url = resultSet.getString("url");
				int slb = resultSet.getInt("slb");
				books.add(new Book(title, author, description, nph, nop,category, url, slb));
				}
			} 
		catch (Exception e) {
			e.printStackTrace();
			}
		model.addAttribute("books2", books);
		return "books2";
		}
    
    @GetMapping("/books3")
   	public String getBooks3(Model model) throws IOException {
   		Connection connection = null;
   		Statement statement = null;
   		ResultSet resultSet = null;
   		List<Book> books = new ArrayList<Book>();
   		try {
   			Class.forName("com.mysql.cj.jdbc.Driver");
   			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
   			statement = connection.createStatement();
   			resultSet = statement.executeQuery("select * from btl.book");
   			while (resultSet.next()) {
   				String title = resultSet.getString("title");
   				String author = resultSet.getString("author");
   				String description = resultSet.getString("description");
   				Date nph = resultSet.getDate("nph");
   				int nop = resultSet.getInt("nop");
   				String category = resultSet.getString("category");
   				String url = resultSet.getString("url");
   				int slb = resultSet.getInt("slb");
   				books.add(new Book(title, author, description, nph, nop,category, url, slb));
   				}
   			} 
   		catch (Exception e) {
   			e.printStackTrace();
   			}
   		model.addAttribute("books3", books);
   		return "books3";
   		}
    
    
    
    @DeleteMapping("/book/delete/{title}")
	public String DeleteBook(@PathVariable String title) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
			statement = connection.prepareStatement("DELETE FROM btl.book WHERE title = ?");
			statement.setString(1, title);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/books2";
	}
    
    @GetMapping("/book/view/{title}")
    public String getViewBook(Model model, @PathVariable String title) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        Book book = new Book();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
            ps = connection.prepareStatement("SELECT * FROM btl.book WHERE title = ?");
            ps.setString(1, title);
            result = ps.executeQuery();
            while(result.next()) {
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setDescription(result.getString("description"));
                book.setNph(result.getDate("nph"));
                book.setNop(result.getInt("nop"));
                book.setCategory(result.getString("category"));
                book.setUrl(result.getString("url"));
                book.setSlb(result.getInt("slb"));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("book", book);
        return "viewBook";
    }

    
    @GetMapping("/book/{title}/{nop}")
    public String getBook(Model model, @PathVariable String nop,@PathVariable String title) {
    	Connection connection = null;
    	PreparedStatement ps = null;
    	ResultSet result = null;

    	Book book = new Book();
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
    		ps = connection.prepareStatement("SELECT * FROM btl.book WHERE title = ?");
    		ps.setString(1, title);
    		result = ps.executeQuery();
    		while (result.next()) {
    			book.setTitle(result.getString("title"));
    			book.setAuthor(result.getString("author"));
    			book.setDescription(result.getString("description"));
    			book.setNph(result.getDate("nph"));
    			book.setNop(result.getInt("nop"));
    			book.setCategory(result.getString("category"));
    			book.setUrl(result.getString("url"));
    			book.setSlb(result.getInt("slb"));
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
    	model.addAttribute("book", book);
    	return "book-detail";
    }

   
    @PostMapping("/book/save/{nop}")
    public String addBook(
        @ModelAttribute("book") Book b,
        @PathVariable String nop,
        Model model
    ) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");
            int result = 0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Kiểm tra xem tiêu đề hoặc tác giả đã tồn tại trong cơ sở dữ liệu hay chưa
            String countQuery = "SELECT COUNT(*) FROM btl.book WHERE title = ? OR author = ?";
            PreparedStatement countStatement = connection.prepareStatement(countQuery);
            countStatement.setString(1, b.getTitle());
            countStatement.setString(2, b.getAuthor());
            ResultSet countResult = countStatement.executeQuery();
            countResult.next();
            int count = countResult.getInt(1);
            countStatement.close();

            if (count > 0) {
                model.addAttribute("error", "Tiêu đề hoặc tác giả đã tồn tại!");
               
            } else {
                java.util.Date nphUtilDate = b.getNph();
                java.sql.Date nph = new java.sql.Date(nphUtilDate.getTime());


                String insertQuery = "INSERT INTO btl.book (title, author, description, nph, nop, category,url, slb) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, b.getTitle());
                insertStatement.setString(2, b.getAuthor());
                insertStatement.setString(3, b.getDescription());
                insertStatement.setDate(4, nph);
                insertStatement.setInt(5, Integer.valueOf(nop));
                insertStatement.setString(6, b.getCategory());
                insertStatement.setString(7, b.getUrl());
                insertStatement.setInt(8, 0);
                result = insertStatement.executeUpdate();
                insertStatement.close();

                connection.close();
                return "redirect:/books2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình thêm sách.");
        }

        return "error";
    }

    @PutMapping("/book/save/{nop}")
    public String updateBook(
        @ModelAttribute("book") Book b,
        @PathVariable String nop,
        Model model
    ) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/btl", "root", "1102");

            // Cập nhật thông tin sách
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date nphUtilDate = b.getNph();
            java.sql.Date nph = new java.sql.Date(nphUtilDate.getTime());

            PreparedStatement ps = connection.prepareStatement("UPDATE btl.book SET author=?, description=?, nph=?, nop=?, category=?, url=?, slb=? WHERE title=?");
            ps.setString(1, b.getAuthor());
            ps.setString(2, b.getDescription());
            ps.setDate(3, nph);
            ps.setInt(4, Integer.valueOf(nop));
            ps.setString(5, b.getCategory());
            ps.setString(6, b.getUrl());
            ps.setInt(7, 0);
            ps.setString(8, b.getTitle());

            int result = ps.executeUpdate();
            ps.close();
            connection.close();

            return "redirect:/books2";
        } catch (Exception e) {
        	e.printStackTrace();
            model.addAttribute("error", "Đã xảy ra lỗi trong quá trình cập nhật sách.");
        }
        return "error";
    }

    	
    
   
    
}
