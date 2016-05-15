package com.show.api.test;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Date;

import org.junit.Test;

import com.show.api.NormalRequest;
import com.show.api.ShowApiRequest;

public class ShowapiTest {
	
	String appid="3";
	String secret="43d67552d6614a6a9637360b4170fdf4";
	
	public void sssss() {
//		for(int i=0;i<100;i++){
			String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/909-1",appid,secret)
//			String res=new ShowApiRequest("http://route.showapi.com/900-2",appid,secret)
			   .addTextPara("from","北京")
		           .addTextPara("to","武汉")
		           .addTextPara("trainDate","2016-4-26")
		           .post();
			System.out.println(res);
//		}
		
	}

	@Test
	public void word转pdf() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/770-1",appid,secret)
	        .addFilePara("doc",new File("c:/222.doc"))
	        .post();
		System.out.println(res);
	}
	
	public void 文档预览2() {
		String res=new NormalRequest("http://api.huceo.com/wxnew/?key=8e87d70304eac6f51effd583b4b64989")
	        .addTextPara("num","10") //doc和url参数必须有一个
	        .get();
		System.out.println(res);
	}
	
	public void 二维码识别() {
		String res=new ShowApiRequest("http://route.showapi.com/887-2",appid,secret)
	           .addFilePara("img",new File("c:/aaa.png"))
	           .post();
		System.out.println(res);
	}
	
//	

	public void 小说章节内容查询() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/211-4",appid,secret)
	        .addTextPara("bookId","124227")
	        .addTextPara("cid","22048512")
	        .get();
		System.out.println(res);
	}
	
	
	
	public void 影院周排行榜() {
		String res=new ShowApiRequest("http://route.showapi.com/578-7",appid,secret)
		.setProxy("202.108.23.247", 80)
	        .post();
		System.out.println(res);
	}
	
	
	
	public void 根据地名查询天气() {
		for(int i=0;i<10;i++){
			String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/9-2",appid,secret)
		        .addTextPara("area","青岛市")
		        .post();
			System.out.println(res);
		}
		
	}
	
	public void 我爱单词类别() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/8-11",appid,secret)
	        .post();
		System.out.println(res);
	}
	
	public void 根据gps查询天气() {
		String res=new ShowApiRequest("http://route.showapi.com/9-5",appid,secret)
	           .addTextPara("from","5")
	           .addTextPara("lng","116.4038070000")
	           .addTextPara("lat","39.9237430000")
	           .post();
		System.out.println(res);
		
//		Longitude:116.4038070000   Latitude:39.9237430000
	}
	
	public void 网站信息查询() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/594-2",appid,secret)
	        .addTextPara("flb","1")
	        .addTextPara("n","1")
	        .get();
		System.out.println(res);
	}
	
	
	public void ocr() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/21-1",appid,secret)
	        .addTextPara("flb","1")
	        .addFilePara("image", new File("c:/1.jpg"))
//	        .addTextPara("showapi_res_gzip","1")
	        .post();
		System.out.println(res);
	}
	
	public void 快递公司查询() {
		String res=new ShowApiRequest("http://route.showapi.com/64-17",appid,secret)
		.setProxy("111.13.12.216", 80)
	        .addTextPara("comName","顺丰")
//	        .addTextPara("showapi_res_gzip","1")
	        .post();
		System.out.println(res);
	}

	
	public void fax() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/714-1",appid,secret)
	        .addTextPara("faxNumber","1")
	        .addTextPara("guestName","1")
	        .addTextPara("guestCompany","1")
	        .addFilePara("fileName", new File("c:/1.jpg"))
	        .addTextPara("showapi_res_gzip","1")
	        .post();
		System.out.println(res);
	}
	
	
	public void 天气() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/9-2",appid,secret)
	        .addTextPara("area","丽江")
	        .addTextPara("needMoreDay","0")
	        .addTextPara("needIndex","0")
	        .addTextPara("needHourData","0")
	        .get();
		System.out.println(res);
	}
	
	
	public void ip() {
		long a=System.currentTimeMillis();
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/20-1", appid, secret)
		.addTextPara("ip","221.1.1.2")
		.post();
		System.out.println(res);
		System.out.println(System.currentTimeMillis()-a);
	}
	public void qq音乐() {
		long a=System.currentTimeMillis();
		String res=new ShowApiRequest("http://route.showapi.com/213-1", appid, secret)
		.addTextPara("keyword","海阔天空")
		.post();
		System.out.println(res);
		System.out.println(System.currentTimeMillis()-a);
	}
	
	
	public void 条码查询() {
		long a=System.currentTimeMillis();
//		String res=new ShowApiRequest("http://121.41.60.109:903/fileRoute/run/66-22", appid, secret)
//		for(int i=0;i<10;i++){
			String res=new ShowApiRequest("http://route.showapi.com/66-22", appid, secret)
			.addTextPara("code","6938166920785")
			.post();
			System.out.println(res);
			System.out.println(System.currentTimeMillis()-a);
//		}
		
		
	}
	
	public void 图灵机器人() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/60-27",appid,secret)
	           .addTextPara("info","你是谁")
	           .post();
		System.out.println(res);
	}
	
	
	public void 微信精选() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/181-1", appid,secret)
	        .addTextPara("num","3")
	        .addTextPara("rand","1")
	        .addTextPara("word","中文")
	        .post();
		System.out.println(res);
	}
	
	public void 药企大全_地域信息() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/89-29", appid,secret)
	        .post();
		System.out.println(res);
	}
	
	
	public void 根据code查询股票() {
		String res=new ShowApiRequest("http://route.showapi.com/131-44",appid,secret)
	           .addTextPara("code","600000")
	           .post();
		System.out.println(res);
		
	}
	
	
	public void 百度poi() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/52-26",appid,secret)
	           .addTextPara("q","银行")
	           .addTextPara("showapi_res_gzip","1")
	           
	           .addTextPara("region","北京")
	           .post();
		System.out.println(res);
		
	}
	
	
	public void 车辆违章_直连查询() {
		String res=new ShowApiRequest("http://route.showapi.com/862-1",appid,secret)
		.setConnectTimeout(30000)
		.setReadTimeout(30000)
	        .addTextPara("carNumber","川A811LH")
	        .addTextPara("carCode","005433")
	        .addTextPara("carEngineCode","123456")
	        .post(); 
//		http://localhost:802/myapp?s=cwddd&m=r&carnumber=川QA8723&carframe=724743
		System.out.println(res);
	}
	
		
	public void 车辆违章_参数规则() {
		String res=new ShowApiRequest("http://route.showapi.com/139-121",appid,secret)
	        .addTextPara("preCarNum","蒙L")
	        .post();
		System.out.println(res);
	}
	
	
	public void 车辆违章查询() {
		String res=new ShowApiRequest("http://route.showapi.com/139-117", appid, secret)
		 .addTextPara("carNumber","粤BBT775")
	         .addTextPara("carCode","105103")
	         .addTextPara("carEngineCode","027693")
		.post();
		System.out.println(res);
	}
	
	
	public void 验证码识别() {
		String res=new ShowApiRequest("http://route.showapi.com/184-1", appid, secret)
		 .addFilePara("image",new File("c:/1.jpg"))
	         .addTextPara("typeId","3040")
		.post();
		System.out.println(res);
	}
	
	public void 车辆违章查罚款() {
		String res=new ShowApiRequest("http://route.showapi.com/139-118", appid, secret)
	         .addTextPara("recordId","0d536c95-0aae-4053-8514-10aaaa15cc66")
		.post();
		System.out.println(res);
	}
	public void 稳定代理 () {
		String res=new ShowApiRequest("http://route.showapi.com/22-2", appid, secret)
		.get();
		System.out.println(res);
	}
	
	public void 充值查询扣费金额() {
		String res=new ShowApiRequest("http://121.41.60.109:903/fileRoute/run/56-5", appid, secret)
		 .addTextPara("phone","13700645500")
		 .addTextPara("money","10")
		.post();
		System.out.println(res);
	}
	
	
	public void 充值查询状态() {
		String res=new ShowApiRequest("http://121.41.60.109:903/fileRoute/run/56-6", appid, secret)
		 .addTextPara("orderCode","1437985331373")
		.post();
		
		System.out.println(res);
	}
	
	
	
	public void 充值() {
		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/56-4", appid, secret)
		 .addTextPara("orderCode",""+new Date().getTime())
		 .addTextPara("phone","18908712871")
		 .addTextPara("money","10")
		.post();
		
		System.out.println(res);
	}
	
	
	public void 查询短信模板() {
		String res=new ShowApiRequest("http://route.showapi.com/28-3", appid, secret)
		.post();
		System.out.println(res);
	}
	
	
	public void 新增短信模板() {
		String res=new ShowApiRequest("http://route.showapi.com/28-2",appid,secret)
	        .addTextPara("content","您好,验证码是[code], 本验证码有效时间为[minute]分钟")
	        .addTextPara("title","易源接口")
	        .post();
		System.out.println(res);
	}
	
	
	
	
	public void  发送短信() {
		String res=new ShowApiRequest("http://route.showapi.com/28-1",appid,secret)
//		String res=new ShowApiRequest("http://www.abc.com:803/fileRoute/run/28-1",appid,secret)
	        .addTextPara("mobile","13700645500")
	        .addTextPara("content","{ code:'本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为本验证码有效时间为',minute:'3'}")
	        .addTextPara("tNum","T150713000124")
	        .addTextPara("big_msg","1")
	        
	        .post();
		System.out.println(res);
	}
	
	
	public void  发送不要模板的短信() {
		String res=new ShowApiRequest("http://route.showapi.com/860-1",appid,secret)
	        .addTextPara("mobile","15658664469")
	        .addTextPara("content","863352(验证码)，验证类型[1]。验证码有效时间为10分钟，请尽快验证")
	        .addTextPara("title","易源测试")
	        .addTextPara("big_msg","1")
	        .post();
		System.out.println(res);
	}
	
	
	public void  查询套餐余量() {
		String res=new ShowApiRequest("http://route.showapi.com/631-2",appid,secret)
	        .addTextPara("api_code","860")
	        .addTextPara("point_code","2")
	        .post();
		System.out.println(res);
	}
	
	
	public void  流量包充值() {
		String res=new ShowApiRequest("http://route.showapi.com/70-15",appid,secret)
	        .addTextPara("phone","13700645500")
	        .addTextPara("flowCount","10")
	        .addTextPara("orderCode",""+new Date().getTime())
	        .post();
		System.out.println(res);
	}
	public void  查询流量包价格() {
		String res=new ShowApiRequest("http://route.showapi.com/70-17",appid,secret)
	        .addTextPara("phone","13700645500")
	        .addTextPara("flowCount","10")
	        .post();
		System.out.println(res);
	}
	
	

}
