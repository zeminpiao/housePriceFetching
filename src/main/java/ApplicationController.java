import com.mongodb.client.MongoDatabase;
import service.CntExtrctnDBinsrtService;
import service.LinkExtractService;

import java.io.IOException;
import java.util.Map;

public class ApplicationController {



    public CntExtrctnDBinsrtService cntExtrctnDBinsrt;
    public LinkExtractService linkExtractService;

    public ApplicationController(){

        this.cntExtrctnDBinsrt = new CntExtrctnDBinsrtService();
        this.linkExtractService = new LinkExtractService();

    }

    public void applicationProcess() throws IOException, InterruptedException {
        Map<String, String> linkAddressMap =  this.linkExtractService.linkExtract("http://www.funda.nl/ko" +
                "op/eindhoven/0-375000");

        this.cntExtrctnDBinsrt.contentExtractAndInsert(linkAddressMap);
    }
}
