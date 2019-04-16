import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


import org.json.*;

public class JsonPost {

    private JSONObject jsonToPost;

    public JsonPost(HashMap<Integer, Student> fCompSciStudents, int gradeAverage){
        ArrayList<Integer> test = new ArrayList<Integer>(fCompSciStudents.keySet());
        Collections.sort(test);

        jsonToPost = new JSONObject();
        jsonToPost.put("id","dorceykyle@gmail.com");
        jsonToPost.put("name", "Kyle Dorcey");
        jsonToPost.put("average",gradeAverage);
        jsonToPost.put("studentIds", test);

    }

    public void post() throws Exception{
        URL url = new URL("http://3.86.140.38:5000/challenge");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(jsonToPost.toString());
        out.flush();
        out.close();

        System.out.println(connection.getResponseCode());

        connection.disconnect();
    }

}
