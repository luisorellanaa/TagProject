package tagProject.address.model;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import org.terrier.indexing.Collection;
import org.terrier.indexing.SimpleFileCollection;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.ArrayMetaIndex;
import org.terrier.structures.Index;
import org.terrier.structures.IndexOnDisk;
import org.terrier.structures.indexing.Indexer;
import org.terrier.structures.indexing.classical.BasicIndexer;
import org.terrier.utility.ApplicationSetup;
import org.terrier.matching.models.TF_IDF;


public class IR {
	
	private final static String RUTA_INDICE = "E:\\Indices";
	private static IR instance = new IR();
	private static IndexOnDisk index;
	
	private IR(){
		
        ApplicationSetup.setProperty("indexer.meta.forward.keys", "filename");
        ApplicationSetup.setProperty("indexer.meta.forward.keylens", "200");
	    ApplicationSetup.setProperty("querying.postfilters.order", "org.terrier.querying.SimpleDecorate");
        ApplicationSetup.setProperty("querying.postfilters.controls", "decorate:org.terrier.querying.SimpleDecorate");
	}
	
	public static IR getInstance(){
		
		return instance;
	}
	
	public HashMap<String, String> getAllIndex(){
		
		HashMap<String, String> results = new HashMap<String, String>();


		File[] files = new File(RUTA_INDICE).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 

		for (File file : files) {
		    if (file.isFile() && file.getName().split("\\.")[1].equals("properties")) {

		    	try {
			    	final File propsFile = new File(RUTA_INDICE + "\\" + file.getName());
		        	Properties props = new Properties();
		        	props.load(new FileInputStream(propsFile));
		        	String aDirectoryToIndex = props.getProperty("aDirectoryToIndex");
		        	String indexName = props.getProperty("indexName");
		        	String cantidadDocs =  props.getProperty("cantidadDocs");
		        	 
		        	results.put(indexName, aDirectoryToIndex + "♠" + cantidadDocs);
		    	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		    }
		}
		return results;
		
	}
	
	@SuppressWarnings("deprecation")
	public void createIndex(String aDirectoryToIndex, String indexName){
		
		//Crea Index
        Collection coll = new SimpleFileCollection(Arrays.asList(aDirectoryToIndex), true);
        BasicIndexer indexer = new BasicIndexer(RUTA_INDICE, indexName);
        Collection[] arrayCol = new Collection[]{coll};
        indexer.index(arrayCol);
        //Lee index creado
        String cantidadDocs = String.valueOf(Index.createIndex(RUTA_INDICE, indexName).getCollectionStatistics().getNumberOfDocuments());
        try {
        	final File propsFile = new File(RUTA_INDICE + "\\" + indexName + ".properties");
        	Properties props = new Properties();
        	props.load(new FileInputStream(propsFile));
        	props.setProperty("aDirectoryToIndex", aDirectoryToIndex);
			props.setProperty("indexName", indexName);
			props.setProperty("cantidadDocs", cantidadDocs);
        	props.save(new FileOutputStream(propsFile), "");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void setIndexName(String indexName){
		
		index = Index.createIndex(RUTA_INDICE, indexName);
	}
	
	public String getIndexName(){
		
		return index == null ? "" : index.getPrefix();
	}
	

	   public static void main(String[] args) throws Exception {

	    	// Directorio Indexado
	    	String aDirectoryToIndex = "C:\\Users\\luis\\Documents\\Java\\Books\\Spring";

	        // Map recuperacion archivo
	        ApplicationSetup.setProperty("indexer.meta.forward.keys", "filename");
	        ApplicationSetup.setProperty("indexer.meta.forward.keylens", "200");

	        Indexer indexer = new BasicIndexer("E:\\", "data");
	        Collection coll = new SimpleFileCollection(Arrays.asList(aDirectoryToIndex), true);
	        indexer.index(new Collection[]{coll});

	        Index index = Index.createIndex("E:\\", "data");

	    // Obtener propiedades archivos
	    ApplicationSetup.setProperty("querying.postfilters.order", "org.terrier.querying.SimpleDecorate");
	        ApplicationSetup.setProperty("querying.postfilters.controls", "decorate:org.terrier.querying.SimpleDecorate");

	    // Manager de consultas
	        Manager queryingManager = new Manager(index);

	    // Consulta
	        SearchRequest srq = queryingManager.newSearchRequestFromQuery("Madhusudhan Konda");

	    // Modelo de busqueda
	        srq.addMatchingModel("Matching",tagProject.address.model.ranking.MyRankingModel.class.getName());

	     // Obtener propiedades archivos
	        srq.setControl("decorate", "on");

	    // Ejecutar consulta
	        queryingManager.runSearchRequest(srq);

	    // Resultados
	        ResultSet results = srq.getResultSet();

	 
	        System.out.println("---------------------------------------------------------");
	        System.out.println(results.getExactResultSize()+" documents were scored");
	        System.out.println("The top "+results.getResultSize()+" of those documents were returned");
	        System.out.println("Document Ranking");
	        for (int i =0; i< results.getResultSize(); i++) {
	            int docid = results.getDocids()[i];
	            double score = results.getScores()[i];
	            System.out.println("   Rank "+i+": "+docid+" "+results.getMetaItem("filename", i)+" "+score);
	        }
	  }
}