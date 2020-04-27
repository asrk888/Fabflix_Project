import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

// Declaring a WebServlet called SingleStarServlet, which maps to url "/api/single-star"
@WebServlet(name = "DeleteCartItem", urlPatterns = "/api/cartDelete")
public class DeleteCartItem extends HttpServlet {
    private static final long serialVersionUID = 2L;

    // Create a dataSource which registered in web.xml
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json"); // Response mime type

        String movieId = request.getParameter("id");
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        try {

                HttpSession session = request.getSession();
                HashMap<String, JsonObject> cardItem = (HashMap<String, JsonObject>) session.getAttribute("cardItem");

//                if (cardItem == null) {
//                    cardItem = new HashMap<>();
//                    cardItem.put(movieId, newJsonObject);
//                    session.setAttribute("cardItem", cardItem);
//                } else {
//                    if (cardItem.get(movieId) == null) {
//                        cardItem.put(movieId, newJsonObject);
//                    } else {
//                        JsonElement quantity = cardItem.get(movieId).get("quantity");
//                        int number = Integer.parseInt(quantity.toString());
//
//                        cardItem.get(movieId).addProperty("quantity", number + 1);
//
//                    }
//                }

                cardItem.remove(movieId);

                JsonArray jsonArray = new JsonArray();
                for (JsonObject i : cardItem.values()) {
                    jsonArray.add(i);
                }


                // write JSON string to output
                out.write(jsonArray.toString());
                // set response status to 200 (OK)
                response.setStatus(200);

        } catch (Exception e) {

            // write error message JSON object to output
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("errorMessage", e.getMessage());
            out.write(jsonObject.toString());

            // set reponse status to 500 (Internal Server Error)
            response.setStatus(500);

        }
        out.close();
        //close it;

    }

}
