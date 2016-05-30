package com.pdt.android_gis.util;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.pdt.android_gis.database.DBManager;

public class Constants {

	public static final int ERROR = 1001;// 缃戠粶寮傚父
	public static final int ROUTE_START_SEARCH = 2000;
	public static final int ROUTE_END_SEARCH = 2001;
	public static final int ROUTE_BUS_RESULT = 2002;// 璺緞瑙勫垝涓叕浜ゆā寮�
	public static final int ROUTE_DRIVING_RESULT = 2003;// 璺緞瑙勫垝涓┚杞︽ā寮�
	public static final int ROUTE_WALK_RESULT = 2004;// 璺緞瑙勫垝涓琛屾ā寮�
	public static final int ROUTE_NO_RESULT = 2005;// 璺緞瑙勫垝娌℃湁鎼滅储鍒扮粨鏋�

	public static final int GEOCODER_RESULT = 3000;// 鍦扮悊缂栫爜鎴栬�呴�嗗湴鐞嗙紪鐮佹垚鍔�
	public static final int GEOCODER_NO_RESULT = 3001;// 鍦扮悊缂栫爜鎴栬�呴�嗗湴鐞嗙紪鐮佹病鏈夋暟鎹�

	public static final int POISEARCH = 4000;// poi鎼滅储鍒扮粨鏋�
	public static final int POISEARCH_NO_RESULT = 4001;// poi娌℃湁鎼滅储鍒扮粨鏋�
	public static final int POISEARCH_NEXT = 5000;// poi鎼滅储涓嬩竴椤�

	public static final int BUSLINE_LINE_RESULT = 6001;// 鍏氦绾胯矾鏌ヨ
	public static final int BUSLINE_id_RESULT = 6002;// 鍏氦id鏌ヨ
	public static final int BUSLINE_NO_RESULT = 6003;// 寮傚父鎯呭喌

	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 鍖椾含甯傜粡绾害
	public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 鍖椾含甯備腑鍏虫潙缁忕含搴�
	public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 涓婃捣甯傜粡绾害
	public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 鏂规亽鍥介檯涓績缁忕含搴�
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 鎴愰兘甯傜粡绾害
	public static final LatLng JINNIU = new LatLng(30.689879, 103.994855);// 閲戠墰鍖虹粡绾害
	public static final LatLng CENTER = new LatLng(30.649879, 104.024855);// 閲戠墰鍖虹粡绾害
	public static final LatLng WUHOU = new LatLng(30.619879, 103.984855);// 閲戠墰鍖虹粡绾害
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 瑗垮畨甯傜粡绾害
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 閮戝窞甯傜粡绾害
	
	public static View color60Panel;
	public static View color70Panel;
	public static View color80Panel;
	public static View color90Panel;
	public static View color100Panel;
	public static View color110Panel;
	public static View color120Panel;
	public static View color130Panel;
	
	public boolean loginFlag;
	//SQLITE鏁版嵁搴�
	public static DBManager dbM;
	public static SQLiteDatabase database;
	public static RssMapColor rssmapcolor;
	
	public static String result;
	
	public static String ipaddress ="192.168.31.156";//鏈嶅姟鍣↖P鍦板潃 鍙墜鍔ㄩ厤缃�
	
	public static boolean uploadflag = false;//鑷姩涓婁紶or鎵嬪姩涓婁紶鏍囪瘑
	
}
