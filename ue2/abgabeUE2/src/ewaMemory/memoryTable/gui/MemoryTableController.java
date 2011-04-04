package ewaMemory.memoryTable.gui;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ewaMemory.memoryTable.api.MemoryAPI;
import ewaMemory.memoryTable.beans.MemoryTable;

/**
 * Servlet implementation class MemoryTableController
 */
@WebServlet("/MemoryTableController")
public class MemoryTableController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(MemoryTableController.class.getSimpleName());

	// TODO check this values
	private static final int MEMORY_WIDTH = 3;
	private static final int MEMORY_HEIGHT = 2;

	private static final String MEMORY_TABLE_VIEW = "/MemoryTableView.jsp";

	private MemoryAPI memoryApi = new MemoryAPI();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemoryTableController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		synchronized (session) {
			MemoryTable memory = (MemoryTable) session
					.getAttribute(SessionKeys.MEMORY_TABLE);

			// no memory table open, create a new one
			if (memory == null) {
				memory = memoryApi.createMemoryTable(MEMORY_WIDTH,
						MEMORY_HEIGHT);
				session.setAttribute(SessionKeys.MEMORY_TABLE, memory);
			}

			if (request.getParameter(MemoryTableParams.X) != null) {
				int click_x = Integer.valueOf(request
						.getParameter(MemoryTableParams.X));
				if (request.getParameter(MemoryTableParams.Y) != null) {
					int click_y = Integer.valueOf(request
							.getParameter(MemoryTableParams.Y));
					memoryApi.clickOnCard(memory, click_x, click_y);
				} else {
					log.warning("invalid parameters passed. missing y click value");
				}
			}

			request.getRequestDispatcher(MEMORY_TABLE_VIEW).forward(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
