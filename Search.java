/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static cs454_indexing_demo.LuceneDemo.FIELD_CONTENTS;
import static cs454_indexing_demo.LuceneSearch.INDEX_PATH;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Search {

	List l=new ArrayList();

	public List searchData(String query)
	{

		try
		{

			File indexDir = new File(INDEX_PATH);
			String query1 = query;
			int maxHits = 100;


			IndexReader reader = IndexReader.open(FSDirectory.open(new File(INDEX_PATH)));
			IndexSearcher searcher = new IndexSearcher(reader);


			//   reader.document(i);

			//   Directory fsDir = FSDirectory.open(indexDir);
			//  DirectoryReader reader = DirectoryReader.open(fsDir);
			// IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);

			QueryParser parser = new QueryParser(Version.LUCENE_35,FIELD_CONTENTS, analyzer);

			Query q= parser.parse(query1);

			TopDocs hits = searcher.search(q,maxHits);
			ScoreDoc[] scoreDocs = hits.scoreDocs;
			System.out.println("hits=" + scoreDocs.length);
			System.out.println("Hits (rank,score,docId)");
			for (int n = 0; n < scoreDocs.length; ++n) {
				ScoreDoc sd = scoreDocs[n];
				float score = sd.score;
				int docId = sd.doc;

				l.add(docId);

				System.out.printf("%3d %4.2f %d\n",
						n, score, docId);
			}
			reader.close();
		}catch(Exception ae)
		{

		}
		return l; 
	}

}





