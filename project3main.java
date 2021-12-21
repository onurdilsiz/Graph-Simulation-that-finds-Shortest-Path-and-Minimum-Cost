import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class project3main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File inFile = new File(args[0]);  // args[0] is the input file
		File outFile = new File(args[1]);  // args[1] is the output file
		
		PrintStream outstream1;//for printing outfile
		try {
		        outstream1 = new PrintStream(outFile);
		}catch(FileNotFoundException e2) {
		        e2.printStackTrace();
		        return;
		}
		Scanner reader;//to scan input
		try {
			reader = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find input file");
			outstream1.close();
			return;
		}
		//reading the input
		String firstline=reader.nextLine();
		String[] timel=firstline.split(" ");
		int timelimit=Integer.parseInt(timel[0]);
		
		String secondline=reader.nextLine();
		String[] second=secondline.split(" ");
		int numofCities=Integer.parseInt(second[0]);
		
		String thirdline=reader.nextLine();
		String[] third=thirdline.split(" ");
		String mecnun=third[0];
		String leyla=third[1];
		
		int n=1;
		//necessary data structures
		PriorityQueue<City>unvisited2=new PriorityQueue<City>();
		HashMap<String, City >cityMap=new HashMap<String,City>();
		Set<String> set = new HashSet<String> ();
		PriorityQueue<City>unvisited4=new PriorityQueue<City>();
		//scanning input
		//boolean to differ d's from c's
		boolean edgesB=false;

		while(reader.hasNextLine()) {
			
			try {
			String city=reader.nextLine();
			String[] line=city.split(" ");
			String name=line[0];	
			if(name.equals(leyla)) {
				edgesB=true;
			}
			//adding adjacents to cities
			HashMap<String,Integer> hash=new HashMap<String,Integer>();
			int length=line.length;
			for(int i=1;i<length;i+=2) {
				if(!(name.equals(leyla)&&line[i].substring(0,1).equals("c"))) {
					hash.put(line[i],Integer.parseInt(line[i+1]));
				}
				if(edgesB) {
					if(!line[i].substring(0,1).equals("c")) {
						if(!name.substring(0,1).equals("c")&&Integer.parseInt(name.substring(1))>Integer.parseInt(line[i].substring(1))){
							if(cityMap.get(line[i]).getAdjacents()!=null) {
								if(cityMap.get(line[i]).getAdjacents().containsKey(name)) {
									if(cityMap.get(line[i]).getAdjacents().get(name)>Integer.parseInt(line[i+1])) {	
										cityMap.get(line[i]).addHash(name, Integer.parseInt(line[i+1]));
									}
								}	
							else {	
							cityMap.get(line[i]).addHash(name, Integer.parseInt(line[i+1]));
							}
						}
					}
						
				}
				
				}
			}
			if(edgesB==true) {
				set.add(name);
			}
			
			City newcity=new City(name,hash);
			
			if(edgesB==true) {
				unvisited4.add(newcity);
			}
		
			cityMap.put(name,newcity);
			

			n=n+1;
			}
			catch(Exception e) {}}
// adding adjacents to cities
		
		for(City each:unvisited4) {
			for(String x:each.getAdjacents().keySet()) {
				if((Integer.parseInt(x.substring(1))>Integer.parseInt(each.getName().substring(1)))) {
					if(cityMap.get(x).getAdjacents().containsKey(each.getName())) { 
						if(cityMap.get(x).getAdjacents().get(each.getName())>each.getAdjacents().get(x)) {
							cityMap.get(x).addHash(each.getName(),each.getAdjacents().get(x) );
							
						}
				}	else {
					cityMap.get(x).addHash(each.getName(),each.getAdjacents().get(x) );
					
				}
					}
			}
			
		}
		
		
		HashSet<City>visited=new HashSet<City>();
		// starting from  mecnun cities
		cityMap.get(mecnun).setZeroDistance();
		unvisited2.add(cityMap.get(mecnun));
		//Djikstra
		while(!unvisited2.isEmpty()) {
			City evaluated=unvisited2.poll();

			if(evaluated==cityMap.get(leyla)) {
				break;
			}
			if(visited.contains(evaluated)) {
				continue;
			}
			for(String each:evaluated.getAdjacents().keySet()) {

				int distance=evaluated.getShortestDistance()+evaluated.getAdjacents().get(each);
				if(cityMap.get(each).getShortestDistance()>distance) {
					cityMap.get(each).setParent(evaluated);
					cityMap.get(each).setShortestDistance(distance);
					unvisited2.add(cityMap.get(each));
				}
				
			}
		}
	
		String output1="";
		City thecity=cityMap.get(leyla);

		// decide path output
		boolean path=false;
		while(thecity.getParent()!=null) {

			output1=thecity.getParent().getName()+" "+output1;
			thecity=cityMap.get(thecity.getParent().getName());
			
		}
		
		if(!output1.equals("")) {
			path=true;
			outstream1.println(output1+leyla);
		}
		
		//Prim's algorithm
		PriorityQueue<City>unvisited5=new PriorityQueue<City>();
		unvisited5.add(cityMap.get(leyla));
		
		Set<String> visitedset = new HashSet<String> ();
		int totalcost=0;

		Integer djikstra=cityMap.get(leyla).getShortestDistance();
		cityMap.get(leyla).setShortestDistance(0);;
		// boolean for honeymoon  
		boolean honeymoon=false;
		try {
		while(!visitedset.containsAll(set)){
			City u=unvisited5.poll();
			if(visitedset.contains(u.getName())) {
				continue;
			}
			visitedset.add(u.getName());

			totalcost=totalcost+u.getShortestDistance();
			for(String each:u.getAdjacents().keySet()) {
				if(each.equals(u.getName())) {
					continue;
				}
				
				int distance=u.getAdjacents().get(each);
				if(cityMap.get(each).getShortestDistance()>distance) {
					if(unvisited5.contains(cityMap.get(each))){
						unvisited5.remove(cityMap.get(each));
						cityMap.get(each).setParent(u);
						cityMap.get(each).setShortestDistance(distance);
						unvisited5.add(cityMap.get(each));
					}else {
						cityMap.get(each).setParent(u);
						cityMap.get(each).setShortestDistance(distance);
						unvisited5.add(cityMap.get(each));
						
					}

				}	
			}
			honeymoon=true;
			
		}
		}
		catch(Exception e) {
			honeymoon=false;
		}
		//printing output
		if(path) {
			if(djikstra>timelimit) {
				outstream1.println(-1);
			}else if(honeymoon) {
				outstream1.println(2*totalcost);
			}else if(!honeymoon) {
				outstream1.println("-2");
			}
			
		}
		if(!path) {
			outstream1.println(-1);
			outstream1.println(-1);
		}
		
		}
	

	}


