import java.util.HashMap;
// City Class
public class City implements Comparable<City> {
	private String name;
	private int shortestDistance;
	private int keyValue;
	private City parent;
	private HashMap<String,Integer>adjacents=new HashMap<String,Integer>();
	//Constructor
	public City(String name, HashMap<String, Integer> adjacents) {
		super();
		this.name = name;
		this.adjacents =adjacents;
		this.shortestDistance=Integer.MAX_VALUE;
		this.keyValue=Integer.MAX_VALUE;
	}
	// adding hashmap
	public void addHash(String x,int y)  {
		this.adjacents.put(x, y);
	}
	public int getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(int keyValue) {
		this.keyValue = keyValue;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", shortestDistance=" + shortestDistance + ", adjacents="
				+ adjacents + "]";
	}

	public void setZeroDistance(){
		shortestDistance=0;
	}
	public HashMap<String,Integer> getLowestWeight(){
		int minval=Integer.MAX_VALUE;
		String minCity="";
		
		for(String each:adjacents.keySet()) {
			if(adjacents.get(each)<minval) {
				minval=adjacents.get(each);
				minCity=each;
			}
		}
		HashMap<String,Integer> returna=new HashMap<String,Integer>();
		returna.put(minCity, minval);
		return returna;
	}
	public HashMap<String, Integer> getAdjacents() {
		
		return adjacents;
	}
	public void setAdjacents(HashMap<String, Integer> adjacents) {
		this.adjacents = adjacents;
	}
	public String getName() {
		return name; 
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getShortestDistance() {
		return shortestDistance;
	}
	public void setShortestDistance(int shortestPath) {
		this.shortestDistance = shortestPath;
	}
	public City getParent() {
		return parent;
	}
	public void setParent(City parent) {
		this.parent = parent;
	}
	@Override
	public int compareTo(City o) {
		// TODO Auto-generated method stub
		if(this.shortestDistance-o.shortestDistance>0) {
			return 1;
		}
		else if(this.shortestDistance-o.shortestDistance<0) {
			return -1;
		}
		return 0;
	}

	
}
