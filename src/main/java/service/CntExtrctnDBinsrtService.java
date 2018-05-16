package service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;


public class CntExtrctnDBinsrtService {

    private MongoDatabase connectToDB(){

        MongoDatabase mongoDatabase = null;

        try{
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
            mongoDatabase = mongoClient.getDatabase("local");
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return mongoDatabase;
    }

    public void contentExtractAndInsert(Map<String, String> map) {
        MongoDatabase mongoDatabase = connectToDB();
        MongoCollection<Document> collection = mongoDatabase.getCollection("test");
        System.out.println("Get collection successful");
        map.forEach((link, address) -> {
            //Fetch content from link
            try {
                org.jsoup.nodes.Document jsoupDocument = Jsoup.connect(link).get();
                Document document = new Document("address", address)
                        .append("asking_price",jsoupDocument.select("dt:contains(Vraagprijs)+dd").text())
                        .append("status", jsoupDocument.select("dt:contains(Aangeboden sinds)+dd").text())
                        .append("type_of_house", jsoupDocument.select("dt:contains(Soort woonhuis)+dd").text())
                        .append("type_of_construction", jsoupDocument.select("dt:contains(Soort bouw)+dd").text())
                        .append("construction_year", jsoupDocument.select("dt:contains(Bouwjaar)+dd").text())
                        .append("kind_of_roof", jsoupDocument.select("dt:contains(Soort dak)+dd").text())
                        .append("living_area", jsoupDocument.select("dt:contains(Woonoppervlakte)+dd").text())
                        .append("external_storage_space", jsoupDocument.select("dt:contains(Externe bergruimte)+dd").text())
                        .append("plot_area", jsoupDocument.select("dt:contains(Perceeloppervlakte)+dd").text())
                        .append("content", jsoupDocument.select("dt:contains(Inhoud)+dd").text())
                        .append("number_of_rooms", jsoupDocument.select("dt:contains(Aantal kamers)+dd").text())
                        .append("number_of_bathrooms", jsoupDocument.select("dt:contains(Aantal badkamers)+dd").text())
                        .append("bathroom_amenities", jsoupDocument.select("dt:contains(Badkamervoorzieningen)+dd").text())
                        .append("number_of_floors", jsoupDocument.select("dt:contains(Aantal woonlagen)+dd").text())
                        .append("services", jsoupDocument.select("dt:contains(Voorzieningen)+dd").text())
                        .append("energy_label", jsoupDocument.select("dt:contains(Energielabel)+dd").text())
                        .append("insulation", jsoupDocument.select("dt:contains(Isolatie)+dd").text())
                        .append("heating", jsoupDocument.select("dt:contains(Verwarming)+dd").text())
                        .append("hot_water", jsoupDocument.select("dt:contains(Warm water)+dd").text())
                        .append("ownership_situation", jsoupDocument.select("dt:contains(Eigendomssituatie)+dd").text());

                collection.insertOne(document);

//                jsoupDocument.select("dt").forEach(t -> System.out.println(t.getElementsByTag("dt").text()));

//                jsoupDocument.select("dt").forEach(t -> System.out.println(t));
//                jsoupDocument.select("dd").forEach(t -> System.out.println(t));

            } catch(IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }

}
