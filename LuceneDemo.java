 package cs454_indexing_demo;

import controller.Connexion;
import static cs454_indexing_demo.LuceneSearch.INDEX_PATH;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import model.Search;
import model.StopWords;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.*;
import org.apache.tika.sax.*;
import org.tartarus.snowball.ext.PorterStemmer;
import org.xml.sax.ContentHandler;


 public class LuceneDemo {

    public static final String FILES_TO_INDEX_DIRECTORY = "/Users/manishpurohit/Documents/Csula/454/finalProject";

    public static final String INDEX_DIRECTORY = "/Users/manishpurohit/Documents/Csula/454/finalProjectIndex";

    public static final String FIELD_PATH = "path";
    public static final String FIELD_CONTENTS = "contents";
    IndexWriter indexWriter = null;
    
    
    PorterStemmer stemmer = new PorterStemmer();

    public static void fileRead() {

        Path file = Paths.get("/Users/manishpurohit/Documents/Csula/454/finalProject");
        try (InputStream in = Files.newInputStream(file);
                BufferedReader reader
                = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception x) {
            x.printStackTrace();
            System.err.println(x);
        }

    }

    public static void main(String[] args) throws Exception {

        IndexReader r = IndexReader.open(NIOFSDirectory.open(new File(INDEX_DIRECTORY)));

        double dump = 0.85, rank = 0;
        try {
			//	new LuceneDemo().createIndex();
            //	searchIndex("(pyruvate_kinase)-phosphatase.html");

            System.out.println("d=" + r.maxDoc());

            double num = r.numDocs();
/*
            double d1 = (double) (1 / num);

            System.out.print("d1===" + d1);
            
             for (int i = 0; i < num; i++) {
             if (!r.isDeleted(i)) {

             // Step 1:
             Document d = r.document(i);

             String path = d.get(FIELD_PATH);
             String name = FilenameUtils.getName(path);

             //      System.out.print("path==="+path);
             int m = new HtmlParser().format(path);

             //   System.out.print("k=="+m);
             new Connexion().insertDataForLinkAnalysis(i, m, path, 0, d1);

             }

             }
              */     
             

            for (int p = 0; p < 5; p++) {

                for (int i = 0; i <= num; i++) {
                    if (!r.isDeleted(i)) {

                        // Step 1:
                        Document d = r.document(i);

                        String path = d.get(FIELD_PATH);
                        String name = FilenameUtils.getName(path);

                        System.out.print("path===" + path);

                        int m = new HtmlParser().format(path);

                        System.out.print("k==" + m);

                     //  new Search().searchIndex(name);

                        //      Step 2:
                          int j[] =new int[10000];
                        List l=new Search().searchData(name);
                        
                        
                        
                    //  = searchIndex1(name);

                   //     if (j != null) {

                            System.out.println("total no of id==" + j.length);
                            Double pageRank = 0.0;
                            for (int k = 0; k < l.size(); k++) {

                
                                Double pagerank = new Connexion().viewPageRank((int) l.get(k));
                                Double outgoinglink = new Connexion().viewOutgoingLink((int) l.get(k));

                                //rank=rank+dump * (naaaaaaaew Connexion().viewPageRank(j[k])/new Connexion().viewOutgoingLink(j[k]) );  
                                if (outgoinglink != 0) {
                                    rank = rank +  (pagerank / outgoinglink);
                                }

								//   System.out.println("here=="+j[k]);
                                //  System.out.println("Inside loop Rank == " + rank);
                            }
                            rank=((1-dump)+dump*rank);
                            new Connexion().updateDataForLinkAnalysis(i, rank);

                    //    }

                    }
                }
            }

            r.close();
             
             
			//  final File folder = new File(INDEX_DIRECTORY);
            // listFilesForFolder1(folder);
            //	searchIndex("steak");
            //	searchIndex("steak AND cheese");
            //	searchIndex("steak and cheese");
            //	searchIndex("wiki");
        } catch (Exception ae) {
            ae.printStackTrace();

        }

    }

    public static void listFilesForFolder1(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder1(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

    public void listFilesForFolder(final File folder) throws Exception {

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
    
                FileInputStream fis;
				try {
					fis = new FileInputStream(folder);
				} catch (FileNotFoundException fnfe) {
					return;
				}
                
                                
                                
                                
                int maxStringLength = 10 * 1024 * 1024;
               
                WriteOutContentHandler handler = new WriteOutContentHandler(maxStringLength);
                ContentHandler contenthandler = new BodyContentHandler( handler);
                Metadata metadata = new Metadata();
                Parser parser = new AutoDetectParser();
                parser.parse(fis, contenthandler, metadata,
                        new ParseContext());
                String newString = contenthandler.toString()
                        .replaceAll("/[^a-zA-Z 0-9]+/g", " ")
                        .replaceAll("\\s+", " ").trim();
   Set<String> stopwords=StopWords.getStopwords();
                Tokenizer tokenizer = new StandardTokenizer(
                        Version.LUCENE_35, new StringReader(
                                newString.toLowerCase()));
                final StandardFilter standardFilter = new StandardFilter(
                        Version.LUCENE_35, tokenizer);
                final StopFilter stopFilter = new StopFilter(
                        Version.LUCENE_35, standardFilter,stopwords );
                final CharTermAttribute charTermAttribute = tokenizer
                        .addAttribute(CharTermAttribute.class);
                stopFilter.reset();
                StringBuilder sb = new StringBuilder();

                while (stopFilter.incrementToken()) {
                    final String token = charTermAttribute.toString()
                            .toString();
                    stemmer.setCurrent(token);
                    stemmer.stem();
                    String word = stemmer.getCurrent();
                    if (!stopwords.contains(word)) {
                        sb.append(stemmer.getCurrent()).append(System.getProperty("line.separator"));
                    }
                }

                Document document = new Document();

                String path = fileEntry.getCanonicalPath();
                Field pathField = new Field("path", folder.getPath(),
							Field.Store.YES, Field.Index.NO);
                
                document.add(pathField);
                document.add(new Field("contents", new BufferedReader(
							new InputStreamReader(new ByteArrayInputStream(sb
									.toString().getBytes()), "UTF-8")),
							Field.TermVector.YES));
                
                
                indexWriter.addDocument(document);
                
                
                

            }
        }
    }
/*
    public void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException, Exception {

     //   Analyzer analyzer = new StandardAnalyzer();
		//  Directory directory = new RAMDirectory();

		//     Path p1 = Paths.get("/tmp/foo");
        // To store an index on disk, use this instead:
        //   Directory directory = FSDirectory.open(p1);
        //   IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //  IndexWriter iwriter = new IndexWriter(directory, config);
           //     List<String> words = new ArrayList();
//Read the file into words.
        //     Set<String> s= getStopwords();
        //   Set stopWords = StopFilter.makeStopSet(Version.LUCENE_36, s, true);
        boolean recreateIndexIfExists = true;
      //  indexWriter = new IndexWriter(INDEX_DIRECTORY, analyzer, recreateIndexIfExists);
       // File dir = new File(FILES_TO_INDEX_DIRECTORY);
        
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35,
					analyzer);
        Directory dir = FSDirectory.open(new File(FILES_TO_INDEX_DIRECTORY));
        IndexWriter writer = new IndexWriter(dir, iwc);
			indexDocs( dir);

			writer.optimize();
			writer.close();
        
        

        final File folder = new File("/Users/manishpurohit/Documents/smalldata");
        listFilesForFolder(dir);

		//   File []f=dir.
        //File[] files = dir.listFiles();
		/*		for (File file : files) {



         Document document = new Document();

         String path = file.getCanonicalPath();
         document.add(new Field(FIELD_PATH, path, Field.Store.YES, Field.Index.UN_TOKENIZED));

         Reader reader = new FileReader(file);
         document.add(new Field(FIELD_CONTENTS, reader));

         indexWriter.addDocument(document);
         }
        indexWriter.optimize();
        indexWriter.close();
    }

*/
/*
    public static void searchIndex(String searchString) throws IOException, ParseException, org.apache.lucene.queryParser.ParseException {

        try {
            System.out.println("Searching for '" + searchString + "'");
            Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
            IndexReader indexReader = IndexReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            Analyzer analyzer = new StandardAnalyzer();
            QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
            Query query = queryParser.parse(searchString);
            Hits hits = indexSearcher.search(query);
            System.out.println("Number of hits: " + hits.length());

            Iterator it = hits.iterator();
            while (it.hasNext()) {
                Hit hit = (Hit) it.next();
                Document document = hit.getDocument();
                System.out.println("id===: " + hit.getId());

                String path = document.get(FIELD_PATH);
                System.out.println("Hit: " + path);
            }
        } catch (Exception ae) {
            ae.printStackTrace();

        }
    }
    
    */

    public static int[] searchIndex1(String searchString) throws IOException, ParseException, org.apache.lucene.queryParser.ParseException {
        int k[] = null;
        System.out.println("manish");
        try {
            
            
            	File indexDir = new File(INDEX_PATH);
			String query1 = searchString;
			int maxHits = 16;


			IndexReader reader = IndexReader.open(FSDirectory.open(new File(INDEX_PATH)));
			IndexSearcher searcher = new IndexSearcher(reader);


			//   reader.document(i);

			//   Directory fsDir = FSDirectory.open(indexDir);
			//  DirectoryReader reader = DirectoryReader.open(fsDir);
			// IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_35);

			QueryParser parser = new QueryParser(Version.LUCENE_35,FIELD_CONTENTS, analyzer);

			Query query= parser.parse(query1);

                   	TopDocs hits = searcher.search(query,maxHits);
			ScoreDoc[] scoreDocs = hits.scoreDocs;
		  
            
            
            //     System.out.println("Searching for '" + searchString + "'");
         
             /*           
             Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
            IndexReader indexReader = IndexReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            Analyzer analyzer = new StandardAnalyzer();
            QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
            Query query = queryParser.parse(searchString);
          */
        //     Hits hits = indexSearcher.search(query);
            //	System.out.println("Number of hits: " + hits.length());

         
                        
                  int u = 0;       
            for (int n = 0; n < scoreDocs.length; ++n) {
				ScoreDoc sd = scoreDocs[n];
				float score = sd.score;
				int docId = sd.doc;

                                 k[u] = docId;
				//l.add(docId);
                                u++;
				System.out.printf("%3d %4.2f %d\n",
						n, score, docId);
			}            
                        
                        
                        
                        
                 } catch (Exception ae) {
            ae.printStackTrace();
        }

        return k;
    }
}
