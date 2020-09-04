package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyConfig {
	//������Ҫ�ľ���
	final static public int EXP_PER_LEVEL = 1000;
	//ǩ����þ��飬Ӳ��
	final static public int EXP_CHECK_IN = 100;
	final static public int BALANCE_CHECK_IN = 1;
	
	//n����/Ӳ��
	final static public int EXP_PER_BALANCE = 100;
	//n����/��VIP
	final static public int EXP_PRE_VIP_MONTH = 100;
	
	
	static public String MSG_AUDIT_SUCC = "������Ƶ����%s����ͨ�������";
	static public String MSG_AUDIT_FAIL = "������Ƶ����%s����δͨ�����";
	static public String MSG_GOOD = "��%s��Ϊ������Ƶ��%s������";
	static public String MSG_MARK = "��%s���ղ���������Ƶ��%s��";
	static public String MSG_COMMENT = "��%s��������������Ƶ��%s������%s��";
	static public String MSG_GOOD_COMMENT = "��%s���������������ۡ�%s��";
	static public String MSG_REPLY = "��%s���ظ����������ۡ�%s������%s��";
	static public String MSG_VIP = "�ɹ���ͨVIP��Ա������ʱ�䡾%s��";
	static public String MSG_BALANCE = "�ɹ���ֵ��%d����Ӳ��";

	//TestAction:ÿ�����ݵ�����
	final  static public int TEST_IMPORT_NUM = 100;
	
	final static public int LEVEL_BAN=-1;
	final static public int LEVEL_USER=0;
	final static public int LEVEL_VIP=1;
	final static public int LEVEL_ADMIN=2;
	
	
	final static public int LEVEL_PRIVATE=0;//Ĭ��Ϊ˽��
	final static public int LEVEL_AUDIT=1;//���
	final static public int LEVEL_PUBLIC=2;//����
	final static public int LEVEL_FAIL=3;//���ʧ��
	
    //��Ƶ֧�ָ�ʽ
	final static public Set<String> FORMAT_SUPPORT= new HashSet<String>(){{
		add("video/mp4");
		add("video/webm");
		add("video/ogg");
	}};
	
	//��ֵVIP���·ݣ��۸��Ӧ��
	//���ֱ�ӱ���MAP���õ��Ĳ�������ģ�����������ʱ�������
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
	
	//500���ϣ�����������400-500�������400��������������ʾ
	final static public int ERR_NO_LOGIN = 10;		//û�е�¼
	final static public int ERR_NO_USER = 11;		//�û�������
	final static public int ERR_EXIST_USER = 12;	//�û��Ѵ���
	final static public int ERR_LEVEL = 13;			//Ȩ�޲���
	
	final static public int ERR_DEFAULT = 50;		//û��ѡ��ֱ�Ӵ���
	final static public int ERR_CONDITION = 51;		//������ĳЩ����
	
	
	final static public int ERR_PARAMS_MISS = 400;	//����ȱʧ
	final static public int ERR_OUT_RANGE = 401;	//���ݳ����޶���Χ
	final static public int ERR_TYPE = 402;			//���Ͳ���
	final static public int ERR_NULL = 403;			//����Ϊ��
	
	final static public int ERR_IO = 501;			//�ļ�����ʧ��
	
}
