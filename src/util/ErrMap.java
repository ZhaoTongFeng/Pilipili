package util;

import java.util.HashMap;

public class ErrMap extends HashMap<String, Object>{
	public ErrMap(){
		put("code", -1);
	}
	public void putErr(String msg,int code){
		put("code", code);
		put("errmsg", msg);
		System.out.println(msg);
	}
	public boolean isErr(){    	
    	return (Integer) get("code")==-1?false:true;
	}
	
}
