package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyConfig {
	//升级需要的经验
	final static public int EXP_PER_LEVEL = 1000;
	//签到获得经验，硬币
	final static public int EXP_CHECK_IN = 100;
	final static public int BALANCE_CHECK_IN = 1;
	
	//n经验/硬币
	final static public int EXP_PER_BALANCE = 100;
	//n经验/月VIP
	final static public int EXP_PRE_VIP_MONTH = 100;
	
	
	static public String MSG_AUDIT_SUCC = "您的视频：【%s】已通过了审核";
	static public String MSG_AUDIT_FAIL = "您的视频：【%s】已未通过审核";
	static public String MSG_GOOD = "【%s】为您的视频【%s】点赞";
	static public String MSG_MARK = "【%s】收藏了您的视频【%s】";
	static public String MSG_COMMENT = "【%s】评论了您的视频【%s】：【%s】";
	static public String MSG_GOOD_COMMENT = "【%s】点赞了您的评论【%s】";
	static public String MSG_REPLY = "【%s】回复了您的评论【%s】：【%s】";
	static public String MSG_VIP = "成功开通VIP会员，到期时间【%s】";
	static public String MSG_BALANCE = "成功充值【%d】个硬币";

	//TestAction:每种数据导入量
	final  static public int TEST_IMPORT_NUM = 100;
	
	final static public int LEVEL_BAN=-1;
	final static public int LEVEL_USER=0;
	final static public int LEVEL_VIP=1;
	final static public int LEVEL_ADMIN=2;
	
	
	final static public int LEVEL_PRIVATE=0;//默认为私有
	final static public int LEVEL_AUDIT=1;//审核
	final static public int LEVEL_PUBLIC=2;//公开
	final static public int LEVEL_FAIL=3;//审核失败
	
    //视频支持格式
	final static public Set<String> FORMAT_SUPPORT= new HashSet<String>(){{
		add("video/mp4");
		add("video/webm");
		add("video/ogg");
	}};
	
	//充值VIP，月份，价格对应表
	//如果直接遍历MAP，得到的不是有序的，所有这里暂时先用这个
	final static public List<Map<Integer, Integer>> VIP_MONTH_PRICE = new ArrayList<Map<Integer,Integer>>(){{
		add(new HashMap<Integer, Integer>(){{
			put(1, 30);
		}});
		add(new HashMap<Integer, Integer>(){{
			put(3, 88);
		}});
		add(new HashMap<Integer, Integer>(){{
			put(6, 148);
		}});
		add(new HashMap<Integer, Integer>(){{
			put(12, 248);
		}});
		add(new HashMap<Integer, Integer>(){{
			put(24, 358);
		}});
		add(new HashMap<Integer, Integer>(){{
			put(998, 998);
		}});
	}};

	static public String PATH_USER_IMG = "%s\\file\\image\\user\\%d\\";
	static public String SRC_USER_IMG = "file/image/user/%d/%s";
	
	static public String PATH_VIDEO = "%s\\file\\video\\%d\\";
	static public String SRC_VIDEO = "file/video/%d/%s";
	
	static public String PATH_VIDEO_IMG = "%s\\file\\video\\%d\\image\\";
	static public String SRC_VIDEO_IMG = "file/video/%d/image/%s";
	
	//500以上，服务器出错，400-500请求出错，400以下正常错误提示
	final static public int ERR_NO_LOGIN = 10;		//没有登录
	final static public int ERR_NO_USER = 11;		//用户不存在
	final static public int ERR_EXIST_USER = 12;	//用户已存在
	final static public int ERR_LEVEL = 13;			//权限不足
	
	final static public int ERR_DEFAULT = 50;		//没有选择，直接触发
	final static public int ERR_CONDITION = 51;		//不满足某些条件
	
	
	final static public int ERR_PARAMS_MISS = 400;	//数据缺失
	final static public int ERR_OUT_RANGE = 401;	//数据超出限定范围
	final static public int ERR_TYPE = 402;			//类型不对
	final static public int ERR_NULL = 403;			//数据为空
	
	final static public int ERR_IO = 501;			//文件保存失败
	
}
