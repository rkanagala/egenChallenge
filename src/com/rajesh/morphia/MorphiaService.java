package com.rajesh.morphia;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class MorphiaService {

	private Morphia morphia;
	private Datastore datastore;
	public Morphia getMorphia() {
		return morphia;
	}
	public void setMorphia(Morphia morphia) {
		this.morphia = morphia;
	}
	public Datastore getDatastore() {
		return datastore;
	}
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
	public MorphiaService(){
		MongoClient mongoClient = new MongoClient("127.0.0.1:27017");
		
		//create a new morphia instance
		this.morphia = new Morphia(); 
		String databaseName = "test";
		this.datastore = morphia.createDatastore(mongoClient, databaseName);

		
	}



}
