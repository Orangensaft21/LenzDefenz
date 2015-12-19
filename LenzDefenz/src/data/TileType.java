package data;

public enum TileType {
	
	Grass("grass",true,"0"),Dirt("dirt",false,"1"),Water("water",false,"2");
	
	String textureName;
	boolean buildable;
	String number;
	
	TileType(String textureName, boolean buildable, String number){
		this.textureName=textureName;
		this.buildable=buildable;
		this.number=number;
	}
	
	
	public String getNumber(){
		return number;
	}
	
}
