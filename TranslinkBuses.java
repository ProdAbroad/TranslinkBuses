import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class TranslinkBuses {
	
public static final int WEIGHT_TRANSFERTYPE_ZERO = 0;
public static final int WEIGHT_FROM_STOPTIMES = 1;
public static final int ONE_HUNDRED = 100;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner input = new Scanner ( System.in );
		ArrayList<String> BusStopsArrayList = new ArrayList<String>();
		EdgeWeightedDiGraph stopsDiGraph = new EdgeWeightedDiGraph(700000);
		try(BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\harry\\git\\TranslinkBuses\\stop_times.txt"),StandardCharsets.UTF_8))){
		    String line = br.readLine();
		    String prevLine = br.readLine();
		  	while((line=br.readLine())!=null){
		      	BusStopsArrayList.add(line);
		    	String[] splitLine=line.split(",");
		    	String[] splitPrev=prevLine.split(",");
		    	if (Integer.parseInt(splitLine[0])==Integer.parseInt(splitPrev[0])) {
		    		stopsDiGraph.addEdge(new DirectedEdge(Integer.parseInt(splitPrev[3]),Integer.parseInt(splitLine[3]),WEIGHT_FROM_STOPTIMES));
		    	}
		    	prevLine = line;
		    }
		  	
		} 
		ArrayList<String> BusTransfersArrayList = new ArrayList<String>();
		try(BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\harry\\git\\TranslinkBuses\\transfers.txt"),StandardCharsets.UTF_8))){
		    String line = br.readLine();
		  	while((line=br.readLine())!=null){
		      	BusTransfersArrayList.add(line);
		    	String[] splitLine=line.split(",");
		    	if (splitLine[2] .equals("0")){
		    		stopsDiGraph.addEdge(new DirectedEdge(Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]), WEIGHT_TRANSFERTYPE_ZERO));
		    	}
		    	if (splitLine[2] .equals("2")){
		    		stopsDiGraph.addEdge(new DirectedEdge(Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]), (Integer.parseInt(splitLine[3]) /ONE_HUNDRED)));
		    	}
		    }
		}
		ArrayList<String> StopsArrayList = new ArrayList<String>();
		TST<String> stopsTSTGraph = new TST<String>();
		try(BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\harry\\git\\TranslinkBuses\\stops.txt"),StandardCharsets.UTF_8))){
			  String line = br.readLine();
			  while((line=br.readLine())!=null){
				  StopsArrayList.add(line);
				  String[] splitLine=line.split(",");
				  String searchVariable = splitLine[2];
				  searchVariable = searchVariable.replace("FLAGSTOP ", "");
				  searchVariable = searchVariable.replace("WB ", "");
				  searchVariable = searchVariable.replace("NB ", "");
				  searchVariable = searchVariable.replace("SB ", "");
				  searchVariable = searchVariable.replace("EB ", "");
				  stopsTSTGraph.put(searchVariable, line);
				  }
		}
		System.out.print("Welcome to the Vancouver Translink online public bus service.");
		boolean quit = true;
		while (quit == true)
		{
			System.out.print("\nChoose a service to use by entering the corresponding number in integer form.\n\n"
								+ "1. Find the shortest paths between 2 bus stops, after entering seperately,\n"
								+ "returning the list of stops en route as well as the associated “cost”.\n"
								+ "2. Search for a bus stop using the full name or the first few characters in the name,"
								+ "this will return the full stop information for each stop matching the\n"
								+ "search criteria (which can be zero, one or more stops).\n"
								+ "3. Search for all trips with a given arrival time, returning full details of all\n"
								+ "trips matching the criteria (zero, one or more).\n"
								+ "4. Exit Vancouver Translink online service.\n");
			int inputFromUser = input.nextInt();
			if (inputFromUser == 1)
			{
				System.out.print("Enter the first stop.");
				int v;
				v = input.nextInt();
				System.out.print("Enter the second stop.");
				int w;
				w = input.nextInt();
				DijkstraSP  questionOneDijkstraFunction= new DijkstraSP(stopsDiGraph, v);
				questionOneDijkstraFunction.distTo(w);
				System.out.print(questionOneDijkstraFunction.pathTo(w) + "," + questionOneDijkstraFunction.distTo(w));
			}
			if (inputFromUser == 2)
			{
				System.out.print("Enter in a stop name, or the first few characters relevant to the name in Upper Case eg. HASTINGS.");
				String stopIDFromUser = input.next();
				Queue<String> printableList;
				printableList = (Queue<String>) stopsTSTGraph.keysWithPrefix(stopIDFromUser);
				System.out.println(printableList);
			}
			if	(inputFromUser == 3)
			{
				System.out.print("Coming to a bus system near you soon!");
			}
			if (inputFromUser == 4)
			{
				quit = false;
			}
		}
		input.close();
	}
}

