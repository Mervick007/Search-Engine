package controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mongodb.MongoClient;

public class Connexion {

	public  Connection conn;

	protected Statement stmt;
	protected ResultSet rset;
	protected ResultSetMetaData rsetMeta;
	private static Connexion Instance = null;
	public String userName = "root";
	public String password = "root";
	public String serverName = "LOCALHOST";
	public String portNumber = "3306";

	//use cs454




	public DB Connect()
	{DB db=null;
	try{

		// To connect to mongodb server
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

		// Now connect to your databases
		db = mongoClient.getDB( "cs454" );
		System.out.println("Connect to database successfully");

	}catch(Exception e){
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	}

	return db;  

	}





	public Connexion() 
	{
		connect();	
	}

	public static Connexion getInstance() 
	{
		if (Instance == null) {
			Instance = new Connexion();
		}
		return Instance;
	}



	// Connection to Database


	// close the connection after every commit
	public void close() 
	{
		try {
			getInstance().close();
		} catch (Exception e) {
			System.out.println("exception in close :" + e);
		}
	}




	protected void connect() 
	{
		try {


			MysqlDataSource dataSource = new MysqlDataSource();

			dataSource.setUser(userName);
			dataSource.setPassword(password);
			dataSource.setServerName(serverName);
			dataSource.setDatabaseName("manish");

			conn = dataSource.getConnection();

			//    System.out.println("Database connected :");

		} catch (Exception e) {
			System.out.println("Exception in connect :" + e);
			e.printStackTrace();
			try {
				conn.close();
			} catch (Exception ee) {
			}
		}


	}

	protected void checkConnection() {
		try {
			if (conn.isClosed() || !conn.isValid(1)) {
				connect();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void init() {
		getInstance();
	}



	public  void insertData(String filename,String url,int size,int outgoinglink,String filepath,String metadata) 
	{
		try {


			checkConnection();      
			String requete = "INSERT INTO webcrawl (filename,url,size,outgoinglink,filepath,metadata) VALUES (?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);

			pstmt.setObject(1, filename);
			pstmt.setObject(2, url);
			pstmt.setObject(3, size);
			pstmt.setObject(4, outgoinglink);
			pstmt.setObject(5, filepath);
			pstmt.setObject(6, metadata);

			pstmt.executeUpdate();

			//conn.close();


		} 
		catch (Exception e) 
		{
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
		}

	}


	public  ResultSet viewData() 
	{
		ResultSet rs=null;

		try {

			checkConnection();      
			String requete = "select * from webcrawl";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);
			rs=pstmt.executeQuery();
                        


		} catch (Exception e) {
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
			//	return 0;
		}
		return rs;
	}



	public  void insertDataForLinkAnalysis(int id,int outgoinglink,String filepath,int incominglink,double pagerank) 
	{
		try {



			checkConnection();      
			String requete = "INSERT INTO finallinkAnalysis (id,outgoinglink,filepath,incominglink,pagerank) VALUES (?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);

			pstmt.setObject(1, id);
			pstmt.setObject(2, outgoinglink);
			pstmt.setObject(3, filepath);
			pstmt.setObject(4, incominglink);
			pstmt.setObject(5, pagerank);
			pstmt.executeUpdate();

			conn.close();



		} 
		catch (Exception e) 
		{
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
		}

	}


	public  void updateDataForLinkAnalysis(int id,Double pagerank) 
	{
		try {


			checkConnection();      
			String requete = "UPDATE finallinkAnalysis set pagerank=? where id=?";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);

			pstmt.setObject(1, pagerank);
			pstmt.setObject(2, id);

			pstmt.executeUpdate();

			conn.close();


		} 
		catch (Exception e) 
		{
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
		}

	}

	public double getlinkAnalysis(int id)
	{
		ResultSet rs=null;
		double pagerank=0;
		try {
			//System.out.printf("hii");
			checkConnection();      
			String requete = "select pagerank from finallinkAnalysis where id=?";


			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);
			pstmt.setObject(1, id);
			rs=pstmt.executeQuery();

			while(rs.next())
			{
				pagerank   =rs.getDouble(1);

			}

			rs.close();
                        conn.close();
		} catch (Exception e) {
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
			//	return 0;
		}
		return pagerank;


	}

	public double gettfidf(int id,String term )
	{
		ResultSet rs=null;
		double pagerank=0;
		try {
			//System.out.printf("hii");
			checkConnection();      
			String requete = "select value from tfidf where documentid=? and term=? ";


			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);
			pstmt.setObject(1, id);
			pstmt.setObject(2, term);
			rs=pstmt.executeQuery();

			while(rs.next())
			{
				pagerank   =rs.getDouble(1);

			}

			rs.close();
                        conn.close();
		} catch (Exception e) {
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
			//	return 0;
		}
		return pagerank;


	}

	public String getFilePath(int id )
	{
		ResultSet rs=null;
		String s="";
		try {
			//System.out.printf("hii");
			checkConnection();      
			String requete = "select filepath from finallinkAnalysis where id=? ";


			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);
			pstmt.setObject(1, id);

			rs=pstmt.executeQuery();

			while(rs.next())
			{
				s   =rs.getString(1);

			}

			rs.close();
                        conn.close();
		} catch (Exception e) {
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
			//	return 0;
		}
		return s;


	}











	public  Double viewPageRank(int id) 
	{
		ResultSet rs=null;
		Double pagerank=null;
		try {

			checkConnection();      
			String requete = "select pagerank from finallinkAnalysis where id=?";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);
			pstmt.setObject(1, id);
			rs=pstmt.executeQuery();

			while(rs.next())
			{
				pagerank   =rs.getDouble(1);
			}

			rs.close();
                        conn.close();
		} catch (Exception e) {
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
			//	return 0;
		}
		return pagerank;
	}


	public  Double viewOutgoingLink(int id) 
	{
		ResultSet rs=null;
		Double pagerank=null;
		try {

			checkConnection();      
			String requete = "select outgoinglink from finallinkAnalysis where id=?";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);
			pstmt.setObject(1, id);
			rs=pstmt.executeQuery();

			while(rs.next())
			{
				pagerank   =rs.getDouble(1);
			}

			rs.close();
                        conn.close();
		} catch (Exception e) {
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
			//	return 0;
		}
		return pagerank;
	}

        
        
        
        
	public  void insertTfid(int documentid,String term,double value) 
	{
		try {


			checkConnection();      
			String requete = "INSERT INTO tfidf (documentid,term,value) VALUES (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(requete,rset.TYPE_SCROLL_INSENSITIVE,rset.CONCUR_UPDATABLE);

			pstmt.setObject(1, documentid);
			pstmt.setObject(2, term);
			pstmt.setObject(3, value);
			//pstmt.setObject(4, path);
			pstmt.executeUpdate();

			conn.close();


		} 
		catch (Exception e) 
		{
			System.out.println("User Meeting is already created :" + e);
			e.printStackTrace();
		}

	}   
}