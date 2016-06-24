package com.myapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.myapp.helper.HibernateUtil;
import com.myapp.model.Message;
import com.myapp.model.Tag;
import com.myapp.model.User;

/**
 * Servlet implementation class GuestBookServlet
 */
@WebServlet("/guestbook")
public class GuestBookServlet extends HttpServlet {
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GuestBookServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if (action != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			if (action.equalsIgnoreCase("delete")) {
				deleteGuestbook(request, response, id);
				response.sendRedirect("guestbook");
			} else if (action.equalsIgnoreCase("edit")) {
				editGuestbook(request, response, id);
			} else if (action.equalsIgnoreCase("update")) {
				updateGuestbook(request, response, id);
				response.sendRedirect("guestbook");
			}
		} else {
			System.out.println("test");
			getAll(request, response);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		if (action != null && action.equalsIgnoreCase("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			updateGuestbook(request, response, id);
		} else {
			insertGuestbook(request, response);
		}
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void getAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("FROM Message ORDER BY ID DESC");
		query.setMaxResults(8);
		List <Message> messages = query.list();
		query = session.createQuery("FROM Tag");
		List <Tag> tags =   query.list();
		
//		for(Message item : messages ) {
//			System.out.println( item);
//			System.out.println("User id: " + item.getUser().getId() + "\n Name: " + item.getUser().getUsername() );
//		}		
		tx.commit();
		session.close();
		request.setAttribute("messages", messages);
		request.setAttribute("tags", tags);
		RequestDispatcher view = request.getRequestDispatcher("guestbook.jsp");
		view.forward(request, response);
	}

	public void insertGuestbook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = Integer.parseInt(request.getParameter("userid"));
		String messageIn = request.getParameter("message");		
		String[] chkTags = request.getParameterValues("chkTags");		
		List<Tag> tags = new ArrayList<Tag>();

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();		
		Transaction tx = session.beginTransaction();
		User user = session.get(User.class, userid );
		Message message  = new Message(user, messageIn);		
		for(String tag : chkTags) {
			Tag t = session.get(Tag.class, Integer.parseInt(tag));
			System.out.println("tag = " +  t.getId() + "name: " + t.getName() );
			tags.add(t);			
		}
		message.setTags(tags);
		System.out.println("========== Save message =======");
	    Integer messageId = (Integer) session.save(message);
	    System.out.println("=========end save message =====");
	    System.out.println("guestbookID = " + messageId );
	     
	    tx.commit();
	    session.close();
	}

	public void deleteGuestbook(HttpServletRequest request, HttpServletResponse response, int id) throws IOException {		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
	    Message message = (Message)session.get(Message.class, id);
	    message.setTags(null);
        session.delete(message);
        tx.commit();
        session.close();
	}

	public void editGuestbook(HttpServletRequest request, HttpServletResponse response, int id)
			throws IOException, ServletException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Message message = (Message)session.get(Message.class, id);
		Query query = session.createQuery("FROM Tag");
		List <Tag> tags =   query.list();		
		
        tx.commit();
        session.close();
		request.setAttribute("message", message);
		request.setAttribute("tags", tags);
		RequestDispatcher view = request.getRequestDispatcher("editGuestbook.jsp");
		view.forward(request, response);
	}

	public void updateGuestbook(HttpServletRequest request, HttpServletResponse response, int id) {
//		String name = request.getParameter("name");
		String message = request.getParameter("message");
		String[] chkTags = request.getParameterValues("chkTags");		
		List<Tag> tags = new ArrayList<Tag>();		
		
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Message gb = (Message) session.get(Message.class, id );
		for(String tag : chkTags) {
			Tag t = session.get(Tag.class, Integer.parseInt(tag));
			System.out.println("tag = " +  t.getId() + "name: " + t.getName() );
			tags.add(t);			
		}		
		gb.setTags(tags);
//		gb.setName(name);
		gb.setMessage(message);
		session.update(gb);		
        tx.commit();
        session.close();
	}
}
