/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import cs454_indexing_demo.LuceneSearch;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Search;
import static model.cal.ASC;
import model.format;
import model.normalize;
import model.ranking1;
import model.searchData;

/**
 *
 * @author manishpurohit
 */
public class practise extends HttpServlet {

   public static boolean ASC = true;
	public static boolean DESC = false;

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */


			try
			{  
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet webSearch</title>");            
				out.println("</head>");
				out.println("<body>");

				
				String box=request.getParameter("wsdl");

				out.println("query=="+box);

                                 request.setAttribute("my", box);

                                
                                String query=request.getParameter("txtSearch");

				out.println("query=="+query);

                              
                                
                                
                                
                              // tf -idf score  
				if(box.equalsIgnoreCase("2"))
				{   



                                        


            			//		List <Integer> l= new Search().searchData(query);




					//HashMap <String, Double> hm = new HashMap();
					//   out.println("User ");
                                    //    ranking1.tfidscore(query);
 
                                        
                             HashMap <String, Double> hm= ranking1.tfidscore(query); 
                             
                             
                             
				/*	
                             for(int i=0;i<l.size();i++)
					{
						int id= l.get(i);

						// out.println("id=="+id+" term="+query);

						Double d=  new controller.Connexion().gettfidf(id, query);
                                                
						String filepath = new controller.Connexion().getFilePath(id);

						System.out.println("filepath== :" + filepath);

						hm.put(id + "~" + filepath,d);


					}
                                    */
					Map<String, Double> sortedMapAsc = sortByComparator(hm, DESC);
					getServletContext().setAttribute("query", sortedMapAsc);
                                        
                                        
                                     
					RequestDispatcher r=request.getRequestDispatcher("view.jsp");
					r.forward(request, response);






				}








                                    // link analysis
				else if(box.equalsIgnoreCase("3"))
				{


					List <Integer> l= new Search().searchData(query);




					HashMap <String, Double> hm = new HashMap();
					//   out.println("User ");


					for(int i=0;i<l.size();i++)
					{
						int id= l.get(i);

						out.println("id=="+id+" term="+query);

						Double d=  new controller.Connexion().getlinkAnalysis(id);
						String filepath = new controller.Connexion().getFilePath(id);

						//out.println("User Meeting is already created :" + d);
						hm.put(Integer.toString(id) + "~" + filepath,d);


					}

					Map<String, Double> sortedMapAsc = sortByComparator(hm, DESC);
					getServletContext().setAttribute("query", sortedMapAsc);
                                            
                                       
					   RequestDispatcher r=request.getRequestDispatcher("view.jsp");
					  r.forward(request, response);



				}else if(box.equalsIgnoreCase("4"))
				{   
                                        //vector space model


					HashMap<String,Double> hm= new LuceneSearch().vector(query);

					Map<String, Double> sortedMapAsc = sortByComparator(hm, DESC);
					getServletContext().setAttribute("query", sortedMapAsc);

					RequestDispatcher r=request.getRequestDispatcher("view.jsp");
					r.forward(request, response);




				}else
				{
                                //  normalized 
                                    
                                String prioritymethod=request.getParameter("method");

				out.println("method=="+prioritymethod);

                                
                                HashMap <String, Double> hm1= ranking1.tfidscore(query); 
                                  //  Double tfrank1=hm1.get("3254");
                                   // System.out.println("key value"+tfrank1);
                                
                                
					List <Integer> l= new Search().searchData(query);

                                     if(prioritymethod.equalsIgnoreCase("A"))
                                         
                                     {     

					HashMap <String, Double> hm = new HashMap();
					//   out.println("User ");


					for(int i=0;i<l.size();i++)
					{
						int id= l.get(i);

						//   out.println("id=="+id+" term="+query);

						
						String filepath = new controller.Connexion().getFilePath(id);
						System.out.println("id=="+id);

					//	Double linknor= new normalize().normalize(linkrank)*0.7;
						
                                               
                                                Double tfrank=hm1.get(id+"");
                                                
                                                        //new controller.Connexion().gettfidf(id, query);
                                                
                                                System.out.print("id=="+id+"tfrank=="+tfrank);
                                                
                                                
                                                MathContext mc = new MathContext(4);
                                                Double linkrank=  new controller.Connexion().getlinkAnalysis(id);
                                                BigDecimal bd1 = new BigDecimal(new normalize().normalize(linkrank));
                                                BigDecimal bd2 = new BigDecimal(0.1d);
                                                BigDecimal result1 = bd1.multiply(bd2,mc);
                                                
                                                System.out.print("value123=="+result1.doubleValue());
                                                    Double  sum1=(tfrank*0.9)+(linkrank*0.1);
                                          
					//	Double tfnor= new normalize().normalize(tfrank) *0.3;

                                                BigDecimal bd3 = new BigDecimal(new normalize().normalize(tfrank));
                                                BigDecimal bd4 = new BigDecimal(0.9d);
                                                BigDecimal result2 = bd3.multiply(bd4,mc);

                                                BigDecimal sum=result1.add(result2, mc);
						//out.println("User Meeting is already created :" + d);
						hm.put(Integer.toString(id) + "~" + filepath + "~" + bd1.doubleValue() + "~" + tfrank,sum.doubleValue());
	//hm.put(Integer.toString(id) + "~" + filepath + "~" + linkrank + "~" + tfrank,sum1);


					}

					Map<String, Double> sortedMapAsc = sortByComparator(hm, DESC);
					getServletContext().setAttribute("query", sortedMapAsc);

					RequestDispatcher r=request.getRequestDispatcher("view.jsp");
					r.forward(request, response);








				}
                                else
                                {
                                 
                                    //more weightage to link analysis
                                    
                                    HashMap <String, Double> hm = new HashMap();
					//   out.println("User ");


					for(int i=0;i<l.size();i++)
					{
						int id= l.get(i);

						//   out.println("id=="+id+" term="+query);

						
						String filepath = new controller.Connexion().getFilePath(id);
						out.println("id=="+id);

					//	Double linknor= new normalize().normalize(linkrank)*0.7;
						
                                                
                                                Double tfrank= hm1.get(id+""); 
                                                
                                                System.out.print("id=="+id+"tfrank=="+tfrank);
                                                
                                                
                                                
                                                MathContext mc = new MathContext(4);
                                                Double linkrank=  new controller.Connexion().getlinkAnalysis(id);
                                                BigDecimal bd1 = new BigDecimal(new normalize().normalize(linkrank));
                                                BigDecimal bd2 = new BigDecimal(0.9d);
                                                BigDecimal result1 = bd1.multiply(bd2,mc);
                                                
                                                System.out.print("value123=="+result1.doubleValue());
                                                
					//	Double tfnor= new normalize().normalize(tfrank) *0.3;

                                                BigDecimal bd3 = new BigDecimal(new normalize().normalize(tfrank));
                                                BigDecimal bd4 = new BigDecimal(0.1d);
                                                BigDecimal result2 = bd3.multiply(bd4,mc);

                                                BigDecimal sum=result1.add(result2, mc);
                                                
                                                      Double  sum2=(tfrank*0.1)+(linkrank*0.9);
                                          
						//out.println("User Meeting is already created :" + d);
				//		hm.put(Integer.toString(id) + "~" + filepath + "~" + linkrank + "~" + tfrank,sum2);
	hm.put(Integer.toString(id) + "~" + filepath + "~" + bd1.doubleValue() + "~" + tfrank,sum.doubleValue());

					}

					Map<String, Double> sortedMapAsc = sortByComparator(hm, DESC);
					getServletContext().setAttribute("query", sortedMapAsc);

					RequestDispatcher r=request.getRequestDispatcher("view.jsp");
					r.forward(request, response);








                                    
                                    
                                    
                                    
                                    
                                        
                                        
                                        
                                        
                                        }


                                }
			}

			catch(Exception ae)
			{
			System.	out.println(ae);
			System.out.println(ae.getMessage());

			}







			out.println("</body>");
			out.println("</html>");
		}
	}


	private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
	{

		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>()
		{
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				}
				else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Double> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<String, Double> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}





	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}




	public static void printMap(Map<String, Double>  map)
	{
		for (Map.Entry<String, Double>  entry : map.entrySet())
		{
			System.out.println("Key : " + entry.getKey() + " Value : "+ entry.getValue());
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}

    
    
   

