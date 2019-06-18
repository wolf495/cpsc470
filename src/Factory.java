//@author Joseph Haythorn
package big_project;


public class Factory {
	private static Factory theInstance;
	
	private Factory() {}

	public static Factory getInstance(){
		if(theInstance == null)	theInstance = new Factory();
		
		return theInstance;		
	}
	//takes String name and instantiates correct PlayerAbstract
	//object
        public PlayerAbstract Create(String name){
		PlayerAbstract player;
		try{
			player = (PlayerAbstract)Class.forName(name).newInstance();
			return player;
		}
	       	catch(InstantiationException e){
			e.printStackTrace();
		}
		catch(IllegalAccessException e){
			e.printStackTrace();
		}	
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		//should not return null
		return null;
	}
}
