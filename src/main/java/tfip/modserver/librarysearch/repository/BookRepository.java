package tfip.modserver.librarysearch.repository;

import java.io.StringReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;

@Repository
public class BookRepository {

    @Autowired
    @Qualifier("redisDB")
    private RedisTemplate<String, String> template;

    private String jsonParser(String jsonString , String key){
        JsonParser parser = Json.createParser( new StringReader(jsonString) );
        Event event = parser.next();
        String stringwithQuote = parser.getObjectStream().filter(e -> e.getKey().equalsIgnoreCase(key))
                .map(e->e.getValue().toString())
                .findFirst()
                .orElse("none");
        parser.close();
        return stringwithQuote.substring(1,stringwithQuote.length()-1);
    }

    public void save(String id, HashMap<String,String> bookDetail) {
        JsonObject bookJson = Json.createObjectBuilder()
            .add("title", bookDetail.get("title"))
            .add("Description", bookDetail.get("description"))
            .add("excerpt",bookDetail.get("excerpt"))
            .build();
            
            template.opsForValue().set(id,bookJson.toString(),10, TimeUnit.MINUTES);
    }

    public boolean hasID(String id){
        return template.hasKey(id);
    }

    public HashMap<String,String> getID(String id){
        String jsonString = template.opsForValue().get(id);
        HashMap<String,String> bookDetail = new HashMap<>();

        bookDetail.put("title", jsonParser(jsonString,"title"));
        bookDetail.put("description", jsonParser(jsonString,"description"));
        bookDetail.put("excerpt", jsonParser(jsonString,"excerpt"));

        return bookDetail;
    }
}
