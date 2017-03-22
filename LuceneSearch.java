package cs454_indexing_demo;

import static cs454_indexing_demo.LuceneDemo.FIELD_CONTENTS;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

public class LuceneSearch {

	public static final String INDEX_PATH = "/Users/manishpurohit/Documents/Csula/454/finalProjectIndex";

	public HashMap<String, Double> vector(String query1) throws IOException, ParseException {
		HashMap<String, Double> hm = new HashMap<String, Double>();

		File indexDir = new File(INDEX_PATH);
		String query = query1;
		int maxHits = 100;

		IndexReader reader = IndexReader.open(FSDirectory.open(new File(INDEX_PATH)));
		IndexSearcher searcher = new IndexSearcher(reader);

		//   reader.document(i);
		//   Directory fsDir = FSDirectory.open(indexDir);
		//  DirectoryReader reader = DirectoryReader.open(fsDir);
		// IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);

			QueryParser parser = new QueryParser(Version.LUCENE_35,FIELD_CONTENTS, analyzer);

			Query q= parser.parse(query);

		

		TopDocs hits = searcher.search(q, maxHits);
		ScoreDoc[] scoreDocs = hits.scoreDocs;
		System.out.println("hits=" + scoreDocs.length);
		System.out.println("Hits (rank,score,docId)");
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			double score = sd.score;
			int docId = sd.doc;

			hm.put(Integer.toString(docId) + "~", score);
			System.out.printf("%3d %4.2f %d\n",
					n, score, docId);
		}
		reader.close();

		return hm;

	}

	public static void main(String[] args)
			throws ParseException, CorruptIndexException,
			IOException {

		File indexDir = new File(INDEX_PATH);
		String query = "JAVA";
                
		int maxHits = 100;

		IndexReader reader = IndexReader.open(FSDirectory.open(new File(INDEX_PATH)));
		IndexSearcher searcher = new IndexSearcher(reader);

		//   reader.document(i);
		//   Directory fsDir = FSDirectory.open(indexDir);
		//  DirectoryReader reader = DirectoryReader.open(fsDir);
		// IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);

			QueryParser parser = new QueryParser(Version.LUCENE_35,FIELD_CONTENTS, analyzer);

			Query q= parser.parse(query);

		

		TopDocs hits = searcher.search(q, maxHits);
		ScoreDoc[] scoreDocs = hits.scoreDocs;
		System.out.println("hits=" + scoreDocs.length);
		System.out.println("Hits (rank,score,docId)");
		for (int n = 0; n < scoreDocs.length; ++n) {
			ScoreDoc sd = scoreDocs[n];
			float score = sd.score;
			int docId = sd.doc;

			System.out.printf("%3d %4.2f %d\n",
					n, score, docId);
		}
		reader.close();
	}
}
