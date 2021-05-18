package theone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	
	// le arquivo
	public static String[] readFile (String[] path_to_file) {
		String[] retorno = {""};
	            
		try {
			retorno[0] = Files.readString(Paths.get(path_to_file[0]), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	            
		return retorno;          
	}
	
	// remove quebra de linha e caracteres especiais
	public static String[] filterChars (String[] str_data) {
		
		String[] retorno = {str_data[0].replaceAll("\r\n", " ")}; // remove quebra de linha
		retorno[0] = retorno[0].replaceAll("[^a-zA-Z0-9 ã á é í ó õ â ô û]", " "); // remove caracteres especiais		
		return retorno;
	}
	
	// retorna a String em caixa baixa
	public static String[] normalize (String[] str_data) {
		String[] retorno = {str_data[0].toLowerCase()};
		return retorno;
	}
	
	// divide a String em um vetor de String, onde cada posicao do vetor se encontra uma palavra
	public static String[] scan (String[] str_data) {
		String[] retorno = str_data[0].replaceAll("( +)", " ").split(" ");
		return retorno;
	}
	
	// remove palavras de comprimento 1
	public static String[] remove_stop_words(String[] word_list) {
		
		String[] retorno = {""};
		List<String> list =  new ArrayList<String>();
		Collections.addAll(list, word_list);
		int j=0;
		
		for(int i=0; i< word_list.length; i++) {
			if (word_list[i].length() == 1) {
				list.remove(j);
			}
			else {
				j++;
			}
		}
		
		retorno = list.toArray(new String[list.size()]);
		
		return retorno;
	}
	
	// retorna a frequencia que cada palavra aparece na lista
	public static String[] frequencies(String[] word_list) {
		
		String[] retorno = {""};
		List<String> list =  new ArrayList<String>();
		Map<String, Integer> mapa = new HashMap<String, Integer>();
		
		for(int i=0; i<word_list.length; i++) {
			
			if(mapa.containsKey(word_list[i])) {
				mapa.replace(word_list[i], mapa.get(word_list[i]), mapa.get(word_list[i]) + 1);
			}
			else {
				list.add(word_list[i]);
				mapa.put(word_list[i], 1);
			}
			
		}
		
        List<String> listaux =  new ArrayList<String>();
		
		for(int i=0; i<list.size(); i++) {
			String aux = list.get(i) + ":" + mapa.get(list.get(i)).toString();
			listaux.add(aux);
		}
		
		retorno = listaux.toArray(new String[listaux.size()]);
		
		return retorno;
	}
	
	// ordena de forma decrescente as frequencias 
    public static String[] sort (String[] word_list) {
    	String[] retorno = {""};
        List<Pair<String,Integer>> listaux =  new ArrayList<Pair<String,Integer>>();
	
		for(int i=0; i<word_list.length; i++) {
			
            String[] par = word_list[i].replaceAll("( +)", " ").split(":");
            Pair<String,Integer> tmp = new Pair<String,Integer>(par[0],Integer.parseInt(par[1]));
			listaux.add(tmp);
		}
		        
        Collections.sort(listaux, Comparator.comparing(p -> -p.getR()));
		
        List<String> listaux2 =  new ArrayList<String>();
		
		for(int i=0; i<word_list.length; i++) {
			String aux = listaux.get(i).getL() + " - " + listaux.get(i).getR();
			listaux2.add(aux);
		}
        
		retorno = listaux2.toArray(new String[listaux.size()]);
                
		return retorno;
	}
    
    
	// retorna em uma String as 25 palavras (ou menos) mais frequentes
    public static String[] top25_freqs (String[] word_list) {
		
		String[] retorno = {""};
		
        Integer len = word_list.length < 25 ? word_list.length : 25;
        
        String aux = "";
        
		for(int i=0; i<len; i++) {
			aux = aux + word_list[i] + "\n";
		}
		
		retorno[0] = aux;
		
		return retorno;
	}
    

	public static void main(String[] args) {
		
		// declaracao das funcoes
		TFFunction read_file = (path_to_file) -> readFile (path_to_file);
		TFFunction filter_chars = (str_data) -> filterChars (str_data);
		TFFunction normalize = (str_data) -> normalize (str_data);
		TFFunction scan = (str_data) -> scan (str_data);
		TFFunction remove_stop_words = (word_list) -> remove_stop_words(word_list);
		TFFunction frequencies = (word_list) -> frequencies(word_list);
        TFFunction sort = (word_list) -> sort(word_list);
		TFFunction top25_freqs = (word_list) -> top25_freqs(word_list);
                
		String[] params = {"text.txt"};
		
		new TFTheOne(params)
			.bind(read_file)
			.bind(filter_chars)
			.bind(normalize)
			.bind(scan)
			.bind(remove_stop_words)
			.bind(frequencies)
        	.bind(sort)
            .bind(top25_freqs)
			.printme(0); // .printme() para os outros casos
		
	}
}
