package mongoDBTest;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Mongo {

	@Test
	public void test() throws Exception{
		String dbURI = "mongodb://kuku:asdfasdf@125.209.196.156:27017/company";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		DB database = mongoClient.getDB("company");
		DBCollection collection = database.getCollection("emp");

		// 1. insertion
		BasicDBObject doc = new BasicDBObject("eno", 1234).append("name", "Lee add");
		collection.insert(doc);
		DBCursor cursor = collection.find();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next());
		    }
		} finally {
			mongoClient.close();
		    cursor.close();
		}
	}

}
