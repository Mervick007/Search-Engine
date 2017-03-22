
    package model;

    import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static cs454_indexing_demo.LuceneDemo.FIELD_PATH;

    public class ranking1 {
            public static final String INDEX_DIRECTORY ="/Users/manishpurohit/Documents/Csula/454/finalProjectIndex";


       public static HashMap<String,Double> tfidscore(String data)

       {
         HashMap<String,Double> h =new HashMap<>();

         System.out.println("Inside ranking1 tfidf score");

         IndexReader r;
        try {
            int id[]=searchData.format(data);
            r = IndexReader.open(FSDirectory.open(new File(INDEX_DIRECTORY)));
            int num = r.numDocs();
            for (int j = 0; j <= id.length; j++) {
                System.out.println("tfidf data==" + id[j]);
                TermFreqVector tfv = r.getTermFreqVector(id[j], "contents");
                Document d = r.document(id[j]);
                String path = d.get(FIELD_PATH);
                System.out.println("path==" + path);
                String terms[] = tfv.getTerms();
                int termCount = terms.length;
                int freqs[] = tfv.getTermFrequencies();
                System.out.println("hello==" + termCount);
                for (int t = 0; t < termCount; t++) {

                    if (terms[t].equalsIgnoreCase(data)) {
                        double idf = r.numDocs() / r.docFreq(new Term("contents", terms[t]));
                        System.out.println(terms[t] + " data=== " + freqs[t] * Math.log(idf));
                        h.put(id[j] + "", freqs[t] * Math.log(idf));
                    }
                }
            }
        }catch(Exception ae)
        {
            ae.printStackTrace();
        }
       return h;
       }

       public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

       public static void main(String[] args)
       {
           HashMap <String, Double> hm= ranking1.tfidscore("david");
           printMap(hm);
       }

    }


























