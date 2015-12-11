package wic.wsd.dictionary;


import java.util.Arrays;
import java.util.StringTokenizer;


public class Definition {
	private int[] def;
	
	public Definition(String d){
		StringTokenizer st = new StringTokenizer(d);
		def = new int[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens()){
			def[i] = Integer.parseInt(st.nextToken());
			i++;
		}
	}
	
	public Definition(Definition d){
		int size = d.getSize();
		def = new int[size];
		for(int i=0; i<size ; i++){
			def[i] = d.getElement(i);
		}
	}
	
	public Definition(int size){
		def = new int[size];
		for(int i = 0; i < size; i++){
			def[i]=-1;
		}
	}
	
	public int getSize(){
		return def.length;
	}
	
	public void setElement(int index, int e){
		def[index] = e;
		try{
			Arrays.sort(def);
		} catch (ArrayIndexOutOfBoundsException ex){
			System.err.println(ex.getMessage());
		}
	}
	
	public int getElement(int index){
		return def[index];
	}
	
	
	public String toString(){
		String s = "";
		for(int i = 0; i < def.length; i++){
			s = s+def[i]+" ";
		}
		return s;
	}

}
