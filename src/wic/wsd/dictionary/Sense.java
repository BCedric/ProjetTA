package wic.wsd.dictionary;




public class Sense {
	
	private String ids;
	private String senseID;
	private Definition def;
	
	public Sense(){
		ids = "";
		def = new Definition(1);
	}
	

	public void setIDS(String string){
		this.ids = string;
	}
	
	public String getIDS(){
		return this.ids;
	}

	public void setDef(String string){
		def = new Definition(string);
	}
	
	public Definition getDef(){
		return this.def;
	}

	/**
	 * @return the senseID
	 */
	public String getSenseID() {
	    return senseID;
	}


	/**
	 * @param senseID the senseID to set
	 */
	public void setSenseID(String senseID) {
	    this.senseID = senseID;
	}

	public String toString(){
		return def.toString();
	}
}
