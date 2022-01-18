package tfip.modserver.librarysearch.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class BookService {
    private final String api = "https://openlibrary.org/search.json?fields=title,key&limit=20";
    Logger logger = Logger.getLogger(BookService.class.getName());

    public HashMap<String, String> search(String searchTerm){
        HashMap<String,String> results = new HashMap<>();
        RestTemplate template = new RestTemplate();
        String url = api + "&q=" + searchTerm;
        
        
        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        
        ResponseEntity<String> resp = template.exchange(req, String.class);

        try( InputStream inputStream = new ByteArrayInputStream(resp.getBody().getBytes()) ){
            JsonReader reader = Json.createReader(inputStream);
            JsonObject data = reader.readObject();
            JsonArray searchArray = data.getJsonArray("docs");

            List<JsonObject> keyandTitle = searchArray.stream()
                .map(f -> (JsonObject) f)
                .toList();
            keyandTitle.forEach( f -> results.put(f.getString("key"), f.getString("title")) );
            return results;
        }
        catch (IOException e){
            logger.warning("IOException in reading json");
        }
        
        return results;
    } 

    public HashMap<String,String> getBookDetail(String workid){
        HashMap<String,String> bookDetails = new HashMap<>();
        RestTemplate template = new RestTemplate();
        String url = "https://openlibrary.org/works/" + workid+".json";
        
        RequestEntity<Void> req = RequestEntity.get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        
        try( InputStream inputStream = new ByteArrayInputStream(resp.getBody().getBytes()) ){
            JsonReader reader = Json.createReader(inputStream);
            JsonObject data = reader.readObject();
            
            String excerpt;
            String stringDescription;

            try {
                JsonObject excerptObj = data.getJsonArray("excerpts").getJsonObject(0);
                excerpt = excerptObj.getString("excerpt");

            } catch (NullPointerException e) {
                excerpt = "Excerpt is not written yet...";
            }

            try {
                stringDescription = data.getString("description");

            } catch (NullPointerException e) {
                stringDescription = "description is not written yet...";
            }

            bookDetails.put("title",data.getString("title"));
            bookDetails.put("description",stringDescription);
            bookDetails.put("excerpt", excerpt);
            return bookDetails;
        }
        catch (IOException e){
            logger.warning("getBookDetail IOException in reading json");
        }
        catch( NullPointerException n){
            logger.warning("errror fetching data from json");
        }
        return bookDetails;
    }
}
