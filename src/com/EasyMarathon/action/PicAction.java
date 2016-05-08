package com.EasyMarathon.action;

import java.applet.AppletContext;
import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.EasyMarathon.bean.PicBean;
import com.EasyMarathon.bean.Token;
import com.EasyMarathon.bean.WeixinOauth2Token;
import com.EasyMarathon.service.PicService;
import com.opensymphony.xwork2.ActionContext;
import com.EasyMarathon.util.AdvancedUtil;
import com.EasyMarathon.util.CommonUtil;

public class PicAction
{
	private PicBean picture;
	private String eventID;
	private String wechatID;

	public String getWechatID() {
		return wechatID;
	}

	public void setWechatID(String wechatID) {
		this.wechatID = wechatID;
	}

	public PicBean getPicture()
	{
		return picture;
	}

	public void setPicture(PicBean picture)
	{
		this.picture = picture;
	}

	public String getEventID()
	{
		return eventID;
	}

	public void setEventID(String eventID)
	{
		this.eventID = eventID;
	}

	public String uploadPic()
	{
		// System.out.println(picture.getFile().getAbsolutePath());
		PicService picservice = new PicService();

		if (picservice.uploadPicService(picture.getFile(), eventID))
			return "success";
		else
			return "fail";
	}

	public String uploadPicforUser(){
		// System.out.println(picture.getFile().getAbsolutePath());
		PicService picservice = new PicService();
		System.out.println("进入用户照片上传。。。");
		System.out.println("微信号："+wechatID);
		System.out.println("赛事号："+eventID);
		System.out.println(picture.getPicStatus());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			ServletInputStream in = request.getInputStream();
			int index=-1;
			byte[] b=new byte[1024];
			System.out.println("打印request");
			while((index = in.read(b))!=-1){
				System.out.println(b.toString());
			}
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		if (picservice.uploadPicforUserService(picture.getFile(), eventID,wechatID))
			return "success";
		else
			return "fail";
	}

	public String uploadforUser(){//普通用户上传照片
		System.out.println("--------< uploadforUser >--------");
		System.out.println();
		try {
			// 第三方用户唯一凭证
			String appId = "wxa6bb25947675b744";
			// 第三方用户唯一凭证密钥
			String appSecret = "c39ae4fc9da658a6642e2dd47626a45f";
			String code = (String)ServletActionContext.getRequest().getSession().getAttribute("code");
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil
								.getOauth2AccessToken("wxa6bb25947675b744",
								"c39ae4fc9da658a6642e2dd47626a45f", code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			
			System.out.println("--------< accessToken >--------");
			System.out.println(accessToken);
			String type="image";
			String mediaFilUrl=picture.getFile().getAbsolutePath();
			System.out.println("--------< mediaFilUrl >--------");
			System.out.println(mediaFilUrl);
			AdvancedUtil.uploadMedia(accessToken,type,mediaFilUrl);
		}catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
}
