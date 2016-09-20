import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Entropy {
	private String text;
	private Map<Character, Integer> single_map;
	private Map<String, Integer> pair_map;
	private Map<String, Integer> word_map;
	private int single_total;
	private int pair_total;
	private int word_total;
	private double single_entropy;
	private double pair_entropy;
	private double word_entropy;
	
	public Entropy (String filename){
		readTextFromFile(filename);
		
		getSingleMapFromText();
		calculateSingleEntropy();
		
		getPairMapFromText();
		calculatePairEntropy();
		
		getWordMapFromText();
		calculateWordEntropy();
	}
	
	public double getSingleEntropy(){
		return single_entropy;
	}
	
	public double getPairEntropy(){
		return pair_entropy;
	}
	
	public double getWordEntropy(){
		return word_entropy;
	}
	
	private void readTextFromFile (String filename){
		try{
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			
			text = "";
			String line = bf.readLine();
			while ((line = bf.readLine()) != null)
				text += " " + line;
			
			bf.close();
		} catch (Exception ex){}
	}
	
	private void getSingleMapFromText (){
		single_total = 0;
		single_map = new HashMap<Character, Integer>();
		
		for (int i=0; i<text.length(); i++){
			char key = text.charAt(i);
			
			if (single_map.containsKey(key))
				single_map.put(key, single_map.get(key) + 1);
			else
				single_map.put(key, 1);
			
			single_total++;
		}
	}
	
	private void getPairMapFromText (){
		pair_total = 0;
		pair_map = new HashMap<String, Integer>();
		
		for (int i=0; i<text.length()-1; i++){
			String key = "" + text.charAt(i) + text.charAt(i+1);
			
			if (pair_map.containsKey(key))
				pair_map.put(key, pair_map.get(key) + 1);
			else
				pair_map.put(key, 1);
			
			pair_total++;
		}
	}
	
	private void getWordMapFromText (){
		word_total = 0;
		word_map = new HashMap<String, Integer>();
		
		Set<Character> alpha = new TreeSet<Character>();
		for (char c = 'a'; c <= 'z'; c++)
			alpha.add(c);
		for (char c = 'A'; c <= 'Z'; c++)
			alpha.add(c);
		
		String alphaString = "çáàâäéèêëíìîïóòôöúùûü";
		alphaString += "ÇÁÀÂÄÉÈÊËÍÌÎÏÓÒÔÖÚÙÛÜβ'";
		for (int i=0; i<alphaString.length(); i++)
			alpha.add(alphaString.charAt(i));
		
		for (int i=0; i<text.length(); i++){
			String key = "";
			while (i < text.length() && alpha.contains(text.charAt(i)))
				key += text.charAt(i++);
			
			if (key.isEmpty())
				continue;
			
			if (word_map.containsKey(key))
				word_map.put(key, word_map.get(key) + 1);
			else
				word_map.put(key, 1);
			
			word_total++;
		}
	}
	
	private void calculateSingleEntropy (){
		single_entropy = 0;
		
		for (char key : single_map.keySet()){
			double p = (single_map.get(key))*1.0/single_total;
			single_entropy += -p*(Math.log(p)/Math.log(2));
		}
	}
	
	private void calculatePairEntropy (){
		pair_entropy = 0;
		
		for (char key1 : single_map.keySet()){
			double p1 = single_map.get(key1)*1.0/single_total;
			
			for (char key2 : single_map.keySet()){
				String pair = "" + key1 + key2;
				if (!pair_map.containsKey(pair))
					continue;
				
				double p_pair = pair_map.get(pair)*1.0/pair_total;
				double p_cond = p_pair/p1;
				
				pair_entropy += -p1*p_cond*(Math.log(p_cond)/Math.log(2));
			}
		}
	}
	
	private void calculateWordEntropy (){
		word_entropy = 0;
		
		for (String key : word_map.keySet()){
			double p = (word_map.get(key))*1.0/word_total;
			word_entropy += -p*(Math.log(p)/Math.log(2));
		}
	}
}
