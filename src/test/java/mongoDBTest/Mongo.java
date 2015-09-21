package mongoDBTest;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Mongo {

	@Test
	public void test() {
		String dbURI = "mongodb://kuku:asdfasdf@125.209.196.156:27017/company?authSource=ad,in";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		MongoDatabase database = mongoClient.getDatabase("emp");
		MongoCollection<Document> collection = database.getCollection("emp");
		
		// Document doc = new Document("name", "MongoDB")
		// .append("type", "database")
		// .append("count", 1)
		// .append("info", new Document("x", 203).append("y", 102));
		
		// 1. insertion
		//BasicDBObject doc = new BasicDBObject("eno", 1234).append("name", "Lee add");
		Document documnet = new Document("eno", "2345");
		collection.insertOne(documnet);
		MongoCursor<Document> cursor = collection.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		    }
		} finally {
			mongoClient.close();
		    cursor.close();
		}
	}

}
