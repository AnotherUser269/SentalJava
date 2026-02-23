import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String helloStr = (String) session.getAttribute("WelcomeText");

        if (helloStr == null) {
            helloStr = "HelloWorld";
            session.setAttribute("WelcomeText", helloStr);
        }

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>" + helloStr + "</h1>");
        out.println("</body></html>");
    }
}
