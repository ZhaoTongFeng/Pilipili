package util;

import java.util.StringTokenizer;

public class GoodUtil {

	static public boolean setGood(GoodAPI obj, int user_id) {
		String[] user_ids = obj.getPeople_good().split(",");
		boolean isGood = false;
		for (int i = 0; i < user_ids.length; i++) {
			if ((int) Integer.valueOf(user_ids[i]) == (int) user_id) {
				isGood = true;
				break;
			}
		}
		if (isGood) {
			// 点过就取消点赞，并移除id
			obj.setNum_good(obj.getNum_good() - 1);

			obj.setPeople_good(obj.getPeople_good().replace("," + String.valueOf(user_id), ""));

		} else {
			obj.setNum_good(obj.getNum_good() + 1);
			obj.setPeople_good(obj.getPeople_good() + "," + String.valueOf(user_id));
		}
		return isGood;
	}

	
	static public Integer[] StringArraytoIntArray(String str) {
		String[] strings = str.split(",");
		Integer ret[] = new Integer[strings.length];
		for(int i=0;i<strings.length;i++){
			ret[i]=Integer.parseInt(strings[i]);
		}
		return ret;
	}
}
