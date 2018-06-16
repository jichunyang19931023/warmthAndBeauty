package com.cn.beauty.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class QiniuCloudUtil {

	// 设置需要操作的账号的AK和SK
	private static final String ACCESS_KEY = "-avNe1BI52DHc9yQrWAnsF1rvgVB2enMcKjbHpGi";
	private static final String SECRET_KEY = "0jDLyzd3_RNcKhu7zGMgGRcAQQkvM8WwSjAUGjrx";

	// 要上传的空间
	private static final String bucketname = "jichunyang";

	// 密钥
	private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	private static final String DOMAIN = "http://p748wa0e8.bkt.clouddn.com/";
	
	private static final String style = "imageView2/3/w/500/h/200/q/75|watermark/2/text/5bCP576O5aW9QGxpdHRsZSBiZWF1dHk=/font/5b6u6L2v6ZuF6buR/fontsize/300/fill/IzNGMzkzQw==/dissolve/84/gravity/SouthWest/dx/10/dy/10|imageslim";

	private static final String style2 = "imageView2/1/w/100/h/100/q/75|imageslim";
	// 普通上传
	public String upload(String filePath, String fileName) throws IOException {
		// 创建上传对象
		UploadManager uploadManager = new UploadManager();
		try {
			// 调用put方法上传
			String token = auth.uploadToken(bucketname);
			if(UtilValidate.isEmpty(token)) {
				System.out.println("未获取到token，请重试！");
				return null;
			}
			Response res = uploadManager.put(filePath, fileName, token);
			// 打印返回的信息
			System.out.println(res.bodyString());
			if (res.isOK()) {
				Ret ret = res.jsonToObject(Ret.class);
				return DOMAIN + ret.key + "?" + style;
			}
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}

	
	public static String getUpToken() {        
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }
	
	public static String put64image(byte[] base64, String key, boolean isAvatar) throws Exception {
        String file64 = Base64.encodeToString(base64, 0);
        Integer l = base64.length;
        String url = "http://upload.qiniu.com/putb64/" + l + "/key/"+ UrlSafeBase64.encodeToString(key);      
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        //System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        String mystyle = style;
        if(isAvatar) {
        	mystyle = style2;
        }
        return DOMAIN + key + "?" + mystyle;
    }
	
	
	// 普通删除
	public void delete(String key) throws IOException {
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth);
		// 此处的33是去掉：http://ongsua0j7.bkt.clouddn.com/,剩下的key就是图片在七牛云的名称
		key = key.substring(33);
		try {
			// 调用delete方法移动文件
			bucketManager.delete(bucketname, key);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}

	class Ret {
		public long fsize;
		public String key;
		public String hash;
		public int width;
		public int height;
	}
	
	/**
     * 得到网页中图片的地址
     */
    public static HashSet<String> getImgStr(String htmlStr) {
    	HashSet<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}