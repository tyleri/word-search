import java.util.ArrayList;
import java.util.Random;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class WordSearch {

	char[][] grid;
	ArrayList<String> wordlist;

	public WordSearch() {
		this(10, 10);
	}

	public WordSearch(int rows, int cols) {
		grid = new char[rows][cols];
		wordlist = new ArrayList<>();
		
		// makes all of the characters '_'
		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				grid[i][j] = '_';
	}

	public String toString() {
		String s = "";
		
		// adds the elements in the grid to s
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++)
				s += grid[i][j] + " ";

			s += "\n";
		}

		// adds the list of words to s
		s += "\n";
		for (int i = 0; i < wordlist.size(); i++)
			s += wordlist.get(i) + "\n";

		return s;
	}

	public boolean addWordH(int row, int col, String s) {
		if (row < 0 || col < 0)
			return false;
		if (row >= grid.length || col >= grid[row].length)
			return false;

		s = s.toUpperCase();

		if (grid[row].length - col < s.length()) {
			// checks if there's enough space to fit the word
			return false;
		} else {
			// checks the letters in the grid that overlap the word
			for (int i = 0; i < s.length(); i++) {
				char curr = grid[row][col+i];
				if (curr != '_' && curr != s.charAt(i))
					return false;
			}
		}

		// if it gets here, the word fits
		// then, put the word into the grid
		for (int i = 0; i < s.length(); i++)
			grid[row][col+i] = s.charAt(i);

		wordlist.add(s);
		return true;
	}

	public boolean addWordV(int row, int col, String s) {
		if (row<0 || col < 0)
			return false;
		if (col >= grid.length || row >= grid[row].length)
			return false;
		s = s.toUpperCase();
		if (grid.length - row < s.length())
			return false;
		else{
			for (int i = 0; i < s.length(); i++){
				char curr = grid[row+i][col];
				if (curr != '_' && curr != s.charAt(i)){
					return false;
				}
			}
		}
		for (int i= 0; i < s.length(); i++)
			grid[row+i][col] = s.charAt(i);
		wordlist.add(s);
		return true;
		
	}

	public boolean addWordD(int row, int col, String s) {
		if (row < 0 || col < 0)
			return false;
		if (col >= grid.length || row >= grid[row].length)
			return false;
		s = s.toUpperCase();
		if (grid.length - row < s.length() || grid.length - col < s.length())
			return false;
		else{
			for (int i = 0; i < s.length(); i++){
				char curr = grid[row+i][col+i];
				if (curr != '_' && curr != s.charAt(i))
					return false;
			}
		}
		for (int i=0; i<s.length(); i++)
			grid[row+i][col+i] = s.charAt(i);
		wordlist.add(s);
		return true;
	}

	public void fillGrid() {
		Random r = new Random();

		for (int i = 0; i < grid.length; i++)
			for (int j = 0; j < grid[i].length; j++)
				if (grid[i][j] == '_') {
					char c = (char)('A' + r.nextInt(26));
					grid[i][j] = c;
				}
	}

	public void addWords(int n) {
		Random r = new Random();
		ArrayList<String> dictionary = loadDictionary();
		int row, col;
		String w;

		// adds n random words into random places in the grid
		for (int i = 0; i < n; i++) {
			row = r.nextInt(grid.length);
			col = r.nextInt(grid[row].length);
			w = dictionary.get( r.nextInt(dictionary.size()) );

			// only adds the word in one direction
			if ( addWordH(row, col, w) ) {}
			else if ( addWordV(row, col, w) ) {}
			else if ( addWordD(row, col, w) ) {}
			else
				i--;
		}
	}

	public ArrayList<String> loadDictionary() {
		String s = "zzz";
		ArrayList<String> dictionary = new ArrayList<String>();

		try {
			FileReader f = new FileReader("wordlist.txt");
			BufferedReader b = new BufferedReader(f);
			while( s != null ) {
				s = b.readLine();
				if ( s != null )
					dictionary.add(s);
			}
		}
		catch (IOException e) {}

		return dictionary;
	}

	public static void main(String[] args) {
		WordSearch ws = new WordSearch();
        
        //working horizontal words
        ws.addWordH(0, 0, "hello");
        ws.addWordH(2, 4, "batman");
        ws.addWordH(5, 1, "apple");

        //Horizontal index error checking
        ws.addWordH(-2, 4, "joker");
        ws.addWordH(10, 4, "unicorn");  
        ws.addWordH(3, -1, "cowboys");
        ws.addWordH(5, 8, "dogs");

        //horizontal collision checking
        ws.addWordH(5, 3, "plow");
        ws.addWordH(2, 0, "neato");
        
        //working vertical words
        ws.addWordV(1, 0, "nice");
        ws.addWordV(4, 9, "yankee");
        ws.addWordV(4, 4, "old");
        
        //Verical index error checking
        ws.addWordV(-2, 4, "joker");
        ws.addWordV(7, 4, "unicorn");   
        ws.addWordV(3, -1, "cowboys");
        ws.addWordV(5, 20, "dogs");
        
        //vertical collision checking
        ws.addWordV(0, 4, "ores");
        ws.addWordV(4, 9, "goober");
       
        //working diagonal words
        ws.addWordD(7, 0,  "cat");
        ws.addWordD(0, 0, "home");
        ws.addWordD(0, 3, "loam");
        //Diagonal index error checking
        ws.addWordD(-2, 0,  "cat");
        ws.addWordD(3, -1,  "whelm");
        ws.addWordD(7, 7,  "after");    

        //Diagonal collision checking
        ws.addWordD(0, 4, "ores");
        ws.addWordD(4, 4, "oats");

        System.out.println(ws);
        
        ws.fillGrid();
        System.out.println(ws);

		//testing addWords method
		ws = new WordSearch();
		ws.addWords(10);
		ws.fillGrid();
		System.out.println(ws);
	}
}
