package servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

public class QuestServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher;
    @Mock
    HttpSession session;

    private QuestServlet gameServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gameServlet = new QuestServlet();
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void doGet() throws Exception {
        when(request.getParameter("questionId")).thenReturn("1");
        gameServlet.doGet(request, response);
        verify(request, times(1)).getRequestDispatcher("/question.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws Exception {
        when(request.getParameter("playerName")).thenReturn("Test Player");
        gameServlet.doPost(request, response);
        verify(response, times(1)).sendRedirect(anyString());
    }
}